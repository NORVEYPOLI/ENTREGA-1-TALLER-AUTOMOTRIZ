package com.poli.taller.servicio;

import java.util.Map;

public class ResultadoAnalisis {
    private final Map<Integer, Integer> rotacion;
    private final int ordenesProcesadas;
    private final String repuestoMayorRotacion;

    public ResultadoAnalisis(Map<Integer,Integer> rotacion, int ordenesProcesadas, String repuestoMayorRotacion){
        this.rotacion = rotacion;
        this.ordenesProcesadas = ordenesProcesadas;
        this.repuestoMayorRotacion = repuestoMayorRotacion;
    }

    public Map<Integer,Integer> getRotacion(){ return rotacion; }
    public int getOrdenesProcesadas(){ return ordenesProcesadas; }
    public String getRepuestoMayorRotacion(){ return repuestoMayorRotacion; }
}