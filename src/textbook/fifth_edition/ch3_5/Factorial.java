package textbook.fifth_edition.ch3_5;

public class Factorial {

    public static void main(String[] args) {
        for(int i = 0; i <= 10; i++) {
            System.out.println(i + "! = " + recursiveFactorail(i));
        }
    }

    public static int recursiveFactorail(int n) {
        if(n == 0) {
            return 1;
        }else{
            return n * recursiveFactorail(n - 1);
        }
    }
}
