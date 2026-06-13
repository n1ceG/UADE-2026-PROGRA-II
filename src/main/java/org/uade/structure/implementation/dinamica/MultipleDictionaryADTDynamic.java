package org.uade.structure.implementation.dinamica;

import org.uade.structure.definition.MultipleDictionaryADT;
import org.uade.structure.definition.SetADT;
import org.uade.structure.exception.EmptyADTException;
import org.uade.structure.exception.MissingValueException;

public class MultipleDictionaryADTDynamic<K, V> implements MultipleDictionaryADT<K, V> {
    private NodoDicMultiple<K, V> first;

    public MultipleDictionaryADTDynamic() {
        first = null;
    }

    private NodoDicMultiple<K, V> findNode(K key) {
        NodoDicMultiple<K, V> aux = first;
        while (aux != null) {
            if (aux.key.equals(key)) {
                return aux;
            }
            aux = aux.next;
        }
        return null;
    }

    @Override
    public void add(K key, V value) {
        NodoDicMultiple<K, V> n = findNode(key);
        if (n == null) {
            NodoDicMultiple<K, V> nuevo = new NodoDicMultiple<>(key);
            nuevo.values.add(value);
            nuevo.next = first;
            first = nuevo;
        } else {
            if (!n.values.exist(value)) {
                n.values.add(value);
            }
        }
    }

    @Override
    public void remove(K key) {
        if (isEmpty()) {
            return;
        }
        if (first.key.equals(key)) {
            first = first.next;
            return;
        }
        NodoDicMultiple<K, V> prev = first;
        NodoDicMultiple<K, V> aux = first.next;
        while (aux != null) {
            if (aux.key.equals(key)) {
                prev.next = aux.next;
                return;
            }
            prev = aux;
            aux = aux.next;
        }
    }

    @Override
    public void remove(K key, V value) {
        NodoDicMultiple<K, V> n = findNode(key);
        if (n == null) {
            return;
        }
        if (n.values.exist(value)) {
            n.values.remove(value);
            if (n.values.isEmpty()) {
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
        NodoDicMultiple<K, V> n = findNode(key);
        if (n == null) {
            throw new MissingValueException("La clave no existe en el diccionario.");
        }
        return setToArray(n.values);
    }

    @Override
    public SetADT<K> getKeys() {
        SetADT<K> keys = new SetADTDynamic<>();
        NodoDicMultiple<K, V> aux = first;
        while (aux != null) {
            keys.add(aux.key);
            aux = aux.next;
        }
        return keys;
    }

    @Override
    public boolean isEmpty() {
        return first == null;
    }

    // Vacía el set en una copia local mientras cuenta y arma el array,
    // y luego restaura el set original.
    @SuppressWarnings("unchecked")
    private V[] setToArray(SetADT<V> s) {
        SetADT<V> copia = new SetADTDynamic<>();
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
