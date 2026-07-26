package com.anhtester.keywords;

import org.openqa.selenium.*;
import org.openqa.selenium.support.ui.*;
import org.testng.Assert;

import java.time.Duration;
import java.util.List;
import java.util.Optional;
import java.util.function.Function;

public class WebUI {

    private static WebDriver driver;
    private static WebDriverWait wait;

    private static int TIMEOUT = 10;
    private static double STEP_TIME = 0;
    private static int PAGE_LOAD_TIMEOUT = 20;
    private static int RETRY_TIMEOUT = 15;
    private static int RETRY_POLLING_MILLIS = 500;
    //Thời gian chờ kết quả cho MỖI lần gõ từ khoá vào ô search ajax
    private static int SEARCH_TIMEOUT = 5;

    public WebUI(WebDriver driver) {
        this.driver = driver;
    }

    //Retry - Thao tác với thành phần giao diện tự vẽ lại DOM (Datatable, modal...)

    /**
     * Tạo vòng chờ có thể thử lại, bỏ qua các lỗi mang tính nhất thời.
     * Dùng khi thao tác trên thành phần tự vẽ lại DOM như Datatable: node tìm được ở vòng trước
     * có thể bị thay mới ở vòng sau, vòng chờ này sẽ tự tìm lại thay vì ném lỗi ra ngoài.
     */
    public static Wait<WebDriver> retryWait() {
        return new FluentWait<>(driver)
                .withTimeout(Duration.ofSeconds(RETRY_TIMEOUT))
                .pollingEvery(Duration.ofMillis(RETRY_POLLING_MILLIS))
                .ignoring(NoSuchElementException.class)
                .ignoring(StaleElementReferenceException.class)
                .ignoring(ElementClickInterceptedException.class)
                //Element có trong DOM nhưng chưa thao tác được: đang ẩn, đang chạy hiệu ứng,
                //hoặc là link chỉ hiện khi hover mà trạng thái hover chưa kịp ăn.
                .ignoring(ElementNotInteractableException.class);
    }

    /**
     * Lặp lại điều kiện cho tới khi trả về giá trị khác null và khác false.
     * Mọi element phải được tìm lại BÊN TRONG điều kiện, không truyền WebElement từ ngoài vào,
     * vì WebElement chỉ là tham chiếu tới node cũ và không tự tìm lại khi node đó bị thay mới.
     *
     * @param condition Điều kiện cần lặp lại, nhận WebDriver và trả về kết quả mong muốn
     * @return Giá trị mà điều kiện trả về khi thoả
     */
    public static <T> T retryUntil(Function<WebDriver, T> condition) {
        return retryWait().until(condition);
    }

    //Search - Ô tìm kiếm ajax của selectpicker (Customer, Project, Related To...)

    /**
     * Gõ từ khoá vào ô search ajax của selectpicker rồi chờ option hiện ra.
     * Loại ô search này rất hay hụt: plugin chỉ gắn handler ajax sau khi dropdown mở xong,
     * gõ sớm hơn thì không có request nào được bắn đi và danh sách trống trơn.
     * Vì vậy nếu hết thời gian chờ mà option chưa hiện, hàm sẽ xoá sạch ô search và gõ lại từ đầu,
     * lặp tối đa maxRetries lần trước khi báo fail.
     *
     * @param buttonDropdown Nút mở dropdown, dùng để mở lại nếu dropdown bị đóng giữa chừng
     * @param searchBox      Ô nhập từ khoá bên trong dropdown
     * @param option         Option cần chờ hiện ra sau khi search
     * @param keyword        Từ khoá cần gõ
     * @param maxRetries     Số lần được phép gõ lại
     * @return Option đã hiện ra
     */
    public static WebElement searchSelectPickerOption(By buttonDropdown, By searchBox, By option, String keyword, int maxRetries) {
        return searchSelectPickerOption(buttonDropdown, searchBox, option, keyword, maxRetries, SEARCH_TIMEOUT);
    }

    public static WebElement searchSelectPickerOption(By buttonDropdown, By searchBox, By option, String keyword, int maxRetries, int timeOutPerTry) {
        for (int attempt = 1; attempt <= maxRetries; attempt++) {
            openDropdownIfClosed(buttonDropdown, searchBox);

            //Xoá sạch rồi gõ lại từ đầu để ép plugin bắn một request ajax mới
            retryUntil(_driver -> {
                WebElement input = _driver.findElement(searchBox);
                input.clear();
                input.sendKeys(keyword);
                return true;
            });

            try {
                WebDriverWait waitOption = new WebDriverWait(driver, Duration.ofSeconds(timeOutPerTry), Duration.ofMillis(500));
                WebElement found = waitOption.until(ExpectedConditions.visibilityOfElementLocated(option));
                logConsole("✅ Thấy kết quả '" + keyword + "' ở lần search thứ " + attempt + "/" + maxRetries);
                return found;
            } catch (Throwable error) {
                logConsole("⚠️ Lần search thứ " + attempt + "/" + maxRetries + " chưa thấy '" + keyword + "', xoá ô search và gõ lại.");
            }
        }

        logConsole("❌ Không tìm thấy '" + keyword + "' sau " + maxRetries + " lần search.");
        Assert.fail("FAILED. Không tìm thấy kết quả '" + keyword + "' sau " + maxRetries + " lần search. Option: " + option);
        return null;
    }

    /**
     * Chỉ bấm mở dropdown khi ô search đang không hiển thị.
     * Bấm khi dropdown đang mở sẽ làm nó đóng lại, nên phải kiểm tra trước.
     */
    private static void openDropdownIfClosed(By buttonDropdown, By searchBox) {
        boolean isOpened = false;
        try {
            List<WebElement> searchBoxes = getWebElements(searchBox);
            isOpened = !searchBoxes.isEmpty() && searchBoxes.get(0).isDisplayed();
        } catch (StaleElementReferenceException ignored) {
        }

        if (!isOpened) {
            clickElement(buttonDropdown);
            waitForElementVisible(searchBox);
        }
    }

    //Wait for Element

    public static void waitForElementVisible(By by) {
        try {
            WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(TIMEOUT), Duration.ofMillis(500));
            wait.until(ExpectedConditions.visibilityOfElementLocated(by));
        } catch (Throwable error) {
            logConsole("Timeout waiting for the element Visible. " + by.toString());
            Assert.fail("Timeout waiting for the element Visible. " + by.toString());
        }
    }

    public static void waitForElementVisible(By by, int timeOut) {
        try {
            WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(timeOut), Duration.ofMillis(500));
            wait.until(ExpectedConditions.visibilityOfElementLocated(by));
        } catch (Throwable error) {
            logConsole("Timeout waiting for the element Visible. " + by.toString());
            Assert.fail("Timeout waiting for the element Visible. " + by.toString());
        }
    }

    public static void waitForElementPresent(By by) {
        try {
            WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(TIMEOUT), Duration.ofMillis(500));
            wait.until(ExpectedConditions.presenceOfElementLocated(by));
        } catch (Throwable error) {
            logConsole("Element not exist. " + by.toString());
            Assert.fail("Element not exist. " + by.toString());
        }
    }

    public static void waitForElementPresent(By by, int timeOut) {
        try {
            WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(timeOut), Duration.ofMillis(500));
            wait.until(ExpectedConditions.presenceOfElementLocated(by));
        } catch (Throwable error) {
            logConsole("Element not exist. " + by.toString());
            Assert.fail("Element not exist. " + by.toString());
        }
    }

    public static void waitForElementClickable(By by) {
        try {
            WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(TIMEOUT), Duration.ofMillis(500));
            //Truyền By chứ KHÔNG truyền WebElement: bản nhận By sẽ tìm lại element ở mỗi vòng poll,
            //còn bản nhận WebElement giữ mãi node cũ, node đó bị thay mới là chờ tới hết giờ vô ích.
            wait.until(ExpectedConditions.elementToBeClickable(by));
        } catch (Throwable error) {
            logConsole("Timeout waiting for the element ready to click. " + by.toString());
            Assert.fail("Timeout waiting for the element ready to click. " + by.toString());
        }
    }

    public static void waitForElementClickable(By by, int timeOut) {
        try {
            WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(timeOut), Duration.ofMillis(500));
            wait.until(ExpectedConditions.elementToBeClickable(by));
        } catch (Throwable error) {
            logConsole("Timeout waiting for the element ready to click. " + by.toString());
            Assert.fail("Timeout waiting for the element ready to click. " + by.toString());
        }
    }

    //Chờ đợi trang load xong mới thao tác
    public static void waitForPageLoaded() {
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(PAGE_LOAD_TIMEOUT), Duration.ofMillis(500));
        JavascriptExecutor js = (JavascriptExecutor) driver;

        //Wait for Javascript to load
        ExpectedCondition<Boolean> jsLoad = new ExpectedCondition <Boolean> () {
            @Override
            public Boolean apply(WebDriver driver) {
                return js.executeScript("return document.readyState").toString().equals("complete");
            }
        };

        //Check JS is Ready
        boolean jsReady = js.executeScript("return document.readyState").toString().equals("complete");

        //Wait Javascript until it is Ready!
        if (!jsReady) {
            //System.out.println("Javascript is NOT Ready.");
            //Wait for Javascript to load
            try {
                wait.until(jsLoad);
            } catch (Throwable error) {
                error.printStackTrace();
                Assert.fail("FAILED. Timeout waiting for page load.");
            }
        }

        //document.readyState = complete chỉ nói HTML đã tải xong,
        //các request ajax chạy sau đó vẫn có thể đang vẽ lại giao diện nên phải chờ tiếp.
        waitForJQueryLoad();
        waitForAngularLoad();
    }

    /**
     * Chờ jQuery chạy xong toàn bộ request ajax đang treo (jQuery.active == 0).
     * Cần thiết với các thành phần tự vẽ lại DOM như Datatable, nếu không
     * các element tìm được trước đó sẽ bị stale khi bảng vẽ lại.
     */
    public static void waitForJQueryLoad() {
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(PAGE_LOAD_TIMEOUT));
        try {
            wait.until(_driver -> (Boolean) ((JavascriptExecutor) driver)
                    .executeScript(
                            "return window.jQuery == undefined || jQuery.active == 0"));
        } catch (Exception ignored) {
        }
    }

    public static void waitForAngularLoad() {
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(PAGE_LOAD_TIMEOUT));
        try {
            wait.until(_driver -> (Boolean) ((JavascriptExecutor) driver)
                    .executeScript(
                            "return window.getAllAngularTestabilities ? " +
                                    "window.getAllAngularTestabilities()" +
                                    ".every(x=>x.isStable()) : true"));
        } catch (Exception ignored) {
        }
    }

    public static void sleep(double second) {
        try {
            Thread.sleep((long)(1000 * second));
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }

    public static void logConsole(Object message) {
        System.out.println(message);
    }

    public static WebElement getWebElement(By by) {
        return driver.findElement(by);
    }

    public static List<WebElement> getWebElements(By by) {
        return driver.findElements(by);
    }

    /**
     * Verify if a web element is present (findElements.size > 0).
     *
     * @param by Represent a web element as the By object
     * @return true/false
     */
    public static boolean checkElementExist(By by) {
        boolean result = false;

        List<WebElement> elementList = getWebElements(by);
        if (elementList.size() > 0) {
            System.out.println("✅ Element " + by + " existing.");
            result = true;
        } else {
            System.out.println("❌ Element " + by + " NOT exists.");
            result = false;
        }
        return result;
    }

    // Hàm kiểm tra sự tồn tại của phần tử với lặp lại nhiều lần dùng FluentWait
    public static boolean checkElementExist(By by, int maxRetries, int waitTimeMillis) {
        System.out.println("Kiểm tra tồn tại phần tử với retry: " + by);

        long totalTimeoutMillis = (long) maxRetries * waitTimeMillis;

        try {
            // FluentWait tương tự như vòng lặp của bạn nhưng hiệu quả hơn,
            // không block thread của hệ thống bằng Thread.sleep().
            Wait<WebDriver> wait = new FluentWait<>(driver)
                    .withTimeout(Duration.ofMillis(totalTimeoutMillis)) // Tổng thời gian chờ tối đa
                    .pollingEvery(Duration.ofMillis(waitTimeMillis)) // Tần suất lặp lại (Polling)
                    .ignoring(NoSuchElementException.class) // Tiếp tục lặp nếu không tìm thấy element
                    .ignoring(StaleElementReferenceException.class); // Tiếp tục lặp nếu element bị thay đổi

            WebElement element = wait.until(ExpectedConditions.presenceOfElementLocated(by));

            if (element != null) {
                System.out.println("✅ Tồn tại phần tử: " + by);
                return true;
            }
        } catch (TimeoutException e) {
            System.out.println("❌ Không tìm thấy phần tử sau " + maxRetries + " lần thử.");
            return false;
        }
        return false;
    }

    /**
     * Cuộn element vào giữa màn hình. Cần cho các element nằm ngoài vùng nhìn thấy,
     * đặc biệt khi phải hover: chuột không di tới được element đang nằm ngoài viewport.
     */
    public static void scrollToElement(WebElement element) {
        ((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView({block:'center'});", element);
    }

    public static void openURL(String url) {
        driver.get(url);
        sleep(STEP_TIME);
        logConsole("Open URL:  " + url);
    }

    public static void clickElement(By by) {
        waitForElementClickable(by);
        sleep(STEP_TIME);
        //Tìm lại element ngay trước khi click, và thử lại nếu node bị thay mới đúng lúc đó
        retryUntil(_driver -> {
            _driver.findElement(by).click();
            return true;
        });
        logConsole("Click on element " + by);
    }

    public static void clickElement(By by, int timeout) {
        waitForElementClickable(by, timeout);
        sleep(STEP_TIME);
        retryUntil(_driver -> {
            _driver.findElement(by).click();
            return true;
        });
        logConsole("Click on element " + by);
    }

    /**
     * LƯU Ý: hàm này có clear() trước khi gõ.
     * Bắt buộc phải có, vì nếu element bị thay mới giữa chừng thì lần thử lại sẽ gõ đè lên
     * phần chữ đã gõ dở, cho ra chuỗi kiểu "helhello". Clear trước thì gõ lại bao nhiêu lần
     * kết quả vẫn đúng bằng value.
     */
    public static void setText(By by, String value) {
        waitForElementVisible(by);
        sleep(STEP_TIME);
        retryUntil(_driver -> {
            WebElement element = _driver.findElement(by);
            element.clear();
            element.sendKeys(value);
            return true;
        });
        logConsole("Set text " + value + " on element " + by);
    }

    public static String getElementText(By by) {
        waitForElementVisible(by);
        sleep(STEP_TIME);
        logConsole("Get text of element " + by);
        String text = retryUntil(_driver -> _driver.findElement(by).getText());
        logConsole("==> TEXT: " + text);
        return text; //Trả về một giá trị kiểu String
    }

    public static String getElementAttribute(By by, String attributeName) {
        waitForElementVisible(by);
        System.out.println("Get attribute of element " + by);
        //Bọc Optional vì getAttribute trả về null khi element không có attribute đó,
        //mà retryUntil hiểu null là "chưa xong" nên sẽ lặp tới hết giờ rồi ném TimeoutException.
        String value = retryUntil(_driver -> Optional.ofNullable(_driver.findElement(by).getAttribute(attributeName)))
                .orElse(null);
        System.out.println("==> Attribute value: " + value);
        return value;
    }

    public static String getElementCssValue(By by, String cssPropertyName) {
        waitForElementVisible(by);
        System.out.println("Get CSS value " + cssPropertyName + " of element " + by);
        String value = retryUntil(_driver -> _driver.findElement(by).getCssValue(cssPropertyName));
        System.out.println("==> CSS value: " + value);
        return value;
    }

}
