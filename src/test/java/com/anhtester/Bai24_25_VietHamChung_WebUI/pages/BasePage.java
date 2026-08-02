package com.anhtester.Bai24_25_VietHamChung_WebUI.pages;

import com.anhtester.keywords.WebUI;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

public class BasePage {
   private WebDriver driver;

   By menuDashboard = By.xpath("//span[normalize-space()='Dashboard']");
   By menuCustomers = By.xpath("//span[normalize-space()='Customers']");
   By menuProjects = By.xpath("//span[normalize-space()='Projects']");
   By menuTasks = By.xpath("//span[normalize-space()='Tasks']");

   public BasePage(WebDriver driver) {
      this.driver = driver;
      //Truyền driver vào class WebUI để các hàm static của WebUI dùng chung driver này
      new WebUI(driver);
   }

   public DashboardPage clickDashboardMenu() {
      WebUI.clickElement(menuDashboard);

      return new DashboardPage(driver);
   }

   public CustomersPage clickCustomersMenu() {
      WebUI.clickElement(menuCustomers);

      return new CustomersPage(driver);
   }

   public ProjectsPage clickProjectsMenu() {
      WebUI.clickElement(menuProjects);

      return new ProjectsPage(driver);
   }

   public TasksPage clickTasksMenu() {
      WebUI.clickElement(menuTasks);

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
