package org.uade.tests;

import org.uade.structure.definition.BinaryTreeADT;
import org.uade.structure.implementation.dinamica.BSTADTDynamic;
import org.uade.structure.implementation.estatica.BSTADTStatic;

public class TestBST {

    public static void main(String[] args) {
        System.out.println("=== BST Estático ===");
        run(new BSTADTStatic<Integer>());

        System.out.println();
        System.out.println("=== BST Dinámico ===");
        run(new BSTADTDynamic<Integer>());
    }

    private static void run(BinaryTreeADT<Integer> bst) {
        System.out.println("isEmpty inicial: " + bst.isEmpty());

        int[] valores = {50, 30, 70, 20, 40, 60, 80};
        int idx = 0;
        while (idx < valores.length) {
            bst.add(valores[idx]);
            idx++;
        }
        System.out.println("Insertados: 50 30 70 20 40 60 80");
        System.out.println("isEmpty: " + bst.isEmpty());
        System.out.println("getRoot(): " + bst.getRoot());
        System.out.println("getLeft().getRoot(): " + bst.getLeft().getRoot());
        System.out.println("getRight().getRoot(): " + bst.getRight().getRoot());

        System.out.print("In-order (debe salir ordenado): ");
        inOrder(bst);
        System.out.println();

        bst.remove(20);
        System.out.print("Tras remove(20) (hoja). In-order: ");
        inOrder(bst);
        System.out.println();

        bst.add(25);
        bst.remove(25);
        System.out.print("Tras add(25) y remove(25). In-order: ");
        inOrder(bst);
        System.out.println();

        bst.remove(50);
        System.out.print("Tras remove(50) (raíz, dos hijos). In-order: ");
        inOrder(bst);
        System.out.println();
        System.out.println("Nueva raíz: " + bst.getRoot());

        bst.add(70);
        System.out.println("add(70) duplicado: no rompió.");

        bst.remove(999);
        System.out.println("remove(999) inexistente: no rompió.");
    }

    private static void inOrder(BinaryTreeADT<Integer> bst) {
        if (bst.isEmpty()) {
            return;
        }
        inOrder(bst.getLeft());
        System.out.print(bst.getRoot() + " ");
        inOrder(bst.getRight());
    }
}
