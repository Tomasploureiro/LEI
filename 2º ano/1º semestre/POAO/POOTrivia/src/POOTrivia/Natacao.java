package POOTrivia;

import java.util.ArrayList;

public class Natacao extends Desporto {
    /**
     * Construtor por omissão
     */
    public Natacao(){
    }

    /**
     * Costrutor para criar uma pergunta do tipo natação
     * @param textoPergunta Pergunta
     * @param opcoes Lista de opções de resposta
     * @param resposta Resposta
     * @param tipoPergunta tipo de pergunta
     */
    public Natacao(String textoPergunta, ArrayList<String> opcoes, String resposta, String tipoPergunta){
        super(textoPergunta, opcoes, resposta, tipoPergunta);
    }
    
}
