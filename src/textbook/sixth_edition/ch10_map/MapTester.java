package textbook.sixth_edition.ch10_map;

import textbook.sixth_edition.ch9_priorityQueue.Entry;

import java.security.InvalidKeyException;

public class MapTester {
    public static void main(String[] args) throws InvalidKeyException {
        Map<String, Integer> map = new SortedTableMap<>();
        System.out.println(map);
        System.out.println(map.put("one", 1));
        System.out.println(map);
        map.put("two", 2);
        System.out.println(map);
        map.put("three", 3);
        System.out.println(map);
        map.put("four", 4);
        System.out.println(map);
        map.put("five", 5);
        System.out.println(map);
        map.put("six", 6);
        System.out.println(map);
        map.put("seven", 7);
        System.out.println(map);
        System.out.println(map.put("seven", 777));
        System.out.println(map);
        map.remove("one");
        System.out.println(map);
        map.remove("three");
        System.out.println(map);
        System.out.println(map.get("seven"));
        System.out.println(map.get("zero"));
        for(String key : map.keySet()) {
            System.out.print(key + ", ");
        }
        System.out.println();
        for(int value : map.values()) {
            System.out.print(value + ", ");
        }
        System.out.println();
        for(Entry<String, Integer> entry : map.entrySet()) {
            System.out.print("(" + entry.getKey() + ", " + entry.getValue() + ")" + ", ");
        }
    }
}
