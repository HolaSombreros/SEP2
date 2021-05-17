package client.model;

import server.model.domain.faq.FAQList;

public interface Model extends AppointmentModel, UserModel {
    FAQList getFAQList();
    void close();
}
