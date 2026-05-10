package com.anhtester.Bai8_Checkbox_Radio_Dropdown;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;

import java.time.Duration;
import java.util.List;

public class HandleCheckbox {
   public static void main(String[] args) throws InterruptedException {
      WebDriver driver = new ChromeDriver();

      driver.manage().window().maximize();
      driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(10));

      driver.get("https://crm.anhtester.com/admin/authentication");
      driver.findElement(By.id("email")).sendKeys("admin@example.com");
      driver.findElement(By.name("password")).sendKeys("123456");
      driver.findElement(By.tagName("button")).click();

      driver.findElement(By.xpath("//span[normalize-space()='Tasks']")).click();
      driver.findElement(By.xpath("//a[normalize-space()='New Task']")).click();

      boolean checkboxPublic = driver.findElement(By.xpath("//input[@id='task_is_public']")).isSelected();
      System.out.println("Checkbox Public is selected: " + checkboxPublic);

      boolean checkboxBillable = driver.findElement(By.xpath("//input[@id='task_is_billable']")).isSelected();
      System.out.println("Checkbox Billable is selected: " + checkboxBillable);

      //Click chọn hoặc không chọn checkbox
      driver.findElement(By.xpath("//input[@id='task_is_public']")).click();
      //driver.findElement(By.xpath("//label[normalize-space()='Public']")).click();

      Thread.sleep(1000);
      boolean checkboxPublicAfterSelected = driver.findElement(By.xpath("//label[normalize-space()='Public']")).isSelected();
      System.out.println("Checkbox Public  AFTER SELECTED is selected: " + checkboxPublicAfterSelected);

      //Handle multi checkbox

      //Click chọn từ đầu đến hết list checkbox
      List<WebElement> multiCheckboxes = driver.findElements(By.xpath("(//form[@id='task-form']//div[contains(@class, 'task-add-edit')])//input"));
      System.out.println("Total multi checkboxes: " + multiCheckboxes.size());

      for (int i = 0; i < multiCheckboxes.size(); i++) {
         multiCheckboxes.get(i).click();
         System.out.println("Đã tick vào checkbox " + (i + 1));
      }

      //Click chọn từng checkbox
      Thread.sleep(2000);
      //driver.findElement(By.xpath("((//form[@id='task-form']//div[contains(@class, 'task-add-edit')])//input)[1]")).click();
      driver.findElement(By.xpath("((//form[@id='task-form']//div[contains(@class, 'task-add-edit')])//input)[2]")).click();

      //Kiểm tra giá trị sau khi chọn của cả list checkbox
      List<WebElement> multiCheckboxesAfterSelected = driver.findElements(By.xpath("(//form[@id='task-form']//div[contains(@class, 'task-add-edit')])//input"));
      System.out.println("Total multi checkboxes: " + multiCheckboxes.size());

      for (int i = 0; i < multiCheckboxesAfterSelected.size(); i++) {
         System.out.println(multiCheckboxesAfterSelected.get(i).isSelected());
         if (multiCheckboxesAfterSelected.get(i).isSelected() == false) {
            System.out.println("Fail. Can not select all checkboxes.");
            break;
         }
      }

      Thread.sleep(2000);
      driver.quit();
   }
}
