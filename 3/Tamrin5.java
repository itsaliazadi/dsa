public class Tamrin5 {
    public static int[][][] f(int[][] a, int n) {
        int[][] A = new int[n][n];
        int[][] B = new int[n][n];
        int[][] C = new int[n][n];
        int[][] D = new int[n][n];
        int[][] E = new int[n][n];
        int[][] F = new int[n][n];

        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                A[i][j] = a[i][j] + (i == j ? 1 : 0);
                B[i][j] = a[i][j] - (i + j == n - 1 ? 1 : 0);
                C[i][j] = a[i][j] + (i == j - 1 || i + j == n - 2 ? 2 : 0);
                D[i][j] = a[i][j] - (i - 1 == j || i + j == n ? 2 : 0);
                E[i][j] = a[i][j] + (i - 1 == j || i + j == n - 2 ? 3 : 0);
                F[i][j] = a[i][j] - (i == j - 1 || i + j == n ? 3 : 0);
            }
        }

        return new int[][][] { A, B, C, D, E, F };
    }

    public static void printMatrix(int[][] matrix) {
        for (int[] row : matrix) {
            for (int value : row) {
                System.out.print(value + " ");
            }
            System.out.println();
        }
    }

    public static void main(String[] args) {
        int n = 3; 
        int[][] inputMatrix = {
            {1, 2, 3},
            {4, 5, 6},
            {7, 8, 9}
        };

        int[][][] result = f(inputMatrix, n);

        System.out.println("Matrix A:");
        printMatrix(result[0]);
        System.out.println("Matrix B:");
        printMatrix(result[1]);
        System.out.println("Matrix C:");
        printMatrix(result[2]);
        System.out.println("Matrix D:");
        printMatrix(result[3]);
        System.out.println("Matrix E:");
        printMatrix(result[4]);
        System.out.println("Matrix F:");
        printMatrix(result[5]);
    }
}