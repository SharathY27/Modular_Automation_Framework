package test;

import java.util.Collections;

import org.testng.annotations.Test;

import base.BaseTestForMobileAutomation;
import utils.Appium_Utility;

public class Mobile_Appium_Test extends BaseTestForMobileAutomation {

	@Test
	public void testing_Appium() {
		try {
			Thread.sleep(3000);
			// Give Chrome time to load

			// 🔽 Open URL via Android intent
			driver.executeScript("mobile: deepLink", Collections.singletonMap("url", "https://www.google.com"));
//			driver.get("https://www.google.com");
			Thread.sleep(5000); // Let Chrome load the page

			Appium_Utility.scrollDown(driver, 500, 1300, 500, 500);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
