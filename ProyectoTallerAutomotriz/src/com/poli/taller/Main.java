package com.poli.taller;

import com.poli.taller.servicio.AnalizadorRepuestos;
import com.poli.taller.servicio.GeneradorReportes;
import com.poli.taller.servicio.ResultadoAnalisis;

/**
 * Clase principal del sistema de análisis del taller automotriz.
 *
 * Esta clase representa el punto de inicio de ejecución del programa,
 * encargándose de coordinar el procesamiento entre los diferentes servicios
 * del sistema.
 *
 * El flujo principal consiste en ejecutar el análisis de repuestos,
 * obtener los resultados del procesamiento y generar el reporte final
 * con la información de rotación de inventario.
 *
 */

public class Main {


    /**
     * Método principal de ejecución del sistema.
     *
     * Inicializa el servicio de análisis de repuestos, procesa la información
     * registrada en los archivos del taller y genera el reporte correspondiente.
     *
     * En caso de presentarse algún problema durante la ejecución, se captura
     * la excepción y se muestra un mensaje informativo al usuario.
     *
     * @param args argumentos recibidos desde la línea de comandos.
     */
    public static void main(String[] args) {

        try {

            AnalizadorRepuestos analizador = new AnalizadorRepuestos();

            ResultadoAnalisis resultado = analizador.ejecutar();

            new GeneradorReportes().generar(resultado);


            System.out.println("ANALISIS DEL TALLER AUTOMOTRIZ");
            System.out.println("-------------------------------");
            System.out.println("Ordenes procesadas: " 
                    + resultado.getOrdenesProcesadas());

            System.out.println("Mayor frecuencia: " 
                    + resultado.getRepuestoMayorRotacion());

            System.out.println(
                    "Reporte generado: data/inventario_recomendado.csv");


        } catch(Exception e){

            System.out.println("Error: " + e.getMessage());

        }
    }
}