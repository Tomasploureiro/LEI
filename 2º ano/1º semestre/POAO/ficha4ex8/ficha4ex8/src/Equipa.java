public class Equipa extends Jogador{
    private String nomeEquipa;
    private String nomeTreinador;
    private Jogador[] jogadores;
    private int maxJogadores;
    private int jogadorCont;
    public Equipa(){
    }

    public Equipa(String nomeEquipa, String nomeTreinador){
        this.nomeEquipa = nomeEquipa;
        this.nomeTreinador = nomeTreinador;
        this.maxJogadores = 11;
        this.jogadorCont = 0;
        jogadores = new Jogador[maxJogadores];
    }

    public void addJogador(Jogador jogador){
        if(jogadorCont < maxJogadores){
            jogadores[jogadorCont] = jogador;
            jogadorCont++;
        }

    }

    public void setNomeEquipa(String nomeEquipa) {
        this.nomeEquipa = nomeEquipa;
    }
    public String getNomeEquipa() {
        return nomeEquipa;
    }

    public void setNomeTreinador(String nomeTreinador) {
        this.nomeTreinador = nomeTreinador;
    }

    public String getNomeTreinador() {
        return nomeTreinador;
    }

    public void setJogadores(Jogador[] jogadores) {
        this.jogadores = jogadores;
    }

    public Jogador[] getJogadores() {
        return jogadores;
    }

    public void setMaxjogadores(int maxJogadores) {
        this.maxJogadores = maxJogadores;
    }

    public int getMaxJogadores() {
        return maxJogadores;
    }

    public void setJogadorCont(int jogadorCont) {
        this.jogadorCont = jogadorCont;
    }

    public int getJogadorCont() {
        return jogadorCont;
    }
    


    public String toString() {
        return "{" +
            " nomeEquipa='" + getNomeEquipa() + "'" +
            ", nomeTreinador='" + getNomeTreinador() + "'" +
            ", jogadores='" + getJogadores() + "'" +
            "}";
    }


}
