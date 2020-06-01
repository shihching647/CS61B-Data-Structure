package laboratory.lab5;

public class Animal {
    public static final int CONSTANT = 1;
    private String name;
    private int age;

    public Animal() {
        name = "undefined";
        age = 0;
    }

    public Animal(String name, int age) {
        this.name = name;
        this.age = age;
    }

    public void speak(String name) {
        System.out.println("My name is " + name + " and I'm " + age + "years old.");
    }

    public void attack() {
        System.out.println("Attack");
    }
}
