package com.sel_int_prep;

import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;

// A static import lets you call assertThat(...) directly, without writing Assertions.assertThat(...) every time. It's the usual way to use AssertJ.
import static org.assertj.core.api.Assertions.assertThat;

public class FirstTest {
    
    private WebDriver driver;
    
    @BeforeMethod
    public void setUp() {
        driver = new ChromeDriver();
    }
    
    @AfterMethod
    public void tearDown() {
        driver.quit();
        // A related method, driver.close(), only closes the current window and leaves the driver process running. That difference is a common interview question, so remember it.
    }
    
    @Test
    public void openSauceDemo() {
        driver.get("https://www.saucedemo.com");
        String title = driver.getTitle();
        assertThat(title).isEqualTo("Swag Labs");
    }
}