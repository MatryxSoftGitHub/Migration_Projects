/*
 * Created by Ranorex
 * User: msadmin
 * Date: 30-12-2020
 * Time: 17:40
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

namespace Workday_Ranorex.Steps.Commons
{
    /// <summary>
    /// Description of Notification_UnexpectedPopUpHandle.
    /// </summary>
    [TestModule("9E2B1C50-942F-40A9-888A-40ECF18D396B", ModuleType.UserCode, 1)]
    public class SearchBP : ITestModule
    {
    	BankAccountTransferForSettlement repo = BankAccountTransferForSettlement.Instance;
    	Workday_RanorexRepository repo1 = Workday_RanorexRepository.Instance;
        /// <summary>
        /// Constructs a new instance.
        /// </summary>
        public SearchBP()
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
            
            repo1.WorkdayHomePage.Txt_Search.Click();
            repo1.WorkdayHomePage.Txt_Search.PressKeys("{ControlKey down}{AKey}{ControlKey up}");
            repo1.WorkdayHomePage.Txt_Search.PressKeys("Create Bank Account Transfer For Settlement");
            Keyboard.Press("{ControlKey down}{Down}{ControlKey up}");
            Delay.Seconds(4);
            Keyboard.Press("{ENTER}");
            Delay.Seconds(5);
            
//            if(repo.CreateBankAccountTransferForSettlement.Dlg_TakingTooLongToProcess.Visible)
//            {
//            	repo.CreateBankAccountTransferForSettlement.Btn_Cancel.Click();
//            	repo1.WorkdayHomePage.Txt_Search.PressKeys("Create Bank Account Transfer For Settlement");
//            	Keyboard.Press("{ControlKey down}");
//            	//Keyboard.Down(Keys.ControlKey);
//            	Keyboard.Press("{ENTER}");
            //}
        }
    }
}
