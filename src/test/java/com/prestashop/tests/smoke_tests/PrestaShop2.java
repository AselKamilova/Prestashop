package com.prestashop.tests.smoke_tests;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.Select;
import org.testng.Assert;
import org.testng.annotations.Ignore;
import org.testng.annotations.Test;

import java.util.Random;
import java.util.concurrent.TimeUnit;
public class PrestaShop2 extends TestBase {
    WebElement firstName;
    WebElement lastName;

    @Ignore
    @Test (priority = 1)
    public void Registration() throws InterruptedException {

        driver.get("http://automationpractice.com/index.php");
        driver.manage().window().fullscreen();

        WebElement signInButton = driver.findElement(By.xpath("//a[@class='login']"));
        signInButton.click();

        WebElement emailAddress = driver.findElement(By.id("email_create"));
        emailAddress.sendKeys("aiaz_khalili@hotmail.com");
        String currentEmail = "aiaz_khalili@hotmail.com";

        WebElement creatAcount = driver.findElement(By.xpath("//button[@class='btn btn-default button button-medium exclusive']"));
        creatAcount.click();

        Thread.sleep(3000);
        WebElement emailLink = driver.findElement(By.xpath("//input[@id='email']"));
        String emailLinkText = emailLink.getAttribute("value");

        Assert.assertTrue(currentEmail.equals(emailLinkText));

        WebElement title = driver.findElement(By.id("id_gender1"));
        title.click();

        firstName = driver.findElement(By.id("customer_firstname"));
        firstName.sendKeys("Nematullah");

        lastName = driver.findElement(By.id("customer_lastname"));
        lastName.sendKeys("Khalili");

        WebElement password = driver.findElement(By.id("passwd"));
        password.sendKeys("cybertek786");

        WebElement day = driver.findElement(By.id("days"));
        day.click();

        Select select1 = new Select(day);

        select1.selectByIndex(3);

        WebElement month = driver.findElement(By.id("months"));
        month.click();

        Select select2 = new Select(month);

        select2.selectByIndex(5);

        WebElement year = driver.findElement(By.id("years"));
        year.click();

        Select select3 = new Select(year);

        select3.selectByIndex(34);

        WebElement signUp = driver.findElement(By.id("newsletter"));
        signUp.click();

        WebElement company = driver.findElement(By.id("company"));
        company.sendKeys("Cybertek");

        WebElement streetAddress = driver.findElement(By.id("address1"));
        streetAddress.sendKeys("4028 Brussels Way");

        WebElement city = driver.findElement(By.id("city"));
        city.sendKeys("Woodbridge");

        WebElement state = driver.findElement(By.xpath("//select[@id='id_state']"));
        state.click();

        Select select4 = new Select(state);

        select4.selectByIndex(49);

        WebElement zipcode = driver.findElement(By.id("postcode"));
        zipcode.sendKeys("22192");

        WebElement mobileNumber = driver.findElement(By.id("phone_mobile"));
        mobileNumber.sendKeys("571-331-6767");

        WebElement regButton = driver.findElement(By.xpath("//button[@id='submitAccount']"));
        regButton.click();
    }

    @Ignore
    @Test(priority = 2)
    public void personalInfoTest() throws InterruptedException {

        driver.get("http://automationpractice.com/index.php");
        driver.manage().window().fullscreen();

        WebElement signInButton = driver.findElement(By.cssSelector("a[class='login']"));
        signInButton.click();

        WebElement email = driver.findElement(By.id("email"));
        email.sendKeys("aiaz_khalili@hotmail.com");
        WebElement password = driver.findElement(By.id("passwd"));
        password.sendKeys("cybertek786");

        WebElement signIn = driver.findElement(By.xpath("//button[@id='SubmitLogin']"));
        signIn.click();

        Thread.sleep(3000);
        WebElement checkFullName = driver.findElement(By.xpath("//span[.='Nematullah Khalili']"));
        String fullName = checkFullName.getText();
        System.out.println("Complete Name: " + fullName);

        // Assert.assertTrue(firstName.concat(" ").concat(lastName).equals(fullName));
        //  Assert.assertTrue((firstName+" "+lastName).contains(fullName));

        WebElement personalInformation = driver.findElement(By.xpath("//span[.='My personal information']"));
        personalInformation.click();

        WebElement diplayCheck = driver.findElement(By.cssSelector("h1[class='page-subheading']"));
        String diplayText = diplayCheck.getText();
        System.out.println("Personal Information Text: " + diplayText);

        Assert.assertTrue(diplayText.equals("YOUR PERSONAL INFORMATION"));

        WebElement backToAccount = driver.findElement(By.xpath("//i[@class='icon-chevron-left']"));
        backToAccount.click();

        WebElement addressInfo = driver.findElement(By.xpath("//span[.='My addresses']"));
        addressInfo.click();

        WebElement signOutButton = driver.findElement(By.cssSelector("a[class='logout']"));
        signOutButton.click();

    }

    @Test (priority = 3)
    public void cartDetails() throws InterruptedException {

        driver.get("http://automationpractice.com/index.php");
        driver.manage().window().fullscreen();
        driver.manage().timeouts().implicitlyWait(5, TimeUnit.SECONDS);

        WebElement printedDress = driver.findElement(By.xpath("(//img[@class='replace-2x img-responsive'])[4]"));
        printedDress.click();

        WebElement iframe = driver.findElement(By.xpath("//iframe[@class='fancybox-iframe']"));
        driver.switchTo().frame(iframe);

        Thread.sleep(2000);

        WebElement quantityNumber = driver.findElement(By.xpath("//p[@id='quantity_wanted_p']/a[2]"));

        Random random = new Random();

        int temp = random.nextInt(3);

        switch (temp){

            case 0:
                quantityNumber.click();
                break;
            case 1:
                quantityNumber.click();
                quantityNumber.click();
                break;
            case 2:
                quantityNumber.click();
                quantityNumber.click();
                quantityNumber.click();
                break;
            case 3:
                quantityNumber.click();
                quantityNumber.click();
                quantityNumber.click();
                quantityNumber.click();
                break;
        }

        Thread.sleep(2000);
    }

}
