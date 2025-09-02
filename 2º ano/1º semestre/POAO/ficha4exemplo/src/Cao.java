public class Cao {
    private String nome;
    private Data dataNasc;
    private String raca;

    public Cao() {
        dataNasc = new Data();
    }

    public Cao(String  n){
        this();
        nome = n;
    }

    public Cao(String n, Data d, String r) {
        this(n);
        dataNasc = d;
        raca = r;
    }

    public void setNome(String n) {
        nome = n;
    }

    // public void setNome(String nome){
    //     this.nome = nome;
    // }

    public String getNome() {
        return nome;
    }

    public void setdataNasc(Data dataNasc) {
        dataNasc = dataNasc;
    }

    public Data getdataNasc() {
        return dataNasc;
    }

    public void setRaca(String r) {
        raca = r;
    }

    public String getRaca() {
        return raca;
    }

    public String toString() {
        return nome + " nascido em " + dataNasc + " de raça " + raca;
    }
}
