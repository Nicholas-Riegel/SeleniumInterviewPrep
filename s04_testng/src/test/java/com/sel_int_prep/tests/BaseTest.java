package com.sel_int_prep.tests;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;


public abstract class BaseTest {

    // ThreadLocal field private, since subclasses should now go through getDriver(). That's the same encapsulation idea as your private final By locators.
    private ThreadLocal<WebDriver> driver = new ThreadLocal<>();

    // "alwaysRun = true" makes the lifecycle methods run regardless of the group filter.
    @BeforeMethod(alwaysRun = true)
    protected void setUp(){
        
        ChromeOptions options = new ChromeOptions();
        
        options.addArguments("--incognito");

        // "HEADLESS=true mvn test" runs headless        
        if ("true".equals(System.getenv("HEADLESS"))) {
            options.addArguments("--headless=new");
        }
        
        driver.set(new ChromeDriver(options));
        
        getDriver().get("https://www.saucedemo.com");
    }

    @AfterMethod(alwaysRun = true)
    protected void tearDown(){
    
        if (getDriver() != null ){ 
    
            getDriver().quit();
    
            driver.remove();
        }
    }

    protected WebDriver getDriver(){

        return driver.get();
    }
}
