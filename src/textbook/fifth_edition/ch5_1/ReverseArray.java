package textbook.fifth_edition.ch5_1;

import java.util.Arrays;

public class ReverseArray {

    public static void main(String[] args) {
        Integer[] intArray = {1, 2, 3, 4, 5, 6};
        String[] stringArray = {"Alice", "Bob", "Carol", "Dave", "Eve"};
        System.out.println("intArray = " + Arrays.toString(intArray));
        System.out.println("stringArray = " + Arrays.toString(stringArray));
        System.out.println("Reversing...");
        reverse(intArray);
        reverse(stringArray);
        System.out.println("intArray = " + Arrays.toString(intArray));
        System.out.println("stringArray = " + Arrays.toString(stringArray));
    }

    /*Reverse array in time O(n) */
    public static <E> void reverse(E[] array) {
        Stack<E> stack = new ArrayStack<>(array.length);
        for(int i = 0; i < array.length; i++) {
            stack.push(array[i]);
        }
        for(int i = 0; i < array.length; i++) {
            array[i] = stack.pop();
        }
    }
}
