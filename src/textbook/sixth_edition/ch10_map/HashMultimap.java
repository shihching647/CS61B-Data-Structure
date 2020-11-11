package textbook.sixth_edition.ch10_map;

import java.util.*;
import java.util.AbstractMap;
import java.util.Map;

public class HashMultimap<K,V> {
    Map<K, List<V>> map = new HashMap<>();
    int total = 0;

    public HashMultimap() { }

    public int size() {return total;}

    public boolean isEmpty() {return total == 0;}

    public Iterable<V> get(K key) {
        List<V> secondary = map.get(key);
        if(secondary == null) {
            secondary = new ArrayList<>();
        }
        return secondary;
    }

    public void put(K key, V value) {
        List<V> secondary = map.get(key);
        if(secondary == null) {
            secondary = new ArrayList<>();
            map.put(key, secondary);
        }
        secondary.add(value);
        total++;
    }

    public boolean remove(K key, V value) {
        List<V> secondary = map.get(key);
        if(secondary != null) {
            if(secondary.remove(value)) {
                total--;
                if(secondary.isEmpty()) map.remove(key);
                return true;
            }
        }
        return false;
    }

    public Iterable<V> removeAll(K key) {
        List<V> secondary = map.get(key);
        if(secondary == null)
            return new ArrayList<>();
        map.remove(key);
        total -= secondary.size();
        return secondary;
    }

    public Iterable<Map.Entry<K,V>> entries() {
        List<Map.Entry<K,V>> result = new ArrayList<>();
        for(Map.Entry<K,List<V>> secondary : map.entrySet()) {
            for (V value : secondary.getValue()) {
                K key = secondary.getKey();
                result.add(new AbstractMap.SimpleEntry<>(key, value));
            }
        }
        return result;
    }

    public Iterable<K> keys() {
        List<K> result = new ArrayList<>();
        for(Map.Entry<K,V> entry : entries()) {
            result.add(entry.getKey());
        }
        return result;
    }

    public Iterable<V> values() {
        List<V> result = new ArrayList<>();
        for(Map.Entry<K,V> entry : entries()) {
            result.add(entry.getValue());
        }
        return result;
    }

    public Iterable<K> keySet() {
        List<K> result = new ArrayList<>();
        for(Map.Entry<K,V> entry : entries()) {
            K key = entry.getKey();
            if(!result.contains(key))
                result.add(key);
        }
        return result;
    }

    public static void main(String[] args) {
        HashMultimap<Integer,String> map = new HashMultimap<>();
        map.put(0, "zero");
        map.put(1, "one");
        map.put(1, "one_");
        map.put(1, "one__");
        map.put(2, "two");
        map.put(3, "three");
        map.put(3, "three_");
        System.out.println(map.isEmpty());
        System.out.println(map.size());
        System.out.println(map.get(1));
        System.out.println(map.remove(0,"zero_"));
        System.out.println(map.size());
        System.out.println(map.remove(0,"zero"));
        System.out.println(map.size());
        System.out.println(map.removeAll(3));
        System.out.println(map.size());
        System.out.println(map.keySet());
        System.out.println(map.keys());
        System.out.println(map.values());
        System.out.println(map.entries());
    }
}
