package search;

import java.rmi.Remote;
import java.rmi.RemoteException;
import java.util.*;

public interface GatewayInterface extends Remote {

    void addURL(String url) throws RemoteException;

    Set<String> search(String termos) throws RemoteException;

    Set<String> getBacklinks(String url) throws RemoteException;

    String getStatistics() throws RemoteException;
    
    void reg_barrel(BarrelInterface barrel) throws RemoteException;
}
