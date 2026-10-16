package com.sel_int_prep.tests;

import static org.assertj.core.api.Assertions.assertThat;

import java.time.Duration;
import java.util.Set;

import org.openqa.selenium.By;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.annotations.Test;

public class WindowsTest extends BaseTest {
    
    @Test
    public void switchToNewWindowAndBack() {

        getDriver().get("https://the-internet.herokuapp.com/windows");

        String originalWindow = getDriver().getWindowHandle();

        getDriver().findElement(By.linkText("Click Here")).click();

        new WebDriverWait(getDriver(), Duration.ofSeconds(10))
                .until(ExpectedConditions.numberOfWindowsToBe(2));

        Set<String> handles = getDriver().getWindowHandles();

        for (String handle : handles) {
            if (!handle.equals(originalWindow)) {
                getDriver().switchTo().window(handle);
            }
        }

        assertThat(getDriver().findElement(By.tagName("h3")).getText()).isEqualTo("New Window");

        getDriver().close();

        getDriver().switchTo().window(originalWindow);

        assertThat(getDriver().findElement(By.tagName("h3")).getText()).isEqualTo("Opening a new window");
    }
}
