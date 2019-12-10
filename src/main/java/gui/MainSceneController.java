package gui;

import javafx.fxml.FXML;
import javafx.scene.chart.BarChart;
import javafx.scene.chart.LineChart;
import javafx.scene.chart.XYChart;
import javafx.scene.control.MenuBar;
import data.JsonRecordReader;
import records.CrimeRecord;

import java.io.IOException;
import java.time.Month;
import java.time.ZonedDateTime;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.TreeMap;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class MainSceneController {
    @FXML
    private MenuBar menuBar;

    @FXML
    private BarChart<String, Number> crimesByMonthBarChart;

    @FXML
    private BarChart<String, Number> crimesByDayOfMonthBarChart;

    @FXML
    private LineChart<Number, Number> crimesByDayOfMonthLineChart;

    @FXML
    private LineChart<Number, Number> crimesByDayOfMonthTheftLineChart;

    @FXML
    private BarChart<String, Number> crimesByDayOfWeekBarChart;

    @FXML
    private BarChart<String, Number> crimesByTypeBarChart;

    @FXML
    public void initialize() throws IOException {
        List<CrimeRecord> crimeRecords = JsonRecordReader.fromDefaultSet().readCrimeRecords().parallelStream()
                .filter(r -> Objects.nonNull(r.date))
                .filter(r -> r.date.getMonth().getValue() >= 9 && r.date.getMonth().getValue() <= 11)
                .collect(Collectors.toList());// September - October only;

        List<ZonedDateTime> crimeDates = crimeRecords.parallelStream()
                .map(r -> r.date)
                .collect(Collectors.toList());

        initializeBarChart(crimesByMonthBarChart, crimeDates.parallelStream().map(ZonedDateTime::getMonth));
        initializeBarChart(crimesByDayOfMonthBarChart, crimeDates.parallelStream().map(ZonedDateTime::getDayOfMonth));
        initializeBarChart(crimesByDayOfWeekBarChart, crimeDates.parallelStream().map(ZonedDateTime::getDayOfWeek));
        initializeBarChart(crimesByTypeBarChart, crimeRecords.parallelStream()
                .map(r -> r.primaryType)
                .filter(Objects::nonNull));

        initializeCrimesByDayOfMonthLineChart(crimesByDayOfMonthLineChart, crimeDates);
        initializeCrimesByDayOfMonthLineChart(crimesByDayOfMonthTheftLineChart, crimeRecords.parallelStream()
                .filter(r -> "THEFT".equals(r.primaryType))
                .map(r -> r.date)
                .collect(Collectors.toList()));
    }

    private static <T> void initializeBarChart(final BarChart<String, Number> chart, final Stream<T> recordStream) {
        XYChart.Series<String, Number> series = new XYChart.Series<>();

        recordStream
                .collect(Collectors.groupingBy(Function.identity(), TreeMap::new, Collectors.counting()))
                .forEach((e, c) -> series.getData().add(new XYChart.Data<>(e.toString(), c)));

        chart.setLegendVisible(false);
        chart.getData().add(series);
    }

    private static <T> void initializeCrimesByDayOfMonthLineChart(final LineChart<Number, Number> chart,
            final List<ZonedDateTime> crimeDates) {
        List<Month> distinctMonths = crimeDates.parallelStream()
                .map(ZonedDateTime::getMonth)
                .distinct()
                .sorted()
                .collect(Collectors.toList());

        Map<Month, XYChart.Series<Number, Number>> seriesByMonth = distinctMonths.stream()
                .collect(Collectors.toMap(Function.identity(), v -> {
                    XYChart.Series<Number, Number> series = new XYChart.Series<>();
                    series.setName(v.toString());

                    return series;
                }, (v1, v2) -> v1, TreeMap::new));

        Map<Month, List<ZonedDateTime>> crimeDatesByMonth = crimeDates.parallelStream()
                .collect(Collectors.groupingBy(ZonedDateTime::getMonth));

        crimeDatesByMonth.forEach((month, dates) -> dates.stream()
                .collect(Collectors.groupingBy(ZonedDateTime::getDayOfMonth, TreeMap::new, Collectors.counting()))
                .forEach((dom, count) -> seriesByMonth.get(month).getData().add(new XYChart.Data<>(dom, count))));

        seriesByMonth.values().forEach(s -> chart.getData().add(s));
    }
}
