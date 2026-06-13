package org.uade.structure.implementation.dinamica;

public class NodoArista<V> {
    public int etiqueta;
    public NodoGrafo<V> nodoDestino;
    public NodoArista<V> sigArista;

    public NodoArista(NodoGrafo<V> nodoDestino, int etiqueta) {
        this.nodoDestino = nodoDestino;
        this.etiqueta = etiqueta;
        this.sigArista = null;
    }
}
