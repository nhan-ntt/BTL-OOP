package Game_Leave_me_out;
import Game_Leave_me_out.TextController;
import javafx.scene.layout.*;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.scene.image.Image;
import commandLine.Trie;
import javafx.scene.text.Font;
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
import javafx.scene.image.ImageView;
import java.awt.*;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.geometry.Insets;
import javafx.util.Duration;


import static commandLine.Dictionary.listWord;
import static commandLine.DictionaryManagement.insertFromFileDICT;
import static commandLine.DictionaryManagement.lookupWord;
import static java.awt.Color.white;
import static java.lang.Math.max;
import static java.lang.Math.min;
import static javafx.scene.paint.Color.*;

import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.event.ActionEvent;
import javafx.util.Duration;
public class MainScene extends Application {
    private Stage stage;
    boolean isSoundEnabled =true;

    private static final int WIDTH = 1280;
    private static final int HEIGHT = 800;
    private int letterDown= -1;
    private boolean EndGame =false;
    private int numberDown= 0;
    private int score=0;
    private int question=0;
    int wrongAnswer;
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
    private Text countdownText = new Text("1:00");

    private int countdownTime = 60; // Đếm ngược từ 10 phút (10 * 60 giây)
    private int elapsedTime = 0;

    private Timeline countdown;
    private Text Score =  new Text("Score: 0");
    private int numberQuestion = 10;

    private Text Question =  new Text("Question: 0/"+numberQuestion);

    private Text WrongAnswer =  new Text("Wrong Answer: 0");
    Text totalTime = new Text();
    Media sound = new Media(getClass().getResource("/fxml/Duck.mp3").toString());
    MediaPlayer mediaPlayer = new MediaPlayer(sound);

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
            button.setOnAction(event -> {
                if (!buttonClicked[index]) {
                    if (numberDown == 0) {
                        Animation(word.length(), index, button, "-fx-text-fill: white", 365, -70, 290);
                        letterDown=index;
                        numberDown++;
                    }
                    else {

                        Animation(word.length(), index, button, "-fx-text-fill: white", 365, -70, 290);
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
        if (countdownTime > 0 && !EndGame) {
            countdownTime--;
            elapsedTime++;
            updateTime(totalTime,elapsedTime);
            updateTime(countdownText,countdownTime);
        } else {
            EndGame = true;
            countdown.stop();
            initializeUI(stage);
            // Dừng đếm ngược khi hết thời gian
        }
    }

    private void updateTime(Text text,int time) {
        int minutes = time / 60;
        int seconds = time % 60;

        text.setText("" + String.format("%02d:%02d", minutes, seconds));
    }
    void Music() {
        if (isSoundEnabled) {
            mediaPlayer.setCycleCount(MediaPlayer.INDEFINITE);
            mediaPlayer.play();
        }
    }

    void checkSound(Button sound) {

        sound.setOnAction(event -> {
            if (isSoundEnabled) {
                mediaPlayer.stop(); // Dừng phát âm thanh
                isSoundEnabled = false; // Đặt biến kiểm soát thành false
                sound.setTextFill(WHITE);

            } else {
                mediaPlayer.play(); // Phát âm thanh
                isSoundEnabled = true; // Đặt biến kiểm soát thành true
                sound.setTextFill(TRANSPARENT);
            }
        });
    }

    private void InformationFunc(BorderPane root,String style)
    {
        HBox infoBox = new HBox();

        Button sound = new Button("/");
        sound.setTextFill(TRANSPARENT);
        checkSound(sound);
        sound.setTranslateX(837);
        sound.setTranslateY(-30);
        sound.getStyleClass().add("sound");

        VBox mainInfoBox = new VBox();
        Score.getStyleClass().add(style);
        Score.setFill(WHITE);
        Question.getStyleClass().add(style);
        Question.setFill(WHITE);
        WrongAnswer.getStyleClass().add(style);
        WrongAnswer.setFill(WHITE);
        mainInfoBox.getChildren().addAll(Score,Question,WrongAnswer);

        ImageView scoreImage = new ImageView(new Image(getClass().getResourceAsStream("/fxml/avatar.jpg")));
        scoreImage.setFitWidth(100);
        scoreImage.setFitHeight(100);

        infoBox.getChildren().addAll(scoreImage, mainInfoBox,sound);
        infoBox.setSpacing(10);
        root.setTop(infoBox);
        infoBox.setPadding(new Insets(20, 0, 0, 20));

    }
    void SubmitAndTime(BorderPane root) {
        HBox countdownBox = new HBox();
        countdownBox.getStyleClass().add("time");
        countdownText.setFill(WHITE);
        countdownBox.getChildren().add(countdownText);

        HBox submitBox = new HBox();
        SubmitButton.getStyleClass().add("submit-button");
        submitBox.getChildren().add(SubmitButton);

        HBox bottomBox = new HBox();
        bottomBox.setPadding(new Insets(0, 40, 20, 0));
        bottomBox.setSpacing(890);
        bottomBox.setAlignment(Pos.BOTTOM_RIGHT);
        bottomBox.getChildren().addAll(countdownBox, submitBox);
        root.setBottom(bottomBox);
    }
    void LetterFunc(BorderPane root) {
        root.setCenter(buttonContainer);
        buttonContainer.setAlignment(Pos.CENTER);
        buttonContainer.setPadding(new Insets(0, 0, 150, 0));

    }
    void GameOver(BorderPane root, String style) {
        EndGame =true;
        // Tạo một StackPane để chứa hình ảnh và văn bản
        StackPane stackPane = new StackPane();

        // Tạo ImageView cho hình ảnh overlay
        ImageView GameOverImage = new ImageView(new Image(getClass().getResourceAsStream("/fxml/GameOver.png")));
        GameOverImage.setFitWidth(WIDTH / 1.75);
        GameOverImage.setFitHeight(HEIGHT / 1.5);

        // Tạo một văn bản
        Text gameOverText = new Text("Congratulations!");
        gameOverText.getStyleClass().add("GameOver");
        gameOverText.getStyleClass().add("Congratulations"); // Đặt lớp CSS cho văn bản nếu cần
        gameOverText.setTranslateX(-40);

        VBox GameOverBox = new VBox();
        GameOverBox.setSpacing(20);

        Text FinalScore = new Text("Score: " + score + " points");
        FinalScore.getStyleClass().add("GameOver");
        updateTime(totalTime, elapsedTime);

        totalTime.getStyleClass().add("time");
        totalTime.setFill(WHITE);
        totalTime.setTranslateX(40);
        totalTime.setTranslateY(10);
        GameOverBox.getChildren().addAll(gameOverText, FinalScore, totalTime);

        GameOverBox.setPadding(new Insets(250, 0, 0, 550));

        // Thêm hình ảnh và văn bản vào StackPane
        stackPane.getChildren().addAll(GameOverImage, GameOverBox);

        // Đặt StackPane vào trung tâm của BorderPane
        root.setCenter(stackPane);
    }




    // Tạo một hàm để khởi tạo lại giao diện
    public void initializeUI(Stage stage) {
        try {
            Text();
            Submit(stage);
            BorderPane root = new BorderPane();

            Scene scene = new Scene(root, WIDTH, HEIGHT);
            scene.getStylesheets().add(getClass().getResource("/fxml/decorate.css").toExternalForm());
            scene.getStylesheets().add(getClass().getResource("/fxml/TimeAndSubmit.css").toExternalForm());
            scene.getStylesheets().add(getClass().getResource("/fxml/Inform.css").toExternalForm());
            scene.getStylesheets().add(getClass().getResource("/fxml/GameOver.css").toExternalForm());
            stage.setScene(scene);

            LetterFunc(root);
            SubmitAndTime(root);
            InformationFunc(root,"info-text");
            if(question == numberQuestion || EndGame)  GameOver(root,"GameOver");
            stage.show();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    void updateMainInfo() {
        finalWord = "";
        for (int i = 0; i < word.length(); i++)
            if (i != letterDown) finalWord += word.charAt(i);
        Word wordSearch = lookupWord(finalWord);
        if (wordSearch != null) {
            score += 10;
        }
        else wrongAnswer++;
        question++;
        Score.setText("Score: " + score);
        Question.setText("Question: " + question + "/" + numberQuestion);
        WrongAnswer.setText("Wrong Answers: " + wrongAnswer);
    }
    void Submit(Stage stage) {
        SubmitButton.setOnAction(event -> {
            if (!EndGame && numberDown == 1) {
                updateMainInfo();
                Text();
                initializeUI(stage);
            }

        });
    }

    public void start(Stage stage) throws IOException {
        initializeUI(stage);
        Music();
        this.stage=stage;
        countdown = new Timeline(new KeyFrame(Duration.seconds(1), this::updateCountdown));
        countdown.setCycleCount(Timeline.INDEFINITE);
        countdown.play();
    }

    public static void main(String[] args) {
        insertFromFileDICT();
        launch(args);
    }
}