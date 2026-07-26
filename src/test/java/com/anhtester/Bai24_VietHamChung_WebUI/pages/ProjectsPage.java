package com.anhtester.Bai24_VietHamChung_WebUI.pages;

import com.anhtester.constants.ConfigData;
import com.anhtester.keywords.WebUI;
import org.openqa.selenium.*;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;

import java.time.Duration;

public class ProjectsPage extends BasePage {
   //Số lần được phép gõ lại từ khoá vào ô search ajax khi danh sách chưa nạp về kịp
   private static final int SEARCH_MAX_RETRIES = 3;

   private WebDriver driver;
   private WebDriverWait wait;

   private String projectsPageUrl = "/admin/projects";
   private String addNewProjectPageUrl = "/admin/projects/project";

   private By headerProjectsPage = By.xpath("//span[normalize-space()='Projects Summary']");
   private By totalNotStarted = By.xpath("//span[contains(@class,'project-status') and normalize-space()='Not Started']/preceding-sibling::span");
   private By totalInProgress = By.xpath("//span[contains(@class,'project-status') and normalize-space()='In Progress']/preceding-sibling::span");
   private By totalOnHold = By.xpath("//span[contains(@class,'project-status') and normalize-space()='On Hold']/preceding-sibling::span");
   private By totalCancelled = By.xpath("//span[contains(@class,'project-status') and normalize-space()='Cancelled']/preceding-sibling::span");
   private By totalFinished = By.xpath("//span[contains(@class,'project-status') and normalize-space()='Finished']/preceding-sibling::span");

   private By tableProjects = By.cssSelector("table#projects");
   private By tableProjectsBody = By.cssSelector("#projects tbody");
   private By inputSearchProject = By.cssSelector("#projects_filter input[type='search']");
   //Lớp phủ "Processing..." của Datatable, hiện lên trong lúc bảng đang chờ dữ liệu ajax về
   private By tableProjectsProcessing = By.cssSelector("#projects_processing");
   private By buttonNewProject = By.xpath("//a[contains(@href,'/admin/projects/project') and contains(normalize-space(),'New Project')]");

   //Form Add New Project
   private By inputProjectName = By.id("name");
   private By buttonCustomerDropdown = By.cssSelector("button[data-id='clientid']");
   private By inputCustomerSearch = By.xpath("//select[@id='clientid']/parent::div//div[contains(@class,'bs-searchbox')]/input");
   private By buttonBillingTypeDropdown = By.cssSelector("button[data-id='billing_type']");
   private By buttonStatusDropdown = By.cssSelector("button[data-id='status']");
   //Trang này có cả div#project_cost bọc ngoài input#project_cost nên phải chỉ rõ thẻ input
   private By inputProjectCost = By.cssSelector("input#project_cost");
   private By inputEstimatedHours = By.id("estimated_hours");
   private By inputStartDate = By.id("start_date");
   private By inputDeadline = By.id("deadline");
   private By buttonSave = By.cssSelector("button[type='submit'].btn-primary");
   //Editor TinyMCE của ô Description, load bất đồng bộ và có thể cướp focus nếu thao tác quá sớm
   private By iframeDescriptionEditor = By.id("description_ifr");

   //Trang chi tiết Project sau khi lưu
   private By headerProjectName = By.cssSelector("h3.project-name");
   private By overviewProjectId = By.cssSelector(".project-overview-id dd");
   private By overviewCustomer = By.cssSelector(".project-overview-customer dd a");
   private By overviewBillingType = By.cssSelector(".project-overview-billing dd");
   private By overviewStatus = By.cssSelector(".project-overview-status dd");
   private By overviewStartDate = By.cssSelector(".project-overview-start-date dd");

   public ProjectsPage(WebDriver driver) {
      super(driver);
      this.driver = driver;
      wait = new WebDriverWait(driver, Duration.ofSeconds(10));
      new WebUI(driver);
   }

   public ProjectsPage openProjectsPage() {
      driver.get(ConfigData.BASE_URL + projectsPageUrl);
      WebUI.waitForPageLoaded();
      wait.until(ExpectedConditions.visibilityOfElementLocated(headerProjectsPage));

      return this;
   }

   public ProjectsPage verifyNavigateToProjectsPage() {
      wait.until(ExpectedConditions.visibilityOf(driver.findElement(headerProjectsPage)));
      Assert.assertEquals(driver.findElement(headerProjectsPage).getText(), "Projects Summary", "The header Projects page not match.");
      Assert.assertEquals(driver.getCurrentUrl(), ConfigData.BASE_URL + projectsPageUrl, "The Projects page URL is incorrect.");

      return this;
   }

   public ProjectsPage clickNewProjectButton() {
      wait.until(ExpectedConditions.elementToBeClickable(buttonNewProject));
      driver.findElement(buttonNewProject).click();
      waitForAddNewProjectFormLoaded();

      return this;
   }

   public ProjectsPage verifyNavigateToAddNewProjectPage() {
      waitForAddNewProjectFormLoaded();
      wait.until(ExpectedConditions.urlContains(addNewProjectPageUrl));

      return this;
   }

   private void waitForAddNewProjectFormLoaded() {
      WebUI.waitForPageLoaded();
      wait.until(ExpectedConditions.visibilityOfElementLocated(inputProjectName));
      //Chờ TinyMCE khởi tạo xong, tránh việc editor cướp focus làm đóng dropdown đang mở
      wait.until(ExpectedConditions.visibilityOfElementLocated(iframeDescriptionEditor));
   }

   public ProjectsPage setProjectName(String projectName) {
      setText(inputProjectName, projectName);

      return this;
   }

   /**
    * Customer là selectpicker dạng ajax-search: option chỉ được nạp về sau khi gõ từ khoá.
    * Vì vậy phải gõ thật vào ô search rồi chọn option trong dropdown, không set value trực tiếp được.
    * Danh sách hay bị hụt ở lần gõ đầu nên cho phép gõ lại tối đa SEARCH_MAX_RETRIES lần.
    */
   public ProjectsPage selectCustomer(String customerName) {
      By optionCustomer = getSelectPickerOption("clientid", customerName);
      WebUI.searchSelectPickerOption(buttonCustomerDropdown, inputCustomerSearch, optionCustomer, customerName, SEARCH_MAX_RETRIES);
      WebUI.retryUntil(driver -> {
         driver.findElement(optionCustomer).click();
         return true;
      });

      wait.until(ExpectedConditions.attributeToBe(buttonCustomerDropdown, "title", customerName));

      return this;
   }

   public ProjectsPage selectBillingType(String billingType) {
      selectPickerByText(buttonBillingTypeDropdown, "billing_type", billingType);

      return this;
   }

   public ProjectsPage selectStatus(String status) {
      selectPickerByText(buttonStatusDropdown, "status", status);

      return this;
   }

   //Ô Project Cost chỉ hiển thị khi Billing Type là Fixed Rate
   public ProjectsPage setProjectCost(String projectCost) {
      setText(inputProjectCost, projectCost);

      return this;
   }

   public ProjectsPage setEstimatedHours(String estimatedHours) {
      setText(inputEstimatedHours, estimatedHours);

      return this;
   }

   public ProjectsPage setStartDate(String startDate) {
      setText(inputStartDate, startDate);

      return this;
   }

   public ProjectsPage setDeadline(String deadline) {
      setText(inputDeadline, deadline);

      return this;
   }

   public ProjectsPage clickSaveButton() {
      WebUI.clickElement(buttonSave);

      return this;
   }

   public ProjectsPage waitForProjectViewPage() {
      WebUI.waitForPageLoaded();
      wait.until(ExpectedConditions.urlMatches(".*/admin/projects/view/\\d+$"));
      wait.until(ExpectedConditions.visibilityOfElementLocated(overviewCustomer));

      return this;
   }

   /**
    * Datatable của Perfex lọc bằng ajax: mỗi ký tự gõ vào ô search bắn một request
    * và mỗi response về sẽ vẽ lại toàn bộ tbody, xoá sạch node cũ.
    * Nếu chỉ chờ "bảng đã có chữ cần tìm" thì vẫn còn request của các ký tự cuối đang bay,
    * lần vẽ kế tiếp sẽ làm mọi element tìm được sau đó bị stale.
    * Vì vậy phải chờ đủ 3 mốc: bảng đã lọc xong, lớp phủ Processing đã tắt, và hết ajax đang treo.
    */
   public ProjectsPage searchProject(String keyword) {
      wait.until(ExpectedConditions.visibilityOfElementLocated(inputSearchProject));
      driver.findElement(inputSearchProject).clear();
      driver.findElement(inputSearchProject).sendKeys(keyword);
      if (!keyword.isEmpty()) {
         //Dùng WebUI.retryUntil vì getText() cũng có thể dính stale khi tbody đang được vẽ lại
         WebUI.retryUntil(driver -> {
            String tableText = driver.findElement(tableProjectsBody).getText();
            return tableText.contains(keyword) || tableText.contains("No matching records found");
         });
      }
      wait.until(ExpectedConditions.invisibilityOfElementLocated(tableProjectsProcessing));
      WebUI.waitForJQueryLoad();

      return this;
   }

   public boolean isProjectsTableDisplayed() {
      WebUI.waitForPageLoaded();
      return WebUI.checkElementExist(tableProjects, 10, 1000);
   }

   public boolean isProjectDisplayed(String projectName) {
      searchProject(projectName);
      return WebUI.retryUntil(driver -> driver.findElement(tableProjectsBody).getText().contains(projectName));
   }

   public boolean isProjectNotDisplayed(String projectName) {
      searchProject(projectName);
      return WebUI.retryUntil(driver -> !driver.findElement(tableProjectsBody).getText().contains(projectName));
   }

   /**
    * Cụm link View | Copy | Edit | Delete của mỗi dòng chỉ hiện khi hover vào dòng đó,
    * và nút Delete bung hộp thoại confirm của trình duyệt.
    */
   public ProjectsPage deleteProjectByHoverAndConfirmAlert(String projectName) {
      searchProject(projectName);

      By projectNameLink = getProjectNameLink(projectName);
      By deleteProjectLink = getDeleteProjectLink(projectName);

      //Datatable có thể vẽ lại ngay sau khi lọc làm element cũ bị stale,
      //nên gom hover + bấm Delete vào một vòng chờ có thể thử lại.
      WebUI.retryUntil(driver -> {
         WebElement projectNameElement = driver.findElement(projectNameLink);
         //Cuộn dòng vào giữa màn hình rồi mới hover, chuột không di tới element ngoài viewport được
         WebUI.scrollToElement(projectNameElement);
         new Actions(driver).moveToElement(projectNameElement).perform();

         //Hover có thể chưa kịp ăn, link Delete vẫn đang ẩn thì trả false để hover lại ở vòng sau
         WebElement deleteLink = driver.findElement(deleteProjectLink);
         if (!deleteLink.isDisplayed()) {
            return false;
         }
         deleteLink.click();
         return true;
      });

      wait.until(ExpectedConditions.alertIsPresent());
      Alert confirmAlert = driver.switchTo().alert();
      confirmAlert.accept();

      //Sau khi xóa, trang Projects được load lại từ đầu
      WebUI.waitForPageLoaded();
      wait.until(ExpectedConditions.visibilityOfElementLocated(headerProjectsPage));

      return this;
   }

   private By getProjectNameLink(String projectName) {
      return By.xpath("//table[@id='projects']//tbody/tr[contains(., " + xpathLiteral(projectName) + ")]//a[normalize-space()=" + xpathLiteral(projectName) + "]");
   }

   private By getDeleteProjectLink(String projectName) {
      return By.xpath("//table[@id='projects']//tbody/tr[contains(., " + xpathLiteral(projectName) + ")]//a[contains(@href,'/admin/projects/delete/') and contains(@class,'_delete')]");
   }

   public String getPageTitle() {
      return driver.getTitle();
   }

   public String getProjectNameOnViewPage() {
      //Thẻ h3.project-name có class 'hide' nên phải lấy textContent thay vì getText()
      wait.until(ExpectedConditions.presenceOfElementLocated(headerProjectName));
      return driver.findElement(headerProjectName).getAttribute("textContent").trim();
   }

   public String getProjectIdOnViewPage() {
      return getElementText(overviewProjectId);
   }

   public String getCustomerNameOnViewPage() {
      return getElementText(overviewCustomer);
   }

   public String getBillingTypeOnViewPage() {
      return getElementText(overviewBillingType);
   }

   public String getStatusOnViewPage() {
      return getElementText(overviewStatus);
   }

   public String getStartDateOnViewPage() {
      return getElementText(overviewStartDate);
   }

   public int getNotStartedTotal() {
      wait.until(ExpectedConditions.visibilityOfElementLocated(totalNotStarted));
      int notStartedTotal = Integer.parseInt(driver.findElement(totalNotStarted).getText());
      System.out.println("Total not started: " + notStartedTotal);
      return notStartedTotal;
   }

   public int getInProgressTotal() {
      wait.until(ExpectedConditions.visibilityOfElementLocated(totalInProgress));
      int inProgressTotal = Integer.parseInt(driver.findElement(totalInProgress).getText());
      System.out.println("Total in progress: " + inProgressTotal);
      return inProgressTotal;
   }

   public int getOnHoldTotal() {
      wait.until(ExpectedConditions.visibilityOfElementLocated(totalOnHold));
      int onHoldTotal = Integer.parseInt(driver.findElement(totalOnHold).getText());
      System.out.println("Total on hold: " + onHoldTotal);
      return onHoldTotal;
   }

   public int getCancelledTotal() {
      wait.until(ExpectedConditions.visibilityOfElementLocated(totalCancelled));
      int cancelledTotal = Integer.parseInt(driver.findElement(totalCancelled).getText());
      System.out.println("Total cancelled: " + cancelledTotal);
      return cancelledTotal;
   }

   public int getFinishedTotal() {
      wait.until(ExpectedConditions.visibilityOfElementLocated(totalFinished));
      int finishedTotal = Integer.parseInt(driver.findElement(totalFinished).getText());
      System.out.println("Total finished: " + finishedTotal);
      return finishedTotal;
   }

   private void setText(By locator, String value) {
      wait.until(ExpectedConditions.visibilityOfElementLocated(locator));
      driver.findElement(locator).clear();
      driver.findElement(locator).sendKeys(value);
   }

   private String getElementText(By locator) {
      wait.until(ExpectedConditions.visibilityOfElementLocated(locator));
      return driver.findElement(locator).getText().trim();
   }

   private void selectPickerByText(By buttonDropdown, String selectId, String visibleText) {
      wait.until(ExpectedConditions.elementToBeClickable(buttonDropdown));
      driver.findElement(buttonDropdown).click();

      By option = getSelectPickerOption(selectId, visibleText);
      wait.until(ExpectedConditions.elementToBeClickable(option));
      driver.findElement(option).click();

      wait.until(ExpectedConditions.attributeToBe(buttonDropdown, "title", visibleText));
   }

   private By getSelectPickerOption(String selectId, String visibleText) {
      return By.xpath("//select[@id='" + selectId + "']/parent::div//ul[contains(@class,'dropdown-menu')]//span[@class='text' and normalize-space()='" + visibleText + "']");
   }

}
