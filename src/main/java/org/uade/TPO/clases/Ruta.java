package org.uade.TPO.clases;

public class Ruta {
    final Terminal start;
    final Terminal end;

    public Ruta(Terminal start, Terminal end) {
        this.start = start;
        this.end = end;
    }

    public Terminal getStart() {return start;}
    public Terminal getEnd() {return end;}

    @Override
    public boolean equals(Object obj){
        if(this==obj) return true;
        if(obj == null || getClass() != obj.getClass()) return false;
        Ruta other = (Ruta) obj;
        return this.start.equals(other.start) && this.end.equals(other.end);
    }

    @Override
    public String toString(){
        return start + "--->" + end;
    }

    @Override
    public int hashCode() {return start.hashCode() + end.hashCode();}
}
