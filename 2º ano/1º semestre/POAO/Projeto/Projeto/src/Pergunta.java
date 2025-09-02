import java.util.ArrayList;

public class Pergunta {

    private String textoPergunta;
    private ArrayList<String> opcoes;
    private String resposta;

    public Pergunta() {
    }

    public Pergunta(String textoPergunta, ArrayList<String> opcoes, String resposta){
        this.textoPergunta = textoPergunta;
        this.opcoes = opcoes;
        this.resposta = resposta;
    }

    public boolean verificar(String respostaUtilizador){
        return getResposta().equalsIgnoreCase(respostaUtilizador);
    }


    public String getTextoPergunta() {
        return this.textoPergunta;
    }

    public void setTextoPergunta(String textoPergunta) {
        this.textoPergunta = textoPergunta;
    }

    public ArrayList<String> getOpcoes() {
        return this.opcoes;
    }

    public void setOpcoes(ArrayList<String> opcoes) {
        this.opcoes = opcoes;
    }

    public String getResposta() {
        return this.resposta;
    }

    public void setResposta(String resposta) {
        this.resposta = resposta;
    }

    public String toString() {
        return "Pergunta:" + getTextoPergunta();
    }
    
    public void imprimirPergunta(){
    }


}
