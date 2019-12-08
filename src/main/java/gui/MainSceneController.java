package gui;

import javafx.fxml.FXML;
import javafx.scene.chart.BarChart;
import javafx.scene.chart.XYChart;
import javafx.scene.control.MenuBar;
import data.JsonRecordReader;

import java.io.IOException;
import java.time.ZonedDateTime;
import java.util.List;
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
    private BarChart<String, Number> crimesByDayOfWeekBarChart;

    @FXML
    public void initialize() throws IOException {
        List<ZonedDateTime> crimeDates = JsonRecordReader.fromDefaultSet().readCrimeRecords().parallelStream()
                .map(r -> r.date)
                .filter(Objects::nonNull)
                .collect(Collectors.toList());

        initializeChart(crimesByMonthBarChart, crimeDates.parallelStream().map(ZonedDateTime::getMonth));
        initializeChart(crimesByDayOfMonthBarChart, crimeDates.parallelStream().map(ZonedDateTime::getDayOfMonth));
        initializeChart(crimesByDayOfWeekBarChart, crimeDates.parallelStream().map(ZonedDateTime::getDayOfWeek));
    }

    private static <T> void initializeChart(final BarChart<String, Number> chart, final Stream<T> recordStream) {
        XYChart.Series<String, Number> series = new XYChart.Series<>();

        recordStream
                .collect(Collectors.groupingBy(Function.identity(), TreeMap::new, Collectors.counting()))
                .forEach((e, c) -> series.getData().add(new XYChart.Data<>(e.toString(), c)));

        chart.setLegendVisible(false);
        chart.getData().add(series);
    }
}
