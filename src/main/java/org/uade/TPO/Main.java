package org.uade.TPO;

import org.uade.TPO.entidades.*;

public class Main {

    public static void main(String[] args){
        SistemaOperaciones sistema = new SistemaOperaciones();
        CargaInicial.cargar(sistema);
        GestionFlota flota = new GestionFlota(sistema);
        PriorityViajes prioridad = new PriorityViajes();
        AnalisisConexiones conexiones = new AnalisisConexiones(sistema);
        SimulacionReportes reportes = new SimulacionReportes(sistema);
        Menu menu = new Menu(sistema, flota, prioridad, conexiones, reportes);
        menu.run();
    }
}