package Menu;

import Game.Highscore;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.stage.Stage;

import java.io.IOException;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

public class HighscoreList implements Serializable {
    static Stage window;
    static Scene highscoreScene;
    static BorderPane highscorePane;
    static Label highscoreLabel = new Label();
    static Label usernameLabel = new Label();
    static Label scoreLabel = new Label();
    static Button backButton = new Button();

    static List<Highscore.Score> list = new LinkedList<>();
    static List<Label> labels = new ArrayList<>();

    static VBox vboxUsername = new VBox();
    static VBox vboxScore = new VBox();
    static HBox hboxCenter = new HBox();

    public static void showCurrentHighscore(Stage primaryStage, int currentPlace) {
        window = primaryStage;
        updateLabelTexts();
        resetFont();

        if (currentPlace < 5) {
            labels.get(currentPlace).setFont(Font.font("Arial", FontWeight.BOLD, 18));
            labels.get(currentPlace).setVisible(true);
            labels.get(currentPlace + 5).setFont(Font.font("Arial", FontWeight.BOLD, 18));
            labels.get(currentPlace + 5).setVisible(true);
            return;
        }

        //Username currentPlace label
        labels.get(10).setText((currentPlace + 1) + ". " + list.get(currentPlace).getUsername());
        labels.get(10).setFont(Font.font("Arial", FontWeight.BOLD, 18));
        labels.get(10).setVisible(true);

        //Score currentPlace label
        labels.get(11).setText(Integer.toString(list.get(currentPlace).getScore()));
        labels.get(11).setFont(Font.font("Arial", FontWeight.BOLD, 18));
        labels.get(11).setVisible(true);
    }

    public static void showTop5(Stage primaryStage) {
        window = primaryStage;
        updateLabelTexts();

        resetFont();
        labels.get(10).setVisible(false);
        labels.get(11).setVisible(false);
    }

    private static void updateLabelTexts() {
        list = Highscore.getList();
        int min = Math.min(list.size(), 5);

        for (int i = 0; i < min; i++) {
            labels.get(i).setText((i + 1) + ". " + list.get(i).getUsername());
        }
        for (int i = 0; i < min; i++) {
            labels.get(i + 5).setText(Integer.toString(list.get(i).getScore()));
        }
    }

    public static Scene getScene(){
        return highscoreScene;
    }

    private static void resetFont(){
        for(Label l : labels){
            l.setFont(Font.font("Arial", FontWeight.NORMAL, 18));
        }
    }

    public static void Initialize(){
        list = Highscore.getList();

        highscoreLabel.setText("Highscore");
        highscoreLabel.setFont(Font.font("Arial", FontWeight.BOLD, 28));
        usernameLabel.setText("Username");
        usernameLabel.setFont(Font.font("Arial", FontWeight.BOLD, 18));
        scoreLabel.setText("Score");
        scoreLabel.setFont(Font.font("Arial", FontWeight.BOLD, 18));
        backButton.setText("Back");
        backButton.setFont(Font.font("Arial", FontWeight.BOLD, 18));

        vboxUsername.setSpacing(15);
        vboxUsername.setAlignment(Pos.TOP_CENTER);
        vboxUsername.setPadding(new Insets(20, 0, 0, 0));
        vboxUsername.getChildren().addAll(usernameLabel);

        vboxScore.setSpacing(15);
        vboxScore.setAlignment(Pos.TOP_CENTER);
        vboxScore.setPadding(new Insets(20, 0, 0, 0));
        vboxScore.getChildren().addAll(scoreLabel);

        hboxCenter.setSpacing(80);
        hboxCenter.setAlignment(Pos.CENTER);
        hboxCenter.getChildren().addAll(vboxUsername, vboxScore);

        highscorePane = new BorderPane();
        highscorePane.setPadding(new Insets(20, 0, 20, 0));
        highscorePane.setPrefWidth(600);
        highscorePane.setPrefHeight(400);
        highscorePane.setTop(highscoreLabel);
        highscorePane.setBottom(backButton);
        highscorePane.setCenter(hboxCenter);
        highscorePane.setAlignment(highscoreLabel, Pos.BOTTOM_CENTER);
        highscorePane.setAlignment(backButton, Pos.TOP_CENTER);

        for(int i = 0; i < 5; i++){
            labels.add( new Label() );
            labels.get(i).setFont(Font.font("Arial", 18));
            vboxUsername.getChildren().addAll(labels.get(i));
        }

        for(int i = 0; i < 5; i++){
            labels.add( new Label() );
            labels.get(labels.size() - 1).setFont(Font.font("Arial", 18));
            vboxScore.getChildren().addAll(labels.get(i + 5));
        }

        updateLabelTexts();

        labels.add( new Label() );
        labels.get(labels.size() - 1).setFont(Font.font("Arial", 18));
        labels.get(labels.size() - 1).setVisible(false);
        vboxUsername.getChildren().addAll(labels.get(labels.size() - 1));

        labels.add( new Label() );
        labels.get(labels.size() - 1).setFont(Font.font("Arial", 18));
        labels.get(labels.size() - 1).setVisible(false);
        vboxScore.getChildren().addAll(labels.get(labels.size() - 1));

        highscoreScene = new Scene(highscorePane);

        backButton.setOnAction(e -> {
            Parent root = null;
            try {
                root = FXMLLoader.load(HighscoreList.class.getResource("../Menu/Menu.fxml"));
            } catch (IOException ex) {
                ex.printStackTrace();
            }
            window.setScene(new Scene(root));
        });
    }
}
