import java.util.HashSet;
import java.util.ArrayList;
import java.util.Collections;

public class Tamrin12 {
    private static HashSet<ArrayList<Integer>> previousCombinations = new HashSet<>();

    public static void f(int n, ArrayList<Integer> current_combination) {
        if (n == 0) {
            Collections.sort(current_combination);
            if (!previousCombinations.contains(current_combination)) {
                previousCombinations.add(new ArrayList<>(current_combination));
                System.out.println(current_combination);
            }
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

