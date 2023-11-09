package dictionaryGUI;

import javafx.application.Platform;
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

        input.setWrapText(true);
        output.setWrapText(true);

        setLabelFromCode(langFrom, langFromStr);
        setLabelFromCode(langTo, langToStr);

        switchBtn.setOnMouseClicked(e -> {
            String temp = langFromStr;
            langFromStr = langToStr;
            langToStr = temp;

            setLabelFromCode(langFrom, langFromStr);
            setLabelFromCode(langTo, langToStr);
        });

        output.setEditable(false);

        transBtn.setOnMouseClicked(e -> {
            if (input.getText().isEmpty()) {
                return;
            }

            // Xử lý dịch văn bản trong một luồng mới
            Thread translationThread = new Thread(() -> {
                try {
                    String translatedText = translate(langFromStr, langToStr, input.getText());
                    // Cập nhật giao diện người dùng từ luồng chính
                    Platform.runLater(() -> {
                        output.setText(translatedText);
                    });
                } catch (IOException ex) {
                    throw new RuntimeException(ex);
                }
            });
            translationThread.start();
        });

        speakFromBtn.setOnMouseClicked(e -> {
            if (input.getText().isEmpty()) {
                return;
            }

            // Tạo một luồng mới để phát âm thanh
            Thread speakFromThread = new Thread(() -> {
                generateTextToSpeech(input.getText(), langFrom.getText());

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
            speakFromThread.start();
        });

        speakToBtn.setOnMouseClicked(e -> {
            if (output.getText().isEmpty()) {
                return;
            }

            // Tạo một luồng mới để phát âm thanh
            Thread speakToThread = new Thread(() -> {
                generateTextToSpeech(output.getText(), langTo.getText());

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
            speakToThread.start();
        });
    }
}
