/*
 * Created by Ranorex
 * User: msadmin
 * Date: 27-11-2020
 * Time: 17:15
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
	/// Description of LinesTab.
	/// </summary>
	[TestModule("05E20451-48C3-4132-BB2D-993B9D0ED71F", ModuleType.UserCode, 1)]
	public class LinesTab : ITestModule
	{
		Workday_RanorexRepository repo = Workday_RanorexRepository.Instance;
		
		
		string _SpendCategory = "Advertising";
		[TestVariable("561ad5b8-7ebb-479d-a44a-8e89501f8670")]
		public string SpendCategory
		{
			get { return _SpendCategory; }
			set { _SpendCategory = value; }
		}
		
		
		string _Quantity = "1";
		[TestVariable("679eb217-a29d-45c5-90a8-490e02c17e37")]
		public string Quantity
		{
			get { return _Quantity; }
			set { _Quantity = value; }
		}
		
		
		string _UnitCost = "1000";
		[TestVariable("405445a7-c08c-47f1-86f9-3f5f5be89a51")]
		public string UnitCost
		{
			get { return _UnitCost; }
			set { _UnitCost = value; }
		}
		
		
		string _CostCenter = "100-000 General Overhead";
		[TestVariable("dd18d164-7ad2-46a4-b2e2-902281dc7c7e")]
		public string CostCenter
		{
			get { return _CostCenter; }
			set { _CostCenter = value; }
		}
		
		
		/// <summary>
		/// Constructs a new instance.
		/// </summary>
		public LinesTab()
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
			
			repo.WorkdayHomePage.MainContent.Tab_Lines.Click();
			Delay.Seconds(1);
			repo.WorkdayHomePage.MainContent.Txt_SpendCategory.Click();
			Delay.Seconds(1);
			repo.WorkdayHomePage.MainContent.Txt_SpendCategory.PressKeys(SpendCategory);
//			repo.WorkdayHomePage.MainContent.Txt_SpendCategory.Focus(SpendCategory);
			
			Keyboard.Press("{ENTER}");
			repo.WorkdayHomePage.Rbtn_Advertising.Click();
			Delay.Seconds(2);
			repo.WorkdayHomePage.MainContent.Txt_Quantity.PressKeys("{ControlKey down}{AKey}{ControlKey up}");
			repo.WorkdayHomePage.MainContent.Txt_Quantity.PressKeys(Quantity);
			repo.WorkdayHomePage.MainContent.Txt_UnitCost.PressKeys("{ControlKey down}{AKey}{ControlKey up}");
			repo.WorkdayHomePage.MainContent.Txt_UnitCost.PressKeys(UnitCost);
			repo.WorkdayHomePage.MainContent.Txt_CostCenter.PressKeys(CostCenter);
			repo.WorkdayHomePage.MainContent.Tab_Attachments.Click();
			repo.WorkdayHomePage.MainContent.Lnk_SelectFiles.Click();
			Delay.Seconds(2);
			repo.Open.Cbo_File.Click();
			repo.Open.Txt_File.PressKeys(@"F:\Workday_Ranorex\Text Document.txt");
			
			//var projectDirectory =   System.IO.Directory.GetParent(System.IO.Directory.GetCurrentDirectory()).Parent.FullName;
			
			//repo.WorkdayHomePage.MainContent.Lnk_SelectFiles.PressKeys(projectDirectory"\\Text Document.txt");
			
			repo.Open.Btn_Open.Click();
			//repo.WorkdayHomePage.MainContent.Btn_Submit.Click();
					
			
		}
	}
}
