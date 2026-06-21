package com.anhtester.Bai16_ThucHanh;

import com.anhtester.common.BaseTest;
import com.anhtester.keywords.WebUI;
import com.anhtester.locators.LocatorsCRM;
import org.openqa.selenium.By;
import org.testng.Assert;
import org.testng.annotations.Test;

public class ThucHanhLoginCRM extends BaseTest {

    @Test(priority = 1)
    public void testLoginCRM_Success(){
        driver.get("https://crm.anhtester.com/admin/authentication");
        WebUI.setText(driver, LocatorsCRM.inputEmail, "admin@example.com", 10);
        WebUI.setText(driver, LocatorsCRM.inputPassword, "123456");
        WebUI.clickElement(driver, By.xpath("//button[normalize-space()='Login']"));

        boolean checkDashboardMenu = WebUI.isElementPresent(driver, LocatorsCRM.menuDashboard, 5);
        Assert.assertTrue(checkDashboardMenu, "Login Fail. Dashboard Menu is not present");
    }

    @Test(priority = 2)
    public void testLoginFailWithEmailInvalid(){
        driver.get("https://crm.anhtester.com/admin/authentication");
        WebUI.setText(driver, LocatorsCRM.inputEmail, "admin123@example.com", 10);
        WebUI.setText(driver, LocatorsCRM.inputPassword, "123456");
        WebUI.clickElement(driver, By.xpath("//button[normalize-space()='Login']"));

        boolean checkAlertErrorMessage = WebUI.isElementPresent(driver, LocatorsCRM.alertErrorMessage, 5);
        Assert.assertTrue(checkAlertErrorMessage, "Fail. The Alert Error Message is not present");

        Assert.assertEquals(driver.findElement(LocatorsCRM.alertErrorMessage).getText(), "Invalid email or password");

    }

    @Test(priority = 3)
    public void testLoginFailWithPasswordInvalid(){
        driver.get("https://crm.anhtester.com/admin/authentication");
        WebUI.setText(driver, LocatorsCRM.inputEmail, "admin@example.com", 10);
        WebUI.setText(driver, LocatorsCRM.inputPassword, "123");
        WebUI.clickElement(driver, By.xpath("//button[normalize-space()='Login']"));

        boolean checkAlertErrorMessage = WebUI.isElementPresent(driver, LocatorsCRM.alertErrorMessage, 5);
        Assert.assertTrue(checkAlertErrorMessage, "Fail. The Alert Error Message is not present");

        Assert.assertEquals(driver.findElement(LocatorsCRM.alertErrorMessage).getText(), "Invalid email or password");

    }

    @Test(priority = 4)
    public void testLoginFailWithEmailNull(){
        driver.get("https://crm.anhtester.com/admin/authentication");
        WebUI.setText(driver, LocatorsCRM.inputEmail, "", 10);
        WebUI.setText(driver, LocatorsCRM.inputPassword, "123456");
        WebUI.clickElement(driver, By.xpath("//button[normalize-space()='Login']"));

        boolean checkAlertErrorMessage = WebUI.isElementPresent(driver, LocatorsCRM.alertErrorMessage, 5);
        Assert.assertTrue(checkAlertErrorMessage, "Fail. The Alert Error Message is not present");

        Assert.assertEquals(driver.findElement(LocatorsCRM.alertErrorMessage).getText(), "The Email Address field is required.");

    }

    @Test(priority = 5)
    public void testLoginFailWithPasswordNull(){
        driver.get("https://crm.anhtester.com/admin/authentication");
        WebUI.setText(driver, LocatorsCRM.inputEmail, "admin@example.com", 10);
        WebUI.setText(driver, LocatorsCRM.inputPassword, "");
        WebUI.clickElement(driver, By.xpath("//button[normalize-space()='Login']"));

        boolean checkAlertErrorMessage = WebUI.isElementPresent(driver, LocatorsCRM.alertErrorMessage, 5);
        Assert.assertTrue(checkAlertErrorMessage, "Fail. The Alert Error Message is not present");

        Assert.assertEquals(driver.findElement(LocatorsCRM.alertErrorMessage).getText(), "The Password field is required.");

    }

    @Test(priority = 6)
    public void testLoginFailWithEmailAndPasswordNull(){
        driver.get("https://crm.anhtester.com/admin/authentication");
        WebUI.setText(driver, LocatorsCRM.inputEmail, "", 10);
        WebUI.setText(driver, LocatorsCRM.inputPassword, "");
        WebUI.clickElement(driver, By.xpath("//button[normalize-space()='Login']"));

        boolean checkEmailErrorMessage = WebUI.isElementPresent(driver, LocatorsCRM.alertEmailRequiredMessage, 5);
        Assert.assertTrue(checkEmailErrorMessage, "Fail. The Email Error Message is not present");

        boolean checkPasswordErrorMessage = WebUI.isElementPresent(driver, LocatorsCRM.alertPasswordRequiredMessage, 5);
        Assert.assertTrue(checkPasswordErrorMessage, "Fail. The Password Error Message is not present");

        Assert.assertEquals(driver.getCurrentUrl(), "https://crm.anhtester.com/admin/authentication", "The Current URL is not correct");

    }

    @Test(priority = 7)
    public void testLoginFailWithEmailFormatInvalid_01(){
        driver.get("https://crm.anhtester.com/admin/authentication");
        WebUI.setText(driver, LocatorsCRM.inputEmail, "admin@", 10);
        WebUI.setText(driver, LocatorsCRM.inputPassword, "123456");
        WebUI.clickElement(driver, By.xpath("//button[normalize-space()='Login']"));

        //Handle HTML5 validation message
        //https://anhtester.com/blog/how-to-get-html5-validation-message-with-selenium-b654.html
        String emailMessage = driver.findElement(LocatorsCRM.inputEmail).getAttribute("validationMessage");
        System.out.println(emailMessage);

        Assert.assertEquals(emailMessage, "Please enter a part following '@'. 'admin@' is incomplete.", "Fail. The HTML5 Error Message is not match.");
    }

    @Test(priority = 8)
    public void testLoginFailWithEmailFormatInvalid_02(){
        driver.get("https://crm.anhtester.com/admin/authentication");
        WebUI.setText(driver, LocatorsCRM.inputEmail, "admin@exam", 10);
        WebUI.setText(driver, LocatorsCRM.inputPassword, "123456");
        WebUI.clickElement(driver, By.xpath("//button[normalize-space()='Login']"));

        boolean checkAlertErrorMessage = WebUI.isElementPresent(driver, LocatorsCRM.alertErrorMessage, 5);
        Assert.assertTrue(checkAlertErrorMessage, "Fail. The Alert Error Message is not present");

        Assert.assertEquals(driver.findElement(LocatorsCRM.alertErrorMessage).getText(), "The Email Address field must contain a valid email address.");

    }

}
