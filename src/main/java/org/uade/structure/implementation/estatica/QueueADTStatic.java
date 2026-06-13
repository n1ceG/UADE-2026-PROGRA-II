package org.uade.structure.implementation.estatica;

import org.uade.structure.definition.QueueADT;
import org.uade.structure.exception.EmptyADTException;

public class QueueADTStatic<T> implements QueueADT<T> {

    @SuppressWarnings("unchecked")
    private T[] data = (T[]) new Object[10];
    private int first;
    private int last;

    public QueueADTStatic() {
        this.first = 0;
        this.last = -1;
    }

    @Override
    public void add(T value) {
        if (last == data.length - 1) {
            resize();
        }
        last++;
        data[last] = value;
    }

    @Override
    public void remove() {
        if (isEmpty()) {
            throw new EmptyADTException("La cola está vacía");
        }
        int i = first;
        while (i < last) {
            data[i] = data[i + 1];
            i++;
        }
        data[last] = null;
        last--;
    }

    @Override
    public T getElement() {
        if (isEmpty()) {
            throw new EmptyADTException("La cola está vacía");
        }
        return data[first];
    }

    @Override
    public boolean isEmpty() {
        return first > last;
    }

    @SuppressWarnings("unchecked")
    private void resize() {
        T[] newData = (T[]) new Object[data.length * 2];
        int i = first;
        while (i <= last) {
            newData[i - first] = data[i];
            i++;
        }
        data = newData;
        last = last - first;
        first = 0;
    }

    private QueueADT<T> copy() {
        QueueADT<T> copia = new QueueADTStatic<>();
        QueueADT<T> aux = new QueueADTStatic<>();

        while (!this.isEmpty()) {
            T valor = this.getElement();
            this.remove();
            aux.add(valor);
        }

        while (!aux.isEmpty()) {
            T valor = aux.getElement();
            aux.remove();
            this.add(valor);
            copia.add(valor);
        }
        return copia;
    }
}
