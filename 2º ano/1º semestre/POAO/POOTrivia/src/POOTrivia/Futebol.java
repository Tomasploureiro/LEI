package POOTrivia;

import java.util.ArrayList;

public class Futebol extends Desporto {
    /**
     * Array de strings com o nome dos jogadores das respostas
     */
    private ArrayList<String> nomesJogadores;
    /**
     * Array de strings com o numero dos jogadores das respostas
     */
    private ArrayList<String> numerosJogadores;
    /**
     * Resposta numero
     */
    private String respostaNumeros;
    /**
     * Resposta nome
     */
    private String respostaNomes;
    
    /**
     * Construtor po omissão
     */
    public Futebol(){
    }

    /**
     * Construtor para criar uma pergunta do tipo futebol
     * @param textoPergunta Pergunta
     * @param nomesJogadores Lista com os nomes dos jogadores das respostas
     * @param numerosJogadores Lista com os numeros dos jogadores das respostas
     * @param respostaNomes Resposta nome
     * @param respostaNumeros Resposta numero
     * @param tipoPergunta tipo de pergunta
     */
    public Futebol(String textoPergunta, ArrayList<String> nomesJogadores, ArrayList<String> numerosJogadores, String respostaNomes, String respostaNumeros, String tipoPergunta){
        super(textoPergunta, nomesJogadores, respostaNomes, tipoPergunta);
        this.nomesJogadores = nomesJogadores;
        this.numerosJogadores = numerosJogadores;
        this.respostaNumeros = respostaNumeros;
        this.respostaNomes = respostaNomes;
    }
 
    /** 
     * Função para verificar se a resposta está correta
     * @param respostaUtilizador resposta do utilizador
     * @return boolean true se for correta false se for errada
     */
    public boolean verificar(String respostaUtilizador){
        return getResposta().equalsIgnoreCase(respostaUtilizador);
    }
    
    /** 
     * @return nomesJogadores
     */
    public ArrayList<String> getNomesJogadores() {
        return this.nomesJogadores;
    }
    
    /** 
     * @param nomesJogadores Atribui o valor a nomesJogadores
     */
    public void setNomesJogadores(ArrayList<String> nomesJogadores) {
        this.nomesJogadores = nomesJogadores;
    }
    
    /** 
     * @return numerosJogadores
     */
    public ArrayList<String> getNumerosJogadores() {
        return this.numerosJogadores;
    }
    /**
     * 
     * @param numerosJogadores Atribui o valor a  numerosJogadores
     */
    public void setNumerosJogadores(ArrayList<String> numerosJogadores) {
        this.numerosJogadores = numerosJogadores;
    }
    
    /**
     * 
     * @return respostaNumeros
     */
    public String getRespostaNumeros() {
        return this.respostaNumeros;
    }

    /**
     * 
     * @param respostaNumeros Atribui o valor a respostaNumeros
     */
    public void setRespostaNumeros(String respostaNumeros) {
        this.respostaNumeros = respostaNumeros;
    }
    
    /**
     * 
     * @return respostaNomes
     */
    public String getRespostaNomes() {
        return this.respostaNomes;
    }

    /**
     * 
     * @param respostaNomes Atribui o valor a respostaNomes
     */
    public void setRespostaNomes(String respostaNomes) {
        this.respostaNomes = respostaNomes;
    }

}
