package com.MS.Workday.Pages;

import java.util.concurrent.TimeUnit;

//import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

import com.MS.Workday.Utilities.ConfigDataProvider;

public class LoginPage {
	
	WebDriver driver;
	public ConfigDataProvider config;	

	public LoginPage(WebDriver ldriver) {
		this.driver = ldriver;
	}

	//Username
	@FindBy(xpath = "//input[@type = 'text' and @aria-label = 'Username']") WebElement sTxt_Username;

	//Password
	@FindBy(xpath = "//input[@aria-label='Password']") WebElement Txt_Password;
	
	//Sign in Button
	@FindBy(xpath = "//button[(text() = 'Sign In' or . = 'Sign In')]") WebElement Btn_SignIn;
	
	
	//Skiplink
	@FindBy(xpath = "//button[text()='Skip' and @data-automation-id=\"linkButton\"]") WebElement Lnk_Skip;
	
	

	public void LoginToWorkday_Step() throws InterruptedException 
{
		config=new ConfigDataProvider();
		
		driver.manage().timeouts().implicitlyWait(30, TimeUnit.SECONDS);
		
		sTxt_Username.sendKeys(config.GetUsername());	 
		 
		Txt_Password.sendKeys(config.GetPassword()); 
		
		Btn_SignIn.click();		 
		 
	    Lnk_Skip.click();
		 
		
	}

}


