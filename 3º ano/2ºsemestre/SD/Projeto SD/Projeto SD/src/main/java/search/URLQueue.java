package search;

import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.server.UnicastRemoteObject;
import java.util.concurrent.ConcurrentLinkedQueue;

public class URLQueue extends UnicastRemoteObject implements URLQueueInterface {

    private static final int PORT = 1099;

    private final ConcurrentLinkedQueue<String> urlQueue;

    protected URLQueue() throws RemoteException {
        super();
        urlQueue = new ConcurrentLinkedQueue<>();
    }

    public static void main(String[] args) {
        System.setProperty("java.rmi.server.hostname", "172.20.10.3");
        try {
            LocateRegistry.getRegistry(PORT).rebind("URLQueue", new URLQueue());
            System.out.println("URLQueue RMI registada na porta " + PORT);
        } catch (RemoteException e) {
            System.err.println("Erro ao iniciar a URLQueue: " + e.getMessage());
        }
    }

    @Override
    public void addURL(String url) throws RemoteException {
        urlQueue.add(url);
        System.out.println("URL adicionado à fila: " + url);
    }

    @Override
    public String getNextURL() throws RemoteException {
        String next = urlQueue.poll();
        if (next != null) {
            System.out.println("URL retirado da fila: " + next);
            return next;
        } else {
            System.out.println("Fila de URLs vazia.");
            return null;
        }
    }
}
