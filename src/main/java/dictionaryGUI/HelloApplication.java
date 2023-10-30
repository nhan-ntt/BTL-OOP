package dictionaryGUI;

import javafx.application.Application;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.event.ActionEvent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import javafx.util.Duration;

public class HelloApplication extends Application {
    private static final int WIDTH = 800;
    private static final int HEIGHT = 600;
    private int countdownTime = 60;
    private int elapsedTime = 0; // Thời gian đã trôi qua
    private Timeline countdown;
    private Text countdownText = new Text("Time: 01:00");
    private Text elapsedTimeText = new Text("Elapsed Time: 00:00");
    private boolean gameRunning = true;

    @Override
    public void start(Stage primaryStage) {
        BorderPane root = new BorderPane();

        Button gameOverButton = new Button("Game Over");
        StackPane gameOverPane = new StackPane(gameOverButton);

        HBox hbox = new HBox();
        hbox.getChildren().addAll(countdownText, elapsedTimeText);
        root.setTop(hbox);
        root.setCenter(gameOverPane);

        Scene scene = new Scene(root, WIDTH, HEIGHT);
        primaryStage.setScene(scene);
        primaryStage.setTitle("Countdown Timer");
        primaryStage.show();

        countdown = new Timeline(new KeyFrame(Duration.seconds(1), this::updateCountdown));
        countdown.setCycleCount(Timeline.INDEFINITE);
        countdown.play();

        gameOverButton.setOnAction(event -> {
            gameRunning = false;
            showGameOverScreen(primaryStage);
        });
    }

    private void updateCountdown(ActionEvent event) {
        if (countdownTime > 0) {
            countdownTime--;
            elapsedTime++;
            updateTime(countdownText, countdownTime);
            updateTime(elapsedTimeText, elapsedTime);
        } else if (gameRunning) {
            countdown.stop();
            showGameOverScreen((Stage) countdownText.getScene().getWindow());
        }
    }

    private void updateTime(Text text, int time) {
        int minutes = time / 60;
        int seconds = time % 60;
        text.setText("Time: " + String.format("%02d:%02d", minutes, seconds));
    }

    private void showGameOverScreen(Stage primaryStage) {
        StackPane gameOverPane = new StackPane();
        Text gameOverText = new Text("Game Over!");
        gameOverText.setStyle("-fx-font-size: 48px;");
        Text elapsedTimeText = new Text("Elapsed Time: " + elapsedTime + " seconds");
        elapsedTimeText.setStyle("-fx-font-size: 24px;");
        StackPane.setAlignment(elapsedTimeText, javafx.geometry.Pos.TOP_CENTER);
        gameOverPane.getChildren().addAll(gameOverText, elapsedTimeText);
        Scene gameOverScene = new Scene(gameOverPane, WIDTH, HEIGHT);
        primaryStage.setScene(gameOverScene);
    }

    public static void main(String[] args) {
        launch(args);
    }
}
