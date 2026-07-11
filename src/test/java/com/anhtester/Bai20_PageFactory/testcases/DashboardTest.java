package com.anhtester.Bai20_PageFactory.testcases;

import com.anhtester.Bai20_PageFactory.pages.DashboardPage;
import com.anhtester.Bai20_PageFactory.pages.LoginPage;
import com.anhtester.common.BaseTest;
import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import java.util.List;

public class DashboardTest extends BaseTest {

   private LoginPage loginPage;
   private DashboardPage dashboardPage;

   @BeforeMethod
   public void setUp() {
      loginPage = new LoginPage(driver);
      dashboardPage = new DashboardPage(driver);
   }

   @Test
   public void testMenuList_AdminExample() {
      loginPage.loginCRM_AdminRole();
      dashboardPage.verifyNavigateToDashboardPage();

      List<String> actualMenuList = dashboardPage.getMenuList();
      List<String> expectedMenuList = List.of(
              "Dashboard",
              "Clients",
              "Projects",
              "Tasks",
              "Contracts",
              "Sales",
              "Subscriptions",
              "Expenses",
              "Support",
              "Leads",
              "Estimate Request",
              "Knowledge Base",
              "Utilities",
              "Reports"
      );

      Assert.assertEquals(actualMenuList, expectedMenuList, "The menu list does not match.");
   }
}
