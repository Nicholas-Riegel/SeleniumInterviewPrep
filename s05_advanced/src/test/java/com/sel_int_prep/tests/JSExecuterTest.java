package com.sel_int_prep.tests;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebElement;
import org.testng.annotations.Test;

public class JSExecuterTest extends BaseTest {
    
    // WebDriver has no executeScript method, so the driver is cast to JavascriptExecutor
    private JavascriptExecutor js() {
        return (JavascriptExecutor) getDriver();
    }

    // 1. Read a value from the page
    @Test
    public void readValuesFromThePage() {

        getDriver().get("https://the-internet.herokuapp.com/");

        // The return sends the result back to Java. Without it, executeScript would give you null.
        String title = (String) js().executeScript("return document.title;");

        assertThat(title).isEqualTo("The Internet");
        // assertThat(title).isEqualTo("Nope");
    }

    // Scrolling
    @Test
    public void scrollElementIntoView() {

        getDriver().get("https://the-internet.herokuapp.com/large");

        WebElement lastRow = getDriver().findElement(By.id("sibling-50.3"));

        // arguments[0].scrollIntoView(true) passes your Java WebElement into the script as arguments[0]. The browser then scrolls so that element is at the top of the window.
        js().executeScript("arguments[0].scrollIntoView(true);", lastRow);

        // window.pageYOffset is how many pixels the page has scrolled from the top. Before the scroll it's 0, so a value above 0 proves the page really moved.
        // The cast is (Long), not (Integer). executeScript returns whole numbers as Long. That's a common trap.
        Long scrollY = (Long) js().executeScript("return window.pageYOffset;");

        // 0L has the L so AssertJ compares a Long with a Long.
        assertThat(scrollY).isGreaterThan(0L);

        System.out.println(scrollY);
    }

    // Clicking with js
    @Test
    public void clickWithJavascript() {

        getDriver().get("https://the-internet.herokuapp.com/add_remove_elements/");

        WebElement addButton = getDriver().findElement(By.cssSelector("button[onclick='addElement()']"));

        js().executeScript("arguments[0].click();", addButton);

        List<WebElement> deleteButtons = getDriver().findElements(By.className("added-manually"));

        assertThat(deleteButtons).hasSize(1);
    }
}
