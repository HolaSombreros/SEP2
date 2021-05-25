package server.mediator;

import server.model.domain.chat.Message;
import server.model.domain.user.User;
import utility.observer.subject.RemoteSubject;

public interface RemoteMessageModel extends RemoteSubject<Object, Object> {
}
