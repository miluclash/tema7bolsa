package tema7bolsa;

import org.apache.commons.csv.CSVFormat; 
import org.apache.commons.csv.CSVRecord;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.Reader;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

/**
 * Lee archivos CSV con cotizaciones bursátiles y construye listas de StockDataXXX.
 *
 * Formato esperado del CSV (compatible con Yahoo Finance y Stooq):
 *   Date,Open,High,Low,Close,Volume
 *   2025-01-02,140.23,145.67,139.80,144.50,32000000
 *   ...
 *
 * INSTRUCCIÓN: Renombra esta clase a CsvReader + tus iniciales.
 */
public class CsvReaderMZCG {
	
	public CsvReaderMZCG() {
		super();
	}

	/**
	 * Este metodo obtiene el File que apunta al csv que se quiera leer
	 */
	public static List<StockDataMZCG> leerCSV(File file) {
		List<StockDataMZCG> listaStock = new ArrayList<>();
		try {
			FileReader reader = new FileReader(file);
			Iterable<CSVRecord> records = CSVFormat.DEFAULT.withFirstRecordAsHeader().parse(reader);
			for(CSVRecord record : records) {
				String date = record.get("Date");
				String closePrice = record.get("Close");
				StockDataMZCG stock = new StockDataMZCG(date, closePrice);
				listaStock.add(stock);
			}
		
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		/*La lista esta ordenada en descendente, primero diciembre y finalmente enero. 
		 * Lo ordenaremos de manera 'enero a diciembre' antes de devolverlo*
		 */
		listaStock.sort(Comparator.comparing(StockDataMZCG::getDate));
		
		return listaStock;
	}
	
	public static void main(String[] args) {
		File file = new File("data\\AMZN_2025.csv");
		List<StockDataMZCG> list = leerCSV(file);
		list.forEach(System.out::println);	
	}
	
   }
