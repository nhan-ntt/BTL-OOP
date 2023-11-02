package dictionaryGUI;

import Game.Introduce;
import Game.MainScene;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Tooltip;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import javafx.util.Duration;

import java.io.IOException;
import java.net.URL;
import java.util.Objects;
import java.util.ResourceBundle;

public class baseSceneController implements Initializable {
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        showComponent("/fxml/dictionaryGUI/homeScene.fxml");

        searchBtn.setOnAction(e -> {
           showComponent("/fxml/dictionaryGUI/searchScene.fxml");
        });

        favBtn.setOnAction(e -> {
            showComponent("/fxml/dictionaryGUI/favScene.fxml");
        });

        addBtn.setOnAction(e -> {
            showComponent("/fxml/dictionaryGUI/addScene.fxml");
        });

        transBtn.setOnAction(e -> {
            showComponent("/fxml/dictionaryGUI/transScene.fxml");
        });

        homeBtn.setOnAction(e -> {
            showComponent("/fxml/dictionaryGUI/homeScene.fxml");
        });

        gameBtn.setOnAction(e -> {
            Introduce introduce = new Introduce();
            try {
                Stage newStage = new Stage();
                introduce.start(newStage);
                newStage.setOnCloseRequest(event -> {
                    introduce.mediaPlayer.stop();
                });
//                introduce.mediaPlayer.stop();
            } catch (IOException ex) {
                ex.printStackTrace();
            }
        });


        exitBtn.setOnMouseClicked(e -> {
            System.exit(0);
        });
//
////        tooltip1.setShowDelay(Duration.seconds(0.5));
////        tooltip2.setShowDelay(Duration.seconds(0.5));
////        tooltip3.setShowDelay(Duration.seconds(0.5));

    }

    private void setNode(Node node) {
        container.getChildren().clear();
        container.getChildren().add(node);
    }

    @FXML
    private void showComponent(String path) {
        try {
            AnchorPane component = FXMLLoader.load(getClass().getResource(path));
            setNode(component);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    @FXML
    private Button searchBtn, favBtn, addBtn, transBtn, homeBtn, gameBtn, exitBtn;

    @FXML
    private AnchorPane container;
}
