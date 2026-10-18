package com.sel_int_prep.pages;

import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;

public class CartPage extends BasePage {

    private final By inventoryItem = By.cssSelector("[data-test='inventory-item']");
    
    public CartPage(WebDriver driver){
        super(driver);
    }

    public boolean checkItemInCartPage(String itemName){

        List<WebElement> inventoryItems = wait.until(ExpectedConditions.visibilityOfAllElementsLocatedBy(inventoryItem));

        for (WebElement item : inventoryItems){

            if (item.getText().contains(itemName)){

                return true;
            }
        }
        return false;
    }
}
