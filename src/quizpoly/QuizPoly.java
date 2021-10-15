
package quizpoly;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;
import quizpoly.Answer.INPUT_TYPE;
import quizpoly.Answer.TYPE_ANSWER;

public class QuizPoly {
    
    public static List<Questions> getAllQuestionsInPage(String QuizNum) throws IOException{
        List<Questions> list_quest = new ArrayList<>();
        
        String content = new String(Files.readAllBytes(Paths.get("Quiz/"+QuizNum+".txt")));
        Document document = Jsoup.parse(content);
        Elements elmsPolyBody = document.getElementsByClass("poly-body");
        Elements elmsWrapper = document.getElementsByClass("wrapper-problem-response");
        for (int i = 0; i < elmsPolyBody.size(); i++) {
            List<String> list_Ans = new ArrayList<>();
            int quesNum = i+1;
            String quesCont = elmsPolyBody.get(i).text();
            try {
                Elements elmsPolyAudio = elmsPolyBody.get(i).getElementsByClass("poly-audio");
                String quesAudio ="\n"+ elmsPolyAudio.get(0).getElementsByTag("audio").toString();
                quesCont+=quesAudio;
            } catch (Exception e) {
            }
            Elements elmsChoice = elmsWrapper.get(i).getElementsByClass("field");
            elmsChoice.select("span").remove();
            Elements elmsInput = elmsChoice.get(0).getElementsByTag("input");
            Elements elmsType = elmsInput.get(0).getElementsByAttributeStarting("type");
            String type = elmsType.first().attr("type");
            for (int j = 0; j < elmsChoice.size(); j++) {
                String choice = elmsChoice.get(j).text();
                list_Ans.add(choice);
            }
            
            Answer answer = new Answer(list_Ans, TYPE_ANSWER.poly_choices, INPUT_TYPE.valueOf(type));
            Questions questions = new Questions(quesNum, quesCont, answer);
            list_quest.add(questions);
        }
        return list_quest;
    }
    
    public static void showAllQuestions( List<Questions> list_quest){
        for (Questions questions : list_quest) {
            System.out.println(questions.getQuesNum());
            System.out.println(questions.getQuesCont());
            Answer answer = questions.getAnswer();
            System.out.println(answer.getInput_type()+" "+answer.getType_answer());
            for (String ansCont : answer.getAnsCont()) {
                System.out.println(ansCont);
            }
            System.out.println("***************************************************");
        }
    }

    public static List<AnswerCorrect> getAnswerCorrect() throws IOException, ParseException{
        List<AnswerCorrect> list_correct = new ArrayList<>();
        
        String rawJson = new String(Files.readAllBytes(Paths.get("quizENG.json")));
        
        JSONParser parser = new JSONParser();

        JSONArray data = (JSONArray) parser.parse(rawJson);
        
        for (int i = 0; i < data.size(); i++) {
            JSONObject jsdt = (JSONObject) data.get(i);
            String q = (String) jsdt.get("q");
            List<String> list_AnsCor = new ArrayList<>();
            try {
                String a = (String) jsdt.get("a");
                list_AnsCor.add(a);
            } catch (Exception e) {
                JSONArray aObj = (JSONArray) jsdt.get("a");
                for (int j = 0; j < aObj.size(); j++) {
                    String AnsCor = (String) aObj.get(j);
                    list_AnsCor.add(AnsCor);
                }
            }
            
            AnswerCorrect answerCorrect = new AnswerCorrect(q, list_AnsCor);
            list_correct.add(answerCorrect);
        }
        return list_correct;
    }
    
    public static void showAllAnswerCorrect(List<AnswerCorrect> list_correct){
        for (AnswerCorrect answerCorrect : list_correct) {
            System.out.println(answerCorrect.getQuesCont());
            System.out.println("answer size: "+answerCorrect.getList_AnsCor().size());
            for (String ansCor : answerCorrect.getList_AnsCor()) {
                System.out.println(ansCor);
            }
            System.out.println("***************************************************");
        }
    }
    
    public static void showAllQuestionsAnswerCorrect(List<Questions> list_quest, List<AnswerCorrect> list_correct){
        for (Questions questions : list_quest) {
            System.out.println(questions.getQuesNum());
            System.out.println(questions.getQuesCont());
            
            Answer answer = questions.getAnswer();
            for (String ansCont : answer.getAnsCont()) {
                System.out.println(ansCont);
            }
            
            for (AnswerCorrect answerCorrect : list_correct) {
                if (questions.getQuesCont().replaceAll("\\W","").equals(answerCorrect.getQuesCont().replaceAll("\\W",""))) {
                    System.out.println("CORRECT ANSWER: ");
                    for (String ansCor : answerCorrect.getList_AnsCor()) {
                        System.out.println(ansCor);
                    }
                    System.out.println("***************************************************");
                }
            }
        }
    }
    
    public static void main(String[] args) throws FileNotFoundException, IOException, ParseException {
//        showAllQuestions(getAllQuestionsInPage("Quiz1"));
//        showAllAnswerCorrect(getAnswerCorrect());
        showAllQuestionsAnswerCorrect(getAllQuestionsInPage("Quiz3"), getAnswerCorrect());
    }
    
}
