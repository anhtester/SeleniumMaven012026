package com.anhtester.Bai10_Annotations;

import org.testng.annotations.*;

public class DemoAnnotations_2 {

   @BeforeSuite
   public void beforeSuite(){
      System.out.println("Đây là before suite 2");
   }

   @AfterSuite
   public void afterSuite(){
      System.out.println("Đây là after suite 2");
   }

   @BeforeTest
   public void beforeTest() {
      System.out.println("beforeTest 2: Chạy trước tất cả các test trong một thẻ <test>");
   }

   @AfterTest
   public void afterTest() {
      System.out.println("afterTest 2: Chạy sau tất cả các test trong một thẻ <test>");
   }

   @BeforeClass
   public void beforeClass() {
      System.out.println("beforeClass 2: Chạy trước tất cả các test trong class này");
      System.out.println("Thực hiện Login trước test case trong class này bằng cách get Cookie từ file config");
   }

   @AfterClass
   public void afterClass() {
      System.out.println("afterClass 2: Chạy sau tất cả các test trong class này");
   }

   @Test
   public void test_method_04(){
      System.out.println("Đây là test method 01");
   }

   @Test
   public void test_method_05(){
      System.out.println("Đây là test method 02");
   }

   @Test
   public void test_method_06(){
      System.out.println("Đây là test method 03");
   }
}
