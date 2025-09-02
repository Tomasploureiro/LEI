package POOTrivia;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Random;

public class Pergunta {
    /** 
     * Pergunta
     */
    private String textoPergunta;
    /**
     * Lista das opcões de resposta
     */
    ArrayList<String> opcoes;
    /** 
     * Resposta
     */
    private String resposta;
    /**
     * Tipo da pergunta
     */
    private String tipoPergunta;

    /**
     * Construtor pergunta por omissão
     */
    public Pergunta() {
    }

    /**
     * Construtor para criar uma pergunta
     * @param textoPergunta Pergunta
     * @param opcoes Lista de opções de resposta
     * @param resposta Resposta
     * @param tipoPergunta Tipo de pergunta
     */

    public Pergunta(String textoPergunta, ArrayList<String> opcoes, String resposta, String tipoPergunta){
        this.textoPergunta = textoPergunta;
        this.opcoes = opcoes;
        this.resposta = resposta;
        this.tipoPergunta = tipoPergunta;
    }

    
    /** 
     * Função para verificar se a resposta do utilizador é igual à resposta correta
     * @param respostaUtilizador Resposta do utilizador
     * @param numeroPerguntasRespondidas Numero de perguntas respondidas
     * @return boolean
     */
    public boolean verificar(String respostaUtilizador, int numeroPerguntasRespondidas){
    	String result = resposta;
    	
    	System.out.println(result);
    
    	if(tipoPergunta.equals("Futebol")) {
    		Futebol perguntaAux = (Futebol) this;
    		if(numeroPerguntasRespondidas < 3 ) {
    			result = perguntaAux.getRespostaNomes();
    		}
    		else {
    			result = perguntaAux.getRespostaNumeros();
    		}
    	}
        return result.equalsIgnoreCase(respostaUtilizador);
    }

    
    /** 
     * @return String
     */
    public String getTextoPergunta() {
        return this.textoPergunta;
    }

    
    /** 
     * @param textoPergunta Atribui o valor a textoPergunta
     */
    public void setTextoPergunta(String textoPergunta) {
        this.textoPergunta = textoPergunta;
    }
    /**
     * Função para organizar as perguntas consoante o seu tipo
     * @param numeroPerguntasRespondidas numero de perguntas respondidas
     * @return Array de strings com as opções
     */

    public ArrayList<String> getOpcoes(int numeroPerguntasRespondidas) {
    	ArrayList<String> opcoesAux = this.opcoes;
    	
    	if(tipoPergunta.equals("Artes")) {                     
            if (opcoesAux.size() > 3) {
            	int indiceResposta = opcoesAux.indexOf(resposta);
            	if(indiceResposta > 2) {
            		Collections.swap(opcoesAux, indiceResposta, 2);
            	}
            }
            
            if(numeroPerguntasRespondidas < 3) {
            	opcoesAux = new ArrayList<>(opcoesAux.subList(0, 3));
            }
    	}
    	else if(tipoPergunta.equals("Futebol")) {
    		Futebol perguntaAux = (Futebol) this;
    		if(numeroPerguntasRespondidas >= 3) {
    			opcoesAux = perguntaAux.getNumerosJogadores();
        	}
        	else {
        		opcoesAux = perguntaAux.getNomesJogadores();
        	}
    	}  
    	else if (tipoPergunta.equals("Ciencias")) {
    		 Random random = new Random();
    	     int indice = random.nextInt(20);
    	     
    	     Ciencias perguntaAux = (Ciencias) this;
    	     
    	     if (indice % 2 == 0) {
    	    	 opcoesAux = perguntaAux.getOpcoesDificeis();
    	     }
    	     else {
    	    	 opcoesAux = perguntaAux.getOpcoesFaceis();
    	     }
    	}
    	
    	return opcoesAux;
    }

    
    /** 
     * @param opcoes Atribui o valor a opcoes
     */
    public void setOpcoes(ArrayList<String> opcoes) {
        this.opcoes = opcoes;
    }

    public String getResposta() {
        return this.resposta;
    }
    
    public String getTipoPergunta() {
    	return this.tipoPergunta;
    }

    public void setResposta(String resposta) {
        this.resposta = resposta;
    }

    public String toString() {
        return "Pergunta:" + getTextoPergunta();
    }
       

}
