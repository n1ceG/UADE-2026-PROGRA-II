package org.uade.structure.implementation.dinamica;

import org.uade.structure.definition.SetADT;

public class NodoDicMultiple<K, V> {
    public K key;
    public SetADT<V> values;
    public NodoDicMultiple<K, V> next;

    public NodoDicMultiple(K key) {
        this.key = key;
        this.values = new SetADTDynamic<>();
        this.next = null;
    }
}
