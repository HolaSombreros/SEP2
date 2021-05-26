package server.model;

import server.model.domain.user.User;
import utility.observer.subject.LocalSubject;

import java.rmi.RemoteException;

public interface ServerMessageModel extends LocalSubject<Object, Object> {
    void sendMessage(User user, String message) throws RemoteException;
}
