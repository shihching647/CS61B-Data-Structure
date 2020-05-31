package laboratory.lab5;

public class part_1 {

    static class X {
        public X () {
            System.out.println("X is constructed");
        }
    }

    static class Y extends X {
        public Y () {
            System.out.println("Y is constructed");
        }
    }

    public static void main(String[] args) {
//        X x = new X();
//        Y y = new Y();
//        x = y;
//        y = (Y) x;
        X[] xa = new X[4];
        Y[] ya = new Y[4];
//        xa = ya;
//        ya = (Y[]) xa;
        ya = (Y[]) xa;
        xa = ya;
    }
}
