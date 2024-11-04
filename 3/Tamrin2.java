public class Tamrin2{  
    public static int f(int n, int r){
        if (n < r){
            return 0;
        }
        int[] a = new int[n - r + 1];
        a[0] = 1;
        for (int i = 1; i < n - r + 1; i++){
            a[i] = a[i - 1] * (r + i) / i;
        }
        return a[n - r];
    }

    public static void main(String[] args){
        System.out.println(f(4, 2));
    }
}