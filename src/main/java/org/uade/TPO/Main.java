package org.uade.TPO;

import org.uade.TPO.entidades.*;
import org.uade.structure.definition.LinkedListADT;
import org.uade.structure.exception.*;

public class Main {

    public static void main(String[] args) throws ExistingTerminalException, ExistingVehicleException, ExistingTripException, MissingVehicleException, ExistingRoadException, MissingRoadException {
        SistemaOperaciones sistema = new SistemaOperaciones();
        CargaInicial.cargar(sistema);
        GestionFlota flota = new GestionFlota(sistema);
        PriorityViajes prioridad = new PriorityViajes();
        AnalisisConexiones conexiones = new AnalisisConexiones(sistema);
        SimulacionReportes reportes = new SimulacionReportes(sistema);
    }

    private static void imprimirLista(LinkedListADT<?> lista) {
        int i = 0;
        while (i < lista.size()) {
            System.out.println("  " + lista.get(i));
            i++;
        }
    }
}