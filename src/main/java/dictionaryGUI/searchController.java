package dictionaryGUI;

import commandLine.DictionaryManagement;
import commandLine.Word;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import sun.audio.AudioPlayer;
import sun.audio.AudioStream;

import java.io.*;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;
import java.util.Objects;
import java.util.ResourceBundle;

import static API.VoiceRSSAPI.generateTextToSpeech;
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
    @FXML
    private Tooltip wordTooltip;
    @FXML
    private javafx.scene.image.ImageView starImageView = new ImageView();

    Image starImg = new Image("/Image/star1.png", 40, 40, true, true);
    Image starOutImg = new Image("/Image/star-outline.png", 40, 40, true, true);


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
        setListDefault();
        try {
            importCustomDictionary();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        searchTerm.setOnKeyReleased(e -> {
            if (searchTerm.getText().isEmpty()) {
                setListDefault();
            } else {
                handleOnKeyTyped();
            }
        });

        wordExplain.setEditable(false);
        saveBtn.setVisible(false);
        notAvailable.setVisible(false);
        starImageView.setImage(starOutImg);
        starBtn.setGraphic(starImageView);
        wordTarget.setText("");
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
//        System.out.println(word.getWordTarget() + " " + word.isFavorite());
        for (Word w : favoriteWord) {
            if (w.getWordTarget().equals(selectedWord)) {
                word = w;
                break;
            }
        }

        if(word.isFavorite()) {
            starImageView.setImage(starImg);
        } else {
            starImageView.setImage(starOutImg);
        }
        starBtn.setGraphic(starImageView);

        final Word finalWord = word;
        Platform.runLater(() -> {
                    if (finalWord.getWordTarget().length() > 22) {
                        wordTarget.setText(finalWord.getWordTarget().substring(0, 22) + "...");
                    } else {
                        wordTarget.setText(finalWord.getWordTarget());
                    }
                    wordTooltip.setText(finalWord.getWordTarget());

                    wordExplain.setText(finalWord.getWordExplain());
                    wordExplain.setVisible(true);
                    wordExplain.setEditable(false);
                    saveBtn.setVisible(false);
                });
        recentWord.removeIf((Word w) -> w.getWordTarget().equals(wordTarget));
        recentWord.addFirst(word);
        exportCustomDictionary();

    }

    public void handleFavorite(MouseEvent mouseEvent) throws IOException {
        if (wordTarget.getText().isEmpty()) return;
        String wordTarget = this.wordTarget.getText();

        Word favWord = null;

        for (Word word : favoriteWord) {
            if (word.getWordTarget().equals(wordTarget)) {
                favWord = word;
                break;
            }
        }

        if (favWord == null)
            favWord = new Word(wordTarget, Objects.requireNonNull(lookupWord(wordTarget)).getWordExplain());

        System.out.println("before " + favWord.getWordTarget() + " " + favWord.isFavorite());

        if (!favWord.isFavorite()) {
            favoriteWord.removeIf((Word w) -> w.getWordTarget().equals(wordTarget));
            favoriteWord.addFirst(favWord);
            favWord.setFavorite(true);
            starImageView.setImage(starImg);
        } else {
            favoriteWord.removeIf((Word w) -> w.getWordTarget().equals(wordTarget));
            favWord.setFavorite(false);
            starImageView.setImage(starOutImg);
        }
        System.out.println("after " + favWord.getWordTarget() + " " + favWord.isFavorite());


        exportCustomDictionary();
    }

    public void handleSave(MouseEvent mouseEvent) {
        saveBtn.setVisible(false);
        wordExplain.setEditable(false);
        new Thread(() -> {
            listWord.changeMeaning(wordTarget.getText(), wordExplain.getText());
        }).start();
    }

    public void handleSpeak(MouseEvent mouseEvent) throws Exception {
        if (wordTarget.getText().isEmpty()) return;

        new Thread(() -> {
            try {
                generateTextToSpeech(wordTooltip.getText(), "English");

                String gongFile = "output.mp3";
                InputStream in = Files.newInputStream(Paths.get(gongFile));
                AudioStream audioStream = new AudioStream(in);
                AudioPlayer.player.start(audioStream);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }).start();
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
        Platform.runLater(() -> {
            recList.setItems(list);
            wordExplain.setText("");
            wordTarget.setText("");
        });
        new Thread(() -> {
            removeWord(wordTarget.getText());
        }).start();
    }

    public void handleEdit(MouseEvent mouseEvent) {
        wordExplain.setEditable(true);
        saveBtn.setVisible(true);
    }
}