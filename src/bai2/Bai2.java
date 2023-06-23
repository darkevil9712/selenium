package bai2;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.io.FileUtils;
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
	
	private static List<User> generateListUserToRegister(){
		List<User> lstUser = new ArrayList<User>();
		User user1 = new User();
		user1.setFirstName("Vu");
		user1.setLastName("Nguyen");
		user1.setEmail("nguyen@gmail.com");
		user1.setPassword("123456789");
		
		lstUser.add(user1);
		
		User user2 = new User();
		user2.setFirstName("Tran");
		user2.setLastName("Ngoc");
		user2.setEmail("ngoc@gmail.com");
		user2.setPassword("123456789");
		
		lstUser.add(user2);
		
		User user3 = new User();
		user3.setFirstName("Nguyen");
		user3.setLastName("Toan");
		user3.setEmail("toan@gmail.com");
		user3.setPassword("123456789");
		
		lstUser.add(user3);
		
		User user4 = new User();
		user4.setFirstName("Nguyen");
		user4.setLastName("Hieu");
		user4.setEmail("hieu@gmail.com");
		user4.setPassword("123456789");
		
		lstUser.add(user4);
		
		return lstUser;
	}

}
