public class ficha2ex6 {
    public static void main(String[] args) {
        // int[][] matrix = new int[9][9];
        int[][] matrix = {};
        int x = 1, y = 1;
        int[] lista;
        lista = numpossiveis(matrix, x, y);
        for (int n : lista)
            if (n != 0)
                System.out.print(n + " ");

    }

    static int[] numpossiveis(int[][] matrix, int x, int y) {
        int[] lista = { 0, 1, 2, 3, 4, 5, 6, 7, 8, 9 };
        int[] listafinal = new int[lista.length];
        int n = 0;
        for (int l : lista) {
            int ver = 0;
            for (int i = 0; i < matrix.length; i++) {
                if (matrix[x][i] == l || matrix[i][y] == l) {
                    ver = 1;
                    break;
                }
            }
            if (ver == 0) {
                int ver2 = 0;
                for (int j : listafinal) {
                    if (j == l) {
                        ver2 = 1;
                    }
                }
                if (ver2 == 0) {
                    listafinal[n] = l;
                    n++;
                }
            }
        }
        return listafinal;
    }
}
