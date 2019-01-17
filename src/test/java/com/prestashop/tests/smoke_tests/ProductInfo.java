package com.prestashop.tests.smoke_tests;
import io.github.bonigarcia.wdm.WebDriverManager;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.Select;
import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Ignore;
import org.testng.annotations.*;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;


public class ProductInfo {
    WebDriver driver;
    WebElement product;
    WebElement price;
    String productName;
    String productPrice;

    @BeforeMethod
    public void setUp() {
        WebDriverManager.chromedriver().setup();
        driver = new ChromeDriver();
        driver.get("http://automationpractice.com/index.php");
        driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);

        product = driver.findElement(By.linkText("Blouse"));
        productName = product.getText();

        price = driver.findElement(By.xpath("(//span[@class='price product-price'])[4]"));
        productPrice = price.getText();
    }

    @Test
    public void productInformationPrice() {
        //Click on any product
        product.click();

        //VERIFY that same name and price displayed as on the homepage
        WebElement productPageName = driver.findElement(By.xpath("//h1[.='Blouse']"));
        WebElement productPagePrice = driver.findElement(By.xpath("//span[@id='our_price_display']"));

        System.out.println("Homepage product name: " + productName + " | Product page product name: " + productPageName.getText());
        System.out.println("Homepage product price: " + productPrice + " | Product page product price: " + productPagePrice.getText());

        Assert.assertEquals(productPageName.getText(), productName);
        Assert.assertEquals(productPagePrice.getText(), productPrice);
    }

    @Test
    public void productInfoDetails() {
        //VERIFY that default quantity is 1
        product.click();

        WebElement quantity = driver.findElement(By.id("quantity_wanted"));
        String expectedQuantity = "1";
        String actualQuantity = quantity.getAttribute("value");

        System.out.println("Expected default quantity: " + expectedQuantity +  " | Actual default quantity: " + actualQuantity);
        Assert.assertEquals(expectedQuantity, actualQuantity);

        System.out.println("=====================================");

        //VERIFY that default size is S
        WebElement size = driver.findElement(By.id("group_1"));
        Select select = new Select(size);
        String expectedSize = "S";
        String actualSize = select.getFirstSelectedOption().getText();

        System.out.println("Expected default size: " + expectedSize + " | Actual default size: " + actualSize);
        Assert.assertEquals(expectedSize, actualSize);

        System.out.println("=====================================");

        //VERIFY that size options are S,M,L
        List<String> expectedSizeOptions = new ArrayList<>();
        expectedSizeOptions.add("S");
        expectedSizeOptions.add("M");
        expectedSizeOptions.add("L");

        List<WebElement> actualSizeOptions = select.getOptions();

        for (int i = 0; i < actualSizeOptions.size(); i++) {
            System.out.println("Expected size option: " + expectedSizeOptions.get(i) + " | Actual size option: " + actualSizeOptions.get(i).getText());
            Assert.assertEquals(expectedSizeOptions.get(i), actualSizeOptions.get(i).getText());
        }
    }

    @Test
    public void productInfoAddToCart() {
        product.click();

        //Click on Add to cart
        WebElement addToCart = driver.findElement(By.xpath("//span[.='Add to cart']"));
        addToCart.click();

        //VERIFY confirmation message “Product successfully added to your shopping cart”
        String expectedMessage = "Product successfully added to your shopping cart";
        WebElement confirmationMessage = driver.findElement(By.xpath("//div[@class='layer_cart_product col-xs-12 col-md-6']//h2"));
        confirmationMessage.click();
        String actualMessage = confirmationMessage.getText();
        //String actualMessage = confirmationMsg.getAttribute("textContent").trim();

        System.out.println("Expected confirmation message: " + expectedMessage);
        System.out.println("Actual confirmation message: " + actualMessage);
        Assert.assertEquals(expectedMessage, actualMessage);

        System.out.println("=====================================");

        //VERIFY that default quantity is 1
        WebElement defaultQuantity = driver.findElement(By.id("layer_cart_product_quantity"));
        String expectedQuantity = "1";
        String actualQuantity = defaultQuantity.getText();

        System.out.println("Expected default quantity: " + expectedQuantity + " | Actual default quantity: " + actualQuantity);
        Assert.assertEquals(expectedQuantity, actualQuantity);

        //VERIFY that default size is S
        WebElement defaultSize = driver.findElement(By.id("layer_cart_product_attributes"));
        String expectedDefaultSize = "S";
        String actualDefaultSize = defaultSize.getText();

        Assert.assertTrue(actualDefaultSize.substring(actualDefaultSize.indexOf(","), actualDefaultSize.length()).contains(expectedDefaultSize));

        System.out.println("=====================================");

        //VERIFY that same name and price displayed as on the home page
        String cartName = driver.findElement(By.id("layer_cart_product_title")).getText();
        String cartPrice = driver.findElement(By.id("layer_cart_product_price")).getText();

        System.out.println("Homepage product name: " + productName + " | Add to cart page product name: " + cartName);
        System.out.println("Homepage product price: " + productPrice + " | Add to cart page product price: " + cartPrice);

        Assert.assertEquals(productName, cartName);
        Assert.assertEquals(productPrice, cartPrice);
    }

    @AfterMethod
    public void tearDown () throws InterruptedException {
        Thread.sleep(3000);
        driver.quit();
    }

}



