public class Tamrin13 {
    public static void f(int n, int start, int end){
        String[] chars = {"S", "A", "D"};
        if (n == 1){
            System.out.println(chars[start - 1] + "->" + chars[end - 1]);
            return;
        }
        int help = 6 - (start + end);
        f(n - 1, start, help);
        System.out.println(chars[start - 1] + "->" + chars[end - 1]);
        f(n - 1, help, end);
    }

    public static void main(String[] args){
        f(3, 1, 3);
    }
}