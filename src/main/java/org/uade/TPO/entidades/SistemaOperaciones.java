package org.uade.TPO.entidades;

import org.uade.TPO.clases.Micro;
import org.uade.TPO.clases.Ruta;
import org.uade.TPO.clases.Terminal;
import org.uade.TPO.clases.Viaje;
import org.uade.structure.definition.GraphADT;
import org.uade.structure.definition.LinkedListADT;
import org.uade.structure.definition.SetADT;
import org.uade.structure.definition.SimpleDictionaryADT;
import org.uade.structure.exception.*;
import org.uade.structure.implementation.dinamica.LinkedListADTDynamic;
import org.uade.structure.implementation.dinamica.SetADTDynamic;
import org.uade.structure.implementation.dinamica.SimpleDictionaryADTDynamic;
import org.uade.structure.implementation.estatica.GraphADTStatic;

import java.time.LocalDate;

public class SistemaOperaciones {
    private GraphADT grafoTerminales;
    private SimpleDictionaryADT<String, Integer> codigoAIndice; // Code (BUE,COR,etc.) -> Index Grafo
    private SimpleDictionaryADT<Integer, Terminal> indiceATerminal; // Index Grafo -> Terminal
    private SimpleDictionaryADT<String, Viaje> viajes;   // String id -> Viaje
    private SimpleDictionaryADT<String, Micro> micros;   // String id -> Micro
    private SimpleDictionaryADT<String, Integer> usosPorRuta; // "BUE-COR" -> cantidad de viajes creados
    private int proximoIndice; // aux para gestionar traducciones

    public SistemaOperaciones(){
        this.grafoTerminales = new GraphADTStatic();
        this.codigoAIndice = new SimpleDictionaryADTDynamic<>();
        this.indiceATerminal = new SimpleDictionaryADTDynamic<>();
        this.proximoIndice = 0;
        this.viajes = new SimpleDictionaryADTDynamic<>();
        this.micros = new SimpleDictionaryADTDynamic<>();
        this.usosPorRuta = new SimpleDictionaryADTDynamic<>();
    }

    public Terminal getTerminalPorCodigo(String codigo) {
        if (!codigoAIndice.getKeys().exist(codigo)) {
            throw new MissingTerminalException("La terminal " + codigo + " no está registrada.");
        }
        return indiceATerminal.get(codigoAIndice.get(codigo));
    }
    public SimpleDictionaryADT<String, Micro> getMicros(){return this.micros;}
    public SimpleDictionaryADT<String, Viaje> getViajes(){return this.viajes;}
    public GraphADT getGrafoTerminales() {return grafoTerminales;}
    public int getCantidadTerminales(){return proximoIndice;}
    public Terminal getTerminalPorIndice(int i){return indiceATerminal.get(i);}
    public SimpleDictionaryADT<String, Integer> getUsosPorRuta(){ return usosPorRuta; }

    public void agregarTerminal(Terminal t){
        if(codigoAIndice.getKeys().exist((t.getCode()))) {throw new ExistingTerminalException("La terminal " + t.getCode() + " ya está dada de alta.");} // Si ya existe, no hace nada
        codigoAIndice.add(t.getCode(), proximoIndice);
        indiceATerminal.add(proximoIndice, t);
        grafoTerminales.addVertx(proximoIndice);
        proximoIndice++;
    }

    public void agregarRuta(Ruta r){
        String codigoOrigen = r.getStart().getCode();
        String codigoDestino = r.getEnd().getCode();
        if (!codigoAIndice.getKeys().exist(codigoOrigen)) {throw new MissingTerminalException("La terminal " + codigoOrigen + " no está registrada.");}
        if (!codigoAIndice.getKeys().exist(codigoDestino)) {throw new MissingTerminalException("La terminal " + codigoDestino + " no está registrada.");}
        int indiceOrigen = codigoAIndice.get(codigoOrigen);
        int indiceDestino = codigoAIndice.get(codigoDestino);
        if(grafoTerminales.existsEdge(indiceOrigen,indiceDestino)) throw new ExistingRoadException("La ruta "+codigoOrigen+" -> "+codigoDestino+" ya está registrada.");
        grafoTerminales.addEdge(indiceOrigen, indiceDestino, 1);
        usosPorRuta.add(codigoOrigen+"-"+codigoDestino, 0);
    }

    public void crearViaje(String idViaje, Ruta ruta, String idMicro, LocalDate fecha, int prioridad){
        if(viajes.getKeys().exist(idViaje)){throw new ExistingTripException("El viaje " + idViaje + " ya existe.");}
        if(!micros.getKeys().exist(idMicro)){throw new MissingVehicleException("El micro " + idMicro + " no existe.");}
        String codigoOrigen = ruta.getStart().getCode();
        String codigoDestino = ruta.getEnd().getCode();
        if (!codigoAIndice.getKeys().exist(codigoOrigen)) {throw new MissingTerminalException("La terminal " + codigoOrigen + " no está registrada.");}
        if (!codigoAIndice.getKeys().exist(codigoDestino)) {throw new MissingTerminalException("La terminal " + codigoDestino + " no está registrada.");}
        int indiceOrigen = codigoAIndice.get(codigoOrigen);
        int indiceDestino = codigoAIndice.get(codigoDestino);
        if(!grafoTerminales.existsEdge(indiceOrigen, indiceDestino)){throw new MissingRoadException("La ruta de " + codigoOrigen + " a " + codigoDestino + " no existe.");}
        Micro micro = micros.get(idMicro); // Obtener el micro desde el diccionario
        Viaje viaje = new Viaje(idViaje, ruta, micro, fecha, prioridad); // Crear el viaje como tal
        viajes.add(idViaje, viaje); // Guardar el viaje
        micro.asignarViaje(viaje); // Indicarle al propio micro que se le da este viaje
        String claveRuta = codigoOrigen+"-"+codigoDestino;
        usosPorRuta.add(claveRuta, usosPorRuta.get(claveRuta)+1);
    }

    public void agregarMicro(Micro m){
        if(micros.getKeys().exist(m.getId())) {throw new ExistingVehicleException("El micro ya está dado de alta.");}
        micros.add(m.getId(), m);
    }

    public LinkedListADT<LinkedListADT<Terminal>> rutasPosibles(String codigoOrigen, String codigoDestino, int maxParadas){
        if(!codigoAIndice.getKeys().exist(codigoOrigen)) throw new MissingTerminalException("La terminal "+codigoOrigen+" no existe.");
        if(!codigoAIndice.getKeys().exist(codigoDestino)) throw new MissingTerminalException("La terminal "+codigoDestino+" no existe.");

        LinkedListADT<Terminal> caminoActual = new LinkedListADTDynamic<>();
        SetADT<Integer> visitados = new SetADTDynamic<>();
        LinkedListADT<LinkedListADT<Terminal>> resultados = new LinkedListADTDynamic<>();

        int indiceOrigen = codigoAIndice.get(codigoOrigen);
        int indiceDestino = codigoAIndice.get(codigoDestino);

        dfsRecursivo(indiceOrigen, indiceDestino, maxParadas, caminoActual, visitados, resultados);
        return resultados;
    }

    private LinkedListADT<Terminal> copiarCamino(LinkedListADT<Terminal> original) {
        LinkedListADT<Terminal> copia = new LinkedListADTDynamic<>();
        int i = 0;
        while (i < original.size()) {
            copia.add(original.get(i));
            i = i + 1;
        }
        return copia;
    }

    private void dfsRecursivo(int actual, int destino, int maxParadas,
                              LinkedListADT<Terminal> caminoActual,
                              SetADT<Integer> visitados,
                              LinkedListADT<LinkedListADT<Terminal>> resultados) {

        caminoActual.add(indiceATerminal.get(actual));  // Añado la terminal actual al camino andado
        visitados.add(actual);                          // SET de visitados

        if (actual == destino) {                        // Caso base - destino encontrado
            resultados.add(copiarCamino(caminoActual));
        } else {
            // PASO 3 — Explorar vecinos
            int vecino = 0;
            while (vecino < proximoIndice) {
                if (grafoTerminales.existsEdge(actual, vecino)
                        && !visitados.exist(vecino)
                        && (vecino == destino || caminoActual.size() - 1 < maxParadas)) {
                    dfsRecursivo(vecino, destino, maxParadas, caminoActual, visitados, resultados);
                }
                vecino = vecino + 1;
            }
        }

        // PASO 4 — Desmarcar (backtracking): deshago el paso 1
        caminoActual.remove(caminoActual.size() - 1);
        visitados.remove(actual);
    }

    public LinkedListADT<Terminal> terminalesDesconectadas() {
        LinkedListADT<Terminal> resultado = new LinkedListADTDynamic<>();
        int i = 0;
        while (i < proximoIndice) {
            boolean tieneConexion = false;
            int j = 0;
            while (j < proximoIndice && !tieneConexion) {
                if (grafoTerminales.existsEdge(i, j) || grafoTerminales.existsEdge(j, i)) {
                    tieneConexion = true;
                }
                j = j + 1;
            }
            if (!tieneConexion) {
                resultado.add(indiceATerminal.get(i));
            }
            i = i + 1;
        }
        return resultado;
    }
}