import java.util.ArrayList;

public class Ciencias extends Pergunta {
    
    private ArrayList<String> opcoesDificeis;

    public Ciencias(){
    }

    public Ciencias(String textoPergunta, ArrayList<String> opcoesFaceis, ArrayList<String> opcoesDificeis , String resposta){
        super(textoPergunta, opcoesFaceis, resposta);
        this.opcoesDificeis = opcoesDificeis;
    }

    public boolean verificar(String respostaUtilizador){
        return getResposta().equalsIgnoreCase(respostaUtilizador);
    }

    public ArrayList<String> getOpcoesDificeis() {
        return this.opcoesDificeis;
    }

    public void setOpcoesDificeis(ArrayList<String> opcoesDificeis) {
        this.opcoesDificeis = opcoesDificeis;
    }
    
}
