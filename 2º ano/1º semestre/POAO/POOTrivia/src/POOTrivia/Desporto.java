package POOTrivia;

import java.util.ArrayList;

public class Desporto extends Pergunta{
    /**
     * Construtor por omissão
     */
    public Desporto(){
    }

    /**
     * Construtor para criar uma pergunta do tipo desporto
     * @param textoPergunta Pergunta
     * @param opcoes Lista de opções de resposta
     * @param resposta Resposta
     * @param tipoPergunta Tipo de pergunta dentro da categoria desporto
     */
    public Desporto(String textoPergunta, ArrayList<String> opcoes, String resposta, String tipoPergunta){
        super(textoPergunta, opcoes, resposta, tipoPergunta);
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
