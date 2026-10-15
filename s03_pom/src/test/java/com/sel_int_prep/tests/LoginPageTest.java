package com.sel_int_prep.tests;

import static org.assertj.core.api.Assertions.assertThat;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import com.sel_int_prep.pages.LoginPage;
import com.sel_int_prep.pages.ProductsPage;

public class LoginPageTest {
    
    private ThreadLocal<WebDriver> driver = new ThreadLocal<>();

    @BeforeMethod 
    private void setUp(){

        ChromeOptions options = new ChromeOptions();

        options.addArguments("--incognito");

        options.addArguments("--headless=new");

        driver.set(new ChromeDriver(options));

        driver.get().get("https://www.saucedemo.com");

    }

    @AfterMethod 
    private void tearDown(){

        if (driver.get() != null) driver.get().quit();

    }

    @Test 
    public void successfulLoginLandsOnProductsPage(){
        
        LoginPage loginPage = new LoginPage(driver.get());
        
        ProductsPage productsPage = loginPage.loginAs("standard_user", "secret_sauce");
        
        assertThat(productsPage.isLoaded()).isTrue();

    }

    @Test 
    public void unsuccessfulLoginRaisesErrorMessage(){
        
        LoginPage loginPage = new LoginPage(driver.get());
        
        loginPage.loginAs("standard_user", "wrong_password");
        
        assertThat(loginPage.getErrorMessage()).contains("Epic sadface");

    }

}
