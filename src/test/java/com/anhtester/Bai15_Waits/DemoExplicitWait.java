package com.anhtester.Bai15_Waits;

import com.anhtester.common.BaseTest;
import com.anhtester.keywords.WebUI;
import org.openqa.selenium.By;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.annotations.Test;

import java.time.Duration;

public class DemoExplicitWait extends BaseTest {
   @Test
   public void testExplicitWait(){
      driver.get("https://hrm.anhtester.com/erp/login");

      //WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(5));

      //wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("iusername")));
      //driver.findElement(By.id("iusername")).sendKeys("admin_example");

      WebUI.setText(driver, By.id("iusername"), "admin_example", 3);

      //wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("ipassword")));
      //driver.findElement(By.id("ipassword")).sendKeys("123456");
      WebUI.setText(driver, By.id("ipassword"), "123456");

//      wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//button[@data-style='expand-right']")));
//      driver.findElement(By.xpath("//button[@data-style='expand-right']")).click();

      WebUI.clickElement(driver, By.xpath("//button[@data-style='expand-right']"), 3);

      // Kiểm tra xem element có xuất hiện hay không
//      WebDriverWait  wait2 = new WebDriverWait(driver, Duration.ofSeconds(7));
//      wait2.until(ExpectedConditions.presenceOfElementLocated(By.xpath("//span[normalize-space()='Home']")));

      boolean isElementPresent = WebUI.isElementPresent(driver, By.xpath("//span[normalize-space()='Home']"), 3);
      Assert.assertTrue(isElementPresent, "Login Fail. The element 'Home' menu is not present.");
   }
}
