package Game_Leave_me_out;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.scene.text.Text;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.List;

public class Controller {
    private Stage stage;
    private Parent root;
    private Scene scene;

    public void switchtoIntroduce(ActionEvent actionEvent) throws IOException {
        Parent root = FXMLLoader.load(MainScene.class.getResource("/fxml/Introduce.fxml"));
        stage = (Stage)((Node)actionEvent.getSource()).getScene().getWindow();
        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }

    public void switchtoMain(ActionEvent actionEvent) throws IOException {
        Parent root = FXMLLoader.load(MainScene.class.getResource("/fxml/MainScene.fxml"));
        stage = (Stage)((Node)actionEvent.getSource()).getScene().getWindow();
        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }

    @FXML
    Text wordExercise;

    public void Submit() throws IOException {
        RandomWord key = new RandomWord();
        String word = key.findRandomWord();
        wordExercise.setText(word);

    }

}
