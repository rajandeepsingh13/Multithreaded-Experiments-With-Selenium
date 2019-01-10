package Testing;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.testng.annotations.*;

public class CrossPlatform2 {
	
	public WebDriver driver;
	
	@BeforeTest
	@Parameters("browser")
	public void browserSelect(String browser) throws Exception {
		if(browser.equalsIgnoreCase("firefox")) {
			System.setProperty("webdriver.gecko.driver", "D:\\DevTools\\TestingSetup\\geckodriver.exe");
			driver = new FirefoxDriver();
		} else if(browser.equalsIgnoreCase("chrome")) {
			System.setProperty("webdriver.chrome.driver", "D:\\DevTools\\TestingSetup\\chromedriver.exe");
			driver = new ChromeDriver();
		}
	}
	
	@Test
	public void test1() { 
		driver.get("http://demo.guru99.com/V4/");
		WebElement userName = driver.findElement(By.name("uid"));
		userName.sendKeys("guru99");
		WebElement password = driver.findElement(By.name("password"));
		password.sendKeys("guru99");
	}
}
