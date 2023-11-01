package dictionaryGUI;

import commandLine.Word;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;

import java.io.FileWriter;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import static commandLine.Dictionary.listWord;
import static commandLine.DictionaryManagement.addWord;

public class addController implements Initializable {
    @FXML
    TextField wordTarget;
    @FXML
    Label ok, existed;
    @FXML
    Button addBtn;
    @FXML
    TextArea wordExplain;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        ok.setVisible(false);
        existed.setVisible(false);

        wordTarget.setOnKeyReleased(e -> {
            String wordTarget = this.wordTarget.getText();
            if (listWord.contains(wordTarget)) {
                existed.setVisible(true);
                wordExplain.setEditable(true);
                addBtn.setDisable(true);
            } else {
                existed.setVisible(false);
                wordExplain.setEditable(true);
            }
        });

        wordExplain.setOnKeyReleased(e -> {
            if (wordTarget.getText().isEmpty() || wordExplain.getText().isEmpty()) {
                addBtn.setDisable(true);
            } else {
                addBtn.setDisable(false);
            }
        });

        addBtn.setOnMouseClicked(e -> {
            addWord(wordTarget.getText(), wordExplain.getText());

            FileWriter fw = null;
            try {
                fw = new FileWriter("src\\main\\resources\\addWord.txt");
                fw.write("@" + wordTarget.getText() + "\n");
                fw.write(wordExplain.getText());
                fw.write("\n\n");
                fw.close();
            } catch (IOException ex) {
                throw new RuntimeException(ex);
            }

            ok.setVisible(true);
        });
    }
}
