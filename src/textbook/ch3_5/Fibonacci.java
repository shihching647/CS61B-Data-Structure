package textbook.ch3_5;

import java.util.Arrays;

public class Fibonacci {

    public static void main(String[] args) {
        for(int i = 0; i <= 10; i++){
//            System.out.println("F" + i + " = " + binaryFib(i));
            System.out.println("F" + i + " = " + linearFib(i)[0]);
        }

    }

    public static int binaryFib(int k) {
        if(k <= 1) {
            return k;
        }else{
            return binaryFib(k - 1) + binaryFib(k - 2);
        }
    }
    /** Returns array containing the pair of Fibonacci numbers, F(n) and F(n−1). */
    public static int[] linearFib(int k) {
        if(k <= 1) {
            int[] answer = new int[] {k, 0};
            return answer;
        } else {
            int[] temp = linearFib(k - 1); // returns {Fn−1, Fn−2}
            int[] answer = {temp[0] + temp[1], temp[0]}; // we want {Fn, Fn−1}
            return answer;
        }
    }
}
