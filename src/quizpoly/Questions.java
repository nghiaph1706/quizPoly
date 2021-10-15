
package quizpoly;

public class Questions {
    int quesNum;
    String quesCont;
    Answer answer;

    public Questions(int quesNum, String quesCont, Answer answer) {
        this.quesNum = quesNum;
        this.quesCont = quesCont;
        this.answer = answer;
    }

    public int getQuesNum() {
        return quesNum;
    }

    public void setQuesNum(int quesNum) {
        this.quesNum = quesNum;
    }

    public String getQuesCont() {
        return quesCont;
    }

    public void setQuesCont(String quesCont) {
        this.quesCont = quesCont;
    }

    public Answer getAnswer() {
        return answer;
    }

    public void setAnswer(Answer answer) {
        this.answer = answer;
    }
    
    
}
