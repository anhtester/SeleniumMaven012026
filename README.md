# SeleniumMaven012026

Đây là dự án source code dành cho khóa học Selenium Java 01/2026. Dự án này sử dụng Selenium WebDriver với Java và Maven để thực hiện các bài tập và demo về tự động hóa trình duyệt web.

## Mục lục
- [Yêu cầu hệ thống](#yêu-cầu-hệ-thống)
- [Cài đặt](#cài-đặt)
- [Cấu trúc dự án](#cấu-trúc-dự-án)
- [Cách chạy](#cách-chạy)
- [Bài học](#bài-học)
- [Giấy phép](#giấy-phép)

## Yêu cầu hệ thống
- Java JDK 17 hoặc cao hơn
- Maven 3.x
- Có cài Trình duyệt web (Chrome, Firefox, etc.)
- IDE như IntelliJ IDEA hoặc VS Code, Antigravity

## Cài đặt
1. Clone repository này về máy:
   ```
   git clone https://github.com/anhtester/SeleniumMaven012026.git
   ```
2. Mở dự án trong IDE của bạn.
3. Đảm bảo Maven dependencies được tải về bằng cách chạy:
   ```
   mvn clean install
   ```

## Cấu trúc dự án
- `src/main/java/com/anhtester/`: Chứa code chính (ví dụ: Main.java)
- `src/test/java/com/anhtester/`: Chứa các file test Selenium
  - `DemoSelenium.java`: Demo cơ bản về Selenium
  - `TestSelenium.java`: Các test case Selenium
  - `Bai5_Locators/`: Bài học về Locators
    - `LocatorsHTML.java`
    - `LocatorsXPath_01_05.java`
    - `LocatorsXPath_06_10.java`
  - `Bai6_WebElement/`: Bài học về WebElement
    - `DemoWebElement01.java`
    - `DemoWebElement02.java`
    - `DemoWebElement03.java`
- `pom.xml`: File cấu hình Maven
- `target/`: Thư mục chứa các file compiled

## Cách chạy
Để chạy các test Selenium:
1. Đảm bảo WebDriver được cài đặt và đường dẫn được cấu hình (ví dụ: chromedriver.exe trong PATH).
2. Chạy test từ IDE hoặc sử dụng Maven:
   ```
   mvn test
   ```
3. Để chạy một class cụ thể:
   ```
   mvn test -Dtest=DemoSelenium
   ```

## Bài học
Dự án này bao gồm các bài học về:
- **Cài đặt và cấu hình Selenium WebDriver**
- **Bai5 - Locators**: Sử dụng HTML Locators và XPath để tìm kiếm các phần tử trên trang web
  - HTML Locators cơ bản
  - XPath cấp 1-5 và cấp 6-10
- **Bai6 - WebElement**: Tương tác với các element trên trang web
  - Lấy thông tin CSS (getCssValue)
  - Lấy kích thước và vị trí element (getSize, getLocation)
  - Lấy giá trị thuộc tính (getAttribute)
  - Tương tác với form (submit, sendKeys, click)
  - Xử lý bảng dữ liệu (table)
- **Viết test case tự động**

## Giấy phép
Dự án này được phân phối dưới giấy phép MIT. Xem file [LICENSE](LICENSE) để biết thêm chi tiết.
