import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;

import java.io.InputStreamReader;

public class MainApplication extends Application {
    public static final String TITLE = "Graffiti Metrics";

    public static final int WIDTH = 640;
    public static final int HEIGHT = 480;

    public static void main(final String[] args) {
        launch(args);
    }

    @Override
    public void start(final Stage primaryStage) {
        // TODO(@ykako): Remove this. Just testing CSV reader.
        testCsvSpliterator();

        StackPane root = new StackPane();

        // TODO(@ykako): Set up a little button for now
        Button btn = new Button();
        btn.setText("Say 'Hello World'");
        btn.setOnAction(event -> System.out.println("Hello World!"));
        root.getChildren().add(btn);

        primaryStage.setScene(new Scene(root, WIDTH, HEIGHT));
        primaryStage.setTitle(TITLE);
        primaryStage.show();
    }

    // TODO(@ykako): Remove this. Just testing CSV reader.
    public void testCsvSpliterator() {
        // XXX: getResource NPE
        InputStreamReader reader = new InputStreamReader(this.getClass().getResourceAsStream("csv-test.csv"));
                // new InputStreamReader(this.getClass().getResourceAsStream("graffiti-removal_11-16-2019.csv"));

        CsvSpliterator.stream(reader, true)
                .map(CsvSpliterator.CsvRecord::getValues)
                .forEach(System.out::println);
    }
}
