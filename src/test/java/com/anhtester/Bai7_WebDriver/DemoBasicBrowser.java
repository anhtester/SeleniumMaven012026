package com.anhtester.Bai7_WebDriver;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;

import java.time.Duration;
import java.util.List;

public class DemoBasicBrowser {
   public static void main(String[] args) throws InterruptedException {

      WebDriver driver = new ChromeDriver();

      driver.manage().window().maximize();
      driver.manage().timeouts().pageLoadTimeout(Duration.ofSeconds(30));
      driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(10));

      // Navigate to a page
      driver.navigate().to("https://anhtester.com");

      // Điều hướng về lịch sử trang trước đó
      driver.navigate().back();

      // Làm mới trang hiện tại
      driver.navigate().refresh();

      // Điều hướng đến trang tiếp sau
      driver.navigate().forward();

      // Get the title of the page
      String title = driver.getTitle();
      System.out.println("Page title: " + title);

      // Get the current URL
      String url = driver.getCurrentUrl();
      System.out.println("Page URL: " + url);

      // Get the current page HTML source
      String html = driver.getPageSource();
      System.out.println("Page HTML: " + html);

      System.out.println(html.contains("<button type=\"button\""));

      driver.quit(); //Tắt thoát trình duyệt

   }
}
