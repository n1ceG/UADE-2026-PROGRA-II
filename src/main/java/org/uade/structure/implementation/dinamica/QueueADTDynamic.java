package org.uade.structure.implementation.dinamica;

import org.uade.structure.definition.QueueADT;
import org.uade.structure.exception.EmptyADTException;

public class QueueADTDynamic<T> implements QueueADT<T> {
    public Nodo<T> first;
    public Nodo<T> last;

    public QueueADTDynamic() {
        this.first = null;
        this.last = null;
    }

    @Override
    public void add(T value) {
        Nodo<T> nuevo = new Nodo<>(value);
        if (isEmpty()) {
            first = nuevo;
            last = nuevo;
        } else {
            last.next = nuevo;
            last = nuevo;
        }
    }

    @Override
    public void remove() {
        if (isEmpty()) {
            throw new EmptyADTException("La cola está vacía.");
        }
        first = first.next;
        if (first == null) { last = null; }
    }

    @Override
    public T getElement() {
        if (isEmpty()) {
            throw new EmptyADTException("La cola está vacía.");
        }
        return first.value;
    }

    @Override
    public boolean isEmpty() {
        return first == null;
    }

    private QueueADTDynamic<T> copy() {
        QueueADTDynamic<T> copia = new QueueADTDynamic<>();
        QueueADTDynamic<T> aux = new QueueADTDynamic<>();
        while (!this.isEmpty()) {
            T value = this.getElement();
            this.remove();
            aux.add(value);
        }
        while (!aux.isEmpty()) {
            T value = aux.getElement();
            aux.remove();
            this.add(value);
            copia.add(value);
        }
        return copia;
    }
}
