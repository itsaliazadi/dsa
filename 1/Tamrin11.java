import java.util.ArrayList;

public class Tamrin11 {
    public static void f(int n, ArrayList<Integer> current_combination) {
        if (n == 0) {
            System.out.println(current_combination);
            return;
        }
        if (n < 0) {
            return;
        }
        
        int[] coins = {2, 5, 10};
        for (int coin : coins) {
            current_combination.add(coin);
            f(n - coin, current_combination);
            current_combination.remove(current_combination.size() - 1);
        }
    }

    public static void main(String[] args) {
        ArrayList<Integer> arr = new ArrayList<>();
        f(11, arr);
    }
}