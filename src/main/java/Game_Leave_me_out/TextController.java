package Game_Leave_me_out;

import javafx.event.EventHandler;
import javafx.scene.control.Button;
import javafx.scene.input.MouseEvent;

public class TextController {
    private Button ButtonLetter;

    public TextController(Button buttonLetter) {
        this.ButtonLetter = buttonLetter;
        initializeButton();
    }

    private void initializeButton() {
        ButtonLetter.setOnMouseEntered(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                ButtonLetter.getStyleClass().add("border"); // Thay đổi màu chữ thành đỏ khi chuột vào
            }
        });

        ButtonLetter.setOnMouseExited(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                ButtonLetter.getStyleClass().remove("border"); // Loại bỏ lớp CSS khi chuột ra
            }
        });
    }
}
