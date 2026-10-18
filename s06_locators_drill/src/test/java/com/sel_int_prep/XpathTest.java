package com.sel_int_prep;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import static org.assertj.core.api.Assertions.assertThat;

public class XpathTest {
    
    private WebDriver driver;

    @BeforeMethod 
    public void setUp(){
        ChromeOptions options = new ChromeOptions();
        options.addArguments("--incognito");
        options.addArguments("--headless=new");
        driver = new ChromeDriver(options);
        driver.get("https://www.saucedemo.com");
    }

    @AfterMethod 
    public void tearDown(){
        if (driver != null) driver.quit();
    }

    // 1. Find the username field using By.id.
    // Assert that it is displayed.
    @Test 
    public void usernameTest(){
        assertThat(driver.findElement(By.id("user-name")).isDisplayed()).isTrue();
    }

    // 2. Find the password field using By.name.
    // Assert that it is displayed.
    @Test
    public void passwordTest(){
        assertThat(driver.findElement(By.name("password")).isDisplayed()).isTrue();
    }

    // 3. Find the Login button using a CSS selector that matches an attribute.
    // Assert that its value attribute is the text shown on the button.
    @Test
    public void loginButtonTest(){

        WebElement loginButton = driver.findElement(By.cssSelector("[data-test='login-button']"));
    
        assertThat(loginButton.getAttribute("value")).isEqualTo("Login");
    }

    // 4. Find the "Swag Labs" logo using an XPath that matches its visible text.
    // Assert that its text is "Swag Labs".
    @Test
    public void logoTest(){

        WebElement logo = driver.findElement(By.xpath("//div[text()='Swag Labs']"));
    
        assertThat(logo.getText()).isEqualTo("Swag Labs");
    }

    // 5. Find the Login button by starting from the password field and moving to a parent or sibling.
    // Assert that the element you found is displayed.
    // Hint: inspect the page in DevTools to see the structure around the password field.
    @Test
    public void passwordToLoginButtonTest()
    {
        WebElement loginButton = driver.findElement(By.xpath("//input[@id='password']/../following-sibling::input"));
    
        assertThat(loginButton.getAttribute("value")).isEqualTo("Login");
    }

    // 6. Find the username field using an XPath that matches the id attribute.
    // Assert that it is displayed.
    @Test
    public void xpathUsernameTest()
    {
        WebElement loginButton = driver.findElement(By.xpath("//input[@id='user-name']"));
    
        assertThat(loginButton.isDisplayed()).isTrue();
    }

    // 7. Find the password field using an XPath that matches the name attribute.
    // Assert that its type attribute is "password".
    @Test
    public void xpathPasswordTest()
    {
        WebElement loginButton = driver.findElement(By.xpath("//input[@name='password']"));
    
        assertThat(loginButton.getAttribute("type")).isEqualTo("password");
    }

    // 8. Find the username field using an XPath with contains() on the id attribute.
    // Assert that it is displayed.
    @Test
    public void xpathUsernameContainsTest()
    {
        WebElement username = driver.findElement(By.xpath("//input[contains(@id, 'user')]"));
    
        assertThat(username.isDisplayed()).isTrue();
    }

    // 9. Find the password field using an XPath with starts-with() on the id attribute.
    // Assert that it is displayed.
    @Test
    public void xpathStartsWithTest(){

        WebElement username = driver.findElement(By.xpath("//input[starts-with(@id, 'pass')]"));
    
        assertThat(username.isDisplayed()).isTrue();
    }

    // 10. Find the username field using an XPath that combines two attributes with and.
    // Assert that it is displayed.
    @Test
    public void xpathTwoAttributesTest(){

        WebElement username = driver.findElement(By.xpath("//input[@id='user-name' and @type='text']"));
    
        assertThat(username.isDisplayed()).isTrue();
    }

    // 11. Find the "Swag Labs" logo using an XPath with contains() on its text.
    // Assert that its text is "Swag Labs".
    @Test
    public void xpathContainsTest(){

        WebElement logo = driver.findElement(By.xpath("//div[contains(text(), 'Swag')]"));
    
        assertThat(logo.getText()).isEqualTo("Swag Labs");
    }

    // 12. Find the username field, then move up to its parent using the .. XPath step.
    // Assert that the parent element is displayed.
    // @Test

    // 13. Find the password field by starting at the username field and using a following-sibling axis.
    // Assert that it is displayed.
    // Hint: check in DevTools whether these two fields are siblings before you write the path.
    // @Test

    // 14. Find the password field by starting at the Login button and using a preceding-sibling axis.
    // Assert that it is displayed.
    // @Test

    // 15. Find the "Swag Labs" logo using an exact text() match, then again using normalize-space().
    // Assert that both XPaths find the same element.
    // @Test

    // 16. Find all inputs whose type is NOT "password" using not().
    // Assert that the count matches the number of such inputs you see in DevTools.
    // @Test

    // 17. Find the username field or the password field with a single XPath using or.
    // Assert that the XPath returns exactly 2 elements.
    // @Test

    // 18. Find an element that does not exist on the page using XPath.
    // Assert that findElements returns an empty list (size 0), not an exception.
    // @Test

    // 19. UP: Find an ancestor two levels above the username field, without using its tag name.
    // Assert that the element you reach is displayed.
    // Hint: count the levels in DevTools first.
    // @Test

    // 20. DOWN: Find the direct child inputs of the form or container that holds the username field.
    // Assert how many there are, using what you see in DevTools.
    // @Test

    // 21. DOWN: Find an element by its exact text, starting from a container instead of the whole page.
    // Assert that its text is what you searched for.
    // @Test

    // 22. LISTS: Find every input on the page and assert the list size matches what you see in DevTools.
    // Hint: this is findElements, not findElement.
    // @Test

    // 23. LISTS: Find every input inside the container that holds the username field (not the whole page).
    // Assert that the list is smaller than the list of all inputs on the page.
    // @Test

    // 24. LISTS: Find every input on the page, then loop through them and find the one whose type is "password".
    // Assert that the element you found is displayed.
    // @Test

    // 25. LISTS: Find every input on the page and collect each one's type attribute.
    // Assert that the collected values include "password".
    // @Test

}
