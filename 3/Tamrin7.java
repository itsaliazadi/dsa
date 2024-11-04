public class Tamrin7{  
    public static boolean f(int[][] a, int[][] b, int n){
        for (int i = 0; i < n; i++){
            for (int j = 0; j < n; j++){
                if (a[i][j] != b[i][j]){
                    return false;
                }
            }
        }
        return true;
    }

    public static void main(String[] args) {
        int[][] matrix1 = {
            {1, 2, 3},
            {4, 5, 6},
            {7, 8, 9}
        };
        int[][] matrix2 = {
            {1, 2, 3},
            {4, 5, 6},
            {7, 8, 9}
        };
        System.out.println("Test case 1: " + f(matrix1, matrix2, 3)); 
    }
}
