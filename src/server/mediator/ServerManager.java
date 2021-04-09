package server.mediator;

import server.model.*;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.sql.SQLException;
import java.util.ArrayList;

public class ServerManager extends UnicastRemoteObject implements ServerModel
{

    public ServerManager() throws RemoteException
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

    @Override public void register(String cpr, String password, String firstName, String middleName, String lastName, Address address, String phone, String email)
    {

    }
}
