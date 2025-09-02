package com.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import com.service.ExternalApiService;
import com.service.SearchService;

import java.rmi.RemoteException;
import java.util.*;
import java.util.stream.Collectors;

@Controller
public class SearchController {

    @Autowired
    private SearchService searchService;
    
    @Autowired
    private ExternalApiService externalApiService;
    
    @GetMapping("/")
    public String index() {
        return "index";
    }
    
    @GetMapping("/search")
    public String search(@RequestParam(required = false) String query, 
                         @RequestParam(defaultValue = "1") int page,
                         Model model) {
        try {
            if (query != null && !query.trim().isEmpty()) {
                Set<String> results = searchService.search(query);
                
                // Paginação - 10 resultados por página
                List<String> resultsList = new ArrayList<>(results);
                int totalResults = resultsList.size();
                int totalPages = (int) Math.ceil((double) totalResults / 10);
                
                // Garantir que a página está dentro dos limites
                if (page < 1) page = 1;
                if (page > totalPages && totalPages > 0) page = totalPages;
                
                int start = (page - 1) * 10;
                int end = Math.min(start + 10, totalResults);
                
                List<String> pageResults = start < end ? resultsList.subList(start, end) : Collections.emptyList();
                
                model.addAttribute("query", query);
                model.addAttribute("results", pageResults);
                model.addAttribute("totalResults", totalResults);
                model.addAttribute("currentPage", page);
                model.addAttribute("totalPages", totalPages);
                
                // Se houver resultados, obter análise da OpenAI
                // if (!pageResults.isEmpty()) {
                //     String analysis = externalApiService.getGeminiAnalysis(query, pageResults);
                //     model.addAttribute("analysis", analysis);
                // }
            }
        } catch (RemoteException e) {
            model.addAttribute("error", "Erro na pesquisa: " + e.getMessage());
        }
        
        return "search";
    }
    
    @GetMapping("/backlinks")
    public String backlinks(@RequestParam String url, Model model) {
        try {
            Set<String> backlinks = searchService.getBacklinks(url);
            model.addAttribute("url", url);
            model.addAttribute("backlinks", backlinks);
        } catch (RemoteException e) {
            model.addAttribute("error", "Erro ao obter backlinks: " + e.getMessage());
        }
        
        return "backlinks";
    }
    
    @GetMapping("/stats")
    public String statistics(Model model) {
        try {
            String stats = searchService.getStatistics();
            model.addAttribute("statistics", stats);
        } catch (RemoteException e) {
            model.addAttribute("error", "Erro ao obter estatísticas: " + e.getMessage());
        }
        
        return "stats";
    }
    
    @PostMapping("/add-url")
    public String addURL(@RequestParam String url, Model model) {
        try {
            searchService.addURL(url);
            model.addAttribute("message", "URL adicionado para indexação: " + url);
        } catch (RemoteException e) {
            model.addAttribute("error", "Erro ao adicionar URL: " + e.getMessage());
        }
        
        return "index";
    }
    
    @PostMapping("/index-hackernews")
    public String indexHackerNews(@RequestParam String query, Model model) {
        try {
            externalApiService.indexHackerNewsTopStories(query);
            model.addAttribute("message", "URLs relacionados do Hacker News foram adicionados para indexação.");
            return "redirect:/search?query=" + query;
        } catch (Exception e) {
            model.addAttribute("error", "Erro ao indexar do Hacker News: " + e.getMessage());
            return "search";
        }
    }
    
    // Endpoint para WebSocket
    @MessageMapping("/refreshStats")
    @SendTo("/topic/statistics")
    public String refreshStatistics() throws RemoteException {
        return searchService.getStatistics();
    }
}