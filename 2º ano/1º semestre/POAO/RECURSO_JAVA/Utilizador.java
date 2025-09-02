abstract class Utilizador {
    private String login;
    private String password;
    private String nome;
    private boolean estado;
    private String email;
    private TipoUtilizador tipo;

    public Utilizador() {
    }

    public Utilizador(String login, String password, String nome, boolean estado, String email, TipoUtilizador tipo) {
        this.login = login;
        this.password = password;
        this.nome = nome;
        this.estado = estado;
        this.email = email;
        this.tipo = tipo;
    }

    public String getLogin() {
        return this.login;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public String getPassword() {
        return this.password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getNome() {
        return this.nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public boolean isEstado() {
        return this.estado;
    }

    public boolean getEstado() {
        return this.estado;
    }

    public void setEstado(boolean estado) {
        this.estado = estado;
    }

    public String getEmail() {
        return this.email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public TipoUtilizador getTipo() {
        return this.tipo;
    }

    public void setTipo(TipoUtilizador tipo) {
        this.tipo = tipo;
    }

    
    // public abstract boolean autenticar(String login, String password) {
    //     return this.login.equals(login) && this.password.equals(password);
    // }
    public abstract boolean autenticar(String login, String password);
}
