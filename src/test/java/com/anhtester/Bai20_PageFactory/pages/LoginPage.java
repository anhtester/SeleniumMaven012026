package com.anhtester.Bai20_PageFactory.pages;

import com.anhtester.constants.ConfigData;
import com.anhtester.keywords.WebUI;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.CacheLookup;
import org.openqa.selenium.support.FindAll;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;

import java.time.Duration;

public class LoginPage extends BasePage {

   //Khai báo driver cục bộ trong chính class này
   private WebDriver driver;
   private WebDriverWait wait;

   //Khai báo các element dạng đối tượng By (phương thức tìm kiếm)
//   private By headerPage = By.xpath("//h1[normalize-space()='Login']");
//   private By inputEmail = By.xpath("//input[@id='email']");
//   private By inputPassword = By.xpath("//input[@id='password']");
//   private By buttonLogin = By.xpath("//button[normalize-space()='Login']");
//   private By errorMessage = By.xpath("//div[contains(@class,'alert-danger')]");
//   private By alertEmailRequiredMessage = By.xpath("//div[normalize-space()='The Email Address field is required.']");
//   private By alertPasswordRequiredMessage = By.xpath("//div[normalize-space()='The Password field is required.']");

   @FindBy(xpath = "//h1[normalize-space()='Login']")
   private WebElement headerPage;

   @FindAll({
           @FindBy(id = "email123"),
           @FindBy(xpath = "//input[@id='email123']"),
           @FindBy(xpath = "//input[@id='email']"),
   })
   private WebElement inputEmail;

   @CacheLookup
   @FindBy(xpath = "//input[@id='password']")
   private WebElement inputPassword;

   @CacheLookup
   @FindBy(xpath = "//button[normalize-space()='Login']")
   private WebElement buttonLogin;

   @FindBy(xpath = "//div[contains(@class,'alert-danger')]")
   private WebElement errorMessage;

   @FindBy(xpath = "//div[normalize-space()='The Email Address field is required.']")
   private WebElement alertEmailRequiredMessage;

   @FindBy(xpath = "//div[normalize-space()='The Password field is required.']")
   private WebElement alertPasswordRequiredMessage;


   //Khai báo hàm xây dựng, để truyền driver từ bên ngoài vào chính class này sử dụng
   public LoginPage(WebDriver driver) {
      super(driver);
      this.driver = driver; //Truyền giá trị cho driver
      //driver = _driver;
      wait = new WebDriverWait(driver, Duration.ofSeconds(5)); //Khởi tạo giá trị cho wait

      //PageFactory.initElements(driver, LoginPage.class);
      PageFactory.initElements(driver, this);
   }

   //Khai báo các hàm xử lý automation phục vụ cho trang Login
   private void setEmail(String email) {
      wait.until(ExpectedConditions.visibilityOf(inputEmail));
      inputEmail.sendKeys(email);
   }

   private void setPassword(String password) {
      wait.until(ExpectedConditions.visibilityOf(inputPassword));
      inputPassword.sendKeys(password);
   }

   private void clickLoginButton() {
      wait.until(ExpectedConditions.visibilityOf(buttonLogin));
      buttonLogin.click();
   }

   public void verifyLoginSuccess() {
      wait.until(ExpectedConditions.urlContains("/admin/"));
      Assert.assertTrue(driver.getCurrentUrl().contains("/admin/"), "FAIL. Không chuyển hướng sang trang Dashboard");
      Assert.assertFalse(driver.getCurrentUrl().contains("authentication"), "FAIL. Vẫn đang ở trang Login");
   }

   public void verifyLoginFail(String message) {
      wait.until(ExpectedConditions.visibilityOf(errorMessage));
      Assert.assertTrue(errorMessage.isDisplayed(), "Error message NOT displays");
      Assert.assertEquals(errorMessage.getText(), message, "Content of error massage NOT match.");
      Assert.assertTrue(driver.getCurrentUrl().contains("authentication"), "FAIL. Không còn ở trang Login");
   }

   public void verifyLoginFailWithEmailAndPasswordNull() {
      boolean checkEmailErrorMessage = WebUI.isElementVisible(driver, alertEmailRequiredMessage, 5);
      Assert.assertTrue(checkEmailErrorMessage, "Fail. The Email Error Message is not present");

      boolean checkPasswordErrorMessage = WebUI.isElementVisible(driver, alertPasswordRequiredMessage, 5);
      Assert.assertTrue(checkPasswordErrorMessage, "Fail. The Password Error Message is not present");

      Assert.assertEquals(driver.getCurrentUrl(), "https://crm.anhtester.com/admin/authentication", "The Current LOGIN_URL is not correct");
   }

   public void verifyAlertEmailFormatInvalid() {
      String emailMessage = inputEmail.getAttribute("validationMessage");
      System.out.println(emailMessage);

      Assert.assertEquals(emailMessage, "Please enter a part following '@'. 'admin@' is incomplete.", "Fail. The HTML5 Error Message is not match.");
   }

   //Các hàm xử lý cho chính trang này
   public void loginCRM(String email, String password) {
      //https://crm.anhtester.com/admin/authentication
      driver.get(ConfigData.LOGIN_URL); //Gọi từ class ConfigData dạng biến static
      setEmail(email);
      setPassword(password);
      clickLoginButton();
   }

   public void loginCRM_AdminRole() {
      driver.get(ConfigData.LOGIN_URL);
      setEmail(ConfigData.EMAIL_ADMIN);
      setPassword(ConfigData.PASSWORD_ADMIN);
      clickLoginButton();
      verifyLoginSuccess();
   }

}
