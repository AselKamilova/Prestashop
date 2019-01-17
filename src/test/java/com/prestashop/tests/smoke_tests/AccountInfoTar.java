package com.prestashop.tests.smoke_tests;

import io.github.bonigarcia.wdm.WebDriverManager;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.testng.Assert;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import java.util.concurrent.TimeUnit;

public class AccountInfoTar {
    WebDriver driver;
    String fullNameTop;

    @BeforeMethod
    public void setUp() {
        WebDriverManager.chromedriver().setup();

        driver = new ChromeDriver();
        driver.get("http://automationpractice.com/index.php");
        driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);

        //Click Sign in link
        driver.findElement(By.linkText("Sign in")).click();

        //Login using valid username and password
        WebElement username = driver.findElement(By.id("email"));
        username.sendKeys("aekmlv@gmail.com");

        WebElement password = driver.findElement(By.id("passwd"));
        password.sendKeys("123456");

        driver.findElement(By.id("SubmitLogin")).click();

        fullNameTop = driver.findElement(By.xpath("//a[@class='account']//span")).getText();
    }

    @Test
    public void loginMyAccount() {
        //VERIFY that title contains My account
        String expected = "My account";
        Assert.assertTrue(driver.getTitle().contains(expected));

        //VERIFY that account holder's full name is displayed next to the Sign out link
        String expectedName = "Asel Kamilova";

        System.out.println("Expected name: " + expectedName + " | Actual name: " + fullNameTop);
        Assert.assertEquals(expectedName, fullNameTop);
    }

    @Test
    public void loginMyPersonalInfo() throws InterruptedException {
        //Click on My Personal Information button
        driver.findElement(By.xpath("//a[@title='Information']//span")).click();

        Thread.sleep(3000);

        //VERIFY title contains Identity
        String expected = "Identity";
        Assert.assertTrue(driver.getTitle().contains(expected));

        //VERIFY that first name and last name matches the full name on top
        WebElement firstName = driver.findElement(By.id("firstname"));
        String inputFirstName = firstName.getAttribute("value");

        WebElement lastName = driver.findElement(By.id("lastname"));
        String inputLastName = lastName.getAttribute("value");

        String inputFullName = inputFirstName + " " + inputLastName;

        System.out.println("Input full name: " + inputFullName + " | Actual full name : " + fullNameTop);
        Assert.assertEquals(inputFullName, fullNameTop);

        System.out.println("=====================================");

        //Click on Save button
        driver.findElement(By.name("submitIdentity")).click();

        Thread.sleep(3000);

        //Verify error message “The password you entered is incorrect.”
        String expectedError = "The password you entered is incorrect.";
        String actualError = driver.findElement(By.xpath("//div[@class='alert alert-danger']//ol//li")).getText();

        System.out.println("Expected error message: " + expectedError + " | Actual error message: " + actualError);
        Assert.assertEquals(expectedError, actualError);

        //Click on Back to your account
        driver.findElement(By.linkText("Back to your account")).click();

        Thread.sleep(3000);

        //Verify that title contains My account
        String expectedTitle = "My account";
        Assert.assertTrue(driver.getTitle().contains(expectedTitle));
    }

    @Test
    public void loginMyAddresses() throws InterruptedException {
        //Click on My addresses
        driver.findElement(By.linkText("My addresses")).click();

        Thread.sleep(3000);

        //Click on Add a new address
        driver.findElement(By.linkText("Add a new address")).click();

        Thread.sleep(3000);

        //Verify that first name and last name matches the full name on top
        WebElement firstName = driver.findElement(By.id("firstname"));
        String inputFirstName = firstName.getAttribute("value");

        WebElement lastName = driver.findElement(By.id("lastname"));
        String inputLastName = lastName.getAttribute("value");

        String inputFullName = inputFirstName + " " + inputLastName;

        System.out.println("Input full name: " + inputFullName + " | Actual full name : " + fullNameTop);
        Assert.assertEquals(inputFullName, fullNameTop);

        //Delete the first name
        firstName.clear();

        //Click save
        driver.findElement(By.id("submitAddress")).click();

        Thread.sleep(3000);

        //Verify error message “firstname is required.”
        String expectedAlert = "firstname is required.";
        String actualAlert = driver.findElement(By.xpath("//div[@class='alert alert-danger']//ol//li[1]")).getText();

        System.out.println("Expected alert message: " + expectedAlert + " | Actual alert message: " + actualAlert);
        Assert.assertEquals(expectedAlert, actualAlert);
    }

    @AfterMethod
    public void tearDown () throws InterruptedException {
        Thread.sleep(3000);
        driver.quit();
    }
}
