Here is the whole interactions block as **one challenge**. Put everything in `InteractionsTest.java`, which has the same `setUp()` and `tearDown()` as before. Make `setUp()` open `https://www.saucedemo.com`, and have the tests that need another site call `driver.get(...)` themselves.

**New API you need (everything else you already know):**

- `getAttribute("value")` reads what's typed in a field. `getText()` reads visible text, and it's empty for inputs.

- `isDisplayed()`, `isEnabled()` and `isSelected()` return a boolean. `isSelected` is for checkboxes, radios and options.

- **`Select`** wraps a `<select>` dropdown: `new Select(element)`, then `selectByVisibleText("Option 1")`, `selectByValue(...)`, `selectByIndex(...)` and `getFirstSelectedOption()`. It's in `org.openqa.selenium.support.ui.Select`. It only works on real `<select>` elements. Custom dropdowns built from divs need normal clicks.

- **Alerts:** `Alert alert = driver.switchTo().alert();` gives you `getText()`, `accept()` (OK), `dismiss()` (Cancel) and `sendKeys("...")` (prompts). Selenium can't see an alert as a page element, so you have to switch to it.

- **Iframes:** an iframe is a separate document inside the page, and elements inside it are invisible to `findElement` until you switch with `driver.switchTo().frame("name-or-id")`. Use `switchTo().parentFrame()` to go up one level and `switchTo().defaultContent()` to return to the main page. Forgetting to switch is one of the main causes of `NoSuchElementException` on a page that looks right.

- **File upload:** there's no dialog to handle. Call `sendKeys(absolutePath)` on the `<input type="file">`. Get the path with `new File("pom.xml").getAbsolutePath()` (`java.io.File`).

It passes now, so the flakiness is real. The run took **37 seconds**, which is very slow for a single upload test, and that points to a slow server that sometimes didn't answer in time. I can't prove that was the cause of the earlier failure, but the symptom fits: "element not there yet" followed by "there on retry". The URL line told us nothing, because the upload form posts back to `/upload`.

That pattern is **flakiness**, and it leads straight into waits.

**Waits: the explanation**

Selenium sends commands immediately. `findElement` checks the page **once** and fails if the element isn't there. Pages load, run JavaScript and fetch data over time, so a test that is faster than the page is a flaky test.

**The three kinds of wait:**
- **Implicit:** `driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(10))`. It is set once, and every `findElement` then retries for up to 10s before throwing. It only covers "element exists in the DOM", it applies globally, and it hides slowness. **Never mix it with explicit waits.** The two timeouts can combine unpredictably, so tests wait much longer than expected.
- **Explicit:** `new WebDriverWait(driver, Duration.ofSeconds(10))`, then `.until(condition)`. It waits for one specific condition on one specific element, and it returns as soon as the condition is true. This is the default you should use.
- **Fluent:** an explicit wait you configure further: `new FluentWait<>(driver).withTimeout(...).pollingEvery(...).ignoring(NoSuchElementException.class)`. You use it when you need a custom polling interval or exceptions to ignore. `WebDriverWait` is a `FluentWait` with sensible defaults (500ms polling).

**`ExpectedConditions`** (`org.openqa.selenium.support.ui.ExpectedConditions`) are the conditions you pass to `until(...)`:
- `visibilityOfElementLocated(By)`: in the DOM **and** visible. It returns the `WebElement`.
- `presenceOfElementLocated(By)`: in the DOM, visible or not.
- `elementToBeClickable(By)`: visible and enabled. Use it before clicking.
- `invisibilityOfElementLocated(By)`: for loading spinners.
- `textToBePresentInElementLocated(By, String)`, `urlContains(String)`, `alertIsPresent()`, `stalenessOf(WebElement)`.

If the time runs out, `until` throws `TimeoutException`. It can be an interview talking point that this is a different exception from `NoSuchElementException`.

