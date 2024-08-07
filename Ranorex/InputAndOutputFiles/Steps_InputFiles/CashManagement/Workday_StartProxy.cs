/*
 * Created by Ranorex
 * User: msadmin
 * Date: 26-11-2020
 * Time: 13:09
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

namespace Workday_Ranorex.Testcase.CashManagement
{
    /// <summary>
    /// Description of Workday_StartProxy.
    /// </summary>
    [TestModule("66F2F6E7-73C1-40D9-8E25-132344325056", ModuleType.UserCode, 1)]
    public class Workday_StartProxy : ITestModule
    {
    	Workday_RanorexRepository repo = Workday_RanorexRepository.Instance;
    	
    	
    	
    	string _sStartProxy = "Start Proxy";
    	[TestVariable("16c431c5-4e57-48c9-9e50-7b13bdcabf8b")]
    	public string sStartProxy
    	{
    		get { return _sStartProxy; }
    		set { _sStartProxy = value; }
    	}
    	
    	
    	
    	
    	string _ProxyAs = "Alice Chow";
    	
    	[TestVariable("74a07807-884f-456e-8259-3ae7b2304260")]
    	public string ProxyAs
    	{
    		get { return _ProxyAs; }
    		set { _ProxyAs = value; }
    	}
    	
    	    	
    	
//    	string _TreasuryAdministrator="James Ready";
//    	[TestVariable("74a07807-884f-456e-8259-3ae7b2304260")]
//    	public string TreasuryAdministrator
//    	{
//    		get { return _TreasuryAdministrator; }
//    		set { _TreasuryAdministrator = value; }
//    	}
    	
    	
    	
 
    	
        /// <summary>
        /// Constructs a new instance.
        /// </summary>
        public Workday_StartProxy()
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
            
            Delay.Seconds(5);
            repo.WorkdayHomePage.Txt_Search.Click();
            repo.WorkdayHomePage.Txt_Search.PressKeys("{ControlKey down}{AKey}{ControlKey up}");
            Delay.Seconds(3);
            repo.WorkdayHomePage.Txt_Search.PressKeys(sStartProxy);
            Keyboard.Press("{ENTER}");
            repo.WorkdayHomePage.Lnk_StartProxyPage.Lnk_StartProxy.Click();
            
            repo.WorkdayHomePage.MainContent.Txt_ActAs.PressKeys(ProxyAs);
            Keyboard.Press("{ENTER}");
            Delay.Seconds(3);
            repo.WorkdayHomePage.MainContent.Btn_Ok.Click();
            Delay.Seconds(5);
        }
    }
}
