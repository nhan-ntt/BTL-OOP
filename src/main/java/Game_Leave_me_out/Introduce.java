package Game_Leave_me_out;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.stage.Stage;

import java.io.IOException;

public class Introduce extends Application {
    private static final int WIDTH = 1280;
    private static final int HEIGHT = 800;
    Button StartButton = new Button("Start");

    @Override
    public void start(Stage stage) throws IOException {
        BorderPane root = new BorderPane();
        Scene scene = new Scene(root, WIDTH,HEIGHT);
        scene.getStylesheets().add(getClass().getResource("/fxml/introduce.css").toExternalForm());

        HBox startBox = new HBox();
        startBox.setPadding(new Insets(0, 0, 247, 1100));
        StartButton.getStyleClass().add("transparent-button");
        startBox.getChildren().add(StartButton);
        root.setBottom(startBox);

        StartButton.setOnAction(event -> {
            // Khởi chạy lớp MainScene khi nút "Start" được bấm
            MainScene mainScene = new MainScene();
            try {
                mainScene.start(stage);
            } catch (IOException e) {
                e.printStackTrace();
            }
        });

        stage.setScene(scene);
        stage.show();
    }

    public static void main(String[] args) {
        launch();
    }
}