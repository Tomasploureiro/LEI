package search;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.rmi.Naming;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.util.*;

public class Downloader {

    private static final int PORT = 1098;
    private static final int PORT2 = 1099;
    private static final String URLQUEUE_HOST = "localhost";
    private static ReliableMulticastService multicast;
    private static URLQueueInterface urlQueue;

    private static final Set<String> stopWords = new HashSet<>(Arrays.asList(
        "a", "o", "as", "os", "de", "do", "da", "dos", "das",
        "e", "em", "um", "uma", "uns", "umas", "com", "para", "por",
        "que", "como", "se", "no", "na", "n4os", "nas", "ao", "à", "aos", "às",
        "sua", "suas", "seu", "seus", "meu", "minha", "teu", "tua",
        "eu", "você", "nós", "vocês", "eles", "elas", "é", "foi", "ser", "são"
    ));

    public static void main(String[] args) {
        try {
            Registry registry = LocateRegistry.getRegistry(PORT2);
            urlQueue = (URLQueueInterface) registry.lookup("URLQueue");

            Registry registry2 = LocateRegistry.getRegistry(PORT);
            multicast = (ReliableMulticastService) registry2.lookup("Multicast");

            System.out.println("Downloader iniciado e conectado à URLQueue.");

            Thread thread1 = new Thread(() -> {
                try {
                    while (true) {
                        String url = urlQueue.getNextURL();
                        processURL(url);
                    }
                } catch (Exception e) {
                    System.err.println("Erro ao processar URL na thread 1: " + e.getMessage());
                }
            });

            Thread thread2 = new Thread(() -> {
                try {
                    while (true) {
                        String url = urlQueue.getNextURL();
                        processURL(url);
                    }
                } catch (Exception e) {
                    System.err.println("Erro ao processar URL na thread 2: " + e.getMessage());
                }
            });

            thread1.start();
            thread2.start();

            thread1.join();
            thread2.join();

        } catch (Exception e) {
            System.err.println("Erro no Downloader: " + e.getMessage());
            e.printStackTrace();
        }
    }

    private static void processURL(String url) {
        try {
            
            Document doc = Jsoup.connect(url).get();

            String title = doc.title();
            String text = doc.text();
            String citacao = doc.select("meta[name=description]").attr("content");

            Elements links = doc.select("a[href]");
            Set<String> extractedLinksSet = new HashSet<>();
            for (Element link : links) {
                String foundLink = link.absUrl("href");
                extractedLinksSet.add(foundLink);
                urlQueue.addURL(foundLink);
                try {
                    String mens = "urls/ " + foundLink +" " + url;
                    System.out.println(Thread.currentThread().getName() + " enviando: " + mens);
                    multicast.sendReliableMessage(mens);
                } catch (RemoteException e) {
                    e.printStackTrace();
                    System.out.println("Erro ao enviar mensagem via ReliableMulticastService!");
                }
            }

            palavras(url, text, title, citacao);

        } catch (Exception e) {
            
        }
    }

    private static void palavras(String url, String text, String title, String cit) {
        StringTokenizer tokenizer = new StringTokenizer(text, " \t\n\r\f.,;:!?()[]\"'");
        while (tokenizer.hasMoreTokens()) {
            String word = tokenizer.nextToken().toLowerCase();
            if (word.matches("[a-z]+") && !stopWords.contains(word)) {
                String mens = word + ";URL: " + url + " Titulo: " + title + " Citacao: " + cit;
                try {
                    System.out.println(Thread.currentThread().getName() + " enviando: " + mens);
                    multicast.sendReliableMessage(mens);
                } catch (RemoteException e) {
                    System.out.println("Erro ao enviar mensagem via ReliableMulticastService!");
                }
            }
        }
    }
}
