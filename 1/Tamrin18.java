 public class Tamrin18 {
 
    public static void f(String s){
    String temp = "";
    String rep = "";

    if (s.length() == 1 || s.isEmpty()){
        System.out.println(true);
        return;
    }

    for (int i = 1; i < s.length(); i++){
        if (s.charAt(i) == s.charAt(0)){
            rep = s.substring(0, i);
            break;
        }
    }

    for (int i = 0; i < s.length(); i = i + rep.length()){
        temp = s.substring(i, i + rep.length());
        if (!temp.equals(rep)){
            System.out.println(false);
            return;
        }
        else if (temp.length() == 2 && temp.charAt(0) != temp.charAt(1)){
            System.out.println(false);
            return;
        }
    }
    f(temp.substring(1));

    }

    public static void main(String[] args){
        f("ALLALL");
    }

 }
