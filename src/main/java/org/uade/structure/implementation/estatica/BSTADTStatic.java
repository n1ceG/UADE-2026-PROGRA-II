package org.uade.structure.implementation.estatica;

import org.uade.structure.definition.BinaryTreeADT;
import org.uade.structure.exception.EmptyADTException;
import org.uade.structure.exception.FullADTException;

// Implementación estática del BST usando arreglo posicional:
// la raíz está en el índice 0, y para un nodo en el índice i,
// su hijo izquierdo está en 2i+1 y su hijo derecho en 2i+2.
public class BSTADTStatic<T extends Comparable<T>> implements BinaryTreeADT<T> {
    @SuppressWarnings("unchecked")
    T[] data = (T[]) new Object[31];
    boolean[] used;
    private static final int CAPACIDAD = 31;

    public BSTADTStatic() {
        used = new boolean[CAPACIDAD];
    }

    private int left(int i) {
        return 2 * i + 1;
    }

    private int right(int i) {
        return 2 * i + 2;
    }

    @Override
    public void add(T value) {
        if (!used[0]) {
            data[0] = value;
            used[0] = true;
            return;
        }
        int i = 0;
        while (true) {
            if (data[i].compareTo(value) == 0) {
                return;
            }
            int next = (value.compareTo(data[i]) < 0) ? left(i) : right(i);
            if (next >= CAPACIDAD) {
                throw new FullADTException("El árbol superó su capacidad estática.");
            }
            if (!used[next]) {
                data[next] = value;
                used[next] = true;
                return;
            }
            i = next;
        }
    }

    private int indexOf(T value) {
        int i = 0;
        while (i < CAPACIDAD && used[i]) {
            if (data[i].compareTo(value) == 0) {
                return i;
            }
            i = (value.compareTo(data[i]) < 0) ? left(i) : right(i);
        }
        return -1;
    }

    @Override
    public void remove(T value) {
        int i = indexOf(value);
        if (i == -1) {
            return;
        }
        removeAt(i);
    }

    private void removeAt(int i) {
        int l = left(i);
        int r = right(i);
        boolean tieneIzq = l < CAPACIDAD && used[l];
        boolean tieneDer = r < CAPACIDAD && used[r];

        if (!tieneIzq && !tieneDer) {
            used[i] = false;
        } else if (tieneIzq && !tieneDer) {
            promoteSubtree(l, i);
        } else if (!tieneIzq) {
            promoteSubtree(r, i);
        } else {
            int minIdx = r;
            while (left(minIdx) < CAPACIDAD && used[left(minIdx)]) {
                minIdx = left(minIdx);
            }
            data[i] = data[minIdx];
            removeAt(minIdx);
        }
    }

    private void promoteSubtree(int src, int dest) {
        if (src >= CAPACIDAD || !used[src]) {
            used[dest] = false;
            return;
        }
        data[dest] = data[src];
        used[dest] = true;
        promoteSubtree(left(src), left(dest));
        promoteSubtree(right(src), right(dest));
        used[src] = false;
    }

    @Override
    public T getRoot() {
        if (isEmpty()) {
            throw new EmptyADTException("El árbol está vacío.");
        }
        return data[0];
    }

    @Override
    public BinaryTreeADT<T> getLeft() {
        if (isEmpty()) {
            throw new EmptyADTException("El árbol está vacío.");
        }
        BSTADTStatic<T> sub = new BSTADTStatic<>();
        copySubtree(left(0), 0, sub);
        return sub;
    }

    @Override
    public BinaryTreeADT<T> getRight() {
        if (isEmpty()) {
            throw new EmptyADTException("El árbol está vacío.");
        }
        BSTADTStatic<T> sub = new BSTADTStatic<>();
        copySubtree(right(0), 0, sub);
        return sub;
    }

    private void copySubtree(int srcIdx, int destIdx, BSTADTStatic<T> dest) {
        if (srcIdx >= CAPACIDAD || !used[srcIdx] || destIdx >= CAPACIDAD) {
            return;
        }
        dest.data[destIdx] = data[srcIdx];
        dest.used[destIdx] = true;
        copySubtree(left(srcIdx), left(destIdx), dest);
        copySubtree(right(srcIdx), right(destIdx), dest);
    }

    @Override
    public boolean isEmpty() {
        return !used[0];
    }
}
