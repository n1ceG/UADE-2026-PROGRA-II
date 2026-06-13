package org.uade.structure.implementation.dinamica;

import org.uade.structure.definition.StackADT;
import org.uade.structure.exception.EmptyADTException;

public class StackADTDynamic<T> implements StackADT<T> {

    private Nodo<T> top;

    public StackADTDynamic() {
        this.top = null;
    }

    @Override
    public void add(T value) {
        Nodo<T> nuevo = new Nodo<>(value);
        nuevo.next = top;
        top = nuevo;
    }

    @Override
    public void remove() {
        if (isEmpty()) {
            throw new EmptyADTException("La pila está vacía.");
        }
        top = top.next;
    }

    @Override
    public T getElement() {
        if (isEmpty()) {
            throw new EmptyADTException("La pila está vacía.");
        }
        return top.value;
    }

    @Override
    public boolean isEmpty() {
        return top == null;
    }

    private StackADTDynamic<T> copiar() {
        StackADTDynamic<T> copia = new StackADTDynamic<>();
        StackADTDynamic<T> temp = new StackADTDynamic<>();

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
