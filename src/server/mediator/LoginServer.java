package server.mediator;

import server.model.*;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;

public class LoginServer extends UnicastRemoteObject implements LoginModel
{

    public LoginServer() throws RemoteException
    {
        super();

    }

    @Override
    public void login(Patient user) throws RemoteException
    {

    }

    @Override
    public void sendServerMessage(ServerMessage message) throws RemoteException
    {

    }

    @Override
    public UsersList getOnlineUsers() throws RemoteException
    {
        return null;
    }
}
