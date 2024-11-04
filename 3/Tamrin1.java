public class Tamrin1{  
    public static int f(int n){
        if (n == 1 || n == 2){
            return 1;
        }
        int[] a = new int[n];
        a[0] = 1;
        a[1] = 1;
        for (int i = 2; i < n; i++){
            a[i] = a[i - 1] + a[i - 2];
        }
        return a[n - 1];
    }

    public static void main(String[] args){
         System.out.println(f(6));
    }
}
