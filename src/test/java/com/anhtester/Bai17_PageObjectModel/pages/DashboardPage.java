package com.anhtester.Bai17_PageObjectModel.pages;

import com.anhtester.constants.ConfigData;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;

import java.time.Duration;

public class DashboardPage extends BasePage {
   private WebDriver driver;
   private WebDriverWait wait;

   private String dashboardPageUrl = "/admin/";
   private By totalInvoicesAwaitingPayment = By.xpath("(//span[normalize-space()='Invoices Awaiting Payment']/parent::div)/following-sibling::span");
   private By totalConvertedLeads = By.xpath("(//span[normalize-space()='Converted Leads']/parent::div)/following-sibling::span");
   private By totalProjectsInProgress = By.xpath("(//span[normalize-space()='Projects In Progress']/parent::div)/following-sibling::span");
   private By totalTasksNotFinished = By.xpath("(//span[normalize-space()='Tasks Not Finished']/parent::div)/following-sibling::span");

   public DashboardPage(WebDriver driver) {
      super(driver);
      this.driver = driver;
      wait = new WebDriverWait(driver, Duration.ofSeconds(10));
   }

   public void verifyNavigateToDashboardPage() {
      wait.until(ExpectedConditions.visibilityOfElementLocated(menuDashboard));
      Assert.assertEquals(driver.getCurrentUrl(), ConfigData.BASE_URL + dashboardPageUrl, "The dashboard page URL not match.");
   }

   public void verifyTotalInvoicesAwaitingPayment() {

   }

   public void verifyTotalConvertedLeads() {
   }

   public void verifyTotalProjectsInProgress(int projectsInProgress, int projectsTotal) {
      wait.until(ExpectedConditions.visibilityOfElementLocated(totalProjectsInProgress));
      String text = driver.findElement(totalProjectsInProgress).getText();
      System.out.println("Total Projects In Progress: " + text);
      Assert.assertEquals(projectsInProgress + " / " + projectsTotal, text, "Total Projects In Progress not match.");
   }

   public void verifyTotalTasksNotFinished() {
   }

}
