package server.mediator;

import server.model.Patient;
import server.model.ServerMessage;
import server.model.User;
import utility.observer.subject.RemoteSubject;
import java.rmi.RemoteException;

public interface RemoteModel extends RemoteSubject<User, ServerMessage>
{
    User login(String cpr, String password) throws RemoteException;
    User register(User user) throws RemoteException;
    void close() throws RemoteException;
}
