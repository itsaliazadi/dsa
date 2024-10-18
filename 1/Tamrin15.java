import java.util.ArrayList;
import java.util.List;

public class Tamrin15 {

    private static final int N = 8;

    public static void main(String[] args) {
        List<List<String>> solutions = new ArrayList<>();
        solveNQueens(0, new int[N], solutions);
        printSolutions(solutions);
    }

    private static void solveNQueens(int row, int[] cols, List<List<String>> solutions) {
        if (row == N) {
            List<String> board = new ArrayList<>();
            for (int i = 0; i < N; i++) {
                StringBuilder sb = new StringBuilder();
                for (int j = 0; j < N; j++) {
                    sb.append(cols[i] == j ? "X" : ".");
                }
                board.add(sb.toString());
            }
            solutions.add(board);
            return;
        }

        for (int col = 0; col < N; col++) {
            if (isSafe(row, col, cols)) {
                cols[row] = col;
                solveNQueens(row + 1, cols, solutions); 
            }
        }
    }

    private static boolean isSafe(int row, int col, int[] cols) {
        for (int i = 0; i < row; i++) {
            if (cols[i] == col || 
                cols[i] - i == col - row || 
                cols[i] + i == col + row) {
                return false;
            }
        }
        return true;
    }

    private static void printSolutions(List<List<String>> solutions) {
        for (List<String> solution : solutions) {
            for (String row : solution) {
                System.out.println(row);
            }
            System.out.println();
        }
    }
}
