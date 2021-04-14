package client.mediator;

import server.model.Patient;
import server.model.ServerMessage;
import utility.observer.subject.LocalSubject;

import java.rmi.RemoteException;

public interface LocalClientModel extends LocalSubject<Patient, ServerMessage> {
    Patient login(String cpr, String password) throws RemoteException;
    Patient register(Patient patient) throws RemoteException;
    void close() throws RemoteException;
}
