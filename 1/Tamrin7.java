public class Tamrin7 {
    public static int f(int a, int b) {
        if (a % b == 0) {
            return b;
        } 
        if (b % a == 0) {
            return a;
        } 
        if (a > b) {
            return f(a % b, b);
        } else if (b > a) {
            return f(a, b % a); 
        }
        return 0; 
    }

    public static void main(String[] args) {
        System.out.println(f(12, 18));
    }
}
