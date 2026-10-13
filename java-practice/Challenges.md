**An interface is a promise.** It lists method names, and any class that `implements` it promises to provide those methods. The interface says *what* can be done, and each class decides *how*. In Selenium, `WebDriver` promises `get()`, `quit()` and so on, and `ChromeDriver` provides the real code for Chrome.

**Why it's useful:** code can work with the interface and not care which class is behind it. `WebDriver driver = new ChromeDriver();` is the example you've already used.

**Challenge 1: shapes.** In `java-practice`, create a file named `Shapes.java`. It should contain:

1. A `Shape` interface with one method, `area()`, which returns a `double`.
2. A `Circle` class that takes a radius in its constructor and implements `Shape`. Use `Math.PI` for the area.
3. A `Rectangle` class that takes a width and a height and implements `Shape`.
4. A `main` method that creates one `Circle` and one `Rectangle`, puts both in a `List<Shape>`, then loops over the list and prints each area.

Two practical notes:
- The file name must match the one `public` class, which is `Shapes`. Put `public class Shapes { ...main... }` **first** in the file. Java 21 runs the first class it finds. Put the interface and the other two classes below it, without `public`.
- Run it from inside `java-practice` with `java Shapes.java`.

Try writing it yourself, and tell me what you get or where you get stuck. A rough attempt that doesn't compile is fine, and I can help from there.

# 2

Ran terminal command: cd /Users/nicholas/Dev/Selenium/SeleniumInterviewPrep/java-practice && find . -not -path "*/target/*" -type f | sort; echo ---; cat j02_abstract_classes/pom.xml; for f in j02_abstract_classes/src/main/java/com/sel_int_prep/*.java; do echo "=== $f"; cat $f; echo; done

Your `j02_abstract_classes` module is set up, and it's still a copy of the interface version, which is the right starting point.

First, a short explanation. An abstract class is a **partly finished class**. It can hold real fields and real methods that subclasses share, and it can leave some methods `abstract`, meaning "each subclass must write its own version". You can't create one directly with `new`, because it's incomplete by design.

**Step: turn `Shapes` into an abstract class.** Edit only `Shapes.java`, so it looks like this:

```java
package com.sel_int_prep;

public abstract class Shapes {
    public abstract double area();
}
```

- `abstract class` replaces `interface`, and it marks the class as incomplete.
- `public abstract double area();` is the same promise as before. It has no body and ends with `;`, but this time you must write the word `abstract`. In an interface that was implied.

Save it, but don't touch `Circle` or `Rectangle` yet. Run this from inside `j02_abstract_classes`:

```bash
mvn compile
```



**Challenge 2: give every shape a name and a description.** 

Now for the part an interface can't do: an abstract class can hold **shared state and shared code**.

1. `Shapes` gets a `name` field (a `String`) that every shape has. It should be set through a **constructor** in `Shapes`.
2. `Shapes` gets a normal method called `describe()`, with a real body that returns a `String` like `"Circle with area 12.57"`. It uses `name` and calls `area()`. Format the area to two decimals with `String.format("%.2f", ...)`.
3. `Circle` and `Rectangle` pass their name up to the `Shapes` constructor, with `"Circle"` and `"Rectangle"`. You do that with the `super(...)` call, as the first line of their own constructors.
4. In `Main`, change the loop to print `shape.describe()` instead of `shape.area()`.

Things to keep in mind:
- `describe()` is written **once**, in `Shapes`, and both subclasses get it for free. An interface couldn't have given you that, because it can't hold a `name` field or a normal method (not without the `default` keyword).
- When `describe()` calls `area()`, Java runs the right version for each object, because `area()` is abstract.

Write it, run `mvn compile exec:java -Dexec.mainClass=com.sel_int_prep.Main` from inside `j02_abstract_classes`, and tell me what you see or where you got stuck.