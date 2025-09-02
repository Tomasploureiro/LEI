public class Jogo {
    private Equipa Equipa1;
    private Equipa Equipa2;
    private String campo;
    private Estatisticas resultado;
    private Estatisticas posseDeBola;

    Jogo(){
    }


    public Jogo(Equipa Equipa1, Equipa Equipa2, String campo, Estatisticas resultado, Estatisticas posseDeBola) {
        this.Equipa1 = Equipa1;
        this.Equipa2 = Equipa2;
        this.campo = campo;
        this.resultado = resultado;
        this.posseDeBola = posseDeBola;
    }
   
    public void setCampo(String campo) {
        this.campo = campo;
    }
    public String getCampo() {
        return campo;
    }
    
    public void setEquipa1(Equipa equipa1) {
        Equipa1 = equipa1;
    }
    public Equipa getEquipa1() {
        return Equipa1;
    }

    public void setEquipa2(Equipa equipa2) {
        Equipa2 = equipa2;
    }
    public Equipa getEquipa2() {
        return Equipa2;
    }

    public void setPosseDeBola(Estatisticas posseDeBola) {
        this.posseDeBola = posseDeBola;
    }
    public Estatisticas getPosseDeBola() {
        return posseDeBola;
    }

    public void setResultado(Estatisticas resultado) {
        this.resultado = resultado;
    }
    public Estatisticas getResultado() {
        return resultado;
    }

    public String toString() {
        return "{" +
            " Equipa1='" + getEquipa1() + "'" +
            ", Equipa2='" + getEquipa2() + "'" +
            ", campo='" + getCampo() + "'" +
            ", resultado='" + getResultado() + "'" +
            ", posseDeBola='" + getPosseDeBola() + "'" +
            "}";
    }


}
