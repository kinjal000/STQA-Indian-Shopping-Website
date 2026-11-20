package stqa;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;

public class ValidLoginTest {
    public static void main(String[] args) {
        WebDriver driver = BrowserHelper.openWebsite();
        try {
            BrowserHelper.login(driver, "student", "shop123");
            BrowserHelper.waitFor(driver).until(
                ExpectedConditions.visibilityOfElementLocated(By.id("productGrid"))
            );
            if (driver.findElement(By.id("productsPage")).isDisplayed()) {
                System.out.println("TEST PASSED: Valid login successful. Products are displayed.");
            } else {
                System.out.println("TEST FAILED: Products page not displayed.");
            }
            BrowserHelper.keepBrowserOpen(driver);
        } catch (Exception e) {
            System.out.println("TEST FAILED: " + e.getMessage());
            driver.quit();
        }
    }
}
