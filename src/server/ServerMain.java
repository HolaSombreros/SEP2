package server;

import server.mediator.RemoteModel;
import server.mediator.RemoteModelManager;
import server.model.ServerModelServer;
import server.model.ServerModelManagerServer;

import java.net.MalformedURLException;
import java.rmi.RemoteException;

public class ServerMain {
    public static void main(String[] args) throws MalformedURLException, RemoteException
    {
       ServerModelServer serverModel = new ServerModelManagerServer();
       RemoteModel remoteModel = new RemoteModelManager(serverModel);
    }
}
