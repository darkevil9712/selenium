import java.io.File;
import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.interactions.Actions;

public class Bai1 {
	
	public static void main(String[] args) throws InterruptedException {
		System.setProperty("webdriver.chrome.driver", "C:\\WebDriver\\chromedriver.exe");

		ChromeOptions options = new ChromeOptions();
		options.addArguments("--remote-allow-origins=*"); // Mở quyền sử dụng chuột bàn phím
		options.setBinary(new File("C:\\WebDriver\\chromedriver.exe"));
		WebDriver driver = new ChromeDriver();
		
		driver.manage().window().maximize();
		driver.get("https://blueprint.cyberlogitec.com.vn/");
		
		WebElement username = driver.findElement(By.id("username")); // Lấy element có id = username
		username.sendKeys(CommonValue.USERNAME);
		
		WebElement password = driver.findElement(By.id("password")); // Lấy element có id = password
		password.sendKeys(CommonValue.PASSWORD);
		
		WebElement submit = driver.findElement(By.id("submit-btn")); // Lấy element có id = submit-btn
		submit.click();
		
		Thread.sleep(2000); // Chờ 2s sau làm tiếp
		
		List<WebElement> lstAttendence = driver.findElements(By.className("webix_tree_branch_1"));
		
		WebElement menuAttendance = lstAttendence.get(5);
		
		new Actions(driver)
        .moveToElement(menuAttendance)
        .perform();
		
		WebElement checkInOut = driver.findElement(By.linkText("Check In/Out"));
		checkInOut.click();
		
		Thread.sleep(3000);
		
		WebElement punchInOut = driver.findElement(By.cssSelector("div[view_id='btn_54'] button"));
		punchInOut.click();
	}

}
