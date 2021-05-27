package client.model;

import server.model.domain.chat.Message;
import server.model.domain.user.Administrator;
import server.model.domain.user.Patient;
import utility.observer.subject.LocalSubject;

import java.util.List;

public interface MessageModel extends LocalSubject<Object, Object> {
    void sendMessage(Patient patient, String message, Administrator administrator);
    List<Message> getUnreadMessages(Patient patient);
    boolean isPatientChatBeingViewed(String cpr);
    void lockChat(String cpr, boolean locked);
}
