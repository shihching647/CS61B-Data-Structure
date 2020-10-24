package textbook.fifth_edition.ch5_1;
/*
 *  Assume all HTML tags are in the simple form(like <tag>content</tag>).
 *  There are no tag with attributes(like <a href = "/test/here"></a>),
 *  and no HTML empty elements(like <br>, <hr>).
 */

import java.util.Scanner;

public class HTML {

    public static final int CAPACITY = 10000;

    public static String[] parseHTML(Scanner sc) {
        String[] tags = new String[CAPACITY];
        int count = 0;
        String token;
        while(sc.hasNextLine()) {  //如果要停止需要EOF. Windows:Ctrl+Z, Mac: command+D
            while((token = sc.findInLine("<[^>]*>")) != null){ //find the next tag, pattern is a regex
                tags[count++] = stripEnds(token);
            }
            sc.nextLine(); // go to the next line
        }

        return tags;
    }

    /*Strip the first and last characters off a <tag> string. */
    public static String stripEnds(String tag) {
        if(tag.length() <= 2) return null;
        return tag.substring(1, tag.length() - 1);
    }

    /*Test if every opening tag has a matching closing tag*/
    public static boolean isHTMLMatched(String[] tags) {
        Stack<String> stack = new NodeStack<>(); // store openingTags
        for(int i = 0; i < tags.length && tags[i] != null; i++) {
            if(isOpeningTag(tags[i])) stack.push(tags[i]);
            else {
                if(stack.isEmpty()) return false;
                if(!areMatchingTags(stack.pop(), tags[i])) return false;
            }
        }
        if(stack.isEmpty()) return true;
        else return false;
    }

    /*Test if a stripped tag string is empty or a true opening tag*/
    public static boolean isOpeningTag(String tag) {
        return tag.isEmpty() || tag.charAt(0) != '/';
    }

    /*Test if stripped opening tag is matches closing tag(first character is /)*/
    public static boolean areMatchingTags (String openingTag, String closingTag) {
        return openingTag.equals(closingTag.substring(1));
    }

    public static void main(String[] args) {
        if(isHTMLMatched(parseHTML(new Scanner(System.in)))) {
            System.out.println("The input file is a matched HTML document.");
        } else {
            System.out.println("The input file is not a matched HTML document.");
        }
    }
}
