package org.uade.TPO.entidades;

import org.uade.TPO.clases.Terminal;
import org.uade.structure.definition.GraphADT;
import org.uade.structure.definition.LinkedListADT;
import org.uade.structure.implementation.dinamica.LinkedListADTDynamic;

public class AnalisisConexiones {
    SistemaOperaciones sistema;

    public AnalisisConexiones(SistemaOperaciones sistema){
        this.sistema = sistema;
    }

    public LinkedListADT<Terminal> listarTerminales(){
        LinkedListADT<Terminal> resultado = new LinkedListADTDynamic<Terminal>();
        int cantidad = sistema.getCantidadTerminales();
        int i = 0;
        while(i<cantidad){
            resultado.add(sistema.getTerminalPorIndice(i));
            i++;
        }
        return resultado;
    }

    public LinkedListADT<Terminal> terminalesConMasSalidas(){
        LinkedListADT<Terminal> resultado = new LinkedListADTDynamic<Terminal>();
        int max = -1;
        GraphADT grafoTerminales = sistema.getGrafoTerminales();
        int cantidad = sistema.getCantidadTerminales();
        int i= 0;
        while(i<cantidad){
            int contador = 0;
            int j=0;
            while(j<cantidad){
                if(grafoTerminales.existsEdge(i,j)) contador++;
                j++;
            }
            if(contador>max){
                resultado = new LinkedListADTDynamic<>();
                resultado.add(sistema.getTerminalPorIndice(i));
                max = contador;
            }else if(contador == max) resultado.add(sistema.getTerminalPorIndice(i));
            i++;
        }
        return resultado;
    }

    public LinkedListADT<Terminal> terminalesConMasLlegadas(){
        LinkedListADT<Terminal> resultado = new LinkedListADTDynamic<>();
        int max = -1;
        GraphADT grafoTerminales = sistema.getGrafoTerminales();
        int cantidad = sistema.getCantidadTerminales();
        int i = 0;
        while(i < cantidad){
            int contador = 0;
            int j = 0;
            while(j < cantidad){
                if(grafoTerminales.existsEdge(j, i)) contador++;
                j++;
            }
            if(contador > max){
                resultado = new LinkedListADTDynamic<>();
                resultado.add(sistema.getTerminalPorIndice(i));
                max = contador;
            } else if(contador == max) resultado.add(sistema.getTerminalPorIndice(i));
            i++;
        }
        return resultado;
    }

    public LinkedListADT<Terminal> terminalesConMasConexionesDirectas(){
        LinkedListADT<Terminal> resultado = new LinkedListADTDynamic<>();
        int max = -1;
        GraphADT grafoTerminales = sistema.getGrafoTerminales();
        int cantidad = sistema.getCantidadTerminales();
        int i = 0;
        while(i < cantidad){
            int contador = 0;
            int j = 0;
            while(j < cantidad){
                if(grafoTerminales.existsEdge(i, j)) contador++;
                if(grafoTerminales.existsEdge(j, i)) contador++;
                j++;
            }
            if(contador > max){
                resultado = new LinkedListADTDynamic<>();
                resultado.add(sistema.getTerminalPorIndice(i));
                max = contador;
            } else if(contador == max) resultado.add(sistema.getTerminalPorIndice(i));
            i++;
        }
        return resultado;
    }

}