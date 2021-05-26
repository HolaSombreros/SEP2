package client.model;

import server.model.domain.user.Administrator;
import server.model.domain.user.Patient;
import utility.observer.subject.LocalSubject;

public interface MessageModel extends LocalSubject<Object, Object> {
    void sendMessage(Patient patient, String message, Administrator administrator);
}
