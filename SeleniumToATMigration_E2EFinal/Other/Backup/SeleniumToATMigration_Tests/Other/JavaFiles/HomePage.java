package com.techbeamers.exercises;
import java.util.concurrent.TimeUnit;
import org.openqa.selenium.By;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.FluentWait;
import org.openqa.selenium.support.ui.WebDriverWait;

import com.google.common.base.Function;

public class automateFlipkart {

	public static WebDriver driver;
	public static String driverPath = "C:\workspace\tools\selenium\";
	public static String sBookKey = "Selenium";
	private static final String sSearchBox = "fk-top-search-box";
	private static final String sSearchResult = "//li[contains(text(),'in')]//span";
	private static final String sBookName = "(//a[contains(.,'" + sBookKey + "')])[last()]";
	private static final String sAddToCart = "(//input[contains(@value,'Add to Cart')])[1]";
	private static final String sViewCartXPath = "(//a[contains(.,'view cart')])[1]";

	public static void initWebDriver(String URL) throws InterruptedException {

		// Setting up Chrome driver path.
		System.setProperty("webdriver.chrome.driver", driverPath + "chromedriver.exe");
		// Launching Chrome browser.
		driver = new ChromeDriver();
		driver.get(URL);
		driver.manage().window().maximize();
	}

	public static void main(String[] args) throws InterruptedException {

	

		driver.findElement(By.id(sSearchBox)).sendKeys(sBookKey);

		WebElement searchResult = getElement(By.xpath(sSearchResult));
		searchResult.click();

		WebDriverWait wait = new WebDriverWait(driver, 30);

		wait.until(ExpectedConditions.elementToBeClickable(By.xpath(sBookName))).click();

		wait.until(ExpectedConditions.elementToBeClickable(By.xpath(sAddToCart))).click();

		getElement(By.xpath(sViewCartXPath)).click();
		getElement(By.cssSelector("form[id='view-cart-form'] button")).click();
		getElement(By.xpath("//input[@id='email' and @name='email']")).sendKeys("test@testmail.com");

		// pause for a second and close the browser.
		Thread.sleep(1000);
		endSession();
	}

	public static WebElement getElement(final By locator) {
		FluentWait<WebDriver> wait = new FluentWait<WebDriver>(driver).withTimeout(30, TimeUnit.SECONDS)
				.pollingEvery(5, TimeUnit.SECONDS).ignoring(NoSuchElementException.class);

		WebElement element = wait.until(new Function<WebDriver, WebElement>() {

			@Override
			public WebElement apply(WebDriver arg0) {
				return arg0.findElement(locator);
			}

		});
