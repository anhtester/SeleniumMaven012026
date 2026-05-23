package com.anhtester.Bai10_Annotations;

import org.testng.annotations.*;

public class DemoAnnotations_1 extends ParentTestClass {

   @BeforeSuite
   public void beforeSuite(){
      System.out.println("Đây là before suite 1");
   }

   @AfterSuite
   public void afterSuite(){
      System.out.println("Đây là after suite 1");
   }

   @BeforeTest
   public void beforeTest() {
      System.out.println("beforeTest 1: Chạy trước tất cả các test trong một thẻ <test>");
   }

   @AfterTest
   public void afterTest() {
      System.out.println("afterTest 1: Chạy sau tất cả các test trong một thẻ <test>");
   }

   @BeforeClass
   public void beforeClass() {
      System.out.println("beforeClass 1: Chạy trước tất cả các test trong class này");
      System.out.println("Thực hiện Login trước test case trong class này bằng email password");
   }

   @AfterClass
   public void afterClass() {
      System.out.println("afterClass 1: Chạy sau tất cả các test trong class này");
   }

   @BeforeGroups("smoke")
   public void beforeGroup() {
      System.out.println("beforeGroup 1: Chạy trước nhóm smoke");
   }

   @BeforeMethod
   public void beforeMethod() {
      System.out.println("beforeMethod 1: Chạy trước mỗi phương thức test - Mở Browser");
   }

   @AfterMethod
   public void afterMethod() {
      System.out.println("afterMethod 1: Chạy sau mỗi phương thức test - Đóng Browser");
   }

   @Test(groups = "smoke")
   public void test_method_01(){
      System.out.println("Đây là test method 01");
   }

   @Test
   public void test_method_02(){
      System.out.println("Đây là test method 02");
   }

   @Test
   public void test_method_03(){
      System.out.println("Đây là test method 03");
   }
}
