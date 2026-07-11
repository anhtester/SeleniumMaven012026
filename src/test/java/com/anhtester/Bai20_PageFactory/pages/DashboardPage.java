package com.anhtester.Bai20_PageFactory.pages;

import com.anhtester.constants.ConfigData;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.*;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;

import java.time.Duration;
import java.util.ArrayList;
import java.util.List;

public class DashboardPage extends BasePage {
   private WebDriver driver;
   private WebDriverWait wait;

   private String dashboardPageUrl = "/admin/";

   @CacheLookup
   @FindBy(xpath = "//ul[@id='side-menu']//li[contains(@class,'menu-item-dashboard')]//span[contains(@class,'menu-text') and normalize-space()='Dashboard']")
   private WebElement menuDashboard;

   @FindBys({
           @FindBy(css = "#side-menu > li[class*='menu-item-'] > a > span.menu-text")
   })
   private List<WebElement> menuList;

   @FindBy(xpath = "(//span[normalize-space()='Invoices Awaiting Payment']/parent::div)/following-sibling::span")
   private WebElement totalInvoicesAwaitingPayment;

   @FindBy(xpath = "(//span[normalize-space()='Converted Leads']/parent::div)/following-sibling::span")
   private WebElement totalConvertedLeads;

   @FindBy(xpath = "(//span[normalize-space()='Projects In Progress']/parent::div)/following-sibling::span")
   private WebElement totalProjectsInProgress;

   @FindBy(xpath = "(//span[normalize-space()='Tasks Not Finished']/parent::div)/following-sibling::span")
   private WebElement totalTasksNotFinished;

   public DashboardPage(WebDriver driver) {
      super(driver);
      this.driver = driver;
      wait = new WebDriverWait(driver, Duration.ofSeconds(10));
      PageFactory.initElements(driver, this);
   }

   public void verifyNavigateToDashboardPage() {
      wait.until(ExpectedConditions.visibilityOf(menuDashboard));
      Assert.assertEquals(driver.getCurrentUrl(), ConfigData.BASE_URL + dashboardPageUrl, "The dashboard page URL not match.");
   }

   public List<String> getMenuList() {
      wait.until(ExpectedConditions.visibilityOfAllElements(menuList));

      List<String> menuTextList = new ArrayList<>();
      for (WebElement menu : menuList) {
         String menuText = menu.getText().trim();
         if (!menuText.isEmpty()) {
            menuTextList.add(menuText);
            System.out.println(menuText);
         }
      }

      return menuTextList;
   }

   public void verifyTotalInvoicesAwaitingPayment() {

   }

   public void verifyTotalConvertedLeads() {
   }

   public void verifyTotalProjectsInProgress(int projectsInProgress, int projectsTotal) {
      wait.until(ExpectedConditions.visibilityOf(totalProjectsInProgress));
      String text = totalProjectsInProgress.getText();
      System.out.println("Total Projects In Progress: " + text);
      Assert.assertEquals(projectsInProgress + " / " + projectsTotal, text, "Total Projects In Progress not match.");
   }

   public void verifyTotalTasksNotFinished() {
   }

}
