package server.mediator;

import server.model.domain.chat.Message;
import server.model.domain.user.Administrator;
import server.model.domain.user.Patient;
import utility.observer.subject.RemoteSubject;

import java.rmi.RemoteException;
import java.util.List;

public interface RemoteMessageModel extends RemoteSubject<Object, Object> {
    void sendMessage(Patient patient, String message, Administrator administrator) throws RemoteException;
    List<Message> getUnreadMessages(Patient patient) throws RemoteException;
    boolean isPatientChatBeingViewed(String cpr) throws RemoteException;
    void lockChat(String cpr, boolean locked) throws RemoteException;
}
