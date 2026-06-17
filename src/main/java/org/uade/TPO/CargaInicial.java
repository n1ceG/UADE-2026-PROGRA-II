package org.uade.TPO;

import org.uade.TPO.clases.Micro;
import org.uade.TPO.clases.Ruta;
import org.uade.TPO.clases.Terminal;
import org.uade.TPO.clases.TipoMicro;
import org.uade.TPO.entidades.SistemaOperaciones;

import java.time.LocalDate;

public class CargaInicial {

    public static void cargar(SistemaOperaciones sistema){
        Terminal bue = new Terminal("BUE", "Buenos Aires");
        Terminal cor = new Terminal("COR", "Cordoba");
        Terminal ros = new Terminal("ROS", "Rosario");
        Terminal mdz = new Terminal("MDZ", "Mendoza");
        Terminal sla = new Terminal("SLA", "Salta");
        Terminal tuc = new Terminal("TUC", "Tucuman");
        Terminal sfe = new Terminal("SFE", "Santa Fe");
        Terminal nqn = new Terminal("NQN", "Neuquen");
        Terminal brc = new Terminal("BRC", "Bariloche");
        Terminal pos = new Terminal("POS", "Posadas");
        sistema.agregarTerminal(bue);
        sistema.agregarTerminal(cor);
        sistema.agregarTerminal(ros);
        sistema.agregarTerminal(mdz);
        sistema.agregarTerminal(sla);
        sistema.agregarTerminal(tuc);
        sistema.agregarTerminal(sfe);
        sistema.agregarTerminal(nqn);
        sistema.agregarTerminal(brc);
        sistema.agregarTerminal(pos);

        sistema.agregarRuta(new Ruta(bue, ros));
        sistema.agregarRuta(new Ruta(bue, cor));
        sistema.agregarRuta(new Ruta(bue, mdz));
        sistema.agregarRuta(new Ruta(ros, cor));
        sistema.agregarRuta(new Ruta(ros, sfe));
        sistema.agregarRuta(new Ruta(sfe, cor));
        sistema.agregarRuta(new Ruta(mdz, brc));
        sistema.agregarRuta(new Ruta(cor, sfe));
        sistema.agregarRuta(new Ruta(cor, mdz));
        sistema.agregarRuta(new Ruta(cor, tuc));
        sistema.agregarRuta(new Ruta(tuc, sla));
        sistema.agregarRuta(new Ruta(sla, tuc));
        sistema.agregarRuta(new Ruta(mdz, nqn));
        sistema.agregarRuta(new Ruta(nqn, brc));

        int i = 1;
        String id = "";
        while(i<=15){
            if(i<10) {id = "M0"+i;}
            else {id = "M"+i;}
            TipoMicro tipo = TipoMicro.values()[(i-1)%3];
            sistema.agregarMicro(new Micro(id, tipo));
            i++;
        }

        sistema.crearViaje("V01", new Ruta(bue,cor), "M01", LocalDate.of(2026, 6, 20), 1);
        sistema.crearViaje("V02", new Ruta(bue,cor), "M02", LocalDate.of(2026, 6, 1), 1);
        sistema.crearViaje("V03", new Ruta(bue,cor), "M03", LocalDate.of(2026, 6, 5), 4);
        sistema.crearViaje("V04", new Ruta(bue,cor), "M04", LocalDate.of(2026, 6, 24), 3);
        sistema.crearViaje("V05", new Ruta(bue,ros), "M05", LocalDate.of(2026, 6, 30), 2);
        sistema.crearViaje("V06", new Ruta(bue,ros), "M06", LocalDate.of(2026, 7, 17), 2);
        sistema.crearViaje("V07", new Ruta(ros,cor), "M07", LocalDate.of(2026, 7, 2), 3);
        sistema.crearViaje("V08", new Ruta(ros,cor), "M08", LocalDate.of(2026, 7, 9), 4);
        sistema.crearViaje("V09", new Ruta(cor,mdz), "M09", LocalDate.of(2026, 7, 10), 5);
        sistema.crearViaje("V10", new Ruta(cor,mdz), "M10", LocalDate.of(2026, 7, 5), 2);
        sistema.crearViaje("V11", new Ruta(cor,tuc), "M11", LocalDate.of(2026, 7, 3), 3);
        sistema.crearViaje("V12", new Ruta(cor,tuc), "M12", LocalDate.of(2026, 7, 5), 5);
        sistema.crearViaje("V13", new Ruta(bue,mdz), "M13", LocalDate.of(2026, 7, 17), 2);
        sistema.crearViaje("V14", new Ruta(bue,mdz), "M14", LocalDate.of(2026, 8, 25), 1);
        sistema.crearViaje("V15", new Ruta(mdz,nqn), "M15", LocalDate.of(2026, 8, 21), 2);
        sistema.crearViaje("V16", new Ruta(nqn,brc), "M01", LocalDate.of(2026, 8, 19), 3);
        sistema.crearViaje("V17", new Ruta(tuc,sla), "M01", LocalDate.of(2026, 8, 2), 5);
        sistema.crearViaje("V18", new Ruta(ros,sfe), "M02", LocalDate.of(2026, 8, 6), 4);
        sistema.crearViaje("V19", new Ruta(sfe,cor), "M02", LocalDate.of(2026, 9, 2), 2);
        sistema.crearViaje("V20", new Ruta(mdz,brc), "M03", LocalDate.of(2026, 9, 10), 4);
    }
}
