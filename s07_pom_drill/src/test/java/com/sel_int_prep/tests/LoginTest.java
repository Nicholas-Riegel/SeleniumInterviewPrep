package com.sel_int_prep.tests;

import org.testng.annotations.Test;

import static org.assertj.core.api.Assertions.assertThat;

import com.sel_int_prep.pages.LoginPage;
import com.sel_int_prep.pages.ProductsPage;

public class LoginTest extends BaseTest {
    
    @Test 
    public void successfulLogin(){
        
        LoginPage loginPage = new LoginPage(getDriver());
        
        ProductsPage productsPage = loginPage.loginAs("standard_user", "secret_sauce");
        
        assertThat(productsPage.isLoaded()).isTrue();
    }

    @Test 
    public void unsuccessfulLoginRaisesError(){
        
        LoginPage loginPage = new LoginPage(getDriver());
        
        loginPage.loginAs("standard_user", "wrong_password");
        
        assertThat(loginPage.getErrorMessage()).contains("Epic sadface");
    }
}
