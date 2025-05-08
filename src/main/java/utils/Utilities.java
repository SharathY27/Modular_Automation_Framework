package utils;

import java.io.File;
import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Base64;
import java.util.List;
import java.util.Set;

import org.apache.commons.io.FileUtils;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.Keys;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.WindowType;
import org.openqa.selenium.interactions.Actions;
import org.testng.annotations.DataProvider;

public class Utilities {

	public static List<String> names = new ArrayList<String>();
	public static List<String> email = new ArrayList<String>();
	public static Actions actions;
	public static JavascriptExecutor executor;
	public static TakesScreenshot screenshot;
	
	public static void readExcel() {
		try {
			XSSFWorkbook workbook = new XSSFWorkbook(new File(
					System.getProperty("user.dir") + File.separator + "src" + File.separator + "test" + File.separator
							+ "resources" + File.separator + "TestData" + File.separator + "testData.xlsx"));
			XSSFSheet sheet = workbook.getSheetAt(0);
			int count = 1;
			for (Row row : sheet) {
				if (count == 1) {
					count++;
					continue;
				}
				names.add(row.getCell(0).getStringCellValue());
				email.add(row.getCell(1).getStringCellValue());
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public static ResultSet connectDB(String url, String username, String password, String query) {
		// Java JDBC connection

//		Class.forName(url); // this line not required after java version 7
//		Connection connection = DriverManager.getConnection(url, username, password); 

		// below is try with resources format, which automatically
		// closes connection after try block complete execution
		try (Connection connection = DriverManager.getConnection(url, username, password);
				PreparedStatement statement = connection.prepareStatement(query);
				ResultSet resultSet = statement.executeQuery();) {

			return resultSet;

		} catch (SQLException se) {
			System.out.println("Failed to connect to database");
		}
		return null;
	}

	public static void moveToElementWithActionsClass(WebDriver driver, WebElement element) {
		actions = new Actions(driver);
		actions.moveToElement(element).build().perform();
	}

	@DataProvider(name = "userData")
	public static String[][] userData() {
		String userData[][] = { { "Sha", "sha@gmail.com" }, { "Vani", "vani@gmail.com" } };
		return userData;
	}

	public static String encodeString(String str) {
		return Base64.getEncoder().encodeToString(str.getBytes());
	}

	public static String decodeString(String str) {
		return Base64.getDecoder().decode(str).toString();
	}

	// window.scrollTo and window.scroll => will scroll from top of page from(0,0)
	// window.scrollBy => will scroll from current position of webPage

	public static void scrollByJSE(WebDriver driver, int x, int y) {
		executor = (JavascriptExecutor) driver;
		executor.executeScript("window.scrollBy(" + x + "," + y + ")");
	}

	public static void scrollToElementByJSE(WebDriver driver, WebElement element) {
		executor = (JavascriptExecutor) driver;
		executor.executeScript("arguments[0].scrollIntoView(true)", element);
	}

	public static void scrollToBottomByJSE(WebDriver driver, WebElement element) {
		executor = (JavascriptExecutor) driver;
		executor.executeScript("window.scrollTo(0,document.body.scrollHeight)");
	}

	public static void openNewTab(WebDriver driver) {
		driver.switchTo().newWindow(WindowType.TAB);
	}

	public static void openNewWindow(WebDriver driver) {
		driver.switchTo().newWindow(WindowType.WINDOW);
	}

	public static String getCurrentWindowName(WebDriver driver) {
		return driver.getWindowHandle();
	}

	public static Set<String> getAllWindows(WebDriver driver) {
		return driver.getWindowHandles();
	}

	public static String takeScreenshotAsBase64(WebDriver driver) {
		screenshot = (TakesScreenshot) driver;
		return screenshot.getScreenshotAs(OutputType.BASE64);
	}

	public static String takeScreenshotAsFile(WebDriver driver) {
		File src = null;
		File desc = new File(System.getProperty("user.dir") + File.separator + "src" + File.separator + "test"
				+ File.separator + "resources" + File.separator + "Screenshots" + File.separator + "img.png");
		src = ((TakesScreenshot) driver).getScreenshotAs(OutputType.FILE);
		try {
			FileUtils.copyFile(src, desc);
		} catch (IOException e) {
			e.printStackTrace();
		}
		return desc.getAbsolutePath();
	}

	public static boolean isBrokenLink(String url) {
		try {
			URL link = new URL(url);
			HttpURLConnection connection = (HttpURLConnection) link.openConnection();
			connection.setConnectTimeout(3000);
			connection.connect();
			if (connection.getResponseCode() != 200) {
				return true;
			}
		} catch (IOException e) {
			e.printStackTrace();
		}

		return false;
	}

	public static void enterUpperCaseUsingActionsClass(WebDriver driver, String text) {
		actions = new Actions(driver);
		actions.sendKeys(Keys.SHIFT, Keys.DOWN, text, Keys.UP).build().perform();
	}

}
