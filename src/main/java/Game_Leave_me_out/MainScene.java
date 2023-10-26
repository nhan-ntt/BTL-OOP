package Game_Leave_me_out;
import Game_Leave_me_out.TextController;
import javafx.animation.RotateTransition;
import javafx.animation.TranslateTransition;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Pos;
import javafx.scene.Parent;
import javafx.scene.Scene;
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
    private BorderPane commonLayout = new BorderPane();
    private int number= 0;


    @Override
    public void start(Stage stage) throws IOException {
        try {
            // Tạo cơ cấu giao diện chung với các nút bên dưới

           // Parent root = FXMLLoader.load(MainScene.class.getResource("/fxml/MainScene.fxml"));


            RandomWord key = new RandomWord();
            String word = addletter(key.findRandomWord());


            HBox buttonContainer = new HBox(word.length());

            // buttonContainer.setPadding(new Insets(180,30, 150,100));

            // buttonContainer.setPadding(new Insets(90));

            List<Button> buttonList = new ArrayList<>();
            List<TextController> textControllerList = new ArrayList<>();
            boolean[] buttonClicked = new boolean[word.length()];

            TranslateTransition[] transitions = new TranslateTransition[word.length()];


            for (int i = 0; i < word.length(); i++) {
                final int index = i;

                Button button = new Button(""+ word.charAt(i));
                button.getStyleClass().add("letter");
                button.getStyleClass().add("custom-transparent-button");

                transitions[i] = new TranslateTransition(Duration.seconds(0.001), button);
                //TranslateTransition transition = new TranslateTransition(Duration.seconds(0.001), button);
                RotateTransition rotateTransition = new RotateTransition(Duration.seconds(0.001), button);
                button.setOnAction(event -> {
                    if (!buttonClicked[index] && number==0) {
                        for (int j = index+1; j < word.length(); j++) {
                            {
                                transitions[j].setToX(-70);
                                transitions[j].play();
                            }
                        }
                        transitions[index].setToY(250); // Di chuyển nút xuống phía dưới
                        transitions[index].play();
                        button.setStyle("-fx-text-fill: white;");


                        // Thêm hiệu ứng xoay nút
                        rotateTransition.setByAngle(290);
                        rotateTransition.play();

                        buttonClicked[index]=true;
                        number++;
                    } else if(buttonClicked[index]){
                        for (int j = index+1; j < word.length(); j++) {
                            {
                                transitions[j].setToX(0);
                                transitions[j].play();
                            }
                        }
                        transitions[index].setToY(0); // Di chuyển nút xuống phía dưới
                        transitions[index].play();
                        button.setStyle("-fx-text-fill:#eb4d7d");


                        // Thêm hiệu ứng xoay nút
                        rotateTransition.setByAngle(70);
                        rotateTransition.play();
                        buttonClicked[index]=false;
                        number--;

                    }
                });

                buttonList.add(button);
                buttonContainer.getChildren().add(button);
            }


            for (int i = 0; i < buttonList.size(); i++) {
                TextController textController = new TextController(buttonList.get(i));
                textControllerList.add(textController);
            }
            Scene scene = new Scene(buttonContainer,WIDTH,HEIGHT);
            scene.getStylesheets().add(getClass().getResource("/fxml/decorate.css").toExternalForm());
            stage.setScene(scene);
            buttonContainer.setAlignment(Pos.CENTER);
            buttonContainer.setPadding(new Insets(0, 0, 150, 0));
            stage.show();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public String addletter(String word)
    {
        Random random = new Random();

// Chọn một vị trí ngẫu nhiên trong word để chèn chữ cái
        int randomPosition = random.nextInt(word.length());

// Tạo một chữ cái ngẫu nhiên (trong ví dụ này, tôi sử dụng a-z)
        char randomChar = (char) (random.nextInt(26) + 'a');

// Chèn chữ cái vào vị trí ngẫu nhiên trong xâu word
        return word.substring(0, randomPosition) + randomChar + word.substring(randomPosition);
    }
    public static void main(String[] args) {
        launch(args);
    }

    // Hàm tạo cơ cấu giao diện chung (commonLayout)

}