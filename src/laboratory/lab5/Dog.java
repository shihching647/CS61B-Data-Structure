package laboratory.lab5;

public class Dog extends Animal implements Speakable{

    public static void test() {
        System.out.println(Speakable.CONSTANT);
        System.out.println(Animal.CONSTANT);
    }

    public void attack() {
        System.out.println("Bite!!!");
    }

    public static void main(String[] args) {
        Dog dog = new Dog();
        ((Animal) dog).attack();

//        Animal animal = new Animal();
//        ((Dog) animal).attack();

    }
}
