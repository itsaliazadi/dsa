public class Tamrin5 {
    public static int f(int[] arr, int index, int currentMax){
        if (index == arr.length){
            return currentMax;
        }
        if (arr[index] > currentMax){
            currentMax = arr[index];
        }
        return f(arr, index + 1, currentMax);
    }

    public static void main(String[] args){
        int[] nums = {1,2,3,4,5,38,7,8,9,10};
        System.out.println(f(nums, 0, 0));
    }
}