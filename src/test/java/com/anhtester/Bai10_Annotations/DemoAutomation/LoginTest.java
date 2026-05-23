package com.anhtester.Bai10_Annotations.DemoAutomation;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import java.time.Duration;

public class LoginTest {

   WebDriver driver;

   @BeforeClass
   public void setUp() {
      System.out.println("Setting up the test environment...");
      driver = new ChromeDriver();
      driver.manage().window().maximize();
      driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(10));
      System.out.println("Running with Chrome driver...");
   }

   @AfterClass
   public void tearDown() {
      driver.quit();
      System.out.println("Closed the Chrome driver.");
   }

   @Test(priority = 1)
   public void testLoginSuccess() throws InterruptedException {
      System.out.println("Starting test: testLoginSuccess");

      driver.get("https://crm.anhtester.com/admin/authentication");
      driver.findElement(By.id("email")).sendKeys("admin123@example.com");
      driver.findElement(By.name("password")).sendKeys("123456");
      driver.findElement(By.tagName("button")).click();

      System.out.println("End test: testLoginSuccess");
   }

   @Test(priority = 2)
   public void testLoginFailWithEmailInvalid() throws InterruptedException {
      System.out.println("Starting test: testLoginFailWithEmailInvalid");

      System.out.println("Đoạn logout trước");
      driver.findElement(By.xpath("//li[contains(@class,'user-profile')]")).click();
      driver.findElement(By.linkText("Logout")).click();

      driver.get("https://crm.anhtester.com/admin/authentication");
      driver.findElement(By.id("email")).sendKeys("admin123@example.com");
      driver.findElement(By.name("password")).sendKeys("123456");
      driver.findElement(By.tagName("button")).click();

      System.out.println("End test: testLoginFailWithEmailInvalid");
   }

}
