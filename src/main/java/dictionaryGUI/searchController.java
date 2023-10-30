package dictionaryGUI;

import commandLine.DictionaryManagement;
import commandLine.Word;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;

import java.io.IOException;
import java.net.URL;
import java.util.List;
import java.util.Objects;
import java.util.ResourceBundle;

import static commandLine.Dictionary.favoriteWord;
import static commandLine.Dictionary.listWord;
import static commandLine.DictionaryCommandLine.showAllWords;
import static commandLine.DictionaryManagement.*;

public class searchController implements Initializable {
    @FXML
    private TextField searchTerm;
    @FXML
    private Label wordTarget, notAvailable;
    @FXML
    private Button editBtn, deleteBtn, volumeBtn, starBtn, saveBtn;
    @FXML
    private ListView<String> recList;
    @FXML
    private TextArea wordExplain;

    private final int NUM_OF_WORDS = 20;
    ObservableList<String> list = FXCollections.observableArrayList();

    private void setListDefault() {
        list.clear();
        List<Word> allWord = listWord.getAllWords();

        for (Word word : allWord) {
            list.add(word.getWordTarget());
        }

//        for (int i = 1; i <= NUM_OF_WORDS; i++) {
//            if (i < allWord.size()) {
//                list.add(allWord.get(i).getWordTarget());
//            }
//        }
        recList.setItems(list);
    }
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

        insertFromFileDICT();
        setListDefault();

        searchTerm.setOnKeyTyped(e -> {
            System.out.println(searchTerm.getText());
            if (searchTerm.getText().isEmpty()) {
                setListDefault();
            } else {
                handleOnKeyTyped();
            }
        });


        wordExplain.setEditable(false);
        saveBtn.setVisible(false);
        notAvailable.setVisible(false);
    }

    private void handleOnKeyTyped() {
        list.clear();
        String searchWord = searchTerm.getText().trim();
        List<Word> recWordList = DictionaryManagement.dictionarySearcher(searchWord);


        for (Word word : recWordList) {
            list.add(word.getWordTarget());
        }
//        for (int i = 0; i <= NUM_OF_WORDS; i++) {
//            if (i < recWordList.size()) {
//                list.add(recWordList.get(i).getWordTarget());
//            }
//        }

        if (list.isEmpty()) {
            notAvailable.setVisible(true);
            recList.setItems(list);
        } else {
            notAvailable.setVisible(false);
            recList.setItems(list);

        }
    }


    public void handleMouseClickAWord(MouseEvent mouseEvent) throws IOException {
        String selectedWord = recList.getSelectionModel().getSelectedItem();
        Word word = lookupWord(selectedWord);
        wordTarget.setText(word.getWordTarget());
        wordExplain.setText(word.getWordExplain());
        wordExplain.setVisible(true);
        wordExplain.setEditable(false);
        saveBtn.setVisible(false);
        recentWord.addFirst(word);
        exportCustomDictionary();
    }


    public void handleFavorite(MouseEvent mouseEvent) throws IOException {
        if (wordTarget.getText().isEmpty()) return;
        String wordTarget = this.wordTarget.getText();
        favoriteWord.addFirst(new Word(wordTarget, Objects.requireNonNull(lookupWord(wordTarget)).getWordExplain()));
        exportCustomDictionary();
    }

    public void handleSave(MouseEvent mouseEvent) {
        saveBtn.setVisible(false);
        wordExplain.setEditable(false);
        listWord.changeMeaning(wordTarget.getText(), wordExplain.getText());
    }

    public void handleSpeak(MouseEvent mouseEvent) {
    }

    public void handleDelete(MouseEvent mouseEvent) {
        if (wordTarget.getText().isEmpty()) return;
        removeWord(wordTarget.getText());

        for (int i = 0; i < list.size(); i++) {
            if (list.get(i).equals(wordTarget.getText())) {
                list.remove(i);
                break;
            }
        }

        recList.setItems(list);
        wordExplain.setVisible(false);
        wordTarget.setText("");
    }

    public void handleEdit(MouseEvent mouseEvent) {
        wordExplain.setEditable(true);
        saveBtn.setVisible(true);
    }
}
