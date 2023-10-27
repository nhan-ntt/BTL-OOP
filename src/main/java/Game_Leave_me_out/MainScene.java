package Game_Leave_me_out;
import Game_Leave_me_out.TextController;
import javafx.scene.layout.*;
import javafx.scene.text.Text;

import commandLine.Trie;
import commandLine.Word;
import javafx.animation.RotateTransition;
import javafx.animation.TranslateTransition;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Pos;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.scene.control.Button;

import java.awt.*;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import javafx.geometry.Insets;
import javafx.util.Duration;


import static commandLine.Dictionary.listWord;
import static commandLine.DictionaryManagement.lookupWord;
import static java.awt.Color.white;
import static java.lang.Math.max;
import static java.lang.Math.min;
import static javafx.scene.paint.Color.WHITE;

import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.event.ActionEvent;
import javafx.util.Duration;
public class MainScene extends Application {
    private static final int WIDTH = 1280;
    private static final int HEIGHT = 800;
    private int letterDown= -1;
    private int numberDown= 0;
    private int score=0;
    private String word;
    Button SubmitButton = new Button("Submit");

    private HBox buttonContainer = new HBox();
    RandomWord randomWord = new RandomWord();
    List<Button> buttonList = new ArrayList<>();
    List<TextController> textControllerList = new ArrayList<>();
    TranslateTransition[] transitions;
    RotateTransition[] rotateTransition;
    boolean[] buttonClicked;
    String finalWord;
    private Text countdownText = new Text("10:00");

    private int countdownTime = 600; // Đếm ngược từ 10 phút (10 * 60 giây)
    private Timeline countdown;


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
    Trie listWord = new Trie();

    public void Text() {
        buttonList.clear();
        buttonContainer.getChildren().clear();
        numberDown=0;
        word = randomWord.WordAddLetter();

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
        //System.out.println(letterDown+" "+finalWord + "1");
        for (Button button:buttonList) {
            TextController textController = new TextController(button);
            textControllerList.add(textController);
        }
    }
    private void updateCountdown(ActionEvent event) {
        if (countdownTime > 0) {
            countdownTime--;
            updateCountdownText();
        } else {
            countdown.stop(); // Dừng đếm ngược khi hết thời gian
            handleTimeUp();
        }
    }

    private void updateCountdownText() {
        int minutes = countdownTime / 60;
        int seconds = countdownTime % 60;

        countdownText.setText("" + String.format("%02d:%02d", minutes, seconds));
    }

    private void handleTimeUp() {
        // Thực hiện hành động khi hết thời gian, ví dụ: kết thúc trò chơi
        // Ví dụ: hiển thị thông báo "Hết thời gian!"
    }

    // Tạo một hàm để khởi tạo lại giao diện
    public void initializeUI(Stage stage) {
        try {
            Text();
            Submit(stage);
            BorderPane root = new BorderPane();
            root.setCenter(buttonContainer);
            buttonContainer.setAlignment(Pos.CENTER);
            buttonContainer.setPadding(new Insets(0, 0, 85, 0));

            Scene scene = new Scene(root, WIDTH, HEIGHT);
            scene.getStylesheets().add(getClass().getResource("/fxml/decorate.css").toExternalForm());
            scene.getStylesheets().add(getClass().getResource("/fxml/TimeAndSubmit.css").toExternalForm());
            stage.setScene(scene);

            HBox countdownBox = new HBox();
            countdownBox.getStyleClass().add("time");
            countdownText.setFill(WHITE);
            countdownBox.getChildren().add(countdownText);

            HBox submitBox = new HBox();
            SubmitButton.getStyleClass().add("submit-button");
            submitBox.getChildren().add(SubmitButton);

            HBox bottomBox = new HBox();
            bottomBox.setPadding(new Insets(0, 40, 20, 0));
            bottomBox.setSpacing(900);
            bottomBox.setAlignment(Pos.BOTTOM_RIGHT);
            bottomBox.getChildren().addAll(countdownBox, submitBox);

            root.setBottom(bottomBox);
            stage.show();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    // Gọi hàm initializeUI sau mỗi lần Submit
    void Submit(Stage stage) {
        SubmitButton.setOnAction(event -> {
            if(numberDown==1) {
                finalWord="";
                //System.out.println(numberDown +" ");
                for(int i=0;i<word.length();i++)
                    if(i!=letterDown) finalWord+=word.charAt(i);
                System.out.println(finalWord);
                Text();
                // Đặt lại giao diện sau mỗi lần Submit
                initializeUI(stage);
            }
        });
    }

    public void start(Stage stage) throws IOException {
        // Khởi tạo giao diện ban đầu
        initializeUI(stage);
        countdown = new Timeline(new KeyFrame(Duration.seconds(1), this::updateCountdown));
        countdown.setCycleCount(Timeline.INDEFINITE);
        countdown.play();
    }

    public static void main(String[] args) {
        launch(args);
    }
    // Hàm tạo cơ cấu giao diện chung (commonLayout)

}