package org.uade.TPO.entidades;

import org.uade.TPO.clases.Micro;
import org.uade.structure.definition.LinkedListADT;
import org.uade.structure.definition.SetADT;
import org.uade.structure.exception.MissingTripException;
import org.uade.structure.implementation.dinamica.LinkedListADTDynamic;

import java.time.LocalDate;

public class SimulacionReportes {

    SistemaOperaciones sistema;

    public SimulacionReportes(SistemaOperaciones sistema){
        this.sistema = sistema;
    }

    public void reprogramarViaje(String id, LocalDate fecha){
        if(!sistema.getViajes().getKeys().exist(id)){throw new MissingTripException("El viaje "+id+" no está registrado.");}
        sistema.getViajes().get(id).setFecha(fecha);
    }

    public LinkedListADT<Micro> utilizacionPorMicro(){
        SetADT<String> claves = sistema.getMicros().getKeys();
        LinkedListADT<Micro> resultado = new LinkedListADTDynamic<Micro>();
        while(!claves.isEmpty()){
            String clave = claves.choose();
            resultado.add(sistema.getMicros().get(clave));
            claves.remove(clave);
        }
        return resultado;
    }

    public double utilizacionPromedio(){
        // Contar viajes
        SetADT<String> clavesViajes = sistema.getViajes().getKeys();
        int totalViajes = 0;
        while(!clavesViajes.isEmpty()){
            String clave = clavesViajes.choose();
            totalViajes++;
            clavesViajes.remove(clave);
        }


        // Contar micros
        SetADT<String> clavesMicros = sistema.getMicros().getKeys();
        int totalMicros = 0;
        while(!clavesMicros.isEmpty()){
            String clave = clavesMicros.choose();
            totalMicros++;
            clavesMicros.remove(clave);
        }


        if(totalMicros==0){return 0;}
        return (double) totalViajes/totalMicros;
    }

    public LinkedListADT<String> rutasMasUtilizadas(){
        SetADT<String> clavesUsos = sistema.getUsosPorRuta().getKeys();
        LinkedListADT<String> resultado = new LinkedListADTDynamic<String>();
        int max = -1;
        while(!clavesUsos.isEmpty()){
            String clave = clavesUsos.choose();
            int usos = sistema.getUsosPorRuta().get(clave);
            if(usos>max){
                resultado = new LinkedListADTDynamic<String>();
                resultado.add(clave);
                max = usos;
            }else if(usos == max){resultado.add(clave);}
            clavesUsos.remove(clave);
        }
        return resultado;
    }

    public LinkedListADT<String> rutasMenosUtilizadas(){
        SetADT<String> clavesUsos = sistema.getUsosPorRuta().getKeys();
        LinkedListADT<String> resultado = new LinkedListADTDynamic<String>();
        int min = Integer.MAX_VALUE;
        while(!clavesUsos.isEmpty()){
            String clave = clavesUsos.choose();
            int usos = sistema.getUsosPorRuta().get(clave);
            if(usos<min){
                resultado = new LinkedListADTDynamic<String>();
                resultado.add(clave);
                min = usos;
            }else if(usos == min){resultado.add(clave);}
            clavesUsos.remove(clave);
        }
        return resultado;
    }
}