package base;

import java.io.File;
import java.io.IOException;
import java.lang.reflect.Method;
import java.net.UnknownHostException;
import java.time.Duration;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.edge.EdgeDriver;
import org.testng.ITestResult;
import org.testng.annotations.AfterClass;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.AfterSuite;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.BeforeSuite;
import org.testng.annotations.BeforeTest;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.MediaEntityBuilder;
import com.aventstack.extentreports.Status;
import com.aventstack.extentreports.markuputils.ExtentColor;
import com.aventstack.extentreports.markuputils.Markup;
import com.aventstack.extentreports.markuputils.MarkupHelper;
import com.aventstack.extentreports.reporter.ExtentSparkReporter;
import com.aventstack.extentreports.reporter.configuration.Theme;

import utils.GlobalVariables;
import utils.Utilities;

public class BaseTestForParallelExecution {

	public static ThreadLocal<WebDriver> driver = new ThreadLocal<WebDriver>();
	public static ExtentSparkReporter sparkReporter;
	public static ExtentReports extentReports;
	public static ThreadLocal<ExtentTest> logger = new ThreadLocal<ExtentTest>();

	@BeforeSuite
	public void beforeSuite() {
		try {
			GlobalVariables.loadProperties();
			String hostname = java.net.InetAddress.getLocalHost().getHostName();
			String username = System.getProperty("user.name");
			sparkReporter = new ExtentSparkReporter(new File(
					System.getProperty("user.dir") + File.separator + "src" + File.separator + "test" + File.separator
							+ "resources" + File.separator + "Reports" + File.separator + "AutomationReport.html"));
			extentReports = new ExtentReports();
			sparkReporter.config().setEncoding("utf-8");
			sparkReporter.config().setDocumentTitle("Automation Reports");
			sparkReporter.config().setTheme(Theme.DARK);
			sparkReporter.config().setReportName("Automation Report by Sharath!");
			extentReports.attachReporter(sparkReporter);
			extentReports.setSystemInfo("Hostname", hostname);
			extentReports.setSystemInfo("Username", username);
		} catch (UnknownHostException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	@BeforeTest
	public void beforeTest() {

	}

	@BeforeClass
	public void beforeClass() {

	}

	@BeforeMethod
	public void beforeMethod(Method testMethod) {
		logger.set(extentReports.createTest(testMethod.getName()));
		setupDriver(GlobalVariables.browserName);
		driver.get().manage().window().maximize();
		driver.get().get(GlobalVariables.url);
		driver.get().manage().timeouts().implicitlyWait(Duration.ofSeconds(2));
	}

	@AfterMethod
	public void afterMethod(ITestResult result) {
		if (result.getStatus() == ITestResult.SUCCESS) {
			Markup m = MarkupHelper.createLabel(result.getName() + "Test case Passed , ", ExtentColor.GREEN);
			logger.get().log(Status.PASS, m);
			try {
				logger.get().log(Status.PASS, "Step passed with screenshot", MediaEntityBuilder
						.createScreenCaptureFromBase64String(Utilities.takeScreenshotAsBase64(driver.get())).build());
			} catch (IOException e) {
				e.printStackTrace();
			}

		} else if (result.getStatus() == ITestResult.FAILURE) {
			logger.get().log(Status.FAIL,
					MarkupHelper.createLabel(result.getName() + " - Test Case Failed ", ExtentColor.RED));
			logger.get().log(Status.FAIL, MarkupHelper.createLabel(result.getThrowable() + "", ExtentColor.RED));
			try {
				logger.get().log(Status.FAIL, " Failed ", MediaEntityBuilder
						.createScreenCaptureFromPath(Utilities.takeScreenshotAsFile(driver.get())).build());
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		} else if (result.getStatus() == ITestResult.SKIP) {
			logger.get().log(Status.FAIL,
					MarkupHelper.createLabel(result.getName() + " - Test Case Skipped ", ExtentColor.ORANGE));
		}
		driver.get().quit();
	}

	@AfterClass
	public void afterClass() {

	}

	@AfterTest
	public void afterTest() {
//		driver.get().quit();
	}

	@AfterSuite
	public void afterSuite() {
		extentReports.flush();
	}

	public static void setupDriver(String browserName) {
		switch (browserName) {
		case "chrome":
			driver.set(new ChromeDriver());
			break;
		case "edge":
			driver.set(new EdgeDriver());
			break;
		default:
			driver.set(new ChromeDriver());
		}
	}

}
