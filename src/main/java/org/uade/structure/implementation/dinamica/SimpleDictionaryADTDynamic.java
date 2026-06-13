package org.uade.structure.implementation.dinamica;

import org.uade.structure.definition.SetADT;
import org.uade.structure.definition.SimpleDictionaryADT;
import org.uade.structure.exception.EmptyADTException;
import org.uade.structure.exception.MissingValueException;

public class SimpleDictionaryADTDynamic<K, V> implements SimpleDictionaryADT<K, V> {
    private NodoDic<K, V> first;

    public SimpleDictionaryADTDynamic() {
        first = null;
    }

    private NodoDic<K, V> findNode(K key) {
        NodoDic<K, V> aux = first;
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
        NodoDic<K, V> existente = findNode(key);
        if (existente != null) {
            existente.value = value;
        } else {
            NodoDic<K, V> nuevo = new NodoDic<>(key, value);
            nuevo.next = first;
            first = nuevo;
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
        NodoDic<K, V> prev = first;
        NodoDic<K, V> aux = first.next;
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
    public V get(K key) {
        if (isEmpty()) {
            throw new EmptyADTException("El diccionario está vacío.");
        }
        NodoDic<K, V> n = findNode(key);
        if (n == null) {
            throw new MissingValueException("La clave no existe en el diccionario.");
        }
        return n.value;
    }

    @Override
    public SetADT<K> getKeys() {
        SetADT<K> keys = new SetADTDynamic<>();
        NodoDic<K, V> aux = first;
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
}
