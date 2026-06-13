package org.uade.structure.implementation.dinamica;

import org.uade.structure.definition.PriorityQueueADT;
import org.uade.structure.exception.EmptyADTException;

public class PriorityQueueADTDynamic<T> implements PriorityQueueADT<T> {

    private Nodo<T> head;

    public PriorityQueueADTDynamic() {
        this.head = null;
    }

    @Override
    public void add(T value, int priority) {
        Nodo<T> nuevo = new Nodo<>(value, priority);

        if (head == null || head.priority < priority) {
            nuevo.next = head;
            head = nuevo;
        } else {
            Nodo<T> actual = head;
            while (actual.next != null && actual.next.priority >= priority) {
                actual = actual.next;
            }
            nuevo.next = actual.next;
            actual.next = nuevo;
        }
    }

    @Override
    public void remove() {
        if (isEmpty()) {
            throw new EmptyADTException("La cola con prioridad está vacía");
        }
        head = head.next;
    }

    @Override
    public T getElement() {
        if (isEmpty()) {
            throw new EmptyADTException("La cola con prioridad está vacía");
        }
        return head.value;
    }

    @Override
    public int getPriority() {
        if (isEmpty()) {
            throw new EmptyADTException("La cola con prioridad está vacía");
        }
        return head.priority;
    }

    @Override
    public boolean isEmpty() {
        return head == null;
    }

    private void ordenarMayorAMenor() {
        if (isEmpty() || head.next == null) return;

        PriorityQueueADT<T> copia = this.copiar();
        PriorityQueueADT<T> ordenada = new PriorityQueueADTDynamic<>();

        while (!copia.isEmpty()) {
            T valor = copia.getElement();
            int prio = copia.getPriority();
            copia.remove();
            ordenada.add(valor, prio);
        }

        while (!this.isEmpty()) {
            this.remove();
        }

        while (!ordenada.isEmpty()) {
            this.add(ordenada.getElement(), ordenada.getPriority());
            ordenada.remove();
        }
    }

    private void ordenarMenorAMayor() {
        if (isEmpty() || head.next == null) return;

        PriorityQueueADT<T> copia = this.copiar();
        PriorityQueueADT<T> ordenada = new PriorityQueueADTDynamic<>();

        while (!copia.isEmpty()) {
            T valor = copia.getElement();
            int prio = copia.getPriority();
            copia.remove();
            ordenada.add(valor, prio);
        }

        PriorityQueueADT<T> invertida = new PriorityQueueADTDynamic<>();
        while (!ordenada.isEmpty()) {
            T valor = ordenada.getElement();
            int prio = ordenada.getPriority();
            ordenada.remove();
            invertida.add(valor, -prio);
        }

        while (!this.isEmpty()) {
            this.remove();
        }

        while (!invertida.isEmpty()) {
            T valor = invertida.getElement();
            int prio = invertida.getPriority();
            invertida.remove();
            this.add(valor, -prio);
        }
    }

    private void imprimir() {
        if (isEmpty()) {
            System.out.println("PriorityQueue vacía");
            return;
        }

        PriorityQueueADT<T> copia = this.copiar();

        System.out.print("PriorityQueue [ ");
        while (!copia.isEmpty()) {
            T valor = copia.getElement();
            int prio = copia.getPriority();
            copia.remove();
            System.out.print("(" + valor + ", prio=" + prio + ") ");
        }
        System.out.println("]");
    }

    private PriorityQueueADT<T> copiar() {
        PriorityQueueADT<T> copia = new PriorityQueueADTDynamic<>();
        PriorityQueueADT<T> temp = new PriorityQueueADTDynamic<>();

        while (!this.isEmpty()) {
            T valor = this.getElement();
            int prio = this.getPriority();
            this.remove();
            temp.add(valor, prio);
        }

        while (!temp.isEmpty()) {
            T valor = temp.getElement();
            int prio = temp.getPriority();
            temp.remove();
            this.add(valor, prio);
            copia.add(valor, prio);
        }
        return copia;
    }
}
