# Proyecto Grupal - Taller Automotriz (Entrega 1)

**INTEGRANTES:** Jhonatan Armando Moreno Bohada  
                Edison Norvey Luis Sanchez
**Modulo:** Conceptos Fundamentales de Programación 
**Entrega:** Entrega 1 - Semana 3  
**Tema elegido:** Gestion de repuestos y órdenes de trabajo de un taller automotriz  
**Java:** compatible con Java 8  
**Entorno de desarrollo:** Visual Studio Code; el proyecto conserva la configuracion para Eclipse.

## Descripción

Esta primera entrega genera archivos de prueba para representar información básica de un taller automotriz. Los datos creados serviran como entrada para las siguientes etapas del proyecto, en las que se podra cruzar el catalogo de repuestos con las ordenes de trabajo y analizar la frecuencia de uso de cada pieza.

## Clase principal

La clase `GenerateInfoFiles` crea automaticamente los archivos de entrada sin solicitar información al usuario.

Al ejecutarse:

1. Genera `data/mecanicos.txt` con la información de los mecánicos..
2. Genera `data/repuestos.csv` con el catalogo de repuestos.
3. Genera un único archivo `data/ordenes.txt` con las órdenes de trabajo.
4. Muestra en consola un resumen de los archivos generados.

## Formatos de archivo

### `mecanicos.txt`

Un mecánico por línea:

```text
TipoDocumento;NumeroDocumento;Nombres;Apellidos
```

Ejemplo:

```text
CC;1150137733;Ricardo;Torres
```

### `repuestos.csv`

Un repuesto por linea:

```text
IDRepuesto;NombreRepuesto;PrecioPorUnidad
```

Ejemplo:

```text
1;Filtro de aceite;45000.0
```

### `ordenes.txt`

La primera linea identifica los campos y las siguientes corresponden a las ordenes generadas:

```text
ID_ORDEN;NUMERO_DOCUMENTO_MECANICO;ID_REPUESTO;CANTIDAD_USADA
```

Ejemplo:

```text
1;1150137733;4;2
```

El numero de documento permite relacionar cada orden con un mecanico existente en `mecanicos.txt`; de la misma manera, el identificador del repuesto corresponde a un registro generado en `repuestos.csv`.

## Ejecucion

Ejecutar la clase:

```text
com.poli.taller.GenerateInfoFiles
```

No es necesario ingresar datos por consola. Las cantidades de mecanicos, repuestos y ordenes utilizadas para esta entrega se encuentran definidas como constantes en `GenerateInfoFiles`.
