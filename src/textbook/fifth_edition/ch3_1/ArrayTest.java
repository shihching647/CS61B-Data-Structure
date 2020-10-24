package textbook.fifth_edition.ch3_1;

import java.util.Arrays;
import java.util.Random;

public class ArrayTest {
    public static void main(String[] args){
        int num[] = new int[10];
        Random rand = new Random();
        rand.setSeed(System.currentTimeMillis());

        for(int i = 0; i < num.length; i++){
            num[i] = rand.nextInt(100);
        }
        int[] old = num.clone();

        System.out.println("array equal before sort: " + Arrays.equals(num, old));
        Arrays.sort(num);
        System.out.println("array equal after sort: " + Arrays.equals(num, old));
        System.out.println("old = " + Arrays.toString(old));
        System.out.println("num = " + Arrays.toString(num));
    }
}
