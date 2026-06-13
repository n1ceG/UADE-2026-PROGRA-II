package org.uade.structure.implementation.estatica;

import org.uade.structure.definition.PriorityQueueADT;
import org.uade.structure.exception.EmptyADTException;

public class PriorityQueueADTStatic<T> implements PriorityQueueADT<T> {

    @SuppressWarnings("unchecked")
    private T[] values = (T[]) new Object[10];
    private int[] priorities;
    private int first;
    private int last;
    private int capacity;

    public PriorityQueueADTStatic() {
        this.capacity = 10;
        this.priorities = new int[capacity];
        this.first = 0;
        this.last = -1;
    }

    @Override
    public void add(T value, int priority) {
        if (last == capacity - 1) {
            resize();
        }

        int pos = first;
        while (pos <= last && priorities[pos] >= priority) {
            pos++;
        }

        int i = last;
        while (i >= pos) {
            values[i + 1] = values[i];
            priorities[i + 1] = priorities[i];
            i--;
        }

        values[pos] = value;
        priorities[pos] = priority;
        last++;
    }

    @Override
    public void remove() {
        if (isEmpty()) {
            throw new EmptyADTException("La cola con prioridad está vacía");
        }

        int i = first;
        while (i < last) {
            values[i] = values[i + 1];
            priorities[i] = priorities[i + 1];
            i++;
        }
        values[last] = null;
        last--;
    }

    @Override
    public T getElement() {
        if (isEmpty()) {
            throw new EmptyADTException("La cola con prioridad está vacía");
        }
        return values[first];
    }

    @Override
    public int getPriority() {
        if (isEmpty()) {
            throw new EmptyADTException("La cola con prioridad está vacía");
        }
        return priorities[first];
    }

    @Override
    public boolean isEmpty() {
        return first > last;
    }

    @SuppressWarnings("unchecked")
    private void resize() {
        int newCapacity = capacity * 2;
        T[] newValues = (T[]) new Object[newCapacity];
        int[] newPriorities = new int[newCapacity];

        int i = first;
        while (i <= last) {
            newValues[i - first] = values[i];
            newPriorities[i - first] = priorities[i];
            i++;
        }

        values = newValues;
        priorities = newPriorities;
        last = last - first;
        first = 0;
        capacity = newCapacity;
    }

    private void ordenarMayorAMenor() {
        if (isEmpty() || first == last) return;

        PriorityQueueADT<T> copia = this.copiar();
        PriorityQueueADT<T> ordenada = new PriorityQueueADTStatic<>();

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
        if (isEmpty() || first == last) return;

        PriorityQueueADT<T> copia = this.copiar();
        PriorityQueueADT<T> ordenada = new PriorityQueueADTStatic<>();

        while (!copia.isEmpty()) {
            T valor = copia.getElement();
            int prio = copia.getPriority();
            copia.remove();
            ordenada.add(valor, prio);
        }

        PriorityQueueADT<T> invertida = new PriorityQueueADTStatic<>();
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
        PriorityQueueADT<T> copia = new PriorityQueueADTStatic<>();
        PriorityQueueADT<T> temp = new PriorityQueueADTStatic<>();

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
