import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;
import util.RecordReader;

import java.io.IOException;

public class MainApplication extends Application {
    public static final String TITLE = "Graffiti Metrics";

    public static final int WIDTH = 640;
    public static final int HEIGHT = 480;

    public static void main(final String[] args) {
        launch(args);
    }

    @Override
    public void start(final Stage primaryStage) {
        // TODO(@ykako): Remove this. Just testing record reading.
        try {
            testRecordRead();
        } catch (IOException e) {
            e.printStackTrace();
        }

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

    // TODO(@ykako): Remove this. Just testing record reading.
    public void testRecordRead() throws IOException {
        RecordReader.readCrimeRecords(this.getClass().getResourceAsStream("crime.json"))
                .forEach(System.out::println);
    }
}
