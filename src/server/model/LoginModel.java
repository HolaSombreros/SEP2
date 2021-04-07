package server.model;

import java.rmi.Remote;
import java.rmi.RemoteException;
import java.sql.SQLException;


public interface LoginModel extends Remote
{
    void login(Patient user) throws RemoteException;
    void sendServerMessage(ServerMessage message) throws RemoteException;
    UsersList getOnlineUsers() throws RemoteException;
    Patient readByCpr(String cpr) throws  RemoteException, SQLException;

}
