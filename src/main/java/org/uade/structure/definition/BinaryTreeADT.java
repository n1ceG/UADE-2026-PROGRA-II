package org.uade.structure.definition;

// Esta interfaz representa el TDA Árbol Binario.
// En la implementación de BST (Binary Search Tree) se respeta el invariante:
// todo nodo del subárbol izquierdo es menor que la raíz, y todo nodo del
// subárbol derecho es mayor que la raíz, sin valores repetidos.
public interface BinaryTreeADT<T extends Comparable<T>> {

    /**
     * Descripcion: Devuelve el valor de la raíz.
     * Precondición: El árbol no debe estar vacío.
     */
    T getRoot();

    /**
     * Descripcion: Devuelve el subárbol izquierdo.
     * Precondición: El árbol no debe estar vacío.
     */
    BinaryTreeADT<T> getLeft();

    /**
     * Descripcion: Devuelve el subárbol derecho.
     * Precondición: El árbol no debe estar vacío.
     */
    BinaryTreeADT<T> getRight();

    /**
     * Descripcion: Inserta un valor respetando el orden del BST. Si el valor
     * ya existe no hace nada.
     * Precondición: La estructura no debe sobrepasar la capacidad.
     */
    void add(T value);

    /**
     * Descripcion: Elimina el valor del árbol. Si el valor no existe no hace
     * nada. Contempla los tres casos: hoja, un hijo, dos hijos.
     * Precondición: No tiene.
     */
    void remove(T value);

    /**
     * Descripcion: Debe comprobar si la estructura tiene o no valores.
     * Precondición: No tiene.
     */
    boolean isEmpty();
}
