package com.anhtester.Bai7_WebDriver;

import org.openqa.selenium.Cookie;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WindowType;
import org.openqa.selenium.chrome.ChromeDriver;

import java.time.Duration;

public class DemoCookiesBrowser {
   public static void main(String[] args) throws InterruptedException {

      WebDriver driver = new ChromeDriver();

      driver.manage().window().maximize();
      driver.manage().timeouts().pageLoadTimeout(Duration.ofSeconds(30));
      driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(10));

      driver.get("https://crm.anhtester.com/admin/authentication");

      // Add a new cookie
      Cookie newCookie = new Cookie("sp_session", "f53820cf7e34923d4f419a1a96545c9219049974");
      driver.manage().addCookie(newCookie);

      //driver.get("https://crm.anhtester.com/admin/authentication");
      driver.navigate().refresh();

      //Kiểm tra phải đi đến trang Dashboard

      Thread.sleep(2000);
      driver.quit(); //Tắt thoát trình duyệt

   }
}
