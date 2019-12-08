package gui;

import javafx.fxml.FXML;
import javafx.scene.chart.BarChart;
import javafx.scene.chart.XYChart;
import javafx.scene.control.MenuBar;
import util.RecordReader;

import java.io.IOException;
import java.time.DayOfWeek;
import java.time.Month;
import java.time.ZonedDateTime;
import java.util.Comparator;
import java.util.List;
import java.util.Objects;
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
        // XXX: Is declaring a checked IOException safe here?

        List<ZonedDateTime> crimeRecordDates = RecordReader.readCrimeRecords().parallelStream()
                .filter(r -> Objects.nonNull(r.date))
                .map(r -> r.date)
                .collect(Collectors.toList());

        initializeChart(crimesByMonthBarChart, crimeRecordDates.parallelStream()
                .map(ZonedDateTime::getMonth), Comparator.comparing(k -> Month.valueOf(k)));

        initializeChart(crimesByDayOfMonthBarChart, crimeRecordDates.parallelStream()
                .map(ZonedDateTime::getDayOfMonth), Comparator.comparingInt(k -> Integer.parseInt(k)));

        initializeChart(crimesByDayOfWeekBarChart, crimeRecordDates.parallelStream()
                .map(ZonedDateTime::getDayOfWeek), Comparator.comparing(k -> DayOfWeek.valueOf(k)));
    }

    private static <T> void initializeChart(final BarChart<String, Number> chart, final Stream<T> recordStream,
            final Comparator<String> keyComparator) {
        chart.setLegendVisible(false);
        chart.getData().add(groupAndCountSeries(recordStream, keyComparator));
    }

    private static <T> XYChart.Series<String, Number> groupAndCountSeries(final Stream<T> recordStream,
            final Comparator<String> keyComparator) {
        XYChart.Series<String, Number> series = new XYChart.Series<>();

        recordStream
                .collect(Collectors.groupingBy(Function.identity(), Collectors.counting()))
                .forEach((e, count) ->
                        series.getData().add(new XYChart.Data<>(e.toString(), count)));

        series.getData().sort((d1, d2) -> keyComparator.compare(d1.getXValue(), d2.getXValue()));

        return series;
    }
}
