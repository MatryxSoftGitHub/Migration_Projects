package com.MS.Workday.Pages;

import java.util.concurrent.TimeUnit;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.FindBy;

public class CreateAdHocPaymentPage {
	WebDriver driver;

	public CreateAdHocPaymentPage(WebDriver ldriver) {
		this.driver = ldriver;
	}

	// text fields of ad hoc payment event
	// page----------------------------------------------------------------------------------------------------------------------------------
	// Company
	@FindBy(xpath = "(//label[text()='Company']/parent::div/following-sibling::div//input)")
	WebElement Txt_Company;

	// Bank Account
	@FindBy(xpath = "(//label[text()='Bank Account']/parent::div/following-sibling::div//input)")
	WebElement Txt_BankAccount;

	// label payee ---------------------
	@FindBy(xpath = "//label[text()='Payee']/../parent::div//input[@type=\"radio\"]")
	WebElement Rdo_LabelPayee;

	// Text payee
	@FindBy(xpath = "(//label[text()='Payee']/../parent::div//input)[2]")
	WebElement Txt_LabelPayee;

	// Currency
	@FindBy(xpath = "(//label[text()=\"Currency\"]/../following-sibling::*//span[@data-automation-id=\"promptIcon\"])[1]")
	WebElement SpanIconOption;

	// Currency
	@FindBy(xpath = "(//label[text()='Currency']/parent::div/following-sibling::div//input)")
	WebElement Txt_Currency;

	// Payment Date
	@FindBy(xpath = "(//label[text()='Payment Date']/parent::div/following-sibling::div//input)")
	WebElement Txt_PaymentDate;

	// Payment Type
	@FindBy(xpath = "(//label[text()='Payment Type']/parent::div/following-sibling::div//input)")
	WebElement Txt_PaymentType;

	// ControlTotalAmount
	@FindBy(xpath = "//label[text()='Control Total Amount']/parent::div/following-sibling::div//input")
	WebElement Txt_ControlTotalAmount;

	// Memo
	@FindBy(xpath = "//label[text()='Memo']/parent::div/following-sibling::div//input")
	WebElement Txt_Memo;

	// text fields of lines tab in ad hoc payment event
	// page----------------------------------------------------------------------------------------------------------------------------------
	// Navigate to lines tab
	@FindBy(xpath = "//*[@data-automation-id=\"tabBar\"]//div[text()='Lines']")
	WebElement LinesTab;

	// Column line
	@FindBy(xpath = "(//caption[text()='Lines']/following::tbody//td[2]/div)[1]")
	WebElement ColumnLine;

	// Add Row
	@FindBy(xpath = "//span[text()='Invoice Lines']/ancestor::div[contains(@class,'SuperGrid')]/div//button[not(@data-automation-enabled=\"true\") and  @title=\"Add Row\"]")
	WebElement AddRow;

	// Add Row
	@FindBy(xpath = "(//td[contains(@headers,\"columnheader3\")]//input)[1]")
	WebElement Header;

	// Add Row
	@FindBy(xpath = "//span[text()=\"Order\"]/ancestor::td")
	WebElement TextColmnName;

	// AttachmentsTab
	@FindBy(xpath = "//*[@data-automation-id=\"tabBar\"]//div[text()='Attachments']")
	WebElement AttachmentsTab;

	// Select Files button
	@FindBy(xpath = "//*[@data-automation-id=\"tabBar\"]//div[text()='Attachments']")
	WebElement Btn_SelectFiles;

	// upload file
	@FindBy(xpath = "(//input[@type='file'])[last()]")
	WebElement fileUpload;
	
	// Submit button
		@FindBy(xpath = "//button[@type = 'button' and @title = 'Submit']")
		WebElement Btn_Submit;
	
	
	public void ClickOnSubmit() throws InterruptedException 
	{
		Thread.sleep(2000);
		JavascriptExecutor JSExecutor = (JavascriptExecutor) driver;
		JSExecutor.executeScript("arguments[0].scrollIntoView(true);", Btn_Submit);
		
		Btn_Submit.isDisplayed();
		Btn_Submit.click();
		
	}
	

	public void Uploadfile() throws InterruptedException
	{

		String filePath = "E:\\vidyaWorkspace482020\\EclipseWorkspace\\WorkdayBP_Selenium\\UploadFiles\\SampleUploadFile.txt";
		Thread.sleep(2000);
		JavascriptExecutor JSExecutor = (JavascriptExecutor) driver;
		JSExecutor.executeScript("arguments[0].scrollIntoView(true);", AttachmentsTab);
		Thread.sleep(3000);
		Actions actions = new Actions(driver);
		actions.moveToElement(AttachmentsTab).click().perform();

		Btn_SelectFiles.isDisplayed();
		Thread.sleep(3000);
		fileUpload.sendKeys(filePath);
		Thread.sleep(3000);

	}

	public void AddDetailsToLinesTab_Step() throws InterruptedException
	{
		// Add details to lines tab in Create Ad Hoc Payment Page
		// ---------------------------------------------------------------------------------------------------
		driver.manage().timeouts().implicitlyWait(30, TimeUnit.SECONDS);

		Thread.sleep(2000);
		// Navigate to lines tab
		JavascriptExecutor JSExecutor = (JavascriptExecutor) driver;
		JSExecutor.executeScript("arguments[0].scrollIntoView(true);", LinesTab);
		Actions actions = new Actions(driver);
		actions.moveToElement(LinesTab).click().perform();

		int localVar = 1;
		if (localVar > 1) 
		{
			AddRow.click();
			Thread.sleep(2000);
		}

		JSExecutor.executeScript("arguments[0].click();", ColumnLine);

		Header.isDisplayed();
		Thread.sleep(2000);

		TextColmnName.click();

		CommonsFunctions func = new CommonsFunctions(driver);
		Thread.sleep(2000);
		// Spend Category
		func.SendKeysAndClickEnterUsingTableName_RadioBtn("Lines", "Spend Category", "Advertising");

		// Quantity
		func.SendKeys_Table("Quantity", "1" + Keys.ENTER);
		Thread.sleep(2000);
		// Unit Cost
		func.SendKeys_Table("Unit Cost", "1000");
		Thread.sleep(2000);
		// Cost Center
		func.SendKeysAndClickEnterUsingTableName_RadioBtn("Lines", "*Cost Center", "100-000 General Overhead");

		Thread.sleep(2000);

	}

	public void AddDetailsToAdHocPaymentPage_Step() throws InterruptedException 
	{
		// Add details to Create Ad Hoc Payment Page
		// ---------------------------------------------------------------------------------------------------
		driver.manage().timeouts().implicitlyWait(30, TimeUnit.SECONDS);

		// Company
		Thread.sleep(5000);
		Txt_Company.sendKeys("NYT USD  - New York Home Office");
		Txt_Company.sendKeys(Keys.ENTER);

		Thread.sleep(5000);
		// Bank Account
		Txt_BankAccount.sendKeys("NYT Operating - JPMC USD-4785");
		Txt_BankAccount.sendKeys(Keys.ENTER);

		Thread.sleep(5000);
		// label payee ---------------------

		// Rdo_LabelPayee.click();
		JavascriptExecutor JSExecutor = (JavascriptExecutor) driver;
		JSExecutor.executeScript("arguments[0].click();", Rdo_LabelPayee);

		Thread.sleep(5000);
		// Text payee
		Txt_LabelPayee.sendKeys("AM Best Rating Services, Inc. - 9279");
		Thread.sleep(2000);
		Txt_LabelPayee.sendKeys(Keys.ENTER);

		Thread.sleep(5000);
//		// Currency//
//		JSExecutor.executeScript("arguments[0].click();", SpanIconOption);//
//		Txt_Currency.click();
//		Txt_Currency.sendKeys("USD");
//		Txt_Currency.sendKeys(Keys.ENTER);
//
//		// Payment Date
//		Txt_PaymentDate.sendKeys("08-31-2020");
//
//		// Payment Type
//		Txt_PaymentType.sendKeys("ACH");
//		Thread.sleep(2000);
//		Txt_PaymentType.sendKeys(Keys.ENTER);

		// ControlTotalAmount
		Txt_ControlTotalAmount.sendKeys(Keys.CONTROL, "a");
		Txt_ControlTotalAmount.sendKeys("1000");

		// Memo
		Txt_Memo.sendKeys("TestMemo1");

	}

}
