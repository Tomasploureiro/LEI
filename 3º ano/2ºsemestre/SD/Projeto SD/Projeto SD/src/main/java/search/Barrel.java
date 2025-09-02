package search;

import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.rmi.server.UnicastRemoteObject;
import java.util.*;

public class Barrel extends UnicastRemoteObject implements BarrelInterface, ReliableMulticastClient {

    private static ReliableMulticastService multicast;
    private static final int MULTICAST_PORT = 1098;
    private static final int GATEWAY_PORT = 1099;

    private final HashMap<String, HashSet<String>> invertedIndex = new HashMap<>();
    private final HashMap<String, HashSet<String>> backlinks = new HashMap<>();

    private final Map<String, Integer> searchFrequencies = new HashMap<>();
    private long totalSearchTime = 0;
    private int searchCount = 0;

    protected Barrel() throws RemoteException {
        super();
        reg_Barrel();
    }

    public void reg_Barrel() {
        try {
            Registry reg = LocateRegistry.getRegistry(GATEWAY_PORT);
            GatewayInterface gateway = (GatewayInterface) reg.lookup("Gateway");
            gateway.reg_barrel(this);

            Registry multicastRegistry = LocateRegistry.getRegistry(MULTICAST_PORT);
            multicast = (ReliableMulticastService) multicastRegistry.lookup("Multicast");

            multicast.registerClient(this);
            System.out.println("Barrel registado no Gateway e no Multicast.");

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        try {
            new Barrel();
            System.out.println("Barrel iniciado com sucesso.");
        } catch (RemoteException e) {
            System.err.println("Erro ao iniciar o Barrel: " + e.getMessage());
        }
    }


    @Override
    public synchronized Set<String> search(String termos) throws RemoteException {
    
        String[] words = termos.toLowerCase().split("\\W+");
        Set<String> resultsSet = null;
    
        for (String w : words) {
            if (invertedIndex.containsKey(w)) {
                if (resultsSet == null) {
                    resultsSet = new HashSet<>(invertedIndex.get(w));
                } else {
                    resultsSet.retainAll(invertedIndex.get(w));
                }
            } else {
                return new HashSet<>();
            }
    
            searchFrequencies.put(w, searchFrequencies.getOrDefault(w, 0) + 1);
        }
    
        return resultsSet != null ? resultsSet : new HashSet<>();
    }
    

    @Override
    public synchronized Set<String> getBacklinks(String url) {
        Set<String> apontadores = backlinks.getOrDefault(url, new HashSet<>());

        if (apontadores.isEmpty()) {
            apontadores.add("Sem páginas a apontar para este URL.");
        }

        return apontadores;
    }


    @Override
    public synchronized String getStatistics() {
        StringBuilder stats = new StringBuilder();

        stats.append("Palavras no índice: ").append(invertedIndex.size()).append("\n");
        stats.append("Backlinks registados: ").append(backlinks.size()).append("\n");

        if (searchCount > 0) {
            stats.append("Tempo médio de resposta (ms): ")
                    .append(totalSearchTime / searchCount).append("\n");
        }

        stats.append("\nTop 10 termos mais pesquisados:\n");
        searchFrequencies.entrySet().stream()
                .sorted((a, b) -> b.getValue().compareTo(a.getValue()))
                .limit(10)
                .forEach(e -> stats.append("- ").append(e.getKey()).append(": ").append(e.getValue()).append("\n"));

        return stats.toString();
    }

    public void processarUrls(String message){
        String[] parts = message.split(" ");
        if (parts.length == 3) {
            backlinks.computeIfAbsent(parts[1], k -> new HashSet<>()).add(parts[2]);
        }
    }

    public void processarPalavras(String message){
        String[] parts = message.split(";");
        if (parts.length == 2) {
            invertedIndex.computeIfAbsent(parts[0], k -> new HashSet<>()).add(parts[1]);
        }
    }

        

    @Override
    public synchronized void receiveMessage(String message) throws RemoteException {
        System.out.println(message);
        if(message.startsWith("urls/")){
            processarUrls(message);
    }   else{
            processarPalavras(message);
        }
    }

}
