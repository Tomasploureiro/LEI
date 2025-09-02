public class Jogador {
    private String nome;
    private Data dataNasc;
    private String posicao;

     public Jogador(){
     }


    public Jogador(String nome, Data dataNasc, String posicao) {
        this.nome = nome;
        this.dataNasc = dataNasc;
        this.posicao = posicao;
    }


    public String getNome() {
        return this.nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public Data getDataNasc() {
        return this.dataNasc;
    }

    public void setDataNasc(Data dataNasc) {
        this.dataNasc = dataNasc;
    }

    public String getPosicao() {
        return this.posicao;
    }

    public void setPosicao(String posicao) {
        this.posicao = posicao;
    }

    public String toString() {
        return "{" +
            " nome='" + getNome() + "'" +
            ", dataNasc='" + getDataNasc() + "'" +
            ", posicao='" + getPosicao() + "'" +
            "}";
    }

}
