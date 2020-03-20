package Menu;

import Game.Highscore;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class Menu implements Initializable {

    int difficulty;

    static Stage window;

    @FXML Button startGameButton = new Button();
    @FXML Button highScoreButton = new Button();
    @FXML Button quitButton = new Button();
    @FXML ComboBox<String> difficultyComboBox = new ComboBox<>();

    @FXML void StartGameButtonClicked () throws IOException {
        window.setMinWidth(600);
        window.setMaxWidth(600);
        window.setMinHeight(400);
        window.setMaxHeight(400);

        Username.start(window, difficulty);
        Parent root = FXMLLoader.load(getClass().getResource("../Menu/Username.fxml"));
        window.setScene(new Scene(root));
    }

    @FXML void HighScoreButtonClicked() throws IOException {
        HighscoreList.showTop5(window);
        window.setScene(HighscoreList.getScene());
    }

    @FXML void difficultyComboBoxChoose(){

        if(difficultyComboBox.getValue().equals("Hard"))
            this.difficulty = 3;
        else if(difficultyComboBox.getValue().equals("Medium"))
            this.difficulty = 2;
        else
            this.difficulty = 1;
    }

    @FXML void QuitButtonClicked(){
        closeProgram();
    }

    private static void closeProgram(){
        boolean answer = ConfirmBox.display("Warning", "Are you sure you want to exit?");
        if(answer)
            window.close();
    }

    public static void start(Stage primaryStage){
        window = primaryStage;

        //closing
        window.setOnCloseRequest(e -> {
            e.consume();
            closeProgram();
        });
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        difficultyComboBox.getItems().addAll(
                "Easy", "Medium", "Hard"
        );

        //default value od difficulty
        this.difficulty = 1;
    }
}
 