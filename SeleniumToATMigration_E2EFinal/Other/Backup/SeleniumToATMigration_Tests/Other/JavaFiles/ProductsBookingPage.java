package com.MS.Flipkart.Pages;

import java.util.List;
import java.util.concurrent.TimeUnit;

import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.ui.Select;

public class ProductsBookingPage {

	WebDriver driver;

	public ProductsBookingPage(WebDriver ldriver) {
		this.driver = ldriver;
	}

	// Change button for address change
	@FindBy(xpath = "//*[@id=\"container\"]/div/div[2]/div/div[1]/div[2]/div/div/button")
	public WebElement Btn_AddressChange;

	// Edit link
	@FindBy(xpath = "//button[@class='FoDyGO']")
	public WebElement Lnk_Edit;

	// Name
	@FindBy(xpath = "//input[@name='name']")
	public WebElement Txt_Name;

	// Mobile Number
	@FindBy(xpath = "//input[@name='phone']")
	public WebElement Txt_MobileNumber;

	// pincode
	@FindBy(xpath = "//input[@name='pincode']")
	public WebElement Txt_Pincode;

	// Locality
	@FindBy(xpath = "//input[@name='addressLine2']")
	public WebElement Txt_Locality;

	// Address Area
	@FindBy(xpath = "//textarea[@name='addressLine1']")
	public WebElement Txt_AddressArea;

	// City
	@FindBy(xpath = "//input[@name='city']")
	public WebElement Txt_City;

	// LandMark
	@FindBy(xpath = "//input[@name='landmark']")
	public WebElement Txt_Landmark;

	// alternate Phone
	@FindBy(xpath = "//input[@name='alternatePhone']")
	public WebElement Txt_AlternatePhone;

	// Select state
	@FindBy(xpath = "//select[@name='state']")
	public WebElement Pop_State;

	// Work Radio button
	@FindBy(xpath = "//label[@class='_8J-bZE _2tcMoY _1Icwrf']//div[@class='_6ATDKp']")
	public WebElement Rbtn_Work;

	// Save and Delevery here button
	@FindBy(xpath = "//button[@class='_2AkmmA EqjTfe _7UHT_c']")
	public WebElement Btn_SaveAndDeliveryHere;

	// Credit card radio button
	@FindBy(xpath = "//label[@class='_8J-bZE _3C6tOa _1syowc _2i24Q8 _1Icwrf']//div[@class='_6ATDKp']")
	public WebElement Rbtn_CreditDebitOption;

	// Card number
	@FindBy(xpath = "//input[@name='cardNumber']")
	public WebElement Txt_CardNumber;

	// Select month
	@FindBy(xpath = "//select[@name='month']")
	public WebElement Pop_Month;

	// Select year
	@FindBy(xpath = "//select[@name='year']")
	public WebElement Pop_Year;

	// CVV
	@FindBy(xpath = "//input[@name='cvv']")
	public WebElement Txt_CVV;

	// Pay amount button
	@FindBy(xpath = "//button[@class='_2AkmmA wbv91z _7UHT_c']")
	public WebElement Btn_Pay;

	// Continue button to get payment option
	@FindBy(xpath = "//*[@id=\"to-payment\"]/button")
	public WebElement Btn_Continue;

	// Payment options
	@FindBy(xpath = "//*[@class='_3i_pKg']")
	public List<WebElement> PaymentOptions;

	// Flipkart logo
	@FindBy(xpath = "//img[@class='_1e_EAo']")
	public WebElement Lnk_FlipkartLogo;

	public void AddDetailsToDeliveryAddress() throws InterruptedException {
		System.out.println("Add details to Delivery Address to book product");
		driver.manage().timeouts().implicitlyWait(30, TimeUnit.SECONDS);

		Thread.sleep(2000);
		Btn_AddressChange.click();
		Thread.sleep(2000);
		Lnk_Edit.click();

		Txt_Name.sendKeys(Keys.CONTROL, "a");
		Txt_Name.sendKeys("MatryxsoftTech");

		Txt_MobileNumber.sendKeys(Keys.CONTROL, "a");
		Txt_MobileNumber.sendKeys("8310618462");

		Txt_Pincode.sendKeys(Keys.CONTROL, "a");
		Txt_Pincode.sendKeys("560094");

		Txt_Locality.sendKeys(Keys.CONTROL, "a");
		Txt_Locality.sendKeys("Banglore");

		Txt_AddressArea.sendKeys(Keys.CONTROL, "a");
		Txt_AddressArea.sendKeys("#9/2, 8th Cross, Outer Ring Rd, R.M.V. 2nd Stage,\r\n"
				+ "Lottegollahalli, Bengaluru, Karnataka 560094.");

		Txt_City.sendKeys(Keys.CONTROL, "a");
		Txt_City.sendKeys("Banglore");

		Txt_Landmark.sendKeys(Keys.CONTROL, "a");
		Txt_Landmark.sendKeys("Near Jalageramma Temple");

		Txt_AlternatePhone.sendKeys(Keys.CONTROL, "a");
		Txt_AlternatePhone.sendKeys("0123456789");

		Select dropdown = new Select(Pop_State);
		dropdown.selectByVisibleText("Karnataka");

		Rbtn_Work.click();

		Thread.sleep(2000);
		Btn_SaveAndDeliveryHere.click();

		Thread.sleep(2000);
		Btn_Continue.click();

		System.out.println("Added address details");

	}

	public void AddDetailsPaymentOptions() throws InterruptedException {
		System.out.println("Add details to payment options to book product");
		driver.manage().timeouts().implicitlyWait(30, TimeUnit.SECONDS);

		int iNumOfProducts = PaymentOptions.size();
		// System.out.println(iNumOfProducts);
		Thread.sleep(5000);
		for (WebElement listProducts : PaymentOptions) {
			// System.out.println(listProducts.getText());

			if (listProducts.getText().contains("Credit")) {
				Actions actions = new Actions(driver);
				actions.moveToElement(listProducts).perform();
				listProducts.click();
			}

		}

		Thread.sleep(3000);

		Txt_CardNumber.sendKeys("0123456789012345");

		Select Pop_month = new Select(Pop_Month);
		Pop_month.selectByVisibleText("09");

		Select Pop_year = new Select(Pop_Year);
		Pop_year.selectByVisibleText("30");

		Txt_CVV.sendKeys("1234");

		System.out.println("Added payment details");

	}

	public void NavigateToFlipkartHomePage() throws InterruptedException {
		System.out.println("Add details to Delivery Address to book product");
		driver.manage().timeouts().implicitlyWait(30, TimeUnit.SECONDS);
		Thread.sleep(2000);
		Lnk_FlipkartLogo.click();

	}

}
