package com.prestashop.tests.smoke_tests;

import io.github.bonigarcia.wdm.WebDriverManager;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.Select;
import org.testng.Assert;
import org.testng.annotations.*;

import java.util.List;
import java.util.concurrent.TimeUnit;

public class ProductInformation {
    WebDriver driver;
    String homepageName;
    String homepagePrice;

    @BeforeClass
    public void setDriver() {
        WebDriverManager.chromedriver().setup();
        driver = new ChromeDriver();
        driver.manage().timeouts().implicitlyWait(21, TimeUnit.SECONDS);
    }
    @AfterClass
    public void quitWindows() {
        try { Thread.sleep(3000); } catch (Exception e) { }
        driver.quit();
    }

    /**
     * Product information-price:
     * 1.Go to http://automationpractice.com/index.php
     * 2.Click on any product
     * 3.Verify that same name and price displayed as on the home page
     */
    @Test (priority = 1, groups = "1")
    public void price() {
        driver.get("http://automationpractice.com/index.php");

        // 2. Click on any product
        // HERE, clicking on the 3rd product on the list
        //  the product that is to be clicked can be changed by changing the index
        //  on the following xpath (index from 1 up to 7, inclusive)
        //  //ul[@id='homefeatured']/li[3]

        // STEP 1: Gather product info on HOMEPAGE
        homepageName = driver.findElement(By.xpath("//ul[@id='homefeatured']/li[3]//a[@class='product-name']")).getText().trim();
        homepagePrice = driver.findElement(By.xpath("(//ul[@id='homefeatured']/li[3]//span[@itemprop='price'])[2]")).getText().trim();

//        System.out.println(homepageName + " vs Printed Dress"); System.out.println(homepagePrice + " vs $26.00");

        // STEP 2: Go to product specific page
        driver.findElement(By.xpath("//ul[@id='homefeatured']/li[3]//a[@class='product-name']")).click();

        // STEP 3: Gather product info on PRODUCT PAGE
        String productpageName = driver.findElement(By.xpath("//h1[@itemprop='name']")).getText().trim();
        String productpagePrice = driver.findElement(By.xpath("//span[@itemprop='price']")).getText().trim();

//        System.out.println(productpageName + " vs Printed Dress >> ProductPage"); System.out.println(productpagePrice + " vs $26.00 >> ProductPage");

        // STEP FINAL: Run assertions
     Assert.assertEquals(homepageName, productpageName);
     Assert.assertEquals(homepagePrice, productpagePrice);
    }

    /**
     * Product information-details:
     * 4.that default quantity is 1
     * 5.Verify that default size is S
     * 6.Verify that size options are S, M, L
     */
    @Test (priority = 2, groups = "1", dependsOnMethods = "price")
    public void details() {
        // STEP 4: Store and assert default quantity to be 1
        int defaultQty = Integer.parseInt(driver.findElement(By.name("qty")).getAttribute("value"));
        Assert.assertEquals(defaultQty, 1);
//        System.out.println(defaultQty + " > default quantity: 1");

        // Create SELECT element to store and manipulate the dropdown list
        Select dropdownSize = new Select(driver.findElement(By.name("group_1")));

        // STEP 5: Verify that default size is S
        String defaultSize = dropdownSize.getFirstSelectedOption().getText();
      Assert.assertEquals(defaultSize, "S");
//        System.out.println(defaultSize + " > default size: S");

        // Store and manipulate the size options of the dropdown list
        String sizeOptions = "";
        List<WebElement> allElements = dropdownSize.getOptions();
        for (WebElement each : allElements)
            sizeOptions += each.getText() + ", ";
        sizeOptions = sizeOptions.substring(0, sizeOptions.length() - 2);

        // STEP 6: Verify that size options are S, M, L
     Assert.assertEquals(sizeOptions, "S, M, L");
//        System.out.println(sizeOptions + " > size options: S, M, L");
    }
    /**
     * Product information– Add to cart:
     * 7.Click on Add to cart
     * 8.Verify confirmation message “Product successfully added to your shopping cart”
     * 9.that default quantity is 1
     * 10.Verify that default size is S
     * 11.Verify that same name and price displayed as on the home page
     */
    @Test (priority = 3, groups = "1", dependsOnMethods = "details")
    public void addToCart() {
        // STEP 7: Click on Add to cart
        driver.findElement(By.name("Submit")).click();

        // add hard delay so that the browser loads the popup
        try { Thread.sleep(1000); } catch (Exception e) { }

        // STEP 8: Verify confirmation message
        String confirmationMessage = driver.findElement(By.tagName("h2")).getText();
      Assert.assertEquals(confirmationMessage, "Product successfully added to your shopping cart");
//        System.out.println(driver.findElement(By.tagName("h2")).getText());

        // STEP 9: Verify the default quantity is 1
        int defaultQty = Integer.parseInt(driver.findElement(By.id("layer_cart_product_quantity")).getText());
      Assert.assertEquals(defaultQty, 1);
//        System.out.println(defaultQty + " > default quantity: 1");

        // STEP 10: Verify the default size is S
        String defaultSize = driver.findElement(By.id("layer_cart_product_attributes")).getText();
        defaultSize = defaultSize.substring(defaultSize.length()-1);
        Assert.assertEquals(defaultSize, "S");
//        System.out.println(defaultSize + " vs default size: S");

        // STEP 11: Verify the name and price are as displayed on the home page
        String addToCardName = driver.findElement(By.id("layer_cart_product_title")).getText();
        String addToCardPrice = driver.findElement(By.id("layer_cart_product_price")).getText();
      Assert.assertEquals(homepageName, addToCardName);
       Assert.assertEquals(homepagePrice, addToCardPrice);
//        System.out.println(addToCardName + " vs " + homepageName); System.out.println(addToCardPrice + " vs " + homepagePrice);
    }

 }


