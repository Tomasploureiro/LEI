import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Aplicacao {
    public static void main(String[] args){
        Scanner scanner = new Scanner(System.in);
        List<Gestor> gestores = new ArrayList<>();
        List<Condutor> condutoresPendentes = new ArrayList<>();
        List<Cliente> clientesPendentes = new ArrayList<>();
        List<Condutor> condutoresAprovados = new ArrayList<>();
        List<Cliente> clientesAprovados = new ArrayList<>();
        List<Veiculo> veiculos = new ArrayList<>();
        List<Servico> servicos = new ArrayList<>();

        boolean sair = false;

        while (!sair) {
            System.out.println("### MENU ###");
            System.out.println("1. Registar");
            System.out.println("2. Login");
            System.out.println("3. Sair");
            System.out.print("Escolha uma opção: ");

            int opcao = scanner.nextInt();

            switch (opcao) {
                case 1:
                    if(gestores.isEmpty())
                        realizarRegistoGestor(gestores, condutoresPendentes, clientesPendentes, scanner);
                    else{
                        System.out.println("### CRIAR ENTIDADE ###");
                        System.out.println("1. Criar Gestor");
                        System.out.println("2. Criar Condutor");
                        System.out.println("3. Criar Cliente");
                        System.out.print("opçao: ");
                        int escolha = scanner.nextInt();
                        if (escolha == 1) {
                            realizarRegistoGestor(gestores, condutoresPendentes, clientesPendentes, scanner);
                        } else if (escolha == 2) {
                            adicionarCondutor(condutoresPendentes, scanner);
                        } else if (escolha == 3) {
                            adicionarCliente(clientesPendentes, scanner);
                        }else{
                            System.out.println("Opção inválida.");
                        }
                    }
                    break;
                case 2:
                    Utilizador utilizadorAutenticado = realizarLogin(gestores, condutoresAprovados, clientesAprovados, scanner);
                    if (utilizadorAutenticado instanceof Gestor) {
                        MENU_Gestor((Gestor) utilizadorAutenticado, condutoresPendentes,condutoresAprovados, clientesPendentes, clientesAprovados, veiculos, servicos, scanner);
                    } else if (utilizadorAutenticado instanceof Condutor) {
                        MENU_CONDUTOR((Condutor) utilizadorAutenticado, scanner);
                    } else if (utilizadorAutenticado instanceof Cliente) {
                        MENU_CLIENTE((Cliente) utilizadorAutenticado, servicos, scanner);
                    }
                    break;
                case 3:
                    sair = true;
                    System.out.println("Adeus!");
                    break;
                default:
                    System.out.println("Opção inválida.");
                    break;
            }
        }

        scanner.close();
    }

    public static void clearScreen() {
        System.out.print("\033[H\033[2J");
        System.out.flush();
    }

    public static Gestor realizarRegistoGestor(List<Gestor> gestores, List<Condutor> condutoresPendentes, List<Cliente> clientesPendentes, Scanner scanner) {
        
        System.out.println("### REGISTO DE GESTOR ###");
        System.out.print("Login: ");
        String login = scanner.next();
        System.out.print("Password: ");
        String password = scanner.next();
        System.out.print("Nome: ");
        String nome = scanner.next();
        System.out.print("Email: ");
        String email = scanner.next();
    

        boolean loginExistente = gestores.stream().anyMatch(g -> g.getLogin().equals(login));
        boolean emailExistente = gestores.stream().anyMatch(g -> g.getEmail().equals(email));
    
        if (!loginExistente && !emailExistente) {
            Gestor novoGestor = new Gestor(login,password,nome,true, email);
            gestores.add(novoGestor);
            System.out.println("Gestor registado com sucesso!");
            return novoGestor; 
        } else {
            System.out.println("Login ou Email já existem. Por favor, escolha outros dados.");
        }
    
        return null;
    }

    public static void MENU_Gestor(Gestor gestor, List<Condutor> condutoresPendentes, List<Condutor> condutoresAprovados,List<Cliente> clientesPendentes, List<Cliente> clientesAprovados, List<Veiculo> veiculos, List<Servico> servicos, Scanner scanner) {
        while (true) {
            System.out.println("### Bem Vindo " + gestor.getNome() + " ####");
            System.out.println("1. Aceitar/registrar Condutores");
            System.out.println("2. Aceitar/registrar Clientes");
            System.out.println("5. Aceitar viagens");
            System.out.println("3. Criar Taxi");
            System.out.println("4. Sair do menu");
            System.out.print("Escolha uma opção: ");
    
            int opcao = scanner.nextInt();
    
            switch (opcao) {
                case 1:
                    aceitarRejeitarCondutores(condutoresAprovados,condutoresPendentes,scanner);
                    break;
                case 2:
                    aceitarRejeitarClientes(clientesAprovados, clientesPendentes, scanner);
                    break;
                case 3:
                    adicionarVeiculos(veiculos, scanner);
                    break;
                case 4:
                    System.out.println("Adeus " + gestor.getNome() + ".");
                    return;
                case 5:
                    aceitarViagens(servicos, condutoresAprovados);
                default:
                    System.out.println("Opção inválida.");
                    break;
            }
        }
    }

    public static void MENU_CONDUTOR(Condutor condutor, Scanner scanner){
        while (true) {
            System.out.println("### Bem Vindo " + condutor.getNome() + " ####");
            System.out.println("1. Aceitar Servicos");
            System.out.println("2. Consultar Servicos");
            System.out.println("3. Sair");
            System.out.print("Escolha uma opção: ");
    
            int opcao = scanner.nextInt();
    
            switch (opcao) {
                case 1:
                    break;
                case 2:
                    System.out.println("Adeus " + condutor.getNome() + ".");
                    return;
                default:
                    System.out.println("Opção inválida.");
                    break;
            }
        }
    }

    public static void MENU_CLIENTE(Cliente cliente,List<Servico> servicos,Scanner scanner){
        while (true) {
            System.out.println("### Bem Vindo " + cliente.getNome() + " ####");
            System.out.println("1. Pedir Viagem");
            System.out.println("2. Consultar Servicos");
            System.out.println("3. Sair");
            System.out.print("Escolha uma opção: ");
    
            int opcao = scanner.nextInt();
    
            switch (opcao) {
                case 1:
                    servicos.add(criarServico(servicos, scanner)); // serviço do cliente

                    break;
                case 2:

                    break;
                case 3:
                    System.out.println("Adeus " + cliente.getNome() + ".");
                    return;
                default:
                    System.out.println("Opção inválida.");
                    break;
            }
        }
    }

    public static void adicionarVeiculos(List<Veiculo> veiculos, Scanner scanner) {
        while (true) {
            System.out.println("### INSERIR VEICULO ###");
            System.out.print("Marca: ");
            String marca = scanner.nextLine();

            System.out.print("Modelo: ");
            String modelo = scanner.nextLine();

            System.out.print("Matrícula: ");
            int matricula = scanner.nextInt();
            scanner.nextLine();

            System.out.print("Ano de Matrícula: ");
            int anoMatricula = scanner.nextInt();
            scanner.nextLine();

            System.out.print("Distância percorrida: ");
            double distanciaPercorrida = scanner.nextDouble();
            scanner.nextLine();

            Veiculo novoVeiculo = new Veiculo(marca, modelo, matricula, anoMatricula, distanciaPercorrida);
            veiculos.add(novoVeiculo);
        }
    }

    public static Utilizador realizarLogin(List<Gestor> gestores, List<Condutor> condutoresAprovados, List<Cliente> clientesAprovados, Scanner scanner) {
        
        System.out.println("### LOGIN ###");
        System.out.print("Login: ");
        String inputLogin = scanner.next();
        System.out.print("Password: ");
        String inputPassword = scanner.next();

        for (Gestor gestor : gestores) {
            if (gestor.autenticar(inputLogin, inputPassword)) {
                System.out.println("Bem-vindo " + gestor.getNome() + " (Gestor)!");
                return gestor;
            }
        }
    
        for (Condutor condutor : condutoresAprovados) {
            if (condutor.autenticar(inputLogin, inputPassword)) {
                System.out.println("Bem-vindo " + condutor.getNome() + " (Condutor)!");
                return condutor;
            }
        }

        for (Cliente cliente : clientesAprovados) {
            if (cliente.autenticar(inputLogin, inputPassword)) {
                System.out.println("Bem-vindo " + cliente.getNome() + " (Cliente)!");
                return cliente;
            }
        }
    
        System.out.println("Credenciais invalidas.");
        return null;
    }

    public static void aceitarRejeitarCondutores (List<Condutor> condutoresAprovados, List<Condutor> condutoresPendentes, Scanner scanner) {
        if (!condutoresPendentes.isEmpty()) {
            System.out.println("Pedidos de registo de Condutores pendentes:");
            List<Condutor> condutoresRejeitados = new ArrayList<>();
    
            for (Condutor condutor : condutoresPendentes) {
                System.out.println("Login: " + condutor.getLogin() + " | Nome: " + condutor.getNome());
                System.out.print("Aceitar registo? (S/N): ");
                String resposta = scanner.next();
                if (resposta.equalsIgnoreCase("S")) {
                    condutoresAprovados.add(condutor);
                    System.out.println("Registo de Condutor aprovado: " + condutor.getNome());
                } else if(resposta.equalsIgnoreCase("N")){
                    condutoresRejeitados.add(condutor);
                    System.out.println("Registo de Condutor rejeitado: " + condutor.getNome());
                } else {
                    System.out.println("Opcao Invalida!");
                }
            }
    
            condutoresPendentes.removeAll(condutoresAprovados);
            condutoresPendentes.removeAll(condutoresRejeitados);
        } else {
            System.out.println("Não há pedidos de registo de Condutores pendentes.");
        }
    }

    public static void aceitarRejeitarClientes(List<Cliente> clientesAprovados, List<Cliente> clientesPendentes, Scanner scanner) {
        if (!clientesPendentes.isEmpty()) {
            System.out.println("Pedidos de registo de Clientes pendentes:");
            List<Cliente> clientesRejeitados = new ArrayList<>();
    
            for (Cliente cliente : clientesPendentes) {
                System.out.println("Login: " + cliente.getLogin() + " | Nome: " + cliente.getNome());
                System.out.print("Aceitar registo? (S/N): ");
                String resposta = scanner.next();
                if (resposta.equalsIgnoreCase("S")) {
                    clientesAprovados.add(cliente);
                    System.out.println("Registo de Cliente aprovado: " + cliente.getNome());
                } else if(resposta.equalsIgnoreCase("N")){
                    clientesRejeitados.add(cliente);
                    System.out.println("Registo de Cliente rejeitado: " + cliente.getNome());
                } else {
                    System.out.println("Opcao Invalida!");
                }
            }
    
            clientesPendentes.removeAll(clientesAprovados);
            clientesPendentes.removeAll(clientesRejeitados);
        } else {
            System.out.println("Não há pedidos de registo de Clientes pendentes.");
        }
    }

    public static void adicionarCondutor(List<Condutor> condutoresPendentes, Scanner scanner) {
        
        System.out.println("### REGISTO DE CONDUTOR ###");
        System.out.print("Login: ");
        String login = scanner.next();
        System.out.print("Password: ");
        String password = scanner.next();
        System.out.print("Nome: ");
        String nome = scanner.next();
        System.out.print("Email: ");
        String email = scanner.next();
        System.out.print("Latitude: ");
        double latitude = scanner.nextDouble();
        System.out.print("Longitude: ");
        double longitude = scanner.nextDouble();

        Localizacao localizacao = new Localizacao(latitude,longitude);

        boolean loginExistente = condutoresPendentes.stream().anyMatch(f -> f.getLogin().equals(login));
        boolean emailExistente = condutoresPendentes.stream().anyMatch(f -> f.getEmail().equals(email));
    
        if (!loginExistente && !emailExistente) {
            Condutor novoCondutor = new Condutor(login, password, nome, false, email, localizacao);
            condutoresPendentes.add(novoCondutor);
            System.out.println("Pedido de registo de Condutor enviado para aprovação!");
        } else {
            System.out.println("Login/Email já existentes.");
        }
    }

    public static void adicionarCliente(List<Cliente> clientesPendentes, Scanner scanner) {
        
        System.out.println("### REGISTO DE CLIENTE ###");
        System.out.print("Login: ");
        String login = scanner.next();
        System.out.print("Password: ");
        String password = scanner.next();
        System.out.print("Nome: ");
        String nome = scanner.next();
        System.out.print("Email: ");
        String email = scanner.next();
    
        boolean loginExistente = clientesPendentes.stream().anyMatch(c -> c.getLogin().equals(login));
        boolean emailExistente = clientesPendentes.stream().anyMatch(c -> c.getEmail().equals(email));
    
        if (!loginExistente && !emailExistente) {
            Cliente novoCliente = new Cliente(login, password, nome, false, email);
            clientesPendentes.add(novoCliente);
            System.out.println("Pedido de registo de Cliente enviado para aprovação!");
        } else {
            System.out.println("Login/Email já existentes.");
        }
    }


    public static Servico criarServico(List<Servico> servicos, Scanner scanner) {
        System.out.println("### PEDIR SERVICO ###");
        System.out.println("Forneca dados da localizacao...");
    
        System.out.print("Latitude: ");
        double latitude = scanner.nextDouble();
        System.out.print("Longitude: ");
        double longitude = scanner.nextDouble();
        Localizacao localizacao = new Localizacao(latitude, longitude);

        System.out.println("\nForneca dados da data...");
        System.out.print("Dia: ");
        int dia = scanner.nextInt();
        System.out.print("Mês: ");
        int mes = scanner.nextInt();
        System.out.print("Ano: ");
        int ano = scanner.nextInt();
        System.out.print("Hora: ");
        int hora = scanner.nextInt();
        System.out.print("Minuto: ");
        int minuto = scanner.nextInt();
        Data data = new Data(dia, mes, ano, hora, minuto);

        Servico servico = new Servico(localizacao, data, 0, 0);

        scanner.close();

        return servico;
    }

    public static boolean aceitarViagens(List<Servico> servicos, List<Condutor>condutoresAprovados){
        int distancia = 0;
        for (Servico servico: servicos){
            for (Condutor condutor: condutoresAprovados){
                
            }
        }
        return True
    }

}
