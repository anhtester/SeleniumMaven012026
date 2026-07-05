package com.anhtester.Bai11_Assert;

import com.anhtester.common.BaseTest;
import org.openqa.selenium.By;
import org.testng.Assert;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;
import org.testng.asserts.SoftAssert;

public class DemoSoftAssert extends BaseTest {

   public SoftAssert softAssert;

   @BeforeClass
   public void setUp() {
      softAssert = new SoftAssert();
   }

   @AfterClass
   public void tearDown() {
      softAssert.assertAll();
   }

   @Test
   public void testSoftAssert() throws InterruptedException {
      softAssert = new SoftAssert();
      driver.get("https://crm.anhtester.com/admin/authentication");
      driver.findElement(By.xpath("//input[@type='email']")).sendKeys("admin@example.com");
      driver.findElement(By.xpath("//input[@type='password']")).sendKeys("123456");
      driver.findElement(By.xpath("//button[normalize-space()='Login']")).click();
      Thread.sleep(2000);

      Assert.assertEquals(driver.getCurrentUrl(), "https://crm.anhtester.com/admin/", "LOGIN_URL không đúng sau khi đăng nhập");

      try {
         driver.findElement(By.xpath("//li[contains(@class, 'header-user-profile')]")).isDisplayed();
      } catch (Exception e) {
         Assert.fail("Menu Profile not displayed. Exception: " + e.getMessage());
      }

      driver.findElement(By.xpath("//span[normalize-space()='Customers']")).click();

      String headerText = driver.findElement(By.xpath("//span[normalize-space()='Customers Summary']")).getText();
      softAssert.assertEquals(headerText, "Customers Summary", "Header customer not match.");

      try {
         driver.findElement(By.xpath("//a[normalize-space()='New Customer']")).isDisplayed();
      } catch (Exception e) {
         Assert.fail("Button Add new Customer not displayed. Exception: " + e.getMessage());
      }

      driver.findElement(By.xpath("//a[normalize-space()='New Customer']")).click();

      try {
         driver.findElement(By.xpath("//label[@for='company']//small[@class='req text-danger'][normalize-space()='*']")).isDisplayed();
      } catch (Exception e) {
         softAssert.fail("The red Star (Company required field) not displayed. Exception: " + e.getMessage());
      }

      driver.findElement(By.xpath("//input[@id='company']")).sendKeys("AN_TEST_1704A1");
      driver.findElement(By.xpath("//input[@id='vat']")).sendKeys("10");
      driver.findElement(By.xpath("//input[@id='phonenumber']")).sendKeys("0939206009");
      driver.findElement(By.id("website")).sendKeys("https://anhtester.com");
//      driver.findElement(By.xpath("//button[@data-id='groups_in[]']")).click();
//      Thread.sleep(1000);
//      driver.findElement(By.xpath("//button[@data-id='groups_in[]']/following-sibling::div//input")).sendKeys("VIP");
//      Thread.sleep(2000);
//      driver.findElement(By.xpath("//span[normalize-space()='VIP']")).click();
//      Thread.sleep(1000);
//      driver.findElement(By.xpath("//button[@data-id='groups_in[]']")).click();
//      driver.findElement(By.xpath("//textarea[@id='address']")).sendKeys("123 Nguyen Van Linh, Da Nang");
//      driver.findElement(By.xpath("//input[@id='city']")).sendKeys("Da Nang");
//      driver.findElement(By.xpath("//input[@id='state']")).sendKeys("Da Nang");
//      driver.findElement(By.id("zip")).sendKeys("550000");
//      Thread.sleep(1000);
//      driver.findElement(By.xpath("//button[@data-id='country']")).click();
//      Thread.sleep(1000);
//      driver.findElement(By.xpath("//button[@data-id='country']/following-sibling::div//input")).sendKeys("Vietnam");
//      Thread.sleep(1000);
//      driver.findElement(By.xpath("//span[normalize-space()='Vietnam']")).click();

      softAssert.assertAll();
   }
}
