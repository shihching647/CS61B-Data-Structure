package designPattern.adapterPattern;
//Client
public class Main {
    public static void main(String[] args) {
        Print p = new PrintBanner("Hello"); //Print是Target
        p.printWeak();
        p.printStrong();
    }
}
