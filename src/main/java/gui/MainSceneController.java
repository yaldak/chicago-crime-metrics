package gui;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.chart.BarChart;
import javafx.scene.chart.XYChart;
import javafx.scene.control.MenuBar;
import records.CrimeRecord;
import util.RecordReader;

import java.io.IOException;
import java.time.Month;
import java.util.Comparator;
import java.util.List;
import java.util.Objects;
import java.util.function.Function;
import java.util.stream.Collectors;

public class MainSceneController {
    @FXML
    private MenuBar menuBar;

    @FXML
    private BarChart<String, Number> crimesByMonthBarChart;

    @FXML
    public void initialize() {
        try {
            initializeCrimesByMonthBarChart();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @FXML
    private void onAboutMenuItemAction(final ActionEvent event) {
        System.out.println("About menu item clicked.");
    }

    private void initializeCrimesByMonthBarChart() throws IOException {
        XYChart.Series<String, Number> series = new XYChart.Series<>();

        crimesByMonthBarChart.setLegendVisible(false);
        crimesByMonthBarChart.getData().add(series);

        List<CrimeRecord> crimeRecords = RecordReader.readCrimeRecords();

        crimeRecords.parallelStream()
                .filter(r -> Objects.nonNull(r.date))
                .map(r -> r.date.getMonth())
                .collect(Collectors.groupingBy(Function.identity(), Collectors.counting()))
                .forEach((month, count) ->
                        series.getData().add(new XYChart.Data<>(month.toString(), count)));

        series.getData().sort(Comparator.comparing(d -> Month.valueOf(d.getXValue())));
    }
}
