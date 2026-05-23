package com.anhtester.Bai10_Annotations.DemoAutomation;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.BeforeSuite;

import java.time.Duration;

public class BaseTest {
   WebDriver driver;

   @BeforeSuite
   public void beforeSuite(){
      System.out.println("Connect Database");
      System.out.println("Connect API của Jira");
      System.out.println("Connect API MoMo");
   }

   @BeforeMethod
   public void setUp() {
      System.out.println("Setting up the test environment...");
      driver = new ChromeDriver();
      driver.manage().window().maximize();
      driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(10));
      System.out.println("Running with Chrome driver...");
   }

   @AfterMethod
   public void tearDown() {
      driver.quit();
      System.out.println("Closed the Chrome driver.");
   }
}
