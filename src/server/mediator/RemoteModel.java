package server.mediator;

import server.model.domain.faq.FAQContent;

import java.rmi.RemoteException;

public interface RemoteModel extends RemoteAppointmentModel, RemoteUserModel
{
    
    FAQContent getFaqContent() throws RemoteException;
}
