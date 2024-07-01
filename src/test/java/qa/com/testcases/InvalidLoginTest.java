package qa.com.testcases;

import org.openqa.selenium.WebDriver;
import org.testng.Assert;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;
import qa.com.config.ConfigReader;
import qa.com.general.WebDriverFactory;
import qa.com.pageObjects.DashboardPage;
import qa.com.pageObjects.LoginPage;

import java.awt.*;
import java.util.List;

public class InvalidLoginTest {
    static WebDriver driver;
    LoginPage loginPage;
    DashboardPage dashboardPage;
    ConfigReader configReader;

    @BeforeClass
    public void setUp() throws AWTException {
        driver = WebDriverFactory.getInstance();
        loginPage = new LoginPage(driver);
        dashboardPage = new DashboardPage(driver);
        configReader = new ConfigReader();
    }

    @Test(priority = 1)
    public void loginWithInvalidCredentials() {
        // Get credentials from config file
        String username = configReader.getUsername();
        String invalidPassword = configReader.getInvalidPassword();

        // Perform login
        loginPage.login(username, invalidPassword);

        loginPage.verifyError("Epic sadface: Username and password do not match any user in this service");
    }

    @AfterClass
    public void tearDown() {
        WebDriverFactory.finishDriver();
    }
}
