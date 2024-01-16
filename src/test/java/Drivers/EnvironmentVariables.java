package Drivers;

  public class EnvironmentVariables {

	public final static String DriverPath=System.getProperty("user.dir")+"\\src\\test\\java\\InputData\\chromedriver.exe";
	public final static String LogFilePath=System.getProperty("user.dir")+"Logs\\";
	public final static String ChromDriver="webdriver.chrome.driver";
	public final static String FirefoxDriver="webdriver.gecko.drive";
	public final static String WorkBookName="TestData.xlsx";
	public final static String ExcelsheetPath=System.getProperty("user.dir")+"\\src\\test\\java\\InputData\\"+WorkBookName;
	public final static String DownloadFilePath=System.getProperty("user.dir")+"\\src\\test\\java\\Downloads\\";
	public final static String FlowURLForPre="http://lfe-rbflpreap01.fnb.co.za:8080/flow/pages/personalqueue/pageFragment/myWorkItem.xhtml";
	public final static String FlowURLForInt="http://lfe-rbflintap01.fnb.co.za:8080/flow/nofilter/security/login.xhtml";
	public final static String InsureworxPre="http://lfe-rbflpreap01.fnb.co.za:8084/";
	public final static String InsureworxInt="http://lfe-rbflintap01.fnb.co.za:8084/";

  }
