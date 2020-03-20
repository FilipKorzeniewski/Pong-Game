package Game;

import javafx.fxml.Initializable;

import java.io.*;
import java.net.URL;
import java.util.*;

public class Highscore implements Serializable, Initializable {
    static List<Score> list = new LinkedList<>();

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        loadList();
    }

    public static class Score implements Serializable{
        String username;
        int score;

        Score(String username, int score){
            this.username = username;
            this.score = score;
        }

        public String getUsername(){
            return this.username;
        }

        public int getScore(){
            return this.score;
        }
    }

    public static int addUser(String username, int score){
        loadList();

        Score newScore = new Score(username, score);
        list.add(newScore);
        Collections.sort(list, new Comparator<Score>() {
            @Override
            public int compare(Score o1, Score o2) {
                return (o1.score < o2.score) ? 1 : -1;
            }
        });

        saveList();

        return list.indexOf(newScore);
    }

    public static List<Score> getList(){
        loadList();
        return list;
    }

    private static void saveList(){
        try{
            FileOutputStream fs = new FileOutputStream("Highscore.ser");
            ObjectOutputStream os = new ObjectOutputStream(fs);
            os.writeObject(list);
            os.close();
        }catch(Exception ex){
            ex.printStackTrace();
        }
    }

    private static void loadList(){
        try{
            FileInputStream fs = new FileInputStream("Highscore.ser");
            ObjectInputStream os = new ObjectInputStream(fs);
            list = (List<Score>) os.readObject();
        }catch(Exception ex){
            //if there is no file, create it
            list = new LinkedList<>();
            saveList();
        }
    }

    Highscore(){
        loadList();
    }
}
