package com.anhtester.Bai17_PageObjectModel.testcases;

import com.anhtester.Bai17_PageObjectModel.pages.DashboardPage;
import com.anhtester.Bai17_PageObjectModel.pages.LoginPage;
import com.anhtester.Bai17_PageObjectModel.pages.ProjectsPage;
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
      dashboardPage = new DashboardPage(driver);
      projectsPage = new ProjectsPage(driver);
   }

   @Test
   public void testQuickStatisticsTotal() {
      loginPage.loginCRM_AdminRole();
      dashboardPage.verifyNavigateToDashboardPage();
      dashboardPage.clickProjectsMenu();
      projectsPage.verifyNavigateToProjectsPage();
      int notStartedTotal = projectsPage.getNotStartedTotal();
      int inProgressTotal = projectsPage.getInProgressTotal();
      int onHoldTotal = projectsPage.getOnHoldTotal();
      int cancelledTotal = projectsPage.getCancelledTotal();
      int finishedTotal = projectsPage.getFinishedTotal();
      int projectTotal = notStartedTotal +  inProgressTotal + onHoldTotal + cancelledTotal + finishedTotal;
      System.out.println("Total projects: " + projectTotal);
      projectsPage.clickDashboardMenu();
      dashboardPage.verifyNavigateToDashboardPage();
      dashboardPage.verifyTotalProjectsInProgress(inProgressTotal, projectTotal);
   }
}
