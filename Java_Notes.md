**An interface is a promise.** It lists method names, and any class that `implements` it promises to provide those methods. The interface says *what* can be done, and each class decides *how*. In Selenium, `WebDriver` promises `get()`, `quit()` and so on, and `ChromeDriver` provides the real code for Chrome.

**Why it's useful:** code can work with the interface and not care which class is behind it. `WebDriver driver = new ChromeDriver();` is the example you've already used.


First, a short explanation. An abstract class is a **partly finished class**. It can hold real fields and real methods that subclasses share, and it can leave some methods `abstract`, meaning "each subclass must write its own version". You can't create one directly with `new`, because it's incomplete by design.

