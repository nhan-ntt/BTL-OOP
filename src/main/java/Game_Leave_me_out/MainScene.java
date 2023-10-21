package Game_Leave_me_out;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.text.Font;
import javafx.stage.Stage;

import java.io.IOException;

public class MainScene extends Application {
    private static final int WIDTH = 977;
    private static final int HEIGHT = 706;
    @Override
    public void start(Stage stage) throws IOException {
        try {
            Font customFont = Font.loadFont(getClass().getResourceAsStream("/HoboBold.ttf"), 14);

            Parent root = FXMLLoader.load(MainScene.class.getResource("/fxml/Introduce.fxml"));
            Scene scene = new Scene(root);
            stage.setTitle("hello");
            stage.setScene(scene);
            stage.show();
        } catch(Exception e) {
        e.printStackTrace();
    }
    }

    public static void main(String[] args) {
        launch(args);
    }
}