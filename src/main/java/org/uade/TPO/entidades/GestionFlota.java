package org.uade.TPO.entidades;

import org.uade.TPO.clases.Micro;
import org.uade.structure.definition.LinkedListADT;
import org.uade.structure.definition.SetADT;
import org.uade.structure.exception.MissingVehicleException;
import org.uade.structure.implementation.dinamica.LinkedListADTDynamic;

public class GestionFlota {

    private SistemaOperaciones sistema;

    public GestionFlota(SistemaOperaciones sistema) {this.sistema = sistema;}

    public LinkedListADT<Micro> microsMasAsignados(){
        SetADT<String> microsClaves = sistema.getMicros().getKeys();
        LinkedListADT<Micro> resultado = new LinkedListADTDynamic<Micro>();
        int max = -1;
        while(!microsClaves.isEmpty()){
            String clave = microsClaves.choose();
            Micro micro = sistema.getMicros().get(clave);
            int cantidadAsignaciones = micro.cantidadAsignaciones();

            if(cantidadAsignaciones > max){
                resultado = new LinkedListADTDynamic<Micro>();
                resultado.add(micro);
                max = cantidadAsignaciones;
            }
            else if (cantidadAsignaciones==max){resultado.add(micro);}

            microsClaves.remove(clave);
        }
        return resultado;
    }

    public boolean consultarDisponibilidad(String id) throws MissingVehicleException {
        if(!sistema.getMicros().getKeys().exist(id)) throw new MissingVehicleException("El micro "+id+" no está registrado.");
        else return sistema.getMicros().get(id).isAvailable();
    }

    public void actualizarDisponibilidad(String id, boolean disponible) throws MissingVehicleException {
        if(!sistema.getMicros().getKeys().exist(id)) throw new MissingVehicleException("El micro "+id+" no está registrado.");
        else sistema.getMicros().get(id).setDisponible(disponible);
    }
}