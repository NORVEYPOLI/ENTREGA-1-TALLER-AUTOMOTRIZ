package com.poli.taller.servicio;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import com.poli.taller.modelo.Mecanico;
import com.poli.taller.modelo.Repuesto;

/**
 * Servicio encargado de generar los reportes de salida a partir de los
 * resultados obtenidos durante el análisis del taller automotriz.
 *
 * <p>A partir de un {@link ResultadoAnalisis} genera dos archivos:</p>
 * <ul>
 *   <li>{@code reporte_mecanicos.csv}: cada mecánico con el valor total en
 *       repuestos que utilizó, ordenado de mayor a menor.</li>
 *   <li>{@code inventario_recomendado.csv}: cada repuesto del catálogo con su
 *       cantidad de usos y un nivel de rotación (Alta, Media, Baja o Sin uso),
 *       ordenado de mayor a menor uso, pensado para apoyar al área de
 *       compras y evitar el desabastecimiento de piezas clave.</li>
 * </ul>
 *
 * <p>El nivel de rotación se calcula en relación con el repuesto más
 * utilizado (no con un número fijo), para que la clasificación siga siendo
 * significativa sin importar cuántas órdenes se generen.</p>
 *
 * @author Jhonatan Armando Moreno Bohada
 *         Edison Norvey Luis Sanchez
 *         John Edwin Linares Buitrago
 *         Natalia Andrea Lopez Cardona
 * @version 2.0
 */
public class GeneradorReportes {

    private static final String CARPETA_DATOS = "data";
    private static final String ARCHIVO_REPORTE_MECANICOS = CARPETA_DATOS + File.separator + "reporte_mecanicos.csv";
    private static final String ARCHIVO_INVENTARIO = CARPETA_DATOS + File.separator + "inventario_recomendado.csv";

    /**
     * Genera los dos reportes de salida (mecánicos e inventario recomendado)
     * a partir del resultado del análisis.
     *
     * @param resultado resultado del análisis realizado por {@link AnalizadorRepuestos}.
     * @throws IOException si ocurre un error al crear o escribir alguno de los archivos.
     */
    public void generar(ResultadoAnalisis resultado) throws IOException {
        File carpeta = new File(CARPETA_DATOS);
        if (!carpeta.exists()) {
            carpeta.mkdirs();
        }

        generarReporteMecanicos(resultado);
        generarInventarioRecomendado(resultado);
    }

    /**
     * Genera {@code reporte_mecanicos.csv}: nombre completo del mecánico y
     * valor total en repuestos que utilizó, ordenado de mayor a menor. Se
     * incluyen todos los mecánicos del catálogo, incluso los que no tuvieron
     * ninguna orden válida (con valor 0.00).
     *
     * @param resultado resultado del análisis realizado.
     * @throws IOException si ocurre un error al escribir el archivo.
     */
    private void generarReporteMecanicos(ResultadoAnalisis resultado) throws IOException {
        Map<Long, Mecanico> mecanicos = resultado.getMecanicos();
        Map<Long, Double> valorPorMecanico = resultado.getValorPorMecanico();

        List<Mecanico> mecanicosOrdenados = new ArrayList<>(mecanicos.values());
        mecanicosOrdenados.sort(
                Comparator.<Mecanico>comparingDouble(
                        mecanico -> valorPorMecanico.getOrDefault(mecanico.getNumeroDocumento(), 0.0))
                        .reversed()
                        .thenComparing(Mecanico::getNombreCompleto));

        try (BufferedWriter bw = new BufferedWriter(new FileWriter(ARCHIVO_REPORTE_MECANICOS))) {
            for (Mecanico mecanico : mecanicosOrdenados) {
                double valorTotal = valorPorMecanico.getOrDefault(mecanico.getNumeroDocumento(), 0.0);
                bw.write(mecanico.getNombreCompleto() + ";" + String.format(Locale.US, "%.2f", valorTotal));
                bw.newLine();
            }
        }
    }

    /**
     * Genera {@code inventario_recomendado.csv}: cada repuesto del catálogo
     * con su cantidad de usos y su nivel de rotación, ordenado de mayor a
     * menor uso. Se incluyen todos los repuestos, incluso los que no
     * registraron ningún uso (nivel "Sin uso").
     *
     * @param resultado resultado del análisis realizado.
     * @throws IOException si ocurre un error al escribir el archivo.
     */
    private void generarInventarioRecomendado(ResultadoAnalisis resultado) throws IOException {
        Map<Integer, Repuesto> repuestos = resultado.getRepuestos();
        Map<Integer, Integer> usosPorRepuesto = resultado.getUsosPorRepuesto();

        int usoMaximo = usosPorRepuesto.values().stream().mapToInt(Integer::intValue).max().orElse(0);

        List<Repuesto> repuestosOrdenados = new ArrayList<>(repuestos.values());
        repuestosOrdenados.sort(
                Comparator.<Repuesto>comparingInt(
                        repuesto -> usosPorRepuesto.getOrDefault(repuesto.getId(), 0))
                        .reversed()
                        .thenComparing(Repuesto::getNombre));

        try (BufferedWriter bw = new BufferedWriter(new FileWriter(ARCHIVO_INVENTARIO))) {
            bw.write("ID_REPUESTO;NOMBRE_REPUESTO;CANTIDAD_USADA;NIVEL_ROTACION");
            bw.newLine();

            for (Repuesto repuesto : repuestosOrdenados) {
                int cantidadUsada = usosPorRepuesto.getOrDefault(repuesto.getId(), 0);
                String nivel = clasificarRotacion(cantidadUsada, usoMaximo);

                bw.write(repuesto.getId() + ";" + repuesto.getNombre() + ";" + cantidadUsada + ";" + nivel);
                bw.newLine();
            }
        }
    }

    /**
     * Clasifica el nivel de rotación de un repuesto en relación con el
     * repuesto más utilizado, en lugar de usar un número fijo: así la
     * clasificación se mantiene significativa sin importar cuántas órdenes
     * se hayan generado.
     *
     * @param cantidadUsada cantidad de unidades usadas del repuesto.
     * @param usoMaximo     cantidad de usos del repuesto más utilizado del catálogo.
     * @return {@code "Sin uso"}, {@code "Rotacion baja"}, {@code "Rotacion media"} o {@code "Rotacion alta"}.
     */
    private String clasificarRotacion(int cantidadUsada, int usoMaximo) {
        if (cantidadUsada <= 0) {
            return "Sin uso";
        }
        if (usoMaximo <= 0) {
            return "Sin uso";
        }

        double proporcion = (double) cantidadUsada / usoMaximo;

        if (proporcion >= 0.66) {
            return "Rotacion alta";
        }
        if (proporcion >= 0.33) {
            return "Rotacion media";
        }
        return "Rotacion baja";
    }
}