# Proyecto Grupal - Taller Automotriz

**INTEGRANTES:**
- Jhonatan Armando Moreno Bohada
- Edison Norvey Luis Sanchez
- John Edwin Linares Buitrago
- Natalia Andrea Lopez Cardona

---

- **Modulo:** Conceptos Fundamentales de Programación
- **Tema elegido:** Gestion de repuestos y órdenes de trabajo de un taller automotriz
- **Java:** compatible con Java 8
- **Entorno de desarrollo:** Visual Studio Code; el proyecto conserva la configuracion para Eclipse.

## Historial de entregas

### Entrega 1 - Semana 3

Se diseñó e implementó `GenerateInfoFiles`, la clase encargada de generar de forma pseudoaleatoria los archivos de entrada del proyecto (mecánicos, repuestos y órdenes de trabajo), sin solicitar ningún dato al usuario.

### Entrega 2 - Semana 5 (versión actual)

Se agregó la segunda clase con `main` que exige la guía: `Main`, junto con una capa de servicio (`com.poli.taller.servicio`) que se encarga de leer, validar, cruzar y reportar la información generada en la Entrega 1. En detalle:

- `AnalizadorRepuestos`: carga `mecanicos.txt` y `repuestos.csv`, lee `ordenes.txt` y cruza la información, cualquier línea con formato inválido, con un mecánico o repuesto que no existe, o con una cantidad negativa o cero, se descarta y se reporta en consola **sin detener el análisis del resto del archivo**.
- `ResultadoAnalisis`: objeto que transporta los resultados del análisis hacia `GeneradorReportes` y `Main`.
- `GeneradorReportes`: genera los dos reportes de salida descritos abajo.

`Main` solo orquesta: crea el analizador, ejecuta el análisis, genera los reportes y muestra un resumen en consola. Tampoco solicita ningún dato al usuario.

## Arquitectura

```text
Main
 |
 AnalizadorRepuestos   -> lee y valida mecanicos.txt, repuestos.csv, ordenes.txt
 |
 ResultadoAnalisis     -> transporta los datos ya cruzados
 |
 GeneradorReportes     -> escribe reporte_mecanicos.csv e inventario_recomendado.csv
```

## Reportes generados

### `data/reporte_mecanicos.csv`

Cada mecánico con el valor total en repuestos que utilizó, ordenado de mayor a menor (incluye mecánicos sin órdenes, con valor 0.00):

```text
NombreCompleto;ValorTotalRepuestosUsados
```

### `data/inventario_recomendado.csv`

Cada repuesto del catálogo con su cantidad de usos y un nivel de rotación, ordenado de mayor a menor uso (incluye repuestos sin uso, marcados como "Sin uso"):

```text
ID_REPUESTO;NOMBRE_REPUESTO;CANTIDAD_USADA;NIVEL_ROTACION
```

El nivel de rotación (`Rotacion alta` / `Rotacion media` / `Rotacion baja` / `Sin uso`) se calcula **en relación con el repuesto más usado** del lote, no con un número fijo, para que la clasificación siga siendo útil sin importar cuántas órdenes se generen.

## Formatos de archivo de entrada

### `mecanicos.txt`

```text
TipoDocumento;NumeroDocumento;Nombres;Apellidos
```

### `repuestos.csv`

```text
IDRepuesto;NombreRepuesto;PrecioPorUnidad
```

### `ordenes.txt`

```text
ID_ORDEN;NUMERO_DOCUMENTO_MECANICO;ID_REPUESTO;CANTIDAD_USADA
```

## Orden de ejecución (importante)

El proyecto tiene **exactamente dos clases con `main`**, y deben ejecutarse en este orden:

1. **`com.poli.taller.GenerateInfoFiles`** — genera (o regenera) `mecanicos.txt`, `repuestos.csv` y `ordenes.txt` dentro de `data/`.
2. **`com.poli.taller.Main`** — lee esos mismos archivos, los cruza y genera `reporte_mecanicos.csv` e `inventario_recomendado.csv`.

`Main` **nunca** debe ejecutarse antes que `GenerateInfoFiles`, ni con archivos de `data/` que hayan quedado desactualizados. La solución es simple: volver a correr `GenerateInfoFiles` para refrescar `data/`, y luego correr `Main`.

```text
1º) java com.poli.taller.GenerateInfoFiles
2º) java com.poli.taller.Main
```

Ninguna de las dos clases solicita información por consola.

