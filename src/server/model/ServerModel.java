package server.model;

import server.model.domain.appointment.*;
import server.model.domain.faq.FAQContent;
import server.model.domain.faq.FAQList;
import server.model.domain.user.User;
import utility.observer.subject.LocalSubject;

public interface ServerModel extends ServerAppointmentModel, ServerUserModel, LocalSubject<User, Appointment>  {
    FAQContent getFaqContent();
    void close();
}
