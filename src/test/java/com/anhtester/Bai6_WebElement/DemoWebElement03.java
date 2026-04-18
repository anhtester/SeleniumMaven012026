package com.anhtester.Bai6_WebElement;

import org.openqa.selenium.*;
import org.openqa.selenium.chrome.ChromeDriver;

import java.time.Duration;

public class DemoWebElement03 {

   public static boolean isElementVisible(WebDriver driver, By by) {
      try {
         return driver.findElement(by).isDisplayed();
      } catch (NoSuchElementException e) {
         return false;
      }
   }

   public static void main(String[] args) throws InterruptedException {

      WebDriver driver = new ChromeDriver();

      driver.manage().window().maximize();
      driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(10));

      driver.get("https://crm.anhtester.com/admin/authentication");
      Thread.sleep(1000);
      driver.findElement(By.xpath("//input[@type='email']")).sendKeys("admin@example.com");
      driver.findElement(By.xpath("//input[@type='password']")).sendKeys("123456");
      Thread.sleep(1000);

      //getCssValue
      WebElement loginButton = driver.findElement(By.xpath("//button[normalize-space()='Login']"));
      System.out.println(loginButton.getCssValue("background-color"));
      System.out.println(loginButton.getCssValue("font-size"));
      System.out.println(loginButton.getCssValue("font-family"));

      Dimension dimensions = loginButton.getSize();
      System.out.println("Height: " + dimensions.getHeight() + " Width: " + dimensions.getWidth());

      Point point = loginButton.getLocation();
      System.out.println("X: " + point.getX() + " Y: " + point.getY());

      //driver.findElement(By.xpath("//input[@type='password']")).submit();
      driver.findElement(By.xpath("//input[@type='password']")).sendKeys(Keys.ENTER);
      //driver.findElement(By.xpath("//button[normalize-space()='Login']")).click();
      Thread.sleep(1000);

      String InvoicesAwaitingPaymentTotal = driver.findElement(By.xpath("//div[normalize-space()='Invoices Awaiting Payment']/following-sibling::span")).getText();
      System.out.println("InvoicesAwaitingPaymentTotal: " + InvoicesAwaitingPaymentTotal);

      driver.findElement(By.xpath("//span[normalize-space()='Customers']")).click();
      Thread.sleep(2000);
      System.out.println(driver.findElement(By.xpath("//table[@id='clients']/tbody//tr[1]/td[3]/a")).getText());
      Thread.sleep(1000);
      driver.findElement(By.xpath("//table[@id='clients']/tbody//tr[1]/td[3]/a")).click();

      Thread.sleep(2000);

      //getAttribute
      System.out.println(driver.findElement(By.xpath("//input[@id='company']")).getAttribute("value"));
      System.out.println(driver.findElement(By.xpath("//input[@id='vat']")).getAttribute("value"));
      System.out.println(driver.findElement(By.xpath("//input[@id='phonenumber']")).getAttribute("value"));
      System.out.println(driver.findElement(By.xpath("//input[@id='website']")).getAttribute("value"));
      System.out.println(driver.findElement(By.xpath("//button[@data-id='groups_in[]']")).getText());
      System.out.println(driver.findElement(By.xpath("//textarea[@id='address']")).getText());

      driver.quit();

   }
}
