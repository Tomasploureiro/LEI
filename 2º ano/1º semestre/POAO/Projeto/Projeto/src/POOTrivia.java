import java.util.Scanner;
public class POOTrivia {
    public static void main(String[] args) {
        Jogo jogo = new Jogo();
        jogo.carregarPerguntas("C:\\Users\\User\\Desktop\\uni\\POAO\\Projeto\\Projeto\\src\\perguntas.txt");

        Scanner sc = new Scanner(System.in);
        Pergunta pergunta = jogo.escolherPerguntaAleatoria();
        pergunta.imprimirPergunta();
        String respostaUtilisador = sc.nextLine();
        if(pergunta.verificar(respostaUtilisador)){
            System.out.println("correto");
        }else{
        System.out.println("incorreto");
        }

    }
}
