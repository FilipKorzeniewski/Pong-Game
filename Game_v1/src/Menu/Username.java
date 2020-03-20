package Menu;

import Game.Game;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class Username implements Initializable {

    static Stage window;
    static int difficulty;

    @FXML Button backButton = new Button();
    @FXML TextField usernameTextField = new TextField();

    @FXML private void backButtonClicked() throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("../Menu/Menu.fxml"));
        window.setScene(new Scene(root));
    }

    @FXML private void UsernameEnterClicked() {
        Game.start(window, usernameTextField.getText(), difficulty);
    }

    public static void start(Stage primaryStage, int gameDifficulty){
        window = primaryStage;
        difficulty = gameDifficulty;
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

    }
}
