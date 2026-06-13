package org.uade.structure.implementation.dinamica;

import org.uade.structure.definition.LinkedListADT;

public class LinkedListADTDynamic<T> implements LinkedListADT<T> {
    private Nodo<T> head;
    private int size;

    public LinkedListADTDynamic() {
        this.head = null;
        this.size = 0;
    }

    @Override
    public void add(T value) {
        Nodo<T> nuevo = new Nodo<>(value);
        if (head == null) {
            head = nuevo;
        } else {
            Nodo<T> actual = head;
            while (actual.next != null) {
                actual = actual.next;
            }
            actual.next = nuevo;
        }
        size++;
    }

    @Override
    public void insert(int index, T value) {
        if (index < 0 || index > size) {
            throw new IndexOutOfBoundsException("Índice inválido: " + index);
        }
        Nodo<T> nuevo = new Nodo<>(value);
        if (index == 0) {
            nuevo.next = head;
            head = nuevo;
        } else {
            Nodo<T> actual = head;
            int i = 0;
            while (i < index - 1) {
                actual = actual.next;
                i++;
            }
            nuevo.next = actual.next;
            actual.next = nuevo;
        }
        size++;
    }

    @Override
    public void remove(int index) {
        if (index < 0 || index >= size) {
            throw new IndexOutOfBoundsException("Índice inválido: " + index);
        }
        if (index == 0) {
            head = head.next;
        } else {
            Nodo<T> actual = head;
            int i = 0;
            while (i < index - 1) {
                actual = actual.next;
                i++;
            }
            actual.next = actual.next.next;
        }
        size--;
    }

    @Override
    public T get(int index) {
        if (index < 0 || index >= size) {
            throw new IndexOutOfBoundsException("Índice inválido: " + index);
        }
        Nodo<T> actual = head;
        int i = 0;
        while (i < index) {
            actual = actual.next;
            i++;
        }
        return actual.value;
    }

    @Override
    public int size() {
        return size;
    }

    @Override
    public boolean isEmpty() {
        return size == 0;
    }
}
