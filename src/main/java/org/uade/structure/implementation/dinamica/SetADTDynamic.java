package org.uade.structure.implementation.dinamica;

import org.uade.structure.definition.SetADT;
import org.uade.structure.exception.EmptyADTException;
import org.uade.structure.exception.ExistingValueException;
import org.uade.structure.exception.MissingValueException;

public class SetADTDynamic<T> implements SetADT<T> {
    private Nodo<T> first;
    private int size;

    public SetADTDynamic() {
        first = null;
        size = 0;
    }

    @Override
    public boolean isEmpty() {
        return first == null;
    }

    @Override
    public boolean exist(T value) {
        Nodo<T> aux = first;
        while (aux != null) {
            if (value.equals(aux.value)) { return true; }
            aux = aux.next;
        }
        return false;
    }

    @Override
    public void add(T value) {
        if (!exist(value)) {
            Nodo<T> nuevo = new Nodo<>(value);
            nuevo.next = first;
            first = nuevo;
            size++;
        } else { throw new ExistingValueException("El valor ya existe en el conjunto."); }
    }

    @Override
    public T choose() {
        if (!isEmpty()) {
            Nodo<T> aux = first;
            int random = (int) (Math.random() * size);
            int count = 0;
            while (count < random) {
                aux = aux.next;
                count++;
            }
            return aux.value;
        } else { throw new EmptyADTException("El conjunto está vacío."); }
    }

    @Override
    public void remove(T element) {
        if (isEmpty()) { throw new EmptyADTException("El conjunto está vacío."); }
        else if (!exist(element)) { throw new MissingValueException("El elemento no existe en el conjunto."); }
        else if (element.equals(first.value)) { first = first.next; size--; }
        else {
            Nodo<T> aux = first.next;
            Nodo<T> prev = first;
            while (!aux.value.equals(element)) {
                aux = aux.next;
                prev = prev.next;
            }
            prev.next = aux.next;
            size--;
        }
    }
}
