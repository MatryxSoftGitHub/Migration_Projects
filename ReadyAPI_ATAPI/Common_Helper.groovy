/*
 *Created By  :Prashanth Pai
 *Created Date:19-09-2016
 *Description : This class will be used for CommonHelper Methods 
 *Version : 1.0
 *Last Modified : 19-09-2016
 */
import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;
import java.security.InvalidKeyException;
import java.text.SimpleDateFormat;
import java.text.DateFormat;
import groovy.time.TimeCategory;
import com.eviware.soapui.impl.wsdl.testcase.WsdlTestCaseRunner
import com.eviware.soapui.support.types.StringToObjectMap
import com.eviware.soapui.impl.wsdl.testcase.WsdlTestRunContext
import com.eviware.soapui.support.types.StringToStringMap;
import com.eviware.soapui.impl.wsdl.submit.transports.http.BaseHttpRequestTransport
import com.eviware.soapui.impl.wsdl.support.CompressionSupport
import org.apache.http.entity.StringEntity
import java.io.StringWriter; 
import java.sql.Connection; 
import java.sql.Statement; 
import java.io.*;
import java.sql.DriverManager; 
import java.sql.ResultSet; 
import java.sql.SQLException;
import java.sql.ResultSetMetaData;  
import javax.xml.parsers.DocumentBuilder; 
import javax.xml.parsers.DocumentBuilderFactory; 
import javax.xml.transform.OutputKeys; 
import javax.xml.transform.Transformer; 
import javax.xml.transform.TransformerFactory; 
import javax.xml.transform.dom.DOMSource; 
import javax.xml.transform.stream.StreamResult;  
import org.w3c.dom.Document; 
import org.w3c.dom.Element;
import java.util.Calendar;
import java.util.Date;
import groovy.lang.StringWriterIOException;
import groovy.lang.Writable;
import org.codehaus.groovy.runtime.typehandling.DefaultTypeTransformation;
import java.io.IOException;
import java.io.StringWriter;
import java.io.UnsupportedEncodingException;
import java.io.Writer;
import net.sf.json.JSON;
import net.sf.json.xml.XMLSerializer;
import jxl.*
import jxl.write.*
import jxl.read.*
import com.eviware.soapui.support.XmlHolder
import groovy.lang.Binding
import groovy.util.GroovyScriptEngine
import java.util.Map
import org.apache.commons.lang.RandomStringUtils
import java.sql.CallableStatement;


// CREDIT TESTING //
import com.eviware.soapui.model.testsuite.Assertable
import com.eviware.soapui.model.testsuite.Assertable.AssertionStatus

class CommonHelper {
 public def updatedtagValueDB= "IS NULL"

 /*
  *Created By  :Prashanth Dayalan
  *Created Date:19-09-2016
  *Parameters  : HmacKey,Token,ServerTimeStamp
  *Description : This method will be used to generate the Hmac header by using sha encodeing
  *Version : 1.0
  *Last Modified : 19-09-2016
  */
 def hmac_sha(String secretKey, String data, String hmacSHA) {
  try {
   Mac mac = Mac.getInstance(hmacSHA);
   SecretKeySpec secretKeySpec = new SecretKeySpec(secretKey.getBytes(), hmacSHA);
   mac.init(secretKeySpec);
   byte[] digest = mac.doFinal(data.getBytes());
   return digest;
  } catch (InvalidKeyException e) {
   throw new RuntimeException("Invalid key exception while converting to HMac SHA");
  }
 }

 public class microTimestamp {
  private long startDate;
  private long startNanoseconds;
  private SimpleDateFormat dateFormat;

  // Get the time-stamp with microseconds accuracy

  private microTimestamp() {
   this.startDate = System.currentTimeMillis();
   this.startNanoseconds = System.nanoTime();
   this.dateFormat = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS");
  }

  // Get the time-zone offset with GMT e.g. +05:30 or -10:30

  private String getTimeZoneOffset() {
   def timeStamp = new java.text.SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSSSSSSXXX").format(new Date());
   return timeStamp.substring(27, 33);
  }

  // Convert the Server's timestamp

  private String convertServerTimeStamp(serverTimeStamp) {
   long milliStart = System.currentTimeMillis();
   long microSeconds = (System.nanoTime() - this.startNanoseconds) / 1000;
   DateFormat utcFormat = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss");
   utcFormat.setTimeZone(TimeZone.getTimeZone("UTC"));
   DateFormat localFormat = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS");
   localFormat.setTimeZone(TimeZone.getTimeZone(TimeZone.getDefault().getID()));
   Date timestamp = null;

   try {
    timestamp = utcFormat.parse(serverTimeStamp);
   } catch (Exception e) {
    println("Error in parsing the server timestamp")
    e.printStackTrace();

   }
   long milliEnd = System.currentTimeMillis();
   long latency = (milliEnd - milliStart) / 2;
   return localFormat.format(timestamp.getTime() + latency) + String.format("%04d", microSeconds % 1000);
  }

 }

 public String hmacGeneration(String hmacKey, String tokenValue, String serverTimeStamp, String hmacSHA, String TokenName) {
  def timeStampObj = new microTimestamp();
  def Timestamp = timeStampObj.convertServerTimeStamp(serverTimeStamp) + timeStampObj.getTimeZoneOffset();
  def data = tokenValue + ";" + Timestamp;
  def HMACValue = hmac_sha(hmacKey, data, hmacSHA);
  def HASHValue = HMACValue.encodeBase64().toString()
  def Header = "HMAC Timestamp=$Timestamp;Hash=$HASHValue;$TokenName=$tokenValue";
  return Header.trim();
 }
=====================================================Finish============================================
 /*
  *Created By  :Rakesh Anna
  *Created Date:19-09-2016
  *Parameters  : timeXpath
  *Description : This method will be used to FormattedDateTime
  *Version : 1.0
  *Last Modified : 19-09-2016
  */

 //Adding 'T' between Date and Time

 public def getFormattedDateTime(String timeXpath) {
  def DateTimeXpath = timeXpath
  def DateTimeSplit = DateTimeXpath.split();
  def splitTimeNanoSec = DateTimeSplit[1].split("\\.");
  def finalDateTime = DateTimeSplit[0] + "T" + splitTimeNanoSec[0];
    return finalDateTime
 }

 /*
  *Created By  :Rakesh Anna
  *Created Date:27-09-2016
  *Parameters  : directory
  *Description : This method will be used to Get Project Directory
  *Version : 1.0
  *Last Modified : 04-11-2016
  */

 public def getProjectDirectory(String directory) {
  def ProjectDir = directory
  def finalPath = ProjectDir.split("Projects")
  com.eviware.soapui.SoapUI.globalProperties.setPropertyValue("projectDirectory", finalPath[0])
 }
==================================================Finish=======================================================
 /*Created By :Ujjawal Kumar
  *Created Date:07-12-2016
  *Parameters  : ResponseTime, DBTime
  *Description : To replace "T" from the response to " " and removing millisecond from the DB date and Response Date
  *Version : 1.0
  *Last Modified :07-12-2016
  */
 public String[] formatTime(String ResponseTime, String DBTime) {
  String UpdateModifiedTime = ResponseTime.replace("T", " ")
  String[] FinalModifiedTime = UpdateModifiedTime.split("\\.")
  String[] lastModifiedTime = DBTime.split("\\.")
  String[] result = [FinalModifiedTime[0], lastModifiedTime[0]]
  return result;
 }
==============================================Finish========================================================
 /*
  *Created By  :Ujjawal Kumar 
  *Created Date:19-12-2016
  *Parameters  : timeStamp
  *Description : This method will decrement the time by 8hr 00mins
  *Version : 1.0
  *Last Modified : 19-12-2016
  */
 public def changeTime(String timeStamp) {
  def changedTime = timeStamp.replace("T", " ")
  def updatedTime = changedTime.split("\\.")
  String sDate6 = updatedTime[0]

  SimpleDateFormat formatter6 = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
  Date date6 = formatter6.parse(sDate6);
  def incrementTime = null;
  use(TimeCategory) {
   incrementTime = date6 + 5. hour + 30. minutes
  }
  return incrementTime.format("yyyy-MM-dd HH:mm:ss")
 }
==============================================Finish===================================================
 /*
  *Created By  :Rakesh Anna
  *Created Date:29-12-2016
  *Parameters  : Path,ServerName,UserName,Password
  *Description : This method will be used to runPowerShellScript
  */
 public def runPowerShellScript(String Path, String ServerName, String UserName, String Password) {
  def serverName = ServerName
  def userName = UserName
  def password = Password
  def path = Path
  def command = "Powershell.exe -file ${path} ${serverName} ${userName} ${password}";
  def myList = []
  def line;
  def process = Runtime.getRuntime().exec(command);
  process.getOutputStream().close();
  BufferedReader stdout = new BufferedReader(new InputStreamReader(
   process.getInputStream()));
  while ((line = stdout.readLine()) != null) {
   myList.add(line)
  }
  stdout.close();
  return myList;
 }
====================================================Finish============================================
 /*
  *Created By  :Rakesh Anna
  *Created Date:29-12-2016
  *Parameters  : datetime
  *Description : This method will be used to increment by a Minute for given date time
  */
 public def incrementMinute(String datetime) {
   def dateTimesplit = datetime.split("\\.")
   SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm");
   Date dateTimeStamp = formatter.parse(datetime);
   def decrementedDateTime = null;
   use(TimeCategory) {
    decrementedDateTime = dateTimeStamp + 1. minutes
   }
   def finalDateTime = decrementedDateTime.format("YYYY-MM-dd'T'HH:mm")
   return finalDateTime;
  }
=====================================================Finish============================================
  /*
   *Created By  :Rakesh Anna
   *Created Date:29-12-2016
   *Parameters  : datetime
   *Description : This method will be used to decrement by a Minute for given date time
   */
 public def decrementMinute(String datetime) {
  def dateTimesplit = datetime.split("\\.")
  SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm");
  Date dateTimeStamp = formatter.parse(datetime);
  def decrementedDateTime = null;
  use(TimeCategory) {
   decrementedDateTime = dateTimeStamp - 1. minutes
  }
  def finalDateTime = decrementedDateTime.format("YYYY-MM-dd'T'HH:mm")
  return finalDateTime;
 }
=====================================================Doubt=================================================
 /*
  *Created By  :Rakesh Anna
  *Created Date:24-1-2017
  *Parameters  : testRunner,context,responseXpath,testStepName,parameterName
  *Description : This method will be used to fetch the String tag Values from the response
  */
 public void fetchStringValues(testRunner, context, log, def responseXpath, def testStepName, def parameterName) {

  def stringTags = new XmlSlurper().parseText(responseXpath)
  log.info("Total Items " + stringTags.size())
  for (numberOfTags in stringTags.string) {
   log.info("Response Item Name " + numberOfTags.toString())
   testRunner.testCase.setPropertyValue(parameterName, numberOfTags.toString())
   testRunner.testCase.getTestStepByName(testStepName).run(testRunner, context)
  }
 }
==============================Finished================================================================
 /*
  *Created By  :Rakesh Anna
  *Created Date:10-11-2016
  *Parameters  : clientIP,secureIp
  *Description : This method will be used to Set The EndPoint
  *Version : 1.0
  *Last Modified : 10-11-2016
  */

 public def setXmlEndpoint(String clientIP, String secureIp) {
  String endpoint = secureIp + "://" + clientIP
  return endpoint
 }
==================================Finish============================================
 /*
  *Created By  :Rakesh Anna
  *Created Date:10-11-2016
  *Parameters  : clientIP,secureIp
  *Description : This method will be used to Set The EndPoint
  *Version : 1.0
  *Last Modified : 10-11-2016
  */

 public def setSoapEndpoint(String clientIP, String resource, String secureIp) {
  String endpoint = secureIp + "://" + clientIP + resource
  return endpoint
 }
==================================Finish============================================
 /*
  *Created By  :Rakesh Anna
  *Created Date:19-09-2016
  *Parameters  : time
  *Description : This method will be used to add T and remove miliseconds in time
  *Version : 1.0
  *Last Modified : 19-09-2016
  */

 //Adding 'T' between Date and Time

 public def modifyDBTime(String time) {
  def UpdatedTime = time.replace(" ", 'T')
  def FinalTime = UpdatedTime.toString().split("\\.")
  return FinalTime[0];
 }

==================================Finish============================================
 /*
  *Created By  :Rakesh Anna
  *Created Date:19-09-2016
  *Parameters  : time
  *Description : This method will be used to FormattedDateTime
  *Version : 1.0
  *Last Modified : 19-09-2016
  */

 //Changing date Format 

 public def changeDateFormat(String time) {
  def UpdatedChangeDateFormat = ChangeDateFormat.toString().split("\\.")
  def FinalChangeDateFormat = Date.parse("yyyy-MM-dd hh:mm:ss", UpdatedChangeDateFormat[0]).format("MM/dd/yyyy HH:mm:ss")

  return FinalChangeDateFormat;
 }

===========================Finish========================
 /*
  *Created By  :Rakesh Anna
  *Created Date:28-10-2016
  *Parameters  : Path,Action,TestString
  *Description : This method will be used to endocde and decode the data
  */
 public def encoderDecoder(String Path, String Action, String TestString) {

  def testString = TestString
  def action = Action
  def path = Path
  def command = "${path} ${action} ${testString}";
  String myList = "";
  def line;
  def process = Runtime.getRuntime().exec(command);
  process.getOutputStream().close();
  BufferedReader stdout = new BufferedReader(new InputStreamReader(process.getInputStream()));
  while ((line = stdout.readLine()) != null) {
   myList = myList.concat(line + "\n")
  }

  stdout.close();
  return myList;
 }
===================================Finished======================================
 /*
  *Created By  :Rakesh Anna
  *Created Date:14-12-2016
  *Parameters  :strdatetime
  *Description : This method will be used for invalid Date time expire header
 */
 public def expiredHmacToken(String datetime) {
  def dateTimesplit = datetime.split("\\.")
  SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss");
  Date dateTimeStamp = formatter.parse(dateTimesplit[0]);
  def decrementedDateTime = null;
  use(TimeCategory) {
   decrementedDateTime = dateTimeStamp - 150. minutes
  }
  def finalDateTime = decrementedDateTime.format("YYYY-MM-dd'T'HH:mm:ss.SSSSSSS'Z'")
  return finalDateTime;
 }

 /*
  *Created By  :Rakesh
  *Created Date:30-1-2017
  *Parameters  :testRunner,log,request,testStepName,serverTimestamp,HMACKey,includeTimeStampInHash,hmacSHA
  *Description : This method will be used for generating Hash value of request body and set the header values for XML-RPC 
  */
  public void generateHash(testRunner, log, String authorizationHeader, String hashHeader, String HeaderStatus, String request, String testStepName, String serverTimestamp, String HMACKey, boolean includeTimeStampInHash, String hmacSHA) {
   request.toString().replaceAll(/>\s+</, '><').trim()
   String toBeHashedstring;
   if (includeTimeStampInHash) {
    if (request != null) {
     toBeHashedstring = request + ";" + serverTimestamp;
    } else
     toBeHashedstring = serverTimestamp;
   } else {
    toBeHashedstring = request;
   }
   def HMACValue = hmac_sha(HMACKey, toBeHashedstring, hmacSHA);
   def HASHValue = HMACValue.encodeBase64().toString();
   log.info("X-s-Content-Signature " + HASHValue.trim())
   def headers = new StringToStringMap()
   if (HeaderStatus == "Active") {
    headers.put(authorizationHeader, '${#Project#HmacKey}')
  headers.put(hashHeader, HASHValue.trim())
   } else if (HeaderStatus == "Expired") {
    headers.put(authorizationHeader, '${#Project#ExpiredHmacKey}')
  headers.put(hashHeader, HASHValue.trim())
   }
   else if (HeaderStatus == "NonAdmin") {
    headers.put(authorizationHeader, '${#Project#Non_Admin_HmacKey}')
  headers.put(hashHeader, HASHValue.trim())
   }
   else if(HeaderStatus == "Invalid")
   {
   headers.put(authorizationHeader, '${#Project#InvalidHmacKey}')
    headers.put(hashHeader, HASHValue.trim())
   }
   else if(HeaderStatus.contains("User"))
   {
   def HeaderValue = "\${#Project#" + HeaderStatus + "_Hmac}"
   headers.put(authorizationHeader, HeaderValue)
   headers.put(hashHeader, HASHValue.trim())
   }
   else if(HeaderStatus == "EmptyContentHeader")
   {
   headers.put(authorizationHeader, '${#Project#HmacKey}')
    headers.put(hashHeader, "")
   }
   else if(HeaderStatus == "InvalidContentHeader")
   {
 headers.put(authorizationHeader, '${#Project#HmacKey}')
    headers.put(hashHeader, HASHValue.trim()+"gkjsbfkjcvhfkjuxdzhvudhvudxchnvudhn")
   }
   else if(HeaderStatus == "WINDOWSINTEGRATED")
   {
   headers.put(authorizationHeader, '${#Project#WindowsIntegrated_Hmac}')
   headers.put(hashHeader, HASHValue.trim())
   }
    else if(HeaderStatus == "WithoutContentHeader")
   {
 headers.put(authorizationHeader, '${#Project#HmacKey}')
   }
   else if(HeaderStatus.contains("UserWithAllAdminRights"))
   {
   def HeaderValue = "\${#Project#UserWithAllAdminRights_Hmac}"
   headers.put(authorizationHeader, HeaderValue)
   headers.put(hashHeader, HASHValue.trim())
   }
   else if(HeaderStatus.contains("UserWithFewAdminRights"))
   {
   def HeaderValue = "\${#Project#UserWithFewAdminRights_Hmac}"
   headers.put(authorizationHeader, HeaderValue)
   headers.put(hashHeader, HASHValue.trim())
   }
   else if(HeaderStatus.contains("UserWithoutAdminRights"))
   {
   def HeaderValue = "\${#Project#UserWithoutAdminRights_Hmac}"
   headers.put(authorizationHeader, HeaderValue)
   headers.put(hashHeader, HASHValue.trim())
   }       
   testRunner.testCase.getTestStepByName(testStepName).testRequest.setRequestHeaders(headers)
  }

  /*
   *Created By  :Rakesh Anna
   *Created Date:20-1-2017
   *Parameters  : testRunner,context,log,testCaseName,responseTestStepName,dbTestStepName,responseXpath,dbXpath
   *Description : This method will be used to poll the Response of check login session Until it Matches Active Session Length from data base
   */
 public String[] pollUntilActiveSessionLengthMatches(testRunner, context, log, testCaseName, responseTestStepName, dbTestStepName, responseXpath, dbXpath) {

  def responseMinutes = context.expand(responseXpath)
  log.info("responseMinutes " + responseMinutes)
  def responseTestStep = testRunner.testCase.testSuite.testCases[testCaseName].testSteps[responseTestStepName]
  responseTestStep.run(testRunner, context)
  def latestResponseMinutes = context.expand(responseXpath)
  log.info("latestResponseMinutes " + latestResponseMinutes)
  def dbTestStep = testRunner.testCase.testSuite.testCases[testCaseName].testSteps[dbTestStepName]
  dbTestStep.run(testRunner, context)
  def activeSessionSeconds = context.expand(dbXpath)
  def dbResponseConvertToMinutes = activeSessionSeconds.toInteger() / 60
  def dbMinutes = dbResponseConvertToMinutes.toString().split("\\.");
  log.info("data base Minutes " + dbMinutes[0])
  int activeSessionResponseMinutes = 0;
  if (responseMinutes.toInteger() + 1 == latestResponseMinutes && latestResponseMinutes == dbMinutes[0]) {

   log.info(" Active Session Length Minutes in response is same as DB " + responseMinutes.toInteger() + 1 == dbMinutes[0])
  } else {

   for (int numberOfIterations = 0; numberOfIterations <= 12; numberOfIterations++) {
    responseTestStep.run(testRunner, context)
    def convertActiveSessionLengthMinutes = context.expand(responseXpath)
    activeSessionResponseMinutes = convertActiveSessionLengthMinutes.toInteger()
    if (responseMinutes.toInteger() + 1 != activeSessionResponseMinutes) {
     sleep(5000);
    } else {
     log.info("Active Session Length Minutes in response is same as DB " + responseMinutes.toInteger() + 1 == dbMinutes[0])
     dbTestStep.run(testRunner, context)
     activeSessionSeconds = context.expand(dbXpath)
     dbResponseConvertToMinutes = activeSessionSeconds.toInteger() / 60
     dbMinutes = dbResponseConvertToMinutes.toString().split("\\.");
     break;
    }
   }
  }
  String[] result = [responseMinutes.toInteger() + 1, activeSessionResponseMinutes, dbMinutes[0].toInteger()]
  return result
 }
===========================Confermation========================
 /*
  *Created By  :Rakesh Anna
  *Created Date:20-1-2017
  *Parameters  : testRunner,context,testCaseName,testStepName,ResponseXpath
  *Description : This method will be Polling of test step until For Response displayed
  */
 public def waitForResponse(testRunner, context, testCaseName, testStepName, ResponseXpath) {
  int NumberOfIterations = 0;

  def testStep = testRunner.testCase.testSuite.testCases[testCaseName].testSteps[testStepName]     (Confermation)
  testStep.run(testRunner, context)
  def ResponseContent = context.expand(ResponseXpath)
  while ((ResponseContent == "" || ResponseContent.contains("HTTP Error 503. The service is unavailable.")) && NumberOfIterations < 75) {
   sleep(2000);
   testStep.run(testRunner, context)
   ResponseContent = context.expand(ResponseXpath)
   NumberOfIterations++
  }
  return NumberOfIterations
 }
===========================Finished========================
 /*
  *Created By  : Prabhu Nagaraj
  *Created Date: 24-01-2017
  *Parameters  : path,serverIp,userName,password,serviceName
  *Description : This method is used to get the process ID from the server task manager.
  */
 public def getProcessId(String path, String serverIp, String userName, String password, String serviceName) {

   def ServerName = serverIp
   def UserName = userName
   def Password = password
   def ProcessName = serviceName
   def Path = path
   def command = "Powershell.exe -file ${Path} ${ServerName} ${UserName} ${Password} ${ProcessName}";
   def myList = []
   def line;
   def process = Runtime.getRuntime().exec(command);
   process.getOutputStream().close();
   BufferedReader stdout = new BufferedReader(new InputStreamReader(
    process.getInputStream()));
   while ((line = stdout.readLine()) != null) {                      (Confermation)
    myList.add(line)
   }

   stdout.close();
   return myList;
  }
  /*

  *Created By  :Rakesh Anna

  *Created Date:02-03-2017

  *Parameters  :testRunner,authorizationHeader,testStepName

  *Description : This method will be used to set the header values for soap request

  */

 public void setSoapHeader(testRunner, String authorizationHeader,  String testStepName, String headerStatus) {

   def headers = new StringToStringMap()
   
	if(headerStatus == "Valid")
	{
   headers.put(authorizationHeader, '${#Project#HmacKey}')
    }
   
   else if(headerStatus == "Expired")
   {
   headers.put(authorizationHeader, '${#Project#ExpiredHmacKey}')
   }
   
   else if(headerStatus == "Invalid")
   {
   headers.put(authorizationHeader, '${#Project#InvalidHmacKey}')
   }
    else if(headerStatus == "WINDOWSINTEGRATED")
   {
   headers.put(authorizationHeader, '${#Project#WindowsIntegratedUser_HmacKey}')
   }
    else if(headerStatus.contains("User"))
   {
   def HeaderValue = "\${#Project#" + headerStatus + "_Hmac}"
   headers.put(authorizationHeader, HeaderValue)
   }
   else if(headerStatus.contains("UserWithAllAdminRights"))
   {
   def HeaderValue = "\${#Project#" + headerStatus + "_Hmac}"
   headers.put(authorizationHeader, HeaderValue)
   }
    else if(headerStatus.contains("UserWithoutAdminRights"))
   {
   def HeaderValue = "\${#Project#" + headerStatus + "_Hmac}"
   headers.put(authorizationHeader, HeaderValue)
   }
    else if(headerStatus.contains("UserWithFewAdminRights"))
   {
   def HeaderValue = "\${#Project#" + headerStatus + "_Hmac}"
   headers.put(authorizationHeader, HeaderValue)
   }  
   else if(headerStatus == "OpenIdConnect")
   {
		def HeaderValue = "Bearer " +  "\${#TestCase#" + "AccessToken}"
		headers.put(authorizationHeader, HeaderValue)
   }
  
   testRunner.testCase.getTestStepByName(testStepName).testRequest.setRequestHeaders(headers)
===========================Finished========================
  }
  /*
   *Created By  :Rakesh Anna
   *Created Date:30-03-2017
   *Parameters  : Path, ServerName, UserName, Password, action
   *Description : This method will be used to runPowerShellScript
   */
 public def runPowerShellScript(String Path, String ServerName, String UserName, String Password, String Action) {
  def serverName = ServerName
  def userName = UserName
  def password = Password
  def path = Path
  def action = Action
  def command = "Powershell.exe -file ${path} ${serverName} ${userName} ${password} ${action}";
  def myList = []
  def line;
  def process = Runtime.getRuntime().exec(command);
  process.getOutputStream().close();
  BufferedReader stdout = new BufferedReader(new InputStreamReader(
   process.getInputStream()));
  while ((line = stdout.readLine()) != null) {
   myList.add(line)
  }

  stdout.close();
  return myList;
 }
=================================Finished================================
 /*
  *Created By  : Prasanth Dayalan
  *Created Date: 23/03/17
  *Parameters  : commandLineExePath, configuredXMLFile, resultLogFolderPath, dcmFilesPath, importFromSubDirectories, importMultipleFiles and storageCommit
  *Description : This method is to execute the IWatt commandline exe to send DCMImages to server.
  */
 public void IWattConsoleExecution(log, String commandLineExePath, String configuredXMLFile, String resultLogFolderPath, String dcmFilesPath, String importFromSubDirectories, String importMultipleFiles, String storageCommit) {

  def command = commandLineExePath + " " + configuredXMLFile + " " + "results" + " " + resultLogFolderPath + " " + "store" + " " + dcmFilesPath + " " + importFromSubDirectories + " " + importMultipleFiles + " " + storageCommit;

  def line;
  def process = Runtime.getRuntime().exec(command);
  process.getOutputStream().close();
  BufferedReader stdout = new BufferedReader(new InputStreamReader(
   process.getInputStream()));
  while ((line = stdout.readLine()) != null) {
   log.info line
  }

  stdout.close();
 }
=================================Finished===============================
/*
*Created By  :Ujjawal Kumar
*Created Date:27-02-2017
*Parameters  : HexadecimalVal
\
*Description : This method will be used to check if the input value is Hexadeciaml or not
*/

public def CheckIsHex(def HexadecimalVal)
 {
   if ( HexadecimalVal.length() == 0 || 
         (HexadecimalVal.charAt(0) != '-' && Character.digit(HexadecimalVal.charAt(0), 16) == -1))
        return false;
    if ( HexadecimalVal.length() == 1 && HexadecimalVal.charAt(0) == '-' )
        return false;

    for ( int i = 1 ; i < HexadecimalVal.length() ; i++ )
        if ( Character.digit(HexadecimalVal.charAt(i), 16) == -1 )
            return false;
    return true;
}
==============================================Finished==================================
 /*
*Created By  :Rakesh Anna

  *Created Date:02-03-2017

  *Parameters  :Intvalue

  *Description : This method will be used to convert binary to boolean

  */
 public def intToBoolean(def Intvalue) {
  def BooleanValue = (Intvalue.equals('1')) ? "true" : "false"
  return BooleanValue
 }
=============================================Finished======================================
 /*
  *Created By  : Rakesh Anna
  *Created Date : 02-05-2017
  *Parameters  : path,serverIp,userName,password,filePath
  *Description : This method will get file length of the file specified
  */
 public def getFileLength(String path, String serverIp, String userName, String password, String filePath) {

  def ServerName = serverIp
  def UserName = userName
  def Password = password
  def FilePath = filePath
  def Path = path
  def command = "Powershell.exe -file ${Path} ${ServerName} ${UserName} ${Password} ${FilePath}";
  def myList = []
  def line;
  def process = Runtime.getRuntime().exec(command);
  process.getOutputStream().close();
  BufferedReader stdout = new BufferedReader(new InputStreamReader(
   process.getInputStream()));
  while ((line = stdout.readLine()) != null) {
   myList.add(line)
    //log.info line
  }

  stdout.close();
  return myList;
 }
==================================Finished===============================
 /*
  *Created By  : Rakesh Anna
  *Created Date : 02-05-2017
  *Parameters  : path,serverIp,userName,password,folderName
  *Description : This method will return the list of files present in the path
  */
 public def listFiles(String path, String serverIp, String userName, String password, String folderName) {

  def ServerName = serverIp
  def UserName = userName
  def Password = password
  def FolderName = folderName
  def Path = path
  def command = "Powershell.exe -file ${Path} ${ServerName} ${UserName} ${Password} ${FolderName}";
  def myList = []
  def line;
  def process = Runtime.getRuntime().exec(command);
  process.getOutputStream().close();
  BufferedReader stdout = new BufferedReader(new InputStreamReader(
   process.getInputStream()));
  while ((line = stdout.readLine()) != null) {
   myList.add(line)
    //log.info line
  }

  stdout.close();
  return myList;
 }
==================================Finished===============================
/*
   *Created By  : Prasanth Dayalan
   *Created Date: 06/04/17
   *Parameters  : log, HL7FillingExePath, numberOfRecords, serverIp, organization and portNumber.
   *Description : This method is to send the HL7 data to the server using HL7Filing.exe
   */
 public void sendHl7Data(log,def HL7FilingExePath, def numberOfRecords, def serverIp, def organization, def portNumber)
 {
 def command = HL7FilingExePath+" -c "+numberOfRecords+" -h "+serverIp+" -i "+organization+" -p "+portNumber;
  
 def line;
  def process = Runtime.getRuntime().exec(command);
  process.getOutputStream().close();
  BufferedReader stdout = new BufferedReader(new InputStreamReader(
   process.getInputStream()));
  while ((line = stdout.readLine()) != null) {
   log.info line
  }

  stdout.close();
   }
 ==================================Confermation===============================
 /*
*Created By  :Rakesh Anna

  *Created Date:02-03-2017

  *Parameters  :testRunner,log,valueSource,propertyName,propertyValueDbPath

  *Description : This method will be used to set input properties.

  */
 public def setInputProperty(testRunner, log, def valueSource, def propertyName, def propertyValueDbPath) {
	if (valueSource == "DB") {
	  testRunner.testCase.setPropertyValue(propertyName, propertyValueDbPath)
	  log.info(propertyValueDbPath + " : " + propertyValueDbPath)
	 }else if (valueSource == "DataGen") {
	  testRunner.testCase.setPropertyValue(propertyName, propertyValueDbPath)
	  log.info(propertyValueDbPath + " : " + propertyValueDbPath)
	 }else if (valueSource == "Valid") {
	  testRunner.testCase.setPropertyValue(propertyName, propertyValueDbPath)
	  log.info(propertyValueDbPath + " : " + propertyValueDbPath)
	 }else if (valueSource == "serverValue") {
	  testRunner.testCase.setPropertyValue(propertyName, propertyValueDbPath)
	  log.info(propertyValueDbPath + " : " + propertyValueDbPath)
	 }else  {
   testRunner.testCase.setPropertyValue(propertyName, valueSource)
   log.info(propertyName + " : " + valueSource)
  }
 }
 ==================================Finished==============================
 /*
*Created By  :Rakesh Anna

  *Created Date:02-03-2017

  *Parameters  :testRunner,log,valueSource,propertyName,propertyValueDbPath,validResourceKey

  *Description : This method will be used to set input properties.

  */
	public def setInputProperty(testRunner, log, def valueSource, def propertyName, def propertyValueDbPath, def validResourceKey) {
	 if (valueSource == "DB") {
	  testRunner.testCase.setPropertyValue(propertyName, propertyValueDbPath)
	  log.info(propertyName + " : " + propertyValueDbPath)
	 } else if (valueSource == "Valid") {
	  testRunner.testCase.setPropertyValue(propertyName, validResourceKey)
	  log.info(propertyName + " : " + validResourceKey)
	 } else if (valueSource == "InvalidInput") {
	  testRunner.testCase.setPropertyValue(propertyName, validResourceKey)
	  log.info(propertyName + " : " + validResourceKey)
	 } else if (valueSource == "InvalidValue") {
      testRunner.testCase.setPropertyValue(propertyName, validResourceKey)
      log.info(propertyName + " : " + validResourceKey)
     } else if (valueSource == "Value") {
	  testRunner.testCase.setPropertyValue(propertyName, validResourceKey)
	  log.info(propertyName + " : " + validResourceKey)
	 } else if (valueSource == "DataGen") {
	  testRunner.testCase.setPropertyValue(propertyName, validResourceKey)
	  log.info(propertyName + " : " + validResourceKey)
	 } else if (valueSource == "/Valid") {
	  testRunner.testCase.setPropertyValue(propertyName, validResourceKey)
	  log.info(propertyName + " : " + validResourceKey)
	 }else {
	  testRunner.testCase.setPropertyValue(propertyName, valueSource)
	  log.info(propertyName + " : " + valueSource)
	 }
	}

 ==================================Finished==============================
 /*
*Created By  :Rakesh Anna

  *Created Date:21-04-2017

  *Parameters  :testRunner,log,valueSource,propertyName,propertyValueDbPath,validValuePath

  *Description : This method will be used to set input properties.
  
  *Modified By  :Prabhu
  
  *Modified Date:03-05-2017

  */
 public def setInputProperty(testRunner, log, def valueSource, def propertyName, def propertyValueDbPath, def validValuePath, def differentValuePath) {
  if (valueSource == "DB") {
   testRunner.testCase.setPropertyValue(propertyName, propertyValueDbPath)
   log.info(propertyName + " : " + propertyValueDbPath)
  } else if (valueSource == "Valid") {
   testRunner.testCase.setPropertyValue(propertyName, validValuePath)
   log.info(propertyName + " : " + validValuePath)
  } else if (valueSource == "InvalidValue") {
   testRunner.testCase.setPropertyValue(propertyName, validValuePath)
   log.info(propertyName + " : " + validValuePath)
  } else if (valueSource == "Value") {
   testRunner.testCase.setPropertyValue(propertyName, validValuePath)
   log.info(propertyName + " : " + validValuePath)
  } else if (valueSource == "DataGen") {
   testRunner.testCase.setPropertyValue(propertyName, validValuePath)
   log.info(propertyName + " : " + validValuePath)
  } else if (valueSource == "differentkey") {
   testRunner.testCase.setPropertyValue(propertyName, differentValuePath)
   log.info(propertyName + " : " + differentValuePath)
  } else {
   testRunner.testCase.setPropertyValue(propertyName, valueSource)
   log.info(propertyName + " : " + valueSource)
  }
 }
===================================Finished===========================
 /*
*Created By  :Rakesh Anna

  *Created Date:02-03-2017

  *Parameters  :testRunner,log,expectedStatus,firstStatusMessage,secondStatusMessage,thirdStatusMessage,firstStatusMessageXpath,secondStatusMessageXpath,thirdStatusMessageXpath

  *Description : This method will be used to set actual Status.

  */
 public void returnErrorLog(testRunner, log, def expectedStatus, def firstStatusMessage, def secondStatusMessage, def thirdStatusMessage, def firstStatusMessageXpath, def secondStatusMessageXpath, def thirdStatusMessageXpath) {

  switch (expectedStatus) {
   case firstStatusMessage:
    testRunner.testCase.setPropertyValue("ActualResult", firstStatusMessageXpath)
    log.info("ActualResult " + " : " + firstStatusMessageXpath)
    break;
   case secondStatusMessage:
    testRunner.testCase.setPropertyValue("ActualResult", secondStatusMessageXpath)
    log.info("ActualResult " + " : " + secondStatusMessageXpath)
    break;
   case thirdStatusMessage:
    testRunner.testCase.setPropertyValue("ActualResult", thirdStatusMessageXpath)
    log.info("ActualResult " + " : " + thirdStatusMessageXpath)
  }
 }

================================Finished==========================
 /*
*Created By  :Rakesh Anna

  *Created Date:02-03-2017

  *Parameters  :testRunner,log,expectedStatus,firstStatusMessage,secondStatusMessage,firstStatusMessageXpath,secondStatusMessageXpath

  *Description :  This method will set error log in test cases property based on expected status.

  */
 public void returnErrorLog(testRunner, log, def expectedStatus, def firstStatusMessage, def secondStatusMessage, def firstStatusMessageXpath, def secondStatusMessageXpath) {

  switch (expectedStatus) {
   case firstStatusMessage:
    testRunner.testCase.setPropertyValue("ActualResult", firstStatusMessageXpath)
    log.info("ActualResult " + " : " + firstStatusMessageXpath)
    break;
   case secondStatusMessage:
    testRunner.testCase.setPropertyValue("ActualResult", secondStatusMessageXpath)
    log.info("ActualResult " + " : " + secondStatusMessageXpath)
  }

 }

 /*
  *Created By  :Akshit Dwivedi
  *Created Date:17-4-2017
  *Parameters  :testRunner,log,context,requestContent,requestStepName
  *Description : This method will be used for removing the tags with empty values from the XML-RPC request and setting the new request body to the requestStepName parameter
  */
 public void removeEmptyContentFromRestRequest(testRunner, log, context, def requestContent, def requestStepName) {
  //parse the xml into pretty-printed
  Node xmlNodes = new XmlParser().parseText(requestContent)
  def requestBody = new StringWriter()
  def xmlNodePrinter = new XmlNodePrinter(new PrintWriter(requestBody))
  xmlNodePrinter.with {
   preserveWhitespace = true
   expandEmptyElements = true
   quote = "'" // Use single quote for attributes
  }
  xmlNodePrinter.print(xmlNodes)
  log.info(requestBody)

  def groovyUtils = new com.eviware.soapui.support.GroovyUtils(context)
  def holder = groovyUtils.getXmlHolder(requestBody.toString())

  // find and remove the nodes that has empty content
  for (item in holder.getDomNodes("//*[normalize-space(.) = '' and count(*) = 0]")) {
   item.removeXobj()
  }
  //set the property
  testRunner.testCase.testSteps[requestStepName].setPropertyValue("Request", holder.xml)
 }

 /*=========================Finished==============================================

  *Created By  :Ujjawal Kumar

  *Created Date:17-4-2017

  *Parameters  :HexadecimalValue

  *Description : This method will be used for checking the Hexadecimal value

  */
 public def CheckIsHex(log, def HexadecimalValue) {
  def status;
  for (int i = 1; i < HexadecimalValue.length(); i++) {
   if (HexadecimalValue.charAt(i) == '-') {
    status = false;
   } else if (Character.digit(HexadecimalValue.charAt(i), 16) == -1) {
    status = false;
   }
  }
  status = true;
  log.info("Status : " + status)
  return status;

 }
 
 /*
  *Created By  : Akshit Dwivadi
  *Created Date: 16-05-2017
  *Parameters  : testRunner,log,context,requestContent
  *Description : This method will be used for removing the tags with empty values from the request and setting the new request content to test case property
  */
	public void removeEmptyContentFromRequest(testRunner, log, context, def requestContent) {
	 //parse the xml into pretty-printed
	 Node xmlNodes = new XmlParser().parseText(requestContent)
	 def requestBody = new StringWriter()
	 def xmlNodePrinter = new XmlNodePrinter(new PrintWriter(requestBody))
	 xmlNodePrinter.with {
	  preserveWhitespace = true
	  expandEmptyElements = true
	  quote = "'" // Use single quote for attributes
	 }
	 xmlNodePrinter.print(xmlNodes)
	 log.info(requestBody)

	 def groovyUtils = new com.eviware.soapui.support.GroovyUtils(context)
	 def holder = groovyUtils.getXmlHolder(requestBody.toString())

	 // find and remove the nodes that has empty content
	 for (item in holder.getDomNodes("//*[normalize-space(.) = '' and count(*) = 0]")) {
	  item.removeXobj()
	 }
	 //set the property
	 testRunner.testCase.setPropertyValue("UpdatedRequestContent", holder.xml)
	}

	/*
======================================Finished====================================
*Created By  :Rakesh Anna
*Created Date:02-03-2017
*Parameters  :Intvalue
*Description : This method will be used to convert binary to boolean
*/
 
 public def intToBoolean(log, testRunner,def Intvalue, def nameOfProperty) 
{
def BooleanValue = (Intvalue.equals("1"))?"true":"false"
log.info ("BooleanValue "+BooleanValue)
testRunner.testCase.setPropertyValue(nameOfProperty, BooleanValue)
}
 /*
   *Created By  :Prasanth Dayalan
   *Created Date:24-02-2017
   *Parameters  : testRunner,context,log,testCase,testStep,statusXpath,expectedStatus
   *Description : This method will be used pool a test step for a minute to get the expected status
   */
 
 public def poolForTaskCompletion(testRunner, context, log, String testCase, String testStep, String statusXPath, String expectedStatus)
 {
 def testCaseName = testCase
 def testStepName = testStep
 def StatusXPath = statusXPath
 def status = expectedStatus
 int i =1
  def ResponseContent = context.expand(StatusXPath)
  log.info ("ResponseContent "+ResponseContent)
 def responseTestStep = testRunner.testCase.testSuite.testCases[testCaseName].testSteps[testStepName]
 while(ResponseContent !=status&& i<=30)
{
sleep(2000)

responseTestStep.run(testRunner, context)
ResponseContent = context.expand(StatusXPath)
i++
}
log.info ("ResponseContent "+ResponseContent)
 }
============================Finished====================
/*
 *Created By  : Prasanth Dayalan
 *Created Date: 08-07-2017
 *Parameters  : dsdepath,serverIp,userCreationXMLFile,DBLoginUserName,DBLoginPassword
 *Description : This method will be used to create a user specified in the user's configuration XML file
 */

public def userCreation(def dsdepath, def serverip, def userCreationXMLFile, def DBLoginUserName, def DBLoginPassword) {
 def command = "Powershell.exe " + dsdepath + " /mode import /encrypt /server " + serverip + " /port 3890 /input " + userCreationXMLFile + " /replace 8101BA62-D96C-403B-8886-1E8034C02CD7 \$(\$([guid]::NewGuid()).ToString().ToUpper()) /cred " + DBLoginUserName + " " + DBLoginPassword;
 def myList = []
 def line;
 def process = Runtime.getRuntime().exec(command);
 process.getOutputStream().close();
 BufferedReader stdout = new BufferedReader(new InputStreamReader(
  process.getInputStream()));
 while ((line = stdout.readLine()) != null) {
  myList.add(line)
 }

 stdout.close();
 return myList;
}
 ============================Finished====================
  /*
  *Created By  : Prasanth Dayalan
  *Created Date: 02-03-2017
  *Parameters  : testRunner,log,expectedStatus,firstStatusMessageXpath,secondStatusMessageXpath
  *Description : This method will be used to set actual Status.
  */
 public void returnErrorLog(testRunner, log, def expectedStatus, def firstStatusMessageXpath, def secondStatusMessageXpath) {

  switch (expectedStatus) {
   case firstStatusMessageXpath:
    testRunner.testCase.setPropertyValue("ActualResult", firstStatusMessageXpath)
    log.info("ActualResult " + " : " + firstStatusMessageXpath)
    break;
   case secondStatusMessageXpath:
    testRunner.testCase.setPropertyValue("ActualResult", secondStatusMessageXpath)
    log.info("ActualResult " + " : " + secondStatusMessageXpath)
  }

 }
 
 /*
  *Created By  :Prasanth Dayalan
  *Created Date:24-02-2017
  *Parameters  : testRunner,context,log,testCase,testStep,statusXpath,expectedStatus
  *Description : This method will be used poll a test step for a minute to get the expected status
  */

 public def pollForTaskCompletion(testRunner, context, log, String testCaseName, String testStepName, String statusXPath, String expectedStatus) {
  int i = 1
  def ResponseContent = context.expand(statusXPath)
  log.info("ResponseContent " + ResponseContent)
  def responseTestStep = testRunner.testCase.testSuite.testCases[testCaseName].testSteps[testStepName]
  while (ResponseContent != expectedStatus && i <= 30) {
   sleep(2000)

   responseTestStep.run(testRunner, context)
   ResponseContent = context.expand(statusXPath)
   i++
  }
  log.info("statusXPath " + statusXPath)
 }
 
 /*
  *Created By  :Prashanth Pai
  *Created Date:4-11-2017
  *Parameters  : log,context,sqlCmd,placeHolderList,dbConnectionString
  *Description : This method will execute SQL query and return the JDBC response in XML format
*/
public String generateJDBCResponseXML(log,context,sqlCmd,placeHolderList,fetchSize,dbConnectionString) {
	Connection dbConnection = null;
	Statement dbStatement = null;
	ResultSet dbResultSet = null;
	ResultSetMetaData dbResultSetMetaData = null;
	DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();     
	DocumentBuilder builder = factory.newDocumentBuilder();     
	Document doc = builder.newDocument();     
	Element results = doc.createElement("Results");     
	doc.appendChild(results);     
	Element resultSet = doc.createElement("ResultSet");     
	resultSet.setAttribute("fetchSize",fetchSize ?: '128')     
	results.appendChild(resultSet);  
	dbConnection = context[dbConnectionString]; 		
	dbStatement = dbConnection.prepareStatement(sqlCmd);   
	if (placeHolderList.size()!=0) {
	for (int i=0;i<placeHolderList.size();i++) {
		dbStatement.setString(i+1,placeHolderList[i]);
		}
	}		
	dbResultSet = dbStatement.executeQuery();
	dbResultSetMetaData = dbResultSet.getMetaData();  
	int colCount = dbResultSetMetaData.getColumnCount();     
	int rowNumber=1; 	     
	while (dbResultSet.next()) {       
		Element rowElem = doc.createElement("Row");       
		rowElem.setAttribute("rowNumber","$rowNumber")       
		resultSet.appendChild(rowElem);       
		rowNumber++;       
		for (int i = 1; i <= colCount; i++) {         
			String columnName = dbResultSetMetaData.getColumnName(i); 
			String columnTypeName = dbResultSetMetaData.getColumnTypeName(i);
			Object value=null;
			switch(columnTypeName){
				case 'uniqueidentifier': 
				case 'varbinary':  value=dbResultSet.getString(i);  break;
				case 'varchar':  value=dbResultSet.getString(i);  break;
				case 'nvarchar':  value=dbResultSet.getString(i);  break;
				case 'timestamp':
				case 'datetime': value=dbResultSet.getString(i);  break;
				case 'tinyint': 
				case 'smallint': 
				case 'int': value=dbResultSet.getInt(i);  break;
				case 'integer': value=dbResultSet.getInt(i);  break;
				case 'bit': value=dbResultSet.getInt(i);  break;
         			default: value=dbResultSet.getObject(i);       
			}
			Element node = doc.createElement(columnName);  
			if (value!=null) {      
				node.appendChild(doc.createTextNode(value.toString()));
			}        
			rowElem.appendChild(node);       
		 }     
	 }     
	 DOMSource domSource = new DOMSource(doc);     
	 TransformerFactory tf = TransformerFactory.newInstance();     
	 Transformer transformer = tf.newTransformer();     
	 transformer.setOutputProperty(OutputKeys.OMIT_XML_DECLARATION, "yes");     
	 transformer.setOutputProperty(OutputKeys.METHOD, "xml");     
	 transformer.setOutputProperty(OutputKeys.ENCODING, "UTF-8");//ISO-8859-1     
	 transformer.setOutputProperty(OutputKeys.INDENT, "yes");     
	 transformer.setOutputProperty("{http://xml.apache.org/xslt}indent-amount", "4");     
	 StringWriter sw = new StringWriter();     
	 StreamResult sr = new StreamResult(sw);     
	 transformer.transform(domSource, sr);     
	 log.info(" JDBC response as XML: " + sw.toString())
	 dbResultSet.close();  		 
	 return sw.toString();     
 }

/*
  *Created By  :Prashanth Pai
  *Created Date:4-12-2017
  *Parameters  : log,context,sqlCmd,placeHolderList
  *Description : This method will execute DELETE SQL command and return the number of deleted rows.
*/
public String deleteDBData(log, context, sqlCmd, placeHolderList, dbConnectionString) {
 Connection dbConnection = null;
 Statement dbStatement = null;
 DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
 DocumentBuilder builder = factory.newDocumentBuilder();
 Document doc = builder.newDocument();
 Element results = doc.createElement("Results");
 doc.appendChild(results);
 dbConnection = context[dbConnectionString];
 dbStatement = dbConnection.prepareStatement(sqlCmd);
 Object value = null;
 if (placeHolderList.size() != 0) {
  for (int i = 0; i < placeHolderList.size(); i++) {
   dbStatement.setString(i + 1, placeHolderList[i]);
  }
 }
 try {
  value = dbStatement.executeUpdate();
 } catch (SQLException e) {
  log.error("SQL Error in deleting of data")
  e.printStackTrace();
  value = null;
 } catch (Exception e) {
  log.error("Error in deleting of data")
  e.printStackTrace();
  value = null;
 }
 Element node = doc.createElement("DeleteCount");
 if (value != null) {
  node.appendChild(doc.createTextNode(value.toString()));
 }
 results.appendChild(node);

 log.info("Rows Deleted " + value.toString())
 DOMSource domSource = new DOMSource(doc);
 TransformerFactory tf = TransformerFactory.newInstance();
 Transformer transformer = tf.newTransformer();
 transformer.setOutputProperty(OutputKeys.OMIT_XML_DECLARATION, "yes");
 transformer.setOutputProperty(OutputKeys.METHOD, "xml");
 transformer.setOutputProperty(OutputKeys.ENCODING, "UTF-8"); //ISO-8859-1     
 transformer.setOutputProperty(OutputKeys.INDENT, "yes");
 transformer.setOutputProperty("{http://xml.apache.org/xslt}indent-amount", "4");
 StringWriter sw = new StringWriter();
 StreamResult sr = new StreamResult(sw);
 transformer.transform(domSource, sr);
 log.info(" JDBC response as XML: " + sw.toString())
 return sw.toString();
}
============================Confermation============================
/*
  *Created By  : Reshma Cutinho
  *Created Date: 10-08-2017
  *Parameters  : timeStamp,offsetValue,isDaylightSavings
  *Description : This method will convert timestamp to UTC format
  *Version : 1.0
  *Last Modified : 10-08-2017
  */
 public def changeTime(String timeStamp,String offsetValue,String isDaylightSavings) {
  SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
  Date date = formatter.parse(timeStamp);
  String[] offSet=offsetValue.split(":")
  def incrementTime = null;
  int timeDifferenceInHour=offSet[0] as Integer
  int timeDifferenceInMinute=offSet[1] as Integer
  timeDifferenceInMinute=(offSet[0].contains("-"))? -timeDifferenceInMinute:timeDifferenceInMinute
  //if(isDaylightSavings.equals("True")){
	//	timeDifferenceInHour=(timeDifferenceInHour)+1
  //}
  use(TimeCategory) {
   incrementTime = date + timeDifferenceInHour.hour +timeDifferenceInMinute.minutes
  }
  return incrementTime.format("yyyy-MM-dd HH:mm:ss") 
 }
=======================================Finished=================================== 
 /*
  *Created By  : Prabhu Nagaraj
  *Created Date: 23-08-2017
  *Parameters  : timeStamp,offsetValue,isDaylightSavings
  *Description : This method used to add or subtract offset value from the server time
  *Version : 1.0
  *Last Modified : 23-08-2017
  */
 public def changeTime1(String timeStamp,String offsetValue,String isDaylightSavings) {
  if (isDaylightSavings == 'True') {
 def serverOffsetValueUpdated = offsetValue.split("\\:")
 
 def serverOffsetValueFinal = serverOffsetValueUpdated[0].toInteger() + 1
 
 offsetValue = serverOffsetValueFinal + ":" + serverOffsetValueUpdated[1]
 
}
def serverOffsetValueUpdatedSign
if (offsetValue.toString().contains("-")) {
 serverOffsetValueUpdatedSign = offsetValue.toString().replace("-", "+")
} else {
 serverOffsetValueUpdatedSign = offsetValue.toString().replace("+", "-")
}

String[] offSet = serverOffsetValueUpdatedSign.split(":")
def incrementTime = null;
int timeDifferenceInHour = offSet[0] as Integer
int timeDifferenceInMinute = offSet[1] as Integer
timeDifferenceInMinute = (offSet[0].contains("-")) ? -timeDifferenceInMinute : timeDifferenceInMinute
use(TimeCategory) {
 SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
 Date date = formatter.parse(timeStamp);
 incrementTime = date + timeDifferenceInHour.hour + timeDifferenceInMinute.minutes
 }
  return incrementTime
 }
 
 /*
  *Created By  : Prashanth Pai
  *Created Date: 6-29-2017
  *Parameters  : log,context,sqlCmd,placeHolderList
  *Description : This method will initialize and setup the JDBC connection.
*/
public def init_DB(log,context,String driver, String user, String password, String nodeToInit,String connectionName, String dbTableName,String dbDriver,String dbPort,String integratedSecurity) {
	
	def url = dbDriver+nodeToInit+":"+dbPort+";databaseName="+dbTableName+";user="+user+";password="+password+";integratedSecurity="+integratedSecurity+";pool=True";
	//try to create connection to database, if available. load this connection on context if not, log error 
	//log.info("url  :  " +url)
	if ( (null != url) && (null != driver) && (null != user) && (null != password) ) 
	{
	 try {
		Connection connection = DriverManager.getConnection(url);
		switch (connectionName) {
			case 'primaryDB' : context.setProperty("dbConnectionString", connection); break;
			case 'masterDB_IFNode' : context.setProperty("dbConnectionString_IFNode", connection); break;
			case 'slaveDB_ARNode' : context.setProperty("dbConnectionString_ARNode", connection); break;
			case 'slaveDB_INNode' : context.setProperty("dbConnectionString_INNode", connection); break;
			case 'slaveDB_CANode' : context.setProperty("dbConnectionString_CANode", connection); break;
			case 'slaveDB_PRNode' : context.setProperty("dbConnectionString_PRNode", connection); break;
			case 'slaveDB_ASNode' : context.setProperty("dbConnectionString_ASNode", connection); break;
			case 'slaveDB_DBNode' : context.setProperty("dbConnectionString_RuleEngine", connection); break;
			default:log.error "Could not establish connection to the Database."
		}
		log.info("Successfully established connection to the database.")+connectionName; //	+connection.toString()
	  } catch (Exception e) {
		log.error "Could not establish connection to the database." + connectionName
		log.error (e.getMessage())
	  }
	}
 }
 
 /*
  *Created By  : Prashanth Pai
  *Created Date: 7-12-2017
  *Parameters  : context,log
  *Description : This method will setup the JDBC connections.
*/  

public  setupDBConnections(context,log) {
	def driver = context.expand( '${#Project#SQLDriver}' )
	def user = context.expand( '${#Project#DBUserName}' )
	def password = context.expand( '${#Project#DBPassword}' )
	def nodeToInit = context.expand( '${#Project#DBNode}')
	def dbTableName = context.expand( '${#Project#DBInstanceName}')
	def dbDriver = context.expand( '${#Project#DBDriver}')
	def dbPort = context.expand( '${#Project#DBPort}')
	def integratedSecurity = context.expand( '${#Project#IntegratedSecurity}')
	//log.info("driver" +driver)
	//log.info("user" +user)
	//log.info("password" +password)
	def startDateTime = new Date().format("yyyy-MM-dd HH:mm:ss")
	log.info "Start Date Time is " + startDateTime

	// register JDBC driver
	com.eviware.soapui.support.GroovyUtils.registerJdbcDriver(driver)
	Class.forName(driver);	
	
	// Intialize the primary DB 
	def connectionName = "primaryDB";
	init_DB(log,context,driver, user, password, nodeToInit,connectionName,dbTableName,dbDriver,dbPort,integratedSecurity)
	
	// Intialize iVault DB nodes 
	def isIvault = context.expand( '${#Project#IsIvault}' )
	def IsIFNodeInit = context.expand( '${#Project#IsIFNodeInit}')
	def IsARNodeInit = context.expand( '${#Project#IsARNodeInit}')
	def IsDBNodeInit = context.expand( '${#Project#IsDBNodeInit}')
	def IsPRNodeInit = context.expand( '${#Project#IsPRNodeInit}')
	def IsINNodeInit = context.expand( '${#Project#IsINNodeInit}')
	def IsCANodeInit = context.expand( '${#Project#IsCANodeInit}')
	def IsASNodeInit = context.expand( '${#Project#IsASNodeInit}')

	if (isIvault.equalsIgnoreCase("True")) {
		if (IsIFNodeInit.equalsIgnoreCase("True")) {
			connectionName = "masterDB_IFNode";
			nodeToInit = context.expand( '${#Project#IFNode}')
			dbTableName = context.expand( '${#Project#SlaveDBInstanceName}')
			init_DB(log,context,driver, user, password, nodeToInit,connectionName,dbTableName,dbDriver,dbPort,integratedSecurity)
		}
		if (IsARNodeInit.equalsIgnoreCase("True")) {
			connectionName = "slaveDB_ARNode";
			nodeToInit = context.expand( '${#Project#ARNode}')
			dbTableName = context.expand( '${#Project#SlaveDBInstanceName}')
			init_DB(log,context,driver, user, password, nodeToInit,connectionName,dbTableName,dbDriver,dbPort,integratedSecurity)
		}
		if (IsDBNodeInit.equalsIgnoreCase("True")) {
			connectionName = "slaveDB_DBNode";
			nodeToInit = context.expand( '${#Project#DBNode}')
			dbTableName = context.expand( '${#Project#DBInstanceName_RuleEngine}')
			init_DB(log,context,driver, user, password, nodeToInit,connectionName,dbTableName,dbDriver,dbPort,integratedSecurity)
		}
		if (IsPRNodeInit.equalsIgnoreCase("True")) {
			connectionName = "slaveDB_PRNode";
			nodeToInit = context.expand( '${#Project#PRNode}')
			dbTableName = context.expand( '${#Project#SlaveDBInstanceName}')
			init_DB(log,context,driver, user, password, nodeToInit,connectionName,dbTableName,dbDriver,dbPort,integratedSecurity)
		}
		if (IsINNodeInit.equalsIgnoreCase("True")) {
			connectionName = "slaveDB_INNode";
			nodeToInit = context.expand( '${#Project#INNode}')
			dbTableName = context.expand( '${#Project#SlaveDBInstanceName}')
			init_DB(log,context,driver, user, password, nodeToInit,connectionName,dbTableName,dbDriver,dbPort,integratedSecurity)
		}
		if (IsCANodeInit.equalsIgnoreCase("True")) {
			connectionName = "slaveDB_CANode";
			nodeToInit = context.expand( '${#Project#CANode}')
			dbTableName = context.expand( '${#Project#SlaveDBInstanceName}')
			init_DB(log,context,driver, user, password, nodeToInit,connectionName,dbTableName,dbDriver,dbPort,integratedSecurity)
		}
		if (IsASNodeInit.equalsIgnoreCase("True")) {
			connectionName = "slaveDB_ASNode";
			nodeToInit = context.expand( '${#Project#ASNode}')
			dbTableName = context.expand( '${#Project#SlaveDBInstanceName}')
			init_DB(log,context,driver, user, password, nodeToInit,connectionName,dbTableName,dbDriver,dbPort,integratedSecurity)
		}
	}

	def endDateTime = new Date().format("yyyy-MM-dd HH:mm:ss")
	log.info "End Date Time is " + endDateTime
 }
==========================================================Finished=======================================================================
/*
  *Created By  : Prashanth Pai
  *Created Date: 07-24-2017
  *Parameters  : runner,log,key,serverIP,resource
  *Description : This method is used to set EndPoint
  *Version : 1.0
  *Last Modified : 
  */
  public void setEndpoint(runner,context,log,def key,def serverIP, def resource, def location) {
  def endpoint = "https://"  + serverIP + resource
  if(location.equals("Project")){
 runner.testSuite.project.setPropertyValue(key,endpoint);
 log.info( "Setting project property ["+key+"] to ["+endpoint+"]")
  }
  else if (location.equals("TestSuite")){
  runner.testSuite.setPropertyValue(key,endpoint);
  log.info( "Setting testsuite property ["+key+"] to ["+endpoint+"]")
  }
  else if (location.equals("CommonTestSuite")){
  runner.testSuite.project.getTestSuiteByName(location).setPropertyValue(key,endpoint);
  log.info( "Setting common testsuite property ["+key+"] to ["+endpoint+"]")
  }
 }
=================================Finished===========================
 /*
  *Created By  : Prashanth Pai
  *Created Date: 07-24-2017
  *Parameters  : runner,log,key,serverIP,resource
  *Description : This method is used to set EndPoint
  *Version : 1.0
  *Last Modified : 
  */
  public void changeDatabaseConnection(runner,log,String key,String serverIP, String resource) {
  String endpoint = "https://"  + serverIP + resource
  runner.testSuite.setPropertyValue(key,endpoint);
  log.info( "Setting project property ["+key+"] to ["+endpoint+"]")
 }
 
  /*
  *Created By  :Lokesh Kalal
  *Created Date:9-07-2017
  *Parameters  : log,context,sqlCmd,placeHolderList
  *Description : This method will execute INSERT SQL command and return the number of Updated rows.
*/
public String insertDBData(log, context, sqlCmd, placeHolderList, dbConnectionString) {
 Connection dbConnection = null;
 Statement dbStatement = null;
 DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
 DocumentBuilder builder = factory.newDocumentBuilder();
 Document doc = builder.newDocument();
 Element results = doc.createElement("Results");
 doc.appendChild(results);
 dbConnection = context[dbConnectionString];
 dbStatement = dbConnection.prepareStatement(sqlCmd);
 Object value = null;
 if (placeHolderList.size() != 0) {
  for (int i = 0; i < placeHolderList.size(); i++) {
   if (placeHolderList[i].toString() != 'null') {
    dbStatement.setString(i+1, placeHolderList[i]);  
   } 
  else {
   dbStatement.setNull(i+1,java.sql.Types.NULL);
  }
  }
 }
 try {
  dbStatement.addBatch();
  value = dbStatement.executeUpdate();
  log.info "value: "+value
 } catch (SQLException e) {
  log.error("SQL Error in inserting of data")
  e.printStackTrace();
  value = null;
 } catch (Exception e) {
  log.error("Error in inserting of data")
  e.printStackTrace();
  value = null;
 }
 Element node = doc.createElement("InsertCount");
 if (value != null) {
  node.appendChild(doc.createTextNode(value.toString()));
 }
 results.appendChild(node);

 log.info("Rows inserted " + value.toString())
 DOMSource domSource = new DOMSource(doc);
 TransformerFactory tf = TransformerFactory.newInstance();
 Transformer transformer = tf.newTransformer();
 transformer.setOutputProperty(OutputKeys.OMIT_XML_DECLARATION, "yes");
 transformer.setOutputProperty(OutputKeys.METHOD, "xml");
 transformer.setOutputProperty(OutputKeys.ENCODING, "UTF-8"); //ISO-8859-1     
 transformer.setOutputProperty(OutputKeys.INDENT, "yes");
 transformer.setOutputProperty("{http://xml.apache.org/xslt}indent-amount", "4");
 StringWriter sw = new StringWriter();
 StreamResult sr = new StreamResult(sw);
 transformer.transform(domSource, sr);
 log.info(" JDBC response as XML: " + sw.toString())
 return sw.toString();
}
 
 /*
  *Created By  :Lokesh Kalal
  *Created Date:9-07-2017
  *Parameters  : log,context,sqlCmd,placeHolderList
  *Description : This method will execute UPDATE SQL command and return the number of Updated rows.
*/
public String updateDBData(log, context, sqlCmd, placeHolderList, dbConnectionString) {
 Connection dbConnection = null;
 Statement dbStatement = null;
 DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
 DocumentBuilder builder = factory.newDocumentBuilder();
 Document doc = builder.newDocument();
 Element results = doc.createElement("Results");
 doc.appendChild(results);
 dbConnection = context[dbConnectionString];
 dbStatement = dbConnection.prepareStatement(sqlCmd);
 log.info("dbStatement " +dbStatement)
 Object value = null;
 if (placeHolderList.size() != 0) {
  for (int i = 0; i < placeHolderList.size(); i++) {
   dbStatement.setString(i + 1, placeHolderList[i]);
  }
 }
 try {
  value = dbStatement.executeUpdate();
 } catch (SQLException e) {
  log.error("SQL Error in updating of data")
  e.printStackTrace();
  value = null;
 } catch (Exception e) {
  log.error("Error in updating of data")
  e.printStackTrace();
  value = null;
 }
 Element node = doc.createElement("UpdateCount");
 if (value != null) {
  node.appendChild(doc.createTextNode(value.toString()));
 }
 results.appendChild(node);

 log.info("Rows Updated " + value.toString())
 DOMSource domSource = new DOMSource(doc);
 TransformerFactory tf = TransformerFactory.newInstance();
 Transformer transformer = tf.newTransformer();
 transformer.setOutputProperty(OutputKeys.OMIT_XML_DECLARATION, "yes");
 transformer.setOutputProperty(OutputKeys.METHOD, "xml");
 transformer.setOutputProperty(OutputKeys.ENCODING, "UTF-8"); //ISO-8859-1     
 transformer.setOutputProperty(OutputKeys.INDENT, "yes");
 transformer.setOutputProperty("{http://xml.apache.org/xslt}indent-amount", "4");
 StringWriter sw = new StringWriter();
 StreamResult sr = new StreamResult(sw);
 transformer.transform(domSource, sr);
 log.info(" JDBC response as XML: " + sw.toString())
 return sw.toString();
}
 ============================Finished====================
/*
   *Created By  : Prasanth Dayalan
   *Created Date: 16-09-2017
   *Parameters  : Path, ServerName, UserName, Password, serviceDisplayName and action
   *Description : This method will be used to runPowerShellScript
   */
 public def runPowerShellScript(def Path, def ServerName, def UserName, def Password, def ServiceDisplayName,def Action) {
  def serverName = ServerName
  def userName = UserName
  def password = Password
  def path = Path
  def serviceDisplayName = ServiceDisplayName
  def action = Action
  def command = "Powershell.exe -file ${path} ${serverName} ${userName} ${password} ${serviceDisplayName} ${action}";
  def myList = []
  def line;
  def process = Runtime.getRuntime().exec(command);
  process.getOutputStream().close();
  BufferedReader stdout = new BufferedReader(new InputStreamReader(
   process.getInputStream()));
  while ((line = stdout.readLine()) != null) {
   myList.add(line)
  }

  stdout.close();
  return myList;
 }
====================================Finish==============================
 /*
*Created By  :Ranjitha J

  *Created Date:02-03-2017

  *Parameters  :Intvalue

  *Description : This method will be used to convert boolean to binary

  */
 public def booleanToBinary(def Intvalue) 
  {
	def BooleanValue = (Intvalue == "true")?"1":"0"
	return BooleanValue
  }
  
  /*
  *Created By  :Jijo
  *Created Date:7-12-2017
  *Parameters  : log,context,sqlCmd,placeHolderList,dbConnectionString
  *Description : This method will execute INSERT SQL command and return the number of deleted rows.
*/
   
   public boolean InsertDBdata(log,context,sqlCmd,placeHolderList,dbConnectionString) {
	Connection dbConnection = null;
	Statement dbStatement = null;
	dbConnection = context[dbConnectionString]; 		
	dbStatement = dbConnection.prepareStatement(sqlCmd);   
	int rowsInserted = 0;
	if (placeHolderList.size()!=0) {
	for (int i=0;i<placeHolderList.size();i++) {
		dbStatement.setString(i+1,placeHolderList[i]);
		}
	}	
	try{	
		rowsInserted = dbStatement.executeUpdate(); 
		if (rowsInserted < 0) {
			rowsInserted=0;
		} 
	} catch (SQLException e){
		log.error("SQL Error in Insertion of data")
		e.printStackTrace();
		rowsInserted=0;
	} catch (Exception e) {
		log.error("Error in insertion of data")
		e.printStackTrace();
		rowsInserted=0;
	}
	 log.info(" Number of rows Inserted: " + rowsInserted)
	 return rowsInserted;     
   }
   

    /*
  *Created By  :Jijo
  *Created Date:7-12-2017
  *Parameters  : log,context,sqlCmd,placeHolderList,dbConnectionString
  *Description : This method will execute Select SQL command and return the number of deleted rows.
*/
   
   public boolean SelectDBdata(log,context,sqlCmd,placeHolderList,dbConnectionString) {
	Connection dbConnection = null;
	Statement dbStatement = null;
	dbConnection = context[dbConnectionString]; 		
	dbStatement = dbConnection.prepareStatement(sqlCmd);   
	int rowsSelected = 0;
	if (placeHolderList.size()!=0) {
	for (int i=0;i<placeHolderList.size();i++) {
		dbStatement.setString(i+1,placeHolderList[i]);
		}
	}	
	try{	
		rowsSelected = dbStatement.execute(); 
		if (rowsSelected < 0) {
			rowsSelected=0;
		} 

	} catch (SQLException e){
		log.error("SQL Error in Selection of data")
		e.printStackTrace();
		rowsSelected=0;
	} catch (Exception e) {
		log.error("Error in Selection of data")
		e.printStackTrace();
		rowsSelected=0;
	}
	 log.info(" Number of rows Selected: " + rowsSelected)
	 return rowsSelected;     
   }
  =======================================Finish============================================
  /*Created By  :Santosh

  *Created Date:02-10-2017

  *Parameters  :command

  *Description : This method will be used execute a command in command prompt

  */

public def runExecutable(String command) {
	def myList = [];
	def line;
	def process = Runtime.getRuntime().exec(command);
	process.getOutputStream().close();
	BufferedReader stdout = new BufferedReader(new InputStreamReader(process.getInputStream()));
	while ((line = stdout.readLine()) != null) {
		myList.add(line)
		}
	stdout.close();
	return myList;
 } 
 ========================================================Finished=============================z
 /*
   *Created By  : Prasanth Dayalan
   *Created Date: 09-10-2017
   *Parameters  : testRunner, log, itemValue , disableMessageIntegrityCheckTag and enableMessageIntegrityCheckTag
   *Description : This method is to modify the item value
   */

public def modifyMessageIntegrityCheckTag(testRunner, log, def itemValue, def configuration, def disableMessageIntegrityCheckTag, def enableMessageIntegrityCheckTag) {

log.info ("itemValue "+itemValue)
log.info ("configuration "+configuration)
log.info ("disableMessageIntegrityCheckTag "+disableMessageIntegrityCheckTag)
log.info ("enableMessageIntegrityCheckTag "+enableMessageIntegrityCheckTag)

if (configuration.equals("Disable")) {
    if (itemValue.contains('<EnableMessageIntegrityCheck>1</EnableMessageIntegrityCheck>')) {
     def modifiedItemValue = itemValue.replace('<EnableMessageIntegrityCheck>1</EnableMessageIntegrityCheck>', disableMessageIntegrityCheckTag)
     log.info("modifiedItemValue " + modifiedItemValue)
	 def modifiedItemValueSingleLine = modifiedItemValue.toString().replaceAll('\n', '')
     return modifiedItemValueSingleLine.replaceAll("\\s","")
    } else {
     return itemValue.replaceAll("\\s","")
    }
   } else {
   
    if (itemValue.contains('<EnableMessageIntegrityCheck>0</EnableMessageIntegrityCheck>')) {
	 def modifiedItemValue = itemValue.replace('<EnableMessageIntegrityCheck>0</EnableMessageIntegrityCheck>', enableMessageIntegrityCheckTag)
     log.info("modifiedItemValue " + modifiedItemValue)
     def modifiedItemValueSingleLine = modifiedItemValue.toString().replaceAll('\n', '')
     return modifiedItemValueSingleLine.replaceAll("\\s","")
    } else {
     return itemValue.replaceAll("\\s","")
    }
   }
 }
 ======================Finished==================================================
 /*
  *Created By  : Prasanth Dayalan
  *Created Date: 09-10-2017 
  *Parameters  : testRunner, configuration, itemValue ,expectedConfigurationItemDisabled and expectedConfigurationItemEnabled
  *Description : This method will be used to set the verification for store
  */

 public void storeVerfication(testRunner, log ,def configuration, def itemValue, def expectedConfigurationItemDisabled, def expectedConfigurationItemEnabled) {
  log.info ("configuration "+configuration)
  log.info ("itemValue "+itemValue)
  log.info ("expectedConfigurationItemDisabled "+expectedConfigurationItemDisabled)
  log.info ("expectedConfigurationItemEnabled "+expectedConfigurationItemEnabled)
  
  if (configuration.equals("Disable")) {
   if (itemValue.contains(expectedConfigurationItemDisabled)) {
    testRunner.testCase.setPropertyValue("enableMessageIntegrityCheckValueValidation", "true")
   }
  } else {
   if (itemValue.contains(expectedConfigurationItemEnabled)) {
    testRunner.testCase.setPropertyValue("enableMessageIntegrityCheckValueValidation", "true")
   }
  }
 }
 =======================================Finish============================================
  /*
   *Created By  : Prasanth Dayalan
   *Created Date: 09-10-2017 
   *Parameters  : testRunner, configuration, propertyName, enabledXPath and disabledXPath
   *Description : This method will be used to set expected XPath 
   */

def void settingExpectedXPath(testRunner, log, def configuration,def propertyName, def enabledXPath,def disabledXPath)
{
 log.info ("configuration "+configuration)
 log.info ("propertyName "+propertyName)
 log.info ("enabledXPath "+enabledXPath)
 log.info ("disabledXPath "+disabledXPath)
 
 switch(configuration)
 {
  case 'Enable':
   testRunner.testCase.setPropertyValue(propertyName, enabledXPath)
   break;
   
  case 'Disable':
   testRunner.testCase.setPropertyValue(propertyName, disabledXPath)
   break;
 }
 }
 
  /*
   *Created By  : Prasanth Dayalan
   *Created Date: 09-10-2017
   *Parameters  : testRunner, log, responseValue , requestName , expectedStatusCodePropertyName and actualStatusCodePropertyName
   *Description : This method is to set actual and expected status code.
   */

public void settingExpectedAndActualStatusCode(testRunner, context, log, def responseValue, def requestName, def expectedStatusCodePropertyName, def actualStatusCodePropertyName)
{
	log.info ("responseValue "+responseValue)
	log.info ("requestName "+requestName)
	log.info ("expectedStatusCodePropertyName "+expectedStatusCodePropertyName)
	log.info ("actualStatusCodePropertyName "+actualStatusCodePropertyName)
	
	switch(responseValue){
	
		case context.expand( '${#Project#BadRequest}' ):
			testRunner.testCase.setPropertyValue(expectedStatusCodePropertyName,"400")
			break;
		case context.expand( '${#Project#InternalServerErrorResponse}' ):
			testRunner.testCase.setPropertyValue(expectedStatusCodePropertyName,"500")
			break;
		case context.expand( '${#Project#DeserializationError}' ):
			testRunner.testCase.setPropertyValue(expectedStatusCodePropertyName,"500")
			break;
		default: 
			testRunner.testCase.setPropertyValue(expectedStatusCodePropertyName,"200")
			break;
	
	}
	
	def httpResponseHeader = context.testCase.testSteps[requestName].testRequest.response.responseHeaders[context.expand( '${#TestSuite#HeaderStatus}' )]
	def httpStatusCodeActual = ( httpResponseHeader =~ "[1-5]\\d\\d")[0]
	log.info ("httpStatusCodeActual"+httpStatusCodeActual)
	testRunner.testCase.setPropertyValue(actualStatusCodePropertyName,httpStatusCodeActual)
}
===============================================Finished===================================
/*
*Created By  :ujjawal
*Created Date:27-02-2017
*Parameters  : HexadecimalVal
*Description : This method will be used to check if the input value is Hexadeciaml or not
*/
 public def checkIsHex(String HexadecimalVal)
 {
   if ( HexadecimalVal.length() == 0 || 
         (HexadecimalVal.charAt(0) != '-' && Character.digit(HexadecimalVal.charAt(0), 16) == -1))
        return false;
    if ( HexadecimalVal.length() == 1 && HexadecimalVal.charAt(0) == '-' )
        return false;

    for ( int i = 1 ; i < HexadecimalVal.length() ; i++ )
        if ( Character.digit(HexadecimalVal.charAt(i), 16) == -1 )
            return false;
    return true;
}
/*
   *Created By  :Ujjawal kumar
   *Created Date: 10-2-2017
   *Parameters  : Path,Integer value
   *Description : This method will be used to return the GUID value for the passed Integer value 
   */
 public def IntToGuid(String Path, int data) 
 {  
    def Value= data;
   def path = Path
   def command = "${path} ${Value}";
   def sout = new StringBuilder(), serr = new StringBuilder()
def proc = command.execute()
proc.consumeProcessOutput(sout, serr)
proc.waitForOrKill(1000)

return  "$sout"
 ============================Finished========================== 
  }
  /*
  *Created By  : Prasanth Dayalan
  *Created Date: 12-10-2017
  *Parameters  : log and registerPpsResult
  *Description : This method is used to convert GUID to integer.
 
  */
public def ConvertGUIDToInteger(log, def registerPpsResult)
{
	log.info ("registerPpsResult " + registerPpsResult)
	def integerValueOfPpsId = registerPpsResult.replace("-", "")
	log.info("integerValueOfPpsId " + integerValueOfPpsId)

	def finalValueOfPpsId = Integer.parseInt(integerValueOfPpsId, 16)
	log.info("finalValueOfPpsId " + finalValueOfPpsId)

	def decimalValueOfPpsId = Math.abs(finalValueOfPpsId)
	log.info("decimalValueOfPpsId " + decimalValueOfPpsId)
	
	return decimalValueOfPpsId

}
==============================Finished=======================================
 /*
   *Created By  : Prasanth Dayalan
   *Created Date: 09-10-2017 
   *Parameters  : testRunner, expectedStatus, propertyName, expectedCondition, schemaXpath and faultcodeXpath
   *Description : This method will be used to set expected XPath 
   */

def void settingExpectedXPath(testRunner, log, def expectedStatus,def propertyName, def expectedCondition, def schemaXpath,def faultcodeXpath)
{
 log.info ("expectedStatus "+expectedStatus)
 log.info ("propertyName "+propertyName)
 log.info ("expectedCondition "+expectedCondition)
 log.info ("schemaXpath "+schemaXpath)
 log.info ("faultcodeXpath "+faultcodeXpath)
 
 if(expectedStatus.equals(expectedCondition))
 {
	testRunner.testCase.setPropertyValue(propertyName,schemaXpath)
 }
 else
 {
    testRunner.testCase.setPropertyValue(propertyName,faultcodeXpath)
 }
 }
 ==============================Finished==============================
  /*
   *Created By  : Prasanth Dayalan
   *Created Date: 09-10-2017 
   *Parameters  : binaryArrayList
   *Description : This method used to convert binarybytes to hex
   */
    public Writable convertToHexaDecimal(final byte[] binaryArrayList){

	final Writable printableHex = binaryArrayList.encodeHex()
	return printableHex;
	}
	
  /*
  *Created By  : Prasanth Dayalan
  *Created Date: 05-10-2017
  *Parameters  : testRunner,log,authorizationHeader, additionalHeaderName, additionalHeaderValue, testStepName
  *Description : This method will be used to set the header for the request.
  */
  public void setRequestHeaders(testRunner, log, def authorizationHeader,def additionalHeaderName, def additionalHeaderValue, def testStepName)
  {
  def headers = new StringToStringMap()
  headers.put(authorizationHeader, '${#Project#HmacKey}')
  headers.put(additionalHeaderName, additionalHeaderValue)
  testRunner.testCase.getTestStepByName(testStepName).testRequest.setRequestHeaders(headers)
  log.info ("headers "+headers)
  }
============================Finished=======================================
  /*
  *Created By  : Prashanth Pai
  *Created Date: 07-24-2017
  *Parameters  : runner,log,key,serverIP,resource
  *Description : This method is used to set EndPoint
  *Version : 1.0
  *Last Modified : 
  */
  public void setEndpoint(runner,context,log, def protocol,def key,def serverIP, def resource, def location) {
  def endpoint = protocol+"://"  + serverIP + resource
  if(location.equals("Project")){
 runner.testSuite.project.setPropertyValue(key,endpoint);
 log.info( "Setting project property ["+key+"] to ["+endpoint+"]")
  }
  else if (location.equals("TestSuite")){
  runner.testSuite.setPropertyValue(key,endpoint);
  log.info( "Setting testsuite property ["+key+"] to ["+endpoint+"]")
  }
  else if (location.equals("CommonTestSuite")){
  runner.testSuite.project.getTestSuiteByName(location).setPropertyValue(key,endpoint);
  log.info( "Setting common testsuite property ["+key+"] to ["+endpoint+"]")
  }
 }
 ============================Finished===========================================
 /*
   *Created By  : Prabhu
   *Created Date: 24-01-2017
   *Parameters  : powershellPath,serverIP,adminUser,adminPassword,filePath
   *Description : This method is used to get the process ID from the server task manager.
   */
 public def getFilePath(String powershellPath, String serverIP, String adminUser, String adminPassword, String filePath) {

  def ServerName = serverIP
  def UserName = adminUser
  def Password = adminPassword
  def FilePath = filePath
  def command = "Powershell.exe -file ${powershellPath} ${ServerName} ${UserName} ${Password} ${FilePath}";
  def myList = []
  def line;
  def process = Runtime.getRuntime().exec(command);
  process.getOutputStream().close();
  BufferedReader stdout = new BufferedReader(new InputStreamReader(
   process.getInputStream()));
  while ((line = stdout.readLine()) != null) {
   myList.add(line)
      }

  stdout.close();
  return myList;
 }
 ===============================================Finished====================================
 /*
  *Created By  : Prabhu Nagaraj
  *Created Date: 11-11-2017
  *Parameters  : testRunner,log, NumberOfDaysForBeginDateTime, NumberOfDaysForEndDateTime
  *Description : This method used to format and set the BeginDateTime and EndDateTime.
  *Version : 1.0
  */
 public def setBeginAndEndDateTime(testRunner,log,def NumberOfDaysForBeginDateTime,def NumberOfDaysForEndDateTime) {
 

Date dateTime = new Date()
log.info("dateTime " + dateTime)

SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
Date dateTimeStamp = formatter.parse(dateTime.format("yyyy-MM-dd HH:mm:ss"));
def numberOfDaysForBeginDateTime = NumberOfDaysForBeginDateTime
def numberOfDaysForEndDateTime = NumberOfDaysForEndDateTime
log.info("numberOfDaysForBeginDateTime "+numberOfDaysForBeginDateTime)
log.info("numberOfDaysForEndDateTime "+numberOfDaysForEndDateTime)


use(TimeCategory) {
	//Set BeginDateTime
    def updatedBeginDateTimeRequest = dateTimeStamp + numberOfDaysForBeginDateTime.toInteger()
    log.info ("updatedBeginDateTimeRequest " + updatedBeginDateTimeRequest)
    def beginDateTimeRequest = updatedBeginDateTimeRequest.format("yyyyMMdd HHmmss")
    log.info ("beginDateTimeRequest " + beginDateTimeRequest)
    testRunner.testCase.setPropertyValue("beginDateTimeRequest ", beginDateTimeRequest)
    def beginDateTimeDB = updatedBeginDateTimeRequest.format("yyyy-MM-dd HH:mm:ss")
    log.info ("beginDateTimeDB " + beginDateTimeDB)
    testRunner.testCase.setPropertyValue("beginDateTimeDB ", beginDateTimeDB)
	//Set EndDateTime
    def updatedEndDateTimeRequest = dateTimeStamp + numberOfDaysForEndDateTime.toInteger()
	log.info ("updatedEndDateTimeRequest " + updatedEndDateTimeRequest)
    def endDateTimeRequest = updatedEndDateTimeRequest.format("yyyyMMdd HHmmss")
    log.info ("endDateTimeRequest " + endDateTimeRequest)
    testRunner.testCase.setPropertyValue("endDateTimeRequest ", endDateTimeRequest)
    def endDateTimeDB = updatedEndDateTimeRequest.format("yyyy-MM-dd HH:mm:ss")
    log.info ("endDateTimeDB " + endDateTimeDB)
    testRunner.testCase.setPropertyValue("endDateTimeDB ", endDateTimeDB)
	}
 }
===========================================Finished========================================================================
 /*
  *Created By  : Prabhu Nagaraj
  *Created Date: 11-11-2017
  *Parameters  : testRunner,log, NumberOfDaysForBeginDateTime, NumberOfDaysForEndDateTime, NumberOfDaysForFutureBeginDateTime, NumberOfDaysForFutureEndDateTime
  *Description : This method used to format and set the BeginDateTime, EndDateTime, FutureBeginDateTime and FutureEndDateTime
  *Version : 1.0
  */
 public def setBeginAndEndDateTime(testRunner,log,def NumberOfDaysForBeginDateTime,def NumberOfDaysForEndDateTime,def NumberOfDaysForFutureBeginDateTime,def NumberOfDaysForFutureEndDateTime) {
 

Date dateTime = new Date()
log.info("dateTime " + dateTime)

SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
Date dateTimeStamp = formatter.parse(dateTime.format("yyyy-MM-dd HH:mm:ss"));
def numberOfDaysForBeginDateTime = NumberOfDaysForBeginDateTime
def numberOfDaysForEndDateTime = NumberOfDaysForEndDateTime
def numberOfDaysForFutureBeginDateTime = NumberOfDaysForFutureBeginDateTime
def numberOfDaysForFutureEndDateTime = NumberOfDaysForFutureEndDateTime
log.info("numberOfDaysForBeginDateTime "+numberOfDaysForBeginDateTime)
log.info("numberOfDaysForEndDateTime "+numberOfDaysForEndDateTime)
log.info("numberOfDaysForFutureBeginDateTime "+numberOfDaysForFutureBeginDateTime)
log.info("numberOfDaysForFutureEndDateTime "+numberOfDaysForFutureEndDateTime)


use(TimeCategory) {
	//Set BeginDateTime
    def updatedBeginDateTimeRequest = dateTimeStamp + numberOfDaysForBeginDateTime.toInteger()
    log.info ("updatedBeginDateTimeRequest " + updatedBeginDateTimeRequest)
    def beginDateTimeRequest = updatedBeginDateTimeRequest.format("yyyyMMdd HHmmss")
    log.info ("beginDateTimeRequest " + beginDateTimeRequest)
    testRunner.testCase.setPropertyValue("beginDateTimeRequest ", beginDateTimeRequest)
    //Set EndDateTime
    def updatedEndDateTimeRequest = dateTimeStamp + numberOfDaysForEndDateTime.toInteger()
	log.info ("updatedEndDateTimeRequest " + updatedEndDateTimeRequest)
    def endDateTimeRequest = updatedEndDateTimeRequest.format("yyyyMMdd HHmmss")
    log.info ("endDateTimeRequest " + endDateTimeRequest)
    testRunner.testCase.setPropertyValue("endDateTimeRequest ", endDateTimeRequest)
    // Set FutureBeginDateTime
	def updatedFutureBeginDateTimeRequest = dateTimeStamp + numberOfDaysForFutureBeginDateTime.toInteger()
	log.info ("updatedFutureBeginDateTimeRequest " + updatedFutureBeginDateTimeRequest)
    def futureBeginDateTimeRequest = updatedFutureBeginDateTimeRequest.format("yyyyMMdd HHmmss")
    log.info ("futureBeginDateTimeRequest " + futureBeginDateTimeRequest)
    testRunner.testCase.setPropertyValue("futureBeginDateTimeRequest ", futureBeginDateTimeRequest)
	// Set FutureEndDateTime
	def updatedFutureEndDateTimeRequest = dateTimeStamp + numberOfDaysForFutureEndDateTime.toInteger()
	log.info ("updatedFutureEndDateTimeRequest " + updatedFutureEndDateTimeRequest)
    def futureEndDateTimeRequest = updatedFutureEndDateTimeRequest.format("yyyyMMdd HHmmss")
    log.info ("futureEndDateTimeRequest " + futureEndDateTimeRequest)
    testRunner.testCase.setPropertyValue("futureEndDateTimeRequest ", futureEndDateTimeRequest)
	
	}
 }
==================================================================Finished======================================================
 /*
  *Created By  : Prabhu Nagaraj
  *Created Date: 11-11-2017
  *Parameters  : testRunner,log, numberOfDays, serverDateTime
  *Description : This method used to modify Server DateTime and set it to test case property
  *Version : 1.0
  */
 public def modifyServerDateTime(testRunner,log,def numberOfDays,def serverDateTime) {
 
def dateTimesplit = serverDateTime[2].split("\\:")
def serverCurrentDateTime = dateTimesplit[0] + ":" + dateTimesplit[1]
log.info("serverCurrentDateTime " + serverCurrentDateTime)
testRunner.testCase.setPropertyValue("ServerCurrentTime", serverCurrentDateTime)

SimpleDateFormat date = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm");
Date dateTimeStamp = date.parse(serverCurrentDateTime);
def dateFormatted = dateTimeStamp.toString().split(" ")
log.info ("dateFormatted " + dateFormatted[1])
testRunner.testCase.setPropertyValue("ServerMonth", dateFormatted[1])

Date presentDate = new Date()
SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
Date presentDateTimeStamp = formatter.parse(presentDate.format("yyyy-MM-dd HH:mm:ss"));

use(TimeCategory) {
   def  UpdatedPresentDate = presentDateTimeStamp+numberOfDays.toInteger()
    def currentDate = UpdatedPresentDate.format("yyyy-MM-dd")
    log.info ("CurrentDate " + currentDate)
    testRunner.testCase.setPropertyValue("CurrentDate ", currentDate)
	}
 }
==================================Finished============================
  /*
   *Created By  : Rekha Rathod
   *Created Date: 09-10-2017 
   *Parameters  : log,testRunner, filePath and fileName
   *Description : this method will set the file path and fileName to testCase
   */

public void settingFilePathAndName(log,testRunner,def filePath, def fileName )
{
	log.info("fileName "+fileName)
	log.info("filePath "+filePath)
	
	def finalFilePath = filePath +"\\"+ fileName.trim()
	log.info ("finalFilePath " +finalFilePath)
	
	testRunner.testCase.setPropertyValue("finalFilePath", finalFilePath)
	testRunner.testCase.setPropertyValue("filename", fileName.trim())
}
 /*
  *Created By  : Rekha Rathod 
  *Created Date:24-02-2017
  *Parameters  : testRunner,context,log,testCase,testStep,statusXpath,expectedStatus,pollingTime
  *Description : This method will be used poll a test step for a minute to get the expected status
  */

 public def pollForTaskCompletion(testRunner, context, log, def testCaseName, def testStepName, def statusXPath, def expectedStatus, def pollingTime) {
 log.info("pollingTime "+pollingTime)
   int i = 1
  def ResponseContent = context.expand(statusXPath)
  log.info("ResponseContent " + ResponseContent)
  def responseTestStep = testRunner.testCase.testSuite.testCases[testCaseName].testSteps[testStepName]
  while (ResponseContent != expectedStatus && i <= pollingTime.toInteger()) {
   sleep(2000)

   responseTestStep.run(testRunner, context)
   ResponseContent = context.expand(statusXPath)
   i++
  }
  log.info("statusXPath " + statusXPath)
 }
 =============================Finished============================
 /*
   *Created By  :Rakesh Anna
   *Created Date:30-03-2017
   *Parameters  : Path
   *Description : This method will be used to runPowerShellScript
   */
  public def runPowerShellScript(String Path) {
    def path = Path
  
  def command = "Powershell.exe -file ${path}";
  def myList = []
  def line;
  def process = Runtime.getRuntime().exec(command);
  process.getOutputStream().close();
  BufferedReader stdout = new BufferedReader(new InputStreamReader(
   process.getInputStream()));
  while ((line = stdout.readLine()) != null) {
   myList.add(line)
  }

  stdout.close();
  return myList;
 }
==============================Finished==============================

/*
*Created By  : Rahul Kulkarni
*Created Date:25-11-2016
*Parameters  : Path,ServerName,UserName,Password
*Description : This method will be used to get the data from server
*/
public def GetFileData(String Path,String ServerName,String UserName,String Password,String GetDataFilePath){
def serverName=ServerName
def userName =UserName
def password = Password
def path =Path
def getDataFilePath=GetDataFilePath
def command = "Powershell.exe -file ${path} ${serverName} ${userName} ${password} ${getDataFilePath}";
def myList = ""
def line
def process = Runtime.getRuntime().exec(command);
process.getOutputStream().close();
BufferedReader stdout = new BufferedReader(new InputStreamReader(process.getInputStream()));
while ((line = stdout.readLine()) != null) 
{
 myList = myList.concat(line )
 }
 stdout.close();
return myList
}
============================Finished=======================================
/*
*Created By  : Rahul Kulkarni
*Created Date: 25-11-2016
*Parameters  : datetime
*Description : This method used to modify date time by minute
*/
public def plusMinute(String datetime) {
  def dateTimesplit = datetime.split("\\.")
  SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm");
  Date dateTimeStamp = formatter.parse(datetime);
  def decrementedDateTime = null;
  use(TimeCategory) {
   decrementedDateTime = dateTimeStamp + 1. minutes
  }
  def finalDateTime = decrementedDateTime.format("YYYY-MM-dd'T'HH:mm")
  return finalDateTime;
 } 
 ===========================Finished======================
 /*
  *Created By  :Rakesh
  *Created Date:29-12-2016
  *Parameters  : strxPath
  *Description : This method will be used to calliSiteMonitor
  */
 public def calliSiteMonitor(String Path, String ServerName, String UserName, String Password) {
   def serverName = ServerName
   def userName = UserName
   def password = Password
   def path = Path
   def command = "Powershell.exe -file ${path} ${serverName} ${userName} ${password}";
   def myList = []
   def line;
   def process = Runtime.getRuntime().exec(command);
   process.getOutputStream().close();
   BufferedReader stdout = new BufferedReader(new InputStreamReader(
    process.getInputStream()));
   while ((line = stdout.readLine()) != null) {
    myList.add(line)
   }
}
===================Finished==================================
/*
*Created By  :Rakesh
*Created Date:23-02-2017
*Parameters  : xmlValue
*Description : This method will be used to format XMl string
*/
 public String formatXml(String xmlValue) 
 {
 String totalValue=xmlValue.replace("\n" ,"")
String FinalValue=totalValue.replace(" ","")
 return FinalValue
}
======================Finished===============================
 /*Created By :
  *Created Date:06-03-2017
  *Parameters  : dateTime
  *Description :  Removing millisecond from the Server dateTime and Response dateTime
  *Version : 1.0
  *Last Modified :06-03-2017
  */
 public String[] splitDatetimeMilliseconds(String ResponseTime) {
 String[] FinalModifiedTime = ResponseTime.split(":")
 
 String[] result = [FinalModifiedTime[0]+":"+FinalModifiedTime[1]]
  return result;
 }
=============================================Finished========================
 /*
  *Created By  : Prabhu Nagaraj
  *Created Date: 14-11-2017
  *Parameters  : testRunner,log, NumberOfDaysForBeginDateTime, NumberOfDaysForEndDateTime,DateTime,DateTimeFormat
  *Description : This method used to format and set the BeginDateTime and EndDateTime.
  *Version : 1.0
  */
 public def setBeginAndEndDateTimeWithDiffFormat(testRunner,log,def NumberOfDaysForBeginDateTime,def NumberOfDaysForEndDateTime,def dcmDate,def DateTimeFormat) {
 

def dateTime = dcmDate
log.info("dateTime " + dateTime)

def dateTimeStamp = new Date().parse("yyyy-MM-dd HH:mm:ss", dateTime)
def numberOfDaysForBeginDateTime = NumberOfDaysForBeginDateTime
def numberOfDaysForEndDateTime = NumberOfDaysForEndDateTime
log.info("numberOfDaysForBeginDateTime "+numberOfDaysForBeginDateTime)
log.info("numberOfDaysForEndDateTime "+numberOfDaysForEndDateTime)
def dateTimeFormat = DateTimeFormat
log.info("dateTimeFormat "+dateTimeFormat)

use(TimeCategory) {
	//Set BeginDateTime
    def updatedBeginDateTimeRequest = dateTimeStamp + numberOfDaysForBeginDateTime.toInteger()
    log.info ("updatedBeginDateTimeRequest " + updatedBeginDateTimeRequest)
	def beginDateTimeRequest 
	if(dateTimeFormat.toString().equals("yyyy-MM-dd'T'HH:mm:ss")){
     beginDateTimeRequest = updatedBeginDateTimeRequest.format("yyyy-MM-dd'T'HH:mm:ss")
    log.info ("beginDateTimeRequest " + beginDateTimeRequest)
	}
	else{
		  beginDateTimeRequest = updatedBeginDateTimeRequest.format("yyyyMMdd HHmmss")
    log.info ("beginDateTimeRequest " + beginDateTimeRequest)
	}
    testRunner.testCase.setPropertyValue("beginDateTimeRequest ", beginDateTimeRequest)
    def beginDateTimeDB = updatedBeginDateTimeRequest.format("yyyy-MM-dd HH:mm:ss")
    log.info ("beginDateTimeDB " + beginDateTimeDB)
    testRunner.testCase.setPropertyValue("beginDateTimeDB ", beginDateTimeDB)
	//Set EndDateTime
    def updatedEndDateTimeRequest = dateTimeStamp + numberOfDaysForEndDateTime.toInteger()
	log.info ("updatedEndDateTimeRequest " + updatedEndDateTimeRequest)
	def endDateTimeRequest
	if(dateTimeFormat.toString().equals("yyyy-MM-dd'T'HH:mm:ss")){
     endDateTimeRequest = updatedEndDateTimeRequest.format("yyyy-MM-dd'T'HH:mm:ss")
    log.info ("endDateTimeRequest " + endDateTimeRequest)
	}
	else{
		 endDateTimeRequest = updatedEndDateTimeRequest.format("yyyyMMdd HHmmss")
    log.info ("endDateTimeRequest " + endDateTimeRequest)
	}
    testRunner.testCase.setPropertyValue("endDateTimeRequest ", endDateTimeRequest)
    def endDateTimeDB = updatedEndDateTimeRequest.format("yyyy-MM-dd HH:mm:ss")
    log.info ("endDateTimeDB " + endDateTimeDB)
    testRunner.testCase.setPropertyValue("endDateTimeDB ", endDateTimeDB)
}
 }
=================================Pending===============================
  /*
  *Created By  : Prabhu Nagaraj
  *Created Date: 06-12-2017
  *Parameters  : testRunner,log, tagName,tagValue
  *Description :Replaces empty tag values 
  *Version : 1.0
  */
 public def updatingEmptyValues(log,testRunner,tagName,tagValue)
{
	def updatedtagValue = (tagValue.matches("<"+tagName+"/>"))?" ":tagValue
	log.info ("updated"+tagName+" "+updatedtagValue)
	testRunner.testCase.setPropertyValue(tagName, updatedtagValue)
}
 /*
  *Created By  : Prabhu Nagaraj
  *Created Date: 14-11-2017
  *Parameters  : testRunner,log, NumberOfDaysForBeginDateTime, NumberOfDaysForEndDateTime,DateTime,DateTimeFormat
  *Description : This method used to format and set the BeginDateTime and EndDateTime.
  *Version : 1.0
  */
 public def setBeginAndEndDateTimeWithDiffFormat(testRunner,log,def NumberOfDaysForBeginDateTime,def NumberOfDaysForEndDateTime,def dcmDate,def DateTimeFormat,def PropertyName_One,def PropertyName_Two) {
 

def dateTime = dcmDate
log.info("dateTime " + dateTime)

def dateTimeStamp = new Date().parse("yyyy-MM-dd HH:mm:ss", dateTime)
def numberOfDaysForBeginDateTime = NumberOfDaysForBeginDateTime
def numberOfDaysForEndDateTime = NumberOfDaysForEndDateTime
log.info("numberOfDaysForBeginDateTime "+numberOfDaysForBeginDateTime)
log.info("numberOfDaysForEndDateTime "+numberOfDaysForEndDateTime)
def dateTimeFormat = DateTimeFormat
log.info("dateTimeFormat "+dateTimeFormat)

use(TimeCategory) {
	//Set BeginDateTime
    def updatedBeginDateTimeRequest = dateTimeStamp + numberOfDaysForBeginDateTime.toInteger()
    log.info ("updatedBeginDateTimeRequest " + updatedBeginDateTimeRequest)
	def beginDateTimeRequest 
	if(dateTimeFormat.toString().equals("yyyy-MM-dd'T'HH:mm:ss")){
     beginDateTimeRequest = updatedBeginDateTimeRequest.format("yyyy-MM-dd'T'HH:mm:ss")
    log.info ("beginDateTimeRequest " + beginDateTimeRequest)
	}
	else{
		  beginDateTimeRequest = updatedBeginDateTimeRequest.format("yyyyMMdd HHmmss")
    log.info ("beginDateTimeRequest " + beginDateTimeRequest)
	}
    testRunner.testCase.setPropertyValue(PropertyName_One+"Request ", beginDateTimeRequest)
    def beginDateTimeDB = updatedBeginDateTimeRequest.format("yyyy-MM-dd HH:mm:ss")
    log.info ("beginDateTimeDB " + beginDateTimeDB)
    testRunner.testCase.setPropertyValue(PropertyName_One+"DB ", beginDateTimeDB)
	//Set EndDateTime
    def updatedEndDateTimeRequest = dateTimeStamp + numberOfDaysForEndDateTime.toInteger()
	log.info ("updatedEndDateTimeRequest " + updatedEndDateTimeRequest)
	def endDateTimeRequest
	if(dateTimeFormat.toString().equals("yyyy-MM-dd'T'HH:mm:ss")){
     endDateTimeRequest = updatedEndDateTimeRequest.format("yyyy-MM-dd'T'HH:mm:ss")
    log.info ("endDateTimeRequest " + endDateTimeRequest)
	}
	else{
		 endDateTimeRequest = updatedEndDateTimeRequest.format("yyyyMMdd HHmmss")
    log.info ("endDateTimeRequest " + endDateTimeRequest)
	}
    testRunner.testCase.setPropertyValue(PropertyName_Two+"Request ", endDateTimeRequest)
    def endDateTimeDB = updatedEndDateTimeRequest.format("yyyy-MM-dd HH:mm:ss")
    log.info ("endDateTimeDB " + endDateTimeDB)
    testRunner.testCase.setPropertyValue(PropertyName_Two+"DB ", endDateTimeDB)
}
 }
 =========================================Finished========================
 	/*
  *Created By  : Prabhu Nagaraj
  *Created Date: 21-11-2017
  *Parameters  : log, DateTime
  *Description : This method used to add one minute to the given datetime 
  *Version : 1.0
  */
 public def addOneMinuteToDateTime(log,def dateDate) {
  def dateTimeStamp = new Date().parse("yyyy-MM-dd HH:mm:ss", dateDate)
  use(TimeCategory) {
    def updatedDateTime = dateTimeStamp + 1.minutes 
    updatedDateTime = updatedDateTime.format("yyyy-MM-dd HH:mm:ss")
    log.info ("updatedDateTime " + updatedDateTime)
	return updatedDateTime
 }
 }
 ============================Finished============================
 /*
   *Created By  : Gourab Chakraborty
   *Created Date: 20-12-2017
   *Parameters  : testRunner, context, log , dbPatientName and Intented propertyName
   *Description : This method is to format Paitient name, eg : dbPatientname: Doe John, FormattedPatientName = Doe^John.
   */
   
public void formatPatientName(testRunner, context, log, def dbPatientName, def propertyName)
{
	def updatedPatientname = dbPatientName.split(" ")
	log.info("formattedPatientName " + updatedPatientname[0].toString() + "^" + updatedPatientname[1].toString() )
	testRunner.testCase.setPropertyValue(propertyName, updatedPatientname[0] + "^" + updatedPatientname[1])
}
=========================================Finished==================================
/*
   *Created By  : Gourab Chakraborty
   *Created Date: 20-12-2017
   *Parameters  : testRunner, context, log , dbPatientBirthDate and Intented propertyName
   *Description : This method is to format Paitient Birthdate, eg : dbBirthdate: 2017-12-20, FormattedPatientBirthDate = 20171220.
   */
   
public void formatPatientBirthDate(testRunner, context, log, def dbPatientBirthDate, def propertyName)
{
	
	def updatedPatientBirthDate = dbPatientBirthDate.split(" ")
	updatedPatientBirthDate =updatedPatientBirthDate[0].replace("-","")
	log.info("formattedPatientBirthDate " + updatedPatientBirthDate)
	testRunner.testCase.setPropertyValue(propertyName,updatedPatientBirthDate)
}

  /*
  *Created By  :Prasanth Dayalan
  *Created Date:24-02-2017
  *Parameters  : testRunner,context,log,testCase,testStep,statusXpath,expectedStatus
  *Description : This method will be used poll a test step for a minute to get the expected status
  */

 public def pollToUpdateDCMRecords(testRunner, context, log, def testCaseName, def testStepName, def statusXPath, def expectedStatus) {
  int i = 1
  def ResponseContent = context.expand(statusXPath)
  log.info("ResponseContent " + ResponseContent)
  def responseTestStep = testRunner.testCase.testSuite.testCases[testCaseName].testSteps[testStepName]
  while (ResponseContent.toInteger() <= expectedStatus.toInteger() && i <= 300) {
  
   sleep(2000)
   responseTestStep.run(testRunner, context)
   ResponseContent = context.expand(statusXPath)
   log.info i
   i++
   
  }
  log.info("statusXPath " + statusXPath)
 }

 /*
  *Created By  : Venkatesh Krishna
  *Created Date:29-01-2018
  *Parameters  : file1 - Path of first File,file2 - Path of second File
  *Description : This method will compare two files and return false if files are different and true is files are identical
  */
 public def compare(file1, file2) {
		File a = new File(file1)
		File b = new File(file2)
		
		if(a.length() != b.length()){
		        return false;
	    }
	
	    final int BLOCK_SIZE = 128;
	    InputStream aStream = new FileInputStream(a);
	    InputStream bStream = new FileInputStream(b);
	    byte[] aBuffer = new byte[BLOCK_SIZE];
	    byte[] bBuffer = new byte[BLOCK_SIZE];
	    while (true) {
	        int aByteCount = aStream.read(aBuffer, 0, BLOCK_SIZE);
	        bStream.read(bBuffer, 0, BLOCK_SIZE);
	        if (aByteCount < 0) {
	            return true;
	        }
	        if (!Arrays.equals(aBuffer, bBuffer)) {
	            return false;
	        }
	    }        
        
     }
	 
	  /*
   *Created By  : Prasanth Dayalan
   *Created Date: 09-10-2017
   *Parameters  : testRunner, log, count,responseValue
   *Description : This method is to tokenize the response string value.
   */

public void tokenizeResponseValue(testRunner,context,log,def responseValue)
{
def responseSectionValue = responseValue
log.info ("ResponseSectionValue " +responseSectionValue)
def updatedSectionValue = responseSectionValue.tokenize('^')
log.info ("ResponseSectionValue " +responseSectionValue)
testRunner.testCase.setPropertyValue("ResponseSectionValue",responseSectionValue)

def count  = updatedSectionValue.size()
log.info ("Count " +Count)

def finalValue =""
]
for(def i =1; i<=count; i++)
{
	def dbSectionValue = context.expand('${GetImpressionsForExamJoinDBData#result#//Results[1]/ResultSet[1]/Row['+i+']/sectionValue[1]}' )
	log.info("dbSectionValue " +dbSectionValue)
	finalValue =finalValue+dbSectionValue
}
log.info("FinalValue " +finalValue)
testRunner.testCase.setPropertyValue("dbSectionValue",finalValue)
}
==================================Finished====================
/*
  *Created By  : Prabhu Nagaraj
  *Created Date: 29-11-2017
  *Parameters  : testRunner,log,ServerDate
  *Description :  To set offset and server datetime according to purge method
  */
public void setOffsetAndDate(testRunner, log,ServerDate) {

String[] timeZoneInfo=ServerDate.split(" ")
def updatedServerOffsetValue = timeZoneInfo[0].replaceAll("\\(|\\)", "").minus("UTC")
updatedServerOffsetValue = updatedServerOffsetValue.split(":")
updatedServerOffsetValue = updatedServerOffsetValue[0]
log.info ("updatedServerOffsetValue " +updatedServerOffsetValue)
def serverDateTime=(timeZoneInfo[timeZoneInfo.length-2]).split("T")
log.info ("serverDateTime "+ serverDateTime[0])
testRunner.testCase.setPropertyValue("ServerOffsetValue",updatedServerOffsetValue)
testRunner.testCase.setPropertyValue("ServerDateTime",serverDateTime[0]+ " " + "00:00:000")
}
==================================Finised===========================
/*
  *Created By  :Venkatesh Krishna
  *Created Date:14-02-2017
  *Parameters  : Path,ServerName,UserName,Password, Configpath, StackPath
  *Description : This method will be used to runPowerShellScript
  */
 public def UpdateConfigFile(String Path, String ServerName, String UserName, String Password, String ConfigPath, String Stackpath) {
  
  def serverName = ServerName
  def userName = UserName
  def password = Password
  def path = Path
  def configPath = ConfigPath
  def stackpath = Stackpath
  def command = "Powershell.exe -file ${path} ${serverName} ${userName} ${password} ${configPath} ${stackpath}";
  def myList = []
  def line;
  
  
  def process = Runtime.getRuntime().exec(command);
  myList.add(command)
  
  process.getOutputStream().close();
  BufferedReader stdout = new BufferedReader(new InputStreamReader(
   process.getInputStream()));
  
  while ((line = stdout.readLine()) != null) {
   myList.add(line)
  }
  stdout.close();
  return myList;
 }

 /*
  *Created By  : Santosh Kumar
  *Created Date: 15-02-2018
  *Parameters  : log,context,sqlCmd,placeHolderList,fetchSize,dbConnectionString
  *Description : This method is used to get select query values and creating arrayList
  *Version : 1.0
  *Last Modified : 
  */
 //Select db values and add to arraylist
 public ArrayList<String> selectQueryResult(log,context,sqlCmd,placeHolderList,fetchSize,dbConnectionString) 
{
	Connection dbConnection = null;
	Statement dbStatement = null;
	ResultSet dbResultSet = null;
	ArrayList<String> ColValues = new ArrayList<String>();
    String value = "";
	dbConnection = context[dbConnectionString]; 		
	dbStatement = dbConnection.prepareStatement(sqlCmd);   
	if (placeHolderList.size()!=0) {
	for (int i=0;i<placeHolderList.size();i++) {
		dbStatement.setString(i+1,placeHolderList[i]);
		}
	}		
	dbResultSet = dbStatement.executeQuery();
 	     
	while (dbResultSet.next()) 
	{ 
	  value=dbResultSet.getString(1); 
	  ColValues.add(value)
		
	}     
    
	//log.info ("Query result coloumn vaules="+ColValues)
	 dbResultSet.close();  		 
	  return ColValues;	
 }
 
  /*
*Created By  : Ranjitha Janardhana
*Created Date: 18-01-2018
*Parameters  : log,testRunner,tagName,tagValue
*Description : This method is used set empty Value,Is NULL or the same tag value
*Version : 1.0
*Last Modified : 
*/
public def updatingEmptyAndSettingNull(log,testRunner,tagName,tagValue)
{
def updatedtagValue = (tagValue.matches("<[A-Za-z].*/>"))?" ":tagValue
/* When tag value is empty*/
if(tagValue.matches("<[A-Za-z].*/>"))
{
log.info ("updated"+tagName+" "+updatedtagValue)
log.info ("updated"+tagName+"DB"+updatedtagValueDB)
testRunner.testCase.setPropertyValue("updated"+tagName+"DB" , updatedtagValueDB)

/* Setting space for tag(used in request body)*/
testRunner.testCase.setPropertyValue("updated"+tagName, updatedtagValue)
}
/* When tag has values(data/datetime)*/
else {
if(tagValue.matches("[A-Za-z]"))
{
testRunner.testCase.setPropertyValue("updated"+tagName, updatedtagValue)
 }
else {

updatedtagValue = tagValue
log.info ("updated"+tagName+" "+updatedtagValue)
updatedtagValue = updatedtagValue.replace(" ", 'T')
updatedtagValue = updatedtagValue.toString().split("\\.")
log.info ("updated"+tagName+" "+updatedtagValue[0])
testRunner.testCase.setPropertyValue("updated"+tagName, updatedtagValue[0])

}
}

}

/*
   *Created By  : Prasanth Dayalan
   *Created Date: 09-10-2017 
   *Parameters  : testRunner, expectedStatus, propertyName, expectedCondition,internalserviceFaultCodeXPath,deserializationErrorXPath,schemaXpath and faultcodeXpath
   *Description : This method will be used to set expected XPath 
   */

def void settingExpectedXPath(testRunner, log, context, def expectedStatus,def propertyName, def internalserviceFaultCodeXPath , def deserializationErrorXPath,def schemaXPath)
{
 log.info ("expectedStatus "+expectedStatus)
 log.info ("propertyName "+propertyName)
 log.info ("internalserviceFaultCodeXPath "+internalserviceFaultCodeXPath)
 log.info ("deserializationErrorXPath "+deserializationErrorXPath)

 
 if(expectedStatus.equals(context.expand( '${#Project#InternalServiceFaultResponse}' )))
 {
 testRunner.testCase.setPropertyValue(propertyName,internalserviceFaultCodeXPath)
 
 }
 else if(expectedStatus.equals(context.expand( '${#Project#DeserializationFailedError}' )))
 {
    testRunner.testCase.setPropertyValue(propertyName,deserializationErrorXPath)
 }else {
 testRunner.testCase.setPropertyValue(propertyName,schemaXPath)
 }
 }
 ================================Finished=============================
 /*
  *Created By  : Prashanth Pai
  *Created Date: 10-09-2017
  *Parameters  : command
  *Description : This method will execute Adam command and returns the response 
*/
 public def runAdamExecutable(command) {
	def myList = [];
	def line;
	def process = Runtime.getRuntime().exec(command);
	process.getOutputStream().close();
	BufferedReader stdout = new BufferedReader(new InputStreamReader(
	process.getInputStream()));
	while ((line = stdout.readLine()) != null) {
		myList.add(line)
		}
	stdout.close();
	StringBuilder builder=new StringBuilder();
	for (String value:myList){
		builder.append(value);
	}
	return builder.toString();
 }

   /*
   *Created By  : Gourab Chakraborty
   *Created Date: 21-03-2018 
   *Parameters  : testRunner, log,XmlString
   *Description : This method will Convert XML Strings to JSON 
  */
 
 public def convertXmlToJson(log, def XmlString)
 {
	    XMLSerializer xmlSerializer = new XMLSerializer();  
        JSON json = xmlSerializer.read(XmlString);
         log.info("JSON format : " + json);
         String jsonResponse = json.toString()
		 return  jsonResponse
  } 

  /*
   *Created By  : Ranjitha Janardhana
   *Created Date: 22-03-2018 
   *Parameters  : hostName
   *Description : This method will return the IP Address of the host name passed
   */
 
 public def getIpAddress(def hostName)
 {
	return InetAddress.getByName(hostName).address.collect { it & 0xFF }.join('.')
 }
 
 /*
  *Created By  : Rekha
  *Created Date: 22-03-2018
  *Parameters  : testRunner, log, context, FileName, propertyName, RequestName, response
  *Description : This method will write the response to the text file.
  */
  

public def WriteContentToFile(def TestCaseName, def parentDirectory, def Content)
{
// Create a random name for Directory;
Date currentDateTime = new Date();

DateFormat sdf = new SimpleDateFormat("yyyyMMddHHmmssSSSS")
def currentDateTimeStr = sdf.format(currentDateTime)
def fileName = TestCaseName + "_" + currentDateTimeStr 
def currentDirString = parentDirectory + "\\" +  "Temp\\" + fileName
File currentDir = new File(currentDirString);  
currentDir.mkdirs();

def responseFile = new File(currentDir.absolutePath + "\\"+ fileName )
responseFile.append(Content)
responseFile.toString()	
}

 /*

  *Created By  :Nithya Pillai

  *Created Date:22-03-2018

  *Parameters  :testRunner,authorizationHeader,testStepName,acceptHeader,contentTypeHeader,userAgentHeader,headerStatus

  *Description : This method will be used to set the header values for Rest request

  */

 public void setRestHeader(testRunner, String authorizationHeader,  String testStepName, String acceptHeader,String contentTypeHeader, String userAgentHeader, String headerStatus) {

   def headers = new StringToStringMap()
	if(headerStatus == "Valid")
	{
	   headers.put(authorizationHeader, '${#Project#HmacKey}')
	   headers.put(acceptHeader, '${#Project#Accept}')
	   headers.put(contentTypeHeader, '${#Project#ContentType}')
	   headers.put(userAgentHeader, '${#Project#User-Agent}')
    }
   
   else if(headerStatus == "Expired")
   {
		headers.put(authorizationHeader, '${#Project#ExpiredHmacKey}')
		headers.put(acceptHeader, '${#Project#Accept}')
		headers.put(contentTypeHeader, '${#Project#ContentType}')
		headers.put(userAgentHeader, '${#Project#User-Agent}')
   }
   
   else if(headerStatus == "Invalid")
   {
		headers.put(authorizationHeader, '${#Project#InvalidHmacKey}')
		headers.put(acceptHeader, '${#Project#Accept}')
		headers.put(contentTypeHeader, '${#Project#ContentType}')
		headers.put(userAgentHeader, '${#Project#User-Agent}')
   }
   testRunner.testCase.getTestStepByName(testStepName).testRequest.setRequestHeaders(headers)

  }
  
    /*
  *Created By  : Venkatesh Krishna
  *Created Date:29-01-2018
  *Parameters  : file1 - Path of first File,file2 - Path of second File
  *Description : This method will compare two files and return false if files are different and true is files are identical
  */
 public def compare(log,file1, file2) {
		File a = new File(file1)
		File b = new File(file2)
		log.info ("a.length() "+ a.length())
		log.info ("b.length() "+ b.length())
		if(a.length() != b.length()){
		        return false;
	    }
	
	    final int BLOCK_SIZE = 128;
	    InputStream aStream = new FileInputStream(a);
	    InputStream bStream = new FileInputStream(b);
	    byte[] aBuffer = new byte[BLOCK_SIZE];
	    byte[] bBuffer = new byte[BLOCK_SIZE];
	    while (true) {
	        int aByteCount = aStream.read(aBuffer, 0, BLOCK_SIZE);
	        bStream.read(bBuffer, 0, BLOCK_SIZE);
	        if (aByteCount < 0) {
	            return true;
	        }
	        if (!Arrays.equals(aBuffer, bBuffer)) {
	            return false;
	        }
	    }        
        
    } 

  /*
  *Created By  : Prasanth Dayalan
  *Created Date: 20-03-2018
  *Parameters  : log, context, configuredXMLFilePath and dcmFilesPath
  *Description : This method will call the IWattConsoleExecution with required parameters
  */
 public void sendStudiesWithIWatt(log, context,def configuredXMLFilePath, def dcmFilesPath)
{
	IWattConsoleExecution(log,context.expand( '${#Project#commandLineExePath}' ), configuredXMLFilePath, context.expand( '${#Project#resultLogFolderPath}' ), dcmFilesPath, context.expand( '${#Project#importFromSubDirectories}' ), context.expand( '${#Project#importMultipleFiles}' ), context.expand( '${#Project#storageCommit}' ))
  
}

/*
  *Created By  : Gourab Chakraborty
  *Created Date: 06-04-2018
  *Parameters  : log, filePath
  *Description : This method will read from a file. 
  */
 
 public String readFromFile(log,def filePath){
	File file = new File(filePath);
    StringBuilder fileContents = new StringBuilder((int)file.length());
    Scanner scanner = new Scanner(file);
    String lineSeparator = System.getProperty("line.separator");
        while(scanner.hasNextLine()) {
            fileContents.append(scanner.nextLine() + lineSeparator);
        }
        return fileContents.toString()
 
 }
 
  /*
  *Created By  : Gourab Chakraborty
  *Created Date: 06-04-2018
  *Parameters  : log, defaultConfiguration,locationToAdd
  *Description : This method will add a	location
  */
 
 public String addLocation(log, def defaultConfiguration,def locationToAdd){
	return  defaultConfiguration.replace("</LocationProfiles> </Location>", "") + locationToAdd+"</LocationProfiles> </Location>"
 }
 
 /*
  *Created By  : Prasanth Dayalan
  *Created Date: 07-04-2018
  *Parameters  : log, context and dcmFilesPath
  *Description : This method will sends the random studies information
  */
 public void sendRandomStudiesWithIWatt(log, context, def dcmFilesPath)
{
	def command = context.expand( '${#Project#commandLineExePath}' ) + " " + context.expand( '${#Project#configuredXMLFile}' ) + " " + "results" + " " + context.expand( '${#Project#resultLogFolderPath}' ) + " store-test-data " + dcmFilesPath + " true "+ context.expand( '${#Project#fixedDataPath}' ) +" MR false false";

  def line;
  def process = Runtime.getRuntime().exec(command);
  process.getOutputStream().close();
  BufferedReader stdout = new BufferedReader(new InputStreamReader(
   process.getInputStream()));
  while ((line = stdout.readLine()) != null) {

   log.info line
  }

  stdout.close();
  
  }
      /*
    *Created By  : Deepak/ Janavi
  *Created Date: 07-04-2020
  *Parameters  : log, context and dcmFilesPath
  *Description : This method will sends the random studies information
  */
 public void sendRandomStudiesWithIWattConfigXML(log, context, def dcmFilesPath, def configuredXMLFile)
{
	def command = context.expand( '${#Project#commandLineExePath}' ) + " " + configuredXMLFile + " " + "results" + " " + context.expand( '${#Project#resultLogFolderPath}' ) + " store-test-data " + dcmFilesPath + " true "+ context.expand( '${#Project#fixedDataPath}' ) +" MR false false";

  def line;
  def process = Runtime.getRuntime().exec(command);
  process.getOutputStream().close();
  BufferedReader stdout = new BufferedReader(new InputStreamReader(
   process.getInputStream()));
  while ((line = stdout.readLine()) != null) {

   log.info line
  }

  stdout.close();
  
  }
 
   /*
  *Created By  :Rahul Kulkarni
  *Created Date:24-04-2018
  *Parameters  : directory
  *Description : This method will be used to Get PowershellSriptBasePath
  *Version : 1.0
  *Last Modified : 04-11-2016
  */
 public def getPowershellSriptBasePath(String directory) {
  def ProjectDir = directory
  def finalPath = ProjectDir.replace("APIAutomation_UDM\\Projects\\Regression", "APIAutomation\\")
  com.eviware.soapui.SoapUI.globalProperties.setPropertyValue("powershellScriptBasePath", finalPath)
 }
 ================================Finished==========================================
  /*
  *Created By  : Prasanth Dayalan
  *Created Date: 30-04-2018
  *Parameters  : log
  *Description : This method will return the client machine time
  */
 public def getClientMachineTime(log)
 {
	Date dateTime = new Date()
	def sdf = new SimpleDateFormat("MM/dd/yyyy HH:mm:ss")
	def startTime =  sdf.format(dateTime)
	log.info ("Authentication satrt time is "+startTime)
	return startTime
	
 } 
 ===================Pending==============================
 /*
  *Created By  : Prasanth Dayalan
  *Created Date: 30-04-2018
  *Parameters  : log and context
  *Description : This method will return time difference in seconds
  */
 public def retunDiffrenceBetweenStartAndCurrentTime(log,context)
 {
	Date dateTime = new Date()
	log.info ("Current time is"+dateTime)
	def AuthStartTime = context.expand( '${#Project#AuthenticationStartTime}' )
	def startedTime = Date.parse("MM/dd/yyyy HH:mm:ss",AuthStartTime)
	
	log.info ("Authentication start time is "+startedTime)
	
	def differnceInSecs = ((dateTime.getTime() - startedTime.getTime())/1000)
	return differnceInSecs
	
}
===================================================Finished===================================
/*
  *Created By  : Prasanth Dayalan
  *Created Date: 30-04-2018
  *Parameters  : dateTime and secondsToAdd
  *Description : This method will be used to increment by a given seconds for given date time
  */
 public def incrementGivenMinute(log,def datetime, def secondsToAdd) {
   //def dateTimesplit = datetime.split("\\.")
   SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.S");
   Date dateTimeStamp = formatter.parse(datetime);
   log.info ("formatter "+dateTimeStamp)
   def incrementedDateTime = null;
   use(TimeCategory) {
    incrementedDateTime = dateTimeStamp + secondsToAdd.toInteger(). seconds
   }
   def finalDateTime = incrementedDateTime.format("YYYY-MM-dd'T'HH:mm:ss.S")
   return finalDateTime;
  }
  
   /*
  *Created By  : Prasanth Dayalan
  *Created Date: 30-04-2018
  *Parameters  : log and context
  *Description : This method will return time difference in seconds
  */
  public void generatingAuthTocken(log,context,testRunner)
  {
	def differenceInSeconds = retunDiffrenceBetweenStartAndCurrentTime(log,context)
	log.info ("differenceInSeconds "+differenceInSeconds)
	def finalIncrementTimeInSeconds = differenceInSeconds.toString().split("\\.")
	log.info ("finalIncrementTimeInSeconds "+finalIncrementTimeInSeconds[0])
	def hmacKey = context.expand( '${#Project#HmacKey}' )
	if (finalIncrementTimeInSeconds[0].toInteger() < 540 && finalIncrementTimeInSeconds[0].toInteger() > 50)
	{
		
		def authTimeStamp = context.expand( '${#Project#AuthenticationTimeStamp}' )
		def incrementedTime = incrementGivenMinute(log,authTimeStamp , finalIncrementTimeInSeconds[0].toInteger())
		log.info ("incrementedTime "+incrementedTime)
		testRunner.testCase.testSuite.project.setPropertyValue("AuthenticationTimeStamp",incrementedTime)
		hmacKey = hmacGeneration(context.expand( '${#Project#AuthenticationHMACSecretKey}' ), context.expand( '${#Project#AuthenticationTicket}' ), context.expand( '${#Project#AuthenticationTimeStamp}' ), context.expand('${#Project#HmacSHA}'), context.expand('${#Project#iSiteWebApplication}'))
		testRunner.testCase.testSuite.project.setPropertyValue("HmacKey",hmacKey)
	}else if(finalIncrementTimeInSeconds[0].toInteger() < 50){
		log.info ("No need to do anything !!!")
	}else {
		if(hmacKey.contains("JWT"))
		{
			testRunner.testCase.testSuite.project.testSuites["CommonTestSuite"].testCases["AuthService_ValidUserNameAndPassword"].run(new com.eviware.soapui.support.types.StringToObjectMap(), false);
		}
		else
		{
			testRunner.testCase.testSuite.project.testSuites["CommonTestSuite"].testCases["Authentication_ValidLogout"].run(new com.eviware.soapui.support.types.StringToObjectMap(), false);
			testRunner.testCase.testSuite.project.testSuites["CommonTestSuite"].testCases["Authentication_ValidUserNameAndPassword"].run(new com.eviware.soapui.support.types.StringToObjectMap(), false);
		}
	}
	
  }


   /* ===============================================
XML Report Generation START
=================================================*/
/*
  *Created By  : Prashanth Pai
  *Created Date: 07-24-2017
  *Parameters  : testRunner,log,String testStepName
  *Description : This method is used to set the result of TestCase
  *Version : 1.0
  *Last Modified : 
  */
  public void setTestCaseResult(testRunner,log,String testCaseName) {
	def testStepList = testRunner.testCase.testSuite.testCases[testCaseName].getTestStepList();
	def assertionList;
	testRunner.testCase.setPropertyValue("Result", "PASS")
	testStepList.each{
		log.info it.getName();
		if(it.metaClass.hasProperty(it,'assertionStatus')){
			if (it.config.type=='assertionteststep')
				{
					assertionList = it.getAssertionEntryList()
					for (assertion in assertionList) {
						log.info "assertion: "+it.getName();
						if (assertion.getStatus().toString() == "FAILED") {
							testRunner.testCase.setPropertyValue("Result", "FAIL")
							break;
							}
						}  
				}
			}
		}
		log.info( "Setting of TestCase property [Result] completed")
	}
 
 public void setTestStepResult(testRunner,log,String testStepName) {
	def testStep = testRunner.testCase.getTestStepByName(testStepName)
	def list = testStep.getAssertionEntryList()
	for (assertion in list) {
		if (assertion.getStatus().toString() == "FAILED") {
			testRunner.testCase.setPropertyValue("Result", "FAIL")
			break;
		} else {
			testRunner.testCase.setPropertyValue("Result", "PASS")
			}
	}
	log.info( "Setting of TestCase property [Result] completed")
 }
 
/*
 *Created By  : Prashanth Pai
 *Created Date: 8-4-2017
 *Parameters  : testCase
 *Description : This method will return test step statuses per iteration of test case.
*/

//Remove log from arguments
public  Map<String,List> getTestStepStatusPerIteration(testCase)
{
	def assertionStatusMap = [:]
	def StepList = testCase.getTestStepList()

	def testStepStatusList=[];
	StepList.each{
		if(it.metaClass.hasProperty(it,'assertionStatus')){
			if (it.config.type=='assertionteststep')
				{
					testStepStatusList = getAssertionStatus(it);
				}
				else {
					testStepStatusList = getTestStepStatus(it);
				}
				assertionStatusMap.put(it.name,testStepStatusList)
		}
	}
	return assertionStatusMap;
}

/*
 *Created By  : Prashanth Pai
 *Created Date: 7-31-2017
 *Parameters  : assertionTestStep
 *Description : This method will return all assertion statuses in assertion test step.
*/

public ArrayList<String>  getAssertionStatus(assertionTestStep)
{
	def assertionStatusList = [];
	def errorMessage;
	def assertionList = assertionTestStep.getAssertionEntryList()
	for( assertion in assertionList) {
		assertionStatusList << assertion.getSourceName()
		if (assertion.disabled == false) {
		switch(assertion.getStatus().toString()){
				case 'FAILED': 
					errorMessage = getErrorMessage(assertion);
					assertionStatusList << "fail" << errorMessage
					break;
				case 'VALID':
					assertionStatusList << "pass"
					break;
				case 'UNKNOWN':
					assertionStatusList << "unknown"
					break;
			}
		}
	}
	return assertionStatusList;
}

/*
 *Created By  : Prashanth Pai
 *Created Date: 7-31-2017
 *Parameters  : assertionTestStep
 *Description : This method will return status of test step.
*/

public ArrayList<String> getTestStepStatus(testStep)
{
	def assertionStatusList = []
	if(testStep.assertionStatus == AssertionStatus.FAILED){
		assertionStatusList << "FAIL";
	}else if(testStep.assertionStatus == AssertionStatus.VALID){
		assertionStatusList << "PASS";
	}else if(testStep.assertionStatus == AssertionStatus.UNKNOWN){
		assertionStatusList << "UNKNOWN";
	}
	return assertionStatusList;
}


/*
 *Created By  : Prashanth Pai
 *Created Date: 7-31-2017
 *Parameters  : assertion
 *Description : This method will return error message of assertion.
*/

public def getErrorMessage(assertion)
{
	def errorMessage;
	for(err in assertion.getAssertion().getErrors()) {
		errorMessage = err.getMessage();
	}
	return errorMessage;
}

/*
 *Created By  : Prashanth Pai
 *Created Date: 7-7-2017
 *Parameters  : assertionMap,testStepIndex,log,testRunner
 *Description : This method will update assertion status and error message if any.
*/

public def updateAssertion(assertionMap,testStepIndex,log,testRunner)
{
	def tempList = []
 	assertionMap.each{ key, value -> 
	   	log.info "${key}" 
	   	log.info assertionMap."${key}" 
	    	tempList = assertionMap."${key}" 
		tempList.eachWithIndex { it1, i -> 
		if (tempList.getAt(i)=='fail') {
        		testRunner.testCase.setPropertyValue("AssertionStatus"+testStepIndex, "fail")
        		testRunner.testCase.setPropertyValue("AssertionError"+testStepIndex+"message",tempList.getAt(i+1))
            	log.info tempList.getAt(i+1)
        }
        else {
        		testRunner.testCase.setPropertyValue("AssertionStatus"+testStepIndex, "pass")
        		}
		}
	}
} 
/*
 *Created By  : Prashanth Pai
 *Created Date: 7-7-2017
 *Parameters  : assertionMap,testStepIndex,assertionTeststep,log,testRunner
 *Description : This method will update assertion status and error message if any.
*/
public def updateAssertionStatusWithIndex(assertionMap,testStepIndex,assertionTeststep,log,testRunner)
{
	def tempList = []
	def passResult = []
	def failResult = []
	Integer passIndex = 0;
	Integer failIndex = 0;
 	assertionMap.each{ key, value -> 
	   	if ("${key}"==assertionTeststep){
	    	tempList = assertionMap."${key}" 
	    	log.info tempList
	    	passResult=tempList.findIndexValues {it=='pass'}
		if (passResult.size()>0){
			 passResult.eachWithIndex{it, i -> 
			 passIndex = passResult.getAt(i) as Integer
			 log.info tempList[passIndex-1]
			 testRunner.testCase.setPropertyValue(tempList[passIndex-1]+testStepIndex, "pass")
			}
		}
	    	failResult=tempList.findIndexValues {it=='fail'}
		if (failResult.size()>0){
			 failResult.eachWithIndex{it, i -> 
			 failIndex = failResult.getAt(i) as Integer
			 log.info tempList[failIndex-1]
			 testRunner.testCase.setPropertyValue(tempList[failIndex-1]+testStepIndex, "fail")
			 log.info tempList[failIndex+1]
			 testRunner.testCase.setPropertyValue("AssertionErrorMessage"+(i+1)+testStepIndex, tempList[failIndex+1])
				}
			}
		}
	}
}
/*
 *Created By  : Prashanth Pai
 *Created Date: 7-7-2017
 *Parameters  : assertionMap,testStepIndex,log,testRunner
 *Description : This method will update assertion status and error message if any.
*/
public def updateAssertionStatus(assertionMap,assertionTeststep,log,testRunner, index)
{
	def tempList = []
 	assertionMap.each{ key, value -> 
	   	if ("${key}"==assertionTeststep){
	    	tempList = assertionMap."${key}" 
	    	log.info tempList
			tempList.each { log.info "tempList: $it"}
	    	def passResult=tempList.findIndexValues {it=='pass'}
	    	passResult.each { log.info "passResult: $it"}
		if (passResult.size()>0){
			 passResult.eachWithIndex{it, i -> 
			 Integer passIndex = passResult.getAt(i) as Integer
			 log.info "Should not print " + tempList[passIndex-1]
			 testRunner.testCase.setPropertyValue(tempList[passIndex-1]+"."+"Status" + "[" + index + "]", "Pass")
			 log.info "Should not print " + tempList[passIndex-1]+"."+"Status"+"."+ "[" + index + "]" + "Pass";
			}
		}
	    	def failResult=tempList.findIndexValues {it=='fail'}
			int asssertionErrorMessageIndex=1;
		if (failResult.size()>0){
			 failResult.eachWithIndex{it, i -> 
			 Integer failIndex = failResult.getAt(i) as Integer
			 log.info tempList[failIndex-1]
			 testRunner.testCase.setPropertyValue(tempList[failIndex-1]+"."+"Status" + "[" + index + "]", "Fail")
			 log.info tempList[failIndex-1]+"."+"Status"+"."+"Fail";
			 testRunner.testCase.setPropertyValue(tempList[failIndex-1]+"."+"AssertionErrorMessage"+(i+1) + "[" + index + "]", tempList[failIndex+1])
			 log.info tempList[failIndex-1]+"."+"AssertionErrorMessage"+(i+1)+"." + "[" + index + "]" + tempList[failIndex+1];
			}
		}
		}
	}
}
/*
 *Created By  : Prashanth Pai
 *Created Date: 10-23-2017
 *Parameters  : testStepName,totalTestSteps,context
 *Description : This method will return combination of assertion messages.
*/
public def getAssertionMessage(def testStepName,int totalTestSteps,context)
{
	def assertionMessage="";
	(1..totalTestSteps).each {
	assertionMessage=assertionMessage+context.expand("\${#TestCase#${testStepName}.AssertionErrorMessage${it}}")
	}
	return assertionMessage;
}
/*
 *Created By  : Prashanth Pai
 *Created Date: 01-29-2018
 *Parameters  : testStepName,testCaseResultProperty,log,runner
 *Description : This method will return a map of XML test results.
*/
public Map<String,String> getTestCaseResult(testStepName,testCaseResultProperty,log,runner)
{
	def testCaseResultMap=[:];
	def testCaseName="";
	def testCaseNameShort="";
	runner.testSuite.getTestCaseList().each {
	  if (!it.isDisabled()) {
	    testCaseName=it.getName().split(/\./);
	    //log.info "testCase: "+testCaseName;
	    testCaseNameShort=testCaseName[testCaseName.length-1];
	    //log.info "testCase short: "+testCaseNameShort;  
	    it.getTestStepList().each() {
	    	if ((it.getName()==testStepName) && (!it.isDisabled())) {
	    	  //log.info it.getName();
	    	  log.info it.getPropertyValue(testCaseResultProperty);
	    	  testCaseResultMap.put(testCaseNameShort,it.getPropertyValue(testCaseResultProperty));
	    	}
			
		
	    }
	  }
	}
	return testCaseResultMap;
}

/*
 *Created By  : Prashanth Pai
 *Created Date: 01-29-2018
 *Parameters  : testStepName,testCaseResultProperty,log,runner
 *Description : This method will return a map of XML test results.
*/
public Map<String,String> getProjectTestCaseResult(testStepName,testCaseResultProperty,log,runner)
{
	def testCaseResultMap=[:];
	def testCaseName="";
	def testCaseNameShort="";
	runner.testSuite.getTestCaseList().each {
	  if (!it.isDisabled()) {
	    testCaseName=it.getName();
	    //log.info "testCase: "+testCaseName;
	    //testCaseNameShort=testCaseName[testCaseName.length-1];
	    //log.info "testCase short: "+testCaseNameShort;  
	    it.getTestStepList().each() {
	    	if ((it.getName()==testStepName) && (!it.isDisabled())) {
	    	  //log.info it.getName();
	    	  log.info it.getPropertyValue(testCaseResultProperty);
	    	  //testCaseResultMap.put(it.getName(),it.getPropertyValue(testCaseR//esultProperty));
			  testCaseResultMap.put(testCaseName,it.getPropertyValue(testCaseResultProperty));
	    	}
			
		
	    }
	  }
	}
	return testCaseResultMap;
}

/*
 *Created By  : Prashanth Pai
 *Created Date: 01-29-2018
 *Parameters  : testCaseResultMap,generateXMLReportTestSuite,generateXMLReportTestCase,log,runner
 *Description : This method will set XML test results.
*/
public void setTestCaseResult(testCaseResultMap,generateXMLReportTestSuite,generateXMLReportTestCase,log,runner)
{
 	testCaseResultMap.each{ key, value -> 
	   	//log.info "Key: "+"${key}"; 
	   	//log.info "Value: "+"${value}";
	   	runner.testSuite.project.getTestSuiteByName(generateXMLReportTestSuite).getTestCaseByName(generateXMLReportTestCase).setPropertyValue("${key}", "${value}")
 	}
}
/*=================================================
XML Report Generation END
=================================================*/
================================Finished==============================================
    /*
  *Created By  :Rekha 
  *Created Date:29-12-2016
  *Parameters  : Path,ServerName,UserName,Password,queue,Exchange,RoutingKey and TimeOut
  *Description : This method will be used to runPowerShellScript to fetch the message from RabitMQ
  */
 public def runPowerShellScript(def Path, def ServerName, def UserName, def Password, def queue, def Exchange, def RoutingKey, def TimeOut) {

  def command = "Powershell.exe -file ${Path} ${ServerName} ${UserName} ${Password} ${queue} ${Exchange} ${RoutingKey} ${TimeOut}";
  def myList = []
  def line;
  def process = Runtime.getRuntime().exec(command);
  process.getOutputStream().close();
  BufferedReader stdout = new BufferedReader(new InputStreamReader(
   process.getInputStream()));
  while ((line = stdout.readLine()) != null) {
   myList.add(line)
  }
  stdout.close();
  return myList;  
 }
=============================================Finished==============================================
   /*
  *Created By  :Rekha 
  *Created Date:29-12-2016
  *Parameters  : Path,ServerName,UserName,Password,queue,Exchange,RoutingKey,ExchangeType,PriorityQueue
  *Description : This method will be used to runPowerShellScript to create a queue in RabbitMQ
  */
 public def runPowerShellScript(def Path, def ServerName, def UserName, def Password, def queue, def Exchange, def RoutingKey, def ExchangeType, def PriorityQueue) {

  def command = "Powershell.exe -file ${Path} ${ServerName} ${UserName} ${Password} ${queue} ${Exchange} ${RoutingKey} ${ExchangeType} ${PriorityQueue}";
  def myList = []
  def line;
  def process = Runtime.getRuntime().exec(command);
  process.getOutputStream().close();
  BufferedReader stdout = new BufferedReader(new InputStreamReader(
   process.getInputStream()));
  while ((line = stdout.readLine()) != null) {
   myList.add(line)
  }
  stdout.close();
  return myList;
 }
 
 /*
  *Created By  : Prasanth Dayalan 
  *Created Date: 11-06-2018
  *Parameters  : context, type,  patientCount,  studyCount,  seriesCount,  imageCount and modality
  *Description : This method is to update the DCM Suite batch file
  */
 public void updateDCMSuiteBatchFile(context,def type, def patientCount, def studyCount, def seriesCount, def imageCount, def modality) {
	def batchFile = context.expand( '${#Project#DCMSuiteBatchPath}' )
	File file = new File(batchFile)
	def batchCommand
	if(type.equals("SendRandom"))
	{
		batchCommand = context.expand( '${#Project#DCMSuiteRandomBatcheCommand}' )
	
	}else if(type.equals("GenerateLog"))
	{
		batchCommand = context.expand( '${#Project#DCMSuiteGenerateLogBatcheCommand}' )
	}else if(type.equals("ProcessData"))
	{
		batchCommand = context.expand( '${#Project#DCMSuiteDataProcessBatcheCommand}' )
	}
	Date todayDate1 = new Date()
	def sdf = new SimpleDateFormat("yyyyMMdd")
	def todayDate =  sdf.format(todayDate1)
	def batchContent = batchCommand.replace("patientCount",patientCount).replace("studyCount",studyCount).replace("seriesCount",seriesCount).replace("imageCount",imageCount).replace("modality",modality).replace("today",todayDate).replace("PRServerIP",context.expand( '${#Project#PRServerIP}' )).replaceAll("newLine","\n")
	file.write(batchContent)
	def dCMSuiteBatchPath = context.expand( '${#Project#DCMSuiteBatchPath}' )
	Runtime.getRuntime().exec(dCMSuiteBatchPath);
 }
 =================================================Finished=================================================
  /*
  *Created By  : Prasanth Dayalan 
  *Created Date: 11-06-2018
  *Parameters  : context, patientName and patientId
  *Description : This method is to update log file content
  */
 public void sendStudyToExistingPatient(context, def patientName, def patientId) {
	def logFile = context.expand( '${#Project#DCMSuiteLogPath}' )
	File file = new File(logFile)
	def logFileContent = file.getText()
	def splitDate = logFileContent.split("\\(0018.0015\\)\t")
	def logData = splitDate[1].split("\t")
	def j = 1
	String list = ""
	for (int i = 0; i<logData.size()-1; i++)
{
	if (i == 0 || (i % 25) == 0)
	{
		logData[i] = patientName
	}else if (i==1 || (i % ((25*j)+1)) == 0)
	{
		logData[i] = patientId
	}
	
	if(i > 0 && logData[i].equals(patientName) )
	{
		list = list + "\n"+logData[i] + "\t"
	}else {
		list = list + logData[i] + "\t"
	}
	if(i >= 50 && (i % 25) == 0)
	{
		j++		
	}
	
}
	file.write(splitDate[0] + "(0018.0015)\t\n" + list)
	
 }
======================================Finished============================================
 /*
  *Created By  : Prasanth Dayalan 
  *Created Date: 11-06-2018
  *Parameters  : context, patientName , patientId
  *Description : This method is to update log file content and studyUID
  */
 public void sendSeriesToExisitingStudy(context, def patientName, def patientId,def studyUID) {
	def logFile = context.expand( '${#Project#DCMSuiteLogPath}' )
	File file = new File(logFile)
	def logFileContent = file.getText()
	def splitDate = logFileContent.split("\\(0018.0015\\)\t")
	def logData = splitDate[1].split("\t")
	def j = 1
	String list = ""
	for (int i = 0; i<logData.size()-1; i++)
{
	if (i == 0 || (i % 25) == 0)
	{
		logData[i] = patientName
	}else if (i==1 || (i % ((25*j)+1)) == 0)
	{
		logData[i] = patientId
	}else if (i==7|| (i % ((25*j)+7)) == 0)
	{
		logData[i] = studyUID
	}
	
	if(i > 0 && logData[i].equals(patientName) )
	{
		list = list + "\n"+logData[i] + "\t"
	}else {
		list = list + logData[i] + "\t"
	}
	if(i >= 50 && (i % 25) == 0)
	{
		j++		
	}
	
}
	file.write(splitDate[0] + "(0018.0015)\t\n" + list)
	
 }
============================================================Finished=============================================
 /*
  *Created By  : Prasanth Dayalan 
  *Created Date: 11-06-2018
  *Parameters  : context, patientName , patientId, studyUID and seriesUID
  *Description : This method is to update log file content of patient , study and series
  */
 public void sendImagesToExisitingSeries(context, def patientName, def patientId,def studyUID,def seriesUID) {
	def logFile = context.expand( '${#Project#DCMSuiteLogPath}' )
	File file = new File(logFile)
	def logFileContent = file.getText()
	def splitDate = logFileContent.split("\\(0018.0015\\)\t")
	def logData = splitDate[1].split("\t")
	def j = 1
	String list = ""
	for (int i = 0; i<logData.size()-1; i++)
{
	if (i == 0 || (i % 25) == 0)
	{
		logData[i] = patientName
	}else if (i==1 || (i % ((25*j)+1)) == 0)
	{
		logData[i] = patientId
	}else if (i==7|| (i % ((25*j)+7)) == 0)
	{
		logData[i] = studyUID
	}
	else if (i==11|| (i % ((25*j)+11)) == 0)
	{
		logData[i] = seriesUID
	}
	
	if(i > 0 && logData[i].equals(patientName) )
	{
		list = list + "\n"+logData[i] + "\t"
	}else {
		list = list + logData[i] + "\t"
	}
	if(i >= 50 && (i % 25) == 0)
	{
		j++		
	}
	
}
	file.write(splitDate[0] + "(0018.0015)\t\n" + list)
	
 }
 ======================================Finished==============================================
 /*
  *Created By  :Rekha 
  *Created Date:29-12-2016
  *Parameters  : Path,ServerName,UserName,Password,queue,Exchange,RoutingKey and TimeOut
  *Description : This method will be used to runPowerShellScript to fetch the message from RabitMQ
  */
 public def runPowerShellScript(def Path, def ServerName, def UserName, def Password, def queue, def Exchange, def RoutingKey, def TimeOut , def exchangetype, def priorityqueue) {

  def command = "Powershell.exe -file ${Path} ${ServerName} ${UserName} ${Password} ${queue} ${Exchange} ${RoutingKey} ${TimeOut} ${exchangetype} ${priorityqueue}";
  def myList = []
  def line;
  def process = Runtime.getRuntime().exec(command);
  process.getOutputStream().close();
  BufferedReader stdout = new BufferedReader(new InputStreamReader(
   process.getInputStream()));
  while ((line = stdout.readLine()) != null) {
   myList.add(line)
  }
  stdout.close();
  return myList;
 }
 
 /*
  *Created By  : Nithya Pillai
  *Created Date: 25-05-2018
  *Parameters  : log, context, dcmFilesPath, modalityType
  *Description : This method will sends the random studies information with different types of Modalities
  */
 public void sendRandomStudiesWithIWatt(log, context, def dcmFilesPath,def modalityType)
{
	
	def command = context.expand( '${#Project#commandLineExePath}' ) + " " + context.expand( '${#Project#configuredXMLFile}' ) + " " + "results" + " " + context.expand( '${#Project#resultLogFolderPath}' ) + " store-test-data " + dcmFilesPath + " true "+ context.expand( '${#Project#fixedDataPath}' ) + " "+ modalityType + " false false";
	log.info "command -  " +command
  def line;
  def process = Runtime.getRuntime().exec(command);
  process.getOutputStream().close();
  BufferedReader stdout = new BufferedReader(new InputStreamReader(
   process.getInputStream()));
  while ((line = stdout.readLine()) != null) {

   log.info line
   log.info "line " +line
  }

  stdout.close();
  
  }
  ================================================Finished==============================================
    /*
  *Created By  : Prasanth Dayalan
  *Created Date: 02-08-2017
  *Parameters  : Path,ServerName,UserName,Password, Configpath, configFileName, StackPath, operation and destinationpath
  *Description : This method will be used to runPowerShellScript
  */
 public def UpdateConfigFile(String Path, String ServerName, String UserName, String Password, String ConfigPath,String configFileName ,String Stackpath,String operation,String Destination) {
  
  def serverName = ServerName
  def userName = UserName
  def password = Password
  def path = Path
  def configPath = ConfigPath
  def stackpath = Stackpath
  def command = "Powershell.exe -file ${path} ${serverName} ${userName} ${password} ${configPath} ${configFileName} ${stackpath} ${operation} ${Destination}";
  def myList = []
  def line;
  
  
  def process = Runtime.getRuntime().exec(command);
  myList.add(command)
  
  process.getOutputStream().close();
  BufferedReader stdout = new BufferedReader(new InputStreamReader(
   process.getInputStream()));
  
  while ((line = stdout.readLine()) != null) {
   myList.add(line)
  }
  stdout.close();
  return myList;
 }
=========================================Finished============================
  /*
   *Created By  : Rekha 
   *Created Date: 16-09-2017
   *Parameters  : Path, ServerName, UserName, Password, serviceDisplayName and ProcessName
   *Description : This method will be used to runPowerShellScript to kill a process from the server
   */
 public def runPowerShellScriptToGetStatus(log, def Path, def ServerName, def UserName, def Password, def ProcessName) {
  def serverName = ServerName
  def userName = UserName
  def password = Password
  def path = Path
  def processName = ProcessName
  def command = "Powershell.exe -file ${path} ${serverName} ${userName} ${password} ${processName}";
  def myList = []
  def line;
  def process = Runtime.getRuntime().exec(command);
  process.getOutputStream().close();
  BufferedReader stdout = new BufferedReader(new InputStreamReader(
   process.getInputStream()));
  while ((line = stdout.readLine()) != null) {
   myList.add(line)
  }

  stdout.close();
  return myList;
 }
 =============================================================Finished========================
  /*
  *Created By  : Gourab Chakraborty
  *Created Date: 11-06-2018
  *Parameters  : NA
  *Description : This method will return the client IP address.
  */
 
 public String getClientIP(){
	String[]  clientIp= InetAddress.getLocalHost().toString().split("/")
	return clientIp[1]
 }
 ==================================Finished=================================================================
 /*
  *Created By  : Gourab Chakraborty 
  *Created Date: 11-06-2018
  *Parameters  : context, patientName , patientId, birthDate(YYYYMMDD),sexCode
  *Description : This method is to update log file content
  */
 
 public void sendStudyToExistingPatient(context, def patientName, def patientId, def birthDate , def sexCode ) {
	def logFile = context.expand( '${#Project#DCMSuiteLogPath}' )
	File file = new File(logFile)
	def logFileContent = file.getText()
	def splitDate = logFileContent.split("\\(0018.0015\\)\t")
	def logData = splitDate[1].split("\t")
	def j = 1
	String list = ""
	for (int i = 0; i<logData.size()-1; i++)
{
	if (i == 0 || (i % 25) == 0)
	{
		logData[i] = patientName
	}else if (i==1 || (i % ((25*j)+1)) == 0)
	{
		logData[i] = patientId
	}
	else if (i==2 || (i % ((25*j)+2)) == 0)
	{
		logData[i] = birthDate
	}else if (i==3 || (i % ((25*j)+3)) == 0)
	{
		logData[i] = sexCode
	}
	
	if(i > 0 && logData[i].equals(patientName) )
	{
		list = list + "\n"+logData[i] + "\t"
	}else {
		list = list + logData[i] + "\t"
	}
	
	
	if(i >= 50 && (i % 25) == 0)
	{
		j++		
	}
	
}
	file.write(splitDate[0] + "(0018.0015)\t\n" + list)
	
 }
 ================================================Finished==========================================================================
 /*
  *Created By  :Rekha Rathod
  *Created Date:24-09-2018
  *Parameters  : datetime, daysToBeIncremented
  *Description : This method will be used to increment the given date time by given day
  */
 public def incrementDate(log,def datetime, def daysToBeIncremented) {
	SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.S");
	Date dateTime = formatter.parse(datetime);
	def incrimented
	use(TimeCategory) {
     incrimented = dateTime + daysToBeIncremented.toInteger() . days
	 log.info("dateTime "+incrimented)
	 log.info formatter.format(incrimented)
     }
	SimpleDateFormat formatterNew = new SimpleDateFormat("MM/dd/yyyy HH:mm:ss.S");
	return formatterNew.format(incrimented)
  }
 
 /*
  *Created By  : Rekha 
  *Created Date: 11-06-2018
  *Parameters  : context, type,  patientCount,  studyCount,  seriesCount,  imageCount and modality
  *Description : This method is to update the DCM Suite batch file
  */
 public void updateDCMSuiteBatchFileWithOrg(context, def patientCount, def studyCount, def seriesCount, def imageCount, def modality, def organization) {
	def batchFile = context.expand( '${#Project#DCMSuiteBatchPath}' )
	File file = new File(batchFile)
	def batchCommand = context.expand( '${#Project#DCMSuiteOrgCommand}' )
	Date todayDate1 = new Date()
	def sdf = new SimpleDateFormat("yyyyMMdd")
	def todayDate =  sdf.format(todayDate1)
	def batchContent = batchCommand.replace("patientCount",patientCount).replace("studyCount",studyCount).replace("seriesCount",seriesCount).replace("imageCount",imageCount).replace("modality",modality).replace("today",todayDate).replace("PRServerIP",context.expand( '${#Project#PRServerIP}' )).replaceAll("newLine","\n").replace("Organization",organization)
	file.write(batchContent)
	def dCMSuiteBatchPath = context.expand( '${#Project#DCMSuiteBatchPath}' )
	Runtime.getRuntime().exec(dCMSuiteBatchPath);
 }
 ==============================================Finished===================================================================
 /*
   *Created By  : Rekha 
   *Created Date: 16-09-2017
   *Parameters  : Path, ServerName, UserName, Password, stackFolderPath and StudyUid
   *Description : This method will be used to runPowerShellScript to get the .dts files from the server
   */
 public def runPowerShellScriptToGetdtsFiles(log, def Path, def ServerName, def UserName, def Password, def StackFolderPath, def StudyUid) {
  def serverName = ServerName
  def userName = UserName
  def password = Password
  def path = Path
  def stackFolderPath = StackFolderPath
  def studyUid = StudyUid
  def command = "Powershell.exe -file ${path} ${serverName} ${userName} ${password} ${stackFolderPath} ${studyUid} ";
  def myList = []
  def line;
  def process = Runtime.getRuntime().exec(command);
  process.getOutputStream().close();
  BufferedReader stdout = new BufferedReader(new InputStreamReader(
   process.getInputStream()));
  while ((line = stdout.readLine()) != null) {
   myList.add(line)
  }

  stdout.close();
  return myList;
 }
 ==============================================Finished=================================== 
 /*
   *Created By  : Rahul Kulkarni
   *Created Date: 31-01-2019
   *Parameters  : Path FolderPath and FileName
   *Description : This method will be used to runPowerShellScript for deleting the file
   */
 public def runPowerShellScriptForDelete(def Path, def FolderPath, def FileName) {
  
  def path = Path
  def folderPath = FolderPath
  def fileName = FileName
  def command = "Powershell.exe -file ${path} ${folderPath} ${fileName}";
  def myList = []
  def line
  def process = Runtime.getRuntime().exec(command);
  process.getOutputStream().close();
  BufferedReader stdout = new BufferedReader(new InputStreamReader(
   process.getInputStream()));
  while ((line = stdout.readLine()) != null) {
   myList.add(line)
  }

  stdout.close();
  return myList;
 }
===================================================Finished==============================================================
 /* 
  /*
   *Created By  : Rahul Kulkarni
   *Created Date: 02-02-2019
   *Parameters  : Path FolderPath and FileName
   *Description : This method will be used to runPowerShellScript for replacing the result.xml file content
   */
 public def runPSToReplaceXMLContent(def Path, def SoupUIResultPath , def TestSuiteName, def FailureCount) {
  
  def path = Path
  def soupUIResultPath = SoupUIResultPath
  def testSuiteName = TestSuiteName
  def failureCount = FailureCount
  def command = "Powershell.exe -file ${path} ${soupUIResultPath} ${testSuiteName} ${failureCount}";
  def myList = []
  def line
  def process = Runtime.getRuntime().exec(command);
  process.getOutputStream().close();
  BufferedReader stdout = new BufferedReader(new InputStreamReader(
   process.getInputStream()));
  while ((line = stdout.readLine()) != null) {
   myList.add(line)
  }

  stdout.close();
  return myList;
 }
 =======================================Finished==============================================================
 /*
  *Created By  : Prasanth Dayalan 
  *Created Date: 20-11-2018
  *Parameters  : context, patientName and patientId
  *Description : This method is to update log file content to create the exception
  */
 public void createPatientException(context, def patientName, def patientId) {
	def logFile = context.expand( '${#Project#DCMSuiteLogPath}' )
	File file = new File(logFile)
	def logFileContent = file.getText()
	def splitDate = logFileContent.split("\\(0018.0015\\)\t")
	def logData = splitDate[1].split("\t")
	def j = 1
	String list = ""
	for (int i = 0; i<logData.size()-1; i++)
{
	if ((i == 0 || (i % 25) == 0) && patientName != "NULL")      /*if ((i == 0 || (i % 25) == 0) && patientName != "NULL")*/
	{
		logData[i] = patientName
	}else if ((i==1 || (i % 25+1) == 0) && patientId != "NULL")      /*else if ((i==1 || (i % ((25*j)+1)) == 0) && patientId != "NULL") */  
	{
		logData[i] = patientId
	}
	
	if(i > 0 && logData[i].equals(patientName) )
	{
		list = list + "\n"+logData[i] + "\t"
	}else {
		list = list + logData[i] + "\t"
	}
	if(i >= 50 && (i % 25) == 0)
	{
		j++		
	}
	
}
	file.write(splitDate[0] + "(0018.0015)\t\n" + list)
	
 }
 =====================================================Finished================================
 /*
  *Created By  : Prasanth Dayalan 
  *Created Date: 21-11-2018
  *Parameters  : context, patientName , patientId
  *Description : This method is to update log file content and studyUID
  */
 public void duplicateStudyException(context, log, def patientName, def patientId,def studyUID) {
	def logFile = context.expand( '${#Project#DCMSuiteLogPath}' )
	File file = new File(logFile)
	def logFileContent = file.getText()
	def splitDate = logFileContent.split("\\(0018.0015\\)\t")
	def logData = splitDate[1].split("\t")
	def j = 1
	String list = ""
	for (int i = 0; i<logData.size()-1; i++)
	{
		
		if ((i == 0 || (i % 25) == 0) && patientName.toString() != "NULL")    /*if ((i == 0 || (i % 25) == 0) && patientName.toString() != "NULL")*/
		{
			log.info patientName
			logData[i] = patientName
		}else if ((i==1 || (i % 25+1) == 0) && patientId != "NULL")  /*else if ((i==1|| (i % ((25*j)+1)) == 0) && patientId != "NULL" )*/
		{
			logData[i] = patientId
		}else if ((i==7|| (i % 25 +1) == 0) && studyUID != "NULL" )    /*else if ((i==7|| (i % ((25*j)+7)) == 0) && studyUID != "NULL" )*/
		{
			log.info studyUID
			logData[i] = studyUID.toString()
		}
		
		if((i > 0 && logData[i].equals(patientName)) )
		{
			list = list + "\n"+logData[i] + "\t"
		}else {
			list = list + logData[i] + "\t"
		}
		if(i >= 50 && (i % 25) == 0)
		{
			j++		
		}
	
	}
	file.write(splitDate[0] + "(0018.0015)\t\n" + list)
	
 }
 
 /*
  *Created By  :Rakesh
  *Created Date:30-1-2017
  *Parameters  :testRunner,log,request,testStepName,serverTimestamp,HMACKey,includeTimeStampInHash,hmacSHA
  *Description : This method will be used for generating Hash value of request body and set the header values for XML-RPC 
  */
  public void generateHashandAccept(testRunner, log, String authorizationHeader, String hashHeader, String HeaderStatus, String request, String testStepName, String serverTimestamp, String HMACKey, boolean includeTimeStampInHash, String hmacSHA, String acceptHeaderName, String acceptHeaderValue, String hmacKey) {
   request.toString().replaceAll(/>\s+</, '><').trim()
   String toBeHashedstring;
   def bearer = "Bearer "
   if (includeTimeStampInHash) {
    if (request != null) {
     toBeHashedstring = request + ";" + serverTimestamp;
    } else
     toBeHashedstring = serverTimestamp;
   } else {
    toBeHashedstring = request;
   }
   def HMACValue = hmac_sha(HMACKey, toBeHashedstring, hmacSHA);
   def HASHValue = HMACValue.encodeBase64().toString();
   log.info("X-s-Content-Signature " + HASHValue.trim())
   def headers = new StringToStringMap()
   if (HeaderStatus == "Active") {
    headers.put(authorizationHeader, hmacKey)
  headers.put(hashHeader, HASHValue.trim())
  headers.put(acceptHeaderName, acceptHeaderValue)
   } else if (HeaderStatus == "Expired") {
    headers.put(authorizationHeader, '${#Project#ExpiredHmacKey}')
  headers.put(hashHeader, HASHValue.trim())
   }
   else if (HeaderStatus == "NonAdmin") {
    headers.put(authorizationHeader, '${#Project#Non_Admin_HmacKey}')
  headers.put(hashHeader, HASHValue.trim())
   }
   else if(HeaderStatus == "Invalid")
   {
   headers.put(authorizationHeader, '${#Project#InvalidHmacKey}')
    headers.put(hashHeader, HASHValue.trim())
   }
   else if(HeaderStatus.contains("User"))
   {
   def HeaderValue = "\${#Project#" + HeaderStatus + "_Hmac}"
   headers.put(authorizationHeader, HeaderValue)
   headers.put(hashHeader, HASHValue.trim())
   }
   else if(HeaderStatus == "EmptyContentHeader")
   {
   headers.put(authorizationHeader, '${#Project#HmacKey}')
    headers.put(hashHeader, "")
   }
   else if(HeaderStatus == "InvalidContentHeader")
   {
 headers.put(authorizationHeader, '${#Project#HmacKey}')
    headers.put(hashHeader, HASHValue.trim()+"gkjsbfkjcvhfkjuxdzhvudhvudxchnvudhn")
   }
   else if(HeaderStatus == "WINDOWSINTEGRATED")
   {
   headers.put(authorizationHeader, '${#Project#WindowsIntegratedUser_HmacKey}')
   headers.put(hashHeader, HASHValue.trim())
   }
    else if(HeaderStatus == "WithoutContentHeader")
   {
 headers.put(authorizationHeader, '${#Project#HmacKey}')
   }
    else if(HeaderStatus == "OpenIdConnect")
   {
 headers.put(authorizationHeader, bearer + '${#Project#HmacKey}')
   }
   testRunner.testCase.getTestStepByName(testStepName).testRequest.setRequestHeaders(headers)
  }
=====================================================Finished=================================================================
  /*
 *Created By  : Prasanth Dayalan
 *Created Date: 22-11-2018
 *Parameters  : dsdepath,serverIp,userCreationXMLFile,DBLoginUserName,DBLoginPassword
 *Description : This method will be used to create a user specified in the user's configuration XML file
 */

public def userCreationDSDE(log,def dsdepath, def serverip, def userCreationXMLFile, def DBLoginUserName, def DBLoginPassword) {
   def command = "Powershell.exe " + dsdepath + " /mode import  /encrypt /server " + serverip + " /port 3890 /input " + userCreationXMLFile + " /cred " +    DBLoginUserName + " " + DBLoginPassword+ " " + " /resume";
   
      log.info "command:"+command;

  def myList = []
 def line;
 def process = Runtime.getRuntime().exec(command);
 process.getOutputStream().close();
 BufferedReader stdout = new BufferedReader(new InputStreamReader(
  process.getInputStream()));
 while ((line = stdout.readLine()) != null) {
  myList.add(line)
 }

 stdout.close();
 return myList;
}

/*
 *Created By  : Nithya Pillai
 *Created Date: 16-01-2019
 *Parameters  : context,domainUser,sqlCommandPath
 *Description : This method will be used to update the sqlCommand batch file
 */

public def updateSQLCommandBatch(context,def batchFileCommand, def domainUser, def sqlCommandPath) {
	def batchFile = context.expand( '${#Project#SQLBatchFilePath}' )
	File file = new File(batchFile)
	def batchCommand = batchFileCommand
	def batchContent = batchCommand.replace("domainUser",domainUser).replace("sqlCommandPath",sqlCommandPath)
	file.write(batchContent)
	def SQLBatchFilePath = context.expand( '${#Project#SQLBatchFilePath}' )
	return batchCommand
	}
==================================Finished======================================  
 /*
   *Created By  : Nithya Pillai
   *Created Date: 04/02/19
   *Parameters  : log, HL7FillingExePath, hl7ServerIp, portNumber and hl7MessageFilePath.
   *Description : This method is to send different types of HL7 data to the server using HL7Filing.exe
   */
 public def sendHl7Data(log,def HL7FilingExePath, def hl7ServerIp, def portNumber, def hl7MessageFilePath)
 {
 def command = HL7FilingExePath+" "+hl7ServerIp+" "+portNumber+" "+hl7MessageFilePath
  
 def line, hl7MessageOutput;
 
  def process = Runtime.getRuntime().exec(command);
  process.getOutputStream().close();
  BufferedReader stdout = new BufferedReader(new InputStreamReader(
   process.getInputStream()));
  while ((line = stdout.readLine()) != null) {
   log.info line
   hl7MessageOutput = line
   
  }
  stdout.close();
 
   return hl7MessageOutput.toString()
 }
 
=====================================Finished======================================== 
 
 /*
   *Created By  : Gourab Chakraborty
   *Created Date: 19/03/19
   *Parameters  : String batch Command
   *Description : This method is used to run batch command via cmd.
   */
 
 
public  String runCommand(String command){
	def myList = ""
	def line
	def process = Runtime.getRuntime().exec(command)
	process.getOutputStream().close()
	BufferedReader stdout = new BufferedReader(new InputStreamReader(process.getInputStream()))
	while ((line = stdout.readLine()) != null) 
		{
	 		myList = myList.concat(line)
	 	}
	stdout.close()
	return myList.toString();
} 

/*
  *Created By  : Rekha Rathod 
  *Created Date:11-04-2019
  *Parameters  : testRunner,context,log,testCase,testStep,statusXpath,expectedStatus,pollingIteration
  *Description : This method will be used poll a test step job status to be completed
  */

 public def pollForJobTaskCompletion(testRunner, context, log, def testCaseName, def testStepName, def statusXPath, def expectedStatus, def pollingIteration) {
 log.info("pollingIteration "+pollingIteration)
   int i = 1
  def ResponseContent = context.expand(statusXPath)
  log.info("ResponseContent " + ResponseContent)
  def responseTestStep = testRunner.testCase.testSuite.testCases[testCaseName].testSteps[testStepName]
  while (ResponseContent != expectedStatus && i <= pollingIteration.toInteger()) {
   sleep(60000)

   responseTestStep.run(testRunner, context)
   ResponseContent = context.expand(statusXPath)
   i++
  }
  log.info("statusXPath " + statusXPath)
 }
 =======================================================Finished======================================================================
 /*
   *Created By  : Jijo Joy
   *Created Date: 29/03/19
   *Parameters  : path,args
   *Description : This method is used to start and stop the services from IIS
   */
  
  public def runGenericPowerShellScript(def path, def args) {

  def command = "Powershell.exe -file ${path}"
  for(int i = 0; i < args.size(); i++)
  {
  def arg = args[i]
  command = command+" "+arg
  }
  //def command = "Powershell.exe -file ${path} ${serverName} ${userName} ${password} ${serviceDisplayName} ${action}";
  def myList = []
  def line;
  def process = Runtime.getRuntime().exec(command);
  process.getOutputStream().close();
  BufferedReader stdout = new BufferedReader(new InputStreamReader(
   process.getInputStream()));
  while ((line = stdout.readLine()) != null) {
   myList.add(line)
  }
  stdout.close();
  return myList;
 }
==================================================================Finished====================================================================
 /*
  *Created By  :Rekha 
  *Created Date:22-05-2019
  *Parameters  : Path,ServerName,UserName,Password,FolderPath
  *Description : This method will be used to runPowerShellScript to get the files count from the folder and to delete the files from the folder
  */
 public def runPowerShellScriptForMultipleFileOperation(def Path, def ServerName, def UserName, def Password, def FolderPath) 
 {

  def command = "Powershell.exe -file ${Path} ${ServerName} ${UserName} ${Password} ${FolderPath}";
  def myList = []
  def line;
  def process = Runtime.getRuntime().exec(command);
  process.getOutputStream().close();
  BufferedReader stdout = new BufferedReader(new InputStreamReader(
   process.getInputStream()));
  while ((line = stdout.readLine()) != null) {
   myList.add(line)
  }
  stdout.close();
  return myList;  
 }
 
/*
  *Created By  : Nithya Pillai 
  *Created Date: 20-08-2019
  *Parameters  : context
  *Description : This method is to send the missing accession number exception
  */
 public void createMissingAccessionNumberException(context) {
	def logFile = context.expand( '${#Project#DCMSuiteLogPath}' )
	File file = new File(logFile)
	def logFileContent = file.getText()
	def splitDate = logFileContent.split("\\(0018.0015\\)\t")
	def logData = splitDate[1].split("\t")
	def j = 1
	def accessionNumber, patientName
	String charset = ('a'..'z')
	patientName = "Test" + "^" + RandomStringUtils.random(5,charset.toCharArray())
	String list = ""
	for (int i = 0; i<logData.size()-1; i++)
	{
		if ((i == 0 || (i % 25) == 0))    /*if ((i == 0 || (i % 25) == 0) && patientName.toString() != "NULL")*/
		{
			logData[i] = patientName
		}
		else if (i == 10 || (i % 25) == 0)    /*if ((i == 0 || (i % 25) == 0) && accessionNumber.toString() != "NULL")*/
		{
			logData[i] = "!NULL!"
		}	
		if(i > 0 && logData[i].equals(accessionNumber) )
		{
			list = list + "\n"+logData[i] + "\t"
		}else {
			list = list + logData[i] + "\t"
		}
		if(i >= 50 && (i % 25) == 0)
		{
			j++		
		}
	
	}
	file.write(splitDate[0] + "(0018.0015)\t\n" + list)
	
 }
 
 /*
  *Created By  :Nithya Pillai 
  *Created Date:09-27-2019
  *Parameters  : log,context,sqlCmd,placeHolderList,dbConnectionString
  *Description : This method will execute Select SQL command and return the number of deleted rows.
*/
   
   public boolean AlterDB(log,context,sqlCmd,placeHolderList,dbConnectionString) {
	Connection dbConnection = null;
	Statement dbStatement = null;
	dbConnection = context[dbConnectionString]; 		
	dbStatement = dbConnection.prepareStatement(sqlCmd);   
	int rowsSelected = 0;
	if (placeHolderList.size()==0) {
		rowsSelected = 1;	
	}
	else{ 
		rowsSelected = 0;	
	}
	
	 log.info(" Number of rows Selected: " + rowsSelected)
	 return rowsSelected;     
   }
   
 /*
  *Created By  : Prashanth Pai
  *Created Date: 05-04-2018
  *Parameters  : log,context,sqlStoredProcCall,placeHolderList,fetchSize,dbConnectionString
  *Description : This method will execute SQL Stored Procedure and returns the response in XML format.
*/
   String runStoredProc(log,context,sqlStoredProcCall,placeHolderList,fetchSize,dbConnectionString) {
	Connection dbConnection = null;
	CallableStatement dbStatement = null;
	ResultSet dbResultSet = null;
	ResultSetMetaData dbResultSetMetaData = null;
	DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();     
	DocumentBuilder builder = factory.newDocumentBuilder();     
	Document doc = builder.newDocument();     
	Element results = doc.createElement("Results");     
	doc.appendChild(results);     
	Element resultSet = doc.createElement("ResultSet");     
	resultSet.setAttribute("fetchSize",fetchSize ?: '128')     
	results.appendChild(resultSet);  
	dbConnection = context[dbConnectionString]; 		
	dbStatement=dbConnection.prepareCall(sqlStoredProcCall);   
	if (placeHolderList.size()!=0) {
	for (int i=0;i<placeHolderList.size();i++) {
		dbStatement.setString(i+1,placeHolderList[i]);
		}
	}		
	dbResultSet = dbStatement.executeQuery();
	dbResultSetMetaData = dbResultSet.getMetaData();  
	int colCount = dbResultSetMetaData.getColumnCount();     
	int rowNumber=1; 	     
	while (dbResultSet.next()) {       
		Element rowElem = doc.createElement("Row");       
		rowElem.setAttribute("rowNumber","$rowNumber")       
		resultSet.appendChild(rowElem);       
		rowNumber++;       
		for (int i = 1; i <= colCount; i++) {         
			String columnName = dbResultSetMetaData.getColumnName(i); 
			String columnTypeName = dbResultSetMetaData.getColumnTypeName(i);
			Object value=null;
			switch(columnTypeName){
				case 'uniqueidentifier': 
				case 'varbinary':  value=dbResultSet.getString(i);  break;
				case 'varchar':  value=dbResultSet.getString(i);  break;
				case 'nvarchar':  value=dbResultSet.getString(i);  break;
				case 'timestamp':
				case 'datetime': value=dbResultSet.getString(i);  break;
				case 'tinyint': 
				case 'smallint': 
				case 'int': value=dbResultSet.getInt(i);  break;
				case 'integer': value=dbResultSet.getInt(i);  break;
				case 'bit': value=dbResultSet.getInt(i);  break;
         			default: value=dbResultSet.getObject(i);       
			}
			Element node = doc.createElement(columnName);  
			if (value!=null) {      
				node.appendChild(doc.createTextNode(value.toString()));
			}        
			rowElem.appendChild(node);       
		 }     
	 }     
	 DOMSource domSource = new DOMSource(doc);     
	 TransformerFactory tf = TransformerFactory.newInstance();     
	 Transformer transformer = tf.newTransformer();     
	 transformer.setOutputProperty(OutputKeys.OMIT_XML_DECLARATION, "yes");     
	 transformer.setOutputProperty(OutputKeys.METHOD, "xml");     
	 transformer.setOutputProperty(OutputKeys.ENCODING, "UTF-8");//ISO-8859-1     
	 transformer.setOutputProperty(OutputKeys.INDENT, "yes");     
	 transformer.setOutputProperty("{http://xml.apache.org/xslt}indent-amount", "4");     
	 StringWriter sw = new StringWriter();     
	 StreamResult sr = new StreamResult(sw);     
	 transformer.transform(domSource, sr);     
	 log.info(" JDBC response as XML: " + sw.toString())
	 dbResultSet.close();  		 
	 return sw.toString();  
	}
}


