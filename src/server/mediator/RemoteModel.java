package server.mediator;

import server.model.domain.faq.Category;
import server.model.domain.faq.FAQList;
import server.model.domain.user.Administrator;

import java.rmi.RemoteException;

public interface RemoteModel extends RemoteAppointmentModel, RemoteUserModel
{
    void addFAQ(String question, String answer, Category category, Administrator creator) throws RemoteException;
    FAQList getFAQList() throws RemoteException;
}
