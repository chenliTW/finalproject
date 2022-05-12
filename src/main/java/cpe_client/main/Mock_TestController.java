package cpe_client.main;

import javafx.fxml.FXML;
import javafx.scene.control.Label;

public class Mock_TestController {
    @FXML
    private Label welcomeText;

    @FXML
    protected void onHelloButtonClick() {
        welcomeText.setText("Welcome to JavaFX Application!");
    }
}