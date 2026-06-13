package org.uade.structure.implementation.estatica;

import org.uade.structure.definition.LinkedListADT;

public class LinkedListADTStatic<T> implements LinkedListADT<T> {

    @SuppressWarnings("unchecked")
    private T[] data = (T[]) new Object[10];
    private int size;

    public LinkedListADTStatic() {
        this.size = 0;
    }

    @Override
    public void add(T value) {
        if (size == data.length) {
            resize();
        }
        data[size] = value;
        size++;
    }

    @Override
    public void insert(int index, T value) {
        if (index < 0 || index > size) {
            throw new IndexOutOfBoundsException("Índice inválido: " + index);
        }
        if (size == data.length) {
            resize();
        }
        int i = size;
        while (i > index) {
            data[i] = data[i - 1];
            i--;
        }
        data[index] = value;
        size++;
    }

    @Override
    public void remove(int index) {
        if (index < 0 || index > size) {
            throw new IndexOutOfBoundsException("Índice inválido: " + index);
        }
        int i = index;
        while (i < size - 1) {
            data[i] = data[i + 1];
            i++;
        }
        data[size - 1] = null;
        size--;
    }

    @Override
    public T get(int index) {
        if (index < 0 || index >= size) {
            throw new IndexOutOfBoundsException("Índice inválido: " + index);
        }
        return data[index];
    }

    @Override
    public int size() {
        return size;
    }

    @Override
    public boolean isEmpty() {
        return size == 0;
    }

    @SuppressWarnings("unchecked")
    private void resize() {
        T[] newData = (T[]) new Object[data.length + 10];
        int i = 0;
        while (i < size) {
            newData[i] = data[i];
            i++;
        }
        data = newData;
    }
}
