# STQA Task 2 - Selenium

A small beginner-friendly Java and Maven project for three Selenium WebDriver practical scenarios on [SauceDemo](https://www.saucedemo.com/).

## Project contents

- `pom.xml` - Maven project settings and Selenium dependency.
- `src/main/java/STQA_Task2_Selenium.java` - runs the three scenarios in order.

## Requirements

- Java JDK 17 or newer
- Maven 3.6 or newer
- Google Chrome installed
- Internet connection for Maven to download Selenium dependencies and for the website

No separate ChromeDriver download or setup is needed. Selenium Manager, included with Selenium, finds and manages the matching driver automatically.

## Open the project

### VS Code

1. Extract the ZIP file.
2. Open VS Code and select **File > Open Folder**.
3. Choose the extracted `STQA_Task2_Selenium` folder.
4. If prompted, install the **Extension Pack for Java**.

### IntelliJ IDEA

1. Extract the ZIP file.
2. Select **File > Open** and choose the extracted `STQA_Task2_Selenium` folder (or its `pom.xml`).
3. Allow IntelliJ to load or import the project as a Maven project.

## Install dependencies and run all scenarios

Open a terminal in the project folder (the folder containing `pom.xml`) and run:

```text
mvn clean compile
mvn exec:java
```

The first command downloads Maven dependencies and compiles the Java program. The second runs the program. You can also refresh Maven dependencies in your IDE using its Maven reload/reimport action.

The program runs all three scenarios in order, opening and closing Chrome for each one:

1. Valid login with `standard_user` / `secret_sauce`; checks that the Products page appears.
2. Invalid login; checks that the login error appears.
3. Valid login; adds the first two products, checks the cart, removes one product, and checks that one remains.

## Expected console output

When the scenarios pass, the console should include messages similar to these:

```text
Valid Login Test: PASSED - Products page is displayed.
Invalid Login Test: PASSED - Error message is displayed.
Product Cart Test: PASSED - Both products are displayed in the cart.
Product Cart Test: PASSED - One product remains after removal.
```

The exact timing and browser messages may vary. A `FAILED` message means a check did not match the expected page state. If Chrome does not open, check that Chrome is installed and that your internet connection is available.

## Simple code explanation (for viva)

- `main()` calls the three test methods one after another.
- Each test creates a Chrome browser with `new ChromeDriver()` and a `WebDriverWait` that waits up to 10 seconds for a page element or condition.
- The `login()` helper enters the supplied username and password and clicks the Login button. It is just a small reusable method, not a Page Object Model.
- The valid-login test checks the page URL and the title text `Products`.
- The invalid-login test waits for SauceDemo's error message and checks that it is visible.
- The cart test clicks the first two Add to cart buttons, checks that two cart items appear, removes one, then checks that one remains.
- Simple `if` conditions print `PASSED` or `FAILED` messages to the console.
- A `finally` block calls `driver.quit()` so each browser closes even if a test encounters an error.
- Maven reads the Selenium dependency from `pom.xml`. Selenium Manager handles ChromeDriver setup automatically.

These are simple browser automation checks, not a TestNG test suite. No fake screenshots or reports are included.
