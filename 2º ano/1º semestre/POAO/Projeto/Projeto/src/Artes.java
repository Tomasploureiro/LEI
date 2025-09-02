import java.util.ArrayList;

public class Artes extends Pergunta {

    public Artes() {
    }
    
    public Artes(String textoPergunta, ArrayList<String> opcoes, String resposta){
        super(textoPergunta, opcoes, resposta);
    }

    public boolean verificar(String respostaUtilizador){
        return getResposta().equalsIgnoreCase(respostaUtilizador);
    }

    public void imprimirPergunta(){
        System.out.println(getTextoPergunta());
        for(int i = 0; i<getOpcoes().size(); i++){
            System.out.println((i+1) + ") "+ getOpcoes().get(i));
        }
        System.out.println("Escreve a opção correta: ");
    }

}
