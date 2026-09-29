package com.poli.taller.servicio;

import java.io.*;
import java.util.*;

public class AnalizadorRepuestos {

    private final String rutaOrdenes = "data/ordenes.txt";

    public ResultadoAnalisis ejecutar() throws IOException {

        Map<Integer,Integer> rotacion = new HashMap<>();
        int ordenes = 0;
        int mayorUso = 0;
        String mayor = "Sin datos";

        try(BufferedReader br = new BufferedReader(new FileReader(rutaOrdenes))){
            br.readLine();
            String linea;

            while((linea = br.readLine()) != null){
                String[] datos = linea.split(";");

                if(datos.length == 4){
                    int idRepuesto = Integer.parseInt(datos[2]);
                    int cantidad = Integer.parseInt(datos[3]);

                    rotacion.merge(idRepuesto, cantidad, Integer::sum);
                    ordenes++;

                    if(rotacion.get(idRepuesto) > mayorUso){
                        mayorUso = rotacion.get(idRepuesto);
                        mayor = "ID Repuesto " + idRepuesto;
                    }
                }
            }
        }

        return new ResultadoAnalisis(rotacion, ordenes, mayor);
    }
}