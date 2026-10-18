package com.sel_int_prep.pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

public class LoginPage extends BasePage {
    
    private final By usernameField = By.id("user-name");
    private final By passwordField = By.id("password");
    private final By loginButton = By.cssSelector("[data-test='login-button']");
    private final By errorMessage = By.cssSelector("[data-test='error']");
    
    public LoginPage(WebDriver driver){
        super(driver);
    }

    public ProductsPage loginAs(String username, String password){
        type(usernameField, username);
        type(passwordField, password);
        click(loginButton);
        return new ProductsPage(driver);
    }

    public String getErrorMessage(){
        return getText(errorMessage);
    }
}
