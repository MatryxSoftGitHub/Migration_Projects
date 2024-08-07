/*
 * Created by Ranorex
 * User: msadmin
 * Date: 01-12-2020
 * Time: 16:45
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
    /// Description of Approval.
    /// </summary>
    [TestModule("51761AD8-A82B-4FF0-835B-C7DE5B1556D0", ModuleType.UserCode, 1)]
    public class Approval : ITestModule
    {
    	Workday_RanorexRepository repo = Workday_RanorexRepository.Instance;
    	
    	string _sStartProxy = "Start Proxy";
    	[TestVariable("487d9126-e20e-4da5-b77c-dbbd8288ddc6")]
    	public string sStartProxy
    	{
    		get { return _sStartProxy; }
    		set { _sStartProxy = value; }
    	}
    	
    	
    	string _TreasuryAdministrator = "James Ready";
    	[TestVariable("3cb1a3b4-438d-4b8e-bfc7-ef5a3a24db5c")]
    	public string TreasuryAdministrator
    	{
    		get { return _TreasuryAdministrator; }
    		set { _TreasuryAdministrator = value; }
    	}
    	
    	
    	string _GlobalChiefFinancialOfficer = "Matthew Mahoney";
    	[TestVariable("8d54c015-0bfc-4ab9-a5fd-96cc912233cd")]
    	public string GlobalChiefFinancialOfficer
    	{
    		get { return _GlobalChiefFinancialOfficer; }
    		set { _GlobalChiefFinancialOfficer = value; }
    	}
    	
        /// <summary>
        /// Constructs a new instance.
        /// </summary>
        public Approval()
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
            
            Delay.Seconds(3);
            repo.WorkdayHomePage.Txt_Search.Click();
            repo.WorkdayHomePage.Txt_Search.PressKeys("{ControlKey down}{AKey}{ControlKey up}");
            Delay.Seconds(3);
            repo.WorkdayHomePage.Txt_Search.PressKeys(sStartProxy);
            Keyboard.Press("{ENTER}");
            repo.WorkdayHomePage.Lnk_StartProxyPage.Lnk_StartProxy.Click();
            
            repo.WorkdayHomePage.MainContent.Txt_ActAs.PressKeys(TreasuryAdministrator);
            Keyboard.Press("{ENTER}");
            Delay.Seconds(3);
            repo.WorkdayHomePage.MainContent.Btn_Ok.Click();
            Delay.Seconds(5);
            
            repo.WorkdayHomePage.Lnk_MailBox.Click();
            repo.WorkdayHomePage.MainContent.Txt_Comment.PressKeys("Approved");
            repo.WorkdayHomePage.MainContent.Btn_Approve.Click();
            
            Delay.Seconds(5);
            repo.WorkdayHomePage.MainContent.Btn_Done.Click();
            
            
            Delay.Seconds(3);
            repo.WorkdayHomePage.Txt_Search.Click();
            repo.WorkdayHomePage.Txt_Search.PressKeys("{ControlKey down}{AKey}{ControlKey up}");
            Delay.Seconds(3);
            repo.WorkdayHomePage.Txt_Search.PressKeys(sStartProxy);
            Keyboard.Press("{ENTER}");
            repo.WorkdayHomePage.Lnk_StartProxyPage.Lnk_StartProxy.Click();
            
            repo.WorkdayHomePage.MainContent.Txt_ActAs.PressKeys(GlobalChiefFinancialOfficer);
            Keyboard.Press("{ENTER}");
            Delay.Seconds(3);
            repo.WorkdayHomePage.MainContent.Btn_Ok.Click();
            Delay.Seconds(5);
            
            repo.WorkdayHomePage.Lnk_MailBox.Click();
            repo.WorkdayHomePage.MainContent.Txt_Comment.PressKeys("Approved");
            repo.WorkdayHomePage.MainContent.Btn_Approve.Click();
            
            Delay.Seconds(5);
            repo.WorkdayHomePage.MainContent.Btn_Done.Click();
            
        }
    }
}
