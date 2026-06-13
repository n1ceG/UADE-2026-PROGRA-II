package org.uade.TPO.clases;

import java.time.LocalDate;

public class Viaje {
    final String id;
    final Ruta ruta;
    private Micro micro;
    private LocalDate fecha;
    private int prioridad;

    public Viaje(String id, Ruta ruta, Micro micro, LocalDate fecha, int prioridad){
        this.id = id;
        this.ruta = ruta;
        this.micro = micro;
        this.fecha = fecha;
        this.prioridad = prioridad;
    }

    public String getId(){return id;}
    public Ruta getRuta() {return ruta;}
    public Micro getMicro(){return micro;}
    public LocalDate getFecha(){return fecha;}
    public int getPrioridad(){return prioridad;}

    public void setFecha(LocalDate nuevaFecha){this.fecha = nuevaFecha;}
    public void setPrioridad(int nuevaPrioridad){this.prioridad=nuevaPrioridad;}

    @Override
    public String toString(){return id + " | " + ruta.toString() + " | " + micro.toString() + " | " + fecha.toString() + " | Prioridad: " + prioridad + " |";}
}