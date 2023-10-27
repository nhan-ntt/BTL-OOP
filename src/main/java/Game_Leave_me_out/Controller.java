package Game_Leave_me_out;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.input.MouseEvent;
import javafx.scene.text.Text;
import javafx.stage.Stage;

import java.io.IOException;

public class Controller {
    @FXML
    private Button startButton; // hoặc @FXML private ImageView clickableImage;
    private Stage stage;
    public void initialize(Stage stage) {
        this.stage = stage;

        // Gán sự kiện khi chuột được click
        startButton.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                openMainScene(event);
            }
        });

    }

    public void openMainScene(MouseEvent event) {
        try {
            MainScene mainScene = new MainScene();
            mainScene.start(stage);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    @FXML
    Text wordExercise;

    public void Submit() throws IOException {
        RandomWord key = new RandomWord();
        String word = key.WordAddLetter();
        wordExercise.setText(word);

    }
}
