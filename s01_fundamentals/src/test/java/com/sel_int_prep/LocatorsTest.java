package com.sel_int_prep;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.devtools.DevTools;
import org.openqa.selenium.remote.http.UrlTemplate.Match;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.openqa.selenium.support.locators.RelativeLocator.with;

import java.util.List;

import javax.swing.text.html.HTML;
import javax.xml.xpath.XPath;

public class LocatorsTest {

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

    @Test
    public void findUsernameById() {
        WebElement username = driver.findElement(By.id("user-name"));
        assertThat(username.isDisplayed()).isTrue();
    }

    @Test
    public void findPasswordByName() {
        WebElement password = driver.findElement(By.name("password"));
        assertThat(password.isDisplayed()).isTrue();
    }

    @Test
    public void findLoginButtonByClassName() {
        // By.className(null) can only accept one classname at a time
        // WebElement button = driver.findElement(By.className("submit-button"));
        WebElement button = driver.findElement(By.cssSelector(".submit-button.btn_action"));
        assertThat(button.isDisplayed()).isTrue();
    }

    @Test
    public void findLinkByLinkText() {
        driver.get("https://the-internet.herokuapp.com");
        WebElement link = driver.findElement(By.linkText("A/B Testing"));
        //  (By.partialLinkText("A/") matches part of the text.)
        assertThat(link.getAttribute("href")).endsWith("/abtest");
    }


    @Test
    public void findHeadingByTagName() {
        driver.get("https://the-internet.herokuapp.com");
        WebElement heading = driver.findElement(By.tagName("h1"));
        assertThat(heading.getText()).isEqualTo("Welcome to the-internet");
    }
    
    // findElements (plural) to collect all matches, for example every link on the page.
    // findElements(...) returns a List<WebElement>. It returns an empty list if nothing matches and does not throw NoSuchElementException. Interviewers like asking about that difference.

    @Test
    public void countLinksOnHomePage() {
        driver.get("https://the-internet.herokuapp.com");
        List<WebElement> links = driver.findElements(By.tagName("a"));
        assertThat(links).hasSizeGreaterThan(10);

        // The second half shows that a missing element with findElements gives an empty list and no exception. Testers often use this to check "this element is not on the page".
        List<WebElement> none = driver.findElements(By.id("does-not-exist"));
        assertThat(none).isEmpty();
    }

    // **Step 11: CSS selectors, the main locator.**

    // In real projects, CSS is the go-to when there's no good `id` or `name`. It's faster and more readable than XPath. You've already used two pieces of the syntax:

    // | Selector | Meaning | Example |
    // |---|---|---|
    // | `#value` | `id` equals value | `#user-name` |
    // | `.value` | has this class | `.submit-button` |
    // | `tag` | tag name | `input` |
    // | `tag[attr='value']` | attribute equals value | `input[name='password']` |
    // | `tag.class` | tag **and** class | `input.submit-button` |
    // | `A B` (with a space) | `B` anywhere inside `A` | `form input` |

    // The one that matters most is the **attribute selector**, `[attr='value']`. It works on any attribute, including custom ones like saucedemo's `data-test`, which is why you saw `data-test="username"`. Many teams add `data-test` attributes specifically for automation, so `[data-test='username']` is a stable locator.

    // **Your task, in the browser only (no code yet):**
    // 1. Go to `https://www.saucedemo.com` and open DevTools, then the **Elements** tab.
    // 2. Press `Cmd+F` inside that tab to open its search box.
    // 3. Type `[data-test='username']` and press Enter.

    // DevTools reports a count like "1 of 1" and highlights the match. Tell me what count you get. Then try `input` and tell me that count too.

    // It shows why a bare tag name is a weak locator. findElement(By.tagName("input")) would silently give you the first one, and you'd only notice if the page order changed.

    @Test
    public void findByCssAttribute() {
        WebElement username = driver.findElement(By.cssSelector("[data-test='username']"));
        WebElement password = driver.findElement(By.cssSelector("input[name='password']"));
        assertThat(username.getAttribute("placeholder")).isEqualTo("Username");
        assertThat(password.getAttribute("placeholder")).isEqualTo("Password");
    }

    //     **Step 13: partial attribute matching.**

    // Sometimes an attribute value is partly dynamic, such as `id="button-4821"`, where the number changes on every load. CSS has three operators for that:

    // | Selector | Meaning | Example |
    // |---|---|---|
    // | `[attr^='x']` | value **starts with** x | `[id^='button-']` |
    // | `[attr$='x']` | value **ends with** x | `[href$='.pdf']` |
    // | `[attr*='x']` | value **contains** x | `[class*='error']` |

    // Memory aid: `^` is the start, `$` is the end, `*` is "anywhere".

    // **Your task, in DevTools only:** on `https://www.saucedemo.com`, press `Cmd+F` in the Elements tab and try each selector in turn:

    // 1. `[data-test^='user']` 1 of 1
    // 2. `[data-test$='button']` 1 of 1
    // 3. `[data-test*='ass']` 1 fo 2

    // Tell me what each one highlights, or the "x of y" count. You can work out the answers from the attribute values you've already seen. For example, the username field has `data-test="username"`.

    // *='ass' gives 2. One is the password field (password). To find the other, press Enter in the search box to jump to match 2 and read its data-test. My guess is a login-password element in the accepted-passwords block lower on the page, but check it yourself.

    // The count of 2 is the point. A looser selector matches more than you meant. 
    
    // !!!! --> Before relying on a CSS selector, check the match count in DevTools. For a locator you want exactly "1 of 1". <-- !!!!


    // **Step 14: relationships between elements.**

    // Two more pieces of syntax cover most real cases:

    // | Selector | Meaning |
    // |---|---|
    // | `A B` (space) | `B` anywhere **inside** `A` (descendant) |
    // | `A > B` | `B` as a **direct child** of `A` |

    // For example, `form input` finds every input inside a `<form>`, however deeply nested. `form > input` finds only inputs that are direct children of the form.

    // **Your task, in DevTools:** on saucedemo, find out what `*='ass'` matched second, then try these two selectors and tell me the count for each:

    // 1. `form input` 3
    // 2. `form > input` 1

    // If the counts differ, look at the HTML structure and tell me why.

    @Test
    public void findByDirectChild() {
        WebElement button = driver.findElement(By.cssSelector("form > input"));
        assertThat(button.getAttribute("value")).isEqualTo("Login");
    }


    // **Step 16: XPath, when CSS isn't enough.**

    // XPath is a query language for the HTML tree. Its syntax is more verbose than CSS, but it can do two things CSS can't:
    // 1. **Match by visible text.** This is the big one.
    // 2. **Move up** to a parent or ancestor element.

    // Basic syntax:

    // | XPath | Meaning |
    // |---|---|
    // | `//input` | any `<input>` anywhere in the page |
    // | `//input[@name='password']` | input whose `name` attribute is `password` |
    // | `//a[text()='A/B Testing']` | link whose text is exactly that |
    // | `//a[contains(text(),'A/B')]` | link whose text contains `A/B` |

    // Notes:
    // - `//` means "anywhere in the document".
    // - `@` marks an attribute name. In CSS you'd write `[name='password']`, and in XPath it's `[@name='password']`.
    // - `text()` is the element's own text.
    // - The `[...]` square brackets are a filter condition, as in CSS.

    // **Your task, in DevTools only:** open `https://the-internet.herokuapp.com`. In the Elements tab, press `Cmd+F` and try these in turn:

    // 1. `//a[text()='Checkboxes']`
    // 2. `//a[contains(text(),'Check')]`
    // 3. `//h1`

    // Tell me the count for each. For 2, also say what the matches are, which you can see by pressing Enter to step through them.

    @Test
    public void findByXPathText() {
        driver.get("https://the-internet.herokuapp.com");
        WebElement link = driver.findElement(By.xpath("//a[text()='Checkboxes']"));
        link.click();
        assertThat(driver.getCurrentUrl()).endsWith("/checkboxes");
    }

    //     **Step 18: XPath going up.**

    // CSS can only move *down* the tree (descendants and children). XPath can also move **up**. That's the second thing CSS can't do, and it's useful when the element you can identify (say, by its text) isn't the one you want to act on.

    // - `..` means "the parent of the current element".

    // On the-internet home page, each link sits in a list item: `<li><a href='/checkboxes'>Checkboxes</a></li>`.

    // **Your task, in DevTools:** on `https://the-internet.herokuapp.com`, press `Cmd+F` and try these:

    // 1. `//a[text()='Checkboxes']/..` li <a href="/checkboxes">Checkboxes</a>
    // 2. `//a[text()='Checkboxes']/../following-sibling::li[1]` li <a href="/context_menu">Context Menu</a>

    // For each, tell me which element it highlights (its tag, and what it contains). Hints:
    // - 1 starts at the link and steps up one level.
    // - `following-sibling::li[1]` means "the first `<li>` after this one at the same level".

    // **XPath indexes start at 1, not 0.** That differs from Java lists and arrays, which start at 0. `[1]` means "the first one in the set".

    // **What `following-sibling::li` returns.** Starting from the Checkboxes `<li>`, it returns every `<li>` that comes after it at the same level, in page order:

    // ```
    // Checkboxes          ← where we start
    // Context Menu        ← following-sibling::li[1]
    // Digest Authentication  ← following-sibling::li[2]
    // Disappearing Elements  ← following-sibling::li[3]
    // ...
    // ```

    // `[1]` picks the closest one after the start, which is Context Menu. Without `[1]`, the expression matches all of them.

    // Two related notes:
    // - For `following-sibling` the numbering counts **forward** from the starting element. So `[1]` is always the nearest, `[2]` the next, and so on.
    // - If you write `(//li)[1]` with parentheses, it means "the first `<li>` in the whole page". Without the parentheses, `//li[1]` means "every `<li>` that is the first `<li>` among its own siblings", which can match several elements. This is a classic XPath gotcha, so it's worth remembering for interviews.


    // **Step 19: relative locators.** Selenium 4 added a way to get the same "next to" idea without writing XPath. 

    // **Relative locators (Selenium 4).**
    
    // They find an element by its position relative to another element on the screen, using the words *above*, *below*, *toLeftOf*, *toRightOf* and *near*. They compare where elements are drawn on the page, which is not the same as where they sit in the HTML tree.
    
    // ```java
    
    // WebElement password = driver.findElement(By.id("password"));
    // WebElement username = driver.findElement(with(By.tagName("input")).above(password));
    // ```
    
    // Read it as: "find an `<input>` that is above the password field."
    
    // - `with(By.tagName("input"))` is the starting point. It names the kind of element you want, using any normal `By`.
    // - `.above(password)` narrows the search using another element you already found. You can pass a `WebElement` or a `By`.
    // - It can be chained: `.above(x).toRightOf(y)`.
    // - `import static ...RelativeLocator.with` lets you write `with(...)` directly, like the AssertJ static import.
    
    // **When it helps:** the element has no good id or attribute, but it sits next to something stable. A typical case is a label with an input beside it.
    
    // **Caveats (interview material):**
    // - Layout dependent. A responsive layout or a different window size can change what's "above" or "near".
    // - `near` means within 50 pixels by default.
    // - Plain CSS or XPath is usually more stable. Use relative locators when they make the intent clearer, not as a default.
    
    // **Step 19 (your task):** add this test to `LocatorsTest`. It uses saucedemo's password field and finds the field above it:
    
    @Test
    public void findByRelativeLocator() {
        WebElement password = driver.findElement(By.id("password"));
        WebElement above = driver.findElement(with(By.tagName("input")).above(password));
        assertThat(above.getAttribute("placeholder")).isEqualTo("Username");
    }
    
    //  Locators are covered. Next is deciding which one to use.

    // **Step 20: locator priority and fragile vs reliable.**

    // The preferred order, which is also a good spoken interview answer:

    // 1. **`id`**: unique by HTML rules, fast and stable. Skip it if it looks auto-generated (`id="input-8f3a2"`).
    // 2. **`data-test` / `data-testid` via CSS** (`[data-test='username']`): added specifically for tests, so developers won't change it for styling reasons.
    // 3. **`name`**: stable on form fields.
    // 4. **CSS selector** on meaningful attributes or simple structure.
    // 5. **XPath**: for what CSS can't do (match by text, go up to a parent).
    // 6. **Relative locators / `linkText`**: situational. `linkText` breaks if the wording or translation changes.

    // **Fragile locators to avoid:**
    // - Absolute XPath like `/html/body/div[2]/div/form/input[1]`. It breaks when any element above it changes.
    // - Index-based picks like `//input[3]`, or taking the 3rd of a `findElements` list.
    // - Styling classes (`.btn-blue`, `.mt-4`). A redesign changes them.
    // - Long CSS chains like `div > div > div > span`.
    // - Auto-generated ids or classes.

    // **The rule of thumb:** a locator should describe *what the element is for*, not *where it happens to sit on the page today*.

    // **Your task:** the Login button on saucedemo has these attributes: `id="login-button"`, `data-test="login-button"`, `class="submit-button btn_action"`, `name="login-button"`, `value="Login"`. Rank your top two locators for it, with one sentence of reasoning each, and name one you would avoid.   
}