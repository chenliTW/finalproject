package cpe_client.main;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;

public class Auto_Sign_UpController {
    @FXML
    private Button loginButton;
    private Button backButton;
    public ComboBox examLocationSelector;
    public TextField schoolTextField;
    public TextField departmentTextField;
    public TextField gradeTextField;


    @FXML
    protected void onloginButtonClick() {
        return;
    }

    @FXML
    protected void onbackButtonClick() {
        MainGui.currentStage.setScene(MainGui.menuScene);
    }
}
