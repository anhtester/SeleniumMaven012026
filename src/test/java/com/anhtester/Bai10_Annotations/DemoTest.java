package com.anhtester.Bai10_Annotations;

import org.testng.Assert;
import org.testng.annotations.Test;

public class DemoTest {

   @Test(priority = 1, description = "Test login CRM success")
   public void testLoginCRM() throws Exception {
      System.out.println("Đây là test case login CRM");
      throw new Exception("Test case login CRM failed!");
   }

   @Test(priority = 2, description = "Test logout CRM", enabled = false)
   public void testLogoutCRM() {
      System.out.println("Đây là test case logout CRM");
   }

   @Test(priority = 3, description = "Test login thất bại với email không hợp lệ", timeOut = 2000)
   public void testLoginFailWithEmailInvalid() throws InterruptedException {
      Thread.sleep(3000);
      System.out.println("Đây là test case login thất bại với email không hợp lệ");
   }

   @Test(dependsOnMethods = {"testLoginCRM"}, alwaysRun = true, description = "Test case thêm mới khách hàng")
   public void testAddNewCustomer() {
      System.out.println("Đây là test case thêm mới khách hàng");
   }

}
