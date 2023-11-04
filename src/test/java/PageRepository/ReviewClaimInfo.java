package PageRepository;

import Methods.GenericMethod;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.util.List;

public class ReviewClaimInfo extends GenericMethod {

    HomePage home=new HomePage();
    public static By ReviewValidationResults=By.xpath("//textarea[@name='validatePolicySvcFromPASDescription']");
    public static By ReviewClaimFinalOutcome=By.xpath("//mat-select[@ng-reflect-name='decision']");
    public static By ReviewClaimFinalOutcomeReason=By.xpath("//mat-select[@name='decisionReason']");


    //Final Outcome Options
    public static String ClaimDetailsSuccessfullyUpdated="Claim Details Successfully Updated";
    public static String NonFuneralClaimRequest="Non-Funeral Claim Request";
    public static String InvalidClaimRequest="Invalid Claim Request";
    public static String CloseClaim="Close Claim Request";

    //Final Outcome Reason for Non-Funeral Claim Request
    public static String FuneralClaimRequest="FG/FN/FP Funeral Claim Request";
    public static String EmbeddedChequeFuneralClaimRequest="Embedded Cheque Funeral Claim Request";
    public static String AccidentalDeathClaimRequest="Accidental Death Claim Request";
    public static String LifestyleProtectClaimRequest="Lifestyle Protect Claim Request";
    public static String CoverForLifeClaimRequest="Cover for Life Claim Request";

    //Final Outcome Reason for Invalid Claim Request
    public static String UnableToConfirmCover="Unable to Confirm Deceased Cover";
    public static String NoCoverForDeceased="No Cover for Deceased";

    //Final Outcome Reason for Close Claim Request
    public static String InsufficientClaimDetails="Insufficient Claim Details Provided";


    public void SelectMemberFromList(String MemberName){
        try{
            List<WebElement> getRows=driver.findElements(By.tagName("td"));
            for(WebElement wb : getRows){
                String value=wb.getText();
                if(value.equalsIgnoreCase(MemberName)){
                    ClickElement(wb);
                    log.info("Record is selected successfully from list");
                }
            }
        }catch (Exception e){
            log.error(e.getMessage());
        }
    }

    public boolean SubmitPASReviewFormAsValid(String MemberName,String finalOutcome, String AdditionalComments)
    {
        boolean flag=false;
        try{
            SelectMemberFromList(MemberName);
            ClickElement(SelectReviewFinalOutcome(finalOutcome));
            SendText(VOPDDeathValidations.DeathAdditionalComments,AdditionalComments);
            flag=home.SubmitClaim(HomePage.SubmitFormNotification);
            if(flag)
                 flag=true;
        }
        catch (Exception e) {
            log.error(e.getMessage());
        }
        return flag;
    }

    public boolean SubmitPASReviewFormAsInvalid(String finalOutcome,String FinalOutcomeReason ,String AdditionalComments)
    {
        boolean flag=false;
        try{
            ClickElement(SelectReviewFinalOutcome(finalOutcome));
            ClickElement(SelectReviewFinalOutcomeReason(FinalOutcomeReason));
            SendText(VOPDDeathValidations.DeathAdditionalComments,AdditionalComments);
            flag=home.SubmitClaim(HomePage.SubmitFormNotification);
            if(flag)
                flag=true;
        }
        catch (Exception e) {
            log.error(e.getMessage());
        }
        return flag;
    }

    public WebElement SelectReviewFinalOutcome(String str)
    {
        WebElement wb=null;
        try {
            ClickElement(ReviewClaimInfo.ReviewClaimFinalOutcome);
            WebDriverWait wait=new WebDriverWait(driver,10);
            wb=wait.until(ExpectedConditions.visibilityOfElementLocated(WaitforDropdownItemVisibility(str)));
        }
        catch (Exception e) {
            log.error(e.getMessage());
        }
        return  wb;
    }

    public WebElement SelectReviewFinalOutcomeReason(String str)
    {
        WebElement wb=null;
        try {
            ClickElement(ReviewClaimInfo.ReviewClaimFinalOutcomeReason);
            WebDriverWait wait=new WebDriverWait(driver,10);
            wb=wait.until(ExpectedConditions.visibilityOfElementLocated(WaitforDropdownItemVisibility(str)));
        }
        catch (Exception e) {
            log.error(e.getMessage());
        }
        return  wb;
    }
}
