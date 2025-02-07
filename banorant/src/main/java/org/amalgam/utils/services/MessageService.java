package org.amalgam.utils.services;

import java.rmi.Remote;
import java.rmi.RemoteException;

public interface MessageService extends Remote {

    public void logSession(MessageCallback messageCallback) throws RemoteException;

    public void broadcast(MessageCallback messageCallback, String msg) throws RemoteException;
    public void logout(MessageCallback messageCallback) throws RemoteException;
}
