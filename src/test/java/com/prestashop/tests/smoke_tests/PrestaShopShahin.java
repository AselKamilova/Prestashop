package com.prestashop.tests.smoke_tests;
import com.prestashop.utilities.*; // local imports

import org.apache.commons.compress.compressors.brotli.BrotliUtils;
import org.openqa.selenium.*; // automation + testing related imports
import org.openqa.selenium.interactions.Actions;
import org.testng.annotations.*;

import static org.testng.Assert.*; // static imports
public class PrestaShopShahin extends TestBase {

    Actions actions;
    static int totalItemsInCard = 0;
    static double totalPrice = 2.0; // initial value $2 due to shipping cost

    /**
     * Cart Details
     * 1.Open browser
     * 2.Go to http://automationpractice.com/index.php
     * 3.Click on any product that is not on sale
     * 4.Enter a random quantity between 2 and 5
     * 5.Select a different size
     * 6.Click on add to cart
     * 7.Verify confirmation message Product successfully added to your shopping cart
     * 8.Dismiss the confirmation window by clicking on the x icon
     * 9.Click on the company logo
     * 10.Click on any product that is on sale
     * 11.Enter a random quantity between 2 and 5
     * 12.Select a different size
     * 13.Click on add to cart
     * 14.Verify confirmation message Product successfully added to your shopping cart
     * 15.Dismiss the confirmation window by clicking on the x icon
     * 16.Hover over the Cart icon
     * 17.Verify that correct total is displayed
     * 18.Verify that total is correct based on the price and item count of the products you added to cart.
     *    (Shipping is always $2)
     */
    @Test (priority = 1, groups = "add_to_cart")
    public void notOnSaleItem() {
        // 2.Go to http://automationpractice.com/index.php
        driver.get("http://automationpractice.com/index.php");

        // 3.Click on any product that is not on sale
        String xpathNotOnSaleItem = BrowserUtils.xpathOfItemSaleStatus(false, driver, driver.getCurrentUrl());
        driver.findElement(By.xpath(xpathNotOnSaleItem)).click();

        // 4.Enter a random quantity between 2 and 5
        // 5.Select a different size
        int randomQuantity = 2 + (int)(Math.random() * 4);
        totalItemsInCard += randomQuantity;
        driver.findElement(By.id("quantity_wanted")).clear();
        driver.findElement(By.id("quantity_wanted")).sendKeys("" + randomQuantity + Keys.TAB + Keys.TAB
                + Keys.TAB + Keys.ARROW_DOWN);

        // 6.Click on add to cart
        driver.findElement(By.id("add_to_cart")).click();

        // 7.Verify confirmation message Product successfully added to your shopping cart
        try { Thread.sleep(1000); } catch (Exception e) { }
        totalPrice += Double.parseDouble(driver.findElement(By.id("layer_cart_product_price")).getText().substring(1));
        String successMessage = driver.findElement(By.xpath("//div[@id='layer_cart']//h2")).getText();
        assertEquals(successMessage, "Product successfully added to your shopping cart");

        // 8.Dismiss the confirmation window by clicking on the x icon
        driver.findElement(By.className("cross")).click();
    }

    @Test (priority = 2, groups = "add_to_cart", dependsOnMethods = "notOnSaleItem")
    public void onSaleItem() {
        // 9.Click on the company logo
        driver.findElement(By.xpath("//a[@title='My Store']")).click();

        // 10.Click on any product that is on sale
        String xpathOnSaleItem = BrowserUtils.xpathOfItemSaleStatus(false, driver, driver.getCurrentUrl());
        System.out.println(xpathOnSaleItem);
        driver.findElement(By.xpath(xpathOnSaleItem)).click();

        // 11.Enter a random quantity between 2 and 5
        // 12.Select a different size
        int randomQuantity = 2 + (int)(Math.random() * 4);
        totalItemsInCard += randomQuantity;
        driver.findElement(By.id("quantity_wanted")).clear();
        driver.findElement(By.id("quantity_wanted")).sendKeys("" + randomQuantity + Keys.TAB + Keys.TAB
                + Keys.TAB + Keys.ARROW_DOWN);

        // 13.Click on add to cart
        driver.findElement(By.id("add_to_cart")).click();

        // 14.Verify confirmation message Product successfully added to your shopping cart
        try { Thread.sleep(1000); } catch (Exception e) { }
        totalPrice += Double.parseDouble(driver.findElement(By.id("layer_cart_product_price")).getText().substring(1));
        String successMessage = driver.findElement(By.xpath("//div[@id='layer_cart']//h2")).getText();
        assertEquals(successMessage, "Product successfully added to your shopping cart");

        // 15.Dismiss the confirmation window by clicking on the x icon
        driver.findElement(By.className("cross")).click();
    }

    @Test (priority = 3, groups = "add_to_cart", dependsOnMethods = "onSaleItem")
    public void verifyCartContents() {
        driver.get("http://automationpractice.com/index.php");
        // 16.Hover over the Card icon
        actions = new Actions(driver);
        actions.moveToElement(driver.findElement(By.xpath("//a[@title='View my shopping cart']"))).build().perform();
        try { Thread.sleep(1000); } catch (Exception e) {}

        // 17.Verify that correct total is displayed
        // 18.Verify that total is correct based on the price and item count of the products you added to cart.
        //     *    (Shipping is always $2)
        double currentTotal = Double.parseDouble(driver.findElement(By.xpath("//span[@class='price cart_block_total ajax_block_cart_total']")).getText().substring(1));
        int currentTotalQuantity = Integer.parseInt(driver.findElement(By.xpath("//span[@class='ajax_cart_quantity']")).getText());

        // verify total amounts match
        assertEquals(currentTotal, totalPrice);

        // verify total quantities match
        assertEquals(currentTotalQuantity, totalItemsInCard);
    }
}
