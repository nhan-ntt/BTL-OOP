package dictionaryGUI;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;

import java.io.IOException;

public class baseController {


    @FXML
    private BorderPane borderPane = new BorderPane();
    @FXML
    private AnchorPane buttonRow;

    @FXML
    private Button homeBtn, searchBtn, favBtn, transBtn, gameBtn, exitBtn;

    private Button x = new Button();

    public void exit(MouseEvent mouseEvent) {
        System.exit(0);
    }

    public void home(MouseEvent mouseEvent) {
        showComponent("/fxml/dictionaryGUI/favScene.fxml");
    }


    @FXML
    private void showComponent(String path) {
        try {
            AnchorPane component = FXMLLoader.load(getClass().getResource(path));
            borderPane.setCenter(component);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
