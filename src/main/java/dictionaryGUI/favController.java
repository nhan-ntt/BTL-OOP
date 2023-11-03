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
    ObservableList<String> list = FXCollections.observableArrayList();

    Image starImg = new Image("/Image/star.png", 40, 40, true, true);
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
            starImageView.setImage(starImg);
            String selectedWord = favList.getSelectionModel().getSelectedItem();
            Word word = lookupWord(selectedWord);
            wordTarget.setText(word.getWordTarget());
            wordExplain.setText(word.getWordExplain());
        });
        starImageView.setImage(starImg);
        star.setGraphic(starImageView);
        wordTarget.setText("");
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
            starImageView.setImage(starImg);
        } else {
            starImageView.setImage(starOutImg);

            favoriteWord.removeIf((Word w) -> w.getWordTarget().equals(wordTarget));

            for (int i = 0; i < list.size(); i++) {
                if (list.get(i).equals(this.wordTarget.getText())) {
                    list.remove(i);
                    break;
                }
            }

            favList.setItems(list);
//            this.wordExplain.setText("");
//            this.wordTarget.setText("");
            favWord.setFavorite(false);
        }

        exportCustomDictionary();
    }

    public void handleSpeak(MouseEvent mouseEvent) {
        generateTextToSpeech(wordTarget.getText(), "English");

        String gongFile = "output.mp3";
        InputStream in = null;
        try {
            in = Files.newInputStream(Paths.get(gongFile));
            AudioStream audioStream = new AudioStream(in);
            AudioPlayer.player.start(audioStream);
        } catch (IOException ex) {
            throw new RuntimeException(ex);
        }
    }
}