package server.model;

import java.rmi.Remote;
import java.rmi.RemoteException;


public interface LoginModel extends Remote
{
    void login(Patient user) throws RemoteException;
    void sendServerMessage(ServerMessage message) throws RemoteException;
    UsersList getOnlineUsers() throws RemoteException;
}
