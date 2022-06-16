package cpe_client.main;

import javafx.application.Application;
import javafx.application.Platform;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.Random;

public class MainGui extends Application {
    public static Stage currentStage;
    public static Scene menuScene, mockTestScene, autoSignUpScene;

    @Override
    public void start(Stage stage) throws IOException {
        FXMLLoader menuFxmlLoader = new FXMLLoader(MainGui.class.getResource("Main_Menu.fxml"));
        FXMLLoader mockTestFxmlLoader = new FXMLLoader(MainGui.class.getResource("Mock_test.fxml"));
        FXMLLoader autoSignUpFxmlLoader = new FXMLLoader(MainGui.class.getResource("Auto_Sign_Up.fxml"));
        menuScene = new Scene(menuFxmlLoader.load(), 600, 400);
        mockTestScene = new Scene(mockTestFxmlLoader.load(), 850, 600);
        autoSignUpScene = new Scene(autoSignUpFxmlLoader.load(), 850, 600);
        currentStage = stage;
        currentStage.setTitle("CPE_Client");
        currentStage.setScene(menuScene);
        currentStage.show();
    }

    public static void main(String[] args){
        new Thread() {
            public void run() {
                cpe_client.uvacrawler.account.loginAndGetCookie();
            }
        }.start();
        launch();
    }
}