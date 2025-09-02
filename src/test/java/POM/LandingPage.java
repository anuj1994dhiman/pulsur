package POM;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

public class LandingPage {
	WebDriver driver;

	public LandingPage(WebDriver driver) {
		this.driver = driver;
		PageFactory.initElements(driver, this);
	}
	
	@FindBy(id = "userEmail")
	WebElement user;
	
	@FindBy(id = "userPassword")
	WebElement pass;
	
	@FindBy(id = "login")
	WebElement login;
	
	@FindBy(css = ".toast-message")
	WebElement toastMessage;

	public void goTo() {
		driver.get("https://rahulshettyacademy.com/client");
		
	}
	
	public HomePage login(String username, String password) {
		user.sendKeys(username);
		pass.sendKeys(password);
		login.click();
		HomePage homePage = new HomePage(driver);
		return homePage;
	}
	
	public String getErrorLoginErrorMsg() throws InterruptedException {
		Thread.sleep(1000);
		return toastMessage.getText();
	}
	

}
