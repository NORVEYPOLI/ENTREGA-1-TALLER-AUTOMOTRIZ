package com.poli.taller.modelo;

/**
 * Representa un repuesto disponible en el inventario del taller automotriz.
 * <p>
 * Cada repuesto cuenta con un identificador único, un nombre descriptivo
 * y un precio de referencia por unidad. Esta clase es inmutable: una vez creado
 * un {@code Repuesto}, sus atributos no pueden modificarse.
 * </p>
 *
 * @author Jhonatan Armando Moreno Bohada
 *         Edison Norvey Luis Sanchez
 * @version 1.0
 */
public class Repuesto {

    /** Identificador único del repuesto. */
    private final int id;

    /** Nombre descriptivo del repuesto (p. ej. "Pastillas de freno"). */
    private final String nombre;

    /** Precio de venta por unidad del repuesto. */
    private final double precioPorUnidad;

    /**
     * Crea una nueva instancia de {@code Repuesto}.
     *
     * @param id              identificador único del repuesto.
     * @param nombre          nombre descriptivo del repuesto.
     * @param precioPorUnidad precio de referencia por cada unidad del repuesto.
     */
    public Repuesto(int id, String nombre, double precioPorUnidad) {
        this.id = id;
        this.nombre = nombre;
        this.precioPorUnidad = precioPorUnidad;
    }

    /**
     * Obtiene el identificador del repuesto.
     *
     * @return id del repuesto.
     */
    public int getId() {
        return id;
    }

    /**
     * Obtiene el nombre del repuesto.
     *
     * @return nombre del repuesto.
     */
    public String getNombre() {
        return nombre;
    }

    /**
     * Obtiene el precio de referencia por unidad del repuesto.
     *
     * @return precio por unidad.
     */
    public double getPrecioPorUnidad() {
        return precioPorUnidad;
    }

    /**
     * Genera la línea de texto plano correspondiente a este repuesto,
     * respetando el formato definido para el archivo de información de
     * repuestos: {@code IDRepuesto;NombreRepuesto;PrecioPorUnidad}.
     *
     * @return línea formateada, lista para escribirse en el archivo plano.
     */
    public String toFileLine() {
        return id + ";" + nombre + ";" + precioPorUnidad;
    }
}
