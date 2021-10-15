
package quizpoly;

import java.util.ArrayList;
import java.util.List;

public class Answer {
    
    private List<String> list_Ans = new ArrayList<>();
    private TYPE_ANSWER type_answer;
    private INPUT_TYPE input_type;

    public Answer(List<String> list_Ans, TYPE_ANSWER type_answer, INPUT_TYPE input_type) {
        this.list_Ans = list_Ans;
        this.type_answer = type_answer;
        this.input_type = input_type;
    }

    public List<String> getAnsCont() {
        return list_Ans;
    }

    public void setAnsCont(String ansCont) {
        this.list_Ans.add(ansCont);
    }

    public TYPE_ANSWER getType_answer() {
        return type_answer;
    }

    public void setType_answer(TYPE_ANSWER type_answer) {
        this.type_answer = type_answer;
    }

    public INPUT_TYPE getInput_type() {
        return input_type;
    }

    public void setInput_type(INPUT_TYPE input_type) {
        this.input_type = input_type;
    }
    public static enum TYPE_ANSWER{
        choicegroup_capa_inputtype, poly_choices
    }
    public static enum INPUT_TYPE{
        checkbox , text , radio 
    }
}
