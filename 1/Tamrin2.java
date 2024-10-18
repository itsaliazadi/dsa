import java.util.ArrayList;

public class Tamrin2 {
    public static double f(ArrayList<Integer> arr, int index){
        if (index == 0){
            return 0;
        }
        return arr.get(index - 1) + f(arr, index - 1);
    }

    public static void main(String[] args){
        ArrayList<Integer> arr = new ArrayList<>();
        arr.add(1); 
        arr.add(2);
        arr.add(3);
        arr.add(4);
        int size = arr.size();
        System.out.println(f(arr, size) / size);
    }
}