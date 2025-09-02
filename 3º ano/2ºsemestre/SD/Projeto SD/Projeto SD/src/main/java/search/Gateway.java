package search;

import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.rmi.server.UnicastRemoteObject;
import java.util.*;

public class Gateway extends UnicastRemoteObject implements GatewayInterface {

    private static final int GATEWAY_PORT = 1099;
    private static final int MULTICAST_PORT = 1098;
    private static ReliableMulticastService multicastService;

    private final List<BarrelInterface> barrels = Collections.synchronizedList(new ArrayList<>());

    protected Gateway() throws RemoteException {
        super();
    }

    public static void main(String[] args) {
        System.setProperty("java.rmi.server.hostname", "172.20.10.3");
        try {
            Gateway gateway = new Gateway();

            Registry registry = LocateRegistry.createRegistry(GATEWAY_PORT);
            registry.rebind("Gateway", gateway);

            multicastService = new ReliableMulticastServiceImpl();
            LocateRegistry.createRegistry(MULTICAST_PORT).rebind("Multicast", multicastService);

            System.out.println("Gateway ativo na porta " + GATEWAY_PORT);
        } catch (Exception e) {
            System.err.println("Erro ao iniciar Gateway: " + e.getMessage());
            e.printStackTrace();
        }
    }

    @Override
    public void addURL(String url) throws RemoteException {
        try {
            Registry reg = LocateRegistry.getRegistry(GATEWAY_PORT);
            URLQueueInterface queue = (URLQueueInterface) reg.lookup("URLQueue");
            queue.addURL(url);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public Set<String> search(String termos) throws RemoteException {
        synchronized (barrels) {
            for (BarrelInterface barrel : barrels) {
                try {
                    Set<String> resultado = barrel.search(termos);
                    if (resultado != null) {
                        return resultado;
                    }
                } catch (RemoteException e) {
                    System.err.println("Barrel indisponível, tentando próximo...");
                }
            }
        }
        return new HashSet<String>();
    }
    

    @Override
    public Set<String> getBacklinks(String url) throws RemoteException {
        synchronized (barrels) {
            for (BarrelInterface barrel : barrels) {
                try {
                    Set<String> resposta = barrel.getBacklinks(url);
                    if (resposta != null ) {
                        return resposta;
                    }
                } catch (RemoteException e) {
                    System.err.println("Barrel indisponível. Tentando próximo...");
                }
            }
        }
        return new HashSet<String>();
    }

    @Override
    public String getStatistics() throws RemoteException {
        StringBuilder stats = new StringBuilder();
        synchronized (barrels) {
            if (barrels.isEmpty()) {
                return "Nenhum Barrel registado no sistema.";
            }

            int barrelNum = 1;
            for (BarrelInterface barrel : barrels) {
                try {
                    stats.append("\n--- Estatísticas Barrel #").append(barrelNum++).append(" ---\n");
                    stats.append(barrel.getStatistics()).append("\n");
                } catch (RemoteException e) {
                    stats.append("\n--- Barrel indisponível ---\n");
                    System.err.println("Falha ao obter estatísticas de um Barrel: " + e.getMessage());
                }
            }
        }
        return stats.toString();
    }

    @Override
    public void reg_barrel(BarrelInterface barrel) throws RemoteException {
        barrels.add(barrel);
        System.out.println("Novo Barrel registado. Total de barrels ativos: " + barrels.size());
    }
}
