import javafx.application.Application;
import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

import java.io.IOException;

import static commandLine.DictionaryManagement.importCustomDictionary;
import static commandLine.DictionaryManagement.insertFromFileDICT;


public class DictionaryApp extends Application {

    private double xOffset = 0;
    private double yOffset = 0;

    public static void main(String[] args) throws IOException {
        importCustomDictionary();
        insertFromFileDICT();
        launch(args);
    }

    @Override
    public void start(final Stage stage) throws Exception {
        Parent root = FXMLLoader.load(getClass().getResource("/fxml/dictionaryGUI/baseScene.fxml"));

        stage.setTitle("Dictionary Application");
//        stage.initStyle(StageStyle.TRANSPARENT);

        Scene scene = new Scene(root);
        scene.setFill(Color.TRANSPARENT);
        stage.setScene(scene);
        stage.show();
    }
}
