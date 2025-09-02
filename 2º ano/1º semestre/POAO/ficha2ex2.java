public class ficha2ex2 {
    public static void main(String[] args) {
        int[] tabela1 = {1, 2, 6, 7};
        int[] tabela2 = {3, 4, 5, 8};
        int[] tabela = juntatabelas(tabela1, tabela2);
        for(int n : tabela)
            System.out.println(n);
    }

    static int[] juntatabelas(int[] tabela1, int[] tabela2){
        int[] tabela3 = new int[tabela1.length + tabela2.length];
        int j = 0;
        for(int i = 0; i < tabela3.length; i+=2){
            tabela3[i] = tabela1[j];
            tabela3[i+1] = tabela2[j];
            j++;
        }
        return tabela3;
    }
}
