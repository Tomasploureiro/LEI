package POOTrivia;

import java.util.ArrayList;

public class Ciencias extends Pergunta {
    /**
     * Array de strings com as opções da categoria dificil
     */
    private ArrayList<String> opcoesDificeis;
    /**
     * Array de strings com as opções de categoria facil
     */
    private ArrayList<String> opcoesFaceis;

    /**
     * Construtor por omissão
     */
    public Ciencias(){
    }

    /**
     * Construtor para criar uma pergunta do tipo ciências
     * @param textoPergunta Pergunta
     * @param opcoesFaceis Lista das opções faceis
     * @param opcoesDificeis Lista das opcções dificeis
     * @param resposta Resposta
     */
    public Ciencias(String textoPergunta, ArrayList<String> opcoesFaceis, ArrayList<String> opcoesDificeis , String resposta){
        super(textoPergunta, opcoesFaceis, resposta, "Ciencias");
        this.opcoesDificeis = opcoesDificeis;
        this.opcoesFaceis = opcoesFaceis;
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
     * @return  opcoesDificeis
     */
    public ArrayList<String> getOpcoesDificeis() {
        return this.opcoesDificeis;
    }
    
    
    /** 
     * @return opcoesDificeis
     */
    public ArrayList<String> getOpcoesFaceis() {
        return this.opcoesFaceis;
    }
    
    
    /** 
     * @param opcoesFaceis Atribui o valor a opcoesFaceis
     */
    public void setOpcoesFaceis(ArrayList<String> opcoesFaceis) {
        this.opcoesFaceis = opcoesFaceis;
    }

    /**
     * 
     * @param opcoesDificeis Atribui o valor a opcoesDificeis
     */
    public void setOpcoesDificeis(ArrayList<String> opcoesDificeis) {
        this.opcoesDificeis = opcoesDificeis;
    }
    
}
