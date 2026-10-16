package com.sel_int_prep.tests;

import org.testng.annotations.BeforeSuite;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.AfterClass;
import org.testng.annotations.AfterTest;
import org.testng.annotations.AfterSuite;
import org.testng.annotations.Test;
import org.testng.Assert;


public class LifecycleDemoTest {
    
    @BeforeSuite 
    public void beforeSuite(){
        System.out.println("BeforeSuite");
    }
    
    @BeforeTest
    public void beforeTest(){
        System.out.println("BeforeTest");
    }
    
    @BeforeClass
    public void beforeClass(){
        System.out.println("BeforeClass");
    }
    
    @BeforeMethod
    public void beforeMethod(){
        System.out.println("BeforeMethod");
    }
    
    @AfterMethod
    public void afterMethod(){
        System.out.println("AfterMethod");
    }
    
    @AfterClass
    public void afterClass(){
        System.out.println("AfterClass");
    }
    
    @AfterTest
    public void afterTest(){
        System.out.println("AfterTest");
    }
    
    @AfterSuite
    public void afterSuite(){
        System.out.println("AfterSuite");
    }

    // @Test(priority = 2)
    // @Test
    // public void test1(){
    //     System.out.println("Test 1");
    //     // Assert.fail("boom");
    // }
    
    // @Test(priority = 1, enabled = false)
    // @Test
    // public void test2(){
    //     System.out.println("Test 2");
    // }
    
    // @Test(dependsOnMethods = "test1")
    // public void test3(){
    //     System.out.println("Test 3");
    // }
}

// "mvn test -Dtest=LifecycleDemoTest" printout:

// BeforeSuite
// BeforeTest
// BeforeClass
// BeforeMethod
// Test 1
// AfterMethod
// BeforeMethod
// Test 2
// AfterMethod
// AfterClass
// AfterTest
// AfterSuite