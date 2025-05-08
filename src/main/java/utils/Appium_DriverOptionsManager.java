package utils;

import io.appium.java_client.android.options.UiAutomator2Options;
import io.appium.java_client.ios.options.XCUITestOptions;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public class Appium_DriverOptionsManager {

	// This class currently created for mobile testing through wireless debugging

	private static Properties props = new Properties();

	static {
		try (InputStream inputStream = ClassLoader.getSystemClassLoader()
				.getResourceAsStream("Config/appium.properties")) {
			if (inputStream == null) {
				throw new FileNotFoundException("config.properties not found in resources/Config folder");
			}
			props.load(inputStream);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public static String getAppPackage() {
		return props.getProperty("appPackage");
	}

	public static String getPlatformName() {
		return props.getProperty("platformName");
	}

	public static UiAutomator2Options getAndroidOptions() {
		return new UiAutomator2Options().setPlatformName(props.getProperty("platformName"))
				.setDeviceName(props.getProperty("deviceName")).setUdid(props.getProperty("udid"))
				.setPlatformVersion(props.getProperty("platformVersion")).setAppPackage(props.getProperty("appPackage"))
				.setAppActivity(props.getProperty("appActivity")).setAutomationName(props.getProperty("automationName"))
				.setNoReset(Boolean.parseBoolean(props.getProperty("noReset")));
	}

	public static XCUITestOptions getIOSOptions() {
		return new XCUITestOptions().setPlatformName(props.getProperty("platformName"))
				.setDeviceName(props.getProperty("deviceName")).setUdid(props.getProperty("udid"))
				.setPlatformVersion(props.getProperty("platformVersion")).setBundleId(props.getProperty("bundleId"))
				.setAutomationName(props.getProperty("automationName"))
				.setNoReset(Boolean.parseBoolean(props.getProperty("noReset")));
	}
}
