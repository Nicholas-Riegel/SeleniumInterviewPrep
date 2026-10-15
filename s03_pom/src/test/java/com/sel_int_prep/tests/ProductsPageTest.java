package com.sel_int_prep.tests;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import static org.assertj.core.api.Assertions.assertThat;

import com.sel_int_prep.pages.LoginPage;
import com.sel_int_prep.pages.ProductsPage;
import com.sel_int_prep.pages.CartPage;

public class ProductsPageTest {
    
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
    
    @DataProvider(name = "itemsToCart", parallel = true)
    public Object[][] itemsToCart() {
    
        return new Object[][] {
    
            {"Sauce Labs Backpack"},
    
            {"Sauce Labs Bike Light"},
    
            {"Sauce Labs Bolt T-Shirt"}
    
        };
    
    }
    
    @Test(dataProvider = "itemsToCart")    
    public void itemIsAddedToCart(String itemForCart){
        
        LoginPage loginPage = new LoginPage(driver.get());
        
        ProductsPage productsPage = loginPage.loginAs("standard_user", "secret_sauce");
        
        productsPage.addItemToCart(itemForCart);
        
        CartPage cartPage = productsPage.goToCart();
        
        assertThat(cartPage.itemIsInCart(itemForCart)).isTrue();

    }

}
