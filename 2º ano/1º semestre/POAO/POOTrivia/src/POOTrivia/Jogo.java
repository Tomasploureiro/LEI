package POOTrivia;

import java.util.ArrayList;
import java.util.Scanner;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Random;

public class Jogo {
	/**
     * Pontos do jogo
     */
	 private int pontos;
     /**
      * Array de perguntas com as perguntas
      */
	 private ArrayList<Pergunta> perguntas;
     /**
      * Array de perguntas com as perguntas respondidas
      */
	 private ArrayList<Pergunta> perguntasRespondidas;
     /**
      * Array de perguntas com as perguntas certas
      */
	 private ArrayList<Pergunta> perguntasCertas;
     /**
      * Array de perguntas com as perguntas erradas
      */
     private ArrayList<Pergunta>perguntasErradas;
     /**
      * Numero de perguntas geradas
      */
	 private int numeroPerguntasGeradas;
     /**
      * Pontos da pergunta
      */
	 private int pontosPergunta;

    /**
     * Atrubuir o valor as variaveis
     */
	public Jogo() {
		perguntas = new ArrayList<>();
		perguntasRespondidas= new ArrayList<>();
		perguntasCertas = new ArrayList<>();
		perguntasErradas = new ArrayList<>();
		pontos = 0;
		numeroPerguntasGeradas = 0;
		pontosPergunta = 5;
	}
	

    
    /** 
     * @return pontos
     */
    public int getPontos() {
        return this.pontos;
    }

    
    /** 
     * @param pontos Atribui o valor a pontos
     */
    public void setPontos(int pontos) {
        this.pontos = pontos;
    }

    
    /** 
     * @return perguntas
     */
    public ArrayList<Pergunta> getPerguntas() {
        return this.perguntas;
    }

    
    /** 
     * @param perguntas Atribui o valor a perguntas
     */
    public void setPerguntas(ArrayList<Pergunta> perguntas) {
        this.perguntas = perguntas;
    }

    /** 
     * @return perguntasRespondidas
     */
    public ArrayList<Pergunta> getPerguntasRespondidas() {
        return this.perguntasRespondidas;
    }

    /**
     * 
     * @param perguntasRespondidas Atribui o valor a Respondidas
     */
    public void setPerguntasRespondidas(ArrayList<Pergunta> perguntasRespondidas) {
        this.perguntasRespondidas = perguntasRespondidas;
    }

    /**
     * 
     * @return perguntasCertas
     */
    public ArrayList<Pergunta> getPerguntasCertas() {
        return this.perguntasCertas;
    }

    /**
     * 
     * @param perguntasCertas Atribui o valor a perguntasCertas
     */
    public void setPerguntasCertas(ArrayList<Pergunta> perguntasCertas) {
        this.perguntasCertas = perguntasCertas;
    }

    /**
     * 
     * @return perguntasErradas
     */
    public ArrayList<Pergunta> getPerguntasErradas() {
        return this.perguntasErradas;
    }

    /**
     * 
     * @param perguntasErradas Atribui o valor a perguntasErradas
     */
    public void setPerguntasErradas(ArrayList<Pergunta> perguntasErradas) {
        this.perguntasErradas = perguntasErradas;
    }

    /**
     * 
     * @return numeroPerguntasGeradas
     */
    public int getNumeroPerguntasGeradas() {
        return this.numeroPerguntasGeradas;
    }

    /**
     * 
     * @param numeroPerguntasGeradas Atribui o valor a numeroPerguntasGeradas
     */
    public void setNumeroPerguntasGeradas(int numeroPerguntasGeradas) {
        this.numeroPerguntasGeradas = numeroPerguntasGeradas;
    }

    /**
     * 
     * @return pontosPergunta
     */
    public int getPontosPergunta() {
        return this.pontosPergunta;
    }

    /**
     * 
     * @param pontosPergunta Atribui o valor a pontosPergunta
     */
    public void setPontosPergunta(int pontosPergunta) {
        this.pontosPergunta = pontosPergunta;
    }

    
    /** 
     * Função para carregar as perguntas do ficheiro para o Array perguntas
     * @param ficheiro ficheiro onde estão as perguntas
     */
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
                    ArrayList<String> opcoesDificeis = lerOpcoes(sc);
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
                    pergunta = new Futebol(textoPergunta, nomesJogadores, numerosJogadores, respostaNomes, respostaNumeros,tipoPergunta);
                }else if(tipoPergunta.equals("Natacao")){
                    String textoPergunta = sc.nextLine();
                    ArrayList<String> opcoes = lerOpcoes(sc);
                    String resposta = sc.nextLine();
                    resposta = resposta.substring(9, resposta.length());
                    pergunta = new Natacao(textoPergunta, opcoes, resposta, tipoPergunta);
                }else if(tipoPergunta.equals("Ski")){
                    String textoPergunta = sc.nextLine();
                    ArrayList<String> opcoes = lerOpcoes(sc);
                    String resposta = sc.nextLine();
                    resposta = resposta.substring(9, resposta.length());
                    pergunta = new Ski(textoPergunta, opcoes, resposta, tipoPergunta);                    
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
    /**
     * Função que lê as opções do ficheiro
     * @param sc scanner para inputs do utilizador
     * @return Array de Strings com as opções
     */
    public ArrayList<String> lerOpcoes(Scanner sc){
        ArrayList<String> opcoes = new ArrayList<>();
        String numOpcoesString = sc.nextLine();
        int numOpcoes = Integer.parseInt(numOpcoesString);

        for(int i = 0; i<numOpcoes; i++){
            opcoes.add(sc.nextLine());
        }

        return opcoes;
    }
    
    /**
     * 
     * @return perguntas
     */
    public ArrayList<Pergunta> obterPerguntasObjecto() {
    	return perguntas;
    }

    /**
     * Função para escolher uma pergunta aleatoria
     * @return Pergunta
     */
    public Pergunta escolherPerguntaAleatoria(){
        Random random = new Random();
        int indice = random.nextInt(perguntas.size());
                
        Pergunta perguntaAux = perguntas.get(indice);
        
        boolean encontrarPergunta = false;
        
        while(!encontrarPergunta) {
        	if (perguntasRespondidas.contains(perguntaAux)) {
        		indice = random.nextInt(perguntas.size());
            	perguntaAux = perguntas.get(indice);
            }
        	else {
        		encontrarPergunta = true;
        	}
        }
        
        numeroPerguntasGeradas++;
        

		return perguntaAux;
       
    }
    
    /**
     * 
     * @return pontos
     */
    public int obterPontos() {
    	return pontos;
    }
    
    /**
     * Função para verificar se a resposta está correta
     * @param pergunta Pergunta
     * @param respostaSelecionada Resposta do Utilizador
     * @return correto
     */
    public boolean verificarResposta(Pergunta pergunta, String respostaSelecionada) {
    	perguntasRespondidas.add(pergunta);
    	
    	boolean correto = pergunta.verificar(respostaSelecionada, numeroPerguntasGeradas);
    	
    	if(correto) {
    		
    		perguntasCertas.add(pergunta);
    		
    		if(pergunta.getTipoPergunta().equals("Artes")) {
    			pontos= pontos + (pontosPergunta * 10);
    		}
    		else if(pergunta.getTipoPergunta().equals("Ciencias")) {
    			pontos= pontos + (pontosPergunta + 5);
    		}
    		else if(pergunta.getTipoPergunta().equals("Futebol")) {
				pontos= pontos + (pontosPergunta + 1 + 3);
			}
			else if(pergunta.getTipoPergunta().equals("Natacao")) {
				pontos= pontos + (pontosPergunta + 10 + 3);
			}
			else if(pergunta.getTipoPergunta().equals("Ski")) {
				pontos= pontos + ((pontosPergunta + 3) * 2);
			}
    		
    	}
    	else {
    		perguntasErradas.add(pergunta);
    	}
    	
		return correto;
    	
    }
    
    /**
     * 
     * @param pergunta Atribui o valor a pergunta
     * @return opcoes da pergunta
     */
    public ArrayList<String> getOpcoesPergunta(Pergunta pergunta){
    	return pergunta.getOpcoes(numeroPerguntasGeradas);
    }
    
    /**
     * 
     * @return numeroPerguntasGeradas
     */
    public int obterNumeroPerguntasGeradas() {
    	return numeroPerguntasGeradas;
    }

    /**
     * Função para acabar o jogo criar o arquivo com o nome a data e a pontuação
     * @param nomeJogador Nome do Jogador
     */
    public void acabarJogo(String nomeJogador){
    	DateTimeFormatter formatterToFile = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
    	DateTimeFormatter formatterToName = DateTimeFormatter.ofPattern("yyyyMMddHHmmss");
    	
    	String dataHoraAtualNome = LocalDateTime.now().format(formatterToName);
    	String datahoraAtualFicheiro = LocalDateTime.now().format(formatterToFile);
    	
    	String nomeArquivo = "Jogos\\pootrivia_jogo_" + dataHoraAtualNome + "_" +obterIniciaisNome(nomeJogador) + ".dat";
    	
    	
    	File arquivo = new File(nomeArquivo);
    	
    	try {
    		FileWriter fileWriter = new FileWriter(arquivo);
    		
    		String textoParaGravar = "Data e hora: " + datahoraAtualFicheiro + "\nNome do jogador: " + nomeJogador +
                    "\nPontuacao: " + pontos;
    		
    		fileWriter.write(textoParaGravar);
    		fileWriter.close();
    		
    	} catch (IOException e) {
            e.printStackTrace();
        }
    	    	
    }
    
    /**
     * Função para obeter as iniciais do nome
     * @param nomeCompleto Nome
     * @return iniciais do nome
     */
    private static String obterIniciaisNome(String nomeCompleto) {
        StringBuilder iniciais = new StringBuilder();

        String[] palavras = nomeCompleto.split("\\s+");

        for (String palavra : palavras) {
            if (!palavra.isEmpty()) {
                iniciais.append(palavra.charAt(0));
            }
        }

        return iniciais.toString();
    }
}
