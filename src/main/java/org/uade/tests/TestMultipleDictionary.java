package org.uade.tests;

import org.uade.structure.definition.MultipleDictionaryADT;
import org.uade.structure.definition.SetADT;
import org.uade.structure.exception.MissingValueException;
import org.uade.structure.implementation.dinamica.MultipleDictionaryADTDynamic;
import org.uade.structure.implementation.dinamica.SetADTDynamic;
import org.uade.structure.implementation.estatica.MultipleDictionaryADTStatic;

public class TestMultipleDictionary {

    public static void main(String[] args) {
        System.out.println("=== Diccionario Múltiple Estático ===");
        run(new MultipleDictionaryADTStatic<Integer, Integer>());

        System.out.println();
        System.out.println("=== Diccionario Múltiple Dinámico ===");
        run(new MultipleDictionaryADTDynamic<Integer, Integer>());
    }

    private static void run(MultipleDictionaryADT<Integer, Integer> dic) {
        System.out.println("isEmpty inicial: " + dic.isEmpty());

        dic.add(1, 10);
        dic.add(1, 20);
        dic.add(1, 30);
        dic.add(2, 200);
        dic.add(3, 5);
        dic.add(3, 6);
        System.out.println("Agregadas claves 1,2,3. isEmpty: " + dic.isEmpty());

        dic.add(1, 10);
        System.out.println("add(1,10) duplicado: no rompió.");

        System.out.print("get(1) = ");
        printArray(dic.get(1));
        System.out.print("get(2) = ");
        printArray(dic.get(2));
        System.out.print("get(3) = ");
        printArray(dic.get(3));

        System.out.print("getKeys() = ");
        printSet(dic.getKeys());

        dic.remove(1, 20);
        System.out.print("Tras remove(1,20). get(1) = ");
        printArray(dic.get(1));

        dic.remove(2, 200);
        try {
            dic.get(2);
            System.out.println("ERROR: clave 2 debió haberse eliminado al vaciarse");
        } catch (MissingValueException e) {
            System.out.println("OK: get(2) lanzó MissingValueException tras vaciar la clave");
        }

        dic.remove(3);
        try {
            dic.get(3);
            System.out.println("ERROR: clave 3 debió haberse eliminado");
        } catch (MissingValueException e) {
            System.out.println("OK: get(3) lanzó MissingValueException tras remove(3)");
        }

        System.out.print("Claves restantes: ");
        printSet(dic.getKeys());

        dic.remove(99);
        dic.remove(99, 7);
        System.out.println("remove(99) y remove(99,7) inexistentes: no rompieron.");
    }

    private static void printArray(Object[] arr) {
        StringBuilder sb = new StringBuilder("[");
        int i = 0;
        while (i < arr.length) {
            if (i > 0) {
                sb.append(", ");
            }
            sb.append(arr[i]);
            i++;
        }
        sb.append("]");
        System.out.println(sb.toString());
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
