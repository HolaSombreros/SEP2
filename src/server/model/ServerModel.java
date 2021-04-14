package server.model;

import utility.observer.subject.LocalSubject;

import java.rmi.Remote;
import java.rmi.RemoteException;
import java.sql.SQLException;
import java.util.ArrayList;


public interface ServerModel extends LocalSubject<Patient, ServerMessage>
{
    Patient login(String cpr, String password);
    void sendServerMessage(ServerMessage message);
    Patient register(Patient patient);
    void close();
}
