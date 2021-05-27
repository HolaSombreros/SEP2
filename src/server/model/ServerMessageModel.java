package server.model;

import server.model.domain.chat.Message;
import server.model.domain.user.Administrator;
import server.model.domain.user.Patient;
import utility.observer.subject.LocalSubject;

import java.rmi.RemoteException;
import java.util.List;

public interface ServerMessageModel extends LocalSubject<Object, Object> {
    void sendMessage(Patient patient, String message, Administrator administrator) throws RemoteException;
    List<Message> getUnreadMessages(Patient patient);
    boolean isPatientChatBeingViewed(String cpr);
    void lockChat(String cpr, boolean locked);
}
