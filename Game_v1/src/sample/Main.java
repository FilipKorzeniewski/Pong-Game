package sample;

import Menu.HighscoreList;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class Main extends Application {

    @Override
    public void start(Stage primaryStage) throws Exception{
        Parent root = FXMLLoader.load(getClass().getResource("../Menu/Menu.fxml"));
        primaryStage.setScene(new Scene(root));
        primaryStage.show();

        primaryStage.setTitle("PONG GAME");
        HighscoreList.Initialize();
        Menu.Menu.start(primaryStage);
    }


    public static void main(String[] args) {
        launch(args);
    }
}
