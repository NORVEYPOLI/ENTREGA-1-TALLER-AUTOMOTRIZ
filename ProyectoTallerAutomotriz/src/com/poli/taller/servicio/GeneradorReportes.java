package com.poli.taller.servicio;

import java.io.*;

/**
 * Servicio encargado de generar reportes a partir de los resultados obtenidos
 * durante el análisis de rotación de repuestos del taller automotriz.
 *
 * Esta clase toma la información procesada por {@link ResultadoAnalisis}
 * y crea un archivo en formato CSV con la cantidad de veces que fue utilizado
 * cada repuesto, además de asignar un estado según su nivel de rotación.
 *
 * El reporte generado permite consultar cuáles repuestos presentan mayor
 * frecuencia de uso y facilita la toma de decisiones relacionadas con inventario.
 */

public class GeneradorReportes {


    /**
     * Genera un archivo de reporte con la información del análisis realizado.
     *
     * El método crea la carpeta de datos si no existe y genera el archivo
     * {@code inventario_recomendado.csv}, donde se almacena el identificador
     * del repuesto, la cantidad utilizada y el estado de rotación.
     *
     * @param resultado objeto que contiene la información procesada de rotación
     *                  de repuestos.
     *
     * @throws IOException si ocurre algún problema durante la creación o escritura
     *                     del archivo de reporte.
     */
    public void generar(ResultadoAnalisis resultado) throws IOException {

        File carpeta = new File("data");
        if(!carpeta.exists()) carpeta.mkdirs();

        try(BufferedWriter bw = new BufferedWriter(
                new FileWriter("data/inventario_recomendado.csv"))){

            bw.write("ID_REPUESTO;CANTIDAD_USADA;ESTADO");
            bw.newLine();

            resultado.getRotacion().forEach((id,cantidad)->{
                try{

                    String estado = cantidad >= 10 
                            ? "Alta rotacion" 
                            : "Rotacion normal";

                    bw.write(id + ";" + cantidad + ";" + estado);
                    bw.newLine();

                }catch(IOException e){
                    throw new RuntimeException(e);
                }
            });
        }
    }
}