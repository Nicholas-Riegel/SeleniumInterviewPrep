package com.sel_int_prep.tests;

import org.openqa.selenium.Alert;
import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebElement;
import org.testng.annotations.Test;
import org.openqa.selenium.interactions.Actions;

import static org.assertj.core.api.Assertions.assertThat;

public class ActionsTest extends BaseTest {
    
    // Test that hovering shows text
    @Test
    public void hoveringShowText(){
        
        getDriver().get("https://the-internet.herokuapp.com/hovers");

        WebElement firstFigure = getDriver().findElement(By.className("figure"));
        
        new Actions(getDriver()).moveToElement(firstFigure).perform();

        WebElement firstCaption = firstFigure.findElement(By.cssSelector("h5"));

        assertThat(firstCaption.getText()).contains("user1");
    }

    // Right click
    @Test 
    public void contextClick(){

        getDriver().get("https://the-internet.herokuapp.com/context_menu");

        WebElement hotspot = getDriver().findElement(By.id("hot-spot"));

        new Actions(getDriver()).contextClick(hotspot).perform();

        Alert alert = getDriver().switchTo().alert();

        assertThat(alert.getText()).contains("You selected a context menu");

        alert.accept();

    }
    
    // Key presses
    @Test
    public void keyPressIsRegistered(){

        getDriver().get("https://the-internet.herokuapp.com/key_presses");

        new Actions(getDriver()).sendKeys(Keys.ENTER).perform();

        String result = getDriver().findElement(By.id("result")).getText();

        assertThat(result).contains("ENTER");
    }

    // Combo Key presses
    @Test
    public void shiftKeyTypesUppercase(){

        getDriver().get("https://the-internet.herokuapp.com/key_presses");

        WebElement input = getDriver().findElement(By.id("target"));

        new Actions(getDriver())
                .keyDown(Keys.SHIFT)
                .sendKeys(input, "abc")
                .keyUp(Keys.SHIFT)
                .perform();

        assertThat(input.getAttribute("value")).isEqualTo("ABC");
    }
}
