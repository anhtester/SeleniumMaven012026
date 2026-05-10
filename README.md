# 📚 SeleniumMaven012026

> Source code khóa học **Selenium Java 01/2026** — Anh Tester  
> Dự án sử dụng **Selenium WebDriver 4.43** + **Java 17** + **Maven** + **TestNG 7.12** để thực hành tự động hóa kiểm thử trình duyệt web.

---

## 📋 Mục lục

- [Yêu cầu hệ thống](#-yêu-cầu-hệ-thống)
- [Cài đặt và chạy dự án](#-cài-đặt-và-chạy-dự-án)
- [Công nghệ sử dụng](#-công-nghệ-sử-dụng)
- [Cấu trúc dự án](#-cấu-trúc-dự-án)
- [Nội dung bài học](#-nội-dung-bài-học)
  - [Bài 5 — Locators](#bài-5--locators)
  - [Bài 6 — WebElement](#bài-6--webelement)
  - [Bài 7 — WebDriver](#bài-7--webdriver)
  - [Bài 8 — Checkbox, Radio, Dropdown](#bài-8--checkbox-radio-dropdown)
  - [Bài 9 — TestNG Framework](#bài-9--testng-framework)
- [Cách chạy test](#-cách-chạy-test)
- [Giấy phép](#-giấy-phép)

---

## 💻 Yêu cầu hệ thống

| Thành phần       | Yêu cầu                                       |
| ---------------- | ---------------------------------------------- |
| **Java JDK**     | 17 hoặc cao hơn                                |
| **Maven**        | 3.x                                            |
| **Trình duyệt**  | Chrome, Firefox, Edge (bất kỳ trình duyệt nào) |
| **IDE**          | IntelliJ IDEA, VS Code hoặc Antigravity        |

> **Lưu ý:** Selenium 4.x tự động quản lý WebDriver thông qua Selenium Manager — không cần tải `chromedriver` thủ công.

---

## 🚀 Cài đặt và chạy dự án

1. **Clone repository:**
   ```bash
   git clone https://github.com/anhtester/SeleniumMaven012026.git
   cd SeleniumMaven012026
   ```

2. **Mở dự án** trong IDE (IntelliJ IDEA khuyến nghị).

3. **Tải dependencies:**
   ```bash
   mvn clean install -DskipTests
   ```

4. **Chạy toàn bộ test:**
   ```bash
   mvn test
   ```

---

## 🛠 Công nghệ sử dụng

| Thư viện / Tool          | Phiên bản | Mục đích                                    |
| ------------------------ | --------- | -------------------------------------------- |
| **Selenium Java**        | 4.43.0    | Tự động hóa trình duyệt web                 |
| **TestNG**               | 7.12.0    | Framework quản lý và thực thi test case      |
| **SLF4J API**            | 2.0.17    | Logging API chuẩn                            |
| **SLF4J Simple**         | 2.0.17    | Implementation đơn giản cho SLF4J            |
| **Maven Surefire Plugin**| 3.5.5     | Plugin chạy test và tích hợp TestNG suite    |

---

## 📁 Cấu trúc dự án

```
SeleniumMaven012026/
├── pom.xml                          # Cấu hình Maven & dependencies
├── README.md
├── LICENSE
│
├── src/
│   ├── main/java/com/anhtester/
│   │   └── Main.java               # Entry point (demo)
│   │
│   └── test/
│       ├── java/com/anhtester/
│       │   ├── DemoSelenium.java            # Demo Selenium cơ bản
│       │   ├── TestSelenium.java            # Các test case Selenium đơn giản
│       │   │
│       │   ├── Bai5_Locators/               # 📌 Bài 5: Locators
│       │   │   ├── LocatorsHTML.java
│       │   │   ├── LocatorsXPath_01_05.java
│       │   │   └── LocatorsXPath_06_10.java
│       │   │
│       │   ├── Bai6_WebElement/             # 📌 Bài 6: WebElement
│       │   │   ├── DemoWebElement01.java
│       │   │   ├── DemoWebElement02.java
│       │   │   └── DemoWebElement03.java
│       │   │
│       │   ├── Bai7_WebDriver/              # 📌 Bài 7: WebDriver
│       │   │   ├── DemoBasicBrowser.java
│       │   │   ├── DemoAdvancedBrowser.java
│       │   │   ├── DemoCookiesBrowser.java
│       │   │   ├── DemoCookiesBrowser_Auto.java
│       │   │   └── ListWebElement_FindElements.java
│       │   │
│       │   ├── Bai8_Checkbox_Radio_Dropdown/ # 📌 Bài 8: Checkbox, Radio, Dropdown
│       │   │   ├── HandleCheckbox.java
│       │   │   ├── HandleRadio.java
│       │   │   ├── HandleDropdownStatic.java
│       │   │   └── HandleDropdownDynamic.java
│       │   │
│       │   └── Bai9_TestNG/                 # 📌 Bài 9: TestNG Framework
│       │       ├── DemoTestNG.java
│       │       └── SeleniumTestNG.java
│       │
│       └── resources/suites/                # TestNG Suite XML
│           ├── SuiteLoginTest.xml
│           └── SuiteCustomerTest.xml
│
└── target/                          # Thư mục output (auto-generated)
```

---

## 📖 Nội dung bài học

### Bài 5 — Locators

> Tìm kiếm phần tử trên trang web bằng các loại Locator khác nhau.

| File                         | Nội dung                                                         |
| ---------------------------- | ---------------------------------------------------------------- |
| `LocatorsHTML.java`          | Sử dụng 8 HTML Locators cơ bản: `id`, `name`, `className`, `tagName`, `linkText`, `partialLinkText`, `cssSelector`, `xpath` |
| `LocatorsXPath_01_05.java`   | XPath cấp 1–5: Absolute, Relative, Attribute, Contains, Text    |
| `LocatorsXPath_06_10.java`   | XPath cấp 6–10: Axes (parent, child, following-sibling, preceding-sibling, ancestor) |

**Kiến thức chính:**
- Phân biệt Absolute XPath (`/html/body/...`) và Relative XPath (`//tag[@attr='value']`)
- Sử dụng các hàm XPath: `contains()`, `text()`, `normalize-space()`, `starts-with()`
- Kết hợp nhiều điều kiện với `and`, `or`

---

### Bài 6 — WebElement

> Tương tác với các phần tử (element) trên trang web.

| File                   | Nội dung                                                           |
| ---------------------- | ------------------------------------------------------------------ |
| `DemoWebElement01.java` | Lấy thông tin CSS: `getCssValue()` — màu sắc, font, border        |
| `DemoWebElement02.java` | Lấy kích thước và vị trí: `getSize()`, `getLocation()`, `getAttribute()` |
| `DemoWebElement03.java` | Tương tác form: `sendKeys()`, `click()`, `submit()` và xử lý bảng dữ liệu (table) |

**Kiến thức chính:**
- Lấy thông tin CSS properties của element
- Lấy kích thước (`Dimension`) và vị trí (`Point`) của element
- Đọc giá trị attribute HTML bằng `getAttribute()`
- Nhập dữ liệu vào input, click button, submit form
- Duyệt qua các hàng/cột trong bảng HTML (`<table>`)

---

### Bài 7 — WebDriver

> Làm quen với các thao tác điều khiển trình duyệt và quản lý session.

| File                                 | Nội dung                                                              |
| ------------------------------------ | --------------------------------------------------------------------- |
| `DemoBasicBrowser.java`              | Navigate (`to`, `back`, `forward`, `refresh`), `getTitle()`, `getCurrentUrl()`, `getPageSource()` |
| `DemoAdvancedBrowser.java`           | Quản lý cửa sổ và tab: `newWindow()`, `switchTo()`, `close()`, `quit()` |
| `DemoCookiesBrowser.java`            | Thêm cookie thủ công vào trình duyệt                                 |
| `DemoCookiesBrowser_Auto.java`       | Quản lý cookies tự động: `getCookies()`, `deleteCookies()`           |
| `ListWebElement_FindElements.java`   | Sử dụng `findElements()` để lấy danh sách elements và duyệt qua     |

**Kiến thức chính:**
- Điều hướng trình duyệt: tới URL, quay lại, tiến tới, reload
- Lấy thông tin trang: title, URL hiện tại, page source
- Mở và chuyển đổi giữa nhiều tab/cửa sổ trình duyệt
- Thêm, đọc, xóa cookies (thủ công và tự động)
- Phân biệt `findElement()` (1 element) và `findElements()` (danh sách)
- Phân biệt `close()` (đóng tab hiện tại) và `quit()` (tắt toàn bộ trình duyệt)

---

### Bài 8 — Checkbox, Radio, Dropdown

> Xử lý các thành phần form phổ biến: Checkbox, Radio Button và Dropdown.

| File                          | Nội dung                                                             |
| ----------------------------- | -------------------------------------------------------------------- |
| `HandleCheckbox.java`         | Kiểm tra trạng thái, click chọn/bỏ chọn checkbox đơn lẻ và hàng loạt |
| `HandleRadio.java`            | Kiểm tra và chọn radio button, kiểm tra chỉ 1 radio được chọn       |
| `HandleDropdownStatic.java`   | Xử lý dropdown tĩnh (`<select>`) bằng class `Select`                |
| `HandleDropdownDynamic.java`  | Xử lý dropdown động (custom dropdown) bằng cách click + search      |

**Kiến thức chính:**
- **Checkbox:**
  - `isSelected()` — kiểm tra checkbox đã được chọn chưa
  - `click()` — chọn/bỏ chọn checkbox
  - `findElements()` — lấy danh sách nhiều checkbox và duyệt qua từng cái
  - Kiểm tra tất cả checkbox đã được chọn đúng hay chưa
- **Radio Button:**
  - `isSelected()` — kiểm tra radio đã chọn chưa
  - Sử dụng `JavascriptExecutor` để cuộn trang (`scrollTo`)
  - Đảm bảo chỉ có đúng 1 radio button được chọn trong group
- **Dropdown tĩnh (`<select>`):**
  - `Select` class: `selectByVisibleText()`, `selectByValue()`, `selectByIndex()`
  - `getFirstSelectedOption()` — lấy option đang được chọn
  - `isMultiple()` — kiểm tra có phải multi-select dropdown không
- **Dropdown động (custom):**
  - Click mở dropdown → nhập text tìm kiếm → click chọn kết quả
  - Sử dụng XPath axes (`parent`, `following-sibling`) để định vị element

---

### Bài 9 — TestNG Framework

> Giới thiệu TestNG — framework quản lý test case chuyên nghiệp cho Java.

| File                    | Nội dung                                                               |
| ----------------------- | ---------------------------------------------------------------------- |
| `DemoTestNG.java`       | Demo cơ bản TestNG: annotation `@Test`, viết test case đơn giản        |
| `SeleniumTestNG.java`   | Tích hợp Selenium với TestNG: `@BeforeMethod`, `@Test`, `@AfterMethod` |

**TestNG Suite XML:**

| File                      | Mô tả                                                               |
| ------------------------- | -------------------------------------------------------------------- |
| `SuiteLoginTest.xml`      | Chạy class `SeleniumTestNG` — test login và navigation               |
| `SuiteCustomerTest.xml`   | Chạy class `DemoTestNG` — chạy song song với `parallel="tests"`      |

**Kiến thức chính:**
- **Annotations:**
  - `@Test` — đánh dấu method là test case
  - `@BeforeMethod` — chạy trước mỗi `@Test` (khởi tạo driver)
  - `@AfterMethod` — chạy sau mỗi `@Test` (đóng driver)
- **TestNG Suite XML:**
  - Cấu hình suite để chạy nhiều test class cùng lúc
  - Chạy song song: `parallel="tests"`, `thread-count="2"`
- **Tích hợp Maven:**
  - Plugin `maven-surefire-plugin` chạy suite XML qua lệnh `mvn test`
  - Cấu hình nhiều `suiteXmlFile` trong `pom.xml`

---

## ▶ Cách chạy test

### Chạy từ IDE
- Mở file test → Click chuột phải → **Run** (hoặc **Run As TestNG Test**)

### Chạy bằng Maven

```bash
# Chạy tất cả suite XML đã cấu hình trong pom.xml
mvn test

# Chạy một class cụ thể
mvn test -Dtest=com.anhtester.Bai9_TestNG.SeleniumTestNG
# Hoặc dùng dấu ngoặc kép (khuyên dùng trên PowerShell / Windows)
mvn test "-Dtest=com.anhtester.Bai9_TestNG.SeleniumTestNG"

# Chạy với suite XML cụ thể
mvn test -DsuiteXmlFile=src/test/resources/suites/SuiteLoginTest.xml
# Hoặc dùng dấu ngoặc kép (khuyên dùng trên PowerShell / Windows)
mvn test "-DsuiteXmlFile=src/test/resources/suites/SuiteLoginTest.xml"

# Clean và chạy test
mvn clean test
```

### Chạy TestNG Suite XML
- Mở file `.xml` trong thư mục `src/test/resources/suites/`
- Click chuột phải → **Run As TestNG Suite** (IntelliJ / Eclipse)

---

## 📄 Giấy phép

Dự án này được phân phối dưới giấy phép **MIT**. Xem file [LICENSE](LICENSE) để biết thêm chi tiết.

---

> **Tác giả:** [Anh Tester](https://anhtester.com) — Khóa học Selenium Java 01/2026
