package dictionaryGUI;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;

import sun.audio.AudioPlayer;
import sun.audio.AudioStream;

import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.nio.file.Paths;
import java.util.ResourceBundle;

import static API.API_Translator.translate;
import static API.VoiceRSSAPI.generateTextToSpeech;
import static java.nio.file.Files.newInputStream;

public class transController implements Initializable {
    @FXML
    TextArea input, output;
    @FXML
    Button transBtn, switchBtn, speakFromBtn, speakToBtn;
    @FXML
    Label langFrom, langTo;

    String langFromStr, langToStr;


    void setLabelFromCode(Label lb, String lang) {
        if (lang.equals("vi")) {
            lb.setText("Vietnamese");
        }
        if (lang.equals("en")) {
            lb.setText("English");
        }
    }


    @Override
    public void initialize(URL location, ResourceBundle resources) {
        langFromStr = "en";
        langToStr = "vi";

        setLabelFromCode(langFrom, langFromStr);
        setLabelFromCode(langTo, langToStr);

        switchBtn.setOnMouseClicked(e -> {
            String temp = langFromStr;
            langFromStr = langToStr;
            langToStr = temp;

            setLabelFromCode(langFrom, langFromStr);
            setLabelFromCode(langTo, langToStr);
        });

        input.setOnMouseClicked(e -> {
            input.setText("");
            output.setText("");
        });

        transBtn.setOnMouseClicked(e -> {
            if (input.getText().isEmpty()) {
                return;
            }

            try {
                output.setText(translate(langFromStr, langToStr, input.getText()));
            } catch (IOException ex) {
                throw new RuntimeException(ex);
            }
        });

        speakFromBtn.setOnMouseClicked(e -> {
            generateTextToSpeech(input.getText(), langFromStr);

            String gongFile = "output.mp3";
            InputStream in = null;
            try {
                in = newInputStream(Paths.get(gongFile));
                AudioStream audioStream = new AudioStream(in);
                AudioPlayer.player.start(audioStream);
            } catch (IOException ex) {
                throw new RuntimeException(ex);
            }
        });

        speakToBtn.setOnMouseClicked(e -> {
            generateTextToSpeech(output.getText(), langToStr);

            String gongFile = "output.mp3";
            InputStream in = null;
            try {
                in = newInputStream(Paths.get(gongFile));
                AudioStream audioStream = new AudioStream(in);
                AudioPlayer.player.start(audioStream);
            } catch (IOException ex) {
                throw new RuntimeException(ex);
            }
        });
    }
}