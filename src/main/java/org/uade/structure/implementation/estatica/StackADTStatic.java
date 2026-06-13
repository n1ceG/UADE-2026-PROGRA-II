package org.uade.structure.implementation.estatica;

import org.uade.structure.definition.StackADT;
import org.uade.structure.exception.EmptyADTException;

public class StackADTStatic<T> implements StackADT<T> {
    @SuppressWarnings("unchecked")
    private T[] data = (T[]) new Object[10];
    private int top;

    public StackADTStatic() {
        this.top = -1;
    }

    @Override
    public void add(T value) {
        if (top == data.length - 1) {
            resize();
        }
        top++;
        data[top] = value;
    }

    @Override
    public void remove() {
        if (isEmpty()) {
            throw new EmptyADTException("La pila está vacía.");
        }
        data[top] = null;
        top--;
    }

    @Override
    public T getElement() {
        if (isEmpty()) {
            throw new EmptyADTException("La pila está vacía.");
        }
        return data[top];
    }

    @Override
    public boolean isEmpty() {
        return top == -1;
    }

    @SuppressWarnings("unchecked")
    private void resize() {
        T[] newData = (T[]) new Object[data.length + 10];
        int i = 0;
        while (i <= top) {
            newData[i] = data[i];
            i++;
        }
        data = newData;
    }

    private StackADTStatic<T> copiar() {
        StackADTStatic<T> copia = new StackADTStatic<>();
        StackADTStatic<T> temp = new StackADTStatic<>();

        while (!this.isEmpty()) {
            temp.add(this.getElement());
            this.remove();
        }

        while (!temp.isEmpty()) {
            T valor = temp.getElement();
            temp.remove();
            this.add(valor);
            copia.add(valor);
        }
        return copia;
    }
}
