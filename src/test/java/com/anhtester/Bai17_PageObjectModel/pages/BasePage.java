package com.anhtester.Bai17_PageObjectModel.pages;

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

   public BasePage(WebDriver driver) {
      this.driver = driver;
      wait = new WebDriverWait(driver, Duration.ofSeconds(10));
   }

   public void clickDashboardMenu(){
      wait.until(ExpectedConditions.elementToBeClickable(menuDashboard));
      driver.findElement(menuDashboard).click();
   }

   public void clickCustomersMenu(){
      wait.until(ExpectedConditions.elementToBeClickable(menuCustomers));
      driver.findElement(menuCustomers).click();
   }

   public void clickProjectsMenu(){
      wait.until(ExpectedConditions.elementToBeClickable(menuProjects));
      driver.findElement(menuProjects).click();
   }

}
