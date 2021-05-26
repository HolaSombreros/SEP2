package server.mediator;

import server.model.domain.user.Administrator;
import server.model.domain.user.Patient;
import utility.observer.subject.RemoteSubject;

import java.rmi.RemoteException;

public interface RemoteMessageModel extends RemoteSubject<Object, Object> {
    void sendMessage(Patient patient, String message, Administrator administrator) throws RemoteException;
}
