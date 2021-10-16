
package quizpoly;

import java.util.ArrayList;
import java.util.List;

public class AnswerCorrect {
    private String quesCont;
    private List<String> list_AnsCor = new ArrayList<>();

    public AnswerCorrect(String quesCont, List<String> list_AnsCor) {
        this.quesCont = quesCont;
        this.list_AnsCor = list_AnsCor;
    }

    public String getQuesCont() {
        return quesCont;
    }

    public void setQuesCont(String quesCont) {
        this.quesCont = quesCont;
    }

    public List<String> getList_AnsCor() {
        return list_AnsCor;
    }

    public void setList_AnsCor(String ansCor) {
        this.list_AnsCor.add(ansCor);
    }

    
    
    
}
