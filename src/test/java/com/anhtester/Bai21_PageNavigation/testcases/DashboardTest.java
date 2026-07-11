package com.anhtester.Bai21_PageNavigation.testcases;

import com.anhtester.Bai21_PageNavigation.pages.DashboardPage;
import com.anhtester.Bai21_PageNavigation.pages.LoginPage;
import com.anhtester.Bai21_PageNavigation.pages.ProjectsPage;
import com.anhtester.common.BaseTest;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

public class DashboardTest extends BaseTest {

   private LoginPage loginPage;
   private DashboardPage dashboardPage;
   private ProjectsPage projectsPage;

   @BeforeMethod
   public void setUp() {
      loginPage = new LoginPage(driver);
   }

   @Test
   public void testQuickStatisticsTotal() {
      dashboardPage = loginPage.loginCRM_AdminRole();
      dashboardPage.verifyNavigateToDashboardPage();
      projectsPage = dashboardPage.clickProjectsMenu();
      projectsPage.verifyNavigateToProjectsPage();
      int notStartedTotal = projectsPage.getNotStartedTotal();
      int inProgressTotal = projectsPage.getInProgressTotal();
      int onHoldTotal = projectsPage.getOnHoldTotal();
      int cancelledTotal = projectsPage.getCancelledTotal();
      int finishedTotal = projectsPage.getFinishedTotal();
      int projectTotal = notStartedTotal +  inProgressTotal + onHoldTotal + cancelledTotal + finishedTotal;
      System.out.println("Total projects: " + projectTotal);
      dashboardPage = projectsPage.clickDashboardMenu();
      dashboardPage.verifyNavigateToDashboardPage();
      dashboardPage.verifyTotalProjectsInProgress(inProgressTotal, projectTotal);
   }
}
