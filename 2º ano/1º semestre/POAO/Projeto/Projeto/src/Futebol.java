import java.util.ArrayList;

public class Futebol extends Desporto {
    private ArrayList<String> nomesJogadores;
    private ArrayList<String> numerosJogadores;
    private String respostaNumeros;
    
    public Futebol(){
    }

    public Futebol(String textoPergunta, ArrayList<String> nomesJogadores, ArrayList<String> numerosJogadores, String respostaNomes, String respostaNumeros){
        super(textoPergunta, nomesJogadores, respostaNomes);
        this.nomesJogadores = nomesJogadores;
        this.numerosJogadores = numerosJogadores;
        this.respostaNumeros = respostaNumeros;
    }

    public boolean verificar(String respostaUtilizador){
        return getResposta().equalsIgnoreCase(respostaUtilizador);
    }

    public ArrayList<String> getNomesJogadores() {
        return this.nomesJogadores;
    }

    public void setNomesJogadores(ArrayList<String> nomesJogadores) {
        this.nomesJogadores = nomesJogadores;
    }

    public ArrayList<String> getNumerosJogadores() {
        return this.numerosJogadores;
    }

    public void setNumerosJogadores(ArrayList<String> numerosJogadores) {
        this.numerosJogadores = numerosJogadores;
    }

    public String getRespostaNumeros() {
        return this.respostaNumeros;
    }

    public void setRespostaNumeros(String respostaNumeros) {
        this.respostaNumeros = respostaNumeros;
    }


}
