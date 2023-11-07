package Game;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import javafx.stage.Stage;

import java.io.IOException;

import static javafx.scene.paint.Color.TRANSPARENT;
import static javafx.scene.paint.Color.WHITE;

public class UIManager {
    public static final int WIDTH = 1000;
    public static final int HEIGHT = 600;
    private Stage stage = new Stage();
    private GameManager gameManager = new GameManager();
    TimeController timeController = new TimeController("1:00",60);

    Button SubmitButton = new Button("Submit");
    Button sound=new Button("/");
    public UIManager(Stage stage, GameManager gameManager,TimeController timeController) {
        this.stage = stage;
        this.gameManager = gameManager;
        this.timeController = timeController;
    }
    private Text Score =  new Text("Score: 0");
    private Text Question =  new Text("Question: 0/"+gameManager.getNumberQuestion());

    private Text WrongAnswer =  new Text("Wrong Answer: 0");
    void GameOver(BorderPane root, String style) {
        gameManager.setEndGame(true);
        // Tạo một StackPane để chứa hình ảnh và văn bản
        StackPane stackPane = new StackPane();

        // Tạo ImageView cho hình ảnh overlay
        ImageView GameOverImage = new ImageView(new Image(getClass().getResourceAsStream("/Image/GameOver.png")));
        GameOverImage.setFitWidth(WIDTH / 1.85);
        GameOverImage.setFitHeight(HEIGHT / 1.5);

        // Tạo một văn bản
        Text gameOverText = new Text("Congratulations!");
        gameOverText.getStyleClass().add("GameOver");
        gameOverText.getStyleClass().add("Congratulations"); // Đặt lớp CSS cho văn bản nếu cần
      //  gameOverText.setTranslateX(-130);
      //  gameOverText.setTranslateY(-100);

        VBox GameOverBox = new VBox();
        GameOverBox.setSpacing(20);

        Text FinalScore = new Text("Score: " + gameManager.getScore() + " points");
        FinalScore.getStyleClass().add("GameOver");
        FinalScore.setTranslateX(40);


        timeController.getTotalTime().getStyleClass().add("time");
        timeController.getTotalTime().setFill(WHITE);
        timeController.getTotalTime().setTranslateX(70);
        timeController.getTotalTime().setTranslateY(10);
        GameOverBox.getChildren().addAll(gameOverText, FinalScore, timeController.getTotalTime());

        GameOverBox.setPadding(new Insets(160, 0, -450, 390));

        // Thêm hình ảnh và văn bản vào StackPane
        stackPane.getChildren().addAll(GameOverImage, GameOverBox);

        // Đặt StackPane vào trung tâm của BorderPane
        root.setCenter(stackPane);
        PlayAgain();
    }
    void PlayAgain() {
        SubmitButton.setText("Replay");
        SubmitButton.setOnAction(event -> {
            Introduce introduce = new Introduce();
            try {
                introduce.start(stage);
                gameManager.soundManager.stopSoundEffect();
            } catch (IOException e) {
                e.printStackTrace();
            }
        });
    }
    private void InformationFunc(BorderPane root,String style)
    {
        HBox infoBox = new HBox();

        if (gameManager.soundManager.isSoundEnabled()) sound.setTextFill(TRANSPARENT); else sound.setTextFill(WHITE);
        sound.getStyleClass().add("sound");

        sound.setTranslateX(WIDTH-380);
        sound.setTranslateY(-30);

        VBox mainInfoBox = new VBox();
        Score.setText("Score: " + gameManager.getScore());
        Score.getStyleClass().add(style);
        Score.setFill(WHITE);
        Question.setText("Question: " + gameManager.getQuestion() + "/" + gameManager.getNumberQuestion());
        Question.getStyleClass().add(style);
        Question.setFill(WHITE);
        WrongAnswer.getStyleClass().add(style);
        WrongAnswer.setText("Wrong Answers: " + gameManager.getWrongAnswer());
        WrongAnswer.setFill(WHITE);

        mainInfoBox.getChildren().addAll(Score,Question,WrongAnswer);

        ImageView scoreImage = new ImageView(new Image(getClass().getResourceAsStream("/Image/avatar.jpg")));
        scoreImage.setFitWidth(80);
        scoreImage.setFitHeight(80);

        infoBox.getChildren().addAll(scoreImage, mainInfoBox,sound);
        infoBox.setSpacing(10);
        root.setTop(infoBox);
        infoBox.setPadding(new Insets(20, 0, 0, 20));

    }
    void LetterFunc(BorderPane root) {
        root.setCenter(gameManager.textAnimation.getButtonContainer());
        gameManager.textAnimation.getButtonContainer().setAlignment(Pos.CENTER);
        gameManager.textAnimation.getButtonContainer().setPadding(new Insets(0, 0, 150, 0));
    }
    void SubmitAndTime(BorderPane root) {
        HBox countdownBox = new HBox();
        countdownBox.getStyleClass().add("time");
        timeController.getCountdownText().setFill(WHITE);
        countdownBox.getChildren().add( timeController.getCountdownText());

        HBox submitBox = new HBox();
        SubmitButton.getStyleClass().add("submit-button");
        submitBox.getChildren().add(SubmitButton);

        HBox bottomBox = new HBox();
        bottomBox.setPadding(new Insets(0, 23, 12, 0));
        bottomBox.setSpacing(690);
        bottomBox.setAlignment(Pos.BOTTOM_RIGHT);
        bottomBox.getChildren().addAll(countdownBox, submitBox);
        root.setBottom(bottomBox);
    }
    void Submit() {
        SubmitButton.setOnAction(event -> {
            gameManager.checkSubmit();
            initializeUI(stage);
        });
    }
    void checkSound() {
        sound.setOnAction(event -> {
           gameManager.checkSound(sound);
           initializeUI(stage);
        });
    }
    boolean checkEndGame() {
        return gameManager.isEndGame();
    }
    void EndTime() {
            gameManager.setEndGame(true);
            initializeUI(stage);
            return;
    }
    public void initializeUI(Stage stage) {
        try {
            Submit();
            checkSound();
            BorderPane root = new BorderPane();
            Scene scene = new Scene(root, WIDTH, HEIGHT);

            scene.getStylesheets().add(getClass().getResource("/fxml/Game/decorate.css").toExternalForm());
            scene.getStylesheets().add(getClass().getResource("/fxml/Game/TimeAndSubmit.css").toExternalForm());
            scene.getStylesheets().add(getClass().getResource("/fxml/Game/Inform.css").toExternalForm());
            scene.getStylesheets().add(getClass().getResource("/fxml/Game/GameOver.css").toExternalForm());
            stage.setScene(scene);

            LetterFunc(root);
            SubmitAndTime(root);
            InformationFunc(root, "info-text");
            if (gameManager.getQuestion() == gameManager.getNumberQuestion() || gameManager.isEndGame())
            {
                GameOver(root, "GameOver");
                return;
            }
            stage.show();


        }catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void showUI() {
        //stage.show();
    }
}
