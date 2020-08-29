package textbook.ch9_2_HashTableMap;

import java.security.InvalidKeyException;
import java.util.Scanner;

public class WordCount {
    public static void main(String[] args) throws InvalidKeyException {
        //I am a good boy and I live in Taiwan
        Scanner sc = new Scanner(System.in);
        sc.useDelimiter("[^a-zA-Z]");
        HashTableMap<String,Integer> map = new HashTableMap<>();
        String word;
        Integer count;
        while(sc.hasNext()) {
            word = sc.next();
            count = map.get(word);
            if(word.equals("")) continue; //ignore null strings between delimiter
            if(count == null) {
                map.put(word, 1);
            } else {
                map.put(word, ++count);
            }
        }
        int maxCount = 0;
        String maxWord = "No word";
        for(Entry<String,Integer> entry : map.entrySet()) {
            if(maxCount < entry.getValue()) {
                maxCount = entry.getValue();
                maxWord = entry.getKey();
            }
        }
        System.out.println("The most frequent word is " + maxWord + " with count = " + maxCount);
    }
}
