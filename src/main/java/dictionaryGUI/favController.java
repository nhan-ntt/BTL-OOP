package dictionaryGUI;

import commandLine.DictionaryManagement;
import commandLine.Word;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.image.ImageView;
import javafx.scene.image.Image;
import javafx.scene.input.MouseEvent;
import sun.audio.AudioPlayer;
import sun.audio.AudioStream;

import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Objects;
import java.util.ResourceBundle;

import static API.VoiceRSSAPI.generateTextToSpeech;
import static commandLine.Dictionary.*;
import static commandLine.DictionaryManagement.*;

public class favController implements Initializable {
    @FXML
    private ListView<String> favList;
    @FXML
    private TextArea wordExplain;
    @FXML
    private Button star, volumeBtn;
    @FXML
    private Label wordTarget;
    @FXML
    private ImageView starImageView = new ImageView();
    @FXML
    private Tooltip favTooltip, wordTooltip;

    ObservableList<String> list = FXCollections.observableArrayList();

    Image starImg = new Image("/Image/star1.png", 40, 40, true, true);
    Image starOutImg = new Image("/Image/star-outline.png", 40, 40, true, true);


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
            favTooltip.setText("Remove from favorites");

            starImageView.setImage(starImg);
            String selectedWord = favList.getSelectionModel().getSelectedItem();
            Word word = lookupWord(selectedWord);

            if (selectedWord.length() > 30) {
                wordTarget.setText(word.getWordTarget().substring(0, 30) + "...");
            } else {
                wordTarget.setText(word.getWordTarget());
            }
            wordTooltip.setText(word.getWordTarget());
            wordExplain.setText(word.getWordExplain());
        });
        starImageView.setImage(starImg);
        star.setGraphic(starImageView);
        wordTarget.setText("");
    }

    public void handleStar(MouseEvent mouseEvent) throws IOException {
        if (wordTarget.getText().isEmpty()) return;
        String wordTarget = this.wordTooltip.getText();

        Word favWord = null;

        for (Word word : favoriteWord) {
            if (word.getWordTarget().equals(wordTarget)) {
                favWord = word;
                break;
            }
        }

//        assert favWord != null;
//        System.out.println(favWord + " " + favWord.isFavorite());

        if (favWord == null) return;

        if (!favWord.isFavorite()) {
//            favoriteWord.removeIf((Word w) -> w.getWordTarget().equals(wordTarget));
//            favoriteWord.addFirst(favWord);
            favTooltip.setText("");
            favWord.setFavorite(true);
            starImageView.setImage(starImg);
        } else {
            starImageView.setImage(starOutImg);
            favTooltip.setText("Remove from favorites");
            favoriteWord.removeIf((Word w) -> w.getWordTarget().equals(wordTarget));

            for (int i = 0; i < list.size(); i++) {
                if (list.get(i).equals(this.wordTooltip.getText())) {
                    list.remove(i);
                    break;
                }
            }

            favList.setItems(list);
//            this.wordExplain.setText("");
//            this.wordTarget.setText("");
            favWord.setFavorite(false);
            favTooltip.setText("");
        }

        exportCustomDictionary();
    }

    public void handleSpeak(MouseEvent mouseEvent) {
        if (wordTarget.getText().isEmpty()) return;
        Thread speakFromThread = new Thread(() -> {
            generateTextToSpeech(wordTooltip.getText(), "English");

            String gongFile = "output.mp3";
            InputStream in = null;

            try {
                in = Files.newInputStream(Paths.get(gongFile));
                AudioStream audioStream = new AudioStream(in);
                AudioPlayer.player.start(audioStream);
            } catch (IOException ex) {
                throw new RuntimeException(ex);
            }
        });
        speakFromThread.start();
    }
}