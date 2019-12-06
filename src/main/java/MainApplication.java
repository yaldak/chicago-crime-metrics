import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.io.IOException;
import java.io.InputStream;

public class MainApplication extends Application {
    private static final String TITLE = "Crime Metrics";

    public static void main(final String[] args) {
        launch(args);
    }

    @Override
    public void start(final Stage primaryStage) throws IOException {
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(this.getClass().getResource("/fxml/MainScene.fxml"));

        VBox mainSceneLayoutRoot = loader.load();
        Scene mainScene = new Scene(mainSceneLayoutRoot);

        primaryStage.setScene(mainScene);
        primaryStage.setTitle(TITLE);
        primaryStage.show();
    }
}
