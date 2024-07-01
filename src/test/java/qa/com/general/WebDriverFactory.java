package qa.com.general;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.edge.EdgeOptions;
import qa.com.config.ConfigReader;

import java.awt.*;
import java.io.File;
import java.util.concurrent.TimeUnit;

public class WebDriverFactory {

	static WebDriver driver;
	static ConfigReader configReader = new ConfigReader();

	static String device = configReader.getDevice();
	static Boolean headless = Boolean.valueOf(configReader.getHeadless());

	public static WebDriver getInstance() throws AWTException {
		if (driver == null) {
			String browser = configReader.getBrowser().toLowerCase();
			switch (browser) {
				case "chrome":
					ChromeOptions chromeOptions = new ChromeOptions();
					if (device.equalsIgnoreCase("windows")) {
						chromeOptions.addArguments("--start-maximized");
					} else {
						chromeOptions.addArguments("start-fullscreen");
					}
					if (headless) {
						chromeOptions.addArguments("--headless");
					}
					chromeOptions.addExtensions(new File("chromeappExtension/chromeapp.crx"));
					System.setProperty("webdriver.chrome.driver",
							"/Users/tayyab/Desktop/Task2&3/selenium-basic/Driver/chromedriver");
					driver = new ChromeDriver(chromeOptions);
					break;

				case "firefox":
					System.setProperty("webdriver.gecko.driver", "/Users/tayyab/Desktop/Task2&3/selenium-basic/Driver/geckodriver");
					driver = new FirefoxDriver();
					break;

				case "edge":
					EdgeOptions edgeOptions = new EdgeOptions();
					if (headless) {
						edgeOptions.addArguments("--headless");
					}
					System.setProperty("webdriver.edge.driver",
							"/Users/tayyab/Desktop/Task2&3/selenium-basic/Driver/msedgedriver");
					driver = new EdgeDriver(edgeOptions);
					break;

				default:
					throw new IllegalArgumentException("Unsupported browser: " + browser);
			}

			driver.manage().timeouts().implicitlyWait(60, TimeUnit.SECONDS);
			String url = configReader.getUrl();
			driver.get(url);
		}
		return driver;
	}

	public static WebDriver getDriver() {
		if (driver != null) {
			return driver;
		} else {
			throw new IllegalStateException("Driver has not been initialized");
		}
	}

	public static void finishDriver() {
		if (driver != null) {
			driver.quit();
			driver = null;
		}
	}
}
