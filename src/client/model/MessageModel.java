package client.model;

import server.model.domain.user.User;
import utility.observer.subject.LocalSubject;

public interface MessageModel extends LocalSubject<Object, Object> {
    void sendMessage(User user, String message);
}
