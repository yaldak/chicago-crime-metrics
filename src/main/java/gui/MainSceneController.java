package gui;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.chart.BarChart;
import javafx.scene.chart.XYChart;
import javafx.scene.control.MenuBar;
import records.CrimeRecord;
import util.RecordReader;

import java.io.IOException;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

public class MainSceneController {
    @FXML
    private MenuBar menuBar;

    @FXML
    private BarChart<String, Number> crimesPerMonthBarChart;

    @FXML
    public void initialize() {
        try {
            generateBarChartData().forEach(series -> crimesPerMonthBarChart.getData().add(series));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @FXML
    private void onAboutMenuItemAction(final ActionEvent event) {
        System.out.println("About menu item clicked.");
    }

    private List<XYChart.Series<String, Number>> generateBarChartData() throws IOException {
        List<CrimeRecord> crimeRecords = RecordReader.readCrimeRecords();

        // XXX Debugging
        crimeRecords.parallelStream()
                .limit(30)
                .forEach(System.out::println);

        List<String> distinctMonths = crimeRecords.parallelStream()
                .filter(r -> Objects.nonNull(r.date))
                .map(r -> r.date.getMonth().toString())
                .distinct()
                .sorted()
                .collect(Collectors.toList());

        // XXX This is slow
        return distinctMonths.stream()
                .map(month -> {
                    XYChart.Series<String, Number> series = new XYChart.Series<>();

                    long crimesInMonth = crimeRecords.parallelStream()
                            .filter(r -> Objects.nonNull(r.date) && r.date.getMonth().toString().equals(month))
                            .count();

                    series.getData().add(new XYChart.Data<>(month, crimesInMonth));

                    return series;
                })
                .collect(Collectors.toList());

        /*
        final String austria = "Austria";
        final String brazil = "Brazil";
        final String france = "France";
        final String italy = "Italy";
        final String usa = "USA";

        XYChart.Series series1 = new XYChart.Series();
        series1.setName("2003");
        series1.getData().add(new XYChart.Data(austria, 25601.34));
        series1.getData().add(new XYChart.Data(brazil, 20148.82));
        series1.getData().add(new XYChart.Data(france, 10000));
        series1.getData().add(new XYChart.Data(italy, 35407.15));
        series1.getData().add(new XYChart.Data(usa, 12000));

        XYChart.Series series2 = new XYChart.Series();
        series2.setName("2004");
        series2.getData().add(new XYChart.Data(austria, 57401.85));
        series2.getData().add(new XYChart.Data(brazil, 41941.19));
        series2.getData().add(new XYChart.Data(france, 45263.37));
        series2.getData().add(new XYChart.Data(italy, 117320.16));
        series2.getData().add(new XYChart.Data(usa, 14845.27));

        XYChart.Series series3 = new XYChart.Series();
        series3.setName("2005");
        series3.getData().add(new XYChart.Data(austria, 45000.65));
        series3.getData().add(new XYChart.Data(brazil, 44835.76));
        series3.getData().add(new XYChart.Data(france, 18722.18));
        series3.getData().add(new XYChart.Data(italy, 17557.31));
        series3.getData().add(new XYChart.Data(usa, 92633.68));

        return List.of(series1, series2, series3);*/
    }
}
