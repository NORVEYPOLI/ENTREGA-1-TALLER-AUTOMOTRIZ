package com.poli.taller.servicio;

import java.io.*;

public class GeneradorReportes {

    public void generar(ResultadoAnalisis resultado) throws IOException {

        File carpeta = new File("data");
        if(!carpeta.exists()) carpeta.mkdirs();

        try(BufferedWriter bw = new BufferedWriter(
                new FileWriter("data/inventario_recomendado.csv"))){

            bw.write("ID_REPUESTO;CANTIDAD_USADA;ESTADO");
            bw.newLine();

            resultado.getRotacion().forEach((id,cantidad)->{
                try{
                    String estado = cantidad >= 10 ? "Alta rotacion" : "Rotacion normal";
                    bw.write(id + ";" + cantidad + ";" + estado);
                    bw.newLine();
                }catch(IOException e){
                    throw new RuntimeException(e);
                }
            });
        }
    }
}