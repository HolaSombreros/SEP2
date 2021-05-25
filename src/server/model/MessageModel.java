package server.model;

import server.model.domain.chat.Message;
import server.model.domain.user.User;
import utility.observer.subject.LocalSubject;

public interface MessageModel extends LocalSubject<User, Message> {
    void sendMessage(User user, Message message);
}