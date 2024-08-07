package com.MS.Workday.Pages;

import java.util.concurrent.TimeUnit;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

public class ApprovalProcessPages {

	WebDriver driver;

	public ApprovalProcessPages(WebDriver ldriver) {
		this.driver = ldriver;
	}

	// btnInbox
	@FindBy(xpath = "//button[@title=\"Inbox\"]") WebElement Btn_Inbox;

	// Inbox Item Name
	@FindBy(xpath = "//div[@data-automation-id=\"inbox_row_title\" and contains(text(),\"+InboxItemName\")]") WebElement InboxitemName;

	// Text area comment
	@FindBy(xpath = "//textarea[@data-automation-id = 'textAreaField' and @aria-label = 'Process Comment']") WebElement Txt_areaComment;

	// Button aprrove
	@FindBy(xpath = "//button[@title = 'Approve' and @type = 'button' and (text() = 'Approve' or . = 'Approve')]") WebElement Btn_Approve;

	// Button Submit
	@FindBy(xpath = "//button[@type = 'button' and @title = 'Submit']") WebElement Btn_Submit;

	// Button Ok
	@FindBy(xpath = "//button[@type = 'button' and @role = 'button' and @title = 'OK' and (text() = 'OK' or . = 'OK')]") WebElement Btn_Ok;

	public void aprrove() throws InterruptedException 
	{
		driver.manage().timeouts().implicitlyWait(30, TimeUnit.SECONDS);

		// Inbox icon or button
		JavascriptExecutor JSExecutor = (JavascriptExecutor) driver;
		JSExecutor.executeScript("arguments[0].scrollIntoView(true);", Btn_Inbox);
		JSExecutor.executeScript("arguments[0].click();", Btn_Inbox);

		Thread.sleep(5000);
		String InboxItemName = "Ad Hoc Payment: ";

		WebElement LabelInboxItemName = driver.findElement(By.xpath("//div[@data-automation-id=\"inbox_row_title\" and contains(text(),\"" + InboxItemName + "\")]"));

		LabelInboxItemName.click();
		Thread.sleep(5000);

		if (Txt_areaComment.isDisplayed() == true) 
		{

			Txt_areaComment.sendKeys("Approved");
		}

		JSExecutor.executeScript("arguments[0].scrollIntoView(true);", Btn_Approve);
		Thread.sleep(5000);
		if (Btn_Approve.isDisplayed() == true) 
		{
			JSExecutor.executeScript("arguments[0].click();", Btn_Approve);
		} 
		else if (Btn_Submit.isDisplayed() == true) 
		{
			JSExecutor.executeScript("arguments[0].click();", Btn_Submit);
		} 
		else if (Btn_Ok.isDisplayed() == true)
			{
			JSExecutor.executeScript("arguments[0].click();", Btn_Ok);
		} 
		else 
		{
			System.out.println("Check the button for approval");
		}

	}

}
