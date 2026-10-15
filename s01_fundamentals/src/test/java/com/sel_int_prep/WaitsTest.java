package com.sel_int_prep;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.FluentWait;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.openqa.selenium.TimeoutException;
import org.openqa.selenium.NoSuchElementException;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import java.io.File;
import java.time.Duration;

public class WaitsTest {
    
    private WebDriver driver;

    @BeforeMethod
    public void setUp() {
        driver = new ChromeDriver();
        // driver.get("https://www.saucedemo.com");
    }

    @AfterMethod
    public void tearDown() {
        driver.quit();
    }

    // **Challenge: `WaitsTest.java`** (new file, `setUp()` and `tearDown()` as before, no `driver.get` in `setUp` this time). The site has two pages that load content after you click Start:

    // 1. **See it fail.** On `https://the-internet.herokuapp.com/dynamic_loading/1`, click the button inside `#start` (`#start button`), and immediately read `#finish` with `getText()`. Assert it is `Hello World!`. It will fail with an empty string, because on page 1 the element is **in the DOM but hidden**. Do the same on `/dynamic_loading/2`, where the element **doesn't exist yet**, and compare the exception. (Comment out or delete these two failing tests once you've seen both.)

    // @Test 
    // public void seeItFail(){
    //     // driver.get("https://the-internet.herokuapp.com/dynamic_loading/1");
    //     driver.get("https://the-internet.herokuapp.com/dynamic_loading/2");
    //     driver.findElement(By.cssSelector("#start button")).click();
    //     assertThat(driver.findElement(By.id("finish")).getText()).isEqualTo("Hello World!");
    // }

    // 2. **Fix both with explicit waits.** Create a `WebDriverWait` with a 10s `Duration` (`java.time.Duration`) and call `until(ExpectedConditions.visibilityOfElementLocated(By.id("finish")))`. It returns the element, so you can chain `.getText()` straight onto it. Ask yourself whether `presenceOfElementLocated` would work on page 1, and why not.

    @Test 
    public void seeItWork(){
        
        driver.get("https://the-internet.herokuapp.com/dynamic_loading/1");

        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));

        wait.until(ExpectedConditions.elementToBeClickable(By.cssSelector("#start button"))).click();

        WebElement finish = wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("finish")));
        
        assertThat(finish.getText()).isEqualTo("Hello World!");
    }

    // Open https://the-internet.herokuapp.com/dynamic_loading/1 with driver.get(...). Don't click Start. Without the click, #finish stays hidden forever. Create a new WebDriverWait with a 2-second Duration, as in test 2 but with Duration.ofSeconds(2). Give it a different variable name, such as shortWait. Wrap the failing call in a lambda, which is what assertThatThrownBy expects. A lambda here just means "run this code later, inside the assertion": assertThatThrownBy(() -> shortWait.until(ExpectedConditions.visibilityOfElementLocated(By.id("finish")))) Chain .isInstanceOf(TimeoutException.class) onto that. Imports you need: static org.assertj.core.api.Assertions.assertThatThrownBy, which goes next to your existing assertThat static import org.openqa.selenium.TimeoutException. Be careful here: there is also a java.util.concurrent.TimeoutException, and your IDE may offer that one. It's the wrong one. 

    @Test 
    public void timeoutException(){
        
        driver.get("https://the-internet.herokuapp.com/dynamic_loading/1");
        
        WebDriverWait shortWait = new WebDriverWait(driver, Duration.ofSeconds(2));
        
        assertThatThrownBy(() -> shortWait.until(ExpectedConditions.visibilityOfElementLocated(By.id("finish")))).isInstanceOf(TimeoutException.class).hasMessageContaining("finish");
    }
    
    // 4. **#4: Fluent wait.** A fluent wait is the same idea as `WebDriverWait`, but you set the polling interval and the exceptions to ignore yourself.
    // 1. Open `https://the-internet.herokuapp.com/dynamic_loading/2` and click the Start button, as in your `seeItWork` test. On this page `#finish` isn't in the DOM at all until the loading ends, which is why we need to ignore `NoSuchElementException`.
    // 2. Build the wait. The type is `FluentWait<WebDriver>`. Create it with `new FluentWait<>(driver)`, passing your driver. Then chain these configuration methods onto it:
    // - `.withTimeout(Duration.ofSeconds(10))` is the maximum total wait.
    // - `.pollingEvery(Duration.ofMillis(250))` is how often it re-checks.
    // - `.ignoring(NoSuchElementException.class)` means that exception is swallowed and the check retried, and doesn't fail the wait.
   
    // Store the result in a variable, for example `FluentWait<WebDriver> fluentWait`.
    // 3. Use it with `fluentWait.until(...)`. `until` takes a function that receives the driver and returns something. It keeps retrying while the function throws an ignored exception or returns `null`/`false`, and stops once it returns a real value. For us: `d -> d.findElement(By.id("finish"))`. `d` is the driver, and the part after `->` is the code to run on each poll.
    // 4. `until` returns the `WebElement`, so keep it in a variable. Then assert `getText()` is `"Hello World!"`.

    // **Things to watch:**
    // - Import `org.openqa.selenium.NoSuchElementException`, not `java.util.NoSuchElementException`. As with `TimeoutException`, the IDE may suggest the wrong one.
    // - `FluentWait` is in `org.openqa.selenium.support.ui`.
    // - Because `NoSuchElementException` is ignored, `findElement` failing is part of the normal wait here, not an error.

    // **Why this works on page 2 and what to say in an interview:** `WebDriverWait` already ignores `NoSuchElementException` by default, so a plain `until(presenceOfElementLocated(...))` would work here too. `FluentWait` matters when you need a different polling rate or a different set of ignored exceptions, such as `StaleElementReferenceException`.

    @Test 
    public void fluentWait(){
        
        driver.get("https://the-internet.herokuapp.com/dynamic_loading/2");

        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));

        wait.until(ExpectedConditions.elementToBeClickable(By.cssSelector("#start button"))).click();

        FluentWait<WebDriver> fluentWait = new FluentWait<>(driver)
            .withTimeout(Duration.ofSeconds(10))
            .pollingEvery(Duration.ofMillis(250))
            .ignoring(NoSuchElementException.class);

        WebElement finish = fluentWait.until(d -> d.findElement(By.id("finish")));
        
        assertThat(finish.getText()).isEqualTo("Hello World!");
    }

    // 5. **Fix the flaky upload.** In `InteractionsTest.uploadsWork`, wait for `#uploaded-files` to be visible before reading its text. Also remove the two print lines.

    @Test 
    public void uploadsWork2(){
        
        driver.get("https://the-internet.herokuapp.com/upload");
        
        String path = new File("pom.xml").getAbsolutePath();
        
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));

        wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("file-upload"))).sendKeys(path);
        
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("file-submit"))).click();

        WebElement uploadedFiles = wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("uploaded-files")));
        
        assertThat(uploadedFiles.getText()).isEqualTo("pom.xml");
    }

    // Run with `mvn test -Dtest=WaitsTest`. As before, paste only failures and errors.
}
