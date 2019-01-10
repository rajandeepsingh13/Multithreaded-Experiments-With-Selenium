package Testing;

import java.io.*;
import java.util.ArrayList;

import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.openqa.selenium.By;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.io.FileHandler;
import org.testng.annotations.*;

public class Test1 {
	
	public String baseUrl = "Enter Base URL HERE!!!!";
	public WebDriver driver;
	
	public File file;
	public FileInputStream inputStream;
	public Workbook eonWB;
	public Sheet sheet1;
	public int rowCount;
	public int startRow=0, endRow=0;
	
	public boolean isWorking=false;
	public static ArrayList<int[]> dataStore = new ArrayList<int[]>(0);
	
	//Constructor
	public Test1(int iterationCount,int threadCount) throws IOException{
		file = new File("D:\\User Login Details.xlsx");
		
		inputStream = new FileInputStream(file);
		eonWB = new XSSFWorkbook(inputStream);
		sheet1=eonWB.getSheet("Sheet1");
		
		rowCount = sheet1.getLastRowNum()-sheet1.getFirstRowNum()+1;
		int patchSize = rowCount/threadCount;
		int highWorkloadThreads=rowCount%threadCount;
		
		if(iterationCount<highWorkloadThreads) {
			patchSize++;
			startRow=iterationCount*patchSize;
		} else {
			startRow=(highWorkloadThreads)*(patchSize+1)+(iterationCount-highWorkloadThreads)*patchSize;
		}
		
		endRow=startRow+patchSize; //should always be less than this, never less than equal to
		System.out.println("Row Count " + rowCount + " Patch Size " + patchSize);
		
	}
	
	@BeforeMethod
	public void createDriver() {
		System.setProperty("webdriver.chrome.driver", "D:\\Projects\\Testing_Eon\\chromedriver.exe");
		driver = new ChromeDriver();
	}
	
	@Test
	public void fetchAndTest() throws IOException {
		
		driver.get(baseUrl);
			
		String title="VTS-Admin";
		for (int i = startRow; i < endRow; i++) {
			WebElement username=driver.findElement(By.id("txtUserId"));
			WebElement password=driver.findElement(By.id("txtPassword"));
			WebElement login=driver.findElement(By.id("btnLogin"));
			
			Row row = sheet1.getRow(i);
			username.clear();
			username.sendKeys(row.getCell(0).getStringCellValue());
			password.clear();
			password.sendKeys(row.getCell(1).getStringCellValue());
			login.click();
			
			if(driver.getTitle().equals(title)) {
				isWorking = true;
				System.out.println("Row "+i+" is WORKING "+startRow+" "+endRow);
				dataStore.add(new int[] {i,1});
				screenshot(i);
				driver.navigate().back();
			} else {
				System.out.println("Row "+i+" is NOT WORKING "+startRow+" "+endRow);
				dataStore.add(new int[] {i,0});
				screenshot(i);
				}
			}
			
			inputStream.close();
	}
	
	@AfterMethod
	public void shutItDown() {
		driver.close();
		System.out.println("Thread Closed");
	}
	
	public void screenshot(int i)
	{
		File src= ((TakesScreenshot)driver).getScreenshotAs(OutputType.FILE);
		try {
			if(isWorking) {
				FileHandler.copy(src, new File("d:\\login-row"+i+" WORKING.png"));
			} else {
				FileHandler.copy(src, new File("d:\\login-row"+i+" NOT WORKING.png"));
			}
		} catch (Exception e) {
			System.out.println(e.toString());
		}
	}

	public static char[] dataStore(int i) {
		// TODO Auto-generated method stub
		return null;
	}
	
}