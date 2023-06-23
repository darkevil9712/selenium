package bai2;

import java.io.File;
import java.io.IOException;

import org.apache.commons.io.FileUtils;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;

public class TakeScreenshotCommon {
	public static void takeScreenshot(WebDriver driver, String path) throws IOException {
		TakesScreenshot takeScreenshot = (TakesScreenshot) driver;
		File imageFile = takeScreenshot.getScreenshotAs(OutputType.FILE);
		
		File saveImageFile = new File(path);
		FileUtils.copyFile(imageFile, saveImageFile);
	}
}
