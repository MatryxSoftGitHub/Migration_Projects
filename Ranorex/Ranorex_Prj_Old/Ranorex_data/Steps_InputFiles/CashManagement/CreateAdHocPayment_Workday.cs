/*
 * Created by Ranorex
 * User: msadmin
 * Date: 27-11-2020
 * Time: 13:03
 * 
 * To change this template use Tools > Options > Coding > Edit standard headers.
 */
using System;
using System.Collections.Generic;
using System.Text;
using System.Text.RegularExpressions;
using System.Drawing;
using System.Threading;
using WinForms = System.Windows.Forms;

using Ranorex;
using Ranorex.Core;
using Ranorex.Core.Testing;

namespace Workday_Ranorex.Steps.CashManagement
{
    /// <summary>
    /// Description of CreateAdHocPayment_Workday.
    /// </summary>
    [TestModule("0F6FC7E6-788B-479F-A0B7-DBE457EDBEF0", ModuleType.UserCode, 1)]
    public class CreateAdHocPayment_Workday : ITestModule
    {
    	Workday_RanorexRepository repo = Workday_RanorexRepository.Instance;
    	
    	
    	string _Company = "NYT USD  - New York Home Office";
    	[TestVariable("85ba7265-2f47-417a-a55e-3f852bc9b3ef")]
    	public string Company
    	{
    		get { return _Company; }
    		set { _Company = value; }
    	}
    	
    	
    	string _BankAccount = "NYT Operating - JPMC USD-4785";
    	[TestVariable("6c9720cd-7d69-4ff8-a62d-857be3c42442")]
    	public string BankAccount
    	{
    		get { return _BankAccount; }
    		set { _BankAccount = value; }
    	}
    	
    	
    	string _PayeeName = "AM Best Rating Services, Inc. - 9279";
    	[TestVariable("3ff0d5da-12db-445d-b4b5-1a7336849dad")]
    	public string PayeeName
    	{
    		get { return _PayeeName; }
    		set { _PayeeName = value; }
    	}
    	
    	
    	string _PaymentDate = "12/30/2020";
    	[TestVariable("fa36be4b-70ff-4260-9796-39673bd39a40")]
    	public string PaymentDate
    	{
    		get { return _PaymentDate; }
    		set { _PaymentDate = value; }
    	}
    	
    	
    	string _ControlTotalAmount = "1000";
    	[TestVariable("cc480812-9095-4381-8c51-d234664df9d6")]
    	public string ControlTotalAmount
    	{
    		get { return _ControlTotalAmount; }
    		set { _ControlTotalAmount = value; }
    	}
    	
    	
    	string _Memo = "TestMemo1";
    	[TestVariable("3ef02aad-2589-4eee-9039-05400dc6c5e5")]
    	public string Memo
    	{
    		get { return _Memo; }
    		set { _Memo = value; }
    	}
    	
 //Lines Tab  	
    	
//    	string _SpendCategory = "Advertising";
//    	[TestVariable("1cf417d4-a963-4146-b8fa-d2cdc22b6398")]
//    	public string SpendCategory
//    	{
//    		get { return _SpendCategory; }
//    		set { _SpendCategory = value; }
//    	}
    	
        /// <summary>
        /// Constructs a new instance.
        /// </summary>
        public CreateAdHocPayment_Workday()
        {
            // Do not delete - a parameterless constructor is required!
        }

        /// <summary>
        /// Performs the playback of actions in this module.
        /// </summary>
        /// <remarks>You should not call this method directly, instead pass the module
        /// instance to the <see cref="TestModuleRunner.Run(ITestModule)"/> method
        /// that will in turn invoke this method.</remarks>
        void ITestModule.Run()
        {
            Mouse.DefaultMoveTime = 300;
            Keyboard.DefaultKeyPressTime = 100;
            Delay.SpeedFactor = 1.0;
            
            repo.WorkdayHomePage.MainContent.Txt_Company.PressKeys(Company);
            repo.WorkdayHomePage.MainContent.Txt_BankAccount.PressKeys(BankAccount);
            Delay.Seconds(3);
            repo.WorkdayHomePage.MainContent.Rbtn_Payee.Focus();
            Delay.Seconds(1);
            repo.WorkdayHomePage.MainContent.Rbtn_Payee.Click();
            
            repo.WorkdayHomePage.MainContent.Txt_Payee.PressKeys(PayeeName);
            Keyboard.Press("{ENTER}");
            repo.WorkdayHomePage.MainContent.WebTxt_PaymentDate.Click();
            repo.WorkdayHomePage.MainContent.Txt_Date.Focus();
            repo.WorkdayHomePage.MainContent.Txt_Date.Click();
            repo.WorkdayHomePage.MainContent.Txt_Date.PressKeys(PaymentDate);
            repo.WorkdayHomePage.MainContent.Txt_ControlTotalAmount.PressKeys("{ControlKey down}{AKey}{ControlKey up}");
            repo.WorkdayHomePage.MainContent.Txt_ControlTotalAmount.PressKeys(ControlTotalAmount);
            repo.WorkdayHomePage.MainContent.Txt_Memo.PressKeys(Memo);
            //repo.WorkdayHomePage.MainContent.Tab_Lines.Click();
            
            //Lines Tab
//            repo.WorkdayHomePage.MainContent.Txt_SpendCategory.PressKeys();
//            Keyboard.Press("{ENTER}");
//            repo.WorkdayHomePage.Rbtn_Advertising.Click();
//            repo.WorkdayHomePage.MainContent.Txt_Quantity.PressKeys();
//            repo.WorkdayHomePage.MainContent.Txt_UnitCost.PressKeys();
//            repo.WorkdayHomePage.MainContent.Txt_CostCenter.PressKeys();
//            
            
            
        }
    }
}
