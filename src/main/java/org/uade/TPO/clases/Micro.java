package org.uade.TPO.clases;

import org.uade.structure.definition.LinkedListADT;
import org.uade.structure.implementation.dinamica.LinkedListADTDynamic;

public class Micro {
    final String id;
    private TipoMicro tipo;
    private boolean disponible;
    LinkedListADT<Viaje> viajesAsignados;

    public Micro(String id, TipoMicro tipo) {
        this.id = id;
        this.tipo = tipo;
        this.disponible = true;
        this.viajesAsignados = new LinkedListADTDynamic<>();
    }

    public String getId(){return id;}
    public boolean isAvailable(){return disponible;}

    public void setDisponible(boolean bool){this.disponible = bool;}
    public void asignarViaje(Viaje V){viajesAsignados.add(V);}

    public int cantidadAsignaciones(){return viajesAsignados.size();}

    @Override
    public boolean equals(Object obj){
        if(this==obj) return true;
        if(obj==null||getClass()!=obj.getClass()) return false;
        Micro other = (Micro) obj;
        return this.id.equals(other.id);
    }

    @Override
    public String toString(){return id + "; " + tipo + "; Disponible: " + disponible;}

    @Override
    public int hashCode() {return id.hashCode();}
}
