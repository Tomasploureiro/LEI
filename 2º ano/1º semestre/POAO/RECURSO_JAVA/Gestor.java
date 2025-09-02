public class Gestor extends Utilizador {
    public Gestor(String login, String password, String nome,boolean estado, String email) {
        super(login, password, nome, estado, email,TipoUtilizador.GESTOR);
    }

    public boolean autenticar(String login, String password){
        return this.getLogin().equals(login) && this.getPassword().equals(password);
    }

}
