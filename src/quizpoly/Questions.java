
package quizpoly;

public class Questions {
    private int quesNum;
    private String quesCont;

    public Questions(int quesNum, String quesCont) {
        this.quesNum = quesNum;
        this.quesCont = quesCont;
    }

    public String getQuesCont() {
        return quesCont;
    }

    public void setQuesCont(String quesCont) {
        this.quesCont = quesCont;
    }

    public int getQuesNum() {
        return quesNum;
    }

    public void setQuesNum(int quesNum) {
        this.quesNum = quesNum;
    }

    
}
