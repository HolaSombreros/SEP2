package server.model;

import java.rmi.Remote;
import java.rmi.RemoteException;
import java.sql.SQLException;
import java.util.ArrayList;


public interface ServerModel extends Remote
{
    void login(Patient user) throws RemoteException;
    void sendServerMessage(ServerMessage message) throws RemoteException;
    UsersList getRegisteredUsers() throws RemoteException;
    Patient readByCpr(String cpr) throws  RemoteException, SQLException;
    UsersList loadFromDatabasePatients() throws RemoteException, SQLException;
    void register(String cpr, String password, String firstName, String middleName, String lastName, Address address, String phone, String email);
}
