package org.uade.structure.definition;

// Esta interfaz representa el TDA Grafo dirigido y ponderado con vértices enteros.
// Las aristas tienen un peso (etiqueta) entero asociado.
public interface GraphADT {

    /**
     * Descripcion: Devuelve el conjunto de vértices del grafo.
     * Precondición: No tiene.
     */
    SetADT<Integer> getVertxs();

    /**
     * Descripcion: Agrega un vértice. Si el vértice ya existe no hace nada.
     * Precondición: La estructura no debe sobrepasar la capacidad.
     */
    void addVertx(int vertex);

    /**
     * Descripcion: Elimina el vértice y todas las aristas que lo tocan
     * (entrantes y salientes). Si no existe no hace nada.
     * Precondición: No tiene.
     */
    void removeVertx(int vertex);

    /**
     * Descripcion: Agrega una arista dirigida de vertxOne a vertxTwo con el
     * peso indicado. Si ya existe pisa el peso.
     * Precondición: vertxOne y vertxTwo deben existir como vértices.
     */
    void addEdge(int vertxOne, int vertxTwo, int weight);

    /**
     * Descripcion: Elimina la arista dirigida de vertxOne a vertxTwo. Si no
     * existe no hace nada.
     * Precondición: vertxOne y vertxTwo deben existir como vértices.
     */
    void removeEdge(int vertxOne, int vertxTwo);

    /**
     * Descripcion: Devuelve true si existe una arista dirigida de vertxOne a
     * vertxTwo.
     * Precondición: vertxOne y vertxTwo deben existir como vértices.
     */
    boolean existsEdge(int vertxOne, int vertxTwo);

    /**
     * Descripcion: Devuelve el peso de la arista de vertxOne a vertxTwo.
     * Precondición: La arista debe existir.
     */
    int edgeWeight(int vertxOne, int vertxTwo);

    /**
     * Descripcion: Debe comprobar si la estructura tiene o no vértices.
     * Precondición: No tiene.
     */
    boolean isEmpty();
}
