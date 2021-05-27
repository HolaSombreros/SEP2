package server.model.domain.faq;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class FAQList implements Serializable {
    private List<FAQ> questions;

    public FAQList() {
        questions = new ArrayList<>();
    }

    public List<FAQ> getQuestions() {
        return questions;
    }

    public void add(FAQ faq) {
        questions.add(faq);
    }

    public void remove(FAQ faq) {
        questions.remove(faq);
    }

    public int size() {
        return questions.size();
    }

    public FAQ getFAQ(String question, String answer) {
        for (FAQ faq : questions) {
            if (faq.getQuestion().equals(question) && faq.getAnswer().equals(answer)) {
                return faq;
            }
        }
        return null;
    }

    @Override
    public boolean equals(Object obj) {
        if (!(obj instanceof FAQList)) {
            return false;
        }
        FAQList faqList = (FAQList) obj;
        if (questions.size() != faqList.questions.size()) {
            return false;
        }
        for (int i = 0; i < questions.size(); i++) {
            if (!questions.get(i).equals(faqList.questions.get(i))) {
                return false;
            }
        }
        return true;
    }

    @Override
    public String toString() {
        String str = "Questions: " + size();
        for (FAQ faq : questions) {
            str += "\n" + faq.toString();
        }
        return str;
    }
}
