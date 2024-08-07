package com.MS.Workday.Testcase;

import org.openqa.selenium.support.PageFactory;
import org.testng.annotations.Test;
import com.MS.Workday.Baseclass.BaseTest;
import com.MS.Workday.Pages.ApprovalProcessPages;
import com.MS.Workday.Pages.CreateAdHocPaymentPage;
import com.MS.Workday.Pages.HomePage;
import com.MS.Workday.Pages.LoginPage;
import com.MS.Workday.Pages.ProxyAs_Page;

public class AdHocPaymentEvent_Testcase extends BaseTest {

	@Test
	public void AdHocPaymentEvent_Test() throws InterruptedException {
		System.out.println("Test Started");

		// Step-1(Login to workday Application)
		System.out.println("Login to workday Application");
		LoginPage loginPage = PageFactory.initElements(driver, LoginPage.class);
		loginPage.LoginToWorkday_Step();
		System.out.println("Login to workday Application is successfull");

		// Step-2(Start Proxy and Act as step)
		System.out.println("Search for Start proxy and act as");
		ProxyAs_Page proxyAsPage = PageFactory.initElements(driver, ProxyAs_Page.class);
		proxyAsPage.StartProxyAndActAs_Step("Start Proxy", "Alice Chow");
		System.out.println("Act as Start proxy is successfull");

		// Step-3(Search for business flow step)
		System.out.println("Search for business flow");
		HomePage homePage = PageFactory.initElements(driver, HomePage.class);
		homePage.SearchForBP_Step("Create Ad Hoc Payment");
		System.out.println("BP displayed successfull");

		// Step-4(Add details to AdHocPayment Page Step)
		System.out.println("Add details to AdHocPayment Page Step");
		CreateAdHocPaymentPage createAdHocPaymentPage = PageFactory.initElements(driver, CreateAdHocPaymentPage.class);
		createAdHocPaymentPage.AddDetailsToAdHocPaymentPage_Step();
		System.out.println("Details are added successfully to AdHocPayment Page");

		// Step-5(Add details to lines tab in AdHocPayment Page Step)
		System.out.println("Add details to lines tab in AdHocPayment Page Step");
		createAdHocPaymentPage.AddDetailsToLinesTab_Step();
		System.out.println("Details are added successfully to lines tab in AdHocPayment Page");
		
		// Step-6(Upload file and Click on submit button)
		System.out.println("Upload file to the Business flow");
		createAdHocPaymentPage.Uploadfile();
		Thread.sleep(5000);
		createAdHocPaymentPage.ClickOnSubmit();
		Thread.sleep(5000);
		System.out.println("File has uploaded and clicked on Submit button");

		// Step-7(Approve flow for the BP of workday)
		// Aproval process
		System.out.println("Approving the business flow");
		proxyAsPage.StartProxyAndActAs_Step("Start Proxy", "James Ready");
		Thread.sleep(3000);
		ApprovalProcessPages approvalProcessPages = PageFactory.initElements(driver, ApprovalProcessPages.class);
		approvalProcessPages.aprrove();
		Thread.sleep(5000);
		proxyAsPage.StopProxy_Step("Stop Proxy");
		System.out.println("Business flow approved successfully");

		System.out.println("Test Completed");

	}

}
