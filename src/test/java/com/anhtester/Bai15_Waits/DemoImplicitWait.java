package com.anhtester.Bai15_Waits;

import com.anhtester.common.BaseTest;
import com.anhtester.keywords.WebUI;
import org.openqa.selenium.By;
import org.testng.Assert;
import org.testng.annotations.Test;

import java.time.Duration;

public class DemoImplicitWait extends BaseTest {

   @Test
   public void testImplicitWait(){
      driver.get("https://hrm.anhtester.com/erp/login");

      driver.findElement(By.id("iusername")).sendKeys("admin_example");

      driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(10));
      driver.findElement(By.id("ipassword")).sendKeys("123456");

      driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(0));

      driver.findElement(By.xpath("//button[@data-style='expand-right123']")).click();

      // Kiểm tra xem element có xuất hiện hay không
      boolean isElementPresent = WebUI.isElementPresent(driver, By.xpath("//span[normalize-space()='Home']"));
      Assert.assertTrue(isElementPresent, "Login Fail. The element 'Home' menu is not present.");
   }

}
