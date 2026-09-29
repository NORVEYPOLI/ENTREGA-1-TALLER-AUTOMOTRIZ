package com.poli.taller.servicio;

import java.io.*;
import java.util.*;

/**
 * Servicio encargado de analizar la información de los repuestos utilizados
 * en las órdenes generadas por el taller automotriz.
 *
 * Esta clase realiza la lectura del archivo de órdenes, identifica los repuestos
 * utilizados en cada servicio y calcula la frecuencia de uso de cada uno,
 * permitiendo determinar cuál repuesto presenta mayor rotación dentro del sistema.
 *
 * El resultado del procesamiento se almacena en un objeto {@link ResultadoAnalisis},
 * el cual contiene la información obtenida durante el análisis.
 */
public class AnalizadorRepuestos {

    /**
     * Ruta del archivo donde se encuentran registradas
     * las órdenes del taller.
     */
    private final String rutaOrdenes = "data/ordenes.txt";


    /**
     * Ejecuta el análisis de rotación de repuestos.
     *
     * Este método lee el archivo de órdenes, procesa la información registrada,
     * suma la cantidad utilizada de cada repuesto y determina el repuesto
     * con mayor frecuencia de uso.
     *
     * @return objeto {@link ResultadoAnalisis} con los resultados obtenidos
     * del procesamiento.
     *
     * @throws IOException si ocurre algún problema durante la lectura
     * del archivo de órdenes.
     */
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