package Game;

import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.stage.Stage;

import java.io.IOException;

import static commandLine.DictionaryManagement.insertFromFileDICT;

public class Introduce extends Application {
    private static final int WIDTH = 1000;
    private static final int HEIGHT = 600;
    Media sound = new Media(getClass().getResource("/fxml/Sound/Duck.mp3").toString());

    Button StartButton = new Button("Start");
    MediaPlayer mediaPlayer = new MediaPlayer(sound);

    void Music() {

        // Khởi tạo đối tượng MediaPlayer với đối tượng Media
        mediaPlayer.setCycleCount(MediaPlayer.INDEFINITE);

        // Phát nhạc khi game bắt đầu
        mediaPlayer.play();

    }
    @Override
    public void start(Stage stage) throws IOException {
        Music();
        BorderPane root = new BorderPane();
        Scene scene = new Scene(root, WIDTH,HEIGHT);
        scene.getStylesheets().add(getClass().getResource("/fxml/CSS/introduce.css").toExternalForm());

        HBox startBox = new HBox();
        startBox.setPadding(new Insets(0, 0, 187, 850));
        StartButton.getStyleClass().add("transparent-button");
        startBox.getChildren().add(StartButton);
        root.setBottom(startBox);

        StartButton.setOnAction(event -> {
            // Khởi chạy lớp MainScene khi nút "Start" được bấm
            MainScene mainScene = new MainScene();
            try {
                mainScene.start(stage);
                mediaPlayer.stop();
            } catch (IOException e) {
                e.printStackTrace();
            }
        });

        stage.setScene(scene);
        stage.show();
    }

    public static void main(String[] args) {

        insertFromFileDICT();
        launch();
    }
}