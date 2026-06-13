package org.uade.structure.implementation.dinamica;

import org.uade.structure.definition.GraphADT;
import org.uade.structure.definition.SetADT;
import org.uade.structure.exception.InvalidVertexException;
import org.uade.structure.exception.MissingValueException;

// Implementación dinámica del grafo dirigido ponderado mediante listas de adyacencia.
// El grafo es una lista enlazada de NodoGrafo. Cada NodoGrafo tiene su propia
// lista enlazada de NodoArista hacia sus vértices destino (con peso).
public class GraphADTDynamic implements GraphADT {
    private NodoGrafo<Integer> first;

    public GraphADTDynamic() {
        this.first = null;
    }

    private NodoGrafo<Integer> findVertex(int v) {
        NodoGrafo<Integer> aux = first;
        while (aux != null) {
            if (aux.nodo.equals(v)) {
                return aux;
            }
            aux = aux.sigNodo;
        }
        return null;
    }

    private NodoGrafo<Integer> requireVertex(int v) {
        NodoGrafo<Integer> n = findVertex(v);
        if (n == null) {
            throw new InvalidVertexException("El vértice " + v + " no existe.");
        }
        return n;
    }

    private NodoArista<Integer> findEdge(NodoGrafo<Integer> from, int destId) {
        NodoArista<Integer> aux = from.arista;
        while (aux != null) {
            if (aux.nodoDestino.nodo.equals(destId)) {
                return aux;
            }
            aux = aux.sigArista;
        }
        return null;
    }

    @Override
    public void addVertx(int vertex) {
        if (vertex < 0) {
            throw new InvalidVertexException("El identificador del vértice debe ser >= 0.");
        }
        if (findVertex(vertex) != null) {
            return;
        }
        NodoGrafo<Integer> nuevo = new NodoGrafo<>(vertex);
        nuevo.sigNodo = first;
        first = nuevo;
    }

    @Override
    public void removeVertx(int vertex) {
        NodoGrafo<Integer> target = findVertex(vertex);
        if (target == null) {
            return;
        }
        if (first == target) {
            first = first.sigNodo;
        } else {
            NodoGrafo<Integer> prev = first;
            while (prev.sigNodo != target) {
                prev = prev.sigNodo;
            }
            prev.sigNodo = target.sigNodo;
        }
        NodoGrafo<Integer> aux = first;
        while (aux != null) {
            removeEdgeFromList(aux, vertex);
            aux = aux.sigNodo;
        }
    }

    private void removeEdgeFromList(NodoGrafo<Integer> from, int destId) {
        if (from.arista == null) {
            return;
        }
        if (from.arista.nodoDestino.nodo.equals(destId)) {
            from.arista = from.arista.sigArista;
            return;
        }
        NodoArista<Integer> prev = from.arista;
        NodoArista<Integer> cur = from.arista.sigArista;
        while (cur != null) {
            if (cur.nodoDestino.nodo.equals(destId)) {
                prev.sigArista = cur.sigArista;
                return;
            }
            prev = cur;
            cur = cur.sigArista;
        }
    }

    @Override
    public void addEdge(int vertxOne, int vertxTwo, int weight) {
        NodoGrafo<Integer> origen = requireVertex(vertxOne);
        NodoGrafo<Integer> destino = requireVertex(vertxTwo);
        NodoArista<Integer> existente = findEdge(origen, vertxTwo);
        if (existente != null) {
            existente.etiqueta = weight;
            return;
        }
        NodoArista<Integer> nueva = new NodoArista<>(destino, weight);
        nueva.sigArista = origen.arista;
        origen.arista = nueva;
    }

    @Override
    public void removeEdge(int vertxOne, int vertxTwo) {
        NodoGrafo<Integer> origen = requireVertex(vertxOne);
        requireVertex(vertxTwo);
        removeEdgeFromList(origen, vertxTwo);
    }

    @Override
    public boolean existsEdge(int vertxOne, int vertxTwo) {
        NodoGrafo<Integer> origen = requireVertex(vertxOne);
        requireVertex(vertxTwo);
        return findEdge(origen, vertxTwo) != null;
    }

    @Override
    public int edgeWeight(int vertxOne, int vertxTwo) {
        NodoGrafo<Integer> origen = requireVertex(vertxOne);
        requireVertex(vertxTwo);
        NodoArista<Integer> e = findEdge(origen, vertxTwo);
        if (e == null) {
            throw new MissingValueException("No existe arista de " + vertxOne + " a " + vertxTwo + ".");
        }
        return e.etiqueta;
    }

    @Override
    public SetADT<Integer> getVertxs() {
        SetADT<Integer> vs = new SetADTDynamic<>();
        NodoGrafo<Integer> aux = first;
        while (aux != null) {
            vs.add(aux.nodo);
            aux = aux.sigNodo;
        }
        return vs;
    }

    @Override
    public boolean isEmpty() {
        return first == null;
    }
}
