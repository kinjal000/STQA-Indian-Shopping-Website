package stqa;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;

public class InvalidLoginTest {
    public static void main(String[] args) {
        WebDriver driver = BrowserHelper.openWebsite();
        try {
            BrowserHelper.login(driver, "wrong_user", "wrong_password");
            String message = BrowserHelper.waitFor(driver).until(
                ExpectedConditions.visibilityOfElementLocated(By.id("loginError"))
            ).getText();
            if (message.contains("incorrect")) {
                System.out.println("TEST PASSED: Invalid login was rejected and error message is displayed.");
            } else {
                System.out.println("TEST FAILED: Expected error message was not displayed.");
            }
            BrowserHelper.keepBrowserOpen(driver);
        } catch (Exception e) {
            System.out.println("TEST FAILED: " + e.getMessage());
            driver.quit();
        }
    }
}
