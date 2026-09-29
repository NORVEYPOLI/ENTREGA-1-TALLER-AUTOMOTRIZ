package com.poli.taller;

import com.poli.taller.servicio.AnalizadorRepuestos;
import com.poli.taller.servicio.GeneradorReportes;
import com.poli.taller.servicio.ResultadoAnalisis;

/**
 * Clase principal del sistema de análisis del taller automotriz.
 *
 * <p>Esta clase representa el punto de entrada de ejecución del programa,
 * encargándose de coordinar el procesamiento entre los diferentes servicios
 * del sistema: carga de mecánicos y repuestos, lectura y validación de las
 * órdenes de trabajo, y generación de los reportes de salida.</p>
 *
 * <p>El flujo principal consiste en ejecutar el análisis del taller,
 * obtener los resultados del procesamiento y generar los reportes finales
 * de mecánicos y de rotación de inventario.</p>
 *
 * <p>Esta clase no solicita ningún dato al usuario, tal como lo exige la
 * guía de la actividad.</p>
 *
 * @author Jhonatan Armando Moreno Bohada
 *         Edison Norvey Luis Sanchez
 *         John Edwin Linares Buitrago
 *         Natalia Andrea Lopez Cardona
 * @version 2.0
 */
public class Main {

    /**
     * Método principal de ejecución del sistema.
     *
     * <p>Inicializa el servicio de análisis, procesa la información
     * registrada en los archivos del taller y genera los reportes
     * correspondientes.</p>
     *
     * <p>En caso de presentarse algún problema durante la ejecución, se
     * captura la excepción y se muestra un mensaje informativo en consola.</p>
     *
     * @param args argumentos recibidos desde la línea de comandos; no se utilizan.
     */
    public static void main(String[] args) {
        try {
            AnalizadorRepuestos analizador = new AnalizadorRepuestos();
            ResultadoAnalisis resultado = analizador.ejecutar();

            new GeneradorReportes().generar(resultado);

            System.out.println("ANALISIS DEL TALLER AUTOMOTRIZ");
            System.out.println("-------------------------------");
            System.out.println("Mecanicos cargados: " + resultado.getMecanicos().size());
            System.out.println("Repuestos cargados: " + resultado.getRepuestos().size());
            System.out.println("Ordenes procesadas: " + resultado.getOrdenesProcesadas());

            if (resultado.getOrdenesDescartadas() > 0) {
                System.out.println("Ordenes descartadas por formato invalido o datos incoherentes: "
                        + resultado.getOrdenesDescartadas());
            }

            System.out.println("Repuesto con mayor rotacion: " + resultado.getRepuestoMayorRotacion());
            System.out.println("Mecanico con mayor consumo: " + resultado.getMecanicoMayorConsumo());
            System.out.println();
            System.out.println("Reporte de mecanicos generado: data/reporte_mecanicos.csv");
            System.out.println("Inventario recomendado generado: data/inventario_recomendado.csv");

        } catch (Exception e) {
            System.out.println("Error: " + e.getMessage());
        }
    }
}