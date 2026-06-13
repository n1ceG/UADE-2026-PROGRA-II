package org.uade.structure.implementation.dinamica;

public class NodoGrafo<V> {
    public V nodo;
    public NodoArista<V> arista;
    public NodoGrafo<V> sigNodo;

    public NodoGrafo(V nodo) {
        this.nodo = nodo;
        this.arista = null;
        this.sigNodo = null;
    }
}
