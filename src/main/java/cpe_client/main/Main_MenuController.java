package cpe_client.main;

import javafx.fxml.FXML;

public class Main_MenuController {
    @FXML
    protected void onAutoSignUpButtonClick() {
        MainGui.currentStage.setScene(MainGui.autoSignUpScene);
    }

    @FXML
    protected void onMockTestButtonClick() {
        MainGui.currentStage.setScene(MainGui.mockTestScene);
    }
}