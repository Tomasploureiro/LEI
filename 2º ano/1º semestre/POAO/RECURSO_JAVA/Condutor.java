public class Condutor extends Utilizador {
    private int num_servicos;
    private int distancia_total;
    private int duracao_total;
    private int custo_total;
    private boolean disponivel;
    private Localizacao localizacao;

    public Condutor(String login, String password, String nome, boolean estado, String email, Localizacao localizacao) {
        super(login, password, nome, estado, email,TipoUtilizador.CONDUTOR);
        this.localizacao = localizacao;
    }


    public Condutor() {
    }


    public int getNum_servicos() {
        return this.num_servicos;
    }

    public void setNum_servicos(int num_servicos) {
        this.num_servicos = num_servicos;
    }

    public int getDistancia_total() {
        return this.distancia_total;
    }

    public void setDistancia_total(int distancia_total) {
        this.distancia_total = distancia_total;
    }

    public int getDuracao_total() {
        return this.duracao_total;
    }

    public void setDuracao_total(int duracao_total) {
        this.duracao_total = duracao_total;
    }

    public int getCusto_total() {
        return this.custo_total;
    }

    public void setCusto_total(int custo_total) {
        this.custo_total = custo_total;
    }

    public boolean isDisponivel() {
        return this.disponivel;
    }

    public boolean getDisponivel() {
        return this.disponivel;
    }

    public void setDisponivel(boolean disponivel) {
        this.disponivel = disponivel;
    }

    public Localizacao getLocalizacao() {
        return this.localizacao;
    }

    public void setLocalizacao(Localizacao localizacao) {
        this.localizacao = localizacao;
    }
    
    public boolean autenticar(String login, String password){
        return this.getLogin().equals(login) && this.getPassword().equals(password);
    }
}

