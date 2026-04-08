package com.anhtester.Bai5_Locators;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;

import java.time.Duration;

public class LocatorsHTML {
   public static void main(String[] args) throws InterruptedException {

      WebDriver driver = new ChromeDriver();

      driver.manage().window().maximize();
      driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(10));

      driver.get("https://crm.anhtester.com/admin/authentication");
      Thread.sleep(2000);
      driver.findElement(By.id("email")).sendKeys("admin@example.com");
      Thread.sleep(1000);
      driver.findElement(By.name("password")).sendKeys("123456");
      Thread.sleep(1000);
      driver.findElement(By.tagName("button")).click();
      Thread.sleep(2000);
      driver.quit();

   }
}
