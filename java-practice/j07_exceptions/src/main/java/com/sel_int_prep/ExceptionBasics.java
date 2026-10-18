package com.sel_int_prep;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

public class ExceptionBasics {
    public static void main(String[] args) {

        // Unchecked Exceptions (ArrayIndexOutOfBoundsException, RuntimeException subclasses): the compiler doesn't force you to handle it. The compiler doesn't check. You can write the code with no try at all, and it compiles.
        // The compiler's job: before running anything, the compiler checks some exceptions and refuses to compile if they aren't handled. Which exceptions it checks depends on the type:

        // Checked Exceptions (IOException): the compiler checks. If a method can throw one and you don't catch it or declare it with throws, you get a compile error. You can't even run the program.
        
        // the program would continue, since it's in a try/catch
        // without the try/catch it would crash
        //         If you want the full stack trace while still handling the error, call printStackTrace() inside the catch:

        // } catch (ArrayIndexOutOfBoundsException e) {
        //     System.out.println("caught: " + e.getMessage());
        //     e.printStackTrace();
        // }

        try {
            int[] items = new int[2];
            items[5] = 1;
        } catch (ArrayIndexOutOfBoundsException e) {
            System.out.println("caught: " + e.getMessage());
        }


        // try / catch / finally
        System.out.println(divide(10, 2));
        System.out.println(divide(10, 0));

        // Checked: the caller must catch it or declare it with throws
        String path = "config.properties";
        try {
            String content = readFile(path);
            System.out.println("file has " + content.length() + " characters");
        } catch (IOException e) {
            System.out.println("could not read file " + path);
        }
    }

    // for "try / catch / finally" example
    static int divide(int a, int b) {
        try {
            return a / b;
        } catch (ArithmeticException e) {
            System.out.println("cannot divide by zero");
            return -1;
        } finally {
            System.out.println("finally runs");
        }
    }

    // for "Checked "
    static String readFile(String path) throws IOException {
        return Files.readString(Path.of(path));
    }
}