package PageRepository;

import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.*;
import java.util.concurrent.TimeUnit;

import org.apache.poi.util.StringUtil;
import org.apache.xmlbeans.impl.xb.xsdschema.Public;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.remote.server.handler.ClickElement;
import org.openqa.selenium.remote.server.handler.ExecuteScript;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;


import Methods.GenericMethod;

public class HomePage extends GenericMethod {

	GenericMethod gm=new GenericMethod();
	WebElement wb=null;
	WebDriverWait wait=null;

    public static String TeamTask="Team Tasks";     
    public static String MyTask="My Tasks";     
    public static String SubmitClaimMsg="New claim submitted";
	public static String SubmitFormNotification="Task submitted successfully";
	public static String DocumentRequiredForm="Documents Required";
	public static String MedicalPractitionerForm="Medical Practitioner Validation";
	public static String FuneralUndertakerFrom="Funeral Undertaker Validation";
	public static String InformantFrom="Informant Validation";
	public static String InndunaFrom="Induna/Traditional Leader Validation";

	public static By NewTask=By.xpath("//mat-label[contains(text(),'New Task')]");
    public static By MyTasksTab=By.xpath("//*[@id='mat-tab-label-2-0']");
    public static By TeamTasksTab=By.xpath("//*[@id='mat-tab-label-2-1']");
    public static By RefreshIcon=By.xpath("//mat-icon[text()=' refresh ']");
    public static By ClaimSubmitBtn=By.xpath("//mat-label[contains(text(),'Submit')]"); 
    public static By ClaimTaskBtn=By.xpath("//mat-label[contains(text(),'Claim Task')]");
	public static By ClaimSubmitAcknowledge=By.xpath("//span[text()='New claim submitted']");
	public static By OperationsDesk=By.xpath("//mat-label[contains(text(),'Operations Desk')]");

	//public static By AdditionalFilterTask=By.xpath("//button//mat-icon[contains(text(),'filter_list')]");

	public static By AdditionalFilterTask=By.xpath("//button//mat-icon[text()=' filter_list ']");
	//public static By FilterPolicySeachBox=By.xpath("//mat-grid-tile[3]//input[@name='policyNumber']");
	public static By FilterPolicySeachBox=By.xpath("//input[@name='policyNumber']");
	public static By FilterProductSeachBox=By.xpath("//input[@name='product']");
	public static By ApplyFilterButton=By.xpath("//button[@mattooltip='Apply task filter(s)']");
	//public static By ApplyFilterButton=By.xpath("//button//mat-label[text()=' Apply ']");
	public static By ClearAppliedFilter=By.xpath("//mat-icon[contains(text(),'filter_list_off')]");
	public static By CancelAppliedFilter=By.xpath("//button//mat-label[contains(text(),'Cancel')]");
	//public static By FilterAppliedIndicator=By.xpath("//span[@id='mat-badge-content-6']");
	public static By FilterAppliedIndicator=By.xpath("//button[@mattooltip='Additional filters']//span[@id='mat-badge-content-6']");
	public static By FilterPopupScreen=By.xpath("//*[@class='cdk-overlay-pane']");	
	public static By GetStationName=By.xpath("//*[@id='mat-tab-content-1-0']//mat-card/mat-card-content//span[3]");
	public static By ExpandDeathValidation=By.xpath("//mat-label[contains(text(),'Death Validation')]");
	public static By ClaimTaskNotification=By.xpath("//span[text()='Task claimed successfully']");
	public static By SubmitClaimNotification=By.xpath("//span[text()='Task submitted successfully']");
	public static By AdditionalComments=By.xpath("//textarea[@name='additionalComments']");
	public static By SelectPolicyFromList=By.xpath("//mat-icon[contains(text(),'open_in_browser')]");
	/*  public WebElement SelectPolicyFromList(int index)
		{ return driver.findElement(By.xpath("//mat-card['"+index+"']/mat-card-content//div[1]/mat-label[3]"));} */

	public static By OperationalDesk=By.xpath("//mat-select[@name='operationsDesk']");
	public static By RequestChannel=By.xpath("//mat-select[@name='requestChannel']");
	public static By ProcessCategory=By.xpath("//mat-select[@name='processCategory']");
	public static By SubProcessCategory=By.xpath("//mat-select[@name='subProcessCategory']");
	public static By SubmitClaim=By.xpath("//mat-label[contains(text(),'Submit')]");

	//-----------------Upload elements------------------------------
	public static By CRM_Button=By.xpath("//*[contains(text(),'CRM')]");
	public static By DocumentsTab=By.xpath("//div[text()='Documents']");
	public static By NewDocumentsTab=By.xpath("//div[text()='New Documents']");
	public static By PreviewIcon=By.xpath("//mat-icon[contains(text(),'preview')]");
	public static By ImageViewer=By.xpath("pdf-viewer");
	public static By UploadedFiles=By.xpath("//mat-label[text()='Uploaded Files:']");
	public static By UploadTimeStamp=By.xpath("//app-file-card//span[contains(text(),'Indexed Date')]");
	public static By PDFViewerPanel=By.xpath("//pdf-viewer");
	public static By PreLoader=By.xpath("//mat-spinner");
	public static By ClosePDFDoc=By.xpath("//mat-dialog-container//mat-icon[text()=' close ']");
	public static By DownloadPDF=By.xpath("//mat-icon[contains(text(),'file_download')]");

	public static By MediacalFileUpload=By.xpath("//app-medical-validations//mat-icon[contains(text(),'file_upload')]");
	public static By ForensicFileUpload=By.xpath("//app-forensic-validations//mat-icon[contains(text(),'file_upload')]");
	public static By UndertakerFileUpload=By.xpath("//app-undertaker-validations//mat-icon[contains(text(),'file_upload')]");
	public static By InformantFileUpload=By.xpath("//app-informant-validations//mat-icon[contains(text(),'file_upload')]");
	public static By SAPSFileUpload=By.xpath("//app-saps-validations//mat-icon[contains(text(),'file_upload')]");
	public static By RelationFileUpload=By.xpath("//app-relationship-validations//mat-icon[contains(text(),'file_upload')]");
	public static By IndunaFileUpload=By.xpath("//app-induna-validations//mat-icon[contains(text(),'file_upload')]");

	public WebElement InstanceValue(String value)
	{ return driver.findElement(By.xpath("//mat-option//span[contains(text(),'"+value+"')]"));}

	public WebElement NewTaskBtn()
    {return wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//mat-label[contains(text(),'New Task')]")));}
   
    public WebElement NewTaskWindowlbl()
    {return wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//span[contains(text(),'Capture New Claim')]")));}
   
    public WebElement PolicyNumberInput()
    {return driver.findElement(By.cssSelector("input[name='policyNumber']"));}
   
    public WebElement DeceasedIDInput()
	{return driver.findElement(By.cssSelector("input[name='deceasedID']"));}
   
    public WebElement PolicyHolderIDInput()
	{return driver.findElement(By.cssSelector("input[name='policyHolderId']"));}
    
    public WebElement BeneficiaryIDInput()
	{return driver.findElement(By.cssSelector("input[name='beneficiaryID']"));}
   
    public WebElement FirstNameInput()
	{return driver.findElement(By.cssSelector("input[name='firstName']"));}
   
    public WebElement lastNameInput()
	{return driver.findElement(By.cssSelector("input[name='surname']"));}

	public WebElement ContactNumberInput()
	{return driver.findElement(By.cssSelector("input[name='contactNumber']"));}

    public WebElement AccountNoInput()
	{return driver.findElement(By.cssSelector("input[name='accNumber']"));}
   
    public WebElement BranchCodeInput()
	{return driver.findElement(By.cssSelector("input[name='branchCode']"));}


    public WebElement FilterPopUpScreen()
    {return wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//*[@class='cdk-overlay-pane']")));}

	public Boolean ClaimSubmissionIndicator()
	{return wait.until(ExpectedConditions.invisibilityOfElementLocated(By.xpath("//span[text()='Task submitted successfully']")));}


	public  WebElement ValidationResultOptions(Enum str,String Value) {return driver.findElement(By.xpath("//app-" + str.toString() + "-validations//following::mat-option//span[contains(text(),'" + Value + "')]"));
	}
	public WebElement OverviewTabVisibility() {
		WebDriverWait wait=new WebDriverWait(driver,10);
		return wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//app-claimoverview//div[@class='mat-tab-label-container']")));
	}

	public  WebElement SelectOverviewTab(Enum OverviewTabName)
	{return driver.findElement(By.xpath("//app-claimoverview//div[@class='mat-tab-label-content' and contains(text(),'" + OverviewTabName.toString() + "')]"));}

	public  WebElement GetOverviewField(Enum OverviewFieldName)
	{return driver.findElement(By.xpath("//input[contains(@name,'" + OverviewFieldName.toString() + "')]"));}

	public  WebElement GetFormOverviewField(String FormName, Enum OverviewFieldName)
	{return driver.findElement(By.xpath("//app-claimoverview//span[contains(text(),'"+FormName+"')]/..//input[contains(@name,'"+OverviewFieldName.toString()+"')]"));}

	public  WebElement GetOverviewAdditionalComments(Enum OverviewFieldName)
	{return driver.findElement(By.xpath("//app-overviews//textarea[contains(@name,'"+OverviewFieldName+"')]"));}

	public  WebElement GetOverviewAdditionalComments(String FormName,Enum OverviewFieldName)
	{return driver.findElement(By.xpath("//app-overviews//span[contains(text(),'"+FormName+"')]/..//textarea[contains(@name,'"+OverviewFieldName+"')]"));}


	public  List<WebElement> GetOverviewInputValues()
	{ return driver.findElements(By.xpath("//app-claimoverview//input"));}

	public  List<WebElement> GetOverviewValidationComments()
	{ return driver.findElements(By.xpath("//app-claimoverview//textarea"));}

	/*public static By NewTaskBtn=By.xpath("//mat-label[contains(text(),'New Task')]");
    public static By NewTaskWindowlbl=By.xpath("//span[contains(text(),'Capture New Claim')]");
    public static By PolicyNumberInput=By.cssSelector("input#mat-input-7");
   
    public static By DeceasedIDInput=By.cssSelector("input#mat-input-8");	
    public static By PolicyHolderIDInput=By.cssSelector("input#mat-input-9");	
    public static By BeneficiaryIDInput=By.cssSelector("input#mat-input-10");	 
    public static By FirstNameInput=By.cssSelector("input#mat-input-11");	
    public static By lastNameInput=By.cssSelector("input#mat-input-12");	
    public static By AccountNoInput=By.cssSelector("input#mat-input-16");	
    public static By BranchCodeInput=By.cssSelector("input#mat-input-17");
    public static By ClaimSubmitBtn=By.xpath("//mat-label[contains(text(),'Submit')]");        
    */    

	/*
	 * String PolicyNumer; String DeceasedIDInput; String PolicyHolderIDInput;
	 * String BeneficiaryIDInput; String FirstNameInput; String lastNameInput;
	 * String AccountNoInput; String BranchCodeInput;
	 */          
	//PolicyFields fields[]=new PolicyFields[8]; 

    public Boolean SubmitClaimForm(String OperationDesk, String	RequestChannel, String ProcessCategory,String SubProcessCategory,String PolicyNo, String DeceasedID, String PH_ID, String BeneficiaryID, String FirstName, String LastName, String ContactNo, String EmailID, String AccountNumber, String BranchCode)
    {
	  Boolean flag=null;
	  WebDriverWait wait=new WebDriverWait(driver, 2);

		try
 	   { 	
 		 gm.ClickElement(HomePage.NewTask);

		 SelectNewTask(OperationDesk, RequestChannel, ProcessCategory, SubProcessCategory);
 	     Optional<String> PolicyNumber=Optional.ofNullable(PolicyNo);
 	 	 Optional<String> Deceasedid=Optional.ofNullable(DeceasedID);
 	 	 Optional<String> PolicyHolder=Optional.ofNullable(PH_ID);
 	 	 Optional<String> Beneficiary=Optional.ofNullable(BeneficiaryID);
 	 	 Optional<String> Firstname=Optional.ofNullable(FirstName);
 	 	 Optional<String> Lastname=Optional.ofNullable(LastName);
 	 	 Optional<String> Contactno=Optional.ofNullable(ContactNo);
 	 	 Optional<String> Emailaddress=Optional.ofNullable(EmailID);
 	 	 Optional<String> AccountNo=Optional.ofNullable(AccountNumber);
 	 	 Optional<String> Branchcode=Optional.ofNullable(BranchCode);


 	 	 if(PolicyNumber.isPresent())
 	 	   gm.EnterValue(PolicyNumberInput(), PolicyNumber.get());
 	 	 if(Deceasedid.isPresent())
 	 	 	   gm.EnterValue(DeceasedIDInput(), Deceasedid.get()); 	 	
 	 	 if(PolicyHolder.isPresent())
 	 	 	   gm.EnterValue(PolicyHolderIDInput(), PolicyHolder.get());
 	 	 if(Beneficiary.isPresent())
 	 	 	   gm.EnterValue(BeneficiaryIDInput(), Beneficiary.get());
 	 	 if(Firstname.isPresent())
 	 	 	   gm.EnterValue(FirstNameInput(), Firstname.get());
 	 	 if(Lastname.isPresent())
 	 	 	   gm.EnterValue(lastNameInput(), Lastname.get());
 	 	 if(AccountNo.isPresent())
	 	 	   gm.EnterValue(AccountNoInput(), AccountNo.get());
 	 	 if(Branchcode.isPresent())
	 	 	   gm.EnterValue(BranchCodeInput(), Branchcode.get());
	 	  	 	
 		 gm.ClickElement(ClaimSubmitBtn);
		  try {
		   wait.until(ExpectedConditions.visibilityOfAllElementsLocatedBy(ClaimSubmitAcknowledge));
		   wait.until(ExpectedConditions.invisibilityOfElementLocated(ClaimSubmitAcknowledge));

		  } catch (Exception e) {}

         String Msg=driver.findElement(ClaimSubmitAcknowledge).getText();
         if(Msg.equals(SubmitClaimMsg))
             flag=true;
               else
        	     flag=false;
 	   }       
 	   catch(Exception e)
 	   { 		   
 	     System.out.println(e.getMessage());
 	   }
 	   return flag;
    }

	public WebElement OperationsDesk(String str)
	{
		WebElement wb=null;
		WebDriverWait wait=new WebDriverWait(driver, 5);
		try {
			ClickElement(OperationsDesk);
			wb=wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//mat-option//span[contains(text(),'" + str + "')]")));
			ClickElement(wb);
		}
		catch (Exception e) {
			e.printStackTrace();
		}
		return  wb;
	}

	public void SwitchToWindow(String WindowName)
	{
		try{
		String parentwindow=driver.getWindowHandle();
		WebDriverWait wait=new WebDriverWait(driver,10);
		wait.until(ExpectedConditions.numberOfWindowsToBe(2));
		Set<String> Windowlist=driver.getWindowHandles();
		Iterator<String> getwindow=Windowlist.iterator();
		while(getwindow.hasNext()) {
			String value=getwindow.next();
			String currentWindow=driver.switchTo().window(value).getTitle();
			if(currentWindow.equals(WindowName)) {
				System.out.println("Currently focus is on Window "+currentWindow);break;
			}
			else
				driver.switchTo().window(parentwindow);
		  }
		}catch (Exception e) {

		}
	}

    public void SelectNewTask(String OperationDesk, String Request_Channel, String processCategory, String subProcessCategory)
	{
		WebDriverWait wait=new WebDriverWait(driver, 3);
		try {

			ClickElement(OperationalDesk);
			ClickElement(wait.until(ExpectedConditions.visibilityOf(InstanceValue(OperationDesk))));
            Thread.sleep(1000);

			ClickElement(RequestChannel);
			ClickElement(wait.until(ExpectedConditions.visibilityOf(InstanceValue(Request_Channel))));

			ClickElement(ProcessCategory);
			ClickElement(wait.until(ExpectedConditions.visibilityOf(InstanceValue(processCategory))));

			ClickElement(SubProcessCategory);
			ClickElement(wait.until(ExpectedConditions.visibilityOf(InstanceValue(subProcessCategory))));

			ClickElement(SubmitClaim);

		}
		catch (Exception e) {
			System.out.println(e.getMessage());
		}
	}



    public Boolean FindPolicyUnderTask(String OperationalDesk,String TaskTab,String PolicyNumber,int wait)
    {
		Boolean flag=false;
    	try 
    	{
	        String PolicyNo=null; String policyNum=null;List<WebElement>ListOfPolicies=null;
    		if(TaskTab.contains("Team Tasks"))
    		{		
	         String TeamTaskTab=gm.GetAttributeValue(TeamTasksTab, "aria-selected");	        				        
	           if(TeamTaskTab.equals("false")) {
				   ClickElement(TeamTasksTab);
				   WaitforInvisibilityOfElement(ClaimSubmitAcknowledge, 10);
				   OperationsDesk(OperationalDesk);
			   }else {
				   OperationsDesk(OperationalDesk);
			   }
    		}
    		else if(TaskTab.contains("My Tasks"))
    		{
   	         String MyTaskTab=gm.GetAttributeValue(MyTasksTab, "aria-selected");	        				        
	           if(MyTaskTab.equals("false")) {
				   ClickElement(MyTasksTab);
				   WaitforInvisibilityOfElement(ClaimSubmitAcknowledge,10);
				   OperationsDesk(OperationalDesk);
			   } else{
				   OperationsDesk(OperationalDesk);
			   }
    		}

    		ApplyFilter("Policy",PolicyNumber);
			Thread.sleep(3000);
    		ListOfPolicies=driver.findElements(By.xpath("mat-card-content"));
	              if(ListOfPolicies.size()>0)
	              {
	                 for(int i=0;i<wait;i++)
	                 {
	   	               ListOfPolicies=driver.findElements(By.xpath("//app-task-card//mat-card"));
	                   Thread.sleep(2000);
		            	for(int j=1;j<=ListOfPolicies.size();j++)
		                {
							 /*
								PolicyNo=driver.findElement(By.xpath("//mat-card["+j+"]/mat-card-content//div[1]/mat-label[3]")).getText();
								policyNum=PolicyNo.split(" ")[2];
								if(policyNum.equals(PolicyNumber))
								  {
									ClickElement(SelectPolicyFromList(j));
									Thread.sleep(2000);
									flag=true;
									break;
								 }
							 */

							String PolicyNum=driver.findElement(By.xpath("//mat-card-content//div[2]//mat-label[2]")).getText();
							String FetchPolicyNumber=PolicyNum.trim();
							if(FetchPolicyNumber.equals(PolicyNumber)) {
								flag=true;
								if(TaskTab.contains("Team Tasks")) {
									ClickElement(driver.findElement(By.xpath("//mat-icon[contains(text(),'playlist_add')]")));
									WaitforVisibilityOFElement(ClaimTaskNotification,10);
									WaitforInvisibilityOfElement(ClaimTaskNotification,10);break;
								}
								else {
									ClickElement(SelectPolicyFromList);break;
								}
							}
			                else
	    		              { ClickElement(RefreshIcon); }
         		         }
		            	 if(flag)
		            		 break;
	                  }
	                } 
    	            else
	                {
    	   	              for(int i=0;i<wait;i++)
    	   	              {
        	   	              ListOfPolicies=driver.findElements(By.xpath("//app-task-card//mat-card"));

							  Thread.sleep(2000);
    	   	            	  if(ListOfPolicies.size()<=0)
    	   	            	  {
								  ClickElement(RefreshIcon);
    	   	            	  }
    	   	            	  else
    	   	            	  {
    	  		            	for(int j=1;j<=ListOfPolicies.size();j++)
    			                {
/*    				                 PolicyNo=driver.findElement(By.xpath("//mat-card["+j+"]/mat-card-content//div[1]/mat-label[3]")).getText();
    				                 policyNum=PolicyNo.split(" ")[2];
    			    	             if(policyNum.equals(PolicyNumber))
    				                  {
    				                	ClickElement(SelectPolicyFromList(j));
    				                	Thread.sleep(2000);
    				                	flag=true;
    				                    break; 
    				                  }*/
									String PolicyNum=driver.findElement(By.xpath("//mat-card-content//div[2]//mat-label[2]")).getText();
									String FetchPolicyNumber=PolicyNum.trim();
									System.out.println(FetchPolicyNumber);
									if(FetchPolicyNumber.equals(PolicyNumber)) {
										flag=true;
										if(TaskTab.contains("Team Tasks")) {
											ClickElement(driver.findElement(By.xpath("//mat-icon[contains(text(),'playlist_add')]")));
											WaitforVisibilityOFElement(ClaimTaskNotification,10);
											WaitforInvisibilityOfElement(ClaimTaskNotification,10); break;}
										else {
											ClickElement(SelectPolicyFromList);break;
										}
									}
         			              }
    	   	            	  }
    	   	            	  
    	    	          if(flag)
		            		 break; 
		            	        else 
		            	        	ClickElement(RefreshIcon);
    	   	              }    	   	                  	   	              
     	             }	              
 	                    	          
    	}
    	catch(Exception e)
    	{
			System.out.println(e.getMessage());
			return flag=false;
    	}
        return flag;
    }   
    
    public void ApplyFilter(String SearchType, String value)
    {
    	try
    	{
    		  String SearchCriteria=SearchType.toLowerCase();
              ClearFilter();
			  Thread.sleep(2000);
   			  ClickElement(AdditionalFilterTask);
    	      WaitforVisibilityOFElement(FilterPopupScreen, 15);
    	      if(SearchCriteria.contains("policy")) {
                 ClickElement(GetElement(Locators.xpath,FilterPolicySeachBox));
				 SendText(FilterPolicySeachBox,value);
			    }
			  else if(SearchCriteria.contains("product")) {
				  ClickElement(GetElement(Locators.xpath,FilterProductSeachBox));
			      SendText(FilterPolicySeachBox,value);
				}
              try {
				  if(GetElement(ApplyFilterButton).isEnabled())
 				    gm.ClickElement(ApplyFilterButton);
				     else
					  gm.ClickElement(CancelAppliedFilter);
			  }
			  catch (Exception e) {
				  gm.ClickElement(CancelAppliedFilter);
			  }
    	}
    	catch(Exception e)
    	{
           System.out.println(e.getMessage());    		
    	}
    }
    
    public void ClearFilter()
    {
    	try
    	{
    	   	List<WebElement> FilterisApplied=driver.findElements(FilterAppliedIndicator);
    	      if(FilterisApplied.size()>0)
			  gm.ClickElement(ClearAppliedFilter);
    	          else
    	            System.out.println("No filter is applied");
    	    	  
    	}
    	catch(Exception e)
    	{
           System.out.println(e.getMessage());    		
    	}
    }


	public void ClaimTask()
	{
		try{
			ClickElement(HomePage.ClaimTaskBtn);
			WaitforVisibilityOFElement(ClaimTaskNotification,10);
			WaitforInvisibilityOfElement(ClaimTaskNotification,10);
		}
		catch (Exception e)
		{
			System.out.println(e.getMessage());
		}
	}
	public boolean SubmitClaim(String SubmitNotification)
	{
		boolean flag=false;
		try {
			ClickElement(GenericLocators.SubmitButton);
			String str=WaitforVisibilityOFElement(HomePage.SubmitClaimNotification);
			WaitforInvisibilityOfElement(HomePage.SubmitClaimNotification,10);
			if (str.equals(SubmitNotification))
				flag=true;
		}
		catch (Exception e) {
			System.out.println(e.getMessage());
		}
		return flag;
	}

	public void SubmitClaim()
	{
		try {
			ClickElement(GenericLocators.SubmitButton);
			WaitforVisibilityOFElement(HomePage.SubmitClaimNotification,10);
			WaitforInvisibilityOfElement(HomePage.SubmitClaimNotification,10);
		}
		catch (Exception e) {
			System.out.println(e.getMessage());
		}
	}



	public String GetOverviewData(Enum OverviewTabName, Enum Field) {
		String getData=null;
		try {
			WebElement wb = SelectOverviewTab(OverviewTabName);
			ClickElement(wb);
			if(Field.toString()=="additionalComments" || Field.toString()=="comments" ||Field.toString()=="fraudAssessmentComments")
			   getData=getTextUsingJs(GetOverviewAdditionalComments(Field));
		    	 else
			       getData = getTextUsingJs(GetOverviewField(Field));
		} catch (Exception e) {
			e.printStackTrace();
		}
		return getData;
	}

	public String GetOverviewData(Enum OverviewTabName,String FormName, Enum Field) {
		String getData=null;
		try {
			WebElement wb = SelectOverviewTab(OverviewTabName);
			ClickElement(wb);
			if(Field.toString()=="additionalComments" || Field.toString()=="comments")
				getData=getTextUsingJs(GetOverviewAdditionalComments(FormName,Field)); //GetOverviewAdditionalComments(FormName,Field).getAttribute("ng-reflect-model");
			      else
                    getData=getTextUsingJs(GetFormOverviewField(FormName,Field));
		} catch (Exception e) {
			e.printStackTrace();
		}
		return getData;
	}

	public String[] GetOverviewDataList(Enum OverviewTabName) {

		String s[]=null;
		try {
			SelectOverviewTab(OverviewTabName);
			WebElement wb = SelectOverviewTab(OverviewTabName);
			ClickElement(wb);
			Thread.sleep(3000);
			List <WebElement> getData = GetOverviewInputValues();
			s=new String[getData.size()];
			for(int i=0;i<getData.size();i++) {
				try {
					String sp = getData.get(i).getAttribute("ng-reflect-model");
			       	if (StringUtil.isNotBlank(sp)) {
						s[i] = sp;
						System.out.println(s[i]);
					}
				}
				catch (NullPointerException e) {
					System.out.println(e.getMessage());
				}
			}

		} catch (Exception e) {
			e.printStackTrace();
		}
		return s;
	}

	/*public String [] GetOverviewDataList(Enum OverviewTabName, Enum FieldName) {

		HashMap<String,String> list=null;
		String [] FetchValue=new String[1000];
		try {
			WebElement w = driver.findElement(By.xpath("//app-claimoverview//div[@class='mat-tab-label-content' and text()='" + OverviewTabName.toString() + "']"));
			ClickElement(w);
			String SelectedOverviewTab =OverviewTabName.toString().toLowerCase();
			List<WebElement> wb = driver.findElements(By.xpath("//app-"+SelectedOverviewTab+"tab//mat-grid-tile//input"));
			list=new HashMap();
			for(int i=0;i<wb.size();i++)
			{
				List<WebElement> wb1=null;
				List<WebElement> wb2=null;
				try {
					 wb1 = driver.findElements(By.xpath("//app-"+SelectedOverviewTab+"tab//mat-grid-tile['" + i + "']//input"));
					 wb2 = driver.findElements(By.xpath("//app-"+SelectedOverviewTab+"tab//mat-grid-tile['" + i + "']//label/mat-label"));
				}
				catch (Exception e){}

                if(wb1.size()!=0 && wb2.size()!=0) {
					String s2 = wb1.get(i).getAttribute("ng-reflect-model");
					String s1 = wb2.get(i).getText();
					list.put(s1, s2);
					System.out.println(list.put(s1, s2));
				}
			}
			System.out.println("-------------Collections------------");
            for(Map.Entry m : list.entrySet())
			{
               System.out.println(m.getKey()+":\t"+m.getValue());
				 for(int i=0;i<wb.size();i++) {
					 {
						FetchValue[i] = m.getValue().toString();
						System.out.println(FetchValue[i]);
					 }
				}
			}
		} catch (Exception e) {
		 System.out.println("Error \n"+e.getMessage());
		}
		return FetchValue;
	}*/


	public enum OverviewTabName
	{
		Claim,
		Policy,
		Deceased,
		Beneficiary,
		Manual,
		Fraud
	}

	public enum PolicyOverview
	{

		productName,
		policyNumber,
		policyStatus,
		policyHolderId,

	}
	public enum DeceasedOverview
	{
		idNumber,
		dateOfBirth,
		firstName,
		surname,
		role,
		multiplePoliciesLinked,
		deceasedStatus,
		dateOfDeath,
		causeOfDeath,
		placeOfDeath,
	}
	public enum BeneficiaryOverview
	{
		idNumber,
		dateOfBirth,
		firstName,
		surname,
		contactNumber,
		altContactNumber,
		emailAddress,
		isClaimant,
		isPolicyHolder,
	}
	public enum ManualAssessmentOverview
	{
		finalOutcome,
		finalOutcomeReason,
		additionalComments,
		validationAgent1,
		validationComments1,
		managementComments1
	}

	public enum FraudAssessmentOverview
	{
		complexityType,
		fraudOutcome,
		fraudOutcomeReason,
		claimOutcomeRecommendation,
		fraudAssessmentComments
	}


	public enum MedicalPractitionerFormFields
	{
		medicalPractitionerType,
		registrationNumber,
		name,
		surname,
		practiceName,
		practiceNumber,
		contactNumber,
		address,
		city,
		postalCode,
		province,
		validationOutcome,
		validationSource,
		sourceReferenceNumber,
		comments,
	}

	public enum FuneralUndertakerFormFields
	{
		validationOutcome,
		validationSource,
		sourceReferenceNumber,
		comments,
		funeralParlourName,
		dhaDesignationNumber,
		companyRegistrationNumber,
		sarsRegistrationNumber
	}

	public enum InformantFormFields
	{
		validationOutcome,
		validationSource,
		sourceReferenceNumber,
		comments,
		idNumber,
		dateofBirth,
		name,
		surname,
		residentialAddress,
		city,
		postalCode,
		province,
		relationshipToDeceased,
		contactNumber
	}
	public  enum  IndunaField
	{
		validationOutcome,
		validationSource,
		sourceReferenceNumber,
		comments,
		causeOfDeath,
		placeOfDeathDescription,
		causeOfDeathSource,
		name,
		surname,
		idNumber,
		contactNumber,
		address,
		city,
		postalCode,
		province
	}

	public String GetOverviewDetails(Enum OverviewTabName, String getFieldvalue) {

		String getData=null;
		try {
			ClickElement(SelectOverviewTab(OverviewTabName));
			HashMap<String,String> list=new HashMap<String,String>();

			List<WebElement> wb1=driver.findElements(By.xpath("//span[text()=' Manual Assessment Final Outcome ']//following::input"));
			List<WebElement> wb2=driver.findElements(By.xpath("//span[text()=' Manual Assessment Final Outcome ']//following::input/../span//mat-label"));
			//List<WebElement> wb2=driver.findElements(By.xpath("//div[@class='tab-body-container']//following::mat-label"));
			for (int i=1;i<wb2.size();i++){
				String s=wb2.get(i).getText();
				String s2=wb1.get(i).getText();
				System.out.println(s+"\t"+s2);
				if(getFieldvalue.equalsIgnoreCase(s)) {
				 String val1=wb1.get(i).getAttribute("ng-reflect-model");
				 String val2=wb2.get(i).getText();
                 list.put(val2,val1);
				 break;
				}
			}
			System.out.println(list);
            getData=list.get(getFieldvalue);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return getData;
	}

	public String getAttributeValue(By locator, String AttributeName)
	{
		String str=null;
		try {
			str = GetElement(locator).getAttribute(AttributeName);
		}
		catch (Exception e) {
			System.out.println(e.getMessage());
		}
		return str;
	}

	public boolean CompareValidationsForms(String Form_name, String [] formFields, Enum SelectOverviewTab)
	{
		String value=null;
		ArrayList <String> OverviewData=new ArrayList<String>();
		try {
			OverviewData.clear();
			ClickElement(OverviewTabVisibility());
			switch (Form_name) {
				case "Medical Practitioner Validation":
					for (HomePage.MedicalPractitionerFormFields MedicalPrac : HomePage.MedicalPractitionerFormFields.values()) {
						value = GetOverviewData(SelectOverviewTab, "Medical Practitioner Validation", MedicalPrac);
						OverviewData.add(value);
					}
					break;
				case "Funeral Undertaker Validation":
					for (HomePage.FuneralUndertakerFormFields FuneralUndertaker : HomePage.FuneralUndertakerFormFields.values()) {
						value = GetOverviewData(SelectOverviewTab, "Funeral Undertaker Validation", FuneralUndertaker);
						OverviewData.add(value);
					}
					break;
				case "Informant Validation":
					for (HomePage.InformantFormFields informant : HomePage.InformantFormFields.values()) {
						value = GetOverviewData(SelectOverviewTab, "Informant Validation", informant);
						OverviewData.add(value);
					}
					break;
				case "Induna/Traditional Leader Validation":
					for (HomePage.IndunaField induna : HomePage.IndunaField.values()) {
						value = GetOverviewData(SelectOverviewTab, "Induna/Traditional Leader Validation", induna);
						OverviewData.add(value);
					}
					break;
			}
		}
		catch(Exception e) {
				System.out.println(e.getMessage());
		}

		//String [] MedicalPractitionerFields=new String[]{MS5PractitionerType,  MS5HPCSANumber,  MS5PractitionerName, MS5PractitionerSurname, MS5FacilityName, MS5PractitionerNumber, MS5PracticeContact,  MS5BusinessAddress, 	MS5PracticeCity, 	MS5PracticePostalcode,  MS5PracticeProvice,  MS5PractitionerValidationOutcome,  MS5PractitionerValidationSource,  MS5PractionerReferenceNo,  MS5PractitionerComments};
		boolean mismatchFound=false;
		try {
			for (int i = 0; i < formFields.length; i++) {
				boolean flags = true;
				System.out.println(OverviewData.get(i) + "\t" + formFields[i]);
				if (formFields[i].equals(OverviewData.get(i)))
					flags = true;
				else {
					flags = false;
					mismatchFound = true;
				}
			}
		}catch (Exception e){
			System.out.println(e.getMessage());
		}

		System.out.println("------------------New Form------------------------");
		return  mismatchFound;
	}

	public boolean VerifyCRMImage(String Path)
	{
		boolean flag=false;
		try {
			 LocalDateTime myDateObj = LocalDateTime.now();
			 DateTimeFormatter myFormatObj = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");
			 String formattedDate ="Indexed Date: "+myDateObj.format(myFormatObj).trim();
             ClickElement(CRM_Button);
			 Thread.sleep(3000);
             WaitforVisibilityOFElement(DocumentsTab);
			 ClickElement(DocumentsTab);
			 WaitforVisibilityOFElement(NewDocumentsTab);
			 ClickElement(NewDocumentsTab);
			 WaitforVisibilityOFElement(PreviewIcon);
			 ClickElement(PreviewIcon);
             WaitforInvisibilityOfElement(Spinner,20);
			 String TimeStamp=GetText(HomePage.UploadTimeStamp).trim();
			 System.out.println(TimeStamp+" ===== "+formattedDate);

			 List <WebElement> w=driver.findElements(PDFViewerPanel);
			 System.out.println(w.size());
			 if (w.size()>0 && TimeStamp.equals(formattedDate)) {
				 flag=true;
			 } else {
				 flag=false;
			 }

		}
		catch (Exception e) {
			System.out.println(e.getMessage());
		}
		return flag;
	}

	public  void ClosePDFDoc() {
	try {
		ClickElement(ClosePDFDoc);
		WaitforInvisibilityOfElement(ClosePDFDoc);
	    }catch (Exception e) {
		System.out.println(e.getMessage());
	   }
	}

}
