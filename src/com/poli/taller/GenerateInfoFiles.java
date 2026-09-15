package com.poli.taller;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Random;
import java.util.Set;

import com.poli.taller.modelo.Mecanico;
import com.poli.taller.modelo.Repuesto;

/**
 * Clase encargada de generar, de manera pseudoaleatoria, los archivos planos
 * que se utilizarán como datos de entrada del proyecto de Taller Automotriz.
 *
 * <p>Se generan tres archivos dentro de la carpeta {@value #CARPETA_DATOS}:</p>
 * <ul>
 *   <li>Información de los mecánicos del taller.</li>
 *   <li>Catálogo de repuestos disponibles.</li>
 *   <li>Órdenes de trabajo con los repuestos utilizados.</li>
 * </ul>
 *
 * <p>Esta clase corresponde a la Entrega 1 - Semana 3 del módulo
 * Conceptos Fundamentales de Programación.</p>
 *
 * @author Jhonatan Armando Moreno Bohada
 *         Edison Norvey Luis Sanchez
 * @version 1.1
 */
public class GenerateInfoFiles {

    /** Carpeta donde se guardan los archivos generados. */
    private static final String CARPETA_DATOS = "data";

    /** Cantidades utilizadas al ejecutar el programa sin solicitar datos al usuario. */
    private static final int CANTIDAD_MECANICOS_POR_DEFECTO = 6;
    private static final int CANTIDAD_REPUESTOS_POR_DEFECTO = 20;
    private static final int CANTIDAD_ORDENES_POR_DEFECTO = 20;

    /** Posibles tipos de documento de identificación. */
    private static final String[] TIPOS_DOCUMENTO = { "CC", "CE", "TI", "PA" };

    /** Nombres utilizados para generar mecánicos de forma pseudoaleatoria. */
    private static final String[] NOMBRES = {
            "Juan", "Carlos", "Andres", "Camilo", "Jorge", "Wilson", "Oscar",
            "Fabian", "Miguel", "David", "Sebastian", "Felipe", "Ricardo",
            "Alejandro", "Diego", "Mauricio", "Julian", "Leonardo", "Nelson", "Cristian"
    };

    /** Apellidos utilizados para generar mecánicos de forma pseudoaleatoria. */
    private static final String[] APELLIDOS = {
            "Gomez", "Rodriguez", "Martinez", "Lopez", "Garcia", "Perez",
            "Sanchez", "Ramirez", "Torres", "Diaz", "Vargas", "Castro",
            "Ruiz", "Alvarez", "Romero", "Suarez", "Rojas", "Moreno", "Munoz", "Ortiz"
    };

    /** Repuestos comunes de un taller automotriz. */
    private static final String[] NOMBRES_REPUESTOS = {
            "Filtro de aceite", "Filtro de aire", "Pastillas de freno", "Disco de freno",
            "Bujia", "Correa de distribucion", "Bateria", "Radiador", "Alternador",
            "Bomba de agua", "Amortiguador", "Aceite de motor", "Kit de embrague",
            "Sensor de oxigeno", "Rotula de suspension", "Terminal de direccion",
            "Banda de accesorios", "Buje de suspension", "Fusible", "Llanta"
    };

    /** Generador pseudoaleatorio compartido por los métodos de la clase. */
    private static final Random ALEATORIO = new Random();

    private GenerateInfoFiles() {
        // Evita crear instancias de una clase que solo contiene métodos estáticos.
    }

    /**
     * Punto de entrada del programa. Genera los archivos requeridos sin solicitar
     * información al usuario y muestra un resumen en consola.
     *
     * @param args argumentos de línea de comandos; no se utilizan en esta entrega.
     */
    public static void main(String[] args) {
        try {
            Files.createDirectories(Paths.get(CARPETA_DATOS));

            List<Mecanico> mecanicosGenerados = createMecanicosFile(CANTIDAD_MECANICOS_POR_DEFECTO);
            List<Repuesto> repuestosGenerados = createRepuestosFile(CANTIDAD_REPUESTOS_POR_DEFECTO);

            createOrdenesFile(
                    CANTIDAD_ORDENES_POR_DEFECTO,
                    mecanicosGenerados,
                    repuestosGenerados);

            System.out.println("Generacion de archivos completada exitosamente.");
            System.out.println("Carpeta de salida: " + new File(CARPETA_DATOS).getAbsolutePath());
            System.out.println("Mecanicos generados: " + mecanicosGenerados.size());
            System.out.println("Repuestos generados: " + repuestosGenerados.size());
            System.out.println("Ordenes generadas: " + CANTIDAD_ORDENES_POR_DEFECTO);
        } catch (IOException | IllegalArgumentException excepcion) {
            System.err.println("Ocurrio un error generando los archivos: " + excepcion.getMessage());
        }
    }

    /**
     * Genera el archivo {@code mecanicos.txt} con información pseudoaleatoria
     * de los mecánicos del taller.
     *
     * @param cantidadMecanicos cantidad de mecánicos que se deben generar.
     * @return lista con los mecánicos escritos en el archivo.
     * @throws IOException si ocurre un error al crear o escribir el archivo.
     */
    public static List<Mecanico> createMecanicosFile(int cantidadMecanicos) throws IOException {
        if (cantidadMecanicos < 0) {
            throw new IllegalArgumentException("La cantidad de mecanicos no puede ser negativa.");
        }

        List<Mecanico> mecanicosGenerados = new ArrayList<>();
        Set<Long> documentosUsados = new HashSet<>();
        String rutaArchivo = CARPETA_DATOS + File.separator + "mecanicos.txt";

        try (BufferedWriter escritor = new BufferedWriter(new FileWriter(rutaArchivo))) {
            for (int i = 0; i < cantidadMecanicos; i++) {
                String tipoDocumento = TIPOS_DOCUMENTO[ALEATORIO.nextInt(TIPOS_DOCUMENTO.length)];
                long numeroDocumento = generarDocumentoUnico(documentosUsados);
                String nombres = NOMBRES[ALEATORIO.nextInt(NOMBRES.length)];
                String apellidos = APELLIDOS[ALEATORIO.nextInt(APELLIDOS.length)];

                Mecanico mecanico = new Mecanico(tipoDocumento, numeroDocumento, nombres, apellidos);
                mecanicosGenerados.add(mecanico);

                escritor.write(mecanico.toFileLine());
                escritor.newLine();
            }
        }

        return mecanicosGenerados;
    }

    /**
     * Genera el archivo {@code repuestos.csv} con el catálogo de repuestos que
     * podrá ser utilizado por las órdenes de trabajo.
     *
     * @param cantidadRepuestos cantidad de repuestos que se deben generar.
     * @return lista con los repuestos escritos en el archivo.
     * @throws IOException si ocurre un error al crear o escribir el archivo.
     */
    public static List<Repuesto> createRepuestosFile(int cantidadRepuestos) throws IOException {
        if (cantidadRepuestos < 0) {
            throw new IllegalArgumentException("La cantidad de repuestos no puede ser negativa.");
        }

        List<Repuesto> repuestosGenerados = new ArrayList<>();
        String rutaArchivo = CARPETA_DATOS + File.separator + "repuestos.csv";

        try (BufferedWriter escritor = new BufferedWriter(new FileWriter(rutaArchivo))) {
            for (int idRepuesto = 1; idRepuesto <= cantidadRepuestos; idRepuesto++) {
                String nombreRepuesto = NOMBRES_REPUESTOS[ALEATORIO.nextInt(NOMBRES_REPUESTOS.length)];
                double precioPorUnidad = 8000 + ALEATORIO.nextInt(250_000);

                Repuesto repuesto = new Repuesto(idRepuesto, nombreRepuesto, precioPorUnidad);
                repuestosGenerados.add(repuesto);

                escritor.write(repuesto.toFileLine());
                escritor.newLine();
            }
        }

        return repuestosGenerados;
    }

    /**
     * Genera un único archivo {@code ordenes.txt}. Cada registro relaciona una
     * orden con un mecánico existente, un repuesto existente y la cantidad
     * utilizada durante el servicio.
     *
     * @param cantidadOrdenes cantidad de órdenes que se deben generar.
     * @param mecanicos lista de mecánicos disponibles para asignar a las órdenes.
     * @param repuestos lista de repuestos disponibles para utilizar en las órdenes.
     * @throws IOException si ocurre un error al crear o escribir el archivo.
     */
    public static void createOrdenesFile(
            int cantidadOrdenes,
            List<Mecanico> mecanicos,
            List<Repuesto> repuestos) throws IOException {

        if (cantidadOrdenes < 0) {
            throw new IllegalArgumentException("La cantidad de ordenes no puede ser negativa.");
        }
        if (cantidadOrdenes > 0 && mecanicos.isEmpty()) {
            throw new IllegalArgumentException("Se requiere al menos un mecanico para generar ordenes.");
        }
        if (cantidadOrdenes > 0 && repuestos.isEmpty()) {
            throw new IllegalArgumentException("Se requiere al menos un repuesto para generar ordenes.");
        }

        String rutaArchivo = CARPETA_DATOS + File.separator + "ordenes.txt";

        try (BufferedWriter escritor = new BufferedWriter(new FileWriter(rutaArchivo))) {
            escritor.write("ID_ORDEN;NUMERO_DOCUMENTO_MECANICO;ID_REPUESTO;CANTIDAD_USADA");
            escritor.newLine();

            for (int idOrden = 1; idOrden <= cantidadOrdenes; idOrden++) {
                Mecanico mecanico = mecanicos.get(ALEATORIO.nextInt(mecanicos.size()));
                Repuesto repuesto = repuestos.get(ALEATORIO.nextInt(repuestos.size()));
                int cantidadUsada = 1 + ALEATORIO.nextInt(4);

                escritor.write(
                        idOrden + ";"
                                + mecanico.getNumeroDocumento() + ";"
                                + repuesto.getId() + ";"
                                + cantidadUsada);
                escritor.newLine();
            }
        }
    }

    /**
     * Genera un número de documento pseudoaleatorio de 10 dígitos que no se
     * haya utilizado previamente durante la ejecución actual.
     *
     * @param documentosUsados documentos que ya fueron asignados.
     * @return número de documento único dentro de la ejecución.
     */
    private static long generarDocumentoUnico(Set<Long> documentosUsados) {
        long numeroDocumento;
        do {
            numeroDocumento = 1_000_000_000L + ALEATORIO.nextInt(900_000_000);
        } while (!documentosUsados.add(numeroDocumento));

        return numeroDocumento;
    }
}
