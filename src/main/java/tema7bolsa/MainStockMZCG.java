package tema7bolsa;

import java.io.File;
import java.time.LocalDate;
import java.time.Month;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class MainStockMZCG {
    public static void main(String[] args) {

        System.out.println("============================================================");
        System.out.println("  STOCK FORECAST 2025");
        System.out.println("============================================================\n");

        File amznCsv = new File("data\\AMZN_2025.csv");
        File googlCsv = new File("data\\GOOGL_2025.csv");
        File nvdaCsv = new File("data\\NVDA_2025.csv");
        File tslaCsv = new File("data\\TSLA_2025.csv");

        List<StockDataMZCG> amznStockList = CsvReaderMZCG.leerCSV(amznCsv);
        List<StockDataMZCG> googlStockList = CsvReaderMZCG.leerCSV(googlCsv);
        List<StockDataMZCG> nvdaStockList = CsvReaderMZCG.leerCSV(nvdaCsv);
        List<StockDataMZCG> tslaStockList = CsvReaderMZCG.leerCSV(tslaCsv);

        List<StockDataMZCG> amzn1Semestre = amznStockList.stream().filter(s -> s.getDate().getMonthValue() <= 6).collect(Collectors.toList());
        List<StockDataMZCG> googl1Semestre = googlStockList.stream().filter(s -> s.getDate().getMonthValue() <= 6).collect(Collectors.toList());
        List<StockDataMZCG> nvda1Semestre = nvdaStockList.stream().filter(s -> s.getDate().getMonthValue() <= 6).collect(Collectors.toList());
        List<StockDataMZCG> tsla1Semestre = tslaStockList.stream().filter(s -> s.getDate().getMonthValue() <= 6).collect(Collectors.toList());

        List<StockDataMZCG> amzn2Semestre = amznStockList.stream().filter(s -> s.getDate().getMonthValue() > 6).collect(Collectors.toList());
        List<StockDataMZCG> googl2Semestre = googlStockList.stream().filter(s -> s.getDate().getMonthValue() > 6).collect(Collectors.toList());
        List<StockDataMZCG> nvda2Semestre = nvdaStockList.stream().filter(s -> s.getDate().getMonthValue() > 6).collect(Collectors.toList());
        List<StockDataMZCG> tsla2Semestre = tslaStockList.stream().filter(s -> s.getDate().getMonthValue() > 6).collect(Collectors.toList());

        List<StockDataMZCG> predictAmzn = ForecastEngineMZCG.predecir(amzn1Semestre, amzn2Semestre.stream().map(StockDataMZCG::getDate).collect(Collectors.toList()));
        List<StockDataMZCG> predictGoogl = ForecastEngineMZCG.predecir(googl1Semestre, googl2Semestre.stream().map(StockDataMZCG::getDate).collect(Collectors.toList()));
        List<StockDataMZCG> predictNvda = ForecastEngineMZCG.predecir(nvda1Semestre, nvda2Semestre.stream().map(StockDataMZCG::getDate).collect(Collectors.toList()));
        List<StockDataMZCG> predictTsla = ForecastEngineMZCG.predecir(tsla1Semestre, tsla2Semestre.stream().map(StockDataMZCG::getDate).collect(Collectors.toList()));

        ChartGeneratorMZCG.generate1Semestre(amzn1Semestre, "Datos 1er Semestre AMZN 2025");
        ChartGeneratorMZCG.generate1Semestre(googl1Semestre, "Datos 1er Semestre GOOGL 2025");
        ChartGeneratorMZCG.generate1Semestre(nvda1Semestre, "Datos 1er Semestre NVDA 2025");
        ChartGeneratorMZCG.generate1Semestre(tsla1Semestre, "Datos 1er Semestre TSLA 2025");

        ChartGeneratorMZCG.generate2Semestre(amzn2Semestre, predictAmzn, "Comparacion de Precio de AMZN en 2025");
        ChartGeneratorMZCG.generate2Semestre(googl2Semestre, predictGoogl, "Comparacion de Precio de GOOGL en 2025");
        ChartGeneratorMZCG.generate2Semestre(nvda2Semestre, predictNvda, "Comparacion de Precio de NVDA en 2025");
        ChartGeneratorMZCG.generate2Semestre(tsla2Semestre, predictTsla, "Comparacion de Precio de TSLA en 2025");

        String[] tickers = {"AMZN", "GOOGL", "NVDA", "TSLA"};

        List<List<StockDataMZCG>> primeros = new ArrayList<>();
        primeros.add(amzn1Semestre); primeros.add(googl1Semestre);
        primeros.add(nvda1Semestre); primeros.add(tsla1Semestre);

        List<List<StockDataMZCG>> reales = new ArrayList<>();
        reales.add(amzn2Semestre); reales.add(googl2Semestre);
        reales.add(nvda2Semestre); reales.add(tsla2Semestre);

        List<List<StockDataMZCG>> predicciones = new ArrayList<>();
        predicciones.add(predictAmzn); predicciones.add(predictGoogl);
        predicciones.add(predictNvda); predicciones.add(predictTsla);

        for (int t = 0; t < tickers.length; t++) {
            String csvPath = "data/" + tickers[t] + "_2025.csv";
            System.out.println(">>> Procesando: " + tickers[t] + " (" + csvPath + ")");
            System.out.println("--------------------------------------------------");
            System.out.println("  [CsvReader] " + (primeros.get(t).size() + reales.get(t).size()) + " registros leídos de: " + csvPath);
            System.out.println("  1er semestre: " + primeros.get(t).size() + " sesiones");
            System.out.println("  2do semestre: " + reales.get(t).size() + " sesiones");
            System.out.println("  [Chart] Guardada: output/" + tickers[t] + "_1S_real.png");
            System.out.println("  [Chart] Guardada: output/" + tickers[t] + "_2S_estimacion_vs_real.png");

            System.out.println("\n  📊 Desviación mensual estimación vs real — " + tickers[t]);
            System.out.println("  Mes          Desv. media (%)  Valoración");
            System.out.println("  ------------------------------------------");

            Map<Month, List<StockDataMZCG>> porMesReal = reales.get(t).stream()
                .collect(Collectors.groupingBy(s -> s.getDate().getMonth()));
            Map<Month, List<StockDataMZCG>> porMesPredict = predicciones.get(t).stream()
                .collect(Collectors.groupingBy(s -> s.getDate().getMonth()));

            for (Month mes : new Month[]{Month.JULY, Month.AUGUST, Month.SEPTEMBER,
                                          Month.OCTOBER, Month.NOVEMBER, Month.DECEMBER}) {
                List<StockDataMZCG> mesReal = porMesReal.get(mes);
                List<StockDataMZCG> mesPredict = porMesPredict.get(mes);

                double suma = 0;
                for (int i = 0; i < mesReal.size(); i++) {
                    double real = mesReal.get(i).getClosePrice();
                    double estimado = mesPredict.get(i).getClosePrice();
                    suma += ((estimado - real) / real) * 100;
                }
                double media = suma / mesReal.size();
                double absMedia = Math.abs(media);
                String valoracion = absMedia < 5 ? "✅ Buena" : absMedia < 15 ? "⚠️ Aceptable" : "❌ Alta";
                String signo = media >= 0 ? "+" : "";
                System.out.printf("  %-12s %s%.2f%%\t\t%s%n", mes, signo, media, valoracion);
            }
            System.out.println();
        }
    }
}