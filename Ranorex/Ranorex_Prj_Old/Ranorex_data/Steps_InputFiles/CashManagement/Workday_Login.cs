/*
 * Created by Ranorex
 * User: msadmin
 * Date: 26-11-2020
 * Time: 11:56
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
    [TestModule("EB1036F9-4321-49A0-B610-7DF97744D8CE", ModuleType.UserCode, 1)]
    public class Workday_Login : ITestModule
    {
    	Workday_RanorexRepository repo = Workday_RanorexRepository.Instance;
    	
    	
    	string _UserName = "lkumar";
    	[TestVariable("968d408d-7235-4724-85bc-e501dbbf7351")]
    	public string UserName
    	{
    		get { return _UserName; }
    		set { _UserName = value; }
    	}
    	
    	
    	string _Password = "Matryx1@2020";
    	[TestVariable("1bdecc49-079c-4496-bdfe-be9c451cf85e")]
    	public string Password
    	{
    		get { return _Password; }
    		set { _Password = value; }
    	}
    	
        /// <summary>
        /// Constructs a new instance.
        /// </summary>
        public Workday_Login()
        {
            // Do not delete - a parameterless constructor is required!
        }
        
 void ITestModule.Run()
        {
            Mouse.DefaultMoveTime = 300;
            Keyboard.DefaultKeyPressTime = 100;
            Delay.SpeedFactor = 1.0;
            
            repo.WorkdayHomePage.LoginPage.Txt_Username.Click();
            repo.WorkdayHomePage.LoginPage.Txt_Username.PressKeys(UserName);
            repo.WorkdayHomePage.LoginPage.Txt_Password.Click();
            repo.WorkdayHomePage.LoginPage.Txt_Password.PressKeys(Password);
           // repo.WorkdayHomePage.LoginPage.Txt_Password.PressKeys(Password);
            repo.WorkdayHomePage.LoginPage.Btn_SignIn.Click();
            repo.WorkdayHomePage.LoginPage.Btn_Skip.Click();
            
        }
    }
}
