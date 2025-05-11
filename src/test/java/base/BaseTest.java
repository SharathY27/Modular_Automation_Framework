package base;

import java.io.File;
import java.lang.reflect.Method;

import org.openqa.selenium.WebDriver;
import org.testng.ITestResult;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.BeforeTest;
import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.Status;
import com.aventstack.extentreports.markuputils.ExtentColor;
import com.aventstack.extentreports.markuputils.Markup;
import com.aventstack.extentreports.markuputils.MarkupHelper;
import com.aventstack.extentreports.reporter.ExtentHtmlReporter;
import com.aventstack.extentreports.reporter.configuration.Theme;

import utils.GlobalVariables;

public class BaseTest {

	public static WebDriver driver = null;
	public ExtentHtmlReporter extentHtmlReporter;
	public ExtentReports extentReports;
	public ExtentTest logger;

	@BeforeTest
	public void beforeTest() {// Initializing Reports
		GlobalVariables.loadProperties();
		File reportDir = new File(System.getProperty("user.dir") + File.separator + "target" + File.separator + "Reports");
		if (!reportDir.exists()) {
		    reportDir.mkdirs();  // Prevents FileNotFoundException
		}
		extentHtmlReporter = new ExtentHtmlReporter(
				reportDir + File.separator + "Automation_Report.html");
		extentHtmlReporter.config().setEncoding("utf-8");
		extentHtmlReporter.config().setDocumentTitle("Automation Report by Sharath");
		extentHtmlReporter.config().setReportName("Automation Results");
		extentHtmlReporter.config().setTheme(Theme.DARK);
		extentReports = new ExtentReports();
		extentReports.attachReporter(extentHtmlReporter);
	}

	@BeforeMethod // Initializing Driver
	public void beforeMethod(Method testMethod) {
		logger = extentReports.createTest(testMethod.getName());
		driver = Singleton_Driver.getSingletonDriver();
		driver.manage().window().maximize();
		driver.get(GlobalVariables.url);
	}

	@AfterMethod
	public void afterMethod(ITestResult result) {
		if (result.getStatus() == ITestResult.SUCCESS) {
			Markup markup = MarkupHelper.createLabel("Test case " + result.getMethod().getMethodName() + "Passed",
					ExtentColor.GREEN);
			logger.log(Status.PASS, markup);
		} else if (result.getStatus() == ITestResult.FAILURE) {
			Markup markup = MarkupHelper.createLabel("Test case" + result.getMethod().getMethodName() + "Failed",
					ExtentColor.RED);
			logger.log(Status.FAIL, markup);
		}
		Singleton_Driver.quitDriver();
	}

	@AfterTest
	public void afterTest() {
		extentReports.flush();
	}

}