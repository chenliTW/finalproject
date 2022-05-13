package cpe_client.main;

import javafx.fxml.FXML;
import javafx.scene.control.Label;

public class Auto_Sign_UpController {
    @FXML
    private Label welcomeText;

    @FXML
    protected void onHelloButtonClick() {
        welcomeText.setText("Welcome to JavaFX Application!");
    }
}
