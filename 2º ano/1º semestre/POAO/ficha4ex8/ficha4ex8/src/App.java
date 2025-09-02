public class App {
    public static void main(String[] args){
        Equipa equipa1 = new Equipa("Benfica", "Mourinho");
        equipa1.addJogador(new Jogador("Pedro", new Data(8, 2, 2004), "Guarda redes"));
        Equipa equipa2 = new Equipa("Sporting", "Silas");
        equipa2.addJogador(new Jogador("Gustavo", new Data(9,10,2004), "Guarda Redes"));

        Jogo jogo1 = new Jogo(equipa1, equipa2, "luz", new Estatisticas(2, 2), new Estatisticas(49, 51));
        System.out.print(jogo1);
    }
}
