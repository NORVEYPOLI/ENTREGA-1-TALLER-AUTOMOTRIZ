package com.poli.taller.servicio;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import com.poli.taller.modelo.Mecanico;
import com.poli.taller.modelo.Repuesto;

/**
 * Servicio encargado de analizar la información del taller automotriz.
 *
 * <p>Esta clase carga el catálogo de mecánicos y de repuestos, lee el archivo
 * de órdenes de trabajo y cruza esa información para calcular, por cada
 * mecánico, el valor total en repuestos que utilizó, y por cada repuesto, la
 * cantidad total de unidades usadas (su rotación).</p>
 *
 * <p>La lectura de {@code ordenes.txt} es tolerante a errores: cualquier
 * línea con formato inválido, con datos no numéricos donde se esperaba un
 * número, con una cantidad menor o igual a cero, o que haga referencia a un
 * mecánico o a un repuesto que no existe en el catálogo, se descarta y se
 * reporta en consola, <b>sin detener el análisis del resto del archivo</b>.</p>
 *
 * <p>El resultado del procesamiento se almacena en un objeto
 * {@link ResultadoAnalisis}.</p>
 *
 * @author Jhonatan Armando Moreno Bohada
 *         Edison Norvey Luis Sanchez
 *         John Edwin Linares Buitrago
 *         Natalia Andrea Lopez Cardona
 * @version 2.0
 */
public class AnalizadorRepuestos {

    /** Ruta del archivo con la información de los mecánicos. */
    private final String rutaMecanicos = "data/mecanicos.txt";

    /** Ruta del archivo con el catálogo de repuestos. */
    private final String rutaRepuestos = "data/repuestos.csv";

    /** Ruta del archivo donde se encuentran registradas las órdenes del taller. */
    private final String rutaOrdenes = "data/ordenes.txt";

    /** Cantidad de campos esperados en cada línea (distinta del encabezado) de {@code ordenes.txt}. */
    private static final int CAMPOS_POR_ORDEN = 4;

    /**
     * Ejecuta el análisis completo del taller: carga mecánicos y repuestos,
     * lee y valida las órdenes de trabajo, y calcula el valor consumido por
     * cada mecánico y la rotación de cada repuesto.
     *
     * @return objeto {@link ResultadoAnalisis} con los resultados obtenidos.
     * @throws IOException si no se puede leer alguno de los archivos de entrada.
     */
    public ResultadoAnalisis ejecutar() throws IOException {

        Map<Long, Mecanico> mecanicos = cargarMecanicos();
        Map<Integer, Repuesto> repuestos = cargarRepuestos();

        Map<Long, Double> valorPorMecanico = new HashMap<>();
        Map<Integer, Integer> usosPorRepuesto = new HashMap<>();

        int ordenesProcesadas = 0;
        int ordenesDescartadas = 0;

        try (BufferedReader br = new BufferedReader(new FileReader(rutaOrdenes))) {

            br.readLine(); // Se descarta el encabezado ID_ORDEN;NUMERO_DOCUMENTO_MECANICO;...
            String linea;

            while ((linea = br.readLine()) != null) {
                if (linea.trim().isEmpty()) {
                    continue;
                }

                DatosOrden datos = validarOrden(linea, mecanicos, repuestos);

                if (datos == null) {
                    System.out.println("Orden descartada por formato invalido o datos incoherentes: " + linea);
                    ordenesDescartadas++;
                    continue;
                }

                Repuesto repuesto = repuestos.get(datos.idRepuesto);
                double valorOrden = repuesto.getPrecioPorUnidad() * datos.cantidadUsada;

                valorPorMecanico.merge(datos.numeroDocumentoMecanico, valorOrden, Double::sum);
                usosPorRepuesto.merge(datos.idRepuesto, datos.cantidadUsada, Integer::sum);
                ordenesProcesadas++;
            }
        }

        return new ResultadoAnalisis(mecanicos, repuestos, valorPorMecanico, usosPorRepuesto,
                ordenesProcesadas, ordenesDescartadas);
    }

    /**
     * Carga en memoria el archivo de mecánicos.
     *
     * @return mapa de mecánicos indexado por número de documento.
     * @throws IOException si el archivo no existe o no se puede leer.
     */
    private Map<Long, Mecanico> cargarMecanicos() throws IOException {
        Map<Long, Mecanico> mecanicosPorDocumento = new HashMap<>();

        try (BufferedReader br = new BufferedReader(new FileReader(rutaMecanicos))) {
            String linea;
            while ((linea = br.readLine()) != null) {
                if (linea.trim().isEmpty()) {
                    continue;
                }

                String[] campos = linea.split(";");
                if (campos.length != 4) {
                    System.out.println("Linea de mecanico ignorada por formato invalido: " + linea);
                    continue;
                }

                try {
                    String tipoDocumento = campos[0].trim();
                    long numeroDocumento = Long.parseLong(campos[1].trim());
                    String nombres = campos[2].trim();
                    String apellidos = campos[3].trim();

                    mecanicosPorDocumento.put(numeroDocumento,
                            new Mecanico(tipoDocumento, numeroDocumento, nombres, apellidos));
                } catch (NumberFormatException excepcion) {
                    System.out.println("Linea de mecanico ignorada por numero de documento invalido: " + linea);
                }
            }
        }

        return mecanicosPorDocumento;
    }

    /**
     * Carga en memoria el catálogo de repuestos.
     *
     * @return mapa de repuestos indexado por su identificador.
     * @throws IOException si el archivo no existe o no se puede leer.
     */
    private Map<Integer, Repuesto> cargarRepuestos() throws IOException {
        Map<Integer, Repuesto> repuestosPorId = new HashMap<>();

        try (BufferedReader br = new BufferedReader(new FileReader(rutaRepuestos))) {
            String linea;
            while ((linea = br.readLine()) != null) {
                if (linea.trim().isEmpty()) {
                    continue;
                }

                String[] campos = linea.split(";");
                if (campos.length != 3) {
                    System.out.println("Linea de repuesto ignorada por formato invalido: " + linea);
                    continue;
                }

                try {
                    int id = Integer.parseInt(campos[0].trim());
                    String nombre = campos[1].trim();
                    double precioPorUnidad = Double.parseDouble(campos[2].trim());

                    if (precioPorUnidad < 0) {
                        System.out.println("Repuesto ignorado por tener precio negativo: " + linea);
                        continue;
                    }

                    repuestosPorId.put(id, new Repuesto(id, nombre, precioPorUnidad));
                } catch (NumberFormatException excepcion) {
                    System.out.println("Linea de repuesto ignorada por datos numericos invalidos: " + linea);
                }
            }
        }

        return repuestosPorId;
    }

    /**
     * Valida una línea de {@code ordenes.txt} contra los mecánicos y
     * repuestos ya cargados, y extrae sus datos si es válida.
     *
     * @param linea      línea leída del archivo de órdenes.
     * @param mecanicos  mecánicos disponibles, indexados por documento.
     * @param repuestos  repuestos disponibles, indexados por id.
     * @return los datos de la orden si es válida y coherente; {@code null} en caso contrario.
     */
    private DatosOrden validarOrden(String linea, Map<Long, Mecanico> mecanicos, Map<Integer, Repuesto> repuestos) {
        String[] campos = linea.split(";");
        if (campos.length != CAMPOS_POR_ORDEN) {
            return null;
        }

        try {
            long numeroDocumentoMecanico = Long.parseLong(campos[1].trim());
            int idRepuesto = Integer.parseInt(campos[2].trim());
            int cantidadUsada = Integer.parseInt(campos[3].trim());

            if (cantidadUsada <= 0) {
                return null;
            }
            if (!mecanicos.containsKey(numeroDocumentoMecanico)) {
                return null;
            }
            if (!repuestos.containsKey(idRepuesto)) {
                return null;
            }

            return new DatosOrden(numeroDocumentoMecanico, idRepuesto, cantidadUsada);
        } catch (NumberFormatException excepcion) {
            return null;
        }
    }

    /**
     * Estructura interna simple con los datos numéricos ya validados de una
     * línea de {@code ordenes.txt}.
     */
    private static final class DatosOrden {
        private final long numeroDocumentoMecanico;
        private final int idRepuesto;
        private final int cantidadUsada;

        private DatosOrden(long numeroDocumentoMecanico, int idRepuesto, int cantidadUsada) {
            this.numeroDocumentoMecanico = numeroDocumentoMecanico;
            this.idRepuesto = idRepuesto;
            this.cantidadUsada = cantidadUsada;
        }
    }
}