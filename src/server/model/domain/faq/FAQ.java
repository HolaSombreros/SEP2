package server.model.domain.faq;

import java.io.Serializable;

public class FAQ implements Serializable {
    private int id;
    private String question;
    private String answer;
    private Category category;
    
    public FAQ(int id, String question, String answer, Category category) {
        this.id = id;
        this.question = question;
        this.answer = answer;
        this.category = category;
    }
    
    public int getId() {
        return id;
    }
    
    public String getQuestion() {
        return question;
    }
    
    public String getAnswer() {
        return answer;
    }

    public Category getCategory() {
        return category;
    }

    public void setQuestion(String question)  {
        this.question = question;
    }

    public void setAnswer(String answer) {
        this.answer = answer;
    }

    public void setCategory(Category category) {
        this.category = category;
    }

    @Override
    public boolean equals(Object obj) {
        if (!(obj instanceof FAQ)) {
            return false;
        }
        FAQ faq = (FAQ) obj;
        return id == faq.id && question.equals(faq.question) && answer.equals(faq.answer) && category.equals(faq.category);
    }
    
    @Override
    public String toString() {
        return String.format("[%s] (#%d) %s  -  %s", category.toString(), id, question, answer);
    }
}
