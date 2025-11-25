# Examen 3 - Programación Funcional

Aplicación en Java que procesa un archivo CSV con temperaturas de varias ciudades utilizando el paradigma funcional mediante Streams (filter, map, sorted, collect).

---

## Funcionalidades

- Carga de archivo CSV con registros de temperatura.
- Filtrado por rango de fechas.
- Cálculo del promedio de temperaturas por ciudad.
- Representación tipo “gráfica de barras” en consola.
- Identificación de la ciudad más calurosa y la menos calurosa para una fecha específica.
- Uso obligatorio de programación funcional (Streams).

---

## Estructura del Proyecto
Examen-3-Programacion-Funcional/
├── data/
│ └── temperaturas.csv
└── src/
└── Main.java

## Ejecución

Para ejecutar el programa en GitHub Codespaces o en cualquier entorno Java:

1. Abrir la carpeta `src` desde la terminal.
2. Compilar el archivo principal:
   
javac Main.java

3. Ejecutarlo:
   
java Main

4. Cuando el programa lo solicite, ingresar las fechas en formato:
dd/MM/yyyy

Ejemplo de uso:

- Fecha inicial: `01/01/2024`
- Fecha final: `10/01/2024`
- Fecha para comparar: `05/01/2024`

## Licencia

Proyecto de uso académico. 








