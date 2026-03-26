# StockForecast 2025

Análisis y estimación de valores bursátiles (TSLA, AMZN, GOOGL, NVDA) para el año 2025.  
El programa lee históricos en CSV, entrena un modelo de regresión polinómica con el primer semestre y proyecta los precios del segundo, generando gráficas comparativas.

---

## Enunciado del ejercicio

1. Busca una página que permita la descarga de datos históricos de valores bursátiles en formato CSV, como [Stooq.com](https://stooq.com) o [Investing.com](https://es.investing.com).
2. Descarga los valores de **TSLA, AMZN, GOOGL y NVDA** para 2025.
3. Genera una clase `StockDataXXX` (con tus iniciales) con los atributos:
    - `private final LocalDate date` — fecha de la sesión bursátil (ej: 2025-03-15)
    - `private final double closePrice` — precio de cierre de la acción ese día
4. Implementa `CsvReaderXXX` para leer los archivos y generar listas de `StockDataXXX`.
5. Implementa `ForecastEngineXXX` para la estimación por regresión polinómica.
6. Implementa `ChartGeneratorXXX` para la generación de gráficas con JFreeChart.
7. Implementa `MainStockXXX` que, por cada CSV:
    - **Paso 1:** Lea el CSV completo
    - **Paso 2:** Separe los semestres
    - **Paso 3:** Estime el 2do semestre usando los datos del 1ro
    - **Paso 4:** Genere la gráfica del 1er semestre (datos reales)
    - **Paso 5:** Genere la gráfica del 2do semestre: real (verde) vs estimado (naranja discontinuo)
    - **Paso 6:** Muestre por consola la desviación mensual (julio–diciembre) entre lo real y lo estimado

### Tecnologías requeridas
1. Apache Commons CSV | Lectura de archivos CSV |
2. Apache Commons Math3 | Regresión polinómica |
3. JFreeChart | Generación de gráficas PNG |

---

## Estructura del proyecto

```
StockForecast/
├── pom.xml                          ← Dependencias Maven
├── .gitignore
├── README.md
├── data/                            ← Aquí van los CSV descargados (no incluidos en el repo)
│   ├── TSLA_2025.csv
│   ├── AMZN_2025.csv
│   ├── GOOGL_2025.csv
│   └── NVDA_2025.csv
├── output/                          ← Gráficas PNG generadas (se crea automáticamente)
└── src/
    └── main/
        └── java/
            └── tema7bolsa/
                ├── StockDataXXX.java       ← Modelo de datos
                ├── CsvReaderXXX.java       ← Lectura y filtrado de CSV
                ├── ForecastEngineXXX.java  ← Estimación por regresión
                ├── ChartGeneratorXXX.java  ← Generación de gráficas
                └── MainStockXXX.java       ← Programa principal
```

> **IMPORTANTE:** Sustituye `XXX` por tus iniciales en todos los nombres de clase y archivo.  
> Ejemplo: si te llamas Ana Belén Rodríguez → `StockDataABR`, `CsvReaderABR`, etc.

--- 
### Salida esperada en consola

```
============================================================
  STOCK FORECAST 2025
============================================================

>>> Procesando: TSLA (data/TSLA_2025.csv)
--------------------------------------------------
  [CsvReader] 248 registros leídos de: data/TSLA_2025.csv
  1er semestre: 124 sesiones
  2do semestre: 124 sesiones
  [Chart] Guardada: output/TSLA_1S_real.png
  [Chart] Guardada: output/TSLA_2S_estimacion_vs_real.png

  📊 Desviación mensual estimación vs real — TSLA
  Mes          Desv. media (%)  Valoración
  ------------------------------------------
  Julio        +3.21%          ✅ Buena
  Agosto       -7.85%          ⚠️ Aceptable
  Septiembre   +12.40%         ⚠️ Aceptable
  Octubre      -22.31%         ❌ Alta
  Noviembre    -31.05%         ❌ Alta
  Diciembre    -28.90%         ❌ Alta
...
```
 
---

## Subir el proyecto a GitHub, compartir con el profesor y apuntar la  ruta en el excel compartido. 

## Entregar pdf con:
	-las gráficas generadas y explicación de cada gráfica.
	-Un apartado de conclusiones donde indiques la IA utilizada para la resolución del ejercicio, si has usado varias IAs cual ha sido la que más ha ajustado la estimación.

## Dependencias (resumen)

```xml
<!-- Apache Commons CSV -->
<dependency>
    <groupId>org.apache.commons</groupId>
    <artifactId>commons-csv</artifactId>
    <version>1.10.0</version>
</dependency>

<!-- Apache Commons Math3 -->
<dependency>
    <groupId>org.apache.commons</groupId>
    <artifactId>commons-math3</artifactId>
    <version>3.6.1</version>
</dependency>

<!-- JFreeChart -->
<dependency>
    <groupId>org.jfree</groupId>
    <artifactId>jfreechart</artifactId>
    <version>1.5.4</version>
</dependency>
```
