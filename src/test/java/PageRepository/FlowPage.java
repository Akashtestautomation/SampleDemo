package PageRepository;

import java.util.List;
import java.util.Optional;
import java.util.concurrent.TimeUnit;

import org.apache.xmlbeans.impl.xb.xsdschema.Public;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.remote.server.handler.ClickElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import com.gargoylesoftware.htmlunit.javascript.background.JavaScriptExecutor;

import Methods.GenericMethod;
import Methods.GenericMethod.Locators;
import net.sourceforge.htmlunit.corejs.javascript.JavaScriptException;

public class FlowPage extends GenericMethod 
{
	GenericMethod gm=new GenericMethod();
    WebElement wb=null;
    WebDriverWait wait=null;
 
    
    public static By FlowUsernameTextBox=By.id("j_idt15:username");
    public static By FlowPasswordTextBox=By.id("j_idt15:password");
    public static By FlowSubmitBtn=By.id("j_idt15:loginBtn");
    public static By PageHeaderLables=By.xpath("//span[@class='ui-menuitem-text']");
    public static By PolicySearchBox=By.xpath("//input[@id='createSearchItemView:searchPolicyNumber']");
    public static By PolicySearchBtn=By.xpath("//Button[@id='createSearchItemView:search']");
    public static By GetSearchResult=By.xpath("//*[@id='createSearchItemView:mainTabView:workItemtable']/div[@class='ui-datatable-scrollable-body']");
    public static By GetCommentsResult=By.id("createSearchItemView:mainTabView:commentstable_data");
    public static By NoItemFound=By.xpath("//p[text()='No work items found for search criteria.']");
    //public static By HeaerList=By.xpath("//thead[@id='createSearchItemView:mainTabView:workItemtable_head']//th//span[@class='ui-column-title']");
    public static By CommentSummaryBtn=By.id("createSearchItemView:mainTabView:commentstable:CommentsSummary");   
    public static By CommentsSummaryDetails=By.id("commentsSummaryView:commentsTextArea");
    public static By CommentsSummaryScreen=By.xpath("//*[@id='createSearchItemView:mainTabView:commentstable:CommentsSummary_dlg']");

	public WebElement SelectMenu(String MenuItem) {   wait=new WebDriverWait(driver, 5);
    	return wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//span[text()='"+MenuItem+"']")));
    }
    public WebElement HeaerList(String HeaderName)
    { return driver.findElement(By.xpath("//thead[@id='createSearchItemView:mainTabView:workItemtable_head']//th//span[text()="+HeaderName+"]"));}
    
    public WebElement OpenStatusWiseComments(String PolicyStatus)
    { return driver.findElement(By.xpath("//tbody[@id='createSearchItemView:mainTabView:commentstable_data']//td//span[text()='"+PolicyStatus+"']"));}
    
    
	public boolean LoginToFlow(String username, String Password )
	{
		boolean flag=false;
		 try {
		   //WebDriverWait wait=new WebDriverWait(driver,5);
	       //wait.until(ExpectedConditions.visibilityOfElementLocated(FlowPage.FlowSubmitBtn));
		   WaitforVisibilityOFElement(FlowPage.FlowSubmitBtn);
	       gm.SendText(Locators.id,FlowPage.FlowUsernameTextBox,username);
	       gm.SendText(Locators.id,FlowPage.FlowPasswordTextBox,Password);	
           gm.ClickElement(FlowPage.FlowSubmitBtn);
           wait.until(ExpectedConditions.visibilityOf(SelectMenu(username)));          
           flag=gm.VerifyTextFromList(PageHeaderLables, username);
           if(flag)       	   
              return true;          	   
		 }
		 catch(Exception e) {
			 log.error(e.getMessage());
		 }
		 return flag;
	}

	public boolean SearchPolicyNumber(String Policy)
	{
		boolean flag=false;
		try {
			String SubMenu="Create/Search Item";
			String Menu="Personal Queue";
			WebElement wb=SelectMenu(Menu);
			Actions action=new Actions(driver);
			action.moveToElement(wb).build().perform();
			ClickElement(SelectMenu(SubMenu));
			SwitchToWindow("Create Search Item");
			//WebDriverWait wait=new WebDriverWait(driver,20);
			//wait.until(ExpectedConditions.visibilityOfElementLocated(FlowPage.PolicySearchBox));
			WaitforVisibilityOFElement(FlowPage.PolicySearchBox,20);
			SendText(FlowPage.PolicySearchBox,Policy);
			ClickElement(FlowPage.PolicySearchBtn);
			//wait=new WebDriverWait(driver,50);
			//wait.until(ExpectedConditions.visibilityOfElementLocated(FlowPage.GetSearchResult));
			WaitforVisibilityOFElement(FlowPage.GetSearchResult,50);

		}
		catch(Exception e) {
			log.error(e.getMessage());
		}
		return flag;
	}

	public void NaviagteToSubMenu(String Menu, String SubMenu)
	{
		try {
			WebElement wb=SelectMenu(Menu);
			Actions action=new Actions(driver);
			action.moveToElement(wb).build().perform();
			ClickElement(SelectMenu(SubMenu));
		}
		catch (Exception e) {
			log.error(e.getMessage());
		}
	}


	public String getPolicyDetailsOnFlow(String HeaderName)
	{
		String value=null;
		try {
			String getHeaderName=driver.findElement(By.xpath("//thead[@id='createSearchItemView:mainTabView:workItemtable_head']//th//span[text()='"+HeaderName+"']")).getText();
			if(getHeaderName.equals("Work Type"))
				value=driver.findElement(By.xpath("//tbody[@id='createSearchItemView:mainTabView:workItemtable_data']//tr[1]//td[4]//span")).getText();
			else if(getHeaderName.equals("Status"))
				value=driver.findElement(By.xpath("//tbody[@id='createSearchItemView:mainTabView:workItemtable_data']//tr[1]//td[5]//span")).getText();
			else if(getHeaderName.equals("Queue"))
				value=driver.findElement(By.xpath("//tbody[@id='createSearchItemView:mainTabView:workItemtable_data']//tr[1]//td[6]//span")).getText();

		}
		catch (Exception e) {
           log.error(e.getMessage());
			// TODO: handle exception
		}
	  return value;	
	}
    	
    public void OpenFlowComments(String Status)
    {
    	try {
    		WebElement wb=driver.findElement(By.xpath("//tbody[@id='createSearchItemView:mainTabView:workItemtable_data']//tr[1]//td[2]//span"));
    	    Actions action=new Actions(driver);
    	    action.moveToElement(wb).doubleClick().perform();
            gm.WaitforVisibilityOFElement(FlowPage.CommentSummaryBtn, 60);
    	    //JavascriptExecutor js=(JavascriptExecutor)driver;
            //js.executeScript("arguments[0].scrollIntoView(true);",driver.findElement(FlowPage.GetCommentsResult));
			gm.ScollToElement(driver.findElement(FlowPage.GetCommentsResult));
            Thread.sleep(5000);
            //gm.WaitforpageLoad(FlowPage.GetCommentsResult, 60); 
            gm.ClickElement(OpenStatusWiseComments(Status));
            gm.ClickElement(CommentSummaryBtn);
            gm.WaitforFrameLoad(0,20);
    	}
    	catch (Exception e) {
		  log.error(e.getMessage());	// TODO: handle exception
		}
    }

	public boolean VerifyFlowWorkItem(String WorkType, String Status,String Queue){
		boolean Flag=false;
		try{
			List<WebElement> rows=driver.findElements(By.xpath("//table//tbody[@id='createSearchItemView:mainTabView:workItemtable_data']//tr"));
			for(int i=1;i<= rows.size();i++) {
				String workType = driver.findElement(By.xpath("//table//tbody[@id='createSearchItemView:mainTabView:workItemtable_data']//tr["+i+"]//td[4]//span")).getText();
				String status   = driver.findElement(By.xpath("//table//tbody[@id='createSearchItemView:mainTabView:workItemtable_data']//tr["+i+"]//td[5]//span")).getText();
				String queue    = driver.findElement(By.xpath("//table//tbody[@id='createSearchItemView:mainTabView:workItemtable_data']//tr["+i+"]//td[6]//span")).getText();
				if(WorkType.equalsIgnoreCase(workType) && Status.equalsIgnoreCase(status) && Queue.equalsIgnoreCase(queue)){
                  Flag=true;
				  break;
				}
			}
	     }catch(Exception e){
			log.error(e.getMessage());
		}
		return Flag;
	}

    public boolean GetCommentDescriptions(String comments)
    {
    	int i=0;
    	String s[]=null;
		boolean flag=false;
    	try {
    		WebElement wb=driver.findElement(FlowPage.CommentsSummaryDetails);
    	    String str=wb.getText();
			log.info(str);
    	    s=str.split("\n");
    	    for(i=0;i<s.length;i++) {
    	    	if(s[i].contains(comments))
    	    	{ flag=true;break;}
    	    }
    	}
    	catch (Exception e) {
    		log.error(e.getMessage());
		}
    	return flag;
    }
    public String GetCommentDescriptions(String MSName,String comments)
    {
    	int actualComments=0;
    	String s[]=null;
    	try
    	{
    		WebElement wb=driver.findElement(FlowPage.CommentsSummaryDetails);
    	    String str=wb.getText();
    	    s=str.split("\n");	    
    	    for(int i=0;i<s.length;i++)
    	    {
    	    	boolean flag=false;
    	    	if(s[i].contains(MSName))
    	    	{
    	    		actualComments=i;	
    	    		for(int j=0;j<6;j++)
    	    		{
    	    		  if(s[actualComments].contains(comments))
    	    		  { 
    	    		    flag=true;
    	    		    break;
    	    		  }
    	    		  actualComments++;
    	    		}
    	    		
    	    		if(flag)
    	    			break;
    	    	}
    	    }	
    	}
    	catch (Exception e) {
			log.error(e.getMessage());
		}
    	return s[actualComments];
    }

}
