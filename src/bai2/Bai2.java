package bai2;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.apache.commons.io.FileUtils;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
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
import org.openqa.selenium.chrome.ChromeOptions;

public class Bai2 {

	public static void main(String[] args) {
		try {
			System.setProperty("webdriver.chrome.driver", "C:\\WebDriver\\chromedriver.exe");
			
			ChromeOptions options = new ChromeOptions();
			options.addArguments("--remote-allow-origins=*");
			options.setBinary(new File("C:\\WebDriver\\chromedriver.exe"));
			
			
			List<User> lstUser = generateListUserToRegister();
			
			for(User user : lstUser) {
				WebDriver driver = new ChromeDriver();
				driver.manage().window().maximize();
				
				driver.get("https://thinking-tester-contact-list.herokuapp.com/");
				
				Thread.sleep(1500);
				
				
				WebElement btnSignUp = driver.findElement(By.id("signup"));
				btnSignUp.click();
				
				Thread.sleep(2000);
				
				WebElement txtFirstName = driver.findElement(By.id("firstName"));
				txtFirstName.sendKeys(user.getFirstName());
				
				WebElement txtLastName = driver.findElement(By.id("lastName"));
				txtLastName.sendKeys(user.getLastName());
				
				WebElement txtEmail = driver.findElement(By.id("email"));
				txtEmail.sendKeys(user.getEmail());
				
				WebElement txtPassword = driver.findElement(By.id("password"));
				txtPassword.sendKeys(user.getPassword());
				
				WebElement btnSubmit = driver.findElement(By.id("submit"));
				btnSubmit.click();
				
				WebElement errorMessage = driver.findElement(By.id("error"));
				
				if(null != errorMessage.getText() || "".toString() != errorMessage.getText()) {
					// Email bị trùng => chụp lại màn hình bug
					Thread.sleep(1000);
					String file = user.getFirstName() + " " + user.getLastName() + ".jpg";
					String path = "C:\\picture-test\\" + user.getFirstName() + " " + user.getLastName() + "\\register\\" + file ;
					TakeScreenshotCommon.takeScreenshot(driver, path);
				}
				
				Thread.sleep(2000);
				
				driver.quit();
			}
		}
		catch(Exception e) {
			e.printStackTrace();
		}
		
	}
	
	private static final int FIRST_NAME_COLUMN_INDEX = 0;
	private static final int LAST_NAME_COLUMN_INDEX = 1;
	private static final int EMAIL_COLUMN_INDEX = 2;
	private static final int PASSWORD_COLUMN_INDEX = 3;
	
	private static List<User> generateListUserToRegister(){
		List<User> lstUser = new ArrayList<User>();

		try {
			String path = "C:\\data\\user-datas.xlsx";
			// Đọc Excel
			InputStream inputStream = new FileInputStream(new File(path));
			
			Workbook workbook = getWorkbook(inputStream, path);
			
			Sheet sheet = workbook.getSheetAt(0);
			Iterator<Row> iterator = sheet.iterator();

			while(iterator.hasNext()) {
				Row currentRow = iterator.next();
				// Check if current row is header => skip to next row
				if(currentRow.getRowNum() == 0) {
					continue;
				}
				
				Iterator<Cell> cells = currentRow.cellIterator();
				
				User user = new User();
				while(cells.hasNext()) {
					Cell currentCell = cells.next();
					
					int index = currentCell.getColumnIndex();
					
					switch (index) {
						case FIRST_NAME_COLUMN_INDEX:
							user.setFirstName(currentCell.getStringCellValue());
							break;
						case LAST_NAME_COLUMN_INDEX:
							user.setLastName(currentCell.getStringCellValue());
							break;
						case EMAIL_COLUMN_INDEX:
							user.setEmail(currentCell.getStringCellValue());
							break;
						case PASSWORD_COLUMN_INDEX:
							user.setPassword(currentCell.getStringCellValue());
							break;
					}
				}
				lstUser.add(user);
			}
		}
		catch(Exception e) {
			e.printStackTrace();
		}
		return lstUser;
	}

	// Get Workbook
    private static Workbook getWorkbook(InputStream inputStream, String excelFilePath) throws IOException {
        Workbook workbook = null;
        if (excelFilePath.endsWith("xlsx")) {
            workbook = new XSSFWorkbook(inputStream);
        } else if (excelFilePath.endsWith("xls")) {
            workbook = new HSSFWorkbook(inputStream);
        } else {
            throw new IllegalArgumentException("The specified file is not Excel file");
        }
 
        return workbook;
    }
}
