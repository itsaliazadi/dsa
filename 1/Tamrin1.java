public class Tamrin1 {
    public static int f(int a, int b, int res){
        if (a < b){
            return res;
        }
        return 1 + f(a - b, b, res);
    }

    public static void main(String[] args){
        System.out.println(f(123, 4, 0));
    }
}