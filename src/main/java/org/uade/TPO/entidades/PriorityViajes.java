package org.uade.TPO.entidades;

import org.uade.TPO.clases.Viaje;
import org.uade.structure.definition.LinkedListADT;
import org.uade.structure.definition.PriorityQueueADT;
import org.uade.structure.exception.MissingTripException;
import org.uade.structure.implementation.dinamica.LinkedListADTDynamic;
import org.uade.structure.implementation.dinamica.PriorityQueueADTDynamic;

public class PriorityViajes {

    PriorityQueueADT<Viaje> cola;

    public PriorityViajes(){
        this.cola = new PriorityQueueADTDynamic<>();
    }

    public void agregarViajeConPrioridad(Viaje v, int p){
        cola.add(v, p);
        v.setPrioridad(p);
    }

    public Viaje verProximoViaje(){
        return cola.getElement();
    }

    public Viaje atenderProximoViaje(){
        Viaje viaje = cola.getElement();
        cola.remove();
        return viaje;
    }

    public void modificarPrioridad(String idViaje, int nuevaPrioridad) throws MissingTripException {
        LinkedListADT<Viaje> viajes = new LinkedListADTDynamic<>();
        LinkedListADT<Integer> prioridades = new LinkedListADTDynamic<>();
        boolean encontrado = false;
        while(!cola.isEmpty()){
            Viaje v = cola.getElement();
            int p = cola.getPriority();
            if(v.getId().equals(idViaje)){
                encontrado = true;
                p = nuevaPrioridad;
                v.setPrioridad(nuevaPrioridad);
            }
            viajes.add(v);
            prioridades.add(p);
            cola.remove();
        }
        int i = 0;
        while(i<viajes.size()){
            cola.add(viajes.get(i), prioridades.get(i));
            i++;
        }
        if(!encontrado) throw new MissingTripException("El viaje "+idViaje+" no está registrado.");
    }
}