import java.time.Duration;
import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

/**
 * Simple Selenium practical: login checks and shopping-cart checks.
 * Selenium Manager automatically finds/sets up the matching ChromeDriver.
 */
public class STQA_Task2_Selenium {
    private static final String WEBSITE = "https://www.saucedemo.com/";
    private static final String VALID_USERNAME = "standard_user";
    private static final String VALID_PASSWORD = "secret_sauce";

    public static void main(String[] args) {
        runValidLoginTest();
        runInvalidLoginTest();
        runProductCartTest();
    }

    private static void runValidLoginTest() {
        WebDriver driver = new ChromeDriver();
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));

        try {
            driver.get(WEBSITE);
            login(driver, wait, VALID_USERNAME, VALID_PASSWORD);

            wait.until(ExpectedConditions.urlContains("inventory.html"));
            boolean productsPageDisplayed = driver.findElement(By.className("title")).getText().equals("Products");

            if (productsPageDisplayed) {
                System.out.println("Valid Login Test: PASSED - Products page is displayed.");
            } else {
                System.out.println("Valid Login Test: FAILED - Products page was not displayed.");
            }
        } finally {
            driver.quit();
        }
    }

    private static void runInvalidLoginTest() {
        WebDriver driver = new ChromeDriver();
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));

        try {
            driver.get(WEBSITE);
            login(driver, wait, "wrong_user", "wrong_password");

            WebElement errorMessage = wait.until(
                    ExpectedConditions.visibilityOfElementLocated(By.cssSelector("[data-test='error']")));
            boolean errorDisplayed = errorMessage.isDisplayed();

            if (errorDisplayed) {
                System.out.println("Invalid Login Test: PASSED - Error message is displayed.");
            } else {
                System.out.println("Invalid Login Test: FAILED - Error message was not displayed.");
            }
        } finally {
            driver.quit();
        }
    }

    private static void runProductCartTest() {
        WebDriver driver = new ChromeDriver();
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));

        try {
            driver.get(WEBSITE);
            login(driver, wait, VALID_USERNAME, VALID_PASSWORD);
            wait.until(ExpectedConditions.urlContains("inventory.html"));

            // Add the first two products shown on the Products page.
            List<WebElement> addButtons = wait.until(
                    ExpectedConditions.visibilityOfAllElementsLocatedBy(
                            By.cssSelector("button[data-test^='add-to-cart']")));
            addButtons.get(0).click();
            addButtons.get(1).click();

            wait.until(ExpectedConditions.textToBe(By.className("shopping_cart_badge"), "2"));
            driver.findElement(By.className("shopping_cart_link")).click();

            List<WebElement> cartProducts = wait.until(
                    ExpectedConditions.visibilityOfAllElementsLocatedBy(By.className("cart_item")));
            boolean bothProductsDisplayed = cartProducts.size() == 2;

            if (bothProductsDisplayed) {
                System.out.println("Product Cart Test: PASSED - Both products are displayed in the cart.");
            } else {
                System.out.println("Product Cart Test: FAILED - Expected 2 products, found " + cartProducts.size() + ".");
            }

            // Remove one item and check that one item remains.
            driver.findElement(By.cssSelector("button[data-test^='remove']")).click();
            wait.until(ExpectedConditions.numberOfElementsToBe(By.className("cart_item"), 1));
            int remainingProducts = driver.findElements(By.className("cart_item")).size();

            if (remainingProducts == 1) {
                System.out.println("Product Cart Test: PASSED - One product remains after removal.");
            } else {
                System.out.println("Product Cart Test: FAILED - Expected 1 product, found " + remainingProducts + ".");
            }
        } finally {
            driver.quit();
        }
    }

    private static void login(WebDriver driver, WebDriverWait wait, String username, String password) {
        WebElement usernameField = wait.until(
                ExpectedConditions.visibilityOfElementLocated(By.id("user-name")));
        usernameField.sendKeys(username);
        driver.findElement(By.id("password")).sendKeys(password);
        driver.findElement(By.id("login-button")).click();
    }
}
