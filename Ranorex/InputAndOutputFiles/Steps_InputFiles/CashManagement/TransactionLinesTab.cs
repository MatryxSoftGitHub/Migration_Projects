/*
 * Created by Ranorex
 * User: msadmin
 * Date: 28-12-2020
 * Time: 17:58
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
    /// Description of TransactionLinesTab.
    /// </summary>
    [TestModule("7B2AF0AA-CADA-4F84-932E-606908D6A12F", ModuleType.UserCode, 1)]
    public class TransactionLinesTab : ITestModule
    {
    	AdHocBankTransaction repo = AdHocBankTransaction.Instance;
    	
    	string _TransactionLines_RevenueSpendCategory = "Cash Conversion (1/1/2020)";
    	[TestVariable("cdc03ffb-85b0-4ab6-818a-91a19b0b0807")]
    	public string TransactionLines_RevenueSpendCategory
    	{
    		get { return _TransactionLines_RevenueSpendCategory; }
    		set { _TransactionLines_RevenueSpendCategory = value; }
    	}
    	
    	
    	string _TransactionLines_Memo = "Test Ad Hoc Transaction KS 1";
    	[TestVariable("95dfc374-4b3f-4a88-a355-9e8e47721045")]
    	public string TransactionLines_Memo
    	{
    		get { return _TransactionLines_Memo; }
    		set { _TransactionLines_Memo = value; }
    	}
    	
    	string _TransactionLines_CostCenter = "100-050 Corporate Accounting (Default)";
    	[TestVariable("204e73dd-e18d-4b70-824b-fee64532111b")]
    	public string TransactionLines_CostCenter
    	{
    		get { return _TransactionLines_CostCenter; }
    		set { _TransactionLines_CostCenter = value; }
    	}
    	
    	
        /// <summary>
        /// Constructs a new instance.
        /// </summary>
        public TransactionLinesTab()
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
            
            repo.CreateAdHocBankTransactionWorkday.Tab_AdHocBankTransactionLines.Click();
           // repo.CreateAdHocBankTransactionWorkday.Txt_Company_LinesTab.PressKeys(TransactionLines_CompanyName);
            repo.CreateAdHocBankTransactionWorkday.Txt_RevenueSpendCategory.PressKeys(TransactionLines_RevenueSpendCategory);
            Delay.Seconds(2);
            repo.CreateAdHocBankTransactionWorkday.Btn_Memo_LinesTab.Click();
            repo.CreateAdHocBankTransactionWorkday.Txt_Memo_LinesTab.PressKeys(TransactionLines_Memo);
            repo.CreateAdHocBankTransactionWorkday.Txt_CostCenter.PressKeys(TransactionLines_CostCenter);
            repo.CreateAdHocBankTransactionWorkday.Tab_Attachments.Click();
            repo.CreateAdHocBankTransactionWorkday.Lnk_SelectFiles.Click();
            Delay.Seconds(3);
            repo.Open.Cbo_File.Click();
            repo.Open.Txt_File.PressKeys(@"F:\Workday_Ranorex\Text Document.txt");
           // repo.CreateAdHocBankTransactionWorkday.Lnk_SelectFiles.PressKeys(@"F:\Workday_Ranorex\Text Document.txt");
           repo.Open.Btn_Open.Click();
            
        }
    }
}
