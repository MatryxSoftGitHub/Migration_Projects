/*
 * Created by Ranorex
 * User: msadmin
 * Date: 26-11-2020
 * Time: 15:26
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
    /// Description of Workday_StopProxy.
    /// </summary>
    [TestModule("A9CD67F7-4085-409D-9028-4AC81FF58ADE", ModuleType.UserCode, 1)]
    public class Workday_StopProxy : ITestModule
    {
    	Workday_RanorexRepository repo = Workday_RanorexRepository.Instance;
    	
    	string _sStopProxy = "Stop Proxy";
    	[TestVariable("4798abc8-1415-4ba4-af3c-9ee4382e4cc1")]
    	public string sStopProxy
    	{
    		get { return _sStopProxy; }
    		set { _sStopProxy = value; }
    	}
    	
        /// <summary>
        /// Constructs a new instance.
        /// </summary>
        public Workday_StopProxy()
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
            
            Delay.Seconds(2);
            repo.WorkdayHomePage.Txt_Search.Click();
            repo.WorkdayHomePage.Txt_Search.PressKeys("{ControlKey down}{AKey}{ControlKey up}");
            repo.WorkdayHomePage.Txt_Search.PressKeys(sStopProxy);
            Keyboard.Press("{ENTER}");
            repo.WorkdayHomePage.Lnk_StopProxy.Click();
            Delay.Seconds(2);
            repo.WorkdayHomePage.MainContent.Btn_Ok.Click();
        }
    }
}
