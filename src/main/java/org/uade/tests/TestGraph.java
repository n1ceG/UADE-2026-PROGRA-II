package org.uade.tests;

import org.uade.structure.definition.SetADT;
import org.uade.structure.exception.InvalidVertexException;
import org.uade.structure.exception.MissingValueException;
import org.uade.structure.implementation.dinamica.GraphADTDynamic;
import org.uade.structure.implementation.dinamica.SetADTDynamic;
import org.uade.structure.implementation.estatica.GraphADTStatic;

public class TestGraph {

    public static void main(String[] args) {
        System.out.println("=== Grafo Estático (matriz de adyacencia) ===");
        runStatic(new GraphADTStatic());

        System.out.println();
        System.out.println("=== Grafo Dinámico (listas de adyacencia) ===");
        runDynamic(new GraphADTDynamic());
    }

    private static void runStatic(GraphADTStatic g) {
        System.out.println("isEmpty inicial: " + g.isEmpty());

        g.addVertx(0);
        g.addVertx(1);
        g.addVertx(2);
        g.addVertx(3);
        System.out.println("Agregados vértices 0,1,2,3. isEmpty: " + g.isEmpty());

        g.addVertx(2);
        System.out.println("addVertx(2) duplicado: no rompió.");

        g.addEdge(0, 1, 10);
        g.addEdge(0, 2, 20);
        g.addEdge(1, 2, 5);
        g.addEdge(2, 3, 15);
        g.addEdge(3, 0, 1);

        System.out.print("Vértices: ");
        printSet(g.getVertxs());

        System.out.println("existsEdge(0,1): " + g.existsEdge(0, 1));
        System.out.println("existsEdge(1,0): " + g.existsEdge(1, 0) + " (dígrafo: no debe existir)");
        System.out.println("edgeWeight(0,2): " + g.edgeWeight(0, 2));
        System.out.println("edgeWeight(3,0): " + g.edgeWeight(3, 0));

        g.addEdge(0, 1, 99);
        System.out.println("Tras addEdge(0,1,99). edgeWeight(0,1): " + g.edgeWeight(0, 1));

        g.removeEdge(0, 1);
        System.out.println("Tras removeEdge(0,1). existsEdge(0,1): " + g.existsEdge(0, 1));
        try {
            g.edgeWeight(0, 1);
            System.out.println("ERROR: debió lanzar MissingValueException");
        } catch (MissingValueException e) {
            System.out.println("OK: edgeWeight(0,1) lanzó MissingValueException");
        }

        g.removeVertx(0);
        System.out.print("Tras removeVertx(0). Vértices: ");
        printSet(g.getVertxs());
        try {
            g.existsEdge(0, 2);
            System.out.println("ERROR: 0 ya no existe");
        } catch (InvalidVertexException e) {
            System.out.println("OK: existsEdge(0,2) lanzó InvalidVertexException");
        }

        try {
            g.addEdge(99, 1, 7);
            System.out.println("ERROR: vértice 99 no existe");
        } catch (InvalidVertexException e) {
            System.out.println("OK: addEdge(99,1,7) lanzó InvalidVertexException");
        }

        g.removeVertx(123);
        System.out.println("removeVertx(123) inexistente: no rompió.");
    }

    private static void runDynamic(GraphADTDynamic g) {
        System.out.println("isEmpty inicial: " + g.isEmpty());

        g.addVertx(0);
        g.addVertx(1);
        g.addVertx(2);
        g.addVertx(3);
        System.out.println("Agregados vértices 0,1,2,3. isEmpty: " + g.isEmpty());

        g.addVertx(2);
        System.out.println("addVertx(2) duplicado: no rompió.");

        g.addEdge(0, 1, 10);
        g.addEdge(0, 2, 20);
        g.addEdge(1, 2, 5);
        g.addEdge(2, 3, 15);
        g.addEdge(3, 0, 1);

        System.out.print("Vértices: ");
        printSet(g.getVertxs());

        System.out.println("existsEdge(0,1): " + g.existsEdge(0, 1));
        System.out.println("existsEdge(1,0): " + g.existsEdge(1, 0) + " (dígrafo: no debe existir)");
        System.out.println("edgeWeight(0,2): " + g.edgeWeight(0, 2));

        g.addEdge(0, 1, 99);
        System.out.println("Tras addEdge(0,1,99). edgeWeight(0,1): " + g.edgeWeight(0, 1));

        g.removeEdge(0, 1);
        System.out.println("Tras removeEdge(0,1). existsEdge(0,1): " + g.existsEdge(0, 1));

        g.removeVertx(0);
        System.out.print("Tras removeVertx(0). Vértices: ");
        printSet(g.getVertxs());

        try {
            g.addEdge(99, 1, 7);
            System.out.println("ERROR: vértice 99 no existe");
        } catch (InvalidVertexException e) {
            System.out.println("OK: addEdge(99,1,7) lanzó InvalidVertexException");
        }

        g.removeVertx(123);
        System.out.println("removeVertx(123) inexistente: no rompió.");
    }

    private static void printSet(SetADT<Integer> s) {
        SetADT<Integer> copia = new SetADTDynamic<>();
        StringBuilder sb = new StringBuilder("{");
        boolean first = true;
        while (!s.isEmpty()) {
            Integer v = s.choose();
            if (!first) {
                sb.append(", ");
            }
            sb.append(v);
            first = false;
            s.remove(v);
            copia.add(v);
        }
        sb.append("}");
        System.out.println(sb.toString());
        while (!copia.isEmpty()) {
            Integer v = copia.choose();
            copia.remove(v);
            s.add(v);
        }
    }
}
