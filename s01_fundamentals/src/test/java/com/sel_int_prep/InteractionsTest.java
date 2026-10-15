package com.sel_int_prep;

import static org.assertj.core.api.Assertions.assertThat;

import java.io.File;
import java.util.List;

import org.openqa.selenium.Alert;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.Select;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

public class InteractionsTest {
    
    private WebDriver driver;

    @BeforeMethod
    public void setUp() {
        driver = new ChromeDriver();
        driver.get("https://www.saucedemo.com");
    }

    @AfterMethod
    public void tearDown() {
        driver.quit();
    }

    // **1. Login, happy path.** Fix `"standard-user"` to `"standard_user"`. For the assertion, call `driver.getCurrentUrl()`, which returns a `String`. Pass it to `assertThat(...)` (static import from AssertJ) and chain `.endsWith("/inventory.html")`.

    @Test 
    public void loginWithValidUser(){

        WebElement usernameField = driver.findElement(By.id("user-name"));
        usernameField.clear();
        usernameField.sendKeys("standard_user");

        WebElement passwordField = driver.findElement(By.id("password"));
        passwordField.clear();
        passwordField.sendKeys("secret_sauce");

        driver.findElement(By.id("login-button")).click();

        assertThat(driver.getCurrentUrl()).endsWith("/inventory.html");
    }



    // **2. Login, error path.**
    // - Click `#login-button` without typing anything.
    // - Find the error with `By.cssSelector("[data-test='error']")` and keep it in a `WebElement`.
    // - Two assertions: `assertThat(error.isDisplayed()).isTrue()` and `assertThat(error.getText()).contains("Username is required")`.
    // - `getText()` works here because the error is a normal element with visible text.

    @Test 
    public void failedLoginRaisesError(){
        driver.findElement(By.id("login-button")).click();
        WebElement errorMessage = driver.findElement(By.cssSelector("[data-test='error'"));
        assertThat(errorMessage.isDisplayed()).isTrue();
        assertThat(errorMessage.getText()).contains("Username is required");
    }

    // **3. `clear()`.**
    // - Find the username field and call `sendKeys("abc")` on it.
    // - Read it back with `getAttribute("value")`, which returns the typed text. Don't use `getText()` here, because it's empty for input fields. Assert `.isEqualTo("abc")`.
    // - Call `clear()` on the same `WebElement` variable and assert again. The AssertJ method for a `String` that should be empty is `.isEmpty()`.

    @Test 
    public void clearingFieldWorks(){

        WebElement usernameField = driver.findElement(By.id("user-name"));
        usernameField.sendKeys("abc");
        assertThat(usernameField.getAttribute("value")).isEqualTo("abc");
        usernameField.clear();
        assertThat(usernameField.getAttribute("value")).isEmpty();
    }

    // **4. Checkboxes.**
    // - Call `driver.get("https://the-internet.herokuapp.com/checkboxes")`.
    // - `driver.findElements(By.cssSelector("input[type='checkbox']"))` returns a `List<WebElement>`. You need `import java.util.List`.
    // - Use `list.get(0)` for the first box and `list.get(1)` for the second. Java lists are 0-indexed. (XPath wasn't.)
    // - The state check is `isSelected()`, which returns a boolean. Use `assertThat(...).isTrue()` or `.isFalse()`.
    // - Click the first box with `click()`, then call `isSelected()` again and assert it is now true.

    @Test 
    public void canClickCheckboxes(){
        driver.get("https://the-internet.herokuapp.com/checkboxes");
        List<WebElement> checkboxes = driver.findElements(By.cssSelector("input[type='checkbox"));
        assertThat(checkboxes.get(0).isSelected()).isFalse();
        assertThat(checkboxes.get(1).isSelected()).isTrue();
        checkboxes.get(0).click();
        assertThat(checkboxes.get(0).isSelected()).isTrue();
    }


    // **5. Dropdown.**
    // - Go to `/dropdown` and find the `<select>` with `By.id("dropdown")`.
    // - Wrap that `WebElement` in `new Select(element)`, from `org.openqa.selenium.support.ui.Select`. Then call all the methods on the `Select` object, not on the `WebElement`.
    // - Call `selectByVisibleText("Option 2")` on it.
    // - `getFirstSelectedOption()` returns a `WebElement`. Call `getText()` on that and assert it `.isEqualTo("Option 2")`.

    @Test 
    public void dropDownsWork(){
        driver.get("https://the-internet.herokuapp.com/dropdown");
        WebElement element = driver.findElement(By.id("dropdown"));
        Select dropdown = new Select(element);
        dropdown.selectByVisibleText("Option 2");
        WebElement selectedOption = dropdown.getFirstSelectedOption();
        assertThat(selectedOption.getText()).isEqualTo("Option 2");
    }

    // **6. Alert.**
    // - Go to `/javascript_alerts`. The buttons have no `id`, so locate with `By.xpath("//button[text()='Click for JS Confirm']")` and click it.
    // - Get the alert with `driver.switchTo().alert()`, which returns an `Alert` object (`org.openqa.selenium.Alert`).
    // - On it: `getText()` returns the message, which you assert equals `"I am a JS Confirm"`. `dismiss()` clicks Cancel. (`accept()` would click OK.)
    // - The result text is in the element with `id="result"`, so find it with `By.id("result")`, call `getText()`, and assert `.contains("You clicked: Cancel")`.
    // - Don't call anything else on the page before you handle the alert, or Selenium throws an `UnhandledAlertException`.

    @Test 
    public void testAlertsWork(){
        driver.get("https://the-internet.herokuapp.com/javascript_alerts");
        driver.findElement(By.xpath("//button[text()='Click for JS Confirm']")).click();
        Alert alert = driver.switchTo().alert();
        assertThat(alert.getText()).isEqualTo("I am a JS Confirm");
        alert.dismiss();
        WebElement result = driver.findElement(By.id("result"));
        assertThat(result.getText()).contains("You clicked: Cancel");
    }

    // **7. Iframe.**
    // - Go to `/nested_frames`.
    // - `driver.switchTo().frame("frame-top")` moves you into the top frame. `switchTo()` returns a locator for switching, and `frame(...)` takes the frame's name or id as a `String`.
    // - Then, from inside the top frame, call `driver.switchTo().frame("frame-left")`.
    // - Find the content with `By.tagName("body")`, read it with `getText()`, and assert `.isEqualTo("LEFT")`.
    // - Finish with `driver.switchTo().defaultContent()`, which returns you to the main page.
    // - If you skip the first switch, the second one fails with `NoSuchFrameException`. Try that on purpose once if you want to see the error.

    @Test 
    public void iframesWork(){
        driver.get("https://the-internet.herokuapp.com/nested_frames");
        driver.switchTo().frame("frame-top");
        driver.switchTo().frame("frame-left");
        WebElement content = driver.findElement(By.tagName("body"));
        assertThat(content.getText()).isEqualTo("LEFT");
        driver.switchTo().defaultContent();
    }

    // **8. Upload.**
    // - Go to `/upload`.
    // - Build the path with `new File("pom.xml").getAbsolutePath()`, which returns a `String`. `File` is `java.io.File`.
    // - Find the `<input type="file">` with `By.id("file-upload")` and call `sendKeys(path)` on it. Don't click it, because that would open the native dialog, which Selenium can't control.
    // - Click the submit button with `By.id("file-submit")`.
    // - Read `By.id("uploaded-files")` with `getText()` and assert `.isEqualTo("pom.xml")`.

    @Test 
    public void uploadsWork(){
        driver.get("https://the-internet.herokuapp.com/upload");
        String path = new File("pom.xml").getAbsolutePath();//what pom is this getting? the one for the dir I'm currently in?
        driver.findElement(By.id("file-upload")).sendKeys(path);
        driver.findElement(By.id("file-submit")).click();
        System.out.println("URL: " + driver.getCurrentUrl());
        System.out.println("TITLE: " + driver.getTitle());
        assertThat(driver.findElement(By.id("uploaded-files")).getText()).isEqualTo("pom.xml");
    }
    // Run `mvn test -Dtest=InteractionsTest`, and paste only failing tests with their errors.
}
