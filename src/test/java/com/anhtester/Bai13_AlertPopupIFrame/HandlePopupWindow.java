package com.anhtester.Bai13_AlertPopupIFrame;

import com.anhtester.common.BaseTest;
import org.openqa.selenium.By;
import org.openqa.selenium.WindowType;
import org.testng.annotations.Test;

import java.util.Set;

public class HandlePopupWindow extends BaseTest {
   @Test
   public void testOpenNewTab() {
      driver.get("https://anhtester.com");
      sleep(2);
      driver.switchTo().newWindow(WindowType.TAB);
      sleep(2);
      driver.get("https://google.com");
      sleep(2);
   }

   @Test
   public void testOpenNewWindow() {
      driver.get("https://anhtester.com");
      sleep(2);
      driver.switchTo().newWindow(WindowType.WINDOW);
      sleep(1);
      driver.get("https://facebook.com/anhtester");
      sleep(3);
   }

   @Test
   public void demoNotSwitchToTab() throws InterruptedException {
      driver.get("https://demoqa.com/browser-windows");
      Thread.sleep(2000);
      driver.findElement(By.xpath("//button[@id='tabButton']")).click();
      Thread.sleep(1000);
      //Sau khi chuyển hướng sang Tab mới thì getText cái header
      System.out.println(driver.findElement(By.xpath("//h1[@id='sampleHeading']")).getText());
      Thread.sleep(1000);
   }

   @Test
   public void demoSwitchToTab() throws InterruptedException {
      driver.get("https://demoqa.com/browser-windows");
      Thread.sleep(2000);
      driver.findElement(By.xpath("//button[@id='tabButton']")).click();
      Thread.sleep(1000);

      Set<String> windows = driver.getWindowHandles();
      String firstWindow = (String)windows.toArray()[0]; //Cửa sổ đầu
      String secondWindow = (String)windows.toArray()[1]; //Cửa sổ thứ hai
      System.out.println(firstWindow);
      System.out.println(secondWindow);

      driver.switchTo().window(secondWindow);

      //Sau khi chuyển hướng sang Tab mới thì getText cái header
      System.out.println(driver.findElement(By.xpath("//h1[@id='sampleHeading']")).getText());
      Thread.sleep(1000);

      driver.close(); //Đóng tab hiện tại

      //Chuyển về cửa sổ thứ nhất
      driver.switchTo().window(firstWindow);

      System.out.println(driver.findElement(By.xpath("//h1[normalize-space()='Browser Windows']")).getText());

      Thread.sleep(1000);
   }

}
