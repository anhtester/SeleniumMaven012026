package com.anhtester.Bai22_ThucHanhPOM.testcases;

import com.anhtester.Bai22_ThucHanhPOM.pages.CustomersPage;
import com.anhtester.Bai22_ThucHanhPOM.pages.LoginPage;
import com.anhtester.common.BaseTest;
import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

public class CustomersTest extends BaseTest {

   private LoginPage loginPage;
   private CustomersPage customersPage;

   @BeforeMethod
   public void setUp() {
      loginPage = new LoginPage(driver);
      customersPage = new CustomersPage(driver);
   }

   @Test
   public void testAddNewCustomer() {
      String timestamp = String.valueOf(System.currentTimeMillis());
      String companyName = "AUTO_POM_ADD_CUSTOMER_" + timestamp;
      String vatNumber = "VAT" + timestamp;
      String phone = String.format("090%07d", Long.parseLong(timestamp.substring(timestamp.length() - 7)));
      String website = "https://anhtester.com";
      String address = "123 Automation Street";
      String city = "Can Tho";
      String state = "Can Tho";
      String zipCode = "90000";
      String country = "Vietnam";

      loginPage.loginCRM_AdminRole();
      customersPage.openCustomersPage();
      customersPage.verifyNavigateToCustomersPage();
      customersPage.clickNewCustomerButton();
      customersPage.verifyNavigateToAddNewCustomerPage();

      customersPage.fillCustomerDetails(companyName, vatNumber, phone, website);
      customersPage.selectDefaultCurrency("USD");
      customersPage.selectDefaultLanguage("Vietnamese");
      customersPage.fillAddress(address, city, state, zipCode, country);
      customersPage.clickSaveButton();
      customersPage.waitForCustomerProfilePage();

      Assert.assertEquals(customersPage.getPageTitle(), companyName, "Tên công ty trên title trang profile không đúng.");
      Assert.assertEquals(customersPage.getCompanyValue(), companyName, "Company lưu không đúng.");
      Assert.assertEquals(customersPage.getVatNumberValue(), vatNumber, "VAT Number lưu không đúng.");
      Assert.assertEquals(customersPage.getPhoneValue(), phone, "Phone lưu không đúng.");
      Assert.assertEquals(customersPage.getWebsiteValue(), website, "Website lưu không đúng.");
      Assert.assertEquals(customersPage.getAddressValue(), address, "Address lưu không đúng.");
      Assert.assertEquals(customersPage.getCityValue(), city, "City lưu không đúng.");
      Assert.assertEquals(customersPage.getStateValue(), state, "State lưu không đúng.");
      Assert.assertEquals(customersPage.getZipCodeValue(), zipCode, "Zip Code lưu không đúng.");
      Assert.assertEquals(customersPage.getSelectedCountryValue(), country, "Country lưu không đúng.");
      Assert.assertEquals(customersPage.getSelectedDefaultCurrencyValue(), "USD", "Default Currency lưu không đúng.");
      Assert.assertEquals(customersPage.getSelectedDefaultLanguageValue(), "Vietnamese", "Default Language lưu không đúng.");
   }

   @Test
   public void testDeleteCustomer() {
      String timestamp = String.valueOf(System.currentTimeMillis());
      String companyName = "AUTO_POM_DELETE_CUSTOMER_" + timestamp;
      String vatNumber = "VAT" + timestamp;
      String phone = String.format("091%07d", Long.parseLong(timestamp.substring(timestamp.length() - 7)));
      String website = "https://anhtester.com";

      loginPage.loginCRM_AdminRole();
      customersPage.openCustomersPage();
      customersPage.clickNewCustomerButton();
      customersPage.fillCustomerDetails(companyName, vatNumber, phone, website);
      customersPage.clickSaveButton();
      customersPage.waitForCustomerProfilePage();

      customersPage.openCustomersPage();
      Assert.assertTrue(customersPage.isCustomerDisplayed(companyName), "Customer vừa tạo phải hiển thị trong danh sách trước khi xóa.");

      customersPage.deleteCustomerByCompanyName(companyName);

      Assert.assertTrue(customersPage.isCustomerNotDisplayed(companyName), "Customer vừa xóa không được còn hiển thị trong danh sách.");
   }

   @Test
   public void testDeleteCustomerByHoverAndConfirmAlert() {
      String timestamp = String.valueOf(System.currentTimeMillis());
      String companyName = "AUTO_POM_DELETE_HOVER_" + timestamp;
      String vatNumber = "VAT" + timestamp;
      String phone = String.format("092%07d", Long.parseLong(timestamp.substring(timestamp.length() - 7)));
      String website = "https://anhtester.com";

      loginPage.loginCRM_AdminRole();
      customersPage.openCustomersPage();
      customersPage.clickNewCustomerButton();
      customersPage.fillCustomerDetails(companyName, vatNumber, phone, website);
      customersPage.clickSaveButton();
      customersPage.waitForCustomerProfilePage();

      customersPage.openCustomersPage();
      Assert.assertTrue(customersPage.isCustomerDisplayed(companyName), "Customer vừa tạo phải hiển thị trong danh sách trước khi xóa.");

      customersPage.deleteCustomerByHoverAndConfirmAlert(companyName);

      Assert.assertTrue(customersPage.isCustomerNotDisplayed(companyName), "Customer vừa xóa qua hover + confirm alert không được còn hiển thị trong danh sách.");
   }
}
