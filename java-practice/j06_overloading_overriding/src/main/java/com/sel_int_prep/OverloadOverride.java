package com.sel_int_prep;

public class OverloadOverride {

    public static void main(String[] args) {
        
        // Overloading: same name, different parameters, same class
        Printer printer = new Printer();
        printer.print("Hello");        // Hello
        printer.print("Hello", 3);     // Hello Hello Hello
        printer.print(42);             // number: 42

        // Overloading is chosen at compile time from the declared type
        Object text = "hi";
        printer.print(text);           // object: hi

        // Overriding: a subclass replaces the parent's method
        Animal dog = new Dog();
        Animal generic = new Animal();
        System.out.println(dog.sound());     // Woof
        System.out.println(generic.sound()); // ...
    }

    static class Printer {

        void print(String text) {
            System.out.println(text);
        }

        void print(String text, int times) {
            System.out.println((text + " ").repeat(times).trim());
        }

        void print(int number) {
            System.out.println("number: " + number);
        }

        void print(Object o) {
            System.out.println("object: " + o);
        }
    }

    static class Animal {
        String sound() {
            return "...";
        }
    }

    static class Dog extends Animal {
        @Override
        String sound() {
            return "Woof";
        }
    }
}