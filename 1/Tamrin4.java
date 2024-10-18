public class Tamrin4 {
    public static String f(int a){
        if (a == 0) {
            return "";
        }
        int reminder = a % 2;  
        return reminder + f(a / 2) ;  
    }

    public static void main(String[] args){
        System.out.println(f(151));  
    }
}