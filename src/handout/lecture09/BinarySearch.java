package handout.lecture09;

import java.util.Arrays;
import java.util.Random;

public class BinarySearch {

    public static final int FAILURE = -1;

    private static int bsearch(int[] i, int left, int right, int findme) {
        if(left > right) {
            return FAILURE;
        }
        int mid = (left + right) / 2;
        if(i[mid] == findme) {
            return mid;
        } else if(i[mid] > findme){ //findme在左半邊
            return bsearch(i, left, mid - 1, findme);
        } else { //findme在右半邊
            return bsearch(i, mid + 1, right, findme);
        }
    }

    public static int bsearch(int[] i, int findme) {
       return bsearch(i, 0, i.length - 1, findme);
    }

    public static void main(String[] args) {
        Random rand = new Random();
        rand.setSeed(1);
        int[] randArray = new int[100];
        for(int i = 0; i < randArray.length; i++) {
            randArray[i] = rand.nextInt(10);
        }
        System.out.println(Arrays.toString(randArray));
        Arrays.sort(randArray);
        System.out.println(Arrays.toString(randArray));
        System.out.println(bsearch(randArray, 1)); //如果有重複的->不保證回傳哪一個
    }
}
