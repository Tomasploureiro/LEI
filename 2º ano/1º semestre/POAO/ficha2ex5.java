public class ficha2ex5 {
    public static void main(String[] args) {
        int[][] matrix = new int[5][10];
        sequenciamatrixlinha(matrix);
        printmatrix(matrix);

    }

    static int[][] randommarix(int[][] matrix) {
        for (int i = 0; i < matrix.length; i++)
            for (int j = 0; j < matrix[0].length; j++)
                matrix[i][j] = (int) (Math.random() * 100);
        return matrix;
    }

    static int[][] sequenciamatrixcoluna(int[][] matrix) {
        int valor = 0;
        for (int i = 0; i < matrix.length; i++)
            for (int j = 0; j < matrix[0].length; j++) {
                matrix[i][j] = valor;
                valor++;
            }
        return matrix;
    }

    static int[][] sequenciamatrixlinha(int[][] matrix) {
        int valor = 0;
        for (int i = 0; i < matrix[0].length; i++)
            for (int j = 0; j < matrix.length; j++) {
                matrix[j][i] = valor;
                valor++;
            }
        return matrix;
    }

    static void printmatrix(int[][] matrix) {
        for (int i = 0; i < matrix.length; i++) {
            for (int j = 0; j < matrix[0].length; j++) {
                System.out.print(matrix[i][j] + " ");
            }
            System.out.println("");
        }
    }
}
