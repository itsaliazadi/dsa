public class Tamrin6 {
    public static int f(int a, int b){
        if (b == 0){
            return 0;
        }
        return f(a, b - 1) + a;
    }

    public static void main(String[] args){
        System.out.println(f(12, 5));
    }
}