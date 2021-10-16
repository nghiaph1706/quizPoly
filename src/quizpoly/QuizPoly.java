
package quizpoly;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import org.json.simple.parser.ParseException;

public class QuizPoly {
    
    public static final String api = "https://api.cap.quizpoly.xyz/cms/";
    public static List<Questions> list_quest = new ArrayList<>();
    public static List<AnswerCorrect> list_correct = new ArrayList<>();
    
    public static void main(String[] args) throws FileNotFoundException, IOException, ParseException, Exception {
        new GUI().setVisible(true);
    }
    
}
