# Selenium Interview Prep: Core Concepts & APIs

## Element Inspection & State

| Method | Returns | Use When |
|--------|---------|----------|
| `getText()` | String | Reading visible text on the page |
| `getAttribute("value")` | String | Reading what's typed in an input field (getText() returns empty for inputs) |
| `isDisplayed()` | boolean | Checking if an element is visible |
| `isEnabled()` | boolean | Checking if a button/input is clickable |
| `isSelected()` | boolean | Checking if a checkbox, radio, or option is selected |

---

## Dropdowns (Select Elements)

Use `Select` class from `org.openqa.selenium.support.ui.Select` for **real `<select>` HTML elements only**. Custom dropdowns built from divs need normal clicks.

```java
WebElement selectElement = driver.findElement(By.id("dropdown"));
Select dropdown = new Select(selectElement);

dropdown.selectByVisibleText("Option 1");
dropdown.selectByValue("value123");
dropdown.selectByIndex(2);

WebElement selectedOption = dropdown.getFirstSelectedOption();
String selectedText = selectedOption.getText();
```

---

## Alerts

Alerts are separate from the DOM—you can't find them with `findElement()`. Switch to them first:

```java
Alert alert = driver.switchTo().alert();

String message = alert.getText();
alert.accept();      // Click OK
alert.dismiss();     // Click Cancel
alert.sendKeys("text");  // For prompts
```

---

## Iframes

Iframes are separate documents inside the page. Elements inside are **invisible to `findElement()` until you switch**:

```java
// Switch into an iframe
driver.switchTo().frame("iframe-name-or-id");
driver.switchTo().frame(0);  // Also works with index

// Now find elements inside the iframe
WebElement element = driver.findElement(By.id("inside-iframe"));

// Switch back out
driver.switchTo().parentFrame();  // Up one level
driver.switchTo().defaultContent();  // To the main page
```

**Common mistake:** Forgetting to switch causes `NoSuchElementException` even when you can see the element in the browser.

---

## File Upload

No dialog to handle—use `sendKeys()` directly on `<input type="file">`:

```java
WebElement fileInput = driver.findElement(By.id("file-upload"));
String absolutePath = new File("path/to/file.txt").getAbsolutePath();
fileInput.sendKeys(absolutePath);
```

---

## Waits: The Foundation of Reliable Tests

### Why Waits Matter

Selenium sends commands immediately. `findElement()` checks the page **once** and fails if the element isn't there. Since pages load, run JavaScript, and fetch data asynchronously, a test that is faster than the page is a **flaky test**.

### The Three Kinds of Wait

| Kind | Setup | Use Case | Gotcha |
|------|-------|----------|--------|
| **Implicit** | `driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(10))` | Set once; applies globally to all `findElement()` calls | ⚠️ Never mix with explicit waits—they combine unpredictably and cause very long timeouts |
| **Explicit** | `new WebDriverWait(driver, Duration.ofSeconds(10)).until(condition)` | Wait for one specific condition on one specific element; returns as soon as true | Default; use this in most tests (polls every 500ms) |
| **Fluent** | `new FluentWait<>(driver).withTimeout(...).pollingEvery(...).ignoring(...)` | Custom polling interval or exceptions to ignore | Advanced; `WebDriverWait` is a `FluentWait` with sensible defaults |

**Best practice:** Use explicit waits in your page object helper methods (like `BasePage.click()`, `BasePage.type()`). Never use implicit waits.

### ExpectedConditions

Common conditions to pass to `.until()`:

```java
wait.until(ExpectedConditions.visibilityOfElementLocated(By locator));
// ↑ Element is in DOM and visible; returns the WebElement

wait.until(ExpectedConditions.presenceOfElementLocated(By locator));
// ↑ Element is in DOM (may not be visible)

wait.until(ExpectedConditions.elementToBeClickable(By locator));
// ↑ Element is visible and enabled; use before clicking

wait.until(ExpectedConditions.invisibilityOfElementLocated(By locator));
// ↑ For waiting on spinners/loaders to disappear

wait.until(ExpectedConditions.textToBePresentInElementLocated(By locator, String text));
wait.until(ExpectedConditions.urlContains(String substring));
wait.until(ExpectedConditions.alertIsPresent());
wait.until(ExpectedConditions.stalenessOf(WebElement element));
```

If the timeout expires, `.until()` throws `TimeoutException` (different from `NoSuchElementException`—interview talking point).

### Practical Example (in BasePage)

```java
protected void click(By locator){
    wait.until(ExpectedConditions.elementToBeClickable(locator)).click();
}

protected void type(By locator, String input){
    WebElement element = wait.until(ExpectedConditions.visibilityOfElementLocated(locator));
    element.clear();
    element.sendKeys(input);
}
```

This way, every interaction waits before acting—tests are reliable by default.

