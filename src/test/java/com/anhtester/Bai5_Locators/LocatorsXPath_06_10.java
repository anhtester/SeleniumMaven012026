package com.anhtester.Bai5_Locators;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;

import java.time.Duration;

public class LocatorsXPath_06_10 {
   public static void main(String[] args) throws InterruptedException {

      WebDriver driver = new ChromeDriver();

      driver.manage().window().maximize();
      driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(10));

      driver.get("https://crm.anhtester.com/admin/authentication");
      Thread.sleep(1000);
      driver.findElement(By.xpath("//input[@type='email']")).sendKeys("admin@example.com");
      driver.findElement(By.xpath("//input[@type='password']")).sendKeys("123456");
      Thread.sleep(1000);
      driver.findElement(By.xpath("//button[normalize-space()='Login']")).click();
      Thread.sleep(2000);
      driver.findElement(By.xpath("//span[normalize-space()='Customers']")).click();
      driver.findElement(By.xpath("//a[normalize-space()='New Customer']")).click();
      driver.findElement(By.xpath("//input[@id='company']")).sendKeys("AN_TEST_1704A1");
      driver.findElement(By.xpath("//input[@id='vat']")).sendKeys("10");
      driver.findElement(By.xpath("//input[@id='phonenumber']")).sendKeys("0939206009");
      driver.findElement(By.id("website")).sendKeys("https://anhtester.com");
      driver.findElement(By.xpath("//button[@data-id='groups_in[]']")).click();
      Thread.sleep(1000);
      driver.findElement(By.xpath("//button[@data-id='groups_in[]']/following-sibling::div//input")).sendKeys("VIP");
      Thread.sleep(2000);
      driver.findElement(By.xpath("//span[normalize-space()='VIP']")).click();
      Thread.sleep(1000);
      driver.findElement(By.xpath("//button[@data-id='groups_in[]']")).click();
      driver.findElement(By.xpath("//textarea[@id='address']")).sendKeys("123 Nguyen Van Linh, Da Nang");
      driver.findElement(By.xpath("//input[@id='city']")).sendKeys("Da Nang");
      driver.findElement(By.xpath("//input[@id='state']")).sendKeys("Da Nang");
      driver.findElement(By.id("zip")).sendKeys("550000");
      Thread.sleep(1000);
      driver.findElement(By.xpath("//button[@data-id='country']")).click();
      Thread.sleep(1000);
      driver.findElement(By.xpath("//button[@data-id='country']/following-sibling::div//input")).sendKeys("Vietnam");
      Thread.sleep(1000);
      driver.findElement(By.xpath("//span[normalize-space()='Vietnam']")).click();
      Thread.sleep(3000);

      driver.quit();
   }
}
