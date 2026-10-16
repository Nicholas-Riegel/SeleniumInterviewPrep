package com.sel_int_prep.tests;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.testng.ITestListener;
import org.testng.ITestResult;

public class TestListener implements ITestListener {

    @Override
    public void onTestFailure(ITestResult result) { // onTestFailure: TestNG calls it right after a test fails. It runs before @AfterMethod, so the browser is still open. That's why the screenshot works.

        Object testInstance = result.getInstance();

        if (!(testInstance instanceof BaseTest)) { // instanceof BaseTest makes sure it's one of yours, and the cast then lets you call getDriver().
            return;
        }

        WebDriver driver = ((BaseTest) testInstance).getDriver();

        try {
            File source = ((TakesScreenshot) driver).getScreenshotAs(OutputType.FILE);

            Path targetDir = Path.of("target", "screenshots");

            Files.createDirectories(targetDir);

            Files.copy(source.toPath(), targetDir.resolve(result.getName() + "-" + System.currentTimeMillis() + ".png"));

        } catch (IOException e) {
            System.out.println("Could not save screenshot: " + e.getMessage());
        }
    }
}