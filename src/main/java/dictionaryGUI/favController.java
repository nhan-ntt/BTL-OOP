package dictionaryGUI;

import commandLine.DictionaryManagement;
import commandLine.Word;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.TextArea;
import javafx.scene.input.MouseEvent;

import java.io.IOException;
import java.net.URL;
import java.util.Objects;
import java.util.ResourceBundle;

import static commandLine.Dictionary.*;
import static commandLine.DictionaryManagement.*;

public class favController implements Initializable {
    @FXML
    private ListView<String> favList;
    @FXML
    private TextArea wordExplain;
    @FXML
    private Button star;
    @FXML
    private Label wordTarget;

    ObservableList<String> list = FXCollections.observableArrayList();

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        try {
            importCustomDictionary();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        list.clear();
        for (Word word : favoriteWord) {
            list.add(word.getWordTarget());
        }
        favList.setItems(list);

        favList.setOnMouseClicked(e -> {
            String selectedWord = favList.getSelectionModel().getSelectedItem();
            Word word = lookupWord(selectedWord);
            wordTarget.setText(word.getWordTarget());
            wordExplain.setText(word.getWordExplain());
        });
    }

    public void handleStar(MouseEvent mouseEvent) throws IOException {
        if (wordTarget.getText().isEmpty()) return;
        String wordTarget = this.wordTarget.getText();

        Word favWord = null;

        for (Word word : favoriteWord) {
            if (word.getWordTarget().equals(wordTarget)) {
                favWord = word;
                break;
            }
        }

        assert favWord != null;
        System.out.println(favWord + " " + favWord.isFavorite());

        if (!favWord.isFavorite()) {
            favoriteWord.removeIf((Word w) -> w.getWordTarget().equals(wordTarget));
            favoriteWord.addFirst(favWord);
            favWord.setFavorite(true);
        } else {
            favoriteWord.removeIf((Word w) -> w.getWordTarget().equals(wordTarget));

            for (int i = 0; i < list.size(); i++) {
                if (list.get(i).equals(this.wordTarget.getText())) {
                    list.remove(i);
                    break;
                }
            }

            favList.setItems(list);
            this.wordExplain.setText("");
            this.wordTarget.setText("");
            favWord.setFavorite(false);
        }

        exportCustomDictionary();
    }
}
