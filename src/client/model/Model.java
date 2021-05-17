package client.model;

import server.model.domain.faq.FAQContent;

public interface Model extends AppointmentModel, UserModel {
    FAQContent getFaqContent();
    void close();
}
