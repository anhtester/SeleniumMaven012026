package com.anhtester.Bai9_TestNG;

import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.testng.annotations.*;

import java.time.Duration;

public class SeleniumTestNG {
   WebDriver driver;

   @BeforeMethod
   public void createDriver() {
      driver = new ChromeDriver();
      driver.manage().window().maximize();
      driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(10));
      driver.manage().timeouts().pageLoadTimeout(Duration.ofSeconds(30));
   }

   @Test
   public void testAnhTesterBlog() throws InterruptedException {
      driver.get("https://anhtester.com");
      driver.findElement(By.xpath("//a[normalize-space()='blog']")).click();
      Thread.sleep(2000);
   }

   @Test
   public void testLoginCRM() throws InterruptedException {
      driver.get("https://crm.anhtester.com/admin/authentication");
      Thread.sleep(2000);
      driver.findElement(By.id("email")).sendKeys("admin@example.com");
      Thread.sleep(1000);
      driver.findElement(By.name("password")).sendKeys("123456");
      Thread.sleep(1000);
      driver.findElement(By.tagName("button")).click();
      Thread.sleep(2000);
   }

   @AfterMethod
   public void closeDriver() {
      driver.quit();
   }
}