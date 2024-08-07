/*
 * Created by Ranorex
 * User: msadmin
 * Date: 28-12-2020
 * Time: 17:57
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

namespace Workday_Ranorex.Steps.CashManagement.CreateAdHocBankTransactionEvent
{
    /// <summary>
    /// Description of CreateAdHocBankTransactionEvent_Workday.
    /// </summary>
    [TestModule("A263C1ED-E0FE-405F-995F-F756BE6A6764", ModuleType.UserCode, 1)]
    public class CreateAdHocBankTransactionEvent_Workday : ITestModule
    {
    	AdHocBankTransaction repo = AdHocBankTransaction.Instance;
    	
    	string _AdHocBankTransactionDate = "12/29/2020";
    	[TestVariable("1a5e4944-b530-4ee9-b366-80a92a2c8b64")]
    	public string AdHocBankTransactionDate
    	{
    		get { return _AdHocBankTransactionDate; }
    		set { _AdHocBankTransactionDate = value; }
    	}
    	
    	
    	string _Memo = "Test Ad Hoc Transaction 1";
    	[TestVariable("dd0ffc23-66f6-4ee7-a18a-3192c2705675")]
    	public string Memo
    	{
    		get { return _Memo; }
    		set { _Memo = value; }
    	}
    	
    	
    	string _CompanyName = "NYT USD  - New York Home Office";
    	[TestVariable("78811667-cddd-423e-a287-1d689df1b75a")]
    	public string CompanyName
    	{
    		get { return _CompanyName; }
    		set { _CompanyName = value; }
    	}
    	
    	
    	string _BankAccount = "NYT Controlled Disbursement Operating - JPMC USD - 1183";
    	[TestVariable("a5d397a1-4eb4-4169-8c88-f0886d838f25")]
    	public string BankAccount
    	{
    		get { return _BankAccount; }
    		set { _BankAccount = value; }
    	}
    	
    	
    	string _TransactionAmount = "45";
    	[TestVariable("87a744f9-9cd6-4b86-bead-68d9c69f79c6")]
    	public string TransactionAmount
    	{
    		get { return _TransactionAmount; }
    		set { _TransactionAmount = value; }
    	}
    	
    	
    	string _ReferrenceName = "KS-ABT001";
    	[TestVariable("89a26f63-a97d-4b99-acea-fe60f004eb1d")]
    	public string ReferrenceName
    	{
    		get { return _ReferrenceName; }
    		set { _ReferrenceName = value; }
    	}
    	
    	
    	
        /// <summary>
        /// Constructs a new instance.
        /// </summary>
        public CreateAdHocBankTransactionEvent_Workday()
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
            
            repo.CreateAdHocBankTransactionWorkday.Txt_Date.Focus();
            repo.CreateAdHocBankTransactionWorkday.Txt_Date.Click();
            repo.CreateAdHocBankTransactionWorkday.Txt_Date.PressKeys(AdHocBankTransactionDate);
            
            repo.CreateAdHocBankTransactionWorkday.Txt_Memo.PressKeys(Memo);
            Delay.Seconds(2);
            repo.CreateAdHocBankTransactionWorkday.Txt_Company.PressKeys(CompanyName);
            Delay.Seconds(2);
            repo.CreateAdHocBankTransactionWorkday.Txt_BankAccount.PressKeys(BankAccount);
            Delay.Seconds(3);
            repo.CreateAdHocBankTransactionWorkday.Rbtn_Withdrawal.Focus();
            Delay.Seconds(2);
            repo.CreateAdHocBankTransactionWorkday.Rbtn_Withdrawal.Click();
            repo.CreateAdHocBankTransactionWorkday.Txt_TransactionAmount.PressKeys("{ControlKey down}{AKey}{ControlKey up}");
            repo.CreateAdHocBankTransactionWorkday.Txt_TransactionAmount.PressKeys(TransactionAmount);
            repo.CreateAdHocBankTransactionWorkday.Txt_Reference.PressKeys(ReferrenceName);
            
        }
    }
}
