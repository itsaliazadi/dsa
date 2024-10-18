public class Tamrin8 {
    public static String f(int n, String s){
        if (n == 0){
            return s + "\n";
        }
        return f(n - 1, s + "0") + f(n - 1, s + "1");
    }

    public static void main(String[] args){
        System.out.println(f(15, ""));
    }
}