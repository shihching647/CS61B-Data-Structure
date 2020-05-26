package handout.lecture04;

public class PrintIntegers {

    public static void main(String[] args){
        oneToX(5);
    }

    private static void oneToX(int x) {
        if(x < 1){
            return;
        }
        oneToX(x - 1);
        System.out.println(x);
    }

}
