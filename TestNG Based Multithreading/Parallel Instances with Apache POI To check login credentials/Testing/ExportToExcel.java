package Testing;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.testng.annotations.Test;

public class ExportToExcel {
	
	public File file;
	public FileInputStream inputStream;
	public Workbook eonWB;
	public Sheet sheet1;
	
	@Test
	public void writeItDown() throws IOException {
		
		file = new File("D:\\User Login Details.xlsx");
		
		inputStream = new FileInputStream(file);
		eonWB = new XSSFWorkbook(inputStream);
		sheet1=eonWB.getSheet("Sheet1");
		
		int rowCount = sheet1.getLastRowNum()-sheet1.getFirstRowNum()+1;
		
		for(int i=0;i<rowCount;i++) {
			int temp[]=Test1.dataStore.get(i);
			System.out.println(temp[0]+" "+temp[1]);
			Row row=sheet1.getRow(temp[0]);
			Cell cell=row.createCell(2);
			cell.setCellValue(temp[1]==1? "Working" : "Not Working");
		}
		
		inputStream.close();
		
		FileOutputStream outputStream = new FileOutputStream(file);
		eonWB.write(outputStream);
		outputStream.close();  
		
		
	}
}