package com.MS.Workday.Pages;

import java.util.concurrent.TimeUnit;
import org.openqa.selenium.Keys;
//import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;


public class ProxyAs_Page {
	
	WebDriver driver;
	

	public ProxyAs_Page(WebDriver ldriver) {
		this.driver = ldriver;
	}

	//Search field
	@FindBy(xpath = "//input[@aria-label='search all of workday']") WebElement Txt_WorkdaySearch;

	//Start proxy
	@FindBy(xpath = "//div[contains(text(),'Start Proxy')]" WebElement Lnk_StartProxy;
	
	//Stop proxy
		@FindBy(xpath = "//div[contains(text(),'Stop Proxy')]" WebElement Lnk_StopProxy;
	
	//Act as
	@FindBy(xpath = "//label[text()='Act As']/parent::div/following-sibling::div//input") WebElement Txt_ActAs;
	
	
	//Ok button
	@FindBy(xpath = "//button[@type = 'button' and @role = 'button' and @title = 'OK' and (text() = 'OK' or . = 'OK')]" WebElement Btn_Ok;
	
	

	public void StartProxyAndActAs_Step(String sStartProxy, String sActAs) throws InterruptedException 
{		
		driver.manage().timeouts().implicitlyWait(30, TimeUnit.SECONDS);
		//Search for Start proxy--------------------------------------------------------------------------------------
		Txt_WorkdaySearch.sendKeys(Keys.CONTROL, "a");
		 Txt_WorkdaySearch.sendKeys(sStartProxy);
		 Thread.sleep(5000);
		 Txt_WorkdaySearch.sendKeys(Keys.ENTER);			 
		 Lnk_StartProxy.click();
		
		 
		 //Act As Start proxy--------------------------------------------------------------------------------------------------
		 Txt_ActAs.click();
		 Txt_ActAs.sendKeys(sActAs);
		 Txt_ActAs.sendKeys(Keys.ENTER);		 			 
		 Thread.sleep(5000);			 
		
		 Btn_Ok.click();			 
		 Thread.sleep(5000);
		
	}

	public void StopProxy_Step(String sStopProxy) throws InterruptedException 
{		
		driver.manage().timeouts().implicitlyWait(30, TimeUnit.SECONDS);
		//Search for Stop proxy--------------------------------------------------------------------------------------
		Txt_WorkdaySearch.sendKeys(Keys.CONTROL, "a");
		 Txt_WorkdaySearch.sendKeys(sStopProxy);
		 Thread.sleep(5000);
		 Txt_WorkdaySearch.sendKeys(Keys.ENTER);			 
		 Lnk_StopProxy.click();
			  
		 Thread.sleep(3000);
		 Btn_Ok.click();
		
	}
}


