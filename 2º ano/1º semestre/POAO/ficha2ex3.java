public class ficha2ex3 {
    public static void main(String[] args) {
        int[][] matrix1 = { { 1, 2, 3 }, { 3, 4, 5 }, { 5, 2, 1 } };
        int[][] matrix2 = { { 5, 6, 1 }, { 7, 8, 2 }, { 2, 2, 5 } };
        int[][] matrix3 = new int[3][3];
        for (int i = 0; i < matrix1.length; i++) {
            for (int j = 0; j < matrix1.length; j++) {
                matrix3[i][j] = 0;
                for(int f = 0; f<matrix1.length;f++){
                    matrix3[i][j] += matrix1[i][f]*matrix2[f][j];
                }
            }
        }
        printmatrix(matrix1);
        System.out.println("");
        printmatrix(matrix2);
        System.out.println("");
        printmatrix(matrix3);
    }

    static void printmatrix(int[][] matrix) {
        for (int i = 0; i < matrix.length; i++) {
            for (int j = 0; j < matrix.length; j++) {
                System.out.print(matrix[i][j] + " ");
            }
            System.out.println("");
        }
    }
}