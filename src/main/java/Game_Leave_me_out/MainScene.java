package Game_Leave_me_out;
import Game_Leave_me_out.TextController;
import javafx.animation.RotateTransition;
import javafx.animation.TranslateTransition;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Pos;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.layout.Border;
import javafx.stage.Stage;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.Pane;
import javafx.scene.layout.HBox;
import javafx.scene.control.Button;

import java.awt.*;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import javafx.geometry.Insets;
import javafx.util.Duration;

import javax.xml.soap.Text;

import static java.awt.Color.white;

public class MainScene extends Application {
    private static final int WIDTH = 1280;
    private static final int HEIGHT = 800;
    private int letterDown= -1;
    private int numberDown= 0;
    Button SubmitButton = new Button("Submit");

    private HBox buttonContainer = new HBox();
    RandomWord randomWord = new RandomWord();
    List<Button> buttonList = new ArrayList<>();
    List<TextController> textControllerList = new ArrayList<>();
    TranslateTransition[] transitions;
    RotateTransition[] rotateTransition;
    boolean[] buttonClicked;

    public void Animation(int leng, int index, Button button, String colorText, double y, double x, double Angle) {
        button.setTranslateX(0);
        button.setTranslateY(0);
        button.setRotate(0);

        transitions[index] = new TranslateTransition(Duration.seconds(0.001), button);
        rotateTransition[index] = new RotateTransition(Duration.seconds(0.001), button);
        for (int j = index + 1; j < leng; j++) {
            {
                transitions[j].setToX(x);
                transitions[j].play();
            }
        }
        transitions[index].setToY(y); // Di chuyển nút xuống phía dưới
        transitions[index].play();
        button.setStyle(colorText);


        // Thêm hiệu ứng xoay nút
        rotateTransition[index].setByAngle(Angle);
        rotateTransition[index].play();
    }

    public void Text() {
        buttonList.clear();

        String word = randomWord.WordAddLetter();
        System.out.println(word);

        buttonClicked = new boolean[word.length()];
        transitions = new TranslateTransition[word.length()];
        rotateTransition = new RotateTransition[word.length()];
        buttonContainer = new HBox(word.length());

        for (int i = 0; i < word.length(); i++) {
            final int index = i;
            Button button = new Button("" + word.charAt(i));
            button.getStyleClass().add("letter");
            button.getStyleClass().add("custom-transparent-button");

            transitions[i] = new TranslateTransition(Duration.seconds(0.001), button);
            //TranslateTransition transition = new TranslateTransition(Duration.seconds(0.001), button);
            button.setOnAction(event -> {
                if (!buttonClicked[index]) {
                    if (numberDown == 0) {

                        Animation(word.length(), index, button, "-fx-text-fill: white", 375, -70, 290);
                        letterDown=index;
                        numberDown++;
                    }
                    else {

                        Animation(word.length(), index, button, "-fx-text-fill: white", 375, -70, 290);
                        buttonClicked[index] = true;


                        Button letterDownButton = buttonList.get(letterDown);
                        Animation(word.length(), letterDown, letterDownButton, "-fx-text-fill:#eb4d7d", 0, 0, 0);
                        buttonClicked[letterDown] = false;
                        letterDown=index;
                    }
                    buttonClicked[index] = true;
                } else if (buttonClicked[index]) {
                    Animation(word.length(), index, button, "-fx-text-fill:#eb4d7d", 0, 0, 0);
                    buttonClicked[index] = false;
                    letterDown=-1;
                    numberDown--;
                }
            });

            buttonList.add(button);
            buttonContainer.getChildren().add(button);
        }
        for (int i = 0; i < buttonList.size(); i++) {
            TextController textController = new TextController(buttonList.get(i));
            textControllerList.add(textController);
        }

    }
    void Submit(Stage stage) {
        SubmitButton.setOnAction(event -> {

            BorderPane root = new BorderPane();
            Text();

            Submit(stage);
            Scene scene = new Scene(root,WIDTH,HEIGHT);
            scene.getStylesheets().add(getClass().getResource("/fxml/decorate.css").toExternalForm());
            scene.getStylesheets().add(getClass().getResource("/fxml/TimeAndSubmit.css").toExternalForm());

            stage.setScene(scene);

            root.setCenter(buttonContainer);
            buttonContainer.setAlignment(Pos.CENTER);
            buttonContainer.setPadding(new Insets(0, 0, 85, 0));

            HBox submitBox = new HBox();
            SubmitButton.getStyleClass().add("submit-button");
            submitBox.getChildren().add(SubmitButton);

            // Đặt HBox ở vị trí bên dưới trái của BorderPane
            root.setBottom(submitBox);
            submitBox.setAlignment(Pos.BOTTOM_RIGHT);
            submitBox.setPadding(new Insets(0, 40, 25, 0));
        });
    }
    @Override
    public void start(Stage stage) throws IOException {
        try {
            BorderPane root = new BorderPane();
            Text();Submit(stage);
            Scene scene = new Scene(root,WIDTH,HEIGHT);
            scene.getStylesheets().add(getClass().getResource("/fxml/decorate.css").toExternalForm());
            scene.getStylesheets().add(getClass().getResource("/fxml/TimeAndSubmit.css").toExternalForm());

            stage.setScene(scene);

            root.setCenter(buttonContainer);
            buttonContainer.setAlignment(Pos.CENTER);
            buttonContainer.setPadding(new Insets(0, 0, 85, 0));

            HBox submitBox = new HBox();
            SubmitButton.getStyleClass().add("submit-button");
            submitBox.getChildren().add(SubmitButton);

            // Đặt HBox ở vị trí bên dưới trái của BorderPane
            root.setBottom(submitBox);
            submitBox.setAlignment(Pos.BOTTOM_RIGHT);
            submitBox.setPadding(new Insets(0, 40, 25, 0));

            stage.show();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    public static void main(String[] args) {
        launch(args);
    }

    // Hàm tạo cơ cấu giao diện chung (commonLayout)

}