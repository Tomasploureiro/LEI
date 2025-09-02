import java.util.ArrayList;

public class Desporto extends Pergunta{
    
    public Desporto(){
    }

    public Desporto(String textoPergunta, ArrayList<String> opcoes, String resposta){
        super(textoPergunta, opcoes, resposta);
    }
    
    public boolean verificar(String respostaUtilizador){
        return getResposta().equalsIgnoreCase(respostaUtilizador);
    }

}
