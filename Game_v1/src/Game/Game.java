package Game;

import Menu.HighscoreList;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Bounds;
import javafx.scene.Cursor;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.input.MouseDragEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Rectangle;
import javafx.scene.transform.Scale;
import javafx.stage.Stage;
import javafx.util.Duration;

import java.io.IOException;
import java.sql.Time;

public class Game {

    static Stage window;
    static String currentUser;
    static int score;
    static int difficultyCoefficient;
    static int widthCoefficient;
    static int scoreCoefficient;
    static Highscore highscore = new Highscore();
    static boolean isGameOver = false;
    static Timeline ballMove = new Timeline();

    static Scale scale = new Scale();
    static Pane boardPane;
    static Scene boardScene;
    static Circle ball;
    static Rectangle hitRect;

    public static void start(Stage primaryStage, String username, int difficulty){
        window = primaryStage;
        currentUser = username;
        difficultyCoefficient = difficulty + 2; //easy = 3, medium = 4, hard = 5
        widthCoefficient = -difficulty + 6;
        scoreCoefficient = difficulty + 2; //score got per hit
        score = 0;
        loadBoard();
        ballAnimation();
        hitRectAnimation();
    }

    private static void loadBoard(){
        boardPane = new Pane();
        boardScene = new Scene(boardPane, 600, 400);

        ball = new Circle(10, Color.BLACK);
        ball.relocate(600 * Math.random(), ball.getRadius());

        hitRect = new Rectangle(widthCoefficient * 30,10);
        hitRect.relocate((boardScene.getWidth() - hitRect.getWidth())/2, boardScene.getHeight() - 40);

        boardPane.getChildren().addAll(hitRect, ball);

        window.setScene(boardScene);
    }

    private static void ballAnimation() {
        score = 0;

        ballMove = new Timeline(new KeyFrame(Duration.millis(15), new EventHandler<ActionEvent>() {
            double dx = difficultyCoefficient;
            double dy = difficultyCoefficient;

            @java.lang.Override
            public void handle(ActionEvent event) {

                ball.setLayoutX(ball.getLayoutX() + dx);
                ball.setLayoutY(ball.getLayoutY() + dy);

                final Bounds bounds = boardPane.getBoundsInLocal();
                final boolean rightBorder = ball.getLayoutX() >= (boardPane.getWidth() - ball.getRadius());
                final boolean leftBorder = ball.getLayoutX() <= (ball.getRadius());
                final boolean topBorder = ball.getLayoutY() <= (bounds.getMinY() + ball.getRadius());
                final boolean bottomBorder = ball.getLayoutY() >= (bounds.getMaxY() - ball.getRadius());

                final boolean hitBorderY = ball.getLayoutY() >= hitRect.getLayoutY() - ball.getRadius();
                final boolean hitBorderXleft = ball.getLayoutX() - ball.getRadius() >= hitRect.getLayoutX();
                final boolean hitBorderXright = ball.getLayoutX() + ball.getRadius() <= hitRect.getLayoutX() + hitRect.getWidth();
                final boolean hitBorderX = hitBorderXleft && hitBorderXright;
                final boolean hitBorder = hitBorderX && hitBorderY;


                if (leftBorder || rightBorder)
                    dx *= -1;
                if (topBorder)
                    dy *= -1;
                if (hitBorder) {
                    dy *= -1;
                    score += scoreCoefficient;
                }
                if (bottomBorder) {
                    ballMove.stop();
                    try {
                        GameOver(score);
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }
        }));
        ballMove.setCycleCount(Timeline.INDEFINITE);
        ballMove.play();
    }

    private static void hitRectAnimation() {
        boardScene.setOnMouseMoved(new EventHandler<MouseEvent>() {
            @java.lang.Override
            public void handle(MouseEvent mouseEvent) {
                hitRect.setLayoutX(mouseEvent.getX() - hitRect.getWidth()/2);
            }
        });
    }

    static private void GameOver(int score) throws IOException {
        int currentPlace = Highscore.addUser(currentUser, score);
        HighscoreList.showCurrentHighscore(window, currentPlace);
        showHighscore();
    }

    private static void showHighscore(){
        window.setScene(HighscoreList.getScene());
    }
}
