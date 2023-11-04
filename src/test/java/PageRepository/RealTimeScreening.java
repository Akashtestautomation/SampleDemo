package PageRepository;

import Methods.GenericMethod;
import org.openqa.selenium.By;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

public class RealTimeScreening extends GenericMethod {

    GenericMethod gm=new GenericMethod();
    HomePage home=new HomePage();

    public static By ReferenceNumber= By.cssSelector("input[name='referenceNumber']");
    public static By RTSFAILButton= By.xpath("//mat-label[contains(text(),'Fail')]");
    public static By RTSPASSButton= By.xpath("//mat-label[contains(text(),'Pass')]");
    public static By RTSSubmitButton= By.xpath("//mat-label[contains(text(),'Submit')]");
    public static By RTS_Result=By.xpath("//textarea[@name='beneficiaryScreening']");

    public boolean SubmitRTSForm(String ReferenceNo,String RTSResult)
    {
        boolean flag=false;
        String RTS_RESULT=RTSResult.toLowerCase();
        try{
            waitforPageload();
            WebDriverWait wait=new WebDriverWait(driver, 10);
            wait.until(ExpectedConditions.visibilityOfAllElementsLocatedBy(ReferenceNumber));
            SendText(ReferenceNumber,ReferenceNo);
            switch(RTS_RESULT) {
                case "pass" : ClickElement(RTSPASSButton);
                              break;
                case "fail" : ClickElement(RTSFAILButton);
                              break;
            }
            flag=home.SubmitClaim(HomePage.SubmitFormNotification);
            if(flag)
                return flag=true;
            /*ClickElement(RTSSubmitButton);
            try {
                wait.until(ExpectedConditions.visibilityOfAllElementsLocatedBy(home.ClaimSubmitAcknowledge));
                wait.until(ExpectedConditions.invisibilityOfElementLocated(home.ClaimSubmitAcknowledge));
            } catch (Exception e) {}*/
        }
        catch (Exception e) {
            System.out.println(e.getMessage());
        }
       return flag;
    }

}
