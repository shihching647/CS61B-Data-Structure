package homework.hw4;

import homework.hw4.list.*;

public class Test {

    public static void main(String[] args) {
        DList list1 = new DList();
        DList list2 = new DList();
        list1.insertFront(1);
        list2.insertFront(2);
        list1.remove(list2.front());
        System.out.println(list1 + " size = " + list1.length()); // Violate the invariants
    }
}
