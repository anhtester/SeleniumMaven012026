package com.anhtester.Bai13_AlertPopupIFrame;

import com.anhtester.common.BaseTest;
import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.interactions.Actions;
import org.testng.annotations.Test;

public class HandleIFrame extends BaseTest {
   @Test
   public void demoHandleIFrame() throws InterruptedException {

      driver.navigate().to("https://www.lambdatest.com/selenium-playground/iframe-demo/");
      Thread.sleep(3000);
      System.out.println("IFrame total: " + driver.findElements(By.tagName("iframe")).size());

      //----Switch to content of iframe (Editor)--------
      //driver.switchTo().frame(0); //Thẻ iframe thứ nhất
      driver.switchTo().frame("iFrame1");
      Thread.sleep(1000);
      System.out.println(driver.findElement(By.xpath("//div[@class='rsw-ce']")).getText());

      Actions actions = new Actions(driver);
      driver.findElement(By.xpath("//div[@class='rsw-ce']")).click(); //Click vào nội dung trong iframe
      actions.keyDown(Keys.CONTROL).sendKeys("a").keyUp(Keys.CONTROL).perform(); //Bôi đen toàn bộ nội dung trong iframe
      actions.click(driver.findElement(By.xpath("//button[@title='Bold']"))).perform(); //Click vào nội dung trong iframe
      //1. Switch to Parent WindowHandle
      driver.switchTo().parentFrame(); //Chuyển về nội dung chính không thuộc iframe nào
      Thread.sleep(2000);
      //2. Switch to iframe icon of Messenger
      driver.switchTo().frame(1); //Thẻ iframe thứ hai
      driver.findElement(By.xpath("//a[normalize-space()='API Reference']")).click(); //Nhấn icon để ẩn messenger chat đi

      Thread.sleep(1000);
   }

}