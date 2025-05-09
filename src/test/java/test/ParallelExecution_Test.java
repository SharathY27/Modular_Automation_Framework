package test;

import java.util.Set;

import org.openqa.selenium.By;
import org.testng.annotations.Test;

import base.BaseTestForParallelExecution;
import utils.RetryAnalyzer;
import utils.Utilities;
import webPages.Demo_QA_Textbox_Page;

public class ParallelExecution_Test extends BaseTestForParallelExecution {

	@Test
	public void testingSelenium() {
		String currentWindow = Utilities.getCurrentWindowName(driver.get());
		Utilities.openNewTab(driver.get());
		Set<String> set = driver.get().getWindowHandles();
		for (String window : set) {
			if (window != currentWindow) {
				driver.get().switchTo().window(window);
			}
		}
		driver.get().get("https://demoqa.com/broken");
		String url = driver.get().findElement(By.xpath("//a[text()='Click Here for Broken Link']")).getAttribute("href");
		System.out.println(Utilities.isBrokenLink(url));
		driver.get().switchTo().window(currentWindow);
		driver.get().get("https://demoqa.com/upload-download");
		driver.get().findElement(By.id("uploadFile")).sendKeys("C:\\Users\\Sharath\\New Workspace\\Framework_Development\\src\\test\\resources\\config.properties");
	}

	@Test(retryAnalyzer = RetryAnalyzer.class)
	public void testWithDataFromExcel() {
		Demo_QA_Textbox_Page obj = new Demo_QA_Textbox_Page(driver.get());
		Utilities.readExcel();
		Utilities.moveToElementWithActionsClass(driver.get(), obj.fullName);
		obj.enterName(Utilities.names.get(0));
		obj.enterEmail(Utilities.email.get(1));
	}

	@Test(dataProvider = "userData", dataProviderClass = Utilities.class)
	public void testWithDataProvider(String name, String email) {
		Demo_QA_Textbox_Page obj = new Demo_QA_Textbox_Page(driver.get());
		Utilities.readExcel();
		Utilities.moveToElementWithActionsClass(driver.get(), obj.fullName);
		obj.enterName(name);
		obj.enterEmail(email);
	}

}
