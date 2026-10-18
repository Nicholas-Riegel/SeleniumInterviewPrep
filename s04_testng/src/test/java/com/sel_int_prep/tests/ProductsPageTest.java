package com.sel_int_prep.tests;

import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;
import org.assertj.core.api.SoftAssertions;

import com.sel_int_prep.pages.LoginPage;
import com.sel_int_prep.pages.ProductsPage;
import com.sel_int_prep.pages.CartPage;

public class ProductsPageTest extends BaseTest {
    
    
    @DataProvider(name = "itemsToCart", parallel = true)
    public Object[][] itemsToCart() {
    
        return new Object[][] {
    
            {"Sauce Labs Backpack"},
    
            {"Sauce Labs Bike Light"},
    
            {"Sauce Labs Bolt T-Shirt"}
        };
    }
    
    @Test(dataProvider = "itemsToCart", groups = {"smoke", "regression"})    
    public void itemIsAddedToCart(String itemForCart){

        SoftAssertions soft = new SoftAssertions();
        
        LoginPage loginPage = new LoginPage(getDriver());
        
        ProductsPage productsPage = loginPage.loginAs("standard_user", "secret_sauce");
        
        productsPage.addItemToCart(itemForCart);

        soft.assertThat(productsPage.getCartBadgeCount()).isEqualTo(1);
        
        CartPage cartPage = productsPage.goToCart();
        
        soft.assertThat(cartPage.itemIsInCart(itemForCart)).isTrue();

        soft.assertAll();
    }
}
