package Base;

import org.openqa.selenium.WebDriver;
import org.testng.ITestContext;
import org.testng.ITestListener;
import org.testng.ITestResult;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;

public class MyListener extends BaseTest implements ITestListener {
	
	ExtentReports extent = MyExtentReport.getMyExtentReport();
	ExtentTest test;
	ThreadLocal<ExtentTest> extentTest = new ThreadLocal<ExtentTest>();
	
	@Override
	public void onTestStart(ITestResult result) {
		test=extent.createTest(result.getMethod().getMethodName());
		extentTest.set(test);
	  }

	 
	@Override
	public void onTestSuccess(ITestResult result) {
	    // not implemented
	  }

	  
	@Override
	public void onTestFailure(ITestResult result) {
		extentTest.get().fail(result.getThrowable());
		String scPath = null;
		try {
			WebDriver driver = (WebDriver)result.getTestClass().getRealClass().getField("driver").get(result.getInstance());
			scPath=getScreenshot(driver, result.getMethod().getMethodName());
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		extentTest.get().addScreenCaptureFromPath(scPath);
	    
	  }

	  
	@Override
	public void onTestSkipped(ITestResult result) {
		extentTest.get().skip(result.getMethod().getMethodName()+"has been skipped");
	  }

	  
	@Override
	public void onTestFailedButWithinSuccessPercentage(ITestResult result) {
	    // not implemented
	  }

	  
	@Override
	public void onTestFailedWithTimeout(ITestResult result) {
	    onTestFailure(result);
	  }

	  
	@Override
	public void onStart(ITestContext context) {
	    // not implemented
	  }

	  
	@Override
	public void onFinish(ITestContext context) {
		extent.flush();
	  }

}
