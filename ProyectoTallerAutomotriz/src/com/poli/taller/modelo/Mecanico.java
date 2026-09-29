package com.poli.taller.modelo;

/**
 * Representa la información básica de identificación de un mecánico del
 * taller.
 * <p>
 * Cada mecánico es quien registra las órdenes de trabajo
 * </p>
 *
 * @author Jhonatan Armando Moreno Bohada
 *         Edison Norvey Luis Sanchez
 *         John Edwin Linares Buitrago
 *         Natalia Andrea Lopez Cardona
 * 
 * @version 1.0
 */
public class Mecanico {

    /** Tipo de documento de identificación (por ejemplo: CC, CE, TI, PA). */
    private final String tipoDocumento;

    /** Número de documento de identificación del mecánico. */
    private final long numeroDocumento;

    /** Nombres del mecánico. */
    private final String nombres;

    /** Apellidos del mecánico. */
    private final String apellidos;

    /**
     * Crea una nueva instancia de {@code Mecanico}.
     *
     * @param tipoDocumento   tipo de documento de identificación.
     * @param numeroDocumento número de documento de identificación.
     * @param nombres         nombres del mecánico.
     * @param apellidos       apellidos del mecánico.
     */
    public Mecanico(String tipoDocumento, long numeroDocumento, String nombres, String apellidos) {
        this.tipoDocumento = tipoDocumento;
        this.numeroDocumento = numeroDocumento;
        this.nombres = nombres;
        this.apellidos = apellidos;
    }

    /**
     * Obtiene el tipo de documento del mecánico.
     *
     * @return tipo de documento.
     */
    public String getTipoDocumento() {
        return tipoDocumento;
    }

    /**
     * Obtiene el número de documento del mecánico.
     *
     * @return número de documento.
     */
    public long getNumeroDocumento() {
        return numeroDocumento;
    }

    /**
     * Obtiene los nombres del mecánico.
     *
     * @return nombres del mecánico.
     */
    public String getNombres() {
        return nombres;
    }

    /**
     * Obtiene los apellidos del mecánico.
     *
     * @return apellidos del mecánico.
     */
    public String getApellidos() {
        return apellidos;
    }

    /**
     * Obtiene el nombre completo del mecánico (nombres y apellidos).
     *
     * @return nombre completo del mecánico.
     */
    public String getNombreCompleto() {
        return nombres + " " + apellidos;
    }

    /**
     * Genera la línea de texto plano correspondiente a este mecánico,
     * respetando el formato definido para el archivo de información de
     * mecánicos:
     * {@code TipoDocumento;NúmeroDocumento;Nombres;Apellidos}.
     *
     * @return línea formateada, lista para escribirse en el archivo plano.
     */
    public String toFileLine() {
        return tipoDocumento + ";" + numeroDocumento + ";" + nombres + ";" + apellidos;
    }
}
