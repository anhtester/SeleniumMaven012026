package com.anhtester.Bai7_WebDriver;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.safari.SafariDriver;

import java.time.Duration;
import java.util.List;

public class ListWebElement_FindElements {
   public static void main(String[] args) throws InterruptedException {

      WebDriver driver = new ChromeDriver();

      driver.manage().window().maximize();
      driver.manage().timeouts().pageLoadTimeout(Duration.ofSeconds(30));
      driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(10));

      driver.get("https://crm.anhtester.com/admin/authentication");

      driver.findElement(By.xpath("//input[@id='email']")).sendKeys("admin@example.com");
      driver.findElement(By.xpath("//input[@id='password']")).sendKeys("123456");
      driver.findElement(By.xpath("//button[normalize-space()='Login']")).click();

      Thread.sleep(2000);

      List<WebElement> listMenu = driver.findElements(By.xpath("//ul[@id='side-menu']/li[contains(@class, 'menu-item')]"));

      //Duyệt phần tử trong list WebElement
      for (WebElement menu : listMenu) {
         System.out.println(menu.getText());
      }

      //Click xổ menu Sales để text xuất hiện, sau đó mới get list menu
      driver.findElement(By.xpath("//span[normalize-space()='Sales' and @class='menu-text']")).click();
      Thread.sleep(2000);

      //Get sub menu of Sales menu item
      List<WebElement> listSubMenuSales = driver.findElements(By.xpath("(//span[normalize-space()='Sales' and @class='menu-text']/parent::a)/following-sibling::ul/li//span"));
      System.out.println(listSubMenuSales.size());
      for (WebElement subMenu : listSubMenuSales) {
         System.out.println(subMenu.getText());
      }

      driver.quit(); //Tắt thoát trình duyệt

   }
}
