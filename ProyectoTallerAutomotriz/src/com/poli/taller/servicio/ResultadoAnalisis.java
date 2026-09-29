package com.poli.taller.servicio;

import java.util.Map;

/**
 * Clase encargada de almacenar los resultados obtenidos durante el análisis
 * de rotación de repuestos del taller automotriz.
 *
 * Esta clase funciona como un objeto de transferencia de información entre
 * los servicios del sistema, permitiendo transportar los datos calculados por
 * {@link AnalizadorRepuestos} hacia componentes como {@link GeneradorReportes}.
 *
 * Contiene la información relacionada con la cantidad de uso de cada repuesto,
 * el número de órdenes procesadas y el repuesto con mayor nivel de rotación.

 */

public class ResultadoAnalisis {


    /**
     * Mapa que almacena la relación entre el identificador del repuesto
     * y la cantidad total utilizada dentro de las órdenes analizadas.
     */
    private final Map<Integer, Integer> rotacion;


    /**
     * Cantidad total de órdenes procesadas durante el análisis.
     */
    private final int ordenesProcesadas;


    /**
     * Identificación del repuesto con mayor frecuencia de uso.
     */
    private final String repuestoMayorRotacion;


    /**
     * Crea un nuevo resultado del análisis realizado.
     *
     * @param rotacion mapa con la cantidad de uso de cada repuesto.
     * @param ordenesProcesadas cantidad de órdenes procesadas.
     * @param repuestoMayorRotacion repuesto identificado con mayor rotación.
     */
    public ResultadoAnalisis(
            Map<Integer,Integer> rotacion,
            int ordenesProcesadas,
            String repuestoMayorRotacion){

        this.rotacion = rotacion;
        this.ordenesProcesadas = ordenesProcesadas;
        this.repuestoMayorRotacion = repuestoMayorRotacion;
    }


    /**
     * Obtiene la información de rotación de los repuestos.
     *
     * @return mapa con identificador del repuesto y cantidad utilizada.
     */
    public Map<Integer,Integer> getRotacion(){
        return rotacion;
    }


    /**
     * Obtiene la cantidad de órdenes procesadas.
     *
     * @return número total de órdenes analizadas.
     */
    public int getOrdenesProcesadas(){
        return ordenesProcesadas;
    }


    /**
     * Obtiene el repuesto con mayor rotación.
     *
     * @return identificación del repuesto con mayor frecuencia.
     */
    public String getRepuestoMayorRotacion(){
        return repuestoMayorRotacion;
    }
}