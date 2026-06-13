package org.uade.structure.implementation.dinamica;

public class NodoDic<K, V> {
    public K key;
    public V value;
    public NodoDic<K, V> next;

    public NodoDic(K key, V value) {
        this.key = key;
        this.value = value;
        this.next = null;
    }
}
