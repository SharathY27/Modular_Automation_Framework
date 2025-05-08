package base;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.edge.EdgeDriver;

import utils.GlobalVariables;

public class Singleton_Driver {

	private static WebDriver driver = null;

	private Singleton_Driver() {

	};

	public static WebDriver getSingletonDriver() {
		if (driver == null) {
			driver = setupDriver(GlobalVariables.browserName);
			return driver;
		}
		return driver;
	}

	public static void quitDriver() {
		if (driver != null) {
			driver.quit();
			driver = null; // ❗ Reset it here
		}
	}

	public static WebDriver setupDriver(String browserName) {
		switch (browserName) {
		case "chrome":
			return new ChromeDriver();
		case "edge":
			return new EdgeDriver();
		default:
			return new ChromeDriver();
		}
	}

}
