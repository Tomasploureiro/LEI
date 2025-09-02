public class Servico {
    private Localizacao localizacao;
    private Data data;
    private double distancia;
    private double duracao;

    public Servico() {
    }

    public Servico(Localizacao localizacao, Data data, double distancia, double duracao) {
        this.localizacao = localizacao;
        this.data = data;
        this.distancia = distancia;
        this.duracao = duracao;
    }


    public Localizacao getLocalizacao() {
        return this.localizacao;
    }

    public void setLocalizacao(Localizacao localizacao) {
        this.localizacao = localizacao;
    }

    public Data getData() {
        return this.data;
    }

    public void setData(Data data) {
        this.data = data;
    }

    public double getDistancia() {
        return this.distancia;
    }

    public void setDistancia(double distancia) {
        this.distancia = distancia;
    }

    public double getDuracao() {
        return this.duracao;
    }

    public void setDuracao(double duracao) {
        this.duracao = duracao;
    }


}
