package com.anhtester.Bai22_ThucHanhPOM.pages;

import com.anhtester.constants.ConfigData;
import com.anhtester.keywords.WebUI;
import org.openqa.selenium.By;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.StaleElementReferenceException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.FluentWait;
import org.openqa.selenium.support.ui.Wait;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;
import java.util.List;

public class TasksPage extends BasePage {

   private WebDriver driver;
   private WebDriverWait wait;
   private Wait<WebDriver> ajaxSearchWait;

   private String tasksPageUrl = "/admin/tasks";

   private By headerTasksSummary = By.xpath("//span[normalize-space()='Tasks Summary']");
   private By buttonNewTask = By.xpath("//a[contains(@class,'btn-primary') and contains(normalize-space(),'New Task')]");
   private By tableTasks = By.cssSelector("table#tasks");
   private By tableTasksBody = By.cssSelector("#tasks tbody");
   private By inputSearchTask = By.cssSelector("#tasks_filter input[type='search']");

   //Form Add New Task nằm trong modal, được render động khi bấm nút New Task
   private By modalTask = By.cssSelector("#_task_modal.in");
   private By modalTaskTitle = By.cssSelector("#_task_modal .modal-title");
   private By inputTaskName = By.cssSelector("#_task_modal input#name");
   private By inputStartDate = By.cssSelector("#_task_modal input#startdate");
   private By inputDueDate = By.cssSelector("#_task_modal input#duedate");
   private By buttonPriorityDropdown = By.cssSelector("button[data-id='priority']");
   private By buttonRelatedToDropdown = By.cssSelector("button[data-id='rel_type']");
   private By buttonRelatedItemDropdown = By.cssSelector("button[data-id='rel_id']");
   private By inputRelatedItemSearch = By.xpath("//select[@id='rel_id']/parent::div//div[contains(@class,'bs-searchbox')]/input");
   private By buttonSave = By.cssSelector("#_task_modal button[type='submit'].btn-primary");
   private By datePickerPopup = By.cssSelector(".xdsoft_datetimepicker");
   private By modalBackdrop = By.cssSelector(".modal-backdrop");

   //Modal chi tiết task, tự động mở ra sau khi lưu task mới
   private By modalTaskDetail = By.cssSelector("#task-modal.in");
   private By modalTaskDetailTitle = By.cssSelector("#task-modal .modal-title");
   private By buttonCloseTaskDetail = By.cssSelector("#task-modal button.close");
   private By floatAlert = By.cssSelector(".float-alert");

   public TasksPage(WebDriver driver) {
      super(driver);
      this.driver = driver;
      wait = new WebDriverWait(driver, Duration.ofSeconds(10));
      ajaxSearchWait = new FluentWait<>(driver)
              .withTimeout(Duration.ofSeconds(20))
              .pollingEvery(Duration.ofSeconds(2))
              .ignoring(NoSuchElementException.class, StaleElementReferenceException.class);
   }

   public TasksPage openTasksPage() {
      driver.get(ConfigData.BASE_URL + tasksPageUrl);
      WebUI.waitForPageLoaded(driver);
      wait.until(ExpectedConditions.visibilityOfElementLocated(headerTasksSummary));

      return this;
   }

   public TasksPage verifyNavigateToTasksPage() {
      WebUI.waitForPageLoaded(driver);
      wait.until(ExpectedConditions.visibilityOfElementLocated(headerTasksSummary));
      wait.until(ExpectedConditions.urlContains(tasksPageUrl));

      return this;
   }

   public TasksPage clickNewTaskButton() {
      wait.until(ExpectedConditions.elementToBeClickable(buttonNewTask));
      driver.findElement(buttonNewTask).click();
      //Chờ modal chạy xong hiệu ứng fade để tránh click trượt khi modal còn di chuyển
      wait.until(ExpectedConditions.visibilityOfElementLocated(modalTask));
      wait.until(ExpectedConditions.elementToBeClickable(inputTaskName));

      return this;
   }

   public String getAddNewTaskModalTitle() {
      wait.until(ExpectedConditions.visibilityOfElementLocated(modalTaskTitle));
      return driver.findElement(modalTaskTitle).getText().trim();
   }

   public TasksPage setTaskName(String taskName) {
      setText(inputTaskName, taskName);

      return this;
   }

   public TasksPage setStartDate(String startDate) {
      setText(inputStartDate, startDate);
      closeDatePicker();

      return this;
   }

   public TasksPage setDueDate(String dueDate) {
      setText(inputDueDate, dueDate);
      closeDatePicker();

      return this;
   }

   public TasksPage selectPriority(String priority) {
      selectPickerByText(buttonPriorityDropdown, "priority", priority);

      return this;
   }

   public TasksPage selectRelatedTo(String relatedType) {
      selectPickerByText(buttonRelatedToDropdown, "rel_type", relatedType);

      return this;
   }

   /**
    * Ô chọn Project là selectpicker ajax-search, phải gõ thật để nạp dữ liệu về.
    * Option hiển thị theo dạng "#id - Tên project - Tên customer" nên chỉ so khớp chứa tên project.
    */
   public TasksPage selectRelatedProject(String projectName) {
      wait.until(ExpectedConditions.elementToBeClickable(buttonRelatedItemDropdown));
      driver.findElement(buttonRelatedItemDropdown).click();
      wait.until(ExpectedConditions.visibilityOfElementLocated(inputRelatedItemSearch));

      By optionProject = getSelectPickerOptionContains("rel_id", projectName);
      //Perfex chỉ gắn handler ajax cho ô search sau khi dropdown hiện xong, gõ sớm hơn thì không có request nào được bắn đi.
      //Do đó mỗi vòng chờ sẽ gõ lại từ khoá; chu kỳ 2 giây để không cắt ngang debounce của plugin.
      ajaxSearchWait.until(driver -> {
         List<WebElement> options = driver.findElements(optionProject);
         if (!options.isEmpty() && options.get(0).isDisplayed()) {
            return true;
         }
         typeKeywordToRelatedItemSearchBox(projectName);
         return false;
      });

      driver.findElement(optionProject).click();
      wait.until(ExpectedConditions.attributeContains(buttonRelatedItemDropdown, "title", projectName));

      return this;
   }

   private void typeKeywordToRelatedItemSearchBox(String keyword) {
      List<WebElement> searchBoxes = driver.findElements(inputRelatedItemSearch);
      if (searchBoxes.isEmpty() || !searchBoxes.get(0).isDisplayed()) {
         //Dropdown bị đóng lại thì mở ra để vòng chờ kế tiếp gõ tiếp
         driver.findElement(buttonRelatedItemDropdown).click();
         return;
      }
      searchBoxes.get(0).clear();
      searchBoxes.get(0).sendKeys(keyword);
   }

   public String getSelectedRelatedProject() {
      wait.until(ExpectedConditions.visibilityOfElementLocated(buttonRelatedItemDropdown));
      return driver.findElement(buttonRelatedItemDropdown).getAttribute("title");
   }

   /**
    * Lưu xong, Perfex gỡ form nhập và mở tiếp modal chi tiết của task vừa tạo.
    */
   public TasksPage clickSaveButton() {
      WebUI.clickElement(driver, buttonSave);
      wait.until(ExpectedConditions.invisibilityOfElementLocated(modalTask));
      wait.until(ExpectedConditions.visibilityOfElementLocated(modalTaskDetailTitle));

      return this;
   }

   public String getTaskNameOnDetailModal() {
      wait.until(ExpectedConditions.visibilityOfElementLocated(modalTaskDetailTitle));
      return driver.findElement(modalTaskDetailTitle).getText().trim();
   }

   /**
    * Đóng modal chi tiết task và chờ lớp phủ tan hẳn, nếu không bảng danh sách phía dưới chưa thao tác được.
    */
   public TasksPage closeTaskDetailModal() {
      //Toast thông báo lưu thành công nằm đè lên nút đóng ở góc phải trên, chờ nó tự tắt rồi mới bấm
      wait.until(driver -> driver.findElements(floatAlert).stream().noneMatch(WebElement::isDisplayed));
      wait.until(ExpectedConditions.elementToBeClickable(buttonCloseTaskDetail));
      driver.findElement(buttonCloseTaskDetail).click();
      wait.until(ExpectedConditions.invisibilityOfElementLocated(modalTaskDetail));
      wait.until(driver -> driver.findElements(modalBackdrop).stream().noneMatch(WebElement::isDisplayed));
      WebUI.waitForPageLoaded(driver);

      return this;
   }

   public TasksPage searchTask(String keyword) {
      wait.until(ExpectedConditions.visibilityOfElementLocated(inputSearchTask));
      driver.findElement(inputSearchTask).clear();
      driver.findElement(inputSearchTask).sendKeys(keyword);
      if (!keyword.isEmpty()) {
         wait.until(driver -> {
            String tableText = this.driver.findElement(tableTasksBody).getText();
            return tableText.contains(keyword) || tableText.contains("No matching records found");
         });
      }

      return this;
   }

   public boolean isTasksTableDisplayed() {
      WebUI.waitForPageLoaded(driver);
      return WebUI.isElementPresent(driver, tableTasks, 10);
   }

   public boolean isTaskDisplayed(String taskName) {
      searchTask(taskName);
      return wait.until(driver -> this.driver.findElement(tableTasksBody).getText().contains(taskName));
   }

   public String getRelatedProjectOfTask(String taskName) {
      searchTask(taskName);
      By relatedProjectLink = By.xpath("//table[@id='tasks']//tbody/tr[contains(., " + xpathLiteral(taskName) + ")]//a[contains(@class,'task-table-related')]");
      wait.until(ExpectedConditions.visibilityOfElementLocated(relatedProjectLink));
      return driver.findElement(relatedProjectLink).getText().trim();
   }

   /**
    * Lịch của ô ngày (xdsoft datetimepicker) sau khi nhập vẫn còn mở và che các control phía dưới.
    * Chuyển focus sang ô Task Name để đóng lịch:
    * - KHÔNG dùng phím ESC vì ESC đóng luôn cả modal.
    * - KHÔNG chỉ bấm ra vùng trống vì focus vẫn nằm ở ô ngày, lát sau lịch tự bung lại.
    */
   private void closeDatePicker() {
      wait.until(ExpectedConditions.elementToBeClickable(inputTaskName));
      driver.findElement(inputTaskName).click();
      wait.until(driver -> driver.findElements(datePickerPopup).stream().noneMatch(WebElement::isDisplayed));
   }

   private void setText(By locator, String value) {
      wait.until(ExpectedConditions.visibilityOfElementLocated(locator));
      driver.findElement(locator).clear();
      driver.findElement(locator).sendKeys(value);
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
      return By.xpath("//select[@id='" + selectId + "']/parent::div//ul[contains(@class,'dropdown-menu')]//span[@class='text' and normalize-space()=" + xpathLiteral(visibleText) + "]");
   }

   private By getSelectPickerOptionContains(String selectId, String partialText) {
      return By.xpath("//select[@id='" + selectId + "']/parent::div//ul[contains(@class,'dropdown-menu')]//span[@class='text' and contains(normalize-space()," + xpathLiteral(partialText) + ")]");
   }

}
