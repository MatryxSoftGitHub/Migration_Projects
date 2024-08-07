/*
 * Created by Ranorex
 * User: msadmin
 * Date: 26-11-2020
 * Time: 15:38
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
    /// Description of UserCodeModule1.
    /// </summary>
    [TestModule("52063492-9D27-4779-AFFB-15D6E63AEF3A", ModuleType.UserCode, 1)]
    public class Workday_SearchBP : ITestModule
    {
    	public Workday_RanorexRepository repo = Workday_RanorexRepository.Instance;
    	
    	 
 	
        /// <summary>
        /// Constructs a new instance.
        /// </summary>
        public Workday_SearchBP()
        {
        	 
            // Do not delete - a parameterless constructor is required!
        }     
        
          
        
        
        
        string _sBpName = "Create Expense Report";
        [TestVariable("21e6caae-5b9b-44e2-9229-613995abeb5e")]
        public string sBpName
        {
        	get { return _sBpName; }
        	set { _sBpName = value; }
        }
        
        
        
//        public static Workday_BPName Instance
//        {
//            get { return instance; }
//        }

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
            
           
            repo.WorkdayHomePage.Txt_Search.Click();
            repo.WorkdayHomePage.Txt_Search.PressKeys("{ControlKey down}{AKey}{ControlKey up}");
            repo.WorkdayHomePage.Txt_Search.PressKeys(sBpName);
            Delay.Seconds(4);
            Keyboard.Press("{ENTER}");
            
            
            Delay.Seconds(5);
            
            
            
        }
    }
}
