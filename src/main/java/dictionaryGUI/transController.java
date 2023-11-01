package dictionaryGUI;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import static API.API_Translator.translate;

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
            lb.setText("Tiếng Việt");
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

        transBtn.setOnMouseClicked(e -> {
            try {
                output.setText(translate(langFromStr, langToStr, input.getText()));
            } catch (IOException ex) {
                throw new RuntimeException(ex);
            }
        });
    }
}