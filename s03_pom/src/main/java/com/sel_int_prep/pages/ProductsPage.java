package com.sel_int_prep.pages;

import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;

public class ProductsPage extends BasePage {
    
    private final By title = By.cssSelector("[data-test='title']");
    
    private final By inventoryItem = By.cssSelector("[data-test='inventory-item']");
    
    private final By shoppingCartLink = By.cssSelector("[data-test='shopping-cart-link']");
    
    public ProductsPage(WebDriver driver){
    
        super(driver);
    
    }

    public boolean isLoaded(){
    
        return getText(title).equals("Products");
    
    }

    public void addItemToCart(String itemName){
        
        List<WebElement> inventoryItems = wait.until(ExpectedConditions.visibilityOfAllElementsLocatedBy(inventoryItem));
        
        for (WebElement item : inventoryItems){
        
            if (item.getText().contains(itemName)){

                item.findElement(By.cssSelector(".btn_inventory")).click();
    
                return;
    
            }
    
        }
    
    }

    public CartPage goToCart(){
    
        click(shoppingCartLink);
    
        return new CartPage(driver);
    
    }
}
