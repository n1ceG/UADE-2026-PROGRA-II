package org.uade.structure.implementation.dinamica;

import org.uade.structure.definition.BinaryTreeADT;
import org.uade.structure.exception.EmptyADTException;

public class BSTADTDynamic<T extends Comparable<T>> implements BinaryTreeADT<T> {
    private NodoBinario<T> root;

    public BSTADTDynamic() {
        this.root = null;
    }

    private BSTADTDynamic(NodoBinario<T> root) {
        this.root = root;
    }

    @Override
    public void add(T value) {
        root = addRec(root, value);
    }

    private NodoBinario<T> addRec(NodoBinario<T> n, T value) {
        if (n == null) {
            return new NodoBinario<>(value);
        }
        if (value.compareTo(n.value) < 0) {
            n.left = addRec(n.left, value);
        } else if (value.compareTo(n.value) > 0) {
            n.right = addRec(n.right, value);
        }
        return n;
    }

    @Override
    public void remove(T value) {
        root = removeRec(root, value);
    }

    private NodoBinario<T> removeRec(NodoBinario<T> n, T value) {
        if (n == null) {
            return null;
        }
        if (value.compareTo(n.value) < 0) {
            n.left = removeRec(n.left, value);
        } else if (value.compareTo(n.value) > 0) {
            n.right = removeRec(n.right, value);
        } else {
            if (n.left == null && n.right == null) {
                return null;
            }
            if (n.left == null) {
                return n.right;
            }
            if (n.right == null) {
                return n.left;
            }
            // Dos hijos: reemplazo por el mínimo del subárbol derecho.
            T min = findMin(n.right);
            n.value = min;
            n.right = removeRec(n.right, min);
        }
        return n;
    }

    private T findMin(NodoBinario<T> n) {
        while (n.left != null) {
            n = n.left;
        }
        return n.value;
    }

    @Override
    public T getRoot() {
        if (isEmpty()) {
            throw new EmptyADTException("El árbol está vacío.");
        }
        return root.value;
    }

    @Override
    public BinaryTreeADT<T> getLeft() {
        if (isEmpty()) {
            throw new EmptyADTException("El árbol está vacío.");
        }
        return new BSTADTDynamic<>(root.left);
    }

    @Override
    public BinaryTreeADT<T> getRight() {
        if (isEmpty()) {
            throw new EmptyADTException("El árbol está vacío.");
        }
        return new BSTADTDynamic<>(root.right);
    }

    @Override
    public boolean isEmpty() {
        return root == null;
    }
}
