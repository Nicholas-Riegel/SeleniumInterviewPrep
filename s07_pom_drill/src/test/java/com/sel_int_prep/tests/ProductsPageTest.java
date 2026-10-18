package com.sel_int_prep.tests;

import org.assertj.core.api.SoftAssertions;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import com.sel_int_prep.pages.CartPage;
import com.sel_int_prep.pages.LoginPage;
import com.sel_int_prep.pages.ProductsPage;

public class ProductsPageTest extends BaseTest {

    @DataProvider(name="itemsToCart", parallel = true)
    private Object[][] items(){
        return new Object[][] {
            {"Backpack"},
            {"Bike Light"},
            {"Fleece Jacket"}
        };
    }
    
    @Test(dataProvider = "itemsToCart")
    public void itemsSuccessfullyAddedToCart(String itemName){

        SoftAssertions soft = new SoftAssertions();

        LoginPage loginPage = new LoginPage(getDriver());

        ProductsPage productsPage = loginPage.loginAs("standard_user", "secret_sauce");

        productsPage.addItemToCart(itemName);

        soft.assertThat(productsPage.checkCartBadgeCount()).isEqualTo(1);

        CartPage cartPage = productsPage.goToCart();

        soft.assertThat(cartPage.checkItemInCartPage(itemName)).isTrue();

        soft.assertAll();
    }
}
