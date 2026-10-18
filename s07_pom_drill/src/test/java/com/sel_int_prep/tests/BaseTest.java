package com.sel_int_prep.tests;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;

public abstract class BaseTest {
    
    protected ThreadLocal<WebDriver> driver = new ThreadLocal<>();

    @BeforeMethod 
    protected void setUp(){
        ChromeOptions options = new ChromeOptions();
        options.addArguments("--incognito");
        options.addArguments("--headless=new");
        driver.set(new ChromeDriver(options));
        getDriver().get("https://www.saucedemo.com/");
    }

    @AfterMethod 
    protected void tearDown(){
        
        if (driver != null ){

            getDriver().quit();

            driver.remove();
        } 
    }

    public WebDriver getDriver(){

        return driver.get();
    }
}
