import java.util.ArrayList;
import java.util.Scanner;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.Random;
public class Jogo {
    private int pontos;
    private ArrayList<Pergunta> perguntas = new ArrayList<>();
    private ArrayList<Pergunta> perguntasRespondidas;

    public void carregarPerguntas(String ficheiro){
        try{
            Scanner sc = new Scanner(new File(ficheiro));
            
            while(sc.hasNextLine()){
                String tipoPergunta = sc.nextLine();
                Pergunta pergunta;
                if(tipoPergunta.equals("Artes")){
                    String textoPergunta = sc.nextLine();
                    ArrayList<String> opcoes = lerOpcoes(sc);
                    String resposta = sc.nextLine();
                    resposta = resposta.substring(9, resposta.length());
                    pergunta = new Artes(textoPergunta,opcoes,resposta);
                }else if(tipoPergunta.equals("Ciencias")){
                    String textoPergunta = sc.nextLine();
                    ArrayList<String> opcoesFaceis = lerOpcoes(sc);
                    ArrayList<String>opcoesDificeis = lerOpcoes(sc);
                    String resposta = sc.nextLine();
                    resposta = resposta.substring(9, resposta.length());
                    pergunta = new Ciencias(textoPergunta, opcoesFaceis, opcoesDificeis, resposta);
                }else if(tipoPergunta.equals("Futebol")){
                    String textoPergunta = sc.nextLine();
                    ArrayList<String> nomesJogadores = lerOpcoes(sc);
                    String respostaNomes = sc.nextLine();
                    respostaNomes = respostaNomes.substring(9, respostaNomes.length());
                    ArrayList<String> numerosJogadores = lerOpcoes(sc);
                    String respostaNumeros = sc.nextLine();
                    respostaNumeros = respostaNumeros.substring(9, respostaNumeros.length());
                    pergunta = new Futebol(textoPergunta, nomesJogadores, numerosJogadores, respostaNomes, respostaNumeros);
                }else if(tipoPergunta.equals("Natacao")){
                    String textoPergunta = sc.nextLine();
                    ArrayList<String> opcoes = lerOpcoes(sc);
                    String resposta = sc.nextLine();
                    resposta = resposta.substring(9, resposta.length());
                    pergunta = new Natacao(textoPergunta, opcoes, resposta);
                }else if(tipoPergunta.equals("Ski")){
                    String textoPergunta = sc.nextLine();
                    ArrayList<String> opcoes = lerOpcoes(sc);
                    String resposta = sc.nextLine();
                    resposta = resposta.substring(9, resposta.length());
                    pergunta = new Ski(textoPergunta, opcoes, resposta);                    
                }else {
                    pergunta = null;
                }
                sc.nextLine();
                if(pergunta != null){
                    perguntas.add(pergunta);
                }
        }
        sc.close();
        }catch(FileNotFoundException e){
            e.printStackTrace();
        }
    }

    public ArrayList<String> lerOpcoes(Scanner sc){
        ArrayList<String> opcoes = new ArrayList<>();
        String numOpcoesString = sc.nextLine();
        int numOpcoes = Integer.parseInt(numOpcoesString);

        for(int i = 0; i<numOpcoes; i++){
            opcoes.add(sc.nextLine());
        }

        return opcoes;
    }

    public void imprimirPerguntas(){
        for (Pergunta p :perguntas){
            System.out.println(p.toString());
        }
    }   

    public Pergunta escolherPerguntaAleatoria(){
        Random random = new Random();
        int indice = random.nextInt(perguntas.size());
        //indice = 0;
        return perguntas.get(indice);

    }
}
