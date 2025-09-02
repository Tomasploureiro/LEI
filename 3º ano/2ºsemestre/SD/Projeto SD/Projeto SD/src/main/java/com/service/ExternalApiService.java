package com.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import search.URLQueueInterface;

import java.rmi.RemoteException;
import java.util.*;

@Service
public class ExternalApiService {

    @Autowired
    private RestTemplate restTemplate;

    @Autowired
    private URLQueueInterface urlQueue;

    @Value("${gemini.api.key:}")
    private String geminiApiKey;

    // Métodos para interação com a API do Hacker News
    public void indexHackerNewsTopStories(String searchTerms) throws RemoteException {
        try {
            // Obter IDs das top stories
            Integer[] topStoryIds = restTemplate.getForObject(
                    "https://hacker-news.firebaseio.com/v0/topstories.json",
                    Integer[].class);

            if (topStoryIds != null) {
                int count = 0;

                // Aumentar para 100 histórias para garantir uma busca mais abrangente
                for (int i = 0; i < Math.min(100, topStoryIds.length); i++) {
                    Integer storyId = topStoryIds[i];

                    // Obter detalhes da história do Hacker News
                    Map<String, Object> story = restTemplate.getForObject(
                            "https://hacker-news.firebaseio.com/v0/item/" + storyId + ".json",
                            Map.class);

                    if (story != null && ("story".equals(story.get("type")) || "job".equals(story.get("type")))) {
                        String title = (String) story.getOrDefault("title", "");
                        String text = (String) story.getOrDefault("text", "");
                        String storyUrl = (String) story.get("url");

                        // Verificar se o título ou o texto contém pelo menos um dos termos de pesquisa
                        if (containsAnyTerm(title.toLowerCase(), searchTerms.toLowerCase()) || containsAnyTerm(text.toLowerCase(), searchTerms.toLowerCase())) {
                            if (storyUrl != null && !storyUrl.isEmpty()) {
                                urlQueue.addURL(storyUrl);
                                count++;
                                System.out.println("Indexado do Hacker News: " + storyUrl);
                            } else {
                                System.out.println("História sem URL: " + title);
                            }
                        }
                    }
                }

                System.out.println("Total de " + count + " URLs do Hacker News indexados para os termos: " + searchTerms);
            }
        } catch (Exception e) {
            System.err.println("Erro ao indexar Hacker News stories: " + e.getMessage());
        }
    }

    private boolean containsAnyTerm(String text, String searchTerms) {
        // Dividir a string de termos em palavras individuais
        String[] terms = searchTerms.split("\\s+");
        for (String term : terms) {
            // Verificar se o texto contém cada termo individualmente
            if (text.contains(term)) {
                return true;
            }
        }
        return false;
    }

    // Método para obter análise da Gemini API
    public String getGeminiAnalysis(String searchTerms, List<String> searchResults) {
        if (geminiApiKey == null || geminiApiKey.isEmpty()) {
            return "API key não configurada para Gemini. Configure a chave no application.properties.";
        }

        try {
            StringBuilder prompt = new StringBuilder();
            prompt.append("Faça uma análise contextualizada sobre os seguintes termos de pesquisa: \"")
                  .append(searchTerms)
                  .append("\"\n\nBaseado nos seguintes resultados de pesquisa:\n");

            int count = 0;
            for (String result : searchResults) {
                if (count >= 5) break;
                prompt.append("- ").append(result).append("\n");
                count++;
            }

            prompt.append("\nPor favor, forneça uma análise concisa (máximo 150 palavras).");

            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.APPLICATION_JSON);
            headers.set("x-goog-api-key", geminiApiKey);

            Map<String, Object> requestBody = new HashMap<>();
            
            // Configurando o conteúdo para a API do Gemini
            Map<String, Object> content = new HashMap<>();
            List<Map<String, Object>> parts = new ArrayList<>();
            
            // Adicionando o prompt como parte do conteúdo
            Map<String, Object> textPart = new HashMap<>();
            textPart.put("text", prompt.toString());
            parts.add(textPart);
            
            content.put("parts", parts);
            requestBody.put("contents", List.of(content));
            
            // Configurações do modelo
            Map<String, Object> generationConfig = new HashMap<>();
            generationConfig.put("maxOutputTokens", 200);
            generationConfig.put("temperature", 0.7);
            requestBody.put("generationConfig", generationConfig);

            HttpEntity<Map<String, Object>> entity = new HttpEntity<>(requestBody, headers);

            ResponseEntity<Map> response = restTemplate.exchange(
                    "https://generativelanguage.googleapis.com/v1/models/gemini-pro:generateContent",
                    HttpMethod.POST,
                    entity,
                    Map.class
            );

            if (response.getStatusCode() == HttpStatus.OK && response.getBody() != null) {
                Map<String, Object> responseBody = response.getBody();
                List<Map<String, Object>> candidates = (List<Map<String, Object>>) responseBody.get("candidates");
                
                if (candidates != null && !candidates.isEmpty()) {
                    Map<String, Object> candidate = candidates.get(0);
                    Map<String, Object> content_response = (Map<String, Object>) candidate.get("content");
                    
                    if (content_response != null) {
                        List<Map<String, Object>> parts_response = (List<Map<String, Object>>) content_response.get("parts");
                        
                        if (parts_response != null && !parts_response.isEmpty()) {
                            Map<String, Object> part = parts_response.get(0);
                            return (String) part.get("text");
                        }
                    }
                }
            }

            return "Não foi possível obter análise do Gemini.";
        } catch (Exception e) {
            System.err.println("Erro ao obter análise do Gemini: " + e.getMessage());
            return "Erro ao comunicar com a API do Gemini: " + e.getMessage();
        }
    }
}