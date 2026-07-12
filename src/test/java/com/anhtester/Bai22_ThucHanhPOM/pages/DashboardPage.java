package com.anhtester.Bai22_ThucHanhPOM.pages;

import com.anhtester.constants.ConfigData;
import com.anhtester.keywords.WebUI;
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
   private By menuDashboard = By.xpath("//span[normalize-space()='Dashboard']");
   private By totalInvoicesAwaitingPayment = By.xpath("(//span[normalize-space()='Invoices Awaiting Payment']/parent::div)/following-sibling::span");
   private By totalConvertedLeads = By.xpath("(//span[normalize-space()='Converted Leads']/parent::div)/following-sibling::span");
   private By totalProjectsInProgress = By.xpath("(//span[normalize-space()='Projects In Progress']/parent::div)/following-sibling::span");
   private By totalTasksNotFinished = By.xpath("(//span[normalize-space()='Tasks Not Finished']/parent::div)/following-sibling::span");

   public DashboardPage(WebDriver driver) {
      super(driver);
      this.driver = driver;
      wait = new WebDriverWait(driver, Duration.ofSeconds(10));
   }

   public boolean isDashboardPageOpen() {
      WebUI.waitForPageLoaded(driver);
      return driver.findElement(menuDashboard).isDisplayed();
   }

   public void verifyNavigateToDashboardPage() {
      WebUI.waitForPageLoaded(driver);
      wait.until(ExpectedConditions.visibilityOfElementLocated(menuDashboard));
      Assert.assertEquals(driver.getCurrentUrl(), ConfigData.BASE_URL + dashboardPageUrl, "The dashboard page URL not match.");
   }

   public void verifyTotalInvoicesAwaitingPayment(String expectedValue) {
      WebUI.waitForPageLoaded(driver);
      Assert.assertTrue(WebUI.isElementPresent(driver, totalInvoicesAwaitingPayment), "The section Invoices Awaiting Payment not display.");
      Assert.assertEquals(driver.findElement(totalInvoicesAwaitingPayment).getText(), expectedValue, "FAIL!! Invoices Awaiting Payment total not match.");
   }

   public void verifyTotalConvertedLeads(String expectedValue) {
      WebUI.waitForPageLoaded(driver);
      Assert.assertTrue(WebUI.isElementPresent(driver, totalConvertedLeads), "The section Converted Leads not display.");
      Assert.assertEquals(driver.findElement(totalConvertedLeads).getText(), expectedValue, "FAIL!! Converted Leads total not match.");
   }

   public void verifyTotalProjectsInProgress(int projectsInProgress, int projectsTotal) {
      WebUI.waitForPageLoaded(driver);
      wait.until(ExpectedConditions.visibilityOfElementLocated(totalProjectsInProgress));
      String text = driver.findElement(totalProjectsInProgress).getText();
      System.out.println("Total Projects In Progress: " + text);
      Assert.assertEquals(text, projectsInProgress + " / " + projectsTotal, "Total Projects In Progress not match.");
   }

   public void verifyTotalTasksNotFinished(String expectedValue) {
      WebUI.waitForPageLoaded(driver);
      Assert.assertTrue(WebUI.isElementPresent(driver, totalTasksNotFinished), "The section Tasks Not Finished not display.");
      Assert.assertEquals(driver.findElement(totalTasksNotFinished).getText(), expectedValue, "FAIL!! Tasks Not Finished total not match.");
   }

}
