# Selenium Test & Page Object Setup Guide

## Test Class Structure

```java
public class LoginTest {
    private WebDriver driver;

    @BeforeMethod 
    public void setUp(){
        driver = new ChromeDriver();
        driver.get("https://www.saucedemo.com");
    }

    @AfterMethod 
    public void tearDown(){
        if (driver != null) driver.quit();
    }

    @Test 
    public void testName(){
        LoginPage loginPage = new LoginPage(driver);
        ProductsPage productsPage = loginPage.loginAs("standard_user", "secret_sauce");
        assertThat(productsPage.isLoaded()).isTrue();
    }
}
```

**Key points:**
- Extend nothing (plain class, not `LoginPage`)
- Have a `WebDriver` field (not `WebElement`)
- `@BeforeMethod` / `@AfterMethod` run before/after *each* test

---

## BasePage (Parent Class for All Page Objects)

```java
public class BasePage {
    protected WebDriver driver;          // protected: subclasses need it
    protected WebDriverWait wait;        // protected: subclasses use it for waits

    public BasePage(WebDriver driver){   // public: test classes construct it
        this.driver = driver;
        this.wait = new WebDriverWait(driver, Duration.ofSeconds(10));
    }

    // protected: only page objects use these helper methods
    protected void click(By locator){
        wait.until(ExpectedConditions.elementToBeClickable(locator)).click();
    }

    protected void type(By locator, String input){
        WebElement element = wait.until(ExpectedConditions.visibilityOfElementLocated(locator));
        element.clear();
        element.sendKeys(input);
    }

    protected String getText(By locator){
        return wait.until(ExpectedConditions.visibilityOfElementLocated(locator)).getText();
    }
}
```

**Visibility Rules:**
- `protected driver, wait`: Subclasses need these to do anything
- `protected click(), type(), getText()`: Page objects call these; tests never do
- No `public` element locators (keeps the implementation hidden)

---

## Page Objects (e.g., LoginPage, ProductsPage)

### LoginPage
```java
public class LoginPage extends BasePage {
    // private: only this page object uses these
    private final By usernameField = By.id("user-name");
    private final By passwordField = By.id("password");
    private final By loginButton = By.id("login-button");
    private final By errorMessage = By.cssSelector("[data-test='error']");

    public LoginPage(WebDriver driver){  // public: tests construct it
        super(driver);
    }

    // public: tests call this
    public ProductsPage loginAs(String username, String password){
        type(usernameField, username);
        type(passwordField, password);
        click(loginButton);
        return new ProductsPage(driver);
    }

    // public: tests call this in error scenarios
    public String getErrorMessage(){
        return getText(errorMessage);
    }
}
```

### ProductsPage
```java
public class ProductsPage extends BasePage {
    private final By productsContainer = By.className("inventory_container");

    public ProductsPage(WebDriver driver){
        super(driver);
    }

    // public: tests call this to verify we landed on the right page
    public boolean isLoaded(){
        return wait.until(ExpectedConditions.visibilityOfElementLocated(productsContainer)) != null;
    }

    // public: test methods call this to interact with the page
    public void addItemToCart(String itemName){
        By addButton = By.xpath("//div[contains(text(), '" + itemName + "')]//..//button[contains(text(), 'Add')]");
        click(addButton);
    }
}
```

**Visibility Rules:**
- `private final By locator`: Locators stay internal—tests never know they exist
- `public <ReturnType> methodName()`: Only public methods are part of the page's contract
- Return a **different page object** when navigating (e.g., `loginAs()` returns `ProductsPage`)
- Return **self** for chaining on the same page (e.g., `addItemToCart()` could return `this`)

---

## Visibility Quick Reference

| Visibility | Who can use it? | When to use |
|---|---|---|
| `public` | Tests + other classes | Page object methods that tests call; constructors |
| `protected` | This class + subclasses | Helper methods like `click()`, `type()` in BasePage; fields subclasses need |
| `private` | This class only | Locators; internal helper methods tests never call |

**Why this matters:**
- `public loginAs()` = test sees a simple high-level action
- `protected click()` = base class reuses it, test never sees low-level details
- `private By usernameField` = implementation stays hidden; you can change the locator without breaking tests

---

## POM Best Practices

1. **One page class per page**: `LoginPage`, `ProductsPage`, etc.
2. **Locators are private finals**: Hide the selector details from tests
3. **Methods are public**: Only the test-facing actions
4. **Constructors take WebDriver**: Build the page object in tests, never hardcode URLs or init
5. **Return page objects from navigation methods**: Makes the test flow clear
6. **Use BasePage helper methods**: `click()`, `type()`, `getText()` include waits
7. **Wait, don't sleep**: `WebDriverWait` with `ExpectedConditions`, never `Thread.sleep()`

---

## Adding Maven Dependencies

**Versions used in this course:** Selenium `4.20.0`, TestNG `7.10.2`, AssertJ `3.25.3`, Surefire `3.2.5`. Use these unless the course changes them.

**Finding a dependency:**
1. Search for the artifact on [mvnrepository.com](https://mvnrepository.com) or [central.sonatype.com](https://central.sonatype.com). For Selenium, search `selenium-java`.
2. Check the publisher and group ID. Selenium's group ID starts with `org.seleniumhq`.
3. Pick the version above, open its **Maven** tab, and copy the `<dependency>` block.
4. Paste it into `pom.xml` inside `<dependencies>`. If that element doesn't exist, add it below `<properties>`.
5. Run `mvn compile`. `BUILD SUCCESS` means Maven downloaded the jar and the dependency works.

**Choosing `<scope>`:**
- `<scope>test</scope>`: the library is only needed by test code under `src/test/java`, and it isn't packaged into the build. Use it for TestNG and AssertJ.
- No scope (default `compile`): the library is needed by main code too. Selenium is in this group, because page objects may use it.

**Reading a dependency error:**
- `Could not find artifact ...`: the group ID, artifact ID, or version is wrong. Check the spelling against the search result.
- Version errors or conflicts: confirm the version number matches the course version.
- `mvn compile` passes but the IDE shows errors: this is usually the editor, not the code. Run `Java: Clean Java Language Server Workspace` or `Maven: Reload Projects`.

**Reference:** `s04_testng/pom.xml` has the working versions and scopes.

---

## Running Tests
- `mvn test` — Run all tests
- `mvn -Dtest=LoginTest test` — Run only `LoginTest.java`
- `mvn -Dtest=LoginTest#testMethodName test` — Run one test method

---

## App Details
- **URL**: https://www.saucedemo.com
- **Success credentials**: `standard_user` / `secret_sauce`
- **Locked-out user**: `locked_out_user` / `secret_sauce`
