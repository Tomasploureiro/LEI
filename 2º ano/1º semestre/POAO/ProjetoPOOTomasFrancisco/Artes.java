package POOTrivia;

import java.util.ArrayList;

public class Artes extends Pergunta {
	/**
     * Construtor por omissão
     */
    public Artes() {
    }
    
    /**
     * Costrutor para criar uma pergunta do tipo artes
     * @param textoPergunta Pergunta
     * @param opcoes Lista de opções de resposta
     * @param resposta Resposta
     */
    public Artes(String textoPergunta, ArrayList<String> opcoes, String resposta){
        super(textoPergunta, opcoes, resposta, "Artes");
    }

    /** 
     * Função para verificar se a resposta está correta
     * @param respostaUtilizador resposta do utilizador
     * @return boolean true se for correta false se for errada
     */
    public boolean verificar(String respostaUtilizador){
        return getResposta().equalsIgnoreCase(respostaUtilizador);
    }
    

}
