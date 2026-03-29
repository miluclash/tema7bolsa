package tema7bolsa;


import org.jfree.chart.ChartFactory; 
import org.jfree.chart.ChartUtils;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.axis.DateAxis;
import org.jfree.chart.plot.XYPlot;
import org.jfree.chart.renderer.xy.XYLineAndShapeRenderer;
import org.jfree.data.time.Day;
import org.jfree.data.time.TimeSeries;
import org.jfree.data.time.TimeSeriesCollection;

import java.awt.*;
import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Genera gráficas PNG con JFreeChart para:
 *   - Gráfica 1: Evolución real del 1er semestre
 *   - Gráfica 2: Comparativa 2do semestre real (verde) vs estimado (naranja)
 *
 * INSTRUCCIÓN: Renombra esta clase a ChartGenerator + tus iniciales.
 */
public class ChartGeneratorMZCG {
	/*List<StockDataMZCG> → TimeSeries → TimeSeriesCollection → JFreeChart → PNG*/
	
	public ChartGeneratorMZCG() {
		super();
	}
	
	public static void generate1Semestre(List<StockDataMZCG> listaStock1Semestre, String tituloGraph) {
		
		TimeSeries serie1Sem = new TimeSeries("AMZN 1 Semestre");//fecha,valor
		for(StockDataMZCG registro : listaStock1Semestre) {
			// Convierto el LocalDate a Day
			LocalDate fecha = registro.getDate();
			Day dia = new Day(fecha.getDayOfMonth(), fecha.getMonthValue(), fecha.getYear());
			//fecha,valor
			serie1Sem.add(dia, registro.getClosePrice());;/*registro.getDate().da,registro.getClosePrice());*/
		}
		
		// Agrupa series las cuales pasara al grafico
		TimeSeriesCollection collection = new TimeSeriesCollection();
		collection.addSeries(serie1Sem);
		
		// Crea el grafico
		JFreeChart grafico = ChartFactory.createTimeSeriesChart(tituloGraph, "Fecha", "Precio", collection, true, false, false);
		
		// Personalizacion del grafico
		XYPlot plot = grafico.getXYPlot();
		XYLineAndShapeRenderer renderer = new XYLineAndShapeRenderer();
		renderer.setSeriesPaint(0, Color.GREEN);        // color de la serie 0
		renderer.setSeriesStroke(0, new BasicStroke(2)); // grosor de línea
		plot.setRenderer(renderer);
		
		// Formateo del eje x para mas legibilidad
		DateAxis axis = (DateAxis) plot.getDomainAxis();
		axis.setDateFormatOverride(new SimpleDateFormat("MMM-yyyy"));
		
		// Exportar grafico a PNG
		try {
			ChartUtils.saveChartAsPNG(new File("output/"+tituloGraph+".png"), grafico, 1024, 600);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public static void generate2Semestre(List<StockDataMZCG> lista2SemesReal,List<StockDataMZCG> lista2SemesPredict, String tituloGraph) {
		
		TimeSeries serie2SemReal = new TimeSeries("AMZN 2 Semestre Real");//fecha,valor
		for(StockDataMZCG registro : lista2SemesReal) {
			// Convierto el LocalDate a Day
			LocalDate fecha = registro.getDate();
			Day dia = new Day(fecha.getDayOfMonth(), fecha.getMonthValue(), fecha.getYear());
			//fecha,valor
			serie2SemReal.add(dia, registro.getClosePrice());;/*registro.getDate().da,registro.getClosePrice());*/
		}
		
		TimeSeries serie2SemPredict = new TimeSeries("AMZN 2 Semestre Predecido");//fecha,valor
		for(StockDataMZCG registro : lista2SemesPredict) {
			// Convierto el LocalDate a Day
			LocalDate fecha = registro.getDate();
			Day dia = new Day(fecha.getDayOfMonth(), fecha.getMonthValue(), fecha.getYear());
			//fecha,valor
			serie2SemPredict.add(dia, registro.getClosePrice());;/*registro.getDate().da,registro.getClosePrice());*/
		}
		
		// Agrupa series las cuales pasara al grafico
		TimeSeriesCollection collection = new TimeSeriesCollection();
		collection.addSeries(serie2SemReal);
		collection.addSeries(serie2SemPredict);
		
		// Crea el grafico
		JFreeChart grafico = ChartFactory.createTimeSeriesChart(tituloGraph, "Fecha", "Precio", collection, true, false, false);
		
		// Personalizacion del grafico
		XYPlot plot = grafico.getXYPlot();
		XYLineAndShapeRenderer renderer = new XYLineAndShapeRenderer();
		renderer.setSeriesPaint(0, Color.GREEN); // color de la serie 0
		renderer.setSeriesStroke(0, new BasicStroke(2)); // grosor de línea
		renderer.setSeriesPaint(1, Color.ORANGE);
		renderer.setSeriesStroke(1, new BasicStroke(2, BasicStroke.CAP_BUTT, BasicStroke.JOIN_BEVEL, 0, new float[]{6, 3}, 0)); // discontinua
		plot.setRenderer(renderer);
		
		// Formateo del eje x para mas legibilidad
		DateAxis axis = (DateAxis) plot.getDomainAxis();
		axis.setDateFormatOverride(new SimpleDateFormat("MMM-yyyy"));
		
		// Exportar grafico a PNG
		try {
			ChartUtils.saveChartAsPNG(new File("output/"+tituloGraph+".png"), grafico, 1024, 600);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public static void main(String[] args) {
		File file = new File("data\\AMZN_2025.csv");
		List<StockDataMZCG> list = CsvReaderMZCG.leerCSV(file);
		List<StockDataMZCG> lista1Semestre = list.stream()
	    .filter(s -> s.getDate().getMonthValue() <= 6)
	    .collect(Collectors.toList());
		
		List<StockDataMZCG> lista2Semestre =  list.stream()
	    .filter(s -> s.getDate().getMonthValue() > 6)
	    .collect(Collectors.toList());
				
		List<LocalDate> listaFechas2Semestre =  list.stream()
	    .filter(s -> s.getDate().getMonthValue() > 6)
	    .map(StockDataMZCG::getDate)
	    .collect(Collectors.toList());
		
		List<StockDataMZCG> l2SemPredict= ForecastEngineMZCG.predecir(lista1Semestre, listaFechas2Semestre);
		
		generate1Semestre(lista1Semestre, "1er Semestre");
		generate2Semestre(lista2Semestre, l2SemPredict, "2 Semestre Prediccion");
	}
}

