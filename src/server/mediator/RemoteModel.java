package server.mediator;

import server.model.domain.faq.FAQList;

import java.rmi.RemoteException;

public interface RemoteModel extends RemoteAppointmentModel, RemoteUserModel
{
    FAQList getFAQList() throws RemoteException;
}
