package com.poli.taller.servicio;

import java.util.Map;

import com.poli.taller.modelo.Mecanico;
import com.poli.taller.modelo.Repuesto;

/**
 * Clase encargada de almacenar los resultados obtenidos durante el análisis
 * de las órdenes de trabajo del taller automotriz.
 *
 * <p>Esta clase funciona como un objeto de transferencia de información entre
 * los servicios del sistema, permitiendo transportar los datos calculados por
 * {@link AnalizadorRepuestos} hacia {@link GeneradorReportes}.</p>
 *
 * <p>Contiene el catálogo de mecánicos y de repuestos ya cargado, el valor
 * total en repuestos utilizado por cada mecánico, la cantidad total usada de
 * cada repuesto, y el conteo de órdenes procesadas correctamente frente a las
 * descartadas por formato inválido o datos incoherentes.</p>
 *
 * @author Jhonatan Armando Moreno Bohada
 *         Edison Norvey Luis Sanchez
 *         John Edwin Linares Buitrago
 *         Natalia Andrea Lopez Cardona
 * @version 2.0
 */
public class ResultadoAnalisis {

    /** Mecánicos del taller, indexados por número de documento. */
    private final Map<Long, Mecanico> mecanicos;

    /** Catálogo de repuestos, indexado por identificador. */
    private final Map<Integer, Repuesto> repuestos;

    /** Valor total en repuestos utilizado por cada mecánico (clave: número de documento). */
    private final Map<Long, Double> valorPorMecanico;

    /** Cantidad total de unidades usadas de cada repuesto (clave: id del repuesto). */
    private final Map<Integer, Integer> usosPorRepuesto;

    /** Cantidad de órdenes que pasaron todas las validaciones. */
    private final int ordenesProcesadas;

    /** Cantidad de órdenes descartadas por formato inválido o datos incoherentes. */
    private final int ordenesDescartadas;

    /**
     * Crea un nuevo resultado del análisis realizado.
     *
     * @param mecanicos          mecánicos del taller, indexados por documento.
     * @param repuestos          catálogo de repuestos, indexado por id.
     * @param valorPorMecanico   valor total en repuestos usado por cada mecánico.
     * @param usosPorRepuesto    cantidad total usada de cada repuesto.
     * @param ordenesProcesadas  cantidad de órdenes válidas procesadas.
     * @param ordenesDescartadas cantidad de órdenes descartadas.
     */
    public ResultadoAnalisis(
            Map<Long, Mecanico> mecanicos,
            Map<Integer, Repuesto> repuestos,
            Map<Long, Double> valorPorMecanico,
            Map<Integer, Integer> usosPorRepuesto,
            int ordenesProcesadas,
            int ordenesDescartadas) {

        this.mecanicos = mecanicos;
        this.repuestos = repuestos;
        this.valorPorMecanico = valorPorMecanico;
        this.usosPorRepuesto = usosPorRepuesto;
        this.ordenesProcesadas = ordenesProcesadas;
        this.ordenesDescartadas = ordenesDescartadas;
    }

    /**
     * Obtiene los mecánicos del taller.
     *
     * @return mapa de mecánicos indexado por número de documento.
     */
    public Map<Long, Mecanico> getMecanicos() {
        return mecanicos;
    }

    /**
     * Obtiene el catálogo de repuestos.
     *
     * @return mapa de repuestos indexado por id.
     */
    public Map<Integer, Repuesto> getRepuestos() {
        return repuestos;
    }

    /**
     * Obtiene el valor total en repuestos utilizado por cada mecánico.
     *
     * @return mapa con el valor acumulado por número de documento del mecánico.
     */
    public Map<Long, Double> getValorPorMecanico() {
        return valorPorMecanico;
    }

    /**
     * Obtiene la cantidad total de unidades usadas de cada repuesto.
     *
     * @return mapa con la cantidad usada por id de repuesto.
     */
    public Map<Integer, Integer> getUsosPorRepuesto() {
        return usosPorRepuesto;
    }

    /**
     * Obtiene la cantidad de órdenes que pasaron todas las validaciones.
     *
     * @return cantidad de órdenes procesadas.
     */
    public int getOrdenesProcesadas() {
        return ordenesProcesadas;
    }

    /**
     * Obtiene la cantidad de órdenes descartadas por formato inválido o datos
     * incoherentes.
     *
     * @return cantidad de órdenes descartadas.
     */
    public int getOrdenesDescartadas() {
        return ordenesDescartadas;
    }

    /**
     * Obtiene el nombre del repuesto con mayor cantidad de unidades usadas.
     *
     * @return nombre del repuesto con mayor rotación, o {@code "Sin datos"}
     *         si no hay repuestos con uso registrado.
     */
    public String getRepuestoMayorRotacion() {
        int idMayor = -1;
        int mayorUso = 0;

        for (Map.Entry<Integer, Integer> entrada : usosPorRepuesto.entrySet()) {
            if (entrada.getValue() > mayorUso) {
                mayorUso = entrada.getValue();
                idMayor = entrada.getKey();
            }
        }

        if (idMayor == -1 || !repuestos.containsKey(idMayor)) {
            return "Sin datos";
        }

        return repuestos.get(idMayor).getNombre() + " (" + mayorUso + " usos)";
    }

    /**
     * Obtiene el nombre del mecánico con mayor valor acumulado en repuestos
     * utilizados.
     *
     * @return nombre completo del mecánico de mayor consumo, o
     *         {@code "Sin datos"} si no hay mecánicos con consumo registrado.
     */
    public String getMecanicoMayorConsumo() {
        long documentoMayor = -1;
        double mayorValor = 0.0;

        for (Map.Entry<Long, Double> entrada : valorPorMecanico.entrySet()) {
            if (entrada.getValue() > mayorValor) {
                mayorValor = entrada.getValue();
                documentoMayor = entrada.getKey();
            }
        }

        if (documentoMayor == -1 || !mecanicos.containsKey(documentoMayor)) {
            return "Sin datos";
        }

        return mecanicos.get(documentoMayor).getNombreCompleto();
    }
}