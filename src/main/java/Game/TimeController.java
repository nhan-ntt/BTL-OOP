package Game;

// GameTimer.java

import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.event.ActionEvent;
import javafx.scene.text.Text;
import javafx.util.Duration;

public class TimeController {
    private int countdownTime=0;
    private int elapsedTime =0;
    private Timeline countdown = new Timeline();
    private Text countdownText = new Text();;
    private Text totalTime = new Text();
    public TimeController(String countdown, int countdownTime) {
        this.countdownTime = countdownTime;
        this.elapsedTime = 0;
        this.countdownText = new Text(countdown);
    }

    public void startTimer(UIManager uiManager) {
        this.countdown = new Timeline(new KeyFrame(Duration.seconds(1),event ->  updateCountdown(event,uiManager)));
        countdown.setCycleCount(Timeline.INDEFINITE);
        countdown.play();
    }

    private void updateCountdown(ActionEvent event,UIManager uiManager) {
        if (countdownTime > 0 ) {
            countdownTime--;
            elapsedTime++;
            updateTime(totalTime, elapsedTime);
            updateTime(countdownText, countdownTime);
        } else {
            countdown.stop();
            uiManager.checkTime();
        }
    }

    private void updateTime(Text text, int time) {
        int minutes = time / 60;
        int seconds = time % 60;

        text.setText(String.format("%02d:%02d", minutes, seconds));
    }

    public Text getCountdownText() {
        return countdownText;
    }

    public void setCountdownText(Text countdownText) {
        this.countdownText = countdownText;
    }

    public int getCountdownTime() {
        return countdownTime;
    }

    public Timeline getCountdown() {
        return countdown;
    }

    public void setCountdown(Timeline countdown) {
        this.countdown = countdown;
    }

    public Text getTotalTime() {
        return totalTime;
    }

    public void setTotalTime(Text totalTime) {
        this.totalTime = totalTime;
    }
}

