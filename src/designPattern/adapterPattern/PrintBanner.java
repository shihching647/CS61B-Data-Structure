package designPattern.adapterPattern;
//Adapter
public class PrintBanner extends Print {
    private Banner banner;//Adaptee

    public PrintBanner(String string) {
        this.banner = new Banner(string);
    }
    public void printWeak() {
        banner.showWithParen();
    }
    public void printStrong() {
        banner.showWithAster();
    }
}
