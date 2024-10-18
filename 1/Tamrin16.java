import java.util.ArrayList;

public class Tamrin16 {
    public static void f(ArrayList<Integer> arr, ArrayList<Integer> subArr, int index){
        if (index == arr.size()){
            System.out.println(subArr);
            return;
        }
        f(arr, subArr, index + 1);
        subArr.add(arr.get(index));
        f(arr, subArr, index + 1);
        subArr.remove(subArr.size() - 1);
    }

    public static void main(String[] args){
        ArrayList<Integer> arr = new ArrayList<>();
        arr.add(1); 
        arr.add(2);
        arr.add(3);
        ArrayList<Integer> subArr = new ArrayList<>();
        f(arr, subArr, 0);
    }
}