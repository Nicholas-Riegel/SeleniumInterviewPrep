package com.sel_int_prep;

public class Main {
    public static void main(String[] args) {
        // Part 1: strings never change. toUpperCase() returns a NEW string.
        String name = "selenium";
        String upper = name.toUpperCase();
        System.out.println("name = " + name);
        System.out.println("upper = " + upper);

        // Part 2: == vs equals
        String a = "java";              // literal, stored in the string pool
        String b = "java";              // same literal, so Java reuses the same object
        String c = new String("java");  // forces a new object
        String part = "ja";
        String e = part + "va";         // built at runtime, so a new object

        System.out.println("a == b: " + (a == b));          // true
        System.out.println("a == c: " + (a == c));          // false
        System.out.println("a equals c: " + a.equals(c));   // true
        System.out.println("a == e: " + (a == e));          // false
        System.out.println("a equals e: " + a.equals(e));   // true

        // Part 3: null-safe comparison
        String missing = null;
        System.out.println("safe: " + "expected".equals(missing)); // false, no exception
        // missing.equals("expected") would throw NullPointerException
    }
}