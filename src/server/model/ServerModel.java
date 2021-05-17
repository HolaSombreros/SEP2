package server.model;

import server.model.domain.appointment.*;
import server.model.domain.faq.Category;
import server.model.domain.faq.FAQList;
import server.model.domain.user.Administrator;
import server.model.domain.user.User;
import utility.observer.subject.LocalSubject;

public interface ServerModel extends ServerAppointmentModel, ServerUserModel, LocalSubject<User, Appointment>  {
    void addFAQ(String question, String answer, Category category, Administrator creator);
    FAQList getFAQList();
    void close();
}
