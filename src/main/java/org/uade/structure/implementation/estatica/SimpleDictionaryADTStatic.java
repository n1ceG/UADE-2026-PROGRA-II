package org.uade.structure.implementation.estatica;

import org.uade.structure.definition.SetADT;
import org.uade.structure.definition.SimpleDictionaryADT;
import org.uade.structure.exception.EmptyADTException;
import org.uade.structure.exception.FullADTException;
import org.uade.structure.exception.MissingValueException;

public class SimpleDictionaryADTStatic<K, V> implements SimpleDictionaryADT<K, V> {
    @SuppressWarnings("unchecked")
    private K[] claves = (K[]) new Object[10];
    @SuppressWarnings("unchecked")
    private V[] valores = (V[]) new Object[10];
    private int size;
    private static final int CAPACIDAD = 10;

    public SimpleDictionaryADTStatic() {
        size = 0;
    }

    private int indexOf(K key) {
        int count = 0;
        while (count < size) {
            if (claves[count].equals(key)) {
                return count;
            }
            count++;
        }
        return -1;
    }

    @Override
    public void add(K key, V value) {
        int idx = indexOf(key);
        if (idx != -1) {
            valores[idx] = value;
        } else {
            if (size >= CAPACIDAD) {
                throw new FullADTException("El diccionario está lleno.");
            }
            claves[size] = key;
            valores[size] = value;
            size++;
        }
    }

    @Override
    public void remove(K key) {
        int idx = indexOf(key);
        if (idx == -1) {
            return;
        }
        int count = idx;
        while (count < size - 1) {
            claves[count] = claves[count + 1];
            valores[count] = valores[count + 1];
            count++;
        }
        claves[size - 1] = null;
        valores[size - 1] = null;
        size--;
    }

    @Override
    public V get(K key) {
        if (isEmpty()) {
            throw new EmptyADTException("El diccionario está vacío.");
        }
        int idx = indexOf(key);
        if (idx == -1) {
            throw new MissingValueException("La clave no existe en el diccionario.");
        }
        return valores[idx];
    }

    @Override
    public SetADT<K> getKeys() {
        SetADT<K> keys = new SetADTStatic<>();
        int count = 0;
        while (count < size) {
            keys.add(claves[count]);
            count++;
        }
        return keys;
    }

    @Override
    public boolean isEmpty() {
        return size == 0;
    }
}
