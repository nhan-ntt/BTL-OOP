package Game;

import javafx.event.EventHandler;
import javafx.scene.control.Button;
import javafx.scene.input.MouseEvent;

public class BorderButton {

    private Button ButtonLetter;
    public BorderButton(Button buttonLetter) {
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
