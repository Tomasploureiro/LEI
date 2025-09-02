public class ficha2ex1{
    public static void main(String[] args) {
        int size = 10;
        int[] tabela = new int[size];
        for(int i = 0; i < tabela.length; i++)
            tabela[i] = (int) (Math.random() * 100);
        for(int n : tabela)
            System.out.print(n+ " ");
    }
}
