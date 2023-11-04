package Methods;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.sql.*;
import java.lang.String;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.*;
import java.util.Date;

import org.apache.commons.io.FileUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.poifs.filesystem.POIFSFileSystem;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxOptions;
import org.openqa.selenium.ie.InternetExplorerDriver;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedCondition;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.ITestResult;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeTest;
import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.MediaEntityBuilder;
import com.aventstack.extentreports.MediaEntityModelProvider;
import com.aventstack.extentreports.Status;
import com.aventstack.extentreports.reporter.ExtentHtmlReporter;
import PageRepository.ClaimDeclinePage;
import PageRepository.LoginPage;

public class GenericMethod{

	public static WebDriver driver=null;
	public ExtentReports report=null;
	public static ExtentTest test=null;
	public static WebDriverWait wait=null;
	static Sheet sheet=null;
	//ClaimDeclinePage Decline=new ClaimDeclinePage();
	//public static Logger log=LogManager.getLogger(TestRunner.class.getName()); 
    //static { BasicConfigurator.configure(); }
	public static By Spinner=By.tagName("mat-spinner");
    public static Logger log=LogManager.getLogger(GenericMethod.class);

	public void launchBrowser(String BrowserType)
	{
	   try {
			if(BrowserType.equalsIgnoreCase("chrome")) {
			     System.setProperty("webdriver.chrome.driver", System.getProperty("user.dir")+"\\src\\test\\java\\InputData\\chromedriver.exe");;
		    	 ChromeOptions option=new ChromeOptions();
				 Map<String, Object> prefs = new HashMap<String, Object>();
				 prefs.put("download.default_directory", System.getProperty("user.dir")+"\\src\\test\\java\\Downloads");
		    	 option.addArguments("start-maximized");
				 option.addArguments();
		    	 option.setExperimentalOption("excludeSwitches",Collections.singletonList("enable-automation"));
				 option.setExperimentalOption("prefs", prefs);
				 option.addArguments("enable-automation");
		    	 driver=new ChromeDriver(option);
		    	 wait=new WebDriverWait(driver, 5);
		    	 log.info(BrowserType+" is launched successfully");
		    }
	 		else if(BrowserType.equalsIgnoreCase("firefox")) {
			    System.setProperty("webdriver.gecko.drive", System.getProperty("user.dir")+"\\src\\test\\java\\InputData\\geckodriver.exe");
		    	//DesiredCapabilities capbilities=DesiredCapabilities.firefox();
		    	//capbilities.setCapability("marionette", true);			    
			    FirefoxOptions option=new FirefoxOptions();
			    option.setCapability("marionette", true);
			    driver=new FirefoxDriver(option);
				log.info(BrowserType+" is launched successfully");
		    }
			else if(BrowserType.equalsIgnoreCase("internet explorer")||BrowserType.equalsIgnoreCase("IE") ) {
		    	 driver=new InternetExplorerDriver();
		    	 log.info(BrowserType+" is launched successfully");
		    }
	   }
	   catch (Exception e) {
		// TODO: handle exception
	    //System.out.println("Exception occured due to "+e.getMessage()+"\n " +e.getStackTrace());
   	    log.error(BrowserType+" browser is failed to launch \n"+e.getMessage());
	  }
	}
	
	public void GoToURL(String url)
	{
	  try 
	  {
		driver.get(url);	
    	log.info(url+" is launched successfully");
	  } 
	  catch (Exception e) 
	  {
	      log.error(url+" failed to launch URL due to "+e.getMessage());
		  //System.out.println("Exception occured on launching URL "+url+" due to "+e.getStackTrace());
		  // TODO: handle exception
	  }	
		
	}
	
	public void Login(String username, String password)
	{
		try
		{
			driver.findElement(LoginPage.UsernameTextBox).sendKeys(username);
			driver.findElement(LoginPage.PasswordTextBox).sendKeys(password);
			driver.findElement(LoginPage.LoginBtn).click();
			log.info("Insureworx login is successful");
		}
		catch (Exception e) {
			// TODO: handle exception
			log.error("Failed to login to insureworx due to "+e.getMessage());
		}		
	}
	
	public void ClickElement(Locators locatorType, By LocatorValue)
	{
		try {
		  GetElement(locatorType, LocatorValue).click();			
		}
		catch(Exception e)
		{
			try {
			  JavascriptExecutor js=(JavascriptExecutor)driver;
			  js.executeScript("arguments[0].click();",GetElement(locatorType, LocatorValue));  
			}
			catch (Exception ex) {
				// TODO: handle exception
				log.error("Unable to click element due to "+ex.getMessage());
			}
		}		
	}
   	
	public void ClickElement(By wb)
	{
		try {
		  wait.until(ExpectedConditions.elementToBeClickable((By)wb)).click();
			log.info("Element "+wb+" is clicked successfully");

		}
		catch(Exception e) {
			try {
			  JavascriptExecutor js=(JavascriptExecutor)driver;
			  js.executeScript("arguments[0].click();",driver.findElement((By)wb));
			  log.info("Element "+wb+" is clicked successfully using javascript");
			}
			catch (Exception ex) {
				// TODO: handle exception
				log.error("Unable to click element"+wb+" due to "+ex.getMessage());
			}
		}		
	}
	
	public void ClickElement(WebElement wb)
	{
		try {
			wait.until(ExpectedConditions.elementToBeClickable(wb)).click();
			log.info("Element "+wb+" is clicked successfully");
		}
		catch(Exception e) {
			try {
			  JavascriptExecutor js=(JavascriptExecutor)driver;
			  js.executeScript("arguments[0].click();",wb);
				log.info("Element "+wb+" is clicked successfully using javascript");
			}
			catch (Exception ex) {
				log.error("Failed to click element "+wb+" due to "+ex.getMessage());
			}
		}		
	}
	
	public void SendText(Locators LocatorType,By LocatorValue,String text)
	{
		  WebElement wb=null;
		   try {
			GetElement(LocatorType, LocatorValue).sendKeys(text);
			log.info("Text "+text+" has been sent successfully");
		   }
		   catch (Exception e) {
		      // TODO: handle exception
			 try{
				   wb=GetElement(LocatorType, LocatorValue);
				   JavascriptExecutor js=(JavascriptExecutor)driver;
				   js.executeScript("arguments[0].value = '" + text + "';", wb);
				   log.info("Text"+ text+" has been sent successfully using javascript");

			 }catch (Exception ex) {
				 log.error("Unable to send text due to "+ex.getMessage());
			 }
	       }
	}

	public void MoveAndClick(WebElement wb)
	{
		try {
			Actions action=new Actions(driver);
			action.moveToElement(wb).click().build().perform();
			log.info("Move and Click performed successfully");
		}
		catch(Exception e) {
           log.error(e.getMessage());
		}
	}

	public void SendText(By Locator,String text)
	{
		  WebElement wb=null;
		   try {
			 wb=driver.findElement(Locator);
			 wb.clear();
			 wb.click();
			 wb.sendKeys(text);
			 log.info("Text "+text.toUpperCase()+" has been sent successfully");
		   } 
		   catch (Exception e)
		   {

			   try {
				   // TODO: handle exception
				   wb.clear();
				   JavascriptExecutor js = (JavascriptExecutor) driver;
				   js.executeScript("arguments[0].value = '" + text + "';", wb);
				   log.info("Text "+text+" has been sent successfully using javascript");
			   }
			   catch(Exception e1) {
				   log.error(e1.getMessage());
			   }
	       }

	}

	public String RetrieveMonthNameByNumber(int  MonthNumber)
	{
		String getMonthName=null;
		try{
			switch (MonthNumber)
			{
				case 1 : getMonthName= "JAN";break;
				case 2 : getMonthName= "FEB";break;
				case 3 : getMonthName= "MAR";break;
				case 4 : getMonthName= "APR";break;
				case 5 : getMonthName= "MAY";break;
				case 6 : getMonthName= "JUN";break;
				case 7 : getMonthName= "JUL";break;
				case 8 : getMonthName= "AUG";break;
				case 9 : getMonthName= "SEP";break;
				case 10 :getMonthName= "OCT";break;
				case 11 :getMonthName= "NOV";break;
				case 12 :getMonthName= "DEC";break;
			}
		}
		catch (Exception e) {
			log.error(e.getMessage());
		}
     return getMonthName;
	}

	public void SendText(WebElement Locator,String text)
	{
		WebElement wb=null;
		try
		{
			wb=Locator;
			wb.clear();
			wb.click();
			wb.sendKeys(text);
		}
		catch (Exception e)
		{
			try {
				// TODO: handle exception
				wb.clear();
				JavascriptExecutor js = (JavascriptExecutor) driver;
				js.executeScript("arguments[0].value = '" + text + "';", wb);
				log.info(text+" has been sent successfully using javascript");

			}
			catch(Exception ex) {
				log.error("Unable to send text due to "+ex.getMessage());

			}
		}

	}


	public void EnterValue(WebElement LocatorValue,String text)
	{
		   try
		   {
			   LocatorValue.clear();
			   LocatorValue.sendKeys(text);
			   log.info(text+" has been sent successfully");
		   } 
		   catch (Exception e) 
		   {
		      // TODO: handle exception
			  try{
				  JavascriptExecutor js=(JavascriptExecutor)driver;
			      js.executeScript("arguments[0].value = '" + text + "';", LocatorValue);
			  }
			  catch (Exception ex) {
			  log.error("Unable to send text due to "+ex.getMessage());
			  }

	       }
	}

	public void EnterValue(By LocatorValue,String text)
	{
		try
		{
			driver.findElement(LocatorValue).clear();
			driver.findElement(LocatorValue).sendKeys(text);
			log.info(text+" has been sent successfully using javascript");

		}
		catch (Exception e)
		{
			// TODO: handle exception
			try{
				JavascriptExecutor js=(JavascriptExecutor)driver;
			    js.executeScript("arguments[0].value = '" + text + "';", LocatorValue);}
			catch (Exception ex){
				log.error("Unable to send text "+text+" due to "+ex.getMessage());
			}
		}
	}

	public String GetText(By LocatorValue)
	{
           String str=null;      
		   try 
		   {
			   str=driver.findElement(LocatorValue).getText();
			   log.info("Text "+str+" has been fetched successfully");
		   }
		   catch (Exception e) {
		      //TODO: handle exception
			   log.error("Text has not been fetched due to "+e.getMessage());
		    }
		return str;
	}

	public String GetAttributeValue(By LocatorValue, String Value)
	{
           String str=null;      
		   try 
		   {
			  str=driver.findElement(LocatorValue).getAttribute(Value);
			  log.info("Text "+str+" has been fetched successfully using getAttribute");
		   } 
		   catch (Exception e) {
		      //TODO: handle exception
			   log.error(e.getMessage());
		    }	
		return str;
	}

	public String GetAttributeValue(WebElement LocatorValue, String Value)
	{
		String str=null;
		try {
			str=LocatorValue.getAttribute(Value);
			log.info("Text "+str+" has been fetched successfully using getAttribute");
		}
		catch (Exception e) {
			//TODO: handle exception
			log.error(e.getMessage());
		}
		return str;
	}

	
	public boolean VerifyText(Locators LocatorType,By LocatorValue, String TextTobeVerify)
	{
           String str=null;      
		   try 
		   {
			  str=GetElement( LocatorType, LocatorValue).getText();
			  if(str.equals(TextTobeVerify))			  
				 return true;
			       else
				     return false;
			  
		   } 
		   catch (Exception e) 
		   {
		      //TODO: handle exception
			   e.printStackTrace();	
			   return false;
		    }	
	}

	
	public WebElement GetElement(Locators LocatorType, By LocatorPath)
	{
		WebElement wb=null;
		String Locator=null;
	    try {
	       Locator=LocatorType.toString().toLowerCase();
          switch(Locator)
          {
            case "xpath"       : wb= driver.findElement(LocatorPath);
            break;
            case "cssselector" : wb= driver.findElement(LocatorPath);
            break;
            case "id"          : wb= driver.findElement(LocatorPath);
            break;
            case "name"        : wb= driver.findElement(LocatorPath);
            break;
            case "classname"   : wb= driver.findElement(LocatorPath);
            break;
            case "linktext"    : wb= driver.findElement(LocatorPath);
            break;
            case "partiallinktext" : wb= driver.findElement(LocatorPath);
            break;
            case "tagname"     : wb= driver.findElement(LocatorPath);
            break;

          }
		  log.info("Element "+wb+" found successfully using "+Locator);
		} catch (Exception e) {
			// TODO: handle exception
			log.error("Element "+wb+" not found  using "+Locator+" due to "+e.getMessage());
		}
		return wb;
	}

	public WebElement GetElement(By LocatorPath)
	{
		WebElement wb=null;
		try {
			wb=driver.findElement(LocatorPath);
			log.info("Element "+wb+" found successfully");

		} catch (Exception e) {
			// TODO: handle exception
			log.error("Failed to find element due to "+e.getMessage());
		}
		return wb;
	}

	public WebElement GetElement(WebElement LocatorPath)
	{
		WebElement wb=null;
		try {
			wb=driver.findElement((By) LocatorPath);
			log.info("Element "+wb+" found successfully");
		} catch (Exception e) {
			// TODO: handle exception
			log.error("Failed to find element due to "+e.getMessage());

		}
		return wb;
	}

	public void ScollToElement(By wb)
	{
		try {
			JavascriptExecutor js=(JavascriptExecutor)driver;
			js.executeScript("arguments[0].scrollIntoView(true);", driver.findElement(wb));
			log.info("Scrolled performed successfully");
		}
		catch (Exception e) {
			log.error("Failed to scroll to view element due to "+e.getMessage());
		}
	}
	public void ScollToElement(WebElement wb)
	{
		try {
			JavascriptExecutor js=(JavascriptExecutor)driver;
			js.executeScript("arguments[0].scrollIntoView(true);", wb);
			log.info("Scrolled performed successfully");

		}
		catch (Exception e) {
			log.error("Failed to scroll to view element due to "+e.getMessage());
		}
	}
	public void ScollandClickUsingJS(By wb)
	{
		JavascriptExecutor js=(JavascriptExecutor)driver;
		try {
			js.executeScript("arguments[0].scrollIntoView(true);", wb);
			js.executeScript("arguments[0].click();", wb);
			log.info("Scrolled and Clicked performed successfully");

		}
		catch (Exception e) {
			log.error("Failed to scroll and click element due to "+e.getMessage());
		}
	}

	public String getTextUsingJs( By wb) {
		String textFieldValue = null;
		try{
		WaitforVisibilityOFElement(wb);
		WebElement element=driver.findElement(wb);
		textFieldValue = (String) ((JavascriptExecutor) driver).executeScript("return arguments[0].value", element);
		log.info("Text has been fetch successfully using javascript");
		}catch (Exception e) {
			log.error("Failed to get text using javascript due to "+e.getMessage());
		}
		return textFieldValue;
	}

	public By WaitforDropdownItemVisibility(String Value)
	{
		By value=null;
		try { value=By.xpath("//mat-option//span[contains(text(),'" + Value + "')]");
		}
		catch (Exception e) {
			log.error(e.getMessage());}
		return  value;
	}

	public String getTextUsingJs( WebElement wb) {
		String textFieldValue = null;
		try{
			textFieldValue = (String) ((JavascriptExecutor) driver).executeScript("return arguments[0].value", wb);
			log.info("Text has been fetch successfully using javascript");

		}catch (Exception e){
			log.error("Failed to get text using javascript due to "+e.getMessage());

		}
		return textFieldValue;
	}

    public Boolean VerifyTextFromList(By locators, String TextToBeVerified)
    {
    	Boolean flag=false;
    	try
    	{
    		List<WebElement> Values=driver.findElements(locators);
    		for(int i=0;i<Values.size();i++)
    		{
    			String text=Values.get(i).getText();
    		      if(text.equals(TextToBeVerified)) {
					  flag=true;
					  break; }
    		}
    	}
    	catch (Exception e) {
			// TODO: handle exception
			log.error("Failed to verify text from the List due to "+e.getMessage());
		}
    	return flag;
    }


	public enum Locators
	{
		xpath,
		cssselector,
		id,
		name,
		classname,
		linktext,
		partiallinktext,
		tagname
	}


	public void close()
	{
		try {
		  driver.close();
		  log.info("driver is closed successfully");

		} catch (Exception e) {
			// TODO: handle exception
	    	log.error("Failed to close browser due to "+e.getMessage());

		}
	}

	@BeforeTest
	public void ReportInitialization()
	{
		try {
			report=new ExtentReports();
		    String path=System.getProperty("user.dir")+"\\Report\\regression.html";
			ExtentHtmlReporter htmlreport=new ExtentHtmlReporter(path);
			report.attachReporter(htmlreport);
			log.info("Extend report initialized successfully");
		} catch (Exception e) {
			// TODO: handle exception
			log.error("Extend report failed to initialized due to "+e.getMessage());
		}
		
	}
	
	@AfterMethod
	public void CaptureReportResult(ITestResult result)
	{
		try {
			String msg=result.getMethod().getMethodName();			
			   if(result.getStatus()==ITestResult.FAILURE)
			   {
				 test.log(Status.FAIL,msg);   
			   }
			   else if(result.getStatus()==ITestResult.SUCCESS)
			   {
				 test.log(Status.PASS,msg);   
			   }
			   //close();
			       	
		} catch (Exception e) {
			// TODO: handle exception
           log.error("Failed to captured screenshot due to "+e.getMessage());
		}
	
	}
	
	public void LogPass(String msg)
	{
	   test.log(Status.PASS,msg);			
	}
	public void LogFail(String msg)
	{
	   test.log(Status.FAIL,msg);			
	}
	public void LogPass(String screenshotName,String msg)
	{
		try{
			   String Capturescreen=screenshots(screenshotName);
			   MediaEntityModelProvider Media =MediaEntityBuilder.createScreenCaptureFromPath(Capturescreen).build(); 
			   test.log(Status.PASS,msg, Media);			
	
		}catch (Exception e) {
			// TODO: handle exception
			log.error(e.getMessage());
		}		
	}
	
	
	public void LogFail(String screenshotName,String msg)
	{
		try{
			   String Capturescreen=screenshots(screenshotName);
			   MediaEntityModelProvider Media =MediaEntityBuilder.createScreenCaptureFromPath(Capturescreen).build(); 
			   test.log(Status.FAIL,msg, Media);			
	
		}catch (Exception e) {
			// TODO: handle exception
			log.error(e.getMessage());
		}
	}
	
	public static String screenshots( String ScreenshotName)
	{
		String destination=null;
		try {
			  String date=new SimpleDateFormat("yyyy-MM-dd-HHmmss").format(new Date());
			  TakesScreenshot ts=(TakesScreenshot)driver;
			  File source=ts.getScreenshotAs(OutputType.FILE);
			  destination = System.getProperty("user.dir") + "//Report//Screenshots//"+ScreenshotName+"_"+date+".png";
			  File finalDestination = new File(destination);
			  FileUtils.copyFile(source, finalDestination);
		} catch (Exception e) {
			// TODO: handle exception
			log.error(e.getMessage());
		}
	  return destination;
	}
	
	public void DeleteExistingScreenshots()
	{
		try 
		{
		  String destination = System.getProperty("user.dir") + "\\Report\\Screenshots\\";
		  File file=new File(destination);
		  FileUtils.cleanDirectory(file);
		  log.info("Existing captured screenshots deleted successfully");
		} 
		catch (Exception e) {
		  // TODO: handle exception
			log.error("Failed to delete existing screenshots due to "+e.getMessage());
		}		
	}

	public void DeleteExistingLogContent()
	{
		try {
			String destination = System.getProperty("user.dir") + "\\Logs\\Logfile.log";
            File file=new File(destination);
			String val=file.getName();
			if(file.exists()){
			file.deleteOnExit();}
		}
		catch (Exception e) {
			log.error(e.getMessage());// TODO: handle exception
		}
	}


	
	public void ClearWorkItemFromDB(String DBDriver,String DBConnection,String PolicyNumber ,String Username, String Passowrd)
	{
		Connection conn=null;
        java.sql.Statement stmt=null;
		try {
			Class.forName(DBDriver);
			try {
			    conn=DriverManager.getConnection(DBConnection,Username, Passowrd);
			    stmt=conn.createStatement();
				String Policy=AppendZerotoPolicy(PolicyNumber);
				String query="UPDATE WORKFLOW_ITEM SET POLICY_NUMBER = 'FI4' WHERE POLICY_NUMBER ='"+Policy+"'";
				stmt.execute(query);
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				 log.error("Error occurred on cleaning workitem due to "+e.getMessage());
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			log.error("Error occurred on cleaning workitem due to "+e.getMessage());
		}      
	}

	public void DeleteClaimsFromDB(String DBDriver,String DBConnection,String PolicyNumber ,String Username, String Passowrd)
	{
		Connection conn=null;
		java.sql.Statement stmt=null;
		try {
			Class.forName(DBDriver);
			try {
				conn=DriverManager.getConnection(DBConnection,Username, Passowrd);
				stmt=conn.createStatement();
				String ClaimIDQuery="Select clm.id from funins_p.fipolmpf pol inner join funins_p.ficlaimpf clm on pol.id = clm.policy_id where pol. policyno = '"+PolicyNumber+"'";
				ResultSet results=stmt.executeQuery(ClaimIDQuery);
				ArrayList<Integer> claimID=new ArrayList<Integer>();
				while (results.next()) {
                    claimID.add(results.getInt("ID"));
				}
				for (int i=0;i<claimID.size();i++) {
					stmt.executeUpdate("delete from funins_p.ficlaimpf where ID ="+claimID.get(i)+"");
				}
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				log.error("Error occurred in deleting claim "+e.getMessage());
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			log.error("Error occurred in deleting claim "+e.getStackTrace());
		}
	}

	public void UpdatePolicyStatus(String DBDriver,String DBConnection,String PolicyNumber,String Username, String Passowrd, int PolicyStatus)
	{
		Connection conn=null;
		java.sql.Statement stmt=null;
		try {
			Class.forName(DBDriver);
			try {

				/* 07 - Lapse
				   06 - Active
				   09 - Cancel
				   10 - Deceased */

				conn=DriverManager.getConnection(DBConnection,Username, Passowrd);
				stmt=conn.createStatement();
				String ClaimIDQuery="SELECT * FROM FUNINS_P.FIPOLMPF WHERE Policyno='"+PolicyNumber+"'";
				ResultSet resultSet=stmt.executeQuery(ClaimIDQuery);
                if(resultSet.next()){
					int ID=resultSet.getInt("ID");
					stmt.execute("Update FUNINS_P.FIPOLMPF set STATUS_ID="+PolicyStatus+" where ID ="+ID+"");
				}
				log.info("Policy status updated successfully");
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				log.error("Failed to update policy status due to "+e.getMessage());
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			log.error("Failed to update policy status due to "+e.getMessage());
		}
	}


	public String AppendZerotoPolicy(String Policy)
	{
		String s=new String();
		try{
			char [] chars=Policy.toCharArray();
			StringBuffer str=new StringBuffer();
			for(int i=0;i<chars.length;i++) {
				if(chars[i]=='I'){
					s=str.append(chars[i]+"0000").toString();
				}
				else {
					s=str.append(chars[i]).toString();
				}
			}
		}
		catch (Exception e) {
			log.error("Failed to append zero to policy "+e.getMessage());
		}
		return s;
	}

	public Object [][] ReadExcel(String ExcelPath,String SheetName) throws Exception
	{
		Object [][] GetTestCase=null;
		Object [][] GetTestdata=null;
		String [][] GetTestdata2=null;
		try
        {
	        File file=new File(ExcelPath);		        
	        FileInputStream fis=new FileInputStream(file);
	        Workbook workbook;
	        
	        if(ExcelPath.contains(".xlsx"))
	        	workbook=new XSSFWorkbook(ExcelPath);
	        else {
	        	InputStream input=new FileInputStream(ExcelPath);
	        	workbook=new HSSFWorkbook(new POIFSFileSystem(input));
	        }
	        sheet=workbook.getSheetAt(0);
	        System.out.println(sheet.toString());
	        int TC_rowcount=sheet.getLastRowNum();
	        Row TC_row=sheet.getRow(0);
	        int TC_ColumnCount=TC_row.getLastCellNum();
			GetTestdata=new String[TC_rowcount][TC_ColumnCount];
	        for(int i=1;i<TC_rowcount+1;i++)
	        {
				TC_row=sheet.getRow(i);
				GetTestdata[i-1][1]=TC_row.getCell(1).toString().trim();
                  if(GetTestdata[i-1][1].equals("Y")) {
					  GetTestdata[i-1][1]=TC_row.getCell(0).toString().trim();
					  System.out.println(GetTestdata[i-1][1]);
                  }
	        }
	        sheet=workbook.getSheet("TestData");
	        int TestData_rowcount=sheet.getLastRowNum();
			Row TestData_rows=sheet.getRow(0);
			int TC_ColumnCounts=TestData_rows.getLastCellNum();
			//GetTestdata2=new String[TC_rowcount][TC_ColumnCount];
			GetTestdata2=new String[TestData_rowcount][TC_ColumnCounts];
			//GetTestdata2=new String[count][TC_ColumnCounts];

			    for(int i=1;i<TestData_rowcount+1;i++) {
					Row TestData_row = sheet.getRow(i);
					String TestCaseID=TestData_row.getCell(0).toString().trim();
					System.out.println(GetTestdata[i-1][1]+"\t"+TestCaseID);
					if(GetTestdata[i-1][1].equals(TestCaseID))
					{
					   int cols = TestData_row.getLastCellNum();
					   //GetTestdata2 = new String[TestData_rowcount][cols];
					   for (int j = 1; j < cols; j++) {
						   Cell cell=TestData_row.getCell(j);
						   GetTestdata2[i-1][j-1]=cell.getStringCellValue();
						   System.out.println(GetTestdata2[i-1][j-1]);
						     //GetTestdata2[i - 1][j - 1] = TestData_row.getCell(j).toString().trim();
							 //System.out.println(GetTestdata2[i - 1][j - 1]);
							}
					   }
			        }

        }
        catch(Exception e)
        {
          e.printStackTrace();	
        }
        return GetTestdata2;
	}

	public static  List<Map<String,String>> ReadExcelSheet(String ExcelPath,String SheetName) throws Exception
	{
		List<Map<String,String>> list=null;
		try
		{
			File file=new File(ExcelPath);
			FileInputStream fis=new FileInputStream(file);
			Workbook workbook;

			if(ExcelPath.contains(".xlsx"))
				workbook=new XSSFWorkbook(ExcelPath);
			else {
				InputStream input=new FileInputStream(ExcelPath);
				workbook=new HSSFWorkbook(new POIFSFileSystem(input));
			}
			sheet=workbook.getSheet(SheetName);
			int lastrow=sheet.getLastRowNum();
			int lastColumn=sheet.getRow(0).getLastCellNum();

			Map<String,String> map=null;
            list=new ArrayList<>();

			for(int i=1;i<=lastrow;i++) {
				map=new LinkedHashMap<>();
				for(int j=0;j<lastColumn;j++){
                    String key=sheet.getRow(0).getCell(j).getStringCellValue();
					String value=sheet.getRow(i).getCell(j).getStringCellValue();
					map.put(key,value);
				}
				list.add(map);
			}
		}
		catch(Exception e) {
			log.error("Failed to read data from excel due to "+e.getStackTrace());
		}
		return list;
	}

	public void WaitforVisibilityOFElement(By locator, int waitTime)
	{
		try {
			WebDriverWait wait=new WebDriverWait(driver, waitTime);
			wait.until(ExpectedConditions.visibilityOfAllElementsLocatedBy(locator));
		}
		catch(Exception e) {
			log.error(e.getMessage());
		}
	}

	public WebElement WaitforVisibilityOFElement(WebElement locator)
	{
		WebElement element=null;
		try {
			WebDriverWait wait=new WebDriverWait(driver, 20);
			element=wait.until(ExpectedConditions.visibilityOf(locator));
		}
		catch(Exception e) {
			log.error(e.getMessage());
		}
		return element;
	}


	public String WaitforVisibilityOFElement(By locator)
	{
	  String str=null;
	  try {
        WebDriverWait wait=new WebDriverWait(driver, 20);
        List<WebElement> wb=wait.until(ExpectedConditions.visibilityOfAllElementsLocatedBy(locator));
		str=wb.get(0).getText();
	  }
	  catch(Exception e) {
	    log.error(e.getMessage());
	  }
	  return  str;
	}



	public void WaitforInvisibilityOfElement(By locator, int waitTime)
	{
		try {
			WebDriverWait wait=new WebDriverWait(driver, waitTime);
			wait.until(ExpectedConditions.invisibilityOfElementLocated(locator));
		}
		catch(Exception e) {
			log.error(e.getMessage());
		}
	}
	public void WaitforInvisibilityOfElement(By locator)
	{
		try {
			WebDriverWait wait=new WebDriverWait(driver, 20);
			wait.until(ExpectedConditions.invisibilityOfElementLocated(locator));
		}
		catch(Exception e) {
			log.error(e.getMessage());
		}
	}

	public void WaitforFrameLoad(int Frame, int waitTime)
	{
	  try {
        WebDriverWait wait=new WebDriverWait(driver, waitTime);
        wait.until(ExpectedConditions.frameToBeAvailableAndSwitchToIt(Frame));
	  }
	  catch(Exception e) {
		  log.error(e.getMessage());
	  }
	}
	
	public void WaitforFrameLoad(WebElement Frame, int waitTime)
	{
	  try {
        WebDriverWait wait=new WebDriverWait(driver, waitTime);
        wait.until(ExpectedConditions.frameToBeAvailableAndSwitchToIt(Frame));
	  }
	  catch(Exception e) {
		  log.error(e.getMessage());
	  }
	}
	
	
	public void MoveToOptions(String MainMenu, String SubMenu)
	{
		try 
		{
	      WebElement MainMenuOption=driver.findElement(By.xpath("//span[text()='"+MainMenu+"']"));	
		  WebElement SubMenuOption=driver.findElement(By.xpath("//span[text()='"+SubMenu+"']"));

	      Actions action=new Actions(driver);
		  action.moveToElement(MainMenuOption).perform();
		  action.moveToElement(SubMenuOption).click().perform();
		}
		catch (Exception e) {
		  // TODO: handle exception
			log.error(e.getMessage());
		}
	}
    
	public void SwitchToWindow(String WindowTitle)
	{
        try 
        {
			String str=driver.getWindowHandle();
            WebDriverWait wait=new WebDriverWait(driver,10);
			wait.until(ExpectedConditions.numberOfWindowsToBe(2));
	    	Set<String> s1=driver.getWindowHandles();
	        Iterator<String> list=s1.iterator();
	        while(list.hasNext())
	        {
	        	String child=list.next();
	            driver.switchTo().window(child);
	        	String currentwindow=driver.getTitle();  
	        	System.out.println(currentwindow);
	        	if(currentwindow.contains(WindowTitle)) {
		          log.info("Successfully Switch to Winodw "+WindowTitle);
	              break;
	        	}
	        }
        }
        catch (Exception e) {
			log.error(e.getMessage());
        }
	}

	public void waitforPageload()
	{
		try{
			List<WebElement> spinners=driver.findElements(Spinner);
			wait.until(ExpectedConditions.invisibilityOfAllElements(spinners));
		}catch (Exception e) {
			log.error(e.getMessage());
		}
	}

	public void waitforpageload(int waitTime)
	{
	  try {
	    wait=new WebDriverWait(driver, waitTime);		    
	    wait.until((ExpectedCondition<Boolean>)driver1 ->((JavascriptExecutor)driver).executeScript("return document.readyState").equals("complete"));
	  }
	  catch (Exception e) {
		  log.error(e.getMessage());
	  }
	}
	
	public void SelectDateFromCalender(String date)
	{
	  try {
		  WebElement wb=driver.findElement(By.xpath("//input[@name='deceasedDateOfBirthControl']"));
		  wb.sendKeys(date);
	  }
	  catch (Exception e) {
		// TODO: handle exception
		  log.error(e.getMessage());
	  }
	}
	
	public void SelectFinalOutcomeAndReason(String DropdownType, String Value)
	{
	  try {		  
		  if(DropdownType.equalsIgnoreCase("Decision"))
		    driver.findElement(ClaimDeclinePage.DeclineDecision).click();
		     else if (DropdownType.contains("Reason"))	
			   driver.findElement(ClaimDeclinePage.DeclineDecisionReason).click();
             
		  WebElement wb=wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//span[contains(text(),'"+Value+"')]")));		  
		  wb.click();
		  log.info("Final Outcome option selected as "+ Value);
	  }
	  catch (Exception e) {
		// TODO: handle exception
		  log.error(e.getMessage());
	  }
	}

	public int[] addTime(int min, int sec)
	{
		int [] value=new int[2];
		try{
			if(sec>60) {
              sec=00;
			  min++;
			}
			if(min>60) {
				min=00;
			}
			value[0]=min;value[1]=sec;
		}
		catch (Exception e)
		{

		}
       return value;
	}
	public int[] IncreaseMins(int AddedMinutes)
	{
		int [] value=new int[3];
		//System.out.println(Hour+":"+min+":"+sec);
		try{
            //String Hr=String.valueOf(Hour);
			//String Min=String.valueOf(min);
			//String Sec=String.valueOf(sec);

			//String Timeformat=Hr+":"+Min+":"+Sec;

			LocalTime localtime=LocalTime.now();
			try {
				DateTimeFormatter formatter=DateTimeFormatter.ofPattern("HH:mm:ss");
				String getTime=localtime.format(formatter);
				localtime=LocalTime.parse(getTime, formatter);
			}
			catch (Exception e) {
				System.out.println(e.getStackTrace()+"\n"+e.getMessage());
			}
			int Minute=AddedMinutes;
			LocalTime result=localtime.plusMinutes(Minute);
			String val=result.format(DateTimeFormatter.ofPattern("HH:mm:ss")) ;
			System.out.println("after increament "+val);
            String [] Values =val.split(":");

			value[0]=Integer.parseInt(Values[0]);
			value[1]=Integer.parseInt(Values[1]);
			value[2]=Integer.parseInt(Values[2]);

		}
		catch (Exception e) {
			log.error(e.getMessage());
		}
		return value;
	}


	public long [] TimeSubstraction(String Time)
	{
		long [] time=new long[Time.length()];
		try{
			DateTimeFormatter datetime=DateTimeFormatter.ofPattern("HH:mm:ss");
			LocalDateTime LocalTime=LocalDateTime.now();
			String time1=datetime.format(LocalTime);

			// Creating a SimpleDateFormat object
			// to parse time in the format HH:MM:SS
			SimpleDateFormat simpleDateFormat
					= new SimpleDateFormat("HH:mm:ss");

			// Parsing the Time Period
			Date date1 = simpleDateFormat.parse(time1);
			Date date2 = simpleDateFormat.parse(Time);

			// Calculating the difference in milliseconds
			long differenceInMilliSeconds
					= Math.abs(date2.getTime() - date1.getTime());

			// Calculating the difference in Hours
			long differenceInHours
					= (differenceInMilliSeconds / (60 * 60 * 1000))
					% 24;

			// Calculating the difference in Minutes
			long differenceInMinutes
					= (differenceInMilliSeconds / (60 * 1000)) % 60;

			// Calculating the difference in Seconds
			long differenceInSeconds
					= (differenceInMilliSeconds / 1000) % 60;

			// Printing the answer
			System.out.println(
					"Difference is " + differenceInHours + " hours "
							+ differenceInMinutes + " minutes "
							+ differenceInSeconds + " Seconds. ");

            time[0]=differenceInHours;
			time[1]=differenceInMinutes;
			time[2]=differenceInSeconds;
		}
		catch (Exception e) {
			log.error(e.getMessage());
		}
		return time;
	}

}
