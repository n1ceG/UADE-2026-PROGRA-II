package org.uade.structure.implementation.estatica;

import org.uade.structure.definition.SetADT;
import org.uade.structure.exception.EmptyADTException;
import org.uade.structure.exception.ExistingValueException;
import org.uade.structure.exception.MissingValueException;

public class SetADTStatic<T> implements SetADT<T> {
    @SuppressWarnings("unchecked")
    T[] data = (T[]) new Object[10];
    int size;

    public SetADTStatic() {
        size = 0;
    }

    @Override
    public boolean isEmpty() {
        return size == 0;
    }

    @Override
    public boolean exist(T value) {
        int count = 0;
        while (count < size) {
            if (value.equals(data[count])) { return true; }
            count++;
        }
        return false;
    }

    @Override
    public void add(T value) {
        if (!exist(value)) {
            data[size] = value;
            size++;
        } else { throw new ExistingValueException("El valor ya existe en el conjunto."); }
    }

    @Override
    public T choose() {
        if (!isEmpty()) {
            int index = (int) (Math.random() * size);
            return data[index];
        } else { throw new EmptyADTException("El conjunto está vacío"); }
    }

    @Override
    public void remove(T element) {
        int index = 0;
        if (!isEmpty()) {
            if (exist(element)) {
                int count = 0;
                while (count < size) {
                    if (data[count].equals(element)) {
                        index = count;
                    }
                    count++;
                }
                count = 0;
                while (count < size - 1) {
                    if (count >= index) { data[count] = data[count + 1]; }
                    count++;
                }
                data[size - 1] = null;
                size--;
            } else { throw new MissingValueException("El elemento no existe en el conjunto."); }
        } else { throw new EmptyADTException("El conjunto está vacío"); }
    }
}
