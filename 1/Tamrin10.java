public class Tamrin10 {
    public static double f(int n, int i){
        if (n == i){
            return 1;
        }
        return 1 + (double) 1 / (i + 1) * f(n, i + 1);
    }

    public static void main(String[] args){
        System.out.println(f(4, 1));
    }
}