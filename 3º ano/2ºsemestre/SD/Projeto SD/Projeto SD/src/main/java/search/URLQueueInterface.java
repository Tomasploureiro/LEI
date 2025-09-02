package search;

import java.rmi.Remote;
import java.rmi.RemoteException;

public interface URLQueueInterface extends Remote {

    void addURL(String url) throws RemoteException;

    String getNextURL() throws RemoteException;
}
