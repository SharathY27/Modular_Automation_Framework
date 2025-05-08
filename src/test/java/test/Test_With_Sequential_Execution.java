package test;

import java.util.Set;

import org.openqa.selenium.By;
import org.testng.annotations.Test;

import base.BaseTest;
import utils.RetryAnalyzer;
import utils.Utilities;
import webPages.Demo_QA_Textbox_Page;

public class Test_With_Sequential_Execution extends BaseTest {

	@Test
	public void testingSelenium() {
		String currentWindow = Utilities.getCurrentWindowName(driver);
		Utilities.openNewTab(driver);
		Set<String> set = driver.getWindowHandles();
		for (String window : set) {
			if (window != currentWindow) {
				driver.switchTo().window(window);
			}
		}
		driver.get("https://demoqa.com/broken");
		String url = driver.findElement(By.xpath("//a[text()='Click Here for Broken Link']")).getAttribute("href");
		System.out.println(Utilities.isBrokenLink(url));
		driver.close();
		driver.switchTo().window(currentWindow);
		driver.get("https://demoqa.com/upload-download");
		driver.findElement(By.id("uploadFile")).sendKeys(
				"C:\\Users\\Sharath\\New Workspace\\Framework_Development\\src\\test\\resources\\config.properties");

	}

	@Test(retryAnalyzer = RetryAnalyzer.class)
	public void testWithDataFromExcel() {
		Demo_QA_Textbox_Page obj = new Demo_QA_Textbox_Page(driver);
		Utilities.readExcel();
		Utilities.moveToElementWithActionsClass(driver, obj.fullName);
		obj.enterName(Utilities.names.get(0));
		obj.enterEmail(Utilities.email.get(1));
	}

	@Test(dataProvider = "userData", dataProviderClass = Utilities.class)
	public void testWithDataProvider(String name, String email) {
		Demo_QA_Textbox_Page obj = new Demo_QA_Textbox_Page(driver);
		Utilities.readExcel();
		Utilities.moveToElementWithActionsClass(driver, obj.fullName);
		obj.enterName(name);
		obj.enterEmail(email);
	}
	
	@Test(invocationCount = 2)
	public void dummyTest() {
		System.out.println(driver);
	}

}
