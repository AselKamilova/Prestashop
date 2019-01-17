package com.prestashop.tests.smoke_tests;
import io.github.bonigarcia.wdm.WebDriverManager;
import org.openqa.selenium.By;
import org.openqa.selenium.NotFoundException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;

import java.util.concurrent.TimeUnit;
import java.util.*;
public class BrowserUtils {
    public static boolean verifyTextMatches(String str1, String str2) {
        return str1.equals(str2);
    }

    public static boolean verifyTextContains(String str1, String str2) {
        return str1.contains(str2);
    }

    /**
     *  This utility is looping through the URL to find items on or off sale
     *  per the presence of WebElements specific for items on sale.
     * @param isOnSale => do you want to find an item on sale or else
     * @param driver => pass in WebDriver element
     * @param URL => pass URL (this utility is tested to work for 'http://automationpractice.com/index.php'
     * @return => xpath for the element per parameters above
     */
    public static String xpathOfItemSaleStatus(boolean isOnSale, WebDriver driver, String URL) {
        driver.manage().timeouts().implicitlyWait(3, TimeUnit.SECONDS);
        driver.get(URL);

        List<WebElement> featuredItems = driver.findElements(By.xpath("//ul[@id='homefeatured']/li"));

        for (int i = 0; i < featuredItems.size(); i++) {
            String xpathForEachElement = "//ul[@id='homefeatured']/li[" + (i+1) + "]";

            try {
                driver.findElement(By.xpath(xpathForEachElement + "//span[@class='old-price product-price']"));
                if (isOnSale) return xpathForEachElement;
                else continue;
            }catch (Exception e) {
                if (isOnSale) continue;
                else return xpathForEachElement;
            }

        }

        System.out.println("Failure within utility 'xpathOfItemSalesStatus': this utility is valid of automationpractice.com."
                + "\nPlease verify your URL input.");
        throw new NotFoundException();
    }

}
