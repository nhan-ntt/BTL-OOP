package Game_Leave_me_out;
import Game_Leave_me_out.TextController;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Pos;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.Pane;
import javafx.scene.layout.HBox;
import javafx.scene.control.Button;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import javafx.geometry.Insets;

import javax.xml.soap.Text;

public class MainScene extends Application {
    private static final int WIDTH = 977;
    private static final int HEIGHT = 706;
    private BorderPane commonLayout = new BorderPane();

    @Override
    public void start(Stage stage) throws IOException {
        try {
            // Tạo cơ cấu giao diện chung với các nút bên dưới
            createCommonLayout();

            Parent root = FXMLLoader.load(MainScene.class.getResource("/fxml/MainScene.fxml"));
            Scene scene = new Scene(root);
            scene.getStylesheets().add(getClass().getResource("/fxml/decorate.css").toExternalForm());

            if (root instanceof Pane) {
                ((Pane) root).getChildren().add(commonLayout);
            }

            stage.setScene(scene);
            stage.show();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        launch(args);
    }

    // Hàm tạo cơ cấu giao diện chung (commonLayout)
    private void createCommonLayout() {
        RandomWord key = new RandomWord();
        String word = key.findRandomWord();


        HBox buttonContainer = new HBox(word.length());
        buttonContainer.setAlignment(Pos.CENTER);

        buttonContainer.setPadding(new Insets(180,30, 150,100));

       // buttonContainer.setPadding(new Insets(90));

        List<Button> buttonList = new ArrayList<>();
        List<TextController> textControllerList = new ArrayList<>();
        for (int i = 0; i < word.length(); i++) {
            Button button = new Button(""+ word.charAt(i));
           // button.setPrefSize(80, 80);
            button.getStyleClass().add("letter");
            button.getStyleClass().add("custom-transparent-button");

            buttonList.add(button);
            buttonContainer.getChildren().add(button);
        }

        for (int i = 0; i < buttonList.size(); i++) {
            TextController textController = new TextController(buttonList.get(i));
            textControllerList.add(textController);
        }

        commonLayout.setCenter(buttonContainer);
    }
}