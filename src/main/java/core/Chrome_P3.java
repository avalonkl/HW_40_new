package core;

import java.io.FileInputStream;
import java.util.Properties;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.openqa.selenium.*;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

public class Chrome_P3 {

	static WebDriver driver;
	static Properties p = new Properties();

	public static void main(String[] args) throws Exception {

		Logger.getLogger("").setLevel(Level.OFF); // Suppress Warnings
		
		p.load(new FileInputStream("./input.properties"));

		String[] browsers = p.getProperty("browser").split(",");
		System.out.println("Browser: " + browsers[1]);

		System.setProperty("webdriver.chrome.driver", "/usr/local/bin/chromedriver");
        System.setProperty("webdriver.chrome.silentOutput", "true"); // Chrome
        ChromeOptions option = new ChromeOptions();                  // Chrome
        option.addArguments("disable-infobars");                     // Chrome
        option.addArguments("--disable-notifications");              // Chrome
        
		driver = new ChromeDriver();
		
		WebDriverWait wait = new WebDriverWait(driver, 15);
		
		final long start = System.currentTimeMillis();
		
		driver.get(p.getProperty("url"));
		driver.manage().window().maximize();

		System.out.println("Title: " + driver.getTitle());

		wait.until(ExpectedConditions.presenceOfElementLocated(By.id(p.getProperty("id_email")))).sendKeys(System.getenv("fb_email"));

		wait.until(ExpectedConditions.presenceOfElementLocated(By.id(p.getProperty("id_password")))).sendKeys(System.getenv("fb_password"));
		
		wait.until(ExpectedConditions.presenceOfElementLocated(By.xpath(p.getProperty("xpath_login")))).click();
		
		wait.until(ExpectedConditions.titleContains(p.getProperty("title")));
//		if (!driver.findElements(By.xpath(p.getProperty("xpath_snackbar"))).isEmpty())
//			System.out.println(driver.getWindowHandles().toArray().length);
//			driver.switchTo().window((String) driver.getWindowHandles().toArray()[1]);
		while (!driver.findElements(By.xpath(p.getProperty("xpath_snackbar"))).isEmpty());

		wait.until(ExpectedConditions.elementToBeClickable(By.xpath(p.getProperty("xpath_timeline")))).click();
		
		String friends = wait.until(ExpectedConditions.presenceOfElementLocated(By.xpath(p.getProperty("xpath_friends")))).getText();

		wait.until(ExpectedConditions.presenceOfElementLocated(By.id(p.getProperty("id_settings")))).click();

		wait.until(ExpectedConditions.presenceOfElementLocated(By.linkText(p.getProperty("linktext_logout")))).click();

		final long finish = System.currentTimeMillis();
		driver.quit();
		System.out.println("You have " + friends + " friends");
		System.out.println("Response time: " + (finish - start) / 1000.0 + " seconds");
	}
}

