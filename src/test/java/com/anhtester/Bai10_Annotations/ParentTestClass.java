package com.anhtester.Bai10_Annotations;

import org.testng.annotations.*;

public class ParentTestClass {
   @BeforeSuite
   public void beforeSuiteParent(){
      System.out.println("\uD83C\uDFAF PARENT - Đây là before suite 1");
   }

   @AfterSuite
   public void afterSuiteParent(){
      System.out.println("\uD83C\uDFAF PARENT - Đây là after suite 1");
   }

   @BeforeTest
   public void beforeTestParent() {
      System.out.println("\uD83C\uDFAF PARENT - beforeTest 1: Chạy trước tất cả các test trong một thẻ <test>");
   }

   @AfterTest
   public void afterTestParent() {
      System.out.println("\uD83C\uDFAF PARENT - afterTest 1: Chạy sau tất cả các test trong một thẻ <test>");
   }

   @BeforeClass
   public void beforeClassParent() {
      System.out.println("\uD83C\uDFAF PARENT - beforeClass 1: Chạy trước tất cả các test trong class này");
      System.out.println("Thực hiện Login trước test case trong class này bằng email password");
   }

   @AfterClass
   public void afterClassParent() {
      System.out.println("\uD83C\uDFAF PARENT - afterClass 1: Chạy sau tất cả các test trong class này");
   }

   @BeforeGroups("smoke")
   public void beforeGroupParent() {
      System.out.println("\uD83C\uDFAF PARENT - beforeGroup 1: Chạy trước nhóm smoke");
   }

   @BeforeMethod
   public void beforeMethodParent() {
      System.out.println("\uD83C\uDFAF PARENT - beforeMethod 1: Chạy trước mỗi phương thức test - Mở Browser");
   }

   @AfterMethod
   public void afterMethodParent() {
      System.out.println("\uD83C\uDFAF PARENT - afterMethod 1: Chạy sau mỗi phương thức test - Đóng Browser");
   }
}
