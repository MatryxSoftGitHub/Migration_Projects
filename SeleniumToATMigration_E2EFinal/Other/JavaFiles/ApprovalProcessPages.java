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
