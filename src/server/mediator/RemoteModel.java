package server.mediator;

import server.model.Patient;
import server.model.ServerMessage;
import utility.observer.subject.RemoteSubject;
import java.rmi.RemoteException;

public interface RemoteModel extends RemoteSubject<Patient, ServerMessage>
{
    Patient login(String cpr, String password) throws RemoteException;
    Patient register(Patient patient) throws RemoteException;
    void close() throws RemoteException;
}
