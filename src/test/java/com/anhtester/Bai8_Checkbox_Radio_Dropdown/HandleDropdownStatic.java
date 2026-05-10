package com.anhtester.Bai8_Checkbox_Radio_Dropdown;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.Select;

import java.time.Duration;
import java.util.List;

public class HandleDropdownStatic {
   public static void main(String[] args) throws InterruptedException {
      WebDriver driver = new ChromeDriver();

      driver.manage().window().maximize();
      driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(10));

      driver.get("https://crm.anhtester.com/admin/authentication");
      driver.findElement(By.id("email")).sendKeys("admin@example.com");
      driver.findElement(By.name("password")).sendKeys("123456");
      driver.findElement(By.tagName("button")).click();

      driver.findElement(By.xpath("//span[normalize-space()='Customers']")).click();
      Thread.sleep(2000);

      WebElement selectElement = driver.findElement(By.xpath("//select[@name='clients_length']"));

      Select select = new Select(selectElement);

      select.selectByVisibleText("50");
      Thread.sleep(2000);
      select.selectByValue("-1"); //All
      Thread.sleep(2000);
      select.selectByIndex(1); //25

      System.out.println(select.getFirstSelectedOption().getText());

      System.out.println("Check multiple select: " + select.isMultiple());

      Thread.sleep(2000);
      driver.quit();
   }
}
