package webtest;

import static org.testng.Assert.assertEquals;

import java.time.Duration;
import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.testng.Assert;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;

import io.github.bonigarcia.wdm.WebDriverManager;

public class FreshWorksTest {
	WebDriver driver;
	
	@BeforeMethod
	@Parameters("browser")
	public void setup(String browser) {
		if(browser.equalsIgnoreCase("chrome")){
			System.out.println("Inside - Initiate Chrome driver");
			WebDriverManager.chromedriver().setup();
			ChromeOptions options = new ChromeOptions();
            options.addArguments("--remote-allow-origins=*");
            driver = new ChromeDriver(options);
		}
		else if(browser.equalsIgnoreCase("firefox")){
			System.out.println("Inside - Initiate Firefox driver");
			WebDriverManager.firefoxdriver().setup();
			driver = new FirefoxDriver();
		}
		driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(60));
		driver.manage().timeouts().pageLoadTimeout(Duration.ofSeconds(60));
		driver.get("https://www.freshworks.com");
	}
	
	@Test(priority = 1)
	public void freshMorksLogoTest() {
		System.out.println("Inside - freshMorksLogoTest");
		boolean flag = false;
		flag = driver.findElement(By.xpath("//img[@alt='freshworks-logo']")).isDisplayed();
		Assert.assertTrue(flag);
	}
	
	@Test(priority = 2)
	public void freshWorksTitleTest() {
		System.out.println("Inside - freshWorksTitleTest");
		System.out.println(driver.getTitle());
		assertEquals(driver.getTitle(), "Freshworks: Streamlined Solutions | Customer Service, IT, Sales");
	}
	
	@Test(priority = 3)
	public void getFooterLinksTests() {
		System.out.println("Inside - getFooterLinksTests");
		List<WebElement> footerLinksList = driver.findElements(By.cssSelector("ul.footer-nav li a"));
		footerLinksList.forEach(ele -> System.out.println(ele.getText()));
		assertEquals(footerLinksList.size(), 0);
	}
	
	@AfterMethod
	public void tearDown() {
		System.out.println("Inside - tearDown");
		driver.quit();
	}
}
