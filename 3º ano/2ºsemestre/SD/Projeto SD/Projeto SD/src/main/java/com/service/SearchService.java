package com.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import search.GatewayInterface;
import search.URLQueueInterface;

import java.rmi.RemoteException;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

@Service
public class SearchService {

    @Autowired
    private GatewayInterface gateway;

    @Autowired
    private URLQueueInterface urlQueue;

    @Autowired
    private SimpMessagingTemplate messagingTemplate;

    private String lastStats = "";
    private final ConcurrentHashMap<String, Set<String>> searchCache = new ConcurrentHashMap<>();

    public void addURL(String url) throws RemoteException {
        gateway.addURL(url);
    }

    public Set<String> search(String terms) throws RemoteException {
        // Verificar se já está em cache
        if (searchCache.containsKey(terms)) {
            return searchCache.get(terms);
        }

        long startTime = System.currentTimeMillis();
        Set<String> results = gateway.search(terms);
        long endTime = System.currentTimeMillis();

        // Armazenar em cache
        if (results != null && !results.isEmpty()) {
            searchCache.put(terms, results);
        }

        // Enviar estatísticas atualizadas via WebSocket
        sendUpdatedStats();

        System.out.println("Tempo de pesquisa: " + (endTime - startTime) + "ms");
        return results;
    }

    public Set<String> getBacklinks(String url) throws RemoteException {
        return gateway.getBacklinks(url);
    }

    public String getStatistics() throws RemoteException {
        return gateway.getStatistics();
    }

    @Scheduled(fixedRate = 10000) // Verificar por atualizações a cada 10 segundos
    public void checkForUpdates() throws RemoteException {
        sendUpdatedStats();
    }

    public void sendUpdatedStats() {
        try {
            String currentStats = gateway.getStatistics();
            
            // Se as estatísticas mudaram, envia para todos os clientes conectados
            if (!currentStats.equals(lastStats)) {
                lastStats = currentStats;
                
                // Formatar as estatísticas para envio via WebSocket
                Map<String, Object> statsMap = parseStatistics(currentStats);
                messagingTemplate.convertAndSend("/topic/statistics", statsMap);
            }
        } catch (RemoteException e) {
            System.err.println("Erro ao obter estatísticas: " + e.getMessage());
        }
    }
    
    private Map<String, Object> parseStatistics(String statsText) {
        Map<String, Object> statsMap = new HashMap<>();
        
        // Exemplo simples de análise das estatísticas
        statsMap.put("rawStats", statsText);
        
        // Análise mais detalhada pode ser implementada aqui,
        // extraindo informações específicas do texto de estatísticas
        
        return statsMap;
    }
}