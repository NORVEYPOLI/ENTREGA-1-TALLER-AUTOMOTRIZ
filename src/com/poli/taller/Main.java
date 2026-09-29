package com.poli.taller;

import com.poli.taller.servicio.AnalizadorRepuestos;
import com.poli.taller.servicio.GeneradorReportes;
import com.poli.taller.servicio.ResultadoAnalisis;

public class Main {

    public static void main(String[] args) {

        try {
            AnalizadorRepuestos analizador = new AnalizadorRepuestos();
            ResultadoAnalisis resultado = analizador.ejecutar();

            new GeneradorReportes().generar(resultado);

            System.out.println("ANALISIS DEL TALLER AUTOMOTRIZ");
            System.out.println("-------------------------------");
            System.out.println("Ordenes procesadas: " + resultado.getOrdenesProcesadas());
            System.out.println("Mayor frecuencia: " + resultado.getRepuestoMayorRotacion());
            System.out.println("Reporte generado: data/inventario_recomendado.csv");

        } catch(Exception e){
            System.out.println("Error: " + e.getMessage());
        }
    }
}