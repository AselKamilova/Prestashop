package com.prestashop.tests.smoke_tests;

import com.github.javafaker.Faker;
import io.github.bonigarcia.wdm.WebDriverManager;
import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.Select;
import org.testng.Assert;
import org.testng.annotations.*;

import java.util.concurrent.TimeUnit;
public class AccountInfos {
    WebDriver driver;

    @BeforeClass
    public void setDriver() {
        WebDriverManager.chromedriver().setup();
        driver = new ChromeDriver();
        driver.manage().timeouts().implicitlyWait(20, TimeUnit.SECONDS);
    }
    @AfterClass
    public void quitWindows() {
        try { Thread.sleep(3000); } catch (Exception e) { }
        driver.quit();
    }

    /**
     * Login: my account
     * 1.Go to http://automationpractice.com/index.php
     * 2.Click Sign in link
     * 3.Login using valid username and password
     * 4.Verify that title contains My account
     * 5.Verify that account holder full name is displayed next to the Sign out link
     */

    @Test (priority = 1, groups = "login")

    public void loginMyAccount(){
         Faker email=new Faker();

        driver.get("http://automationpractice.com/index.php");
        driver.findElement(By.linkText("Sign in")).click();
        driver.findElement(By.id("email")).sendKeys("seleniumtester@test.com" + Keys.TAB + "123456" + Keys.ENTER);

        //  Verify that title contains "My account"
        Assert.assertTrue(driver.getTitle().contains("My account"));

        //  Verify that account holder full name is displayed next to the Sign out link
        String fullName = driver.findElement(By.xpath("//div[@class='header_user_info']/a/span")).getText();
        Assert.assertTrue(fullName.equals("crocus falcon"));

    }

    /**
     * Login: My personal information
     * 6.Click on My personal information button
     * 7.Verify title contains Identity
     * 8.Verify that first name and last name matches the full name on top
     * 9.Click on Save button
     * 10.Verify error message “The password you entered is incorrect.”
     * 11.Click on Back to your account
     * 12.Verify that title contains My account
     */

    @Test (priority = 2, groups = "login", dependsOnMethods = "loginMyAccount")
    public void myPersonalInformation(){
        // Click On My Personal Information
        driver.findElement(By.xpath("//a[@title='Information']")).click();

        // Verify title contains Identity
        Assert.assertTrue(driver.getTitle().contains("Identity"));

        // Verify that first name and last name matches the full name on top
        String firstName = driver.findElement(By.id("firstname")).getText();
        String lastName = driver.findElement(By.id("lastname")).getText();
        String fullName = driver.findElement(By.xpath("//div[@class='header_user_info']/a/span")).getText();
        Assert.assertTrue(fullName.contains(firstName));
        Assert.assertTrue(fullName.contains(lastName));

        //Click on Save button
        driver.findElement(By.name("submitIdentity")).click();

        //Verify error message “The password you entered is incorrect.”
        String error = driver.findElement(By.xpath("//div[@class='alert alert-danger']/ol/li")).getText();
        Assert.assertEquals(error,"The password you entered is incorrect.");

        //Click on Back to your account
        driver.navigate().back();
        driver.navigate().back();

        //Verify that title contains My account
        Assert.assertTrue(driver.getTitle().contains("My account"));

    }

    /**
     * Login: My addresses
     * 13.Click on My addresses
     * 14.Click on Add a new address
     * 15.Verify that first name and last name matches the full name on top
     * 16.Delete the first name
     * 17.Click save
     * 18.Verify error message “firstname is required.”
     */
    @Test (priority = 3, groups = "login", dependsOnMethods = "myPersonalInformation")
    public void myAddresses(){
        driver.findElement(By.xpath("//a[@title='Addresses']/span")).click();

        //Click on add a new address
        driver.findElement(By.xpath("//a[@title='Add an address']/span")).click();

        //Verify that first name and last name matches the full name on top
        String firstName = driver.findElement(By.id("firstname")).getText();
        String lastName = driver.findElement(By.id("lastname")).getText();
        String fullName = driver.findElement(By.xpath("//div[@class='header_user_info']/a/span")).getText();
        Assert.assertTrue(fullName.contains(firstName));
        Assert.assertTrue(fullName.contains(lastName));

        //Delete the first name
        driver.findElement(By.id("firstname")).clear();

        //Click save
        driver.findElement(By.xpath("//button[@id='submitAddress']/span")).click();

        //Verify error message “firstname is required.”
        String error = driver.findElement(By.xpath("//div[@class='alert alert-danger']/ol/li[1]")).getText();
        Assert.assertTrue(error.equals("firstname is required."));

    }
}