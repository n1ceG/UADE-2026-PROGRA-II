package org.uade.structure.implementation.dinamica;

public class Nodo<T> {
    public T value;
    public Nodo<T> next;
    public int priority; // Sólo para PrioQueue

    public Nodo(T value) {
        this.value = value;
        this.next = null;
        this.priority = 0;
    }

    public Nodo(T value, int priority) {
        this.value = value;
        this.next = null;
        this.priority = priority;
    }

    public Nodo() {
    }
}
