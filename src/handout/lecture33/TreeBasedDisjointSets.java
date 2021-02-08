package handout.lecture33;

import java.util.Arrays;

/**
 * Implementing Quick-Union with an Array :
 * Suppose the items are non-negative integers, numbered from zero. We’ll use an
 * array to record the parent of each item. If an item has no parent, we’ll
 * record the size of its tree. To distinguish it from a parent reference, we’ll
 * record the size s as the negative number -s. Initially, every item is the root
 * of its own tree, so we set every array element to -1.
 */
public class TreeBasedDisjointSets {

    public static void main(String[] args) {
        Integer[] array = {1, -4, -1, 8, 5, 8, 1, 3, -5, 1};
        union(1, 8, array);
        System.out.println(Arrays.deepToString(array));
        Integer[] array2 = {-10, 0, 0, 0, 1, 1, 1, 4, 4, 4};
        find(7,array2);
        System.out.println(Arrays.deepToString(array2));
    }

    //union operation with the union-by-size strategy.
    public static void union(int root1, int root2, Integer[] array) {
        if (array[root1] < array[root2]) {
            array[root1] += array[root2];
            array[root2] = root1;
        } else {
            array[root2] += array[root1];
            array[root1] = root2;
        }
    }

    //find operation with the path compression strategy.
    public static int find(int x, Integer[] array) {
        if (array[x] < 0)
            return x;
        else {
            array[x] = find(array[x], array);
            return array[x];
        }
    }
}
