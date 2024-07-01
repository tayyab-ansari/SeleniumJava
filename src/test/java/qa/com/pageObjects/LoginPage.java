package qa.com.pageObjects;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.testng.Assert;

public class LoginPage {
    private WebDriver driver;

    // Locators for the login elements
    private By usernameField = By.id("user-name");
    private By passwordField = By.id("password");
    private By loginButton = By.id("login-button");
    private By errorLocator = By.cssSelector("h3[data-test='error']");

    private By productPageTitle = By.cssSelector("span[data-test=\"title\"]");

    public LoginPage(WebDriver driver) {
        this.driver = driver;
    }

    // Method to enter the username
    public void enterUsername(String username) {
        driver.findElement(usernameField).sendKeys(username);
    }

    // Method to enter the password
    public void enterPassword(String password) {
        driver.findElement(passwordField).sendKeys(password);
    }

    // Method to click the login button
    public void clickLoginButton() {
        driver.findElement(loginButton).click();
    }

    // Method to perform login
    public void login(String username, String password) {
        enterUsername(username);
        enterPassword(password);
        clickLoginButton();
    }

    public void verifyError(String expectedErrorMessage) {
        WebElement errorElement = driver.findElement(errorLocator);

        // Get the text from the error element
        String actualErrorMessage = errorElement.getText();

        // Verify the error message
        Assert.assertEquals(actualErrorMessage, expectedErrorMessage, "Error message does not match!");
    }

    public void verifySuccessfulLogin(String expectedTitle) {
        WebElement titleElement = driver.findElement(productPageTitle);

        // Get the text from the error element
        String actualTitle = titleElement.getText();

        // Verify the error message
        Assert.assertEquals(actualTitle, expectedTitle, "Login Failed");
    }


}
