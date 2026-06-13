package org.uade.structure.implementation.estatica;

import org.uade.structure.definition.MultipleDictionaryADT;
import org.uade.structure.definition.SetADT;
import org.uade.structure.exception.EmptyADTException;
import org.uade.structure.exception.FullADTException;
import org.uade.structure.exception.MissingValueException;

public class MultipleDictionaryADTStatic<K, V> implements MultipleDictionaryADT<K, V> {
    @SuppressWarnings("unchecked")
    private K[] claves = (K[]) new Object[10];
    @SuppressWarnings("unchecked")
    private SetADT<V>[] valores = (SetADT<V>[]) new SetADT[10];
    private int size;
    private static final int CAPACIDAD = 10;

    public MultipleDictionaryADTStatic() {
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
        if (idx == -1) {
            if (size >= CAPACIDAD) {
                throw new FullADTException("El diccionario está lleno.");
            }
            claves[size] = key;
            valores[size] = new SetADTStatic<>();
            valores[size].add(value);
            size++;
        } else {
            if (!valores[idx].exist(value)) {
                valores[idx].add(value);
            }
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
    public void remove(K key, V value) {
        int idx = indexOf(key);
        if (idx == -1) {
            return;
        }
        if (valores[idx].exist(value)) {
            valores[idx].remove(value);
            if (valores[idx].isEmpty()) {
                remove(key);
            }
        }
    }

    @Override
    @SuppressWarnings("unchecked")
    public V[] get(K key) {
        if (isEmpty()) {
            throw new EmptyADTException("El diccionario está vacío.");
        }
        int idx = indexOf(key);
        if (idx == -1) {
            throw new MissingValueException("La clave no existe en el diccionario.");
        }
        return setToArray(valores[idx]);
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

    // Vacía el set en una copia local mientras cuenta y arma el array,
    // y luego restaura el set original.
    @SuppressWarnings("unchecked")
    private V[] setToArray(SetADT<V> s) {
        SetADT<V> copia = new SetADTStatic<>();
        int count = 0;
        while (!s.isEmpty()) {
            V v = s.choose();
            s.remove(v);
            copia.add(v);
            count++;
        }
        V[] result = (V[]) new Object[count];
        int i = 0;
        while (!copia.isEmpty()) {
            V v = copia.choose();
            copia.remove(v);
            result[i] = v;
            s.add(v);
            i++;
        }
        return result;
    }
}
