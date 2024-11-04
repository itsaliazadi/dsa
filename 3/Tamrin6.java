public class Tamrin6 {  
    public static void f(int n) {
        int[] currentRow = new int[n];

        for (int i = 0; i < n; i++) {
            currentRow[0] = 1; 

            for (int j = 0; j <= i; j++) {
                if (j > 0) { 
                    currentRow[j] = currentRow[j - 1] * (i - j + 1) / j; 
                }
                System.out.print(currentRow[j] + " "); 
            }
            System.out.println(); 
        }
    }

    public static void main(String[] args) {
        System.out.println("\nTest case n=5:");
        f(5);
    }
}

