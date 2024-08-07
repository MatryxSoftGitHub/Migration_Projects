/*
 * Created by Ranorex
 * User: msadmin
 * Date: 27-11-2020
 * Time: 12:22
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
    /// Description of BPOfWorkday.
    /// </summary>
    [TestModule("8122C7F6-947E-47FC-A578-316688A14D67", ModuleType.UserCode, 1)]
    public class BPOfWorkday : ITestModule
    {
    	
    	
    	private static Workday_RanorexRepository repo;
        /// <summary>
        /// Constructs a new instance.
        /// </summary>
        public BPOfWorkday()
        {
        	repo = new Workday_RanorexRepository();
            // Do not delete - a parameterless constructor is required!
        }
        
        
        //string _BPNameVar = "Create Expense Report";
        [TestVariable("8a9e1b2d-3aaa-4540-af3f-dc7f2be0e3ae")]
        public string BPNameVar
        {
        	get { return repo.sTxt_BSNameValue; }
        	set { repo.sTxt_BSNameValue = value;
        		  Delay.Seconds(10);
        		  repo.WorkdayHomePage.Lnk_BPName.Visible.Equals(true);
        		  repo.WorkdayHomePage.Lnk_BPName.Click();
        	
        	}
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
            
            Delay.Seconds(10);
            
        }
    }
}
