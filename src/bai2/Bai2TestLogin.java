package bai2;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;

public class Bai2TestLogin {

	public static void main(String[] args) {
		try {
			System.setProperty("webdriver.chrome.driver", "C:\\WebDriver\\chromedriver.exe");
			
			ChromeOptions options = new ChromeOptions();
			options.addArguments("--remote-allow-origins=*");
			options.setBinary(new File("C:\\WebDriver\\chromedriver.exe"));
			
			List<User> lstUser = generateListUserToLogin();
			
			for(User user : lstUser) {
				WebDriver driver = new ChromeDriver();
				driver.manage().window().maximize();
				
				driver.get("https://thinking-tester-contact-list.herokuapp.com/");
				
				Thread.sleep(2000);
				
				WebElement txtEmail = driver.findElement(By.id("email"));
				txtEmail.sendKeys(user.getEmail());
				
				WebElement txtPassword = driver.findElement(By.id("password"));
				txtPassword.sendKeys(user.getPassword());
				
				WebElement btnLogin = driver.findElement(By.id("submit"));
				btnLogin.click();
				
				WebElement errorMessage = driver.findElement(By.id("error"));
				if(null != errorMessage.getText() || "".toString() != errorMessage.getText()) {
					Thread.sleep(1000);
					
					String imageFile =  user.getFirstName() + " " + user.getLastName() + ".jpg";
					String path = "C:\\picture-test\\" + user.getFirstName() + " " + user.getLastName() + "\\login\\" + imageFile;
					
					TakeScreenshotCommon.takeScreenshot(driver, path);
				}
				
				Thread.sleep(2000);
				
				WebElement btnAddContact = driver.findElement(By.id("add-contact"));
				btnAddContact.click();
				
				Thread.sleep(1000);
				
				// Vô trong màn hình add contact
				// Case 1 Thiếu First name => chụp hình
				
				WebElement txtLastName = driver.findElement(By.id("lastName"));
				txtLastName.sendKeys("Jisoo");
				
				WebElement btnSubmit = driver.findElement(By.id("submit"));
				btnSubmit.click();
				
				errorMessage = driver.findElement(By.id("error"));

				if(null != errorMessage.getText() || "".toString() != errorMessage.getText()) {
					Thread.sleep(1000);
					String imageFile =  user.getFirstName() + " " + user.getLastName() + "-mising-first-name.jpg";
					String path = "C:\\picture-test\\" + user.getFirstName() + " " + user.getLastName() + "\\login\\add-contact\\" + imageFile;
					
					TakeScreenshotCommon.takeScreenshot(driver, path);
					
				}
				
				// Case 2 Thiếu last name => chụp hình
				txtLastName.clear();
				
				WebElement txtFirstName = driver.findElement(By.id("firstName"));
				txtFirstName.sendKeys("Kim");
				
				btnSubmit.click();
				
				errorMessage = driver.findElement(By.id("error"));

				if(null != errorMessage.getText() || "".toString() != errorMessage.getText()) {
					Thread.sleep(1000);
					String imageFile =  user.getFirstName() + " " + user.getLastName() + "-mising-last-name.jpg";
					String path = "C:\\picture-test\\" + user.getFirstName() + " " + user.getLastName() + "\\login\\add-contact\\" + imageFile;
					
					TakeScreenshotCommon.takeScreenshot(driver, path);
					
				}
				
				// Case 3 Thiếu cả first name, last name => chụp hình
				txtFirstName.clear();
				btnSubmit.click();
				
				errorMessage = driver.findElement(By.id("error"));

				if(null != errorMessage.getText() || "".toString() != errorMessage.getText()) {
					Thread.sleep(1000);
					String imageFile =  user.getFirstName() + " " + user.getLastName() + "-mising-first-last-name.jpg";
					String path = "C:\\picture-test\\" + user.getFirstName() + " " + user.getLastName() + "\\login\\add-contact\\" + imageFile;
					
					TakeScreenshotCommon.takeScreenshot(driver, path);
					
				}
				
				// Case 4 đủ hết => pass , chụp hình
				txtFirstName.sendKeys("Kim");
				txtLastName.sendKeys("Jisoo");
				
				btnSubmit.click();
				
				Thread.sleep(2000);
				
				String imageFile =  "grid-user.jpg";
				String path = "C:\\picture-test\\" + user.getFirstName() + " " + user.getLastName() + "\\login\\add-contact\\" + imageFile;
				
				TakeScreenshotCommon.takeScreenshot(driver, path);
			}
		}
		catch(Exception e) {
			e.printStackTrace();
		}
	}
	
	

	private static List<User> generateListUserToLogin(){
		List<User> lstUser = new ArrayList<User>();
		User user1 = new User();
		user1.setEmail("nguyen@gmail.com");
		user1.setPassword("123456789");
		
		lstUser.add(user1);
		
		User user2 = new User();
		user2.setEmail("ngoc@gmail.com");
		user2.setPassword("123456789");
		
		lstUser.add(user2);
		
		User user3 = new User();
		user3.setEmail("toan@gmail.com");
		user3.setPassword("123456789");
		
		lstUser.add(user3);
		
		User user4 = new User();
		user4.setEmail("hieu@gmail.com");
		user4.setPassword("123456789");
		
		lstUser.add(user4);
		
		return lstUser;
	}
}
