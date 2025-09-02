package Base;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.time.Duration;
import java.util.HashMap;
import java.util.List;
import java.util.Properties;

import org.apache.commons.io.FileUtils;
import org.apache.poi.ss.usermodel.DataFormatter;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Optional;
import org.testng.annotations.Parameters;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

import POM.LandingPage;

public class BaseTest {

	public WebDriver driver;
	public LandingPage landingPage;

	public WebDriver initDriver(String xmlBrowser) throws FileNotFoundException, IOException {
		FileInputStream file = new FileInputStream(
				System.getProperty("user.dir") + "\\src\\test\\resources\\Resources\\browser.properties");
		Properties prop = new Properties();
		prop.load(file);
		String browser = System.getProperty("browser") != null ? System.getProperty("browser")
				: (prop.getProperty("browser") != null ? prop.getProperty("browser") : xmlBrowser);
		
		//String browser = prop.getProperty("browser") != null ? prop.getProperty("browser") : xmlBrowser;
		//String browser = xmlBrowser;
		
		//String browser = prop.getProperty("browser");
		if (browser.contains("chrome")) {
			ChromeOptions options = new ChromeOptions();
			if (browser.contains("headless")) {
				options.addArguments("headless");
			}
			driver = new ChromeDriver(options);
		}

		else if (browser.equalsIgnoreCase("firefox")) {
			driver = new FirefoxDriver();
		}

		else {
			System.out.println("invalid browser name");
		}
		driver.manage().window().maximize();
		driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(10));
		return driver;
	}

	
	@BeforeMethod(alwaysRun = true)
	@Parameters("browser")
	public LandingPage initBrowser(@Optional("chrome") String xmlBrowser) throws FileNotFoundException, IOException {
		driver = initDriver(xmlBrowser);
		landingPage = new LandingPage(driver);
		landingPage.goTo();
		return landingPage;
	}

	@AfterMethod(alwaysRun = true)
	public void quitBrowser() {
		driver.close();
	}

	public List<HashMap<String,String>> getJsonData() throws IOException {
		File file = new File(System.getProperty("user.dir") + "\\src\\test\\resources\\Resources\\Data.json");
		String filePath = FileUtils.readFileToString(file, StandardCharsets.UTF_8);
		ObjectMapper mapper = new ObjectMapper();
		List<HashMap<String, String>> data = mapper.readValue(filePath,
				new TypeReference<List<HashMap<String, String>>>() {
				});
		
		return data;
	}
	
	public Object[][] getExelData() throws IOException {
		FileInputStream file = new FileInputStream("E:\\Realme 7\\cred.xlsx");
		XSSFWorkbook workbook = new XSSFWorkbook(file);
		XSSFSheet sheet=workbook.getSheetAt(0);
		XSSFRow row=sheet.getRow(0);
		int rowNum=sheet.getPhysicalNumberOfRows();
		int colNum=row.getPhysicalNumberOfCells();
		Object[][] data = new Object[rowNum-1][colNum];
		DataFormatter formatter = new DataFormatter();
		for(int i = 1;i<rowNum;i++) {
			for(int j=0;j<colNum;j++) {
				data[i-1][j]=formatter.formatCellValue(sheet.getRow(i).getCell(j));
			}
		}
		return data;
	}
	
	public String getScreenshot(WebDriver driver, String testName) throws IOException {
		TakesScreenshot sc= (TakesScreenshot)driver;
		File src=sc.getScreenshotAs(OutputType.FILE);
		FileUtils.copyFile(src, new File("E:\\Realme 7\\Books\\reports"+testName+".png"));
		return "E:\\Realme 7\\Books\\reports"+testName+".png";
	}

}
