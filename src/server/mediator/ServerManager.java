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


    @Override
    public UsersList getRegisteredUsers() throws RemoteException
    {
        return null;
    }

    @Override
    public Patient readByCpr(String cpr) throws RemoteException, SQLException
    {
        return null;
    }

    @Override
    public ArrayList<Patient> loadFromDatabasePatients() throws RemoteException, SQLException
    {
        return null;
    }
}
