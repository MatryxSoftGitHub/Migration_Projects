/*
 * Created by Ranorex
 * User: msadmin
 * Date: 30-12-2020
 * Time: 15:06
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

namespace Workday_Ranorex.Steps.CashManagement.CreateBankAccountTransferForSettlement
{
    /// <summary>
    /// Description of CreateBankAccountTransferForSettlement.
    /// </summary>
    [TestModule("B6C53381-CEC4-4418-8D0B-2897609F893F", ModuleType.UserCode, 1)]
    public class CreateBankAccountTransferForSettlement_Workday : ITestModule
    {
    	BankAccountTransferForSettlement repo = BankAccountTransferForSettlement.Instance;
    	
    	
    	string _TransferDate = "01/31/2021";
    	[TestVariable("ca9c0a92-c144-43b9-85ab-8d13f6821f3b")]
    	public string TransferDate
    	{
    		get { return _TransferDate; }
    		set { _TransferDate = value; }
    	}
    	
    	
    	string _CurrencyName = "USD";
    	[TestVariable("1bbfe99f-1a44-4dcc-8099-940600198559")]
    	public string CurrencyName
    	{
    		get { return _CurrencyName; }
    		set { _CurrencyName = value; }
    	}
    	
    	
    	
    	string _Amount = "100";
    	[TestVariable("e1cf5477-4cc5-4cbc-aed5-ee978ffaf115")]
    	public string Amount
    	{
    		get { return _Amount; }
    		set { _Amount = value; }
    	}
    	
    	
    	
    	string _PaymentType = "Wire";
    	[TestVariable("9f1e9443-d2aa-44b8-a654-7a6c19ad136b")]
    	public string PaymentType
    	{
    		get { return _PaymentType; }
    		set { _PaymentType = value; }
    	}
    	
    	
    	string _InitiatingCompany = "NYT USD  - New York Home Office";
    	[TestVariable("8e25e857-fd24-4725-bba7-52d5bd74cb5b")]
    	public string InitiatingCompany
    	{
    		get { return _InitiatingCompany; }
    		set { _InitiatingCompany = value; }
    	}
    	
    	
    	string _InitiatingAccount = "NYT Operating - JPMC USD-4785";
    	[TestVariable("e433cf94-ad74-47ed-86df-295ed3caff8e")]
    	public string InitiatingAccount
    	{
    		get { return _InitiatingAccount; }
    		set { _InitiatingAccount = value; }
    	}
    	
    	
    	string _Memo = "Test1-BATS";
    	[TestVariable("fef34d5d-3305-4cea-87fb-7052cbcffe3f")]
    	public string Memo
    	{
    		get { return _Memo; }
    		set { _Memo = value; }
    	}
    	
    	
    	string _ToAccountName = "NYT Bermuda - JPMC USD-2175";
    	[TestVariable("98b21340-974f-4f13-9d47-ff078e7c9a08")]
    	public string ToAccountName
    	{
    		get { return _ToAccountName; }
    		set { _ToAccountName = value; }
    	}
    	
        /// <summary>
        /// Constructs a new instance.
        /// </summary>
        public CreateBankAccountTransferForSettlement_Workday()
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
            
            repo.CreateBankAccountTransferForSettlement.TransferPaymentDetails.Txt_Date.PressKeys(TransferDate);
            Delay.Seconds(2);
            repo.CreateBankAccountTransferForSettlement.TransferPaymentDetails.Txt_Amount.PressKeys("{ControlKey down}{AKey}{ControlKey up}");
            Delay.Seconds(3);
            repo.CreateBankAccountTransferForSettlement.TransferPaymentDetails.Txt_Amount.PressKeys(Amount);
            repo.CreateBankAccountTransferForSettlement.TransferPaymentDetails.Txt_Currency.PressKeys(CurrencyName);
            repo.CreateBankAccountTransferForSettlement.TransferPaymentDetails.Txt_PaymentType.PressKeys(PaymentType);
            repo.CreateBankAccountTransferForSettlement.TransferPaymentDetails.Txt_IntiatingCompany.PressKeys(InitiatingCompany);
            repo.CreateBankAccountTransferForSettlement.TransferPaymentDetails.Txt_IntiatingAccount.PressKeys(InitiatingAccount);
            repo.CreateBankAccountTransferForSettlement.TransferPaymentDetails.Txt_Memo.PressKeys(Memo);
            repo.CreateBankAccountTransferForSettlement.Txt_ToAccount.PressKeys(ToAccountName);
            Delay.Seconds(5);
               //Keyboard.Press("{ControlKey down}{PageDown}{ControlKey up}");
            repo.CreateBankAccountTransferForSettlement.Lnk_SelectFiles.Focus();
            repo.CreateBankAccountTransferForSettlement.Lnk_SelectFiles.Click();
            Delay.Seconds(2);
            repo.Open.Cbo_File.Click();
            repo.Open.Txt_File.PressKeys(@"F:\Workday_Ranorex\Text Document.txt");
            repo.Open.Btn_Open.Click();
        }
    }
}
