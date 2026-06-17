package org.uade.TPO;

import org.uade.TPO.clases.Micro;
import org.uade.TPO.clases.Ruta;
import org.uade.TPO.clases.Terminal;
import org.uade.TPO.clases.TipoMicro;
import org.uade.TPO.entidades.*;
import org.uade.structure.definition.LinkedListADT;

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
            System.out.println("Ingrese una opción\n1. Menú de conexiones\n2. Menú de flota\n3. Menú de planificación\n4. Menú de prioridad\n5. Menú de simulación\n6. Cerrar");
            int opcion = leerOpcion();
            try{
                if (opcion == 1) menuConexiones();
                else if (opcion == 2) menuFlota();
                else if (opcion == 3) menuPlanificacion();
                else if (opcion == 4) menuPrioridad();
                else if (opcion == 5) menuSimulacion();
                else if (opcion == 6) {
                    salir = true;
                    System.out.println("Hasta luego!");
                }
                else{System.out.println("Opción inválida.");}
            }catch(RuntimeException e){
                System.out.println("ERROR: "+e.getMessage());
            }
        }
    }

    private void menuSimulacion() {
        boolean volver = false;
        while(!volver){
            System.out.println("Menú de simulación\n1. Reprogramar viaje\n2. Utilización por micro\n3. Rutas más y menos utilizadas\n4. Volver");
            int opcion = leerOpcion();
            if(opcion == 1){
                // Reprogramar viaje
                String id = leerTexto("Ingrese el ID del viaje");
                LocalDate fecha = leerFecha();
                reportes.reprogramarViaje(id, fecha);
                System.out.println("Viaje '"+id+"' reprogramado para "+fecha);
            }else if (opcion == 2){
                // Utilización por micro + promedio
                LinkedListADT<Micro> micros = reportes.utilizacionPorMicro();
                int i = 0;
                while(i<micros.size()){
                    Micro m = micros.get(i);
                    System.out.println(m.getId()+" -> "+m.cantidadAsignaciones()+" viaje(s)");
                    i++;
                }
                System.out.println("Promedio: "+reportes.utilizacionPromedio());
            }else if (opcion == 3){
                // Rutas más y menos utilizadas
                System.out.println("Más utilizadas:");
                imprimirLista(reportes.rutasMasUtilizadas());
                System.out.println("Menos utilizadas:");
                imprimirLista(reportes.rutasMenosUtilizadas());
            }else if (opcion == 4){
                volver = true;
            }else{
                System.out.println("Opción inválida");
            }
        }
    }

    private void menuPrioridad() {
        boolean volver = false;
        while(!volver){
            System.out.println("Menú de prioridad\n1. Encolar viaje con prioridad\n2. Ver próximo viaje\n3. Atender próximo viaje\n4. Modificar prioridad\n5. Volver");
            int opcion = leerOpcion();
            if(opcion==1){
                // Encolar viaje con prioridad
                String id = leerTexto("ID del viaje");
                if(!sistema.getViajes().getKeys().exist(id)) System.out.println("ID no existe");
                else{
                    System.out.println("Ingrese la prioridad");
                    int prio = leerOpcion();

                    prioridad.agregarViajeConPrioridad(sistema.getViajes().get(id), prio);
                    System.out.println("Viaje '"+id+"' encolado con prioridad "+prio);
                }
            }
            else if(opcion==2){
                // Ver próximo viaje
                System.out.println("Próximo viaje: "+prioridad.verProximoViaje());
            }
            else if(opcion==3){
                // Atender próximo viaje
                System.out.println("Atendiendo viaje: "+prioridad.atenderProximoViaje());
            }
            else if(opcion==4){
                // Modificar prioridad
                String id = leerTexto("Ingrese el ID del viaje");
                System.out.println("Ingrese la nueva prioridad");
                int prio = leerOpcion();
                prioridad.modificarPrioridad(id,prio);
                System.out.println("Prioridad de '"+id+"' modificado con prioridad "+prio);
            }
            else if(opcion==5){
                volver = true;
            }
            else {
                System.out.println("Opción inválida");
            }
        }
    }

    private void menuPlanificacion(){
        boolean volver = false;
        while(!volver) {
            System.out.println("Menú de planificación \n1. Agregar terminal\n2. Agregar ruta\n3. Crear viaje\n4. Rutas posibles\n5. Terminales desconectadas\n6. Volver");
            int opcion = leerOpcion();
            if (opcion == 1) {
                String codigo = leerTexto("Ingrese el código de la terminal.");
                String descripcion = leerTexto("Ingrese la descripción para la terminal con código '" + codigo + "'.");
                sistema.agregarTerminal(new Terminal(codigo, descripcion));
                System.out.println("Terminal "+codigo+"; "+descripcion+" registrada.");
            } else if (opcion == 2) {
                String codigoOrigen = leerTexto("Ingrese el código de la terminal de origen.");
                String codigoDestino = leerTexto("Ingrese el código de la terminal de destino.");
                Terminal origen = sistema.getTerminalPorCodigo(codigoOrigen);
                Terminal destino = sistema.getTerminalPorCodigo(codigoDestino);
                sistema.agregarRuta(new Ruta(origen, destino));
                System.out.println("Ruta " + origen + "-" + destino + " agregada.");
            } else if (opcion == 3) {
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
                System.out.println("Viaje creado: " + sistema.getViajes().get(idViaje).toString());
            } else if (opcion == 4) {
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
            } else if (opcion == 5) {
                imprimirLista(sistema.terminalesDesconectadas());
            } else if (opcion == 6) {
                volver = true;
            } else  {
                System.out.println("Opción inválida.");
            }
        }
    }

    private void menuConexiones() {
        boolean volver = false;
        while(!volver) {
            System.out.println("Menú de conexiones\n1. Listar terminales\n2. Terminales con más salidas\n3. Terminales con más entradas\n4. Terminales con más conexiones directas\n5. Volver");
            int opcion = leerOpcion();
            if (opcion == 1) { imprimirLista(conexiones.listarTerminales()); }
            else if (opcion == 2) { imprimirLista(conexiones.terminalesConMasSalidas()); }
            else if (opcion == 3) { imprimirLista(conexiones.terminalesConMasLlegadas()); }
            else if (opcion == 4) { imprimirLista(conexiones.terminalesConMasConexionesDirectas()); }
            else if (opcion == 5) { volver = true; }
            else {System.out.println("Opción inválida");}
        }
    }

    private void menuFlota() {
        boolean volver = false;
        while(!volver) {
            System.out.println("Menú de flota\n1. Agregar micro\n2. Micros con más asignaciones\n3. Consultar disponibilidad\n4. Actualizar disponibilidad\n5. Volver");
            int opcion =  leerOpcion();
            if(opcion == 1){
                // Agregar micro
                String id = leerTexto("Ingrese el ID del micro");
                System.out.println("Tipo de micro\n1. EJECUTIVO\n2. CAMA\n3. SEMICAMA");
                int t = leerOpcion(); // Tipo de micro
                TipoMicro tipo = null;
                if(t==1) tipo = TipoMicro.EJECUTIVO;
                else if(t==2) tipo = TipoMicro.CAMA;
                else if(t==3) tipo = TipoMicro.SEMICAMA;
                if(tipo==null){
                    System.out.println("Tipo de micro inválido. Micro no registrado");
                }
                else{
                    sistema.agregarMicro(new Micro(id, tipo));
                    System.out.println("Micro registrado");
                }
            }
            else if (opcion == 2){
                // Micros con más asignaciones
                imprimirLista(flota.microsMasAsignados());
            }
            else if (opcion == 3){
                // Consultar disponibilidad
                String id  = leerTexto("Ingrese el ID del micro");
                boolean d=flota.consultarDisponibilidad(id);
                if(d){ System.out.println("Micro '"+id+"': Disponible"); }
                else{ System.out.println("Micro '"+id+"': No disponible"); }
            }
            else if (opcion == 4){
                // Actualizar disponibilidad
                String id =  leerTexto("Ingrese el ID del micro");
                int i = 0;
                while(i<1 || i>2) {
                    System.out.println("Ingrese una opción\n1. Disponible\n2. No disponible");
                    i = leerOpcion();
                }
                flota.actualizarDisponibilidad(id, i==1);
                System.out.println("Disponibilidad de "+id+" actualizada");
            }
            else if (opcion == 5){
                volver = true;
            }
            else {
                System.out.println("Opción inválida");
            }
        }
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

    private void imprimirLista(LinkedListADT<?> lista){
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
