package com.anhtester.Bai8_Checkbox_Radio_Dropdown;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;

import java.time.Duration;
import java.util.List;

public class HandleRadio {
   public static void main(String[] args) throws InterruptedException {
      WebDriver driver = new ChromeDriver();

      driver.manage().window().maximize();
      driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(10));

      driver.get("https://crm.anhtester.com/admin/authentication");
      driver.findElement(By.id("email")).sendKeys("admin@example.com");
      driver.findElement(By.name("password")).sendKeys("123456");
      driver.findElement(By.tagName("button")).click();

      driver.findElement(By.xpath("//span[@class='menu-text'][normalize-space()='Sales']")).click();
      driver.findElement(By.xpath("//span[normalize-space()='Proposals']")).click();
      driver.findElement(By.xpath("//a[normalize-space()='New Proposal']")).click();
      Thread.sleep(2000);
      //Cuộn chuột đến cuối trang
      JavascriptExecutor js = (JavascriptExecutor) driver;
      js.executeScript("window.scrollTo(0, document.body.scrollHeight)");

      //Kiểm tra giá trị Radio select hay chưa
      boolean radioHours = driver.findElement(By.xpath("//label[normalize-space()='Hours']/preceding-sibling::input")).isSelected();
      System.out.println("Radio Hours is selected: " + radioHours);

      //Select Radio
      driver.findElement(By.xpath("//label[normalize-space()='Hours']")).click();

      //Kiểm tra giá trị Radio select hay chưa
      boolean radioHoursAfterSelected = driver.findElement(By.xpath("//label[normalize-space()='Hours']/preceding-sibling::input")).isSelected();
      System.out.println("Radio Hours AFTER is selected: " + radioHoursAfterSelected);

      //Kiểm tra tất cả giá trị của Radio
      List<WebElement> listRadio = driver.findElements(By.xpath("//div[contains(@class,'radio radio-primary')]//input"));

      int count = 0;
      for (int i = 0; i < listRadio.size(); i++){
         System.out.println("Giá trị của Radio thứ " + (i+1) + " là: " + listRadio.get(i).isSelected());
         if(listRadio.get(i).isSelected()){
            count++;
         }
      }

      System.out.println("Tổng số giá trị TRUE tại Radio là: " + count);

      if(count != 1){
         System.out.println("Lỗi. Có 2 giá trị TRUE tại Radio.");
      }else {
         System.out.println("Đúng. Chỉ có 1 giá trị TRUE tại Radio.");
      }

      Thread.sleep(2000);
      driver.quit();
   }
}
