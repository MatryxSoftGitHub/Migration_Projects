package com.MS.Flipkart.Pages;

import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Set;
import java.util.concurrent.TimeUnit;

import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

public class ProductDetailsPage {

	WebDriver driver;

	public ProductDetailsPage(WebDriver ldriver) {
		this.driver = ldriver;
	}

	// Button add to cart
	@FindBy(xpath = "//button[@class='_2AkmmA _2Npkh4 _2MWPVK']")
	public WebElement Btn_GoToCart;

	// Button BUY NOW
	@FindBy(xpath = "//button[@class='_2AkmmA _2Npkh4 _2kuvG8 _7UHT_c']")
	public WebElement Btn_BUYNOW;

	// ProductsViewImages
	@FindBy(xpath = "//div[@class='_2_AcLJ']")
	public List<WebElement> ProductsViewImages;

	// ViewImagesBlock
	@FindBy(xpath = "//div[@class='_2rDnao']")
	public WebElement ViewImagesBlock;

	// Cart link
	@FindBy(xpath = "//a[@class='_3ko_Ud']//span[contains(text(),'Cart')]")
	public WebElement Lnk_Cart;

	// Remove product from the cart
	@FindBy(xpath = "//*[contains(text(),'Remove')]")
	public List<WebElement> Lnk_RemoveProducts;

	// RemoveButton in cart
	@FindBy(xpath = "//div[contains(text(),'Remove')]")
	public WebElement Btn_RemoveInCart;

	// Remove item block from the cart
	@FindBy(xpath = "//*[@class='_3hgEev _3QHbp5']")
	public WebElement Blk_RemoveItem;

	// Remove button in Remove item block from the cart
	@FindBy(xpath = "//div[@class='gdUKd9 _3Z4XMp _2nQDKB']")
	public WebElement Btn_RemoveInBlk;
	
	//Place order button
	@FindBy(xpath = "//span[contains(text(),'Place Order')]")
	public WebElement Btn_PlaceOrder;
	
	//Pincode
	@FindBy(xpath = "//input[@id='pincodeInputId']")
	public WebElement Txt_EnterPincode;
	
	//PincodeCheck
	@FindBy(xpath = "//span[@class='_2aK_gu']")
	public WebElement Lnk_PincodeCheck;
	
	//fassured
	@FindBy(xpath = "//img[@class='gYMg9u']")
	public WebElement Lnk_FAssured;
	
	//product ratings
	@FindBy(xpath = "//*[@id=\"sellerName\"]/span/div/img")
	public WebElement Lnk_ProductRatings;
	
	public void ViewProductQualitybyImages() throws InterruptedException {
		Actions actions = new Actions(driver);
		
		driver.manage().timeouts().implicitlyWait(30, TimeUnit.SECONDS);
		System.out.println("View product function");

		Thread.sleep(3000);

		ArrayList<String> tabs = new ArrayList<String>(driver.getWindowHandles());
		if (tabs.size()>1) {
		driver.switchTo().window(tabs.get(0));
		driver.close();
		driver.switchTo().window(tabs.get(1));
		}

		int iNumOfProducts = ProductsViewImages.size();
		System.out.println(iNumOfProducts);

		Thread.sleep(5000);
		for (WebElement listProductImages : ProductsViewImages) {
			Thread.sleep(3000);
			actions.moveToElement(listProductImages).perform();
			Thread.sleep(2000);
			ViewImagesBlock.click();
			Thread.sleep(2000);
			actions.moveToElement(ViewImagesBlock).perform();
			Thread.sleep(2000);
			actions.moveToElement(ViewImagesBlock, 100, 50).perform();
			Thread.sleep(2000);
			actions.moveToElement(ViewImagesBlock, 60, 40).perform();
			Thread.sleep(2000);
			actions.moveToElement(ViewImagesBlock).perform();
		}
	}

	public void NavigateToBuyProduct() throws InterruptedException {
		driver.manage().timeouts().implicitlyWait(30, TimeUnit.SECONDS);
		System.out.println("Buy the product using Buy now option");
		Thread.sleep(3000);
		WebDriverWait wait = new WebDriverWait(driver, 30);

		ArrayList<String> tabs = new ArrayList<String>(driver.getWindowHandles());
		if (tabs.size()>1) {
		driver.switchTo().window(tabs.get(0));
		driver.close();
		driver.switchTo().window(tabs.get(1));
		}

		wait.until(ExpectedConditions.visibilityOf(Btn_BUYNOW));
		Btn_BUYNOW.click();

	}

	public void AddAndNavigateToCart() throws InterruptedException {
		driver.manage().timeouts().implicitlyWait(30, TimeUnit.SECONDS);
		System.out.println("Add the product to the cart");
		Thread.sleep(3000);
		WebDriverWait wait = new WebDriverWait(driver, 30);

		ArrayList<String> tabs = new ArrayList<String>(driver.getWindowHandles());
		if (tabs.size()>1) {
		driver.switchTo().window(tabs.get(0));
		driver.close();
		driver.switchTo().window(tabs.get(1));
		}

		wait.until(ExpectedConditions.visibilityOf(Btn_GoToCart));
		Btn_GoToCart.click();
		
		Thread.sleep(5000);
		Btn_PlaceOrder.click();

	}

	public void RemoveProductsFromCart() throws InterruptedException,InvocationTargetException {
		try {
			driver.manage().timeouts().implicitlyWait(30, TimeUnit.SECONDS);
			Actions actions = new Actions(driver);
			System.out.println("Remove product from the cart");
			Thread.sleep(1000);
			Lnk_Cart.click();

			int iNumOfProducts = Lnk_RemoveProducts.size();
			//System.out.println(iNumOfProducts);

			for (int i = 0; i <= iNumOfProducts; i++) {
				Thread.sleep(3000);
				actions.moveToElement(Btn_RemoveInCart).perform();
				Thread.sleep(2000);
				Btn_RemoveInCart.click();
				if (Blk_RemoveItem.isDisplayed() == true) {
					actions.moveToElement(Btn_RemoveInBlk).perform();
					Thread.sleep(2000);
					Btn_RemoveInBlk.click();
					//System.out.println("Product removed successfully");
				}			
			}
		} catch (Exception e) {
			System.out.println(e.getMessage());		

		}
	}
	
	public void CheckProduct() throws InterruptedException {
		System.out.println("Add details to Delivery Address to book product");
		driver.manage().timeouts().implicitlyWait(30, TimeUnit.SECONDS);
		JavascriptExecutor js = (JavascriptExecutor) driver;
		Actions actions = new Actions(driver);
		WebDriverWait wait = new WebDriverWait(driver, 10);
		
		ArrayList<String> tabs = new ArrayList<String>(driver.getWindowHandles());
		if (tabs.size()>1) {
		driver.switchTo().window(tabs.get(0));
		driver.close();
		driver.switchTo().window(tabs.get(1));
		}

		Thread.sleep(2000);
		Txt_EnterPincode.sendKeys(Keys.CONTROL, "a");
		Txt_EnterPincode.sendKeys("560094");
		Lnk_PincodeCheck.click();
		Thread.sleep(5000);
		Lnk_FAssured.click();
		
		Thread.sleep(4000);
		actions.moveToElement(Lnk_ProductRatings).perform();		
		wait.until(ExpectedConditions.visibilityOf(Lnk_ProductRatings));		
		js.executeScript("arguments[0].scrollIntoView(true);", Lnk_ProductRatings);
		js.executeScript("arguments[0].click();", Lnk_ProductRatings);
		Thread.sleep(2000);
}
	

}
