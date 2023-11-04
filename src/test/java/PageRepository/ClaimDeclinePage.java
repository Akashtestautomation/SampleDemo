package PageRepository;

import com.ibm.db2.jcc.am.by;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import Methods.GenericMethod;

public class ClaimDeclinePage extends GenericMethod{
	
    WebDriverWait wait=null;
    HomePage home=new HomePage();

    public static By DeclineDecision=By.xpath("//*[@name='decision']");
    public static By DeclineDecisionReason=By.xpath("//*[@name='decisionReason']");
    public static By AdditionalComments=By.xpath("//textarea[@name='additionalComments']");
    public static By SystemRecommendedAction=By.xpath("//textarea[@name='systemDeclineMessage']");


    public  WebElement SelectDeclineOption(String str)
    {
        WebElement wb=null;
        try{
            ClickElement(ClaimDeclinePage.DeclineDecision);
            WebDriverWait wait=new WebDriverWait(driver,10);
            wb=wait.until(ExpectedConditions.visibilityOfElementLocated(WaitforDropdownItemVisibility(str)));}
        catch (Exception e){
           log.error(e.getMessage());
        }
        return wb;
    }

    public  WebElement SelectDeclineReason(String str)
    {
        WebElement wb=null;
        try{
            ClickElement(ClaimDeclinePage.DeclineDecisionReason);
            WebDriverWait wait=new WebDriverWait(driver,10);
            wb=wait.until(ExpectedConditions.visibilityOfElementLocated(WaitforDropdownItemVisibility(str)));}
        catch (Exception e){
            log.error(e.getMessage());
        }
        return wb;
    }

    public boolean SubmitPASReviewValidationForm(String DeclinedDecision, String DeclinedReason, String DeclinedComments)
    {
        boolean flag=false;
        try{
            ClickElement(SelectDeclineOption(DeclinedDecision));
            ClickElement(SelectDeclineReason(DeclinedReason));
            SendText(ClaimDeclinePage.AdditionalComments,DeclinedComments);
            flag=home.SubmitClaim(HomePage.SubmitFormNotification);
            if(flag)
                flag=true;
        }
        catch (Exception e) {
            log.error(e.getMessage());
        }
        return flag;
    }

}
