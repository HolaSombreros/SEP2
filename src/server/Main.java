package server;

import server.mediator.RemoteModel;
import server.mediator.RemoteModelManager;
import server.model.ServerModel;
import server.model.ServerModelManager;

import java.net.MalformedURLException;
import java.rmi.RemoteException;

public class Main {
    public static void main(String[] args) throws MalformedURLException, RemoteException
    {
       ServerModel serverModel = new ServerModelManager();
       RemoteModel remoteModel = new RemoteModelManager(serverModel);
    }
}
