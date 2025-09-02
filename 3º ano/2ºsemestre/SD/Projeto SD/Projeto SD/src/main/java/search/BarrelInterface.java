package search;

import java.rmi.Remote;
import java.rmi.RemoteException;
import java.util.*;

public interface BarrelInterface extends Remote {


    // Método chamado pelo Gateway para pesquisar termos
    Set<String> search(String termos) throws RemoteException;

    // Método chamado pelo Gateway para obter backlinks
    Set<String> getBacklinks(String url) throws RemoteException;

    // Método chamado pelo Gateway para obter estatísticas
    String getStatistics() throws RemoteException;

    // Método chamado pelo multicast para enviar dados do Downloader via String
    void receiveMessage(String message) throws RemoteException;
}
