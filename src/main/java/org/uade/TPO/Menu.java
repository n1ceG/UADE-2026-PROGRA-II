package org.uade.TPO;

import jdk.jshell.spi.ExecutionControl;
import org.uade.TPO.clases.Ruta;
import org.uade.TPO.clases.Terminal;
import org.uade.TPO.entidades.*;
import org.uade.structure.definition.LinkedListADT;
import org.uade.structure.exception.*;

import java.time.LocalDate;
import java.util.Scanner;

public class Menu {
    Scanner scanner;
    SistemaOperaciones sistema;
    GestionFlota flota;
    PriorityViajes prioridad;
    AnalisisConexiones conexiones;
    SimulacionReportes reportes;

    public Menu(SistemaOperaciones sistema, GestionFlota flota, PriorityViajes prioridad, AnalisisConexiones conexiones, SimulacionReportes reportes){
        this.sistema = sistema;
        this.flota = flota;
        this.prioridad = prioridad;
        this.conexiones = conexiones;
        this.reportes = reportes;
        scanner = new Scanner(System.in);
    }

    public void run(){
        boolean salir = false;
        while(!salir){
            System.out.println("");
            int opcion = leerOpcion();
            switch (opcion){
                case 1: try {
                    menuConexiones();
                } catch (RuntimeException e) {
                    System.out.println("Error: "+e.getMessage());
                }

                case 2: try{
                    menuFlota();
                }catch(RuntimeException e){
                    System.out.println("Error: "+e.getMessage());
                }
                case 3: try{
                    menuPlanificacion();
                }catch(RuntimeException | ExistingTerminalException | ExistingRoadException | MissingVehicleException |
                       ExistingTripException | MissingRoadException e){
                    System.out.println("Error: "+e.getMessage());
                }
                case 4: try{
                    menuPrioridad();
                }catch(RuntimeException e){
                    System.out.println("Error: "+e.getMessage());
                }
                case 5: try {
                    menuSimulacion();

                }catch(RuntimeException e){
                    System.out.println("Error: "+e.getMessage());
                }
                case 6: salir = true; System.out.println("Hasta luego!");
                // Printear con opcion inválida
            }
        }
    }

    private void menuSimulacion() {
    }

    private void menuPrioridad() {

    }

    private void menuPlanificacion() throws ExistingTerminalException, ExistingRoadException, MissingVehicleException, ExistingTripException, MissingRoadException {
        System.out.println("Opciones: \n1. Agregar terminal\n2. Agregar ruta\n3. Crear viaje\n4. Rutas posibles\n5. Terminales desconectadas\n6. Volver");
        int opcion= leerOpcion();
        if(opcion==1){
            String codigo = leerTexto("Ingrese el código de la terminal.");
            String descripcion = leerTexto("Ingrese la descripción para la terminal con código '"+codigo+"'.");
            sistema.agregarTerminal(new Terminal(codigo, descripcion));
        }else if(opcion==2){
            String codigoOrigen = leerTexto("Ingrese el código de la terminal de origen.");
            String codigoDestino = leerTexto("Ingrese el código de la terminal de destino.");
            Terminal origen = sistema.getTerminalPorCodigo(codigoOrigen);
            Terminal destino = sistema.getTerminalPorCodigo(codigoDestino);
            sistema.agregarRuta(new Ruta(origen, destino));
            System.out.println("Ruta "+origen+"-"+destino+" agregada.");
        }else if(opcion==3){
            String idViaje = leerTexto("Ingrese el ID del viaje.");
            String codigoOrigen = leerTexto("Ingrese el código de origen.");
            Terminal origen = sistema.getTerminalPorCodigo(codigoOrigen);
            String codigoDestino = leerTexto("Ingrese el código de destino.");
            Terminal destino = sistema.getTerminalPorCodigo(codigoDestino);
            Ruta ruta = new Ruta(origen, destino);
            String idMicro = leerTexto("Ingrese el ID del micro.");
            LocalDate fecha = leerFecha();
            System.out.println("Ingrese la prioridad del viaje.");
            int prioridadViaje = leerOpcion();
            sistema.crearViaje(idViaje, ruta, idMicro, fecha, prioridadViaje);
            System.out.println("Viaje creado: "+sistema.getViajes().get(idViaje).toString());
        }else if(opcion==4){
            try{
                String codigoOrigen = leerTexto("Ingrese el código de origen");
                String codigoDestino = leerTexto("Ingrese el código de destino");
                System.out.println("Máximo de paradas intermedias: ");
                int maxParadas = leerOpcion();
                LinkedListADT<LinkedListADT<Terminal>> caminos = sistema.rutasPosibles(codigoOrigen, codigoDestino, maxParadas);
                if (caminos.isEmpty()) System.out.println("No hay caminos " + codigoOrigen + "-" + codigoDestino);
                else {
                    int i = 0;
                    while (i < caminos.size()) {
                        LinkedListADT<Terminal> camino = caminos.get(i);
                        System.out.println("Camino " + (i + 1) + ": " + formatearCamino(camino));
                        i++;
                    }
                }
            }catch(RuntimeException e){System.out.println("Error: "+e.getMessage());}
        }else if(opcion==5){imprimirLista(sistema.terminalesDesconectadas());}
    }

    private String formatearCamino(LinkedListADT<Terminal> camino) {
        String texto = "";
        int j = 0;
        while (j < camino.size()) {
            texto = texto + camino.get(j).getCode();
            if (j < camino.size() - 1) {
                texto = texto + " -> ";
            }
            j++;
        }
        return texto;
    }

    private void menuFlota() {
    }

    private void menuConexiones() {

    }

    private int leerOpcion(){
        try{
            return Integer.parseInt(scanner.nextLine().trim());
        }catch(NumberFormatException exception){
            return -1;
        }
    }

    private String leerTexto(String mensaje){
        System.out.println(mensaje);
        return scanner.nextLine().trim();
    }

    private void imprimirLista(LinkedListADT lista){
        if(lista.isEmpty())System.out.println(" (Sin resultados)");
        else{
            int i = 0;
            while(i<lista.size()){
                System.out.println(" "+lista.get(i));
                i++;
            }
        }
    }

    private LocalDate leerFecha(){
        System.out.println("Fecha (AAAA-MM-DD)");
        return LocalDate.parse(scanner.nextLine().trim());
    }
}
