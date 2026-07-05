package com.anhtester.Bai17_PageObjectModel.pages;

import com.anhtester.constants.ConfigData;
import com.anhtester.keywords.WebUI;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;

import java.time.Duration;

public class LoginPage extends BasePage{

   //Khai báo driver cục bộ trong chính class này
   private WebDriver driver;
   private WebDriverWait wait;

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
      //driver = _driver;
      wait = new WebDriverWait(driver, Duration.ofSeconds(5)); //Khởi tạo giá trị cho wait
   }

   //Khai báo các hàm xử lý automation phục vụ cho trang Login
   private void setEmail(String email) {
      wait.until(ExpectedConditions.visibilityOfElementLocated(inputEmail));
      driver.findElement(inputEmail).sendKeys(email);
   }

   private void setPassword(String password) {
      wait.until(ExpectedConditions.visibilityOfElementLocated(inputPassword));
      driver.findElement(inputPassword).sendKeys(password);
   }

   private void clickLoginButton() {
      wait.until(ExpectedConditions.visibilityOfElementLocated(buttonLogin));
      driver.findElement(buttonLogin).click();
   }

   public void verifyLoginSuccess() {
      wait.until(ExpectedConditions.urlContains("/admin/"));
      Assert.assertTrue(driver.getCurrentUrl().contains("/admin/"), "FAIL. Không chuyển hướng sang trang Dashboard");
      Assert.assertFalse(driver.getCurrentUrl().contains("authentication"), "FAIL. Vẫn đang ở trang Login");
   }

   public void verifyLoginFail(String message) {
      wait.until(ExpectedConditions.visibilityOfElementLocated(errorMessage));
      Assert.assertTrue(driver.findElement(errorMessage).isDisplayed(), "Error message NOT displays");
      Assert.assertEquals(driver.findElement(errorMessage).getText(), message, "Content of error massage NOT match.");
      Assert.assertTrue(driver.getCurrentUrl().contains("authentication"), "FAIL. Không còn ở trang Login");
   }

   public void verifyLoginFailWithEmailAndPasswordNull(){
      boolean checkEmailErrorMessage = WebUI.isElementPresent(driver, alertEmailRequiredMessage, 5);
      Assert.assertTrue(checkEmailErrorMessage, "Fail. The Email Error Message is not present");

      boolean checkPasswordErrorMessage = WebUI.isElementPresent(driver, alertPasswordRequiredMessage, 5);
      Assert.assertTrue(checkPasswordErrorMessage, "Fail. The Password Error Message is not present");

      Assert.assertEquals(driver.getCurrentUrl(), "https://crm.anhtester.com/admin/authentication", "The Current LOGIN_URL is not correct");
   }

   public void verifyAlertEmailFormatInvalid() {
      String emailMessage = driver.findElement(inputEmail).getAttribute("validationMessage");
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
