package server.mediator;

import server.model.domain.faq.Category;
import server.model.domain.faq.FAQList;
import server.model.domain.user.Administrator;
import utility.observer.subject.RemoteSubject;

import java.rmi.Remote;
import java.rmi.RemoteException;

public interface RemoteModel extends RemoteAppointmentModel, RemoteUserModel, RemoteFAQModel
{

}
