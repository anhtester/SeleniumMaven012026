package com.anhtester;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;

import java.time.Duration;

public class DemoSelenium {
   public static void main(String[] args) throws InterruptedException {

      WebDriver driver = new ChromeDriver();

      driver.manage().window().maximize();
      driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(10));
      driver.manage().timeouts().pageLoadTimeout(Duration.ofSeconds(30));

      driver.get("https://crm.anhtester.com/admin/authentication");

      driver.findElement(By.xpath("//input[@id='email']")).sendKeys("admin@example.com");
      driver.findElement(By.xpath("//input[@id='password']")).sendKeys("123456");
      driver.findElement(By.xpath("//button[normalize-space()='Login']")).click();

      Thread.sleep(2000);
      driver.quit();

   }
}
