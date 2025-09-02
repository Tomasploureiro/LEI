package search;

import java.rmi.registry.LocateRegistry;
import java.util.Scanner;
import java.util.*;
public class Client {

    public static void main(String[] args) {
        try {
            GatewayInterface gateway = (GatewayInterface)
                LocateRegistry.getRegistry("localhost", 1099)
                              .lookup("Gateway");

            Scanner scanner = new Scanner(System.in);
            boolean exit = false;

            while (!exit) {
                System.out.print("""
                    \n=== Googol Client ===
                    1. Indexar novo URL
                    2. Pesquisar termos
                    3. Ver backlinks de um URL
                    4. Ver estatísticas
                    5. Sair
                    Escolha uma opção: """);

                String option = scanner.nextLine();

                switch (option) {
                    case "1" -> {
                        System.out.print("Introduza o URL: ");
                        String url = scanner.nextLine();
                        gateway.addURL(url);
                        System.out.println("URL enviado para indexação.");
                    }

                    case "2" -> {
                        System.out.print("Termos da pesquisa: ");
                        String termos = scanner.nextLine();
                        Set<String> resultado = gateway.search(termos);
                        if (resultado == null) {
                            System.out.println("Nenhum resultado encontrado.");
                        } else {
                            for(String atual:resultado){
                                System.out.println(atual);
                            }
                        }
                    }

                    case "3" -> {
                        System.out.print("Introduza o URL: ");
                        String url = scanner.nextLine();
                        Set<String> resultado = gateway.getBacklinks(url);
                        if (resultado == null) {
                            System.out.println("Nenhum resultado encontrado.");
                        } else {
                            for(String atual:resultado){
                                System.out.println(atual);
                            }
                        }
                    }

                    case "4" -> {
                        String stats = gateway.getStatistics();
                        System.out.println(stats);
                    }

                    case "5" -> {
                        System.out.println("Até à próxima!");
                        exit = true;
                    }

                    default -> System.out.println("Opção inválida. Tente novamente.");
                }
            }

            scanner.close();

        } catch (Exception e) {
            System.err.println("Erro no cliente: " + e.getMessage());
            e.printStackTrace();
        }
    }
}
