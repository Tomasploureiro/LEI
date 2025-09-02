import java.util.ArrayList;
import java.util.Scanner;

public class PoW {  
     public static void main(String[] args){
        Scanner sc = new Scanner(System.in);
        ArrayList<Personagem> personagens = new ArrayList<>();
        ArrayList<String> nomes = new ArrayList<>();
        for(int n = 1; n<=5; n++){
            nomes.add("Guerreiro" + n);
            nomes.add("Mago" + n);
            nomes.add("Mercenario" + n);
        }
        for(int i = 0; i<5*3; i++){
            personagens.add(new Guerreiro(nomes.get(i), ((int)(Math.random()*100))%2 == 0, "facas"));
            personagens.add(new Mago(nomes.get(i+=1), "sementes de abobora"));
            personagens.add(new Mercenario(nomes.get(i+=1),"pedra", 10));
        }
        menu(personagens, sc);
    
    }

    public static void menu0(){
        System.out.print(" ____________________________________________"  + "\n" +
                         "|                                            |" + "\n" +
                         "|                   PoW                      |" + "\n" +
                         "|____________________________________________|" + "\n" +
                         "|                                            |" + "\n" +
                         "|   1-Adicionar Personagem                   |" + "\n" +
                         "|   2-Aumentar Experiencia                   |" + "\n" +
                         "|   3-Ver todos os personagens               |" + "\n" +
                         "|   4-Ver personagens por experiencia        |" + "\n" +
                         "|   5-Ver personagens por equipamento        |" + "\n" +
                         "|   6-Menu                                   |" + "\n" +
                         "|   7-Fechar                                 |" + "\n" +
                         "\\____________________________________________/"+ "\n" +
                         "/Opcao: ");
    }

    public static void pnome(){
        System.out.print(" ____________________________________________" + "\n" +
                         "|       Digite o nome do personagem:         |" + "\n" +
                         "\\____________________________________________/" + "\n" +
                         "/Nome: ");
    }

    public static void parmacurta(){
        System.out.print(" ____________________________________________"  + "\n" +
                         "|                                            |" + "\n" +
                         "|                 Arma Curta                 |" + "\n" +
                         "|____________________________________________|" + "\n" +
                         "|                                            |" + "\n" +
                         "|   1-Facas                                  |" + "\n" +
                         "|   2-Machados                               |" + "\n" +
                         "|   3-Espadas                                |" + "\n" +
                         "|   4-Nenhum                                 |" + "\n" +
                         "\\____________________________________________/"+ "\n" +
                         "/Opcao: ");
    }

    public static void parmadura(){
        System.out.print(" ____________________________________________"  + "\n" +
                         "|                                            |" + "\n" +
                         "|                  Armadura                  |" + "\n" +
                         "|____________________________________________|" + "\n" +
                         "|                                            |" + "\n" +
                         "|   1-Sim                                    |" + "\n" +
                         "|   2-Nao                                    |" + "\n" +
                         "|   3-Nenhum                                 |" + "\n" +
                         "\\____________________________________________/"+ "\n" +
                         "/Opcao: ");
    }

    public static void pmochila(){
        System.out.print(" ____________________________________________"  + "\n" +
                         "|                                            |" + "\n" +
                         "|                  Mochila                   |" + "\n" +
                         "|____________________________________________|" + "\n" +
                         "|                                            |" + "\n" +
                         "|   1-Sementes                               |" + "\n" +
                         "|   2-Folhas                                 |" + "\n" +
                         "|   3-Nenhum                                 |" + "\n" +
                         "\\____________________________________________/"+ "\n" +
                         "/Opcao: ");
    }
    public static void parmalonga(){
        System.out.print(" ____________________________________________"  + "\n" +
                         "|                                            |" + "\n" +
                         "|                 Arma Longa                 |" + "\n" +
                         "|____________________________________________|" + "\n" +
                         "|                                            |" + "\n" +
                         "|   1-Pedras                                 |" + "\n" +
                         "|   2-Arcos                                  |" + "\n" +
                         "|   3-Pistolas                               |" + "\n" +
                         "|   4-Nenhum                                 |" + "\n" +
                         "\\____________________________________________/"+ "\n" +
                         "/Opcao: ");        
    }
    public static void ppersonagens(){
        System.out.print(" ____________________________________________"  + "\n" +
                         "|                                            |" + "\n" +
                         "|                 Personagem                 |" + "\n" +
                         "|____________________________________________|" + "\n" +
                         "|                                            |" + "\n" +
                         "|   1-Guerreiro                              |" + "\n" +
                         "|   2-Mago                                   |" + "\n" +
                         "|   3-Mercenario                             |" + "\n" +
                         "\\____________________________________________/"+ "\n" +
                         "/Opcao: ");
    }

    public static void criarPersonagem(ArrayList<Personagem>personagens, Scanner sc){
        String nome;
        int opc_c1 = 0;
        ppersonagens();
        do{
            try{ 
                    opc_c1 = sc.nextInt();
                } 
                catch (java.util.InputMismatchException e){
                    System.out.println("Opcao invalida");
                    sc.nextLine();
                }
            }while (opc_c1 != 1 && opc_c1 != 2 && opc_c1 != 3);

            switch(opc_c1){
                case 1:
                    pnome();
                    nome = sc.next();
                    parmacurta();
                    int opc_armaCurta = sc.nextInt();
                    String armaCurta = new String();
                    switch(opc_armaCurta){
                        case 1:
                            armaCurta = "facas";
                            break;
                        case 2:
                            armaCurta = "machados";
                            break;
                        case 3:
                            armaCurta = "espadas";
                            break;
                        case 4:
                            armaCurta = "null";
                            break;
                        default:
                            System.out.println("Opcao invalida");
                            break;
                    }
                    parmadura();
                    int opc_armadura = sc.nextInt();
                    boolean armadura = false;
                    switch(opc_armadura){
                        case 1: 
                            armadura = true;
                            break;
                        case 2:
                            armadura = false;
                            break;
                        case 3: 
                            break;
                        default:
                            System.out.println("Opcao invalida");
                            break;
                    }
                    personagens.add(new Guerreiro(nome, armadura, armaCurta));
                    System.out.println("Guerreiro adicionado com sucesso.");
                    break;

                case 2:
                    pnome();
                    nome = sc.next();
                    pmochila();
                    int opc_mochila = sc.nextInt();
                    String mochila = new String();
                    switch(opc_mochila){
                        case 1:
                            mochila = "sementes";
                            break;
                        case 2: 
                            mochila = "folhas";
                            break;
                        case 3:
                            mochila = "null";
                            break;
                        default:
                            System.out.println("Opcao invalida");
                            break;
                    }
                    if(mochila.equals("sementes")){
                        String tiposementes;
                        System.out.println("Insira o tipo de semente");
                        tiposementes = sc.next();
                        mochila = mochila + " de " + tiposementes;
                    }

                    if(mochila.equals("folhas")){
                        String tipofolhas;
                        System.out.println("Insira o tipo de semente");
                        tipofolhas = sc.next();
                        mochila = mochila + " de " + tipofolhas;
                    }
                    
                    personagens.add(new Mago(nome, mochila));
                    System.out.println("Mago adicionado com sucesso.");
                    break;

                case 3:
                    pnome();
                    nome = sc.next();
                    parmalonga();
                    int opc_armaLonga = sc.nextInt();
                    String armaLonga = new String();
                    switch(opc_armaLonga){
                        case 1:
                            armaLonga = "pedras";
                            break;
                        case 2:
                            armaLonga = "arcos";
                            break;
                        case 3:
                            armaLonga = "pistolas";
                            break;
                        case 4:
                            armaLonga = "null";
                            break;
                        default:
                            System.out.println("Opcao invalida");
                            break;
                    }
                    System.out.print(" ____________________________________________" + "\n" +
                                     "|            Quantidade de municao           |" + "\n" +
                                     "\\____________________________________________/" + "\n" +
                                     "/Nome: ");
                    int municao = sc.nextInt();
                    personagens.add(new Mercenario(nome, armaLonga, municao));
                    break;
                default:
                    System.out.println("Opcao invalida");
                    break; 
            }
    }

    public static void menu(ArrayList<Personagem>personagens, Scanner sc){
        int opc = 0;
        int opc_case3 = 0;
        menu0();
        while(opc != 7){
            try{ 
                opc = sc.nextInt();
            } 
            catch (java.util.InputMismatchException e){
                sc.nextLine();
            }
            switch(opc){
                case 1: 
                    criarPersonagem(personagens, sc);
                    break;
                
                case 2:
                    System.out.print(" ____________________________________________"  + "\n" +
                                     "|                                            |" + "\n" +
                                     "|            Aumentar experiencia            |" + "\n" +
                                     "|____________________________________________|" + "\n" +
                                     "|                                            |" + "\n" +
                                     "|   1-Todos os personagens                   |" + "\n" +
                                     "|   2-Personagem por nome                    |" + "\n" +
                                     "\\____________________________________________/"+ "\n" +
                                     "/Opcao: ");
                    int opc_aumentarxp = sc.nextInt();
                    switch(opc_aumentarxp){
                        case 1:
                            for(Personagem p : personagens){
                                p.aumentarExperiencia();
                                }
                            break;
                        case 2:
                            pnome();
                            String nome = sc.next();
                            for(Personagem p : personagens){
                            if(p.getNome().equals(nome)){
                                p.aumentarExperiencia();
                                break;
                                }
                            }
                            break;
                        default:
                            System.out.println("Opcao invalida");
                            break;
                    }
                    break;
                case 3:
                    Personagem.imprimirTodos(personagens);
                    break;
                
                case 4:
                    System.out.print("____________________________________________" + "\n" +
                                     "|    Insira o valor minimo de experiencia    |" + "\n" +
                                     "\\____________________________________________/" + "\n" +
                                     "/Experiencia: ");
                    try{ 
                        opc_case3 = sc.nextInt();
                    } 
                    catch (java.util.InputMismatchException e){
                        System.out.println("Opcao invalida");
                        sc.nextLine();
                    }
                    Personagem.imprimirExperiencia(personagens, opc_case3);
                    break;

                case 5:
                    parmacurta();
                    int opc_armaCurta = sc.nextInt();
                    String armaCurta = new String();
                    switch(opc_armaCurta){
                        case 1:
                            armaCurta = "facas";
                            break;
                        case 2:
                            armaCurta = "machados";
                            break;
                        case 3:
                            armaCurta = "espadas";
                            break;
                        case 4:
                            armaCurta = "null";
                            break;
                        default:
                            System.out.println("Opcao invalida");
                            break;
                    }
                    parmadura();
                    int opc_armadura = sc.nextInt();
                    boolean armadura = false;
                    int armaduracheck = 1;
                    switch(opc_armadura){
                        case 1: 
                            armadura = true;
                            break;
                        case 2:
                            armadura = false;
                            break;
                        case 3: 
                            armaduracheck = 0;
                            break;
                        default:
                            System.out.println("Opcao invalida");
                            break;
                    }
                    pmochila();
                    int opc_mochila = sc.nextInt();
                    String mochila = new String();
                    switch(opc_mochila){
                        case 1:
                            mochila = "sementes";
                            break;
                        case 2: 
                            mochila = "folhas";
                            break;
                        case 3:
                            mochila = "null";
                            break;
                        default:
                            System.out.println("Opcao invalida");
                            break;
                    }

                    if(mochila.equals("sementes")){
                        String tiposementes;
                        System.out.println("Insira o tipo de semente");
                        tiposementes = sc.next();
                        mochila = mochila + " de " + tiposementes;
                    }

                    if(mochila.equals("folhas")){
                        String tipofolhas;
                        System.out.println("Insira o tipo de semente");
                        tipofolhas = sc.next();
                        mochila = mochila + " de " + tipofolhas;
                    }

                    parmalonga();
                    int opc_armaLonga = sc.nextInt();
                    String armaLonga = new String();
                    switch(opc_armaLonga){
                        case 1:
                            armaLonga = "pedras";
                            break;
                        case 2:
                            armaLonga = "arcos";
                            break;
                        case 3:
                            armaLonga = "pistolas";
                            break;
                        case 4:
                            armaLonga = "null";
                            break;
                        default:
                            System.out.println("Opcao invalida");
                            break;
                    }
                    for (Personagem p : personagens){
                        p.imprimirEquipamento(armaCurta, armadura, mochila, armaLonga, armaduracheck);;
                    }
                    break;

                case 6:
                    menu0();
                    break;

                case 7:
                    System.out.print("Ate a proxima!");
                    break;
                    
                default:
                    System.out.println("Opcao invalida");
                    break;
            }
        }
        sc.close();
    }
}

