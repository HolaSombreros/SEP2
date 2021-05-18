package client.model;

import server.model.domain.faq.Category;
import server.model.domain.faq.FAQList;
import server.model.domain.user.Administrator;

public interface Model extends AppointmentModel, UserModel {
    void addFAQ(String question, String answer, Category category, Administrator creator);
    FAQList getFAQList();
    void close();
}
