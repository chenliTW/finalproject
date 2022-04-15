package cpe_client.main;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

public class MainGui extends Application {
    @Override
    public void start(Stage stage) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(MainGui.class.getResource("Main-view.fxml"));
        Scene scene = new Scene(fxmlLoader.load(), 320, 240);
        stage.setTitle("Hello World!");
        stage.setScene(scene);
<<<<<<< HEAD
        stage.show();iasdoiahdsuaois
    }//asdkjiaosdjioads
=======
        stage.show();
    }//
>>>>>>> 4a7aefaaf52e1127a541fd9c2a6498491bb3a245

    public static void main(String[] args) {
        launch();
    }
}
