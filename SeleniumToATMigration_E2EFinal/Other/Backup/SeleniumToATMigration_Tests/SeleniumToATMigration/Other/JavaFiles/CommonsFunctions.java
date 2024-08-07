package com.MS.Workday.Pages;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.Keys;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

public class CommonsFunctions {

	WebDriver driver;

	public CommonsFunctions(WebDriver ldriver) {
		this.driver = ldriver;
	}

	public void SendKeysAndClickEnterUsingTableName_RadioBtn(String TableName, String columnName, String TestData)throws InterruptedException 
{
		if (TestData.trim().isEmpty()) 
{
			System.out.println("Skipping the step as the user did not enter any data in the data sheet");
		}
		else 
{
			try 
{
				Thread.sleep(9000);
				WebElement textColName = driver
						.findElement(By.xpath("//span[text()='" + columnName + "']/ancestor::td"));
				String dataAutoId = textColName.getAttribute("data-automation-id");
				System.out.println(dataAutoId);
				String[] colData = dataAutoId.split("-");
				String colNum = colData[1];

				WebElement SpanTablePromotion = driver.findElement(By.xpath("(//caption[text()='" + TableName
						+ "']/following-sibling::tbody//td[contains(@headers,\"columnheader" + colNum
						+ "\")]//span[@data-automation-id=\"promptIcon\"])"));
				JavascriptExecutor JSExecutor = (JavascriptExecutor) driver;
				JSExecutor.executeScript("arguments[0].scrollIntoView(true);", SpanTablePromotion);
				Thread.sleep(2000);
				SpanTablePromotion.isDisplayed();
				SpanTablePromotion.click();
				Thread.sleep(2000);
				WebElement TableNameWithRowHeader = driver.findElement(By.xpath("//caption[text()='" + TableName
						+ "']/following-sibling::tbody//td[contains(@headers,\"columnheader" + colNum + "\")]//input"));
				TableNameWithRowHeader.sendKeys(TestData);
				TableNameWithRowHeader.sendKeys(Keys.ENTER);
				Thread.sleep(5000);

				WebElement Rbtn_Grid = driver
						.findElement(By.xpath("//div[text()='" + TestData + "']/..//input[@type=\"radio\"]"));
				if (Rbtn_Grid.isDisplayed() == true) 
{
					Thread.sleep(5000);

					JSExecutor.executeScript("arguments[0].scrollIntoView(true);", Rbtn_Grid);
					Thread.sleep(2000);
					JSExecutor.executeScript("arguments[0].click();", Rbtn_Grid);

				}
				WebElement VerifyTableSelectedData = driver.findElement(By.xpath("//td[contains(@headers,\"columnheader"
						+ colNum + "\")]//div[contains(@data-automation-id,\"selectedItem\")]//p[text()='" + TestData
						+ "']"));
				if (VerifyTableSelectedData.isDisplayed()) 
{
					System.out.println("Table Radio Data is selected " + TestData);
				} 
else 
{
					System.out.println("Table Radio Data is not selected ");
				}
			} 
catch (Exception e) 
{
				System.out.println("Element not found");
			}

		}

	}

	public void SendKeys_Table(String columnName, String TestData) 
{
		if (TestData.trim().isEmpty()) 
{
			System.out.println("Skipping the step as the user did not enter any data in the data sheet");
		} 
else 
{
			try 
{
				Thread.sleep(5000);
				WebElement textColName = driver.findElement(By.xpath("//span[text()='" + columnName + "']/ancestor::td"));
				String dataAutoId = textColName.getAttribute("data-automation-id");
				System.out.println(dataAutoId);
				String[] colData = dataAutoId.split("-");
				String colNum = colData[1];
				WebElement inputRowHeader = driver.findElement(By.xpath("(//td[contains(@headers,\"columnheader" + colNum + "\")]//input)[1]"));
				inputRowHeader.click();
				inputRowHeader.sendKeys(Keys.CONTROL, "a");
				System.out.println("Entering the data" + TestData);
				inputRowHeader.sendKeys(TestData);
				System.out.println("Data has been entered into the field");

			} 
catch (NoSuchElementException e)
{
				System.out.println("Element not found");
			} 
catch (Exception e) 
{
				System.out.println("Fail to sendkeys and click enter for the element");
			}
		}
	}

}
