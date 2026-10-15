package com.sel_int_prep.tests;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import static org.assertj.core.api.Assertions.assertThat;

import com.sel_int_prep.pages.LoginPage;
import com.sel_int_prep.pages.ProductsPage;



public class LoginTest {
    
    private WebDriver driver;

    @BeforeMethod 
    public void setUp(){
        driver = new ChromeDriver();
        driver.get("https://www.saucedemo.com");
    }

    @AfterMethod 
    public void tearDown(){
        if (driver != null) driver.quit();
    }

    @Test 
    public void successfulLoginLandsOnProductsPage(){
        LoginPage loginPage = new LoginPage(driver);
        ProductsPage productsPage = loginPage.loginAs("standard_user", "secret_sauce");
        assertThat(productsPage.isLoaded()).isTrue();
    }

    @Test 
    public void unsuccessfulLoginRaisesErrorMessage(){
        LoginPage loginPage = new LoginPage(driver);
        loginPage.loginAs("standard_user", "wrong_password");
        assertThat(loginPage.getErrorMessage()).contains("Epic sadface");
    }
}
