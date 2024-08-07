package com.MS.Workday.Pages;

import java.util.concurrent.TimeUnit;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

public class HomePage {
	WebDriver driver;
	
	public HomePage(WebDriver ldriver) {
		this.driver = ldriver;
	}

	//search field
	@FindBy(xpath = "//input[@type = 'search' and @placeholder = 'Search']") WebElement Txt_BPSearch;
	
	//BP flow link
	@FindBy(xpath = "//*[@role = 'link' and (text() = 'Create Ad Hoc Payment' or . = 'Create Ad Hoc Payment')]") WebElement Lnk_BPSearch;
	
	public void SearchForBP_Step(String sBPSearchValue) throws InterruptedException 
{
		//Search for BP---------------------------------------------------------------------------------------------------
		driver.manage().timeouts().implicitlyWait(30, TimeUnit.SECONDS); 
		
		 Txt_BPSearch.click();
		 Txt_BPSearch.sendKeys(sBPSearchValue);
		 Txt_BPSearch.sendKeys(Keys.ENTER);
		 Thread.sleep(5000);	 			 
		 
		 Lnk_BPSearch.click();
		 
		
	}



}
