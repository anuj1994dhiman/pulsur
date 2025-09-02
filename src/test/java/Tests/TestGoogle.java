package Tests;

import static org.assertj.core.api.Assertions.assertThat;

import java.io.IOException;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import Base.BaseTest;
import Base.MyRetry;
import POM.HomePage;

public class TestGoogle extends BaseTest {
	@Test(dataProvider = "userCredentials", groups = "smoke")
	public void testGoogle(String usename, String password) {
		HomePage home=landingPage.login(usename, password);
		assertThat(home.homepageconfirmation()).isEqualTo("Automation Practice");
	}
	
	@Test(retryAnalyzer = MyRetry.class, groups = "sanity")
	public void invalidLogin() throws InterruptedException {
		landingPage.login("anu@example.com", "Anuj@4513");
		assertThat(landingPage.getErrorLoginErrorMsg()).isEqualTo("Incorrect email o password.");
	}
	
	@Test
	public void myTest() {
		System.out.println("hello this is my test");
	}
	
	@DataProvider
	public Object[][] userCredentials() throws IOException{
		Object[][] data=getExelData();
		return data;
	}

}
