package com.anhtester.Bai22_ThucHanhPOM.pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;

public class BasePage {
   private WebDriver driver;
   private WebDriverWait wait;

   By menuDashboard = By.xpath("//span[normalize-space()='Dashboard']");
   By menuCustomers = By.xpath("//span[normalize-space()='Customers']");
   By menuProjects = By.xpath("//span[normalize-space()='Projects']");
   By menuTasks = By.xpath("//span[normalize-space()='Tasks']");

   public BasePage(WebDriver driver) {
      this.driver = driver;
      wait = new WebDriverWait(driver, Duration.ofSeconds(10));
   }

   public DashboardPage clickDashboardMenu(){
      wait.until(ExpectedConditions.elementToBeClickable(menuDashboard));
      driver.findElement(menuDashboard).click();

      return new DashboardPage(driver);
   }

   public CustomersPage clickCustomersMenu(){
      wait.until(ExpectedConditions.elementToBeClickable(menuCustomers));
      driver.findElement(menuCustomers).click();

      return new CustomersPage(driver);
   }

   public ProjectsPage clickProjectsMenu(){
      wait.until(ExpectedConditions.elementToBeClickable(menuProjects));
      driver.findElement(menuProjects).click();

      return new ProjectsPage(driver);
   }

   public TasksPage clickTasksMenu(){
      wait.until(ExpectedConditions.elementToBeClickable(menuTasks));
      driver.findElement(menuTasks).click();

      return new TasksPage(driver);
   }

   /**
    * Bọc chuỗi text thành literal an toàn cho XPath (xử lý trường hợp text có dấu nháy).
    * Dùng chung cho các page cần lọc dòng trong datatable theo tên.
    */
   protected String xpathLiteral(String text) {
      if (!text.contains("'")) {
         return "'" + text + "'";
      }
      if (!text.contains("\"")) {
         return "\"" + text + "\"";
      }
      String[] parts = text.split("'");
      StringBuilder builder = new StringBuilder("concat(");
      for (int i = 0; i < parts.length; i++) {
         if (i > 0) {
            builder.append(", \"'\", ");
         }
         builder.append("'").append(parts[i]).append("'");
      }
      builder.append(")");
      return builder.toString();
   }

}
