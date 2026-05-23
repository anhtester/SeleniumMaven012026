package com.anhtester.Bai10_Annotations.DemoAutomation;

import org.openqa.selenium.By;
import org.testng.annotations.Test;

public class LoginTest3 extends BaseTest {

   @Test(priority = 1)
   public void testLoginSuccess() throws InterruptedException {
      System.out.println("Starting test: testLoginSuccess");

      driver.get("https://crm.anhtester.com/admin/authentication");
      driver.findElement(By.id("email")).sendKeys("admin@example.com");
      driver.findElement(By.name("password")).sendKeys("123456");
      driver.findElement(By.tagName("button")).click();

      System.out.println("End test: testLoginSuccess");
   }

   @Test(priority = 2)
   public void testLoginFailWithEmailInvalid() throws InterruptedException {
      System.out.println("Starting test: testLoginFailWithEmailInvalid");

      driver.get("https://crm.anhtester.com/admin/authentication");
      driver.findElement(By.id("email")).sendKeys("admin123@example.com");
      driver.findElement(By.name("password")).sendKeys("123456");
      driver.findElement(By.tagName("button")).click();

      System.out.println("End test: testLoginFailWithEmailInvalid");
   }

}
