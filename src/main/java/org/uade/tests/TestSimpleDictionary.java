package org.uade.tests;

import org.uade.structure.definition.SetADT;
import org.uade.structure.definition.SimpleDictionaryADT;
import org.uade.structure.exception.MissingValueException;
import org.uade.structure.implementation.dinamica.SimpleDictionaryADTDynamic;
import org.uade.structure.implementation.estatica.SimpleDictionaryADTStatic;

public class TestSimpleDictionary {

    public static void main(String[] args) {
        System.out.println("=== Diccionario Simple Estático ===");
        run(new SimpleDictionaryADTStatic<Integer, Integer>());

        System.out.println();
        System.out.println("=== Diccionario Simple Dinámico ===");
        run(new SimpleDictionaryADTDynamic<Integer, Integer>());
    }

    private static void run(SimpleDictionaryADT<Integer, Integer> dic) {
        System.out.println("isEmpty inicial: " + dic.isEmpty());

        dic.add(1, 100);
        dic.add(2, 200);
        dic.add(3, 300);
        System.out.println("Agregué (1,100) (2,200) (3,300). isEmpty: " + dic.isEmpty());

        System.out.println("get(1) = " + dic.get(1));
        System.out.println("get(2) = " + dic.get(2));
        System.out.println("get(3) = " + dic.get(3));

        dic.add(2, 222);
        System.out.println("Tras pisar (2,222). get(2) = " + dic.get(2));

        SetADT<Integer> keys = dic.getKeys();
        System.out.print("getKeys() contiene: ");
        printSet(keys);

        dic.remove(1);
        System.out.println("remove(1). isEmpty: " + dic.isEmpty());
        try {
            dic.get(1);
            System.out.println("ERROR: debió lanzar MissingValueException");
        } catch (MissingValueException e) {
            System.out.println("OK: get(1) lanzó MissingValueException tras remove: " + e.getMessage());
        }

        dic.remove(99);
        System.out.println("remove(99) (inexistente): no rompió. isEmpty: " + dic.isEmpty());

        dic.remove(2);
        dic.remove(3);
        System.out.println("Tras borrar todo. isEmpty: " + dic.isEmpty());
    }

    private static void printSet(SetADT<Integer> s) {
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
        }
        sb.append("}");
        System.out.println(sb.toString());
    }
}
