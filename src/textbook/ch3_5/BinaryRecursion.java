package textbook.ch3_5;

//TODO BinaryRecursion的例子還不懂
public class BinaryRecursion {

    public static void main(String[] args) {
        int[] nums = {1, 2, 3, 4, 5, 6, 7, 8, 9, 10};
        System.out.println(binarySum(nums, 0, nums.length));
    }

    /** Returns the sum of subarray array[i-1] through array[n-1] inclusive. */
    /*只適用於n是2的次方*/
    public static int binarySum(int[] array, int i, int n) {
        if(n == 1) return array[i];
        else{
            return binarySum(array, i, n / 2) + binarySum(array, i + n / 2, n / 2);
        }
    }
}
