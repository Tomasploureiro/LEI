package search;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.ArrayList;
import java.util.List;

class ReliableMulticastServiceImpl extends UnicastRemoteObject implements ReliableMulticastService {
    private List<ReliableMulticastClient> clients;

    protected ReliableMulticastServiceImpl() throws RemoteException {
        super();
        this.clients = new ArrayList<>();
    }

    @Override
    public void sendReliableMessage(String message) throws RemoteException {

        for (ReliableMulticastClient client : clients) {
            try {
                client.receiveMessage(message);
            } catch (RemoteException e) {
                System.out.println("Um dos barrels foi parado!!!!");
            }
        }
    }

    @Override
    public synchronized void registerClient(ReliableMulticastClient client) throws RemoteException {
        clients.add(client);
        System.out.println("Cliente " + " registrado para ReliableMulticastService.");
    }

}
