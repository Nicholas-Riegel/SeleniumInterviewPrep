package com.sel_int_prep.pages;

import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;

public class CartPage extends BasePage {
    
    private final By cartItem = By.cssSelector(".cart_item");
    
    public CartPage(WebDriver driver){

        super(driver);

    }

    public boolean itemIsInCart(String itemName){

        List<WebElement> cartItems = wait.until(ExpectedConditions.visibilityOfAllElementsLocatedBy(cartItem));
        
        for (WebElement item : cartItems){
        
            if (item.getText().contains(itemName)){

                return true;

            }
        }

        return false;

    }
}
