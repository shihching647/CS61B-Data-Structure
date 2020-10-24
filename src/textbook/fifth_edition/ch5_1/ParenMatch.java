package textbook.fifth_edition.ch5_1;

import java.util.Arrays;

public class ParenMatch {

    public static final String[] OPENING_GROUPING_SYMBOL = {"{", "[", "("};
    public static final String[] CLOSING_GROUPING_SYMBOL = {"}", "]", ")"};

    public static void main(String[] args) {
        //match
        String test1 = "{[(x + 1) + (y - 2)] * [(z + 2) * 3]}";
        String test2 = "((()(()){([()])}))";

        //doesn't match
        String test3 = "({[])}";
        String test4 = "}";
        String test5 = "{";
        System.out.println(parenMatch(test2));
    }

    public static boolean parenMatch(String str) {
        Stack<String> stack = new ArrayStack<>();
        for(int i = 0; i < str.length(); i++) {
            String token =  str.substring(i, i + 1);
            if(Arrays.asList(OPENING_GROUPING_SYMBOL).contains(token)) { //asList將array轉成ArrayList
                stack.push(token);
            } else if(Arrays.asList(CLOSING_GROUPING_SYMBOL).contains(token)) {
                if(stack.isEmpty()) return false;
                if(!matcheParen(stack.pop(), token)) return false;
            }
        }
        if(stack.isEmpty()) return true;
        else return false;
    }

    public static boolean matcheParen(String openingSymbol, String closingSymbol) {
        switch (openingSymbol) {
            case "{" : if(closingSymbol.equals("}")) return true;
            case "[" : if(closingSymbol.equals("]")) return true;
            case "(" : if(closingSymbol.equals(")")) return true;
        }
        return false;
    }
}
