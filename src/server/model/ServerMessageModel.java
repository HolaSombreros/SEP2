package server.model;

import server.model.domain.user.Administrator;
import server.model.domain.user.Patient;
import server.model.domain.user.User;
import utility.observer.subject.LocalSubject;

import java.rmi.RemoteException;

public interface ServerMessageModel extends LocalSubject<Object, Object> {
    void sendMessage(Patient patient, String message, Administrator administrator) throws RemoteException;
}
