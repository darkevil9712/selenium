package bai2;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;

public class SeleniumFullTest {

	public static void main(String[] args) {
		try {
			System.setProperty("webdriver.chrome.driver", "C:\\WebDriver\\chromedriver.exe");
			
			ChromeOptions options = new ChromeOptions();
			options.addArguments("--remote-allow-origins=*");
			options.setBinary(new File("C:\\WebDriver\\chromedriver.exe"));
			
			List<User> lstUser = getListUser();
			for(User user : lstUser) {
				WebDriver driver = new ChromeDriver();
				driver.manage().window().maximize();
				driver.get("https://thinking-tester-contact-list.herokuapp.com/");
				
				Thread.sleep(1000); // Wait 1 sec
				
				testLoginPage(driver, user);
				
				driver.close();
			}
		}
		catch(Exception e) {
			e.printStackTrace();
		}
	}
	
	// Test luồng Login trên trang login 
	private static void testLoginPage(WebDriver driver, User user) {
		try {
			WebElement txtEmail = driver.findElement(By.id("email"));
			txtEmail.sendKeys(user.getEmail());
			
			WebElement txtPassword = driver.findElement(By.id("password"));
			txtPassword.sendKeys(user.getPassword());
			
			WebElement btnLogin = driver.findElement(By.id("submit"));
			btnLogin.click();
			
			Thread.sleep(2000);
			try {
				WebElement errorMessage = driver.findElement(By.id("error"));
				// Kiểm tra khi nhấn nút, nếu hiện Incorrect username or password
				// thì nhấn nút đăng ký
				if("Incorrect username or password".equals(errorMessage.getText())) {
					WebElement btnRegister = driver.findElement(By.id("signup"));
					btnRegister.click();
					
					testRegisterPage(driver, user);
				}
				else {
					testAddNewContract(driver, user);
				}
			}
			catch(Exception e) {
				testAddNewContract(driver, user);
			}
		}
		catch(Exception e) {
			e.printStackTrace();
		}
		
	}
	
	// Test luồng đăng ký, trên trang đăng kí
	private static void testRegisterPage(WebDriver driver, User user) {
		try {
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
			
			Thread.sleep(2000);
			WebElement errorMessage = driver.findElement(By.id("error"));
			// Nếu đăng ký ko thành, chụp lại màn hình lỗi, lưu vô máy => để check lại data
			if(errorMessage.getText().equals("Email address is already in use")) {
				String fullname = user.getFirstName() + " " + user.getLastName();
				String path = "C:\\picture-test\\" + fullname + "\\register\\" + fullname+".jpg";
				TakeScreenshotCommon.takeScreenshot(driver, path);
			}
			else {
				testAddNewContract(driver, user);
			}
		}
		catch(Exception e) {
			e.printStackTrace();
		}
	}
	
	// Test luồng thêm mới contract
	private static void testAddNewContract(WebDriver driver, User user) {
		try {
			Thread.sleep(3000);
			List<Contact> lstContact = getListContact();
			
			int count = 1;
			for(Contact contact : lstContact) {
				WebElement btnAddContract = driver.findElement(By.id("add-contact"));
				btnAddContract.click();
				
				Thread.sleep(3000);
				
				WebElement txtFirstName = driver.findElement(By.id("firstName"));
				if(null != contact.getFirstName()) {
					txtFirstName.sendKeys(contact.getFirstName());
				}
				
				WebElement txtLastName = driver.findElement(By.id("lastName"));
				if(null != contact.getLastName()) {
					txtLastName.sendKeys(contact.getLastName());
				}
				
				WebElement txtBirthday = driver.findElement(By.id("birthdate"));
				if(null != contact.getBirthday()) {
					txtBirthday.sendKeys(contact.getBirthday());
				}
				
				WebElement txtEmail = driver.findElement(By.id("email"));
				if(null != contact.getEmail()) {
					txtEmail.sendKeys(contact.getEmail());
				}
				
				WebElement txtPhone = driver.findElement(By.id("phone"));
				if(null != contact.getPhone()) {
					txtPhone.sendKeys(contact.getPhone());
				}
				
				WebElement txtAddress1 = driver.findElement(By.id("street1"));
				if(null != contact.getAddress1()) {
					txtAddress1.sendKeys(contact.getAddress1());
				}
				
				WebElement txtAddress2 = driver.findElement(By.id("street2"));
				if(null != contact.getAddress2()) {
					txtAddress2.sendKeys(contact.getAddress2());
				}
				
				WebElement txtCity = driver.findElement(By.id("city"));
				if(null != contact.getCity()) {
					txtCity.sendKeys(contact.getCity());
				}
				
				WebElement txtState = driver.findElement(By.id("stateProvince"));
				if(null != contact.getState()) {
					txtState.sendKeys(contact.getState());
				}
				
				WebElement txtPortal = driver.findElement(By.id("postalCode"));
				txtPortal.sendKeys(String.valueOf(contact.getPortal()));
				
				WebElement txtCountry = driver.findElement(By.id("country"));
				if(null != contact.getCountry()) {
					txtCountry.sendKeys(contact.getCountry());
				}
				
				WebElement btnSubmit = driver.findElement(By.id("submit"));
				btnSubmit.click();
				
				Thread.sleep(2000);
				WebElement errorMessage = driver.findElement(By.id("error"));
				
				if(null != errorMessage.getText() || "".toString() != errorMessage.getText()) {
					String fullname = user.getFirstName() + " " + user.getLastName();
					String path = "C:\\picture-test\\" + fullname + "\\add-contact\\" + fullname + "_" + count +".jpg";
					TakeScreenshotCommon.takeScreenshot(driver, path);
					count++;
					
					WebElement btnCancel = driver.findElement(By.id("cancel"));
					btnCancel.click();
				}
				
			}
			
		}
		catch(Exception e) {
			e.printStackTrace();
		}
	}
	
	// NVL(A, B) => If A <> null => A, If A == null => A = B
	private static String nvl(String value, String defaultValue) {
		
		if(null == value) {
			value = defaultValue;
		}
		
		return value;
	}
	
	private static List<Contact> getListContact(){
		List<Contact> lstContact = new ArrayList<Contact>();
		try {
			final int FIRST_NAME_COLUMN_INDEX = 0;
			final int LAST_NAME_COLUMN_INDEX = 1;
			final int DATE_OF_BIRTH_COLUMN_INDEX = 2;
			final int EMAIL_COLUMN_INDEX = 3;
			final int PHONE_COLUMN_INDEX = 4;
			final int ADDRESS1_COLUMN_INDEX = 5;
			final int ADDRESS2_COLUMN_INDEX = 6;
			final int CITY_COLUMN_INDEX = 7;
			final int STATE_COLUMN_INDEX = 8;
			final int PORTAL_COLUMN_INDEX = 9;
			final int COUNTRY_COLUMN_INDEX = 10;
					
			String path = "C:\\data\\user-datas.xlsx";
			// Đọc Excel
			InputStream inputStream = new FileInputStream(new File(path));
			
			Workbook workbook = getWorkbook(inputStream, path);
			
			Sheet sheet = workbook.getSheetAt(1); // Lấy sheet số 2
			Iterator<Row> iterator = sheet.iterator();

			while(iterator.hasNext()) {
				Row currentRow = iterator.next();
				// Check if current row is header => skip to next row
				if(currentRow.getRowNum() == 0) {
					continue;
				}
				
				Iterator<Cell> cells = currentRow.cellIterator();
				
				Contact contact = new Contact();
				while(cells.hasNext()) {
					Cell currentCell = cells.next();
					
					int index = currentCell.getColumnIndex();
					
					switch (index) {
						case FIRST_NAME_COLUMN_INDEX:
							contact.setFirstName(nvl(currentCell.getStringCellValue() , ""));
							break;
						case LAST_NAME_COLUMN_INDEX:
							contact.setLastName(nvl(currentCell.getStringCellValue(), ""));
							break;
						case DATE_OF_BIRTH_COLUMN_INDEX:
							contact.setBirthday(nvl(currentCell.getStringCellValue(), ""));
							break;
						case EMAIL_COLUMN_INDEX:
							contact.setEmail(nvl(currentCell.getStringCellValue(), ""));
							break;
						case PHONE_COLUMN_INDEX:
							contact.setPhone(nvl(currentCell.getStringCellValue(), ""));
							break;
						case ADDRESS1_COLUMN_INDEX:
							contact.setAddress1(nvl(currentCell.getStringCellValue(), ""));
							break;
						case ADDRESS2_COLUMN_INDEX:
							contact.setAddress2(nvl(currentCell.getStringCellValue(), ""));
							break;
						case CITY_COLUMN_INDEX:
							contact.setCity(nvl(currentCell.getStringCellValue(), ""));
							break;
						case STATE_COLUMN_INDEX:
							contact.setState(nvl(currentCell.getStringCellValue(), ""));
							break;
						case PORTAL_COLUMN_INDEX:
							contact.setPortal((int) currentCell.getNumericCellValue());
							break;
						case COUNTRY_COLUMN_INDEX:
							contact.setCountry(nvl(currentCell.getStringCellValue(), ""));
							break;
					}
				}
				lstContact.add(contact);
			}
		}
		catch(Exception e) {
			e.printStackTrace();
		}
		return lstContact;
	}
	
	// Get data from Sheet 1
	private static List<User> getListUser(){
		final int FIRST_NAME_COLUMN_INDEX = 0;
		final int LAST_NAME_COLUMN_INDEX = 1;
		final int EMAIL_COLUMN_INDEX = 2;
		final int PASSWORD_COLUMN_INDEX = 3;
		
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
