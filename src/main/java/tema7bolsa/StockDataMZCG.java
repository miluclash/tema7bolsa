package tema7bolsa;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

/**
 * Modelo de datos que representa una sesión bursátil.
 * Cada objeto almacena la fecha y el precio de cierre de un día de mercado.
 *
 * INSTRUCCIÓN: Renombra esta clase a StockData + tus iniciales.
 * Ejemplo: StockDataABR
 */
public class StockDataMZCG {
	//private final LocalDate date, private final double closePrice : no puede ser final 
	private LocalDate date;
	private double closePrice;
	
	/*
	 * Constructor para usar un array
	 */
	public StockDataMZCG(String[]data) {
		super();
		// Definimos el patrón que tiene el String
        DateTimeFormatter formato = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        LocalDate fechaStock = LocalDate.parse(data[0], formato);
		this.date = fechaStock;
		this.closePrice = Double.parseDouble(data[1]);
	}
	
	/**
	 * Constructor para usar los String de datos
	 * @param date
	 * @param closePrice
	 */
	public StockDataMZCG(String date, String closePrice) {
		// Definimos el patrón que tiene el String
        DateTimeFormatter formato = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        LocalDate fechaStock = LocalDate.parse(date, formato);
		this.date = fechaStock;
		this.closePrice = Double.parseDouble(closePrice);
	}
	
	
	public StockDataMZCG(LocalDate date, double closePrice) {
		super();
		this.date = date;
		this.closePrice = closePrice;
	}

	public LocalDate getDate() {
		return date;
	}
	public double getClosePrice() {
		return closePrice;
	}
	public void setDate(LocalDate date) {
		this.date = date;
	}
	public void setClosePrice(double closePrice) {
		this.closePrice = closePrice;
	}
	@Override
	public String toString() {
		return "StockDataMZCG [date=" + date + ", closePrice=" + closePrice + "]";
	}
	
	
    }
