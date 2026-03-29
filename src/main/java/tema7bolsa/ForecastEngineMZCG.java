package tema7bolsa;

import org.apache.commons.math3.analysis.polynomials.PolynomialFunction;
import org.apache.commons.math3.fitting.PolynomialCurveFitter;
import org.apache.commons.math3.fitting.WeightedObservedPoints;
import org.apache.commons.math3.optimization.fitting.PolynomialFitter;
import org.apache.commons.math3.stat.descriptive.DescriptiveStatistics;

import java.io.File;
import java.time.LocalDate;
import java.time.Month;
import java.time.temporal.ChronoUnit;
import java.util.*;
import java.util.stream.Collectors;

/**
 * Motor de estimación: entrena un modelo de regresión polinómica con los
 * datos del primer semestre y proyecta los precios del segundo semestre.
 *
 * INSTRUCCIÓN: Renombra esta clase a ForecastEngine + tus iniciales.
 */
public class ForecastEngineMZCG {
	
	public static List<StockDataMZCG> predecir(List<StockDataMZCG> listaRegistros1Semestre, List<LocalDate> fechas2Semestre) {
		// Asumimos que recibimos la lista del primer semestre 
		List<StockDataMZCG> prediccion2Semestre = new ArrayList<>();
		WeightedObservedPoints puntos = new WeightedObservedPoints();
		LocalDate fechaIni = listaRegistros1Semestre.get(0).getDate();
		/*long dias = ChronoUnit.DAYS.between(fechaIni, LocalDate.of(2025, 12, 31));
		*/
		// 1. Entrenar al  modelo con los puntos del primerr semestre
		for(StockDataMZCG registroDiario : listaRegistros1Semestre) {
			// Para ello en el 'x' usamos los dias que hay entre ellos y , el valor 'y' sera el precio de cierre de ese dia
			puntos.add(ChronoUnit.DAYS.between(fechaIni, registroDiario.getDate()), registroDiario.getClosePrice());
		}
		
		//2. Predecimos los precios del 2 semestre 
		PolynomialCurveFitter fitter = PolynomialCurveFitter.create(2); // grado 3
		double[] coeficientes = fitter.fit(puntos.toList());
		
		//3. Prediccion 2 Semestre.
		for(LocalDate fecha : fechas2Semestre) {
		    double x = ChronoUnit.DAYS.between(fechaIni, fecha);
		    double precio = 0;
		    for (int i = 0; i < coeficientes.length; i++) {
		        precio += coeficientes[i] * Math.pow(x, i);
		    }
		    // Añadimos el stock con valor y fecha del 2 semestre como prediccion
		    prediccion2Semestre.add(new StockDataMZCG(fecha, precio));
		}
		return prediccion2Semestre;
	}
	
	public static void main(String[] args) {
		File file = new File("data\\AMZN_2025.csv");
		List<StockDataMZCG> list = CsvReaderMZCG.leerCSV(file);
		List<StockDataMZCG> lista1Semestre = list.stream()
	    .filter(s -> s.getDate().getMonthValue() <= 6)
	    .collect(Collectors.toList());
		
		List<LocalDate> listaFechas =  list.stream()
	    .filter(s -> s.getDate().getMonthValue() > 6)
	    .map(StockDataMZCG::getDate)
	    .collect(Collectors.toList());
		
		predecir(lista1Semestre, listaFechas).forEach(System.out::println);;
		
	}
  }
