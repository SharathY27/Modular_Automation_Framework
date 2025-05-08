package base;

import java.io.File;
import java.lang.reflect.Method;
import java.net.MalformedURLException;
import java.net.URL;

import org.testng.ITestResult;
import org.testng.annotations.*;

import com.aventstack.extentreports.*;
import com.aventstack.extentreports.markuputils.ExtentColor;
import com.aventstack.extentreports.markuputils.MarkupHelper;
import com.aventstack.extentreports.reporter.ExtentHtmlReporter;
import com.aventstack.extentreports.reporter.configuration.Theme;

import io.appium.java_client.AppiumDriver;
import io.appium.java_client.InteractsWithApps;
import io.appium.java_client.android.AndroidDriver;
import io.appium.java_client.ios.IOSDriver;
import io.appium.java_client.android.options.UiAutomator2Options;
import io.appium.java_client.ios.options.XCUITestOptions;
import utils.Appium_DriverOptionsManager;

public class BaseTestForMobileAutomation {

	public static AppiumDriver driver = null;
	public ExtentHtmlReporter extentHtmlReporter;
	public ExtentReports extentReports;
	public ExtentTest logger;

	@BeforeTest
	public void beforeTest() {
		extentHtmlReporter = new ExtentHtmlReporter(
				File.separator + "Reports" + File.separator + "Mobile_Automation_Report.html");
		extentHtmlReporter.config().setEncoding("utf-8");
		extentHtmlReporter.config().setDocumentTitle("Mobile Automation Report by Sharath");
		extentHtmlReporter.config().setReportName("Mobile Automation Results");
		extentHtmlReporter.config().setTheme(Theme.DARK);
		extentReports = new ExtentReports();
		extentReports.attachReporter(extentHtmlReporter);
	}

	@BeforeMethod
	public void beforeMethod(Method testMethod) {
		logger = extentReports.createTest(testMethod.getName());
		String platform = Appium_DriverOptionsManager.getPlatformName();

		try {
			if (platform.equalsIgnoreCase("Android")) {
				UiAutomator2Options options = Appium_DriverOptionsManager.getAndroidOptions();
				driver = new AndroidDriver(new URL("http://127.0.0.1:4723"), options);
			} else if (platform.equalsIgnoreCase("iOS")) {
				XCUITestOptions options = Appium_DriverOptionsManager.getIOSOptions();
				driver = new IOSDriver(new URL("http://127.0.0.1:4723"), options);
			} else {
				throw new RuntimeException("Unsupported platform: " + platform);
			}
		} catch (MalformedURLException e) {
			e.printStackTrace();
		}

		System.out.println("Appium session started on platform: " + platform);
	}

	@AfterMethod
	public void afterMethod(ITestResult result) {
		if (result.getStatus() == ITestResult.SUCCESS) {
			logger.log(Status.PASS, MarkupHelper
					.createLabel("Test case " + result.getMethod().getMethodName() + " Passed", ExtentColor.GREEN));
		} else if (result.getStatus() == ITestResult.FAILURE) {
			logger.log(Status.FAIL, MarkupHelper
					.createLabel("Test case " + result.getMethod().getMethodName() + " Failed", ExtentColor.RED));
		}

		if (driver != null) {
			try {
				System.out.println("Terminating app: " + Appium_DriverOptionsManager.getAppPackage());
				((InteractsWithApps) driver).terminateApp(Appium_DriverOptionsManager.getAppPackage());
				driver.quit();
			} catch (Exception ignored) {
			}
			System.out.println("Appium session ended.");
		}
	}

	@AfterTest
	public void afterTest() {
		extentReports.flush();
	}
}
