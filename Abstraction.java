abstract class Animal{
    abstract void sound();
    public void sleep(){
        System.out.println("The animal is sleeping");
    }
}

class Cat extends Animal{
    void sound(){
        System.out.println("The cat is sounding like Mew, Mew");
    }
}

class Dog extends Animal{
    void sound(){
        System.out.println("The cat is sounding like woof, woof");
    }
}

public class Abstraction{
    public static void main(String[] args) {
        Animal cat = new Cat();
        cat.sound();
        cat.sleep();
    }
}