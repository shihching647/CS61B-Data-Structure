package textbook.ch3_4;

import textbook.ch3_3.DList;
import textbook.ch3_3.DNode;

import java.util.Random;

public class InsertionSort_DList {
    public static void main(String[] args) {
        DList list = new DList();
        Random rand = new Random();
//        for(int i = 0; i < 10; i++) {
//            list.addLast(new DNode(String.valueOf(rand.nextInt(10)), null, null));
//        }
        list.addLast(new DNode(String.valueOf(4), null, null));
        list.addLast(new DNode(String.valueOf(3), null, null));
        list.addLast(new DNode(String.valueOf(2), null, null));
        list.addLast(new DNode(String.valueOf(5), null, null));
        list.addLast(new DNode(String.valueOf(8), null, null));
        list.addLast(new DNode(String.valueOf(9), null, null));
        list.addLast(new DNode(String.valueOf(0), null, null));
        System.out.println(list);
        insertionSort(list);
        System.out.println(list);
    }

    public static void insertionSort(DList list) {
        if(list.size() <= 1) {
            return;
        }
        DNode pivot, insertionNode, end = list.getFirst();

        while(end != list.getLast()) {
            pivot = end.getNext();
            list.remove(pivot);
            insertionNode = end;
            //用hasPrev(insertionNode)做判斷!!
            while(list.hasPrev(insertionNode) && pivot.getElement().compareTo(insertionNode.getElement()) <= 0) {
                insertionNode = insertionNode.getPrev();
            }
            list.addAfter(insertionNode, pivot);
            //如果要插入那個本是身就是最後面的位置(不用做任何移動),沒加入這行會變成無窮迴圈
            if(insertionNode == end){
                end = end.getNext();
            }
        }
    }
}
