package com.MS.Flipkart.Pages;

import java.util.List;
import java.util.concurrent.TimeUnit;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.FindBy;

public class ProductPage {

	WebDriver driver;

	public ProductPage(WebDriver ldriver) {
		this.driver = ldriver;
	}

	// Mobile Products
	@FindBy(xpath = "//*[@Class=\"_3wU53n\"]")
	public List<WebElement> sTxt_SearchedProducts;

	// Item Popularity
	@FindBy(xpath = "//div[contains(text(),'Popularity')]")
	public WebElement Itm_Popularity;

//	@FindBys(@FindBy(css=”div[class=’yt-lockup-tile yt-lockup-video’]”)))
//	private List<WebElement> videoElements;
//	@FindAll({@FindBy(how=How.ID, using=”username”),
//	@FindBy(className=”username-field”)})
//private WebElement user_name;

	public void SelectTheReqProduct(String sProductName) throws InterruptedException {
		System.out.println("Search a product in Flipkart Application");
		driver.manage().timeouts().implicitlyWait(30, TimeUnit.SECONDS);

		Itm_Popularity.click();

		Thread.sleep(5000);
		for (WebElement listProducts : sTxt_SearchedProducts) {
			// System.out.println(listProducts.getText());
			// int iNumOfProducts =sTxt_SearchedProducts.size();
			// System.out.println(iNumOfProducts);

			// if (listProducts.getText().equals(sProductName.trim()))
			if (listProducts.getText().contains(sProductName.trim())) {
				Actions actions = new Actions(driver);
				actions.moveToElement(listProducts).perform();
				listProducts.click();
			}
		}

		Thread.sleep(2000);

	}

	public void SelectProductUsingName(String sProductName) throws InterruptedException {
		System.out.println("Add details to Delivery Address to book product");
		driver.manage().timeouts().implicitlyWait(30, TimeUnit.SECONDS);
		Thread.sleep(5000);

		WebElement ReqProduct = driver.findElement(By.xpath("//*[contains(text(),'" + sProductName + "')]"));
		ReqProduct.click();

		
	}

}
