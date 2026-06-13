package org.uade.structure.implementation.estatica;

import org.uade.structure.definition.GraphADT;
import org.uade.structure.definition.SetADT;
import org.uade.structure.exception.FullADTException;
import org.uade.structure.exception.InvalidVertexException;
import org.uade.structure.exception.MissingValueException;

// Implementación estática del grafo dirigido ponderado mediante matriz de adyacencia.
// La posición [i][j] contiene el peso de la arista i -> j, o NO_EDGE si no existe.
public class GraphADTStatic implements GraphADT {
    private int[][] adj;
    private boolean[] activeVertex;
    private int size;
    private static final int CAPACIDAD = 20;
    private static final int NO_EDGE = Integer.MIN_VALUE;

    public GraphADTStatic() {
        adj = new int[CAPACIDAD][CAPACIDAD];
        activeVertex = new boolean[CAPACIDAD];
        size = 0;
        int i = 0;
        while (i < CAPACIDAD) {
            int j = 0;
            while (j < CAPACIDAD) {
                adj[i][j] = NO_EDGE;
                j++;
            }
            i++;
        }
    }

    private void checkVertex(int v) {
        if (v < 0 || v >= CAPACIDAD || !activeVertex[v]) {
            throw new InvalidVertexException("El vértice " + v + " no existe.");
        }
    }

    @Override
    public void addVertx(int vertex) {
        if (vertex < 0 || vertex >= CAPACIDAD) {
            throw new InvalidVertexException("El identificador " + vertex + " está fuera de rango [0," + (CAPACIDAD - 1) + "].");
        }
        if (activeVertex[vertex]) {
            return;
        }
        if (size >= CAPACIDAD) {
            throw new FullADTException("El grafo está lleno.");
        }
        activeVertex[vertex] = true;
        size++;
    }

    @Override
    public void removeVertx(int vertex) {
        if (vertex < 0 || vertex >= CAPACIDAD || !activeVertex[vertex]) {
            return;
        }
        activeVertex[vertex] = false;
        size--;
        int i = 0;
        while (i < CAPACIDAD) {
            adj[vertex][i] = NO_EDGE;
            adj[i][vertex] = NO_EDGE;
            i++;
        }
    }

    @Override
    public void addEdge(int vertxOne, int vertxTwo, int weight) {
        checkVertex(vertxOne);
        checkVertex(vertxTwo);
        adj[vertxOne][vertxTwo] = weight;
    }

    @Override
    public void removeEdge(int vertxOne, int vertxTwo) {
        checkVertex(vertxOne);
        checkVertex(vertxTwo);
        adj[vertxOne][vertxTwo] = NO_EDGE;
    }

    @Override
    public boolean existsEdge(int vertxOne, int vertxTwo) {
        checkVertex(vertxOne);
        checkVertex(vertxTwo);
        return adj[vertxOne][vertxTwo] != NO_EDGE;
    }

    @Override
    public int edgeWeight(int vertxOne, int vertxTwo) {
        checkVertex(vertxOne);
        checkVertex(vertxTwo);
        if (adj[vertxOne][vertxTwo] == NO_EDGE) {
            throw new MissingValueException("No existe arista de " + vertxOne + " a " + vertxTwo + ".");
        }
        return adj[vertxOne][vertxTwo];
    }

    @Override
    public SetADT<Integer> getVertxs() {
        SetADT<Integer> vs = new SetADTStatic<>();
        int i = 0;
        while (i < CAPACIDAD) {
            if (activeVertex[i]) {
                vs.add(i);
            }
            i++;
        }
        return vs;
    }

    @Override
    public boolean isEmpty() {
        return size == 0;
    }
}
