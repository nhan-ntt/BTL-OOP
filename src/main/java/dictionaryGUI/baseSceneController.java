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
        homeBtn.setDefaultButton(true);

        homeBtn.setOnAction(e -> {
            showComponent("/fxml/dictionaryGUI/homeScene.fxml");
            resetBtnStyle();
            homeBtn.setStyle("-fx-background-color: #687EDA;");
        });

        searchBtn.setOnAction(e -> {
           showComponent("/fxml/dictionaryGUI/searchScene.fxml");
           resetBtnStyle();
           searchBtn.setStyle("-fx-background-color: #687EDA;");
        });

        favBtn.setOnAction(e -> {
            showComponent("/fxml/dictionaryGUI/favScene.fxml");
            resetBtnStyle();
            favBtn.setStyle("-fx-background-color: #687EDA;");
        });

        addBtn.setOnAction(e -> {
            showComponent("/fxml/dictionaryGUI/addScene.fxml");
            resetBtnStyle();
            addBtn.setStyle("-fx-background-color: #687EDA;");
        });

        transBtn.setOnAction(e -> {
            showComponent("/fxml/dictionaryGUI/transScene.fxml");
            resetBtnStyle();
            transBtn.setStyle("-fx-background-color: #687EDA;");
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
            resetBtnStyle();
            gameBtn.setStyle("-fx-background-color: #687EDA;");
        });


        exitBtn.setOnMouseClicked(e -> {
            System.exit(0);
        });
//
////        tooltip1.setShowDelay(Duration.seconds(0.5));
////        tooltip2.setShowDelay(Duration.seconds(0.5));
////        tooltip3.setShowDelay(Duration.seconds(0.5));

    }

    private void resetBtnStyle() {
        homeBtn.setStyle("");
        favBtn.setStyle("");
        addBtn.setStyle("");
        searchBtn.setStyle("");
        transBtn.setStyle("");
        gameBtn.setStyle("");
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
    private Button homeBtn, favBtn, addBtn, searchBtn, transBtn, gameBtn, exitBtn;

    @FXML
    private AnchorPane container;
    @FXML
    private Tooltip searchT, favT, addT, transT, homeT, gameT, exitT;
}
