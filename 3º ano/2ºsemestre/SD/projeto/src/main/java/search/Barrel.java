package search;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.net.DatagramPacket;
import java.net.InetAddress;
import java.net.MulticastSocket;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;

public class Barrel implements BarrelInterface, Serializable {
    public Barrel() throws RemoteException {
        super();
    }

    // Armazenamento dos dados indexados
    static HashMap<String, HashMap<String, String>> info = new HashMap<>();
    static HashMap<String, HashSet<String>> urlUrls = new HashMap<>();
    
    // Índice invertido particionado:
    static HashMap<String, HashSet<String>> wordUrlsAM = new HashMap<>();
    static HashMap<String, HashSet<String>> wordUrlsNZ = new HashMap<>();
    
    // Variáveis para estatísticas
    static HashMap<String, Integer> searchFrequency = new HashMap<>();
    static long totalSearchTime = 0;
    static int searchCount = 0;

    public String MULTICAST_ADDRESS = "224.3.2.1";
    public int PORT = 5250;

    public static void main(String[] args) {
        try {
            Barrel barrel = new Barrel();
            LocateRegistry.createRegistry(5100).rebind("Barrel", barrel);

            System.out.println("Barrel Started");

            File fich = new File("barrel.txt");
            fich.createNewFile();

            System.out.println("Barrel Loaded");

            ExecutorService executorBarrels = Executors.newFixedThreadPool(1);
            executorBarrels.execute(new BarrelThread());
            executorBarrels.shutdown();
            try {
                executorBarrels.awaitTermination(5, TimeUnit.SECONDS);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

        } catch (IOException e) {
            System.out.println("Exception in Barrel (Main): " + e);
        }
    }

    public String searchWord(String term) throws RemoteException {
        long startTime = System.currentTimeMillis();
        // Incrementa a frequência da pesquisa:
        searchFrequency.put(term, searchFrequency.getOrDefault(term, 0) + 1);
        
        String msg = "";
        try {
            term += " ";
            String[] words = term.split(" ");
            List<ArrayList<Object>> notOrder = new ArrayList<>();

            // Para cada termo, procurar em ambas as partições:
            for (String word : words) {
                HashSet<String> urls = new HashSet<>();
                if (wordUrlsAM.containsKey(word))
                    urls.addAll(wordUrlsAM.get(word));
                if (wordUrlsNZ.containsKey(word))
                    urls.addAll(wordUrlsNZ.get(word));

                if (!urls.isEmpty()) {
                    for (String url : urls) {
                        HashMap<String, String> infoUrl = info.get(url);
                        HashSet<String> urlsPoint = urlUrls.get(url);
                        int count = (urlsPoint != null ? urlsPoint.size() : 0);
                        String title = infoUrl.get("title");
                        String quote = infoUrl.get("quote");
                        String aux = title + "\n" + url + "\n" + quote + "\n\n";
                        notOrder.add(new ArrayList<Object>(List.of(aux, count)));
                    }
                    
                    List<ArrayList<Object>> inOrder = notOrder.stream()
                        .sorted((lista1, lista2) -> ((Integer) lista2.get(1)).compareTo((Integer) lista1.get(1)))
                        .collect(Collectors.toList());
                    
                    for (ArrayList<Object> list : inOrder) {
                        msg += list.get(0);
                    }
                } else {
                    msg = "Nenhum resultado encontrado";
                }
            }
        } catch (Exception e) {
            System.out.println("Exception in Barrel (searchWord): " + e);
        }
        long searchTime = System.currentTimeMillis() - startTime;
        totalSearchTime += searchTime;
        searchCount++;
        return msg;
    }

    public String searchUrl(String url) throws RemoteException {
        String msg = "";
        try {
            HashSet<String> point = urlUrls.get(url);
            if (point != null) {
                for (String u : point) {
                    msg += u + "\n";
                }
                msg += "\n";
            } else {
                msg += "Sem URLS associados.\n\n";
            }
        }  catch (Exception e) {
            System.out.println("Exception in Barrel (searchUrl): " + e);
        }
        return msg;
    }

    public Object readObject(String filename) {
        Object obj = null;
        try {
            File fich = new File(filename);
            try (FileInputStream fis = new FileInputStream(fich);
                    ObjectInputStream ois = new ObjectInputStream(fis)) {
                obj = ois.readObject();
            } catch (FileNotFoundException e) {
                System.out.println("Erro de abertura do ficheiro.");
            } catch (IOException e) {
                System.out.println("Erro a ler ficheiro.");
            } catch (ClassNotFoundException e) {
                System.out.println("Erro a converter para objeto.");
            }
        } catch (Exception e) {
            System.out.println("Exception in Barrel (readObject): " + e);
        }
        return obj;
    }

    public String getStatistics() throws RemoteException {
        StringBuilder stats = new StringBuilder();
        stats.append("Estatísticas do Barrel:\n");
        stats.append("Número de URLs indexados: " + info.size() + "\n");
        stats.append("Número de palavras (A-M): " + wordUrlsAM.size() + "\n");
        stats.append("Número de palavras (N-Z): " + wordUrlsNZ.size() + "\n");
        if (searchCount > 0) {
            long avgTimeDecisecs = (totalSearchTime / searchCount) / 100;
            stats.append("Tempo médio de resposta (décimas de segundo): " + avgTimeDecisecs + "\n");
        }
        stats.append("Top 10 pesquisas:\n");
        searchFrequency.entrySet().stream()
            .sorted((e1, e2) -> e2.getValue().compareTo(e1.getValue()))
            .limit(10)
            .forEach(e -> stats.append(e.getKey() + " : " + e.getValue() + "\n"));
        return stats.toString();
    }

    private static class BarrelThread implements Runnable {
        public String MULTICAST_ADDRESS = "224.3.2.1";
        public int PORT = 5250;
        File fich;
        FileWriter fw;

        public BarrelThread() {
        }

        public void run() {
            while (true) {
                System.out.println("Waiting for multicast message...");
                receiveMulticast();
            }
        }

        public void receiveMulticast() {
            MulticastSocket socket = null;
            String msg = "";
            try {
                SearchModuleInterface searchModuleInterface = (SearchModuleInterface) LocateRegistry.getRegistry(5000).lookup("SearchModule");
                socket = new MulticastSocket(5250);
                InetAddress group = InetAddress.getByName(MULTICAST_ADDRESS);
                socket.joinGroup(group);

                byte[] buffer = new byte[999999999];

                DatagramPacket packet = new DatagramPacket(buffer, buffer.length);
                socket.receive(packet);

                System.out.println("Received packet from " + packet.getAddress().getHostAddress() + ":"
                        + packet.getPort() + " with message:");
                String received = new String(packet.getData(), 0, packet.getLength());
                String[] data = received.split("\\|\\|\\|");

                info.put(data[0], new HashMap<String, String>());
                info.get(data[0]).put("title", data[1]);
                info.get(data[0]).put("quote", data[2]);

                // Processa palavras e direciona para a partição correta:
                String[] words = data[3].split("[ ,:.?!\"<>«»(){}/#_'|]");
                for (String word : words) {
                    if (word.equals("") || word.trim().isEmpty())
                        continue;
                    char firstChar = Character.toLowerCase(word.charAt(0));
                    if (firstChar >= 'a' && firstChar <= 'm') {
                        if (wordUrlsAM.containsKey(word))
                            wordUrlsAM.get(word).add(data[0]);
                        else {
                            HashSet<String> urls = new HashSet<>();
                            urls.add(data[0]);
                            wordUrlsAM.put(word, urls);
                        }
                    } else if (firstChar >= 'n' && firstChar <= 'z') {
                        if (wordUrlsNZ.containsKey(word))
                            wordUrlsNZ.get(word).add(data[0]);
                        else {
                            HashSet<String> urls = new HashSet<>();
                            urls.add(data[0]);
                            wordUrlsNZ.put(word, urls);
                        }
                    }
                }

                if (data.length == 5) {
                    if (!data[4].isEmpty()) {
                        String[] links = data[4].split(" ");
                        for (String link : links) {
                            if (urlUrls.containsKey(link)) {
                                urlUrls.get(link).add(data[0]);
                            } else {
                                HashSet<String> urls = new HashSet<>();
                                urls.add(data[0]);
                                urlUrls.put(link, urls);
                            }
                            msg += "URL: " + data[0] + " adicionado ao URL: " + link + "\n";
                        }
                    }
                }

                File fichInfo = new File("info.obj");
                File fichWordAM = new File("wordAM.obj");
                File fichWordNZ = new File("wordNZ.obj");
                File fichUrls = new File("urls.obj");
                try (FileOutputStream fos = new FileOutputStream(fichInfo);
                        ObjectOutputStream oos = new ObjectOutputStream(fos)) {
                    oos.writeObject(info);
                } catch (FileNotFoundException e) {
                    System.out.println("Erro a criar ficheiro info");
                } catch (IOException e) {
                    System.out.println("Erro a escrever no ficheiro info.");
                }

                try (FileOutputStream fos = new FileOutputStream(fichWordAM);
                        ObjectOutputStream oos = new ObjectOutputStream(fos)) {
                    oos.writeObject(wordUrlsAM);
                } catch (FileNotFoundException e) {
                    System.out.println("Erro a criar ficheiro wordAM");
                } catch (IOException e) {
                    System.out.println("Erro a escrever no ficheiro wordAM.");
                }
                
                try (FileOutputStream fos = new FileOutputStream(fichWordNZ);
                        ObjectOutputStream oos = new ObjectOutputStream(fos)) {
                    oos.writeObject(wordUrlsNZ);
                } catch (FileNotFoundException e) {
                    System.out.println("Erro a criar ficheiro wordNZ");
                } catch (IOException e) {
                    System.out.println("Erro a escrever no ficheiro wordNZ.");
                }

                try (FileOutputStream fos = new FileOutputStream(fichUrls);
                        ObjectOutputStream oos = new ObjectOutputStream(fos)) {
                    oos.writeObject(urlUrls);
                } catch (FileNotFoundException e) {
                    System.out.println("Erro a criar ficheiro urls");
                } catch (IOException e) {
                    System.out.println("Erro a escrever no ficheiro urls.");
                }
            } catch (IllegalArgumentException e) {
                System.out.println("Exception in Barrel (receiveMulticast) : " + e);
            } catch (IOException e) {
                e.printStackTrace();
            } catch (NotBoundException e1) {
                e1.printStackTrace();
            } finally {
                socket.close();
            }
        }
    }
}
