package client.mediator;

import server.model.Patient;
import utility.observer.subject.LocalSubject;

import java.rmi.RemoteException;

public interface LocalClientModel extends LocalSubject<Patient, String> {
    boolean login(String cpr, String password) throws RemoteException;
    boolean register(Patient patient) throws RemoteException;
    void close();
}
