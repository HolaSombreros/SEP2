package server.model.domain.faq;

import java.io.Serializable;

public class FAQ implements Serializable {
    private String question;
    private String answer;
    
    public FAQ(String question, String answer) {
        this.question = question;
        this.answer = answer;
    }
    
    public String getQuestion() {
        return question;
    }
    
    public String getAnswer() {
        return answer;
    }
    
    @Override
    public boolean equals(Object obj) {
        if (!(obj instanceof FAQ)) {
            return false;
        }
        FAQ faq = (FAQ) obj;
        return question.equals(faq.question) && answer.equals(faq.answer);
    }
}
