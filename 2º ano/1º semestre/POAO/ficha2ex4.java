import java.util.*;

public class ficha2ex4 {
    public static void main(String[] args) {
        Scanner nc = new Scanner(System.in);
        while (!nc.hasNextInt()) {
            nc.nextLine();
        }
        int n = nc.nextInt();
        int[] tabela = peneira(n);
        print(tabela);
        int num = nc.nextInt();
        while (num != 0) {
            for (int a : tabela)
                if (num == a)
                    System.out.println(num + " é primo");
            num = nc.nextInt();
        }
    }

    static int[] peneira(int n) {
        int[] tabela = new int[n];
        tabela[0] = 2;
        for (int i = 1; i < tabela.length; i++) {
            tabela[i] = i + 2;
        }
        int a = 0;
        while (a < tabela.length) {
            if (tabela[a] != -1) {
                for (int j = a + 1; j < tabela.length; j++) {
                    if (tabela[j] != -1) {
                        if (tabela[j] % tabela[a] == 0)
                            tabela[j] = -1;
                    }
                }
            }
            a++;
        }
        int cont = 0;
        for (int c : tabela)
            if (c != -1)
                cont++;
        int[] tabelanova = new int[cont];
        int d = 0;
        for (int i = 0; i < cont; i++) {
            while (tabela[d] == -1)
                d++;
            tabelanova[i] = tabela[d];
            d++;
        }

        return tabelanova;
    }

    static void print(int[] tabela) {
        for (int n : tabela)
            System.out.print(n + " ");
        System.out.println("");
    }
}
