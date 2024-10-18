import java.util.Arrays;

public class Tamrin3 {
    public static void f(int[] arr, int start, int end){
        if (start >= end){
            System.out.println(Arrays.toString(arr));
            return;
        }
        int temp = arr[start];
        arr[start] = arr[end];
        arr[end] = temp;
        f(arr, start + 1, end - 1);
    }

    public static void main(String[] args){
        int[] nums = {1,2,3,4,5,6,7,8,9,10};
        f(nums, 0, nums.length - 1);
    }
}