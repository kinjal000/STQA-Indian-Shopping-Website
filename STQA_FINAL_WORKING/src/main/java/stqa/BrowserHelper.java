package stqa;

import java.nio.file.Path;
import java.time.Duration;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.support.ui.WebDriverWait;

public class BrowserHelper {
    public static WebDriver openWebsite() {
        ChromeOptions options = new ChromeOptions();
        options.addArguments("--start-maximized");
        WebDriver driver = new ChromeDriver(options);
        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(2));
        Path page = Path.of("src", "main", "resources", "site", "index.html").toAbsolutePath();
        driver.get(page.toUri().toString());
        return driver;
    }

    public static WebDriverWait waitFor(WebDriver driver) {
        return new WebDriverWait(driver, Duration.ofSeconds(10));
    }

    public static void login(WebDriver driver, String username, String password) {
        driver.findElement(org.openqa.selenium.By.id("username")).sendKeys(username);
        driver.findElement(org.openqa.selenium.By.id("password")).sendKeys(password);
        driver.findElement(org.openqa.selenium.By.id("loginButton")).click();
    }

    public static void keepBrowserOpen(WebDriver driver) {
        System.out.println("\nBrowser is still open. Press ENTER in this terminal when you want to close it.");
        try {
            System.in.read();
        } catch (Exception ignored) {
        } finally {
            driver.quit();
        }
    }
}
