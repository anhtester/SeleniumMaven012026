package com.anhtester.Bai9_TestNG;

import org.testng.annotations.Test;

public class DemoTestNG {
   @Test
   public void testLoginCRMSuccess() {
      System.out.println("Đây là test case Login CRM");
   }

   @Test
   public void testLoginCRMFailWithEmailInvalid() {
      System.out.println("Đây là test case Login CRM với email không hợp lệ");
   }
}