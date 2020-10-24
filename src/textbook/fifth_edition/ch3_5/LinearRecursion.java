package textbook.fifth_edition.ch3_5;

import java.util.Arrays;

public class LinearRecursion {

    public static void main(String[] args) {
        int[] numArray = {1, 2, 3, 4, 5, 6, 7, 8, 9, 10};
        System.out.println(linearSum(numArray, 10));
        reverseArray(numArray, 0, numArray.length - 1);
//        iterativeReverseArray(numArray, 0, numArray.length - 1);
        System.out.println(Arrays.toString(numArray));
    }

    /**
     * A integer array numArray and an integer n >= 1,such that numArray has at least n elements
     * @param numArray A integer array that you want to calculate the summary
     * @param n The fisrt n element you want to sum
     * @return The sum of the first n integers in A
     */
    public static int linearSum(int[] numArray, int n) {
        if(n == 1){
            return numArray[0];
        }else{
            return numArray[n - 1] + linearSum(numArray, n - 1);
        }
    }

    /** Example of the tail recursion
     *  The reversal of the elements in A starting at index i and ending at j
     * @param array An array you want to reserve
     * @param i index i
     * @param j index j
     */
    public static void reverseArray(int[] array, int i, int j) {
        if(i < j) {
            //swap element i, j
            int temp = array[i];
            array[i] = array[j];
            array[j] = temp;
            //call resursively
            reverseArray(array, i + 1, j - 1);
        }
    }

    public static void iterativeReverseArray(int[] array, int i, int j) {
        while(i < j) {
            int temp = array[i];
            array[i] = array[j];
            array[j] = temp;
            i++;
            j--;
        }
    }
}
