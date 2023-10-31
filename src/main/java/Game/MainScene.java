package Game;

import Game.GameManager;
import Game.UIManager;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;
import javafx.scene.control.Button;
import javafx.scene.text.Text;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.util.Duration;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.event.ActionEvent;

import java.io.IOException;

import static commandLine.DictionaryManagement.insertFromFileDICT;

public class MainScene extends Application {
    private GameManager gameManager;
    private UIManager uiManager;

    public void start(Stage stage) throws IOException {


        gameManager = new GameManager();
        gameManager.initializeGame();

        TimeController timeController = new TimeController("1:00",60);

        uiManager = new UIManager(stage, gameManager,timeController);
        timeController.startTimer(uiManager);

        uiManager.initializeUI(stage);
    }

    public static void main(String[] args) {
        insertFromFileDICT();
        launch(args);
    }
}
