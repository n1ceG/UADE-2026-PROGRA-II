package org.uade.structure.implementation.dinamica;

public class NodoBinario<T extends Comparable<T>> {
    public T value;
    public NodoBinario<T> left;
    public NodoBinario<T> right;

    public NodoBinario(T value) {
        this.value = value;
        this.left = null;
        this.right = null;
    }
}
