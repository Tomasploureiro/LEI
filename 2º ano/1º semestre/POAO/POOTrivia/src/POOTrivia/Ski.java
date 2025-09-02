package POOTrivia;

import java.util.ArrayList;

public class Ski extends Desporto{
    /**
     * Construtor por omissão
     */
    public Ski(){
    }

    /**
     * Costrutor para criar uma pergunta do tipo ski
     * @param textoPergunta Pergunta
     * @param opcoes Lista de opções de resposta
     * @param resposta Resposta
     * @param tipoPergunta tipo de pergunta
     */
    
    public Ski(String textoPergunta, ArrayList<String> opcoes, String resposta, String tipoPergunta){
        super(textoPergunta, opcoes, resposta, tipoPergunta);
    }
    
    
}
