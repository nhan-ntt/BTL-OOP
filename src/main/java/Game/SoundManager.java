package Game;

import javafx.scene.control.Button;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;

import static javafx.scene.paint.Color.TRANSPARENT;
import static javafx.scene.paint.Color.WHITE;

public class SoundManager {
    private boolean isSoundEnabled = true;
    private MediaPlayer backgroundMusic;
    private MediaPlayer soundEffect;

    public SoundManager(String backgroundMusicPath) {
        Media backgroundMedia = new Media(getClass().getResource(backgroundMusicPath).toString());
        backgroundMusic = new MediaPlayer(backgroundMedia);
    }

    public void playBackgroundMusic(double num) {
             setSoundEnabled(true);
            backgroundMusic.setCycleCount(MediaPlayer.INDEFINITE);
            backgroundMusic.setVolume(num);
            backgroundMusic.play();
    }

    public void playSoundEffect(String soundEffectPath) {
            if(!isSoundEnabled()) return;
            Media soundEffectMedia = new Media(getClass().getResource(soundEffectPath).toString());
            soundEffect = new MediaPlayer(soundEffectMedia);
            soundEffect.play();
            setSoundEnabled(true);
    }

    public void stopSoundEffect() {
        setSoundEnabled(false);
        backgroundMusic.stop();
        if (soundEffect != null) {
                soundEffect.stop();
        }
    }
    void ButtonCheck(Button sound) {
        if (isSoundEnabled) {
            stopSoundEffect();
            sound.setTextFill(WHITE);
        } else {
            playBackgroundMusic(0.25);
            sound.setTextFill(TRANSPARENT);
        }
    }

    public boolean isSoundEnabled() {
        return isSoundEnabled;
    }

    public void setSoundEnabled(boolean soundEnabled) {
        isSoundEnabled = soundEnabled;
    }
}
