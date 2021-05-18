package server.model.validator;

import server.model.domain.faq.Category;

public class FAQValidator {
    public void validateNewFAQ(int id, String question, String answer, Category category) {
        if (id < 0) {
            throw new IllegalArgumentException("Please input a valid id equal to or higher than 1");
        }
        if (question == null || question.isEmpty()) {
            throw new IllegalArgumentException("Please specify the question");
        }
        if (answer == null || answer.isEmpty()) {
            throw new IllegalArgumentException("Please specify the answer");
        }
        if (category == null) {
            throw new IllegalArgumentException("Please specify the category for this FAQ");
        }
    }
}
