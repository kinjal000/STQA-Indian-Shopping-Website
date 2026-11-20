package stqa;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;

public class ProductCartTest {
    public static void main(String[] args) {
        WebDriver driver = BrowserHelper.openWebsite();
        try {
            BrowserHelper.login(driver, "student", "shop123");
            BrowserHelper.waitFor(driver).until(
                ExpectedConditions.visibilityOfElementLocated(By.id("productGrid"))
            );

            driver.findElement(By.cssSelector("[data-test='add-1']")).click();
            driver.findElement(By.cssSelector("[data-test='add-2']")).click();
            driver.findElement(By.id("cartTop")).click();

            int before = driver.findElements(By.cssSelector(".cart-row")).size();
            if (before == 2) {
                System.out.println("Two different products are displayed in the cart.");
            }

            driver.findElement(By.cssSelector("[data-test='remove-2']")).click();
            int after = driver.findElements(By.cssSelector(".cart-row")).size();

            if (after == 1) {
                System.out.println("TEST PASSED: Two products added, one removed, one product remains.");
            } else {
                System.out.println("TEST FAILED: Expected one product in the cart, found " + after);
            }
            BrowserHelper.keepBrowserOpen(driver);
        } catch (Exception e) {
            System.out.println("TEST FAILED: " + e.getMessage());
            driver.quit();
        }
    }
}
