package server.model;

import server.model.domain.faq.FAQ;
import utility.observer.subject.LocalSubject;

public interface ServerModel extends ServerAppointmentModel, ServerUserModel, ServerFAQModel  {

    void close();
}
