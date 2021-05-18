package server.model.validator;

import server.model.domain.faq.Category;
import server.model.domain.user.Administrator;

public class FAQValidator {
    public static void validateNewFAQ(String question, String answer, Category category, Administrator creator) {
        if (question == null || question.isEmpty()) {
            throw new IllegalArgumentException("Please specify the question");
        }
        if (answer == null || answer.isEmpty()) {
            throw new IllegalArgumentException("Please specify the answer");
        }
        if (category == null) {
            throw new IllegalArgumentException("Please specify the category for this FAQ");
        }
        if (creator == null) {
            throw new IllegalStateException("Please specify the creator of this FAQ");
        }
    }
}
