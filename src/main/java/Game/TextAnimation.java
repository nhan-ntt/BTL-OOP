package Game;

import javafx.animation.RotateTransition;
import javafx.animation.TranslateTransition;
import javafx.scene.control.Button;
import javafx.scene.layout.HBox;
import javafx.util.Duration;

import java.util.ArrayList;
import java.util.List;

public class TextAnimation {
    private TranslateTransition[] transitions;
    private RotateTransition[] rotateTransition;
    private String word;
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
    HBox buttonContainer = new HBox();
    List<Button> buttonList = new ArrayList<>();
    List<BorderButton> borderButtonList = new ArrayList<>();
    boolean[] buttonClicked;
    private int numberDown=0;
    private int letterDown=-1;
    RandomWord randomWord = new RandomWord();

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
            BorderButton textController = new BorderButton(button);
            borderButtonList.add(textController);
        }
    }

    public int getLetterDown() {
        return letterDown;
    }

    public void setLetterDown(int letterDown) {
        this.letterDown = letterDown;
    }

    public int getNumberDown() {
        return numberDown;
    }

    public void setNumberDown(int numberDown) {
        this.numberDown = numberDown;
    }

    public HBox getButtonContainer() {
        return buttonContainer;
    }

    public String getWord() {
        return word;
    }

    public void setButtonContainer(HBox buttonContainer) {
        this.buttonContainer = buttonContainer;
    }
}
