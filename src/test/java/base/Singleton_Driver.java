package base;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.edge.EdgeDriver;
import utils.GlobalVariables;

import java.io.File;

public class Singleton_Driver {

	private static WebDriver driver = null;

	private Singleton_Driver() {}

	public static WebDriver getSingletonDriver() {
		if (driver == null) {
			driver = setupDriver(GlobalVariables.browserName);
		}
		return driver;
	}

	public static void quitDriver() {
		if (driver != null) {
			driver.quit();
			driver = null;
		}
	}

	public static WebDriver setupDriver(String browserName) {
		switch (browserName.toLowerCase()) {
			case "chrome":
				ChromeOptions chromeOptions = new ChromeOptions();

				// ✅ Enable headless mode for CI
				chromeOptions.addArguments("--headless=new");

				// Required in most CI environments
				chromeOptions.addArguments("--no-sandbox");
				chromeOptions.addArguments("--disable-dev-shm-usage");
				chromeOptions.addArguments("--disable-gpu");

				// Unique user data dir (optional when headless)
				String uniqueUserDir = System.getProperty("java.io.tmpdir") + File.separator + "chrome-profile-" + System.currentTimeMillis();
				chromeOptions.addArguments("--user-data-dir=" + uniqueUserDir);

				return new ChromeDriver(chromeOptions);

			case "edge":
				return new EdgeDriver();

			default:
				return new ChromeDriver(); // fallback
		}
	}
}
