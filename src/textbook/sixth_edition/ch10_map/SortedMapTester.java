package textbook.sixth_edition.ch10_map;

import textbook.sixth_edition.ch9_priorityQueue.Entry;

public class SortedMapTester {

    public static void main(String[] args) {
        SortedMap<Integer, String> map = new SortedTableMap<>();
        map.put(0, "zero");
        map.put(1, "one");
        map.put(2, "two");
        map.put(3, "three");
        map.put(5, "five");
        map.put(6, "six");
        System.out.println(map);
        System.out.println(map.firstEntry());           //0
        System.out.println(map.lastEntry());            //6
        System.out.println(map.ceilingEntry(4));   //5
        System.out.println(map.floorEntry(4));     //3
        System.out.println(map.ceilingEntry(5));   //5
        System.out.println(map.floorEntry(5));     //5
        System.out.println(map.higherEntry(5));    //6
        System.out.println(map.lowerEntry(5));     //3
        for(Entry<Integer,String> entry : map.subMap(1,6)) {
            System.out.print(entry + ", ");
        }
    }
}
