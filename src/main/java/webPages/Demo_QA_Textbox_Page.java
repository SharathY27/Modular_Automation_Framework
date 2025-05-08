package webPages;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

public class Demo_QA_Textbox_Page {
	
	@FindBy(id="userName")
	public WebElement fullName;
	
	@FindBy(id="userEmail")
	public WebElement email;
	
	public Demo_QA_Textbox_Page(WebDriver driver) {
		PageFactory.initElements(driver, this);
	}
	
	public void enterName(String name) {
		fullName.sendKeys(name);
	}
	
	public void enterEmail(String mail) {
		email.sendKeys(mail);
	}

}
