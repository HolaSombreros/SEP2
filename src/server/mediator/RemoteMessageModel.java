package server.mediator;

import server.model.domain.chat.Message;
import server.model.domain.user.User;
import utility.observer.subject.RemoteSubject;

import java.rmi.RemoteException;

public interface RemoteMessageModel extends RemoteSubject<Object, Object> {
    void sendMessage(User user, String message) throws RemoteException;

}
