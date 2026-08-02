package com.anhtester.Bai24_25_VietHamChung_WebUI.pages;

import com.anhtester.constants.ConfigData;
import com.anhtester.keywords.WebUI;
import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.asserts.SoftAssert;

import java.time.Duration;

public class LoginPage extends BasePage {

   //Khai báo driver cục bộ trong chính class này
   private WebDriver driver;
   private WebDriverWait wait;
   public String LOGIN_URL = ConfigData.LOGIN_URL;
   public String LOGIN_PAGE_TITLE = "Perfex CRM | Anh Tester Demo - Login";
   public String LOGIN_PAGE_HEADER_TEXT = "Login";

   //Khai báo các element dạng đối tượng By (phương thức tìm kiếm)
   private By headerPage = By.xpath("//h1[normalize-space()='Login']");
   private By inputEmail = By.xpath("//input[@id='email']");
   private By inputPassword = By.xpath("//input[@id='password']");
   private By buttonLogin = By.xpath("//button[normalize-space()='Login']");
   private By errorMessage = By.xpath("//div[contains(@class,'alert-danger')]");
   private By alertEmailRequiredMessage = By.xpath("//div[normalize-space()='The Email Address field is required.']");
   private By alertPasswordRequiredMessage = By.xpath("//div[normalize-space()='The Password field is required.']");

   //Khai báo hàm xây dựng, để truyền driver từ bên ngoài vào chính class này sử dụng
   public LoginPage(WebDriver driver) {
      super(driver);
      this.driver = driver; //Truyền giá trị cho driver
      new WebUI(driver);
   }

   public void verifyNavigateToTheLoginPage() {
      WebUI.waitForPageLoaded();
      //Title, URL, Header
      SoftAssert softAssert = new SoftAssert();
      System.out.println("Login page title: " + driver.getTitle());
      softAssert.assertEquals(driver.getTitle(), LOGIN_PAGE_TITLE, "Fail. The Login page title not match.");
      Assert.assertEquals(driver.getCurrentUrl(), LOGIN_URL, "Fail. The Login page url not match.");
      softAssert.assertEquals(driver.findElement(headerPage).getText(), LOGIN_PAGE_HEADER_TEXT, "Fail. The Login page header not match.");
      softAssert.assertAll();
   }

   public String getHeaderLoginPage() {
      return WebUI.getElementText(headerPage);
   }

   //Khai báo các hàm xử lý automation phục vụ cho trang Login
   private void setEmail(String email) {
//      wait.until(ExpectedConditions.visibilityOfElementLocated(inputEmail));
//      driver.findElement(inputEmail).sendKeys(email);
      WebUI.setText(inputEmail, email);
   }

   private void setPassword(String password) {
      WebUI.setText(inputPassword, password);
   }

   private void clickLoginButton() {
//      wait.until(ExpectedConditions.visibilityOfElementLocated(buttonLogin));
//      driver.findElement(buttonLogin).click();
      WebUI.clickElement(buttonLogin);
   }

   public void verifyLoginSuccess() {
      new DashboardPage(driver).verifyNavigateToDashboardPage();
      //wait.until(ExpectedConditions.urlContains("/admin/"));
      WebUI.waitForCurrentURLContains("/admin/");
      Assert.assertTrue(driver.getCurrentUrl().contains("/admin/"), "FAIL. Không chuyển hướng sang trang Dashboard");
      Assert.assertFalse(driver.getCurrentUrl().contains("authentication"), "FAIL. Vẫn đang ở trang Login");
   }

   public void verifyLoginFail(String message) {
      WebUI.waitForPageLoaded();
      //WebUI.waitForElementVisible(errorMessage);
      Assert.assertTrue(WebUI.checkElementExist(errorMessage, 10, 1000), "Error message NOT displays");
      WebUI.assertEquals(WebUI.getElementText(errorMessage), message, "Content of error massage NOT match.");
      //Assert.assertTrue(driver.getCurrentUrl().contains("authentication"), "FAIL. Không còn ở trang Login");
      WebUI.assertContains(WebUI.getCurrentURL(), "authentication", "FAIL. Không còn ở trang Login");
   }

   public void verifyLoginFailWithEmailAndPasswordNull() {
      boolean checkEmailErrorMessage = WebUI.checkElementExist(alertEmailRequiredMessage, 5, 1000);
      Assert.assertTrue(checkEmailErrorMessage, "Fail. The Email Error Message is not present");

      boolean checkPasswordErrorMessage = WebUI.checkElementExist(alertPasswordRequiredMessage, 5, 1000);
      Assert.assertTrue(checkPasswordErrorMessage, "Fail. The Password Error Message is not present");

      Assert.assertEquals(WebUI.getCurrentURL(), "https://crm.anhtester.com/admin/authentication", "The Current LOGIN_URL is not correct");
   }

   public void verifyAlertEmailFormatInvalid() {
//      String emailMessage = driver.findElement(inputEmail).getAttribute("validationMessage");
//      System.out.println(emailMessage);

      Assert.assertEquals(WebUI.getElementAttribute(inputEmail, "validationMessage"), "Please enter a part following '@'. 'admin@' is incomplete.", "Fail. The HTML5 Error Message is not match.");
   }

   //Các hàm xử lý cho chính trang này
   public DashboardPage loginCRM(String email, String password) {
      //https://crm.anhtester.com/admin/authentication
      WebUI.openURL(ConfigData.LOGIN_URL); //Gọi từ class ConfigData dạng biến static
      verifyNavigateToTheLoginPage();
      setEmail(email);
      setPassword(password);
      clickLoginButton();

      return new DashboardPage(driver);
   }

   public DashboardPage loginCRM_AdminRole() {
      WebUI.openURL(ConfigData.LOGIN_URL);
      verifyNavigateToTheLoginPage();
      setEmail(ConfigData.EMAIL_ADMIN);
      setPassword(ConfigData.PASSWORD_ADMIN);
      clickLoginButton();
      verifyLoginSuccess();

      return new DashboardPage(driver);
   }

}
