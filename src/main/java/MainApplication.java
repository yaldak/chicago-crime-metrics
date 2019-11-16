import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;

public class MainApplication extends Application {
    public static final String TITLE = "Graffiti Metrics";

    public static final int WIDTH = 640;
    public static final int HEIGHT = 480;

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) {
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
}
