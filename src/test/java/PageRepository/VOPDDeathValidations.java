package PageRepository;

import Methods.GenericMethod;
import com.ibm.as400.access.QSYSObjectPathName;
import org.apache.xmlbeans.impl.xb.xsdschema.Public;
import java.lang.*;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.remote.server.handler.ClickElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import java.sql.Driver;
import java.util.Locale;
import java.util.Optional;

public class VOPDDeathValidations extends GenericMethod {

    HomePage home=new HomePage();
    public static WebDriverWait wait=null;
    public static By CauseOfDeath=By.xpath("//mat-select[@name='causeOfDeath']");
    public static By PlaceOfDeath=By.xpath("//input[@name='placeOfDeath']");
    //public static By DeceasedStatus=By.xpath("//mat-select[@name='deceasedStatus']");
    public static By DeceasedStatus=By.xpath("//mat-label[text()=' Deceased Status ']");
    public static By CauseOfDescription=By.xpath("//mat-select[@name='causeDescription']");
    public static By DeceasedDateOfDeath=By.xpath("//input[@name='deceasedDateOfDeathControl']");
    public static By ValidationResults=By.xpath("//mat-select[contains(@ng-reflect-name,'validationSource')]");
    public static By ValidationStatus=By.xpath("//mat-select[contains(@ng-reflect-name,'status')]");
    public static By ValidationOutcome=By.xpath("//mat-select[contains(@ng-reflect-name,'validationOutcome')]");
    public static By DeathFinalOutcome=By.xpath("//mat-select[@ng-reflect-name='finalOutcome']");
    public static By DeathManagementComments=By.xpath("//textarea[@name='managementComments']");
    public static By DeathAdditionalComments=By.xpath("//textarea[@name='additionalComments']");
    public static By DeathValidationComments=By.xpath("//textarea[@name='deceasedComments']");
    public static By DeathValidationResults=By.xpath("//textarea[@name='deathValidation']");

    public By DeathValidationRequirements(String Value)
    {
        By value=null;
        try { value=By.xpath("//span[(text()='"+" "+ Value +" "+"')]");}
        catch (Exception e) { System.out.println(e.getMessage());}
        return  value;
    }

    public By WaitforDropdownItemVisibility(String Value)
    {
        By value=null;
        try { value=By.xpath("//mat-option//span[contains(text(),'" + Value + "')]");
        }
        catch (Exception e) {
            System.out.println(e.getMessage());}
        return  value;
    }


    public WebElement VOPD_CauseOFDeath(String str)
    {
        WebElement wb=null;
        try {
            ClickElement(VOPDDeathValidations.CauseOfDeath);
            wb=wait.until(ExpectedConditions.visibilityOfElementLocated(WaitforDropdownItemVisibility(str)));
        }
        catch (Exception e) {
            e.printStackTrace();
        }
        return  wb;
    }

    public WebElement VOPD_DeceasedStatus(String str)
    {
        WebElement wb=null;
        try {
            wait=new WebDriverWait(driver,5000);
            ClickElement(VOPDDeathValidations.DeceasedStatus);
            wb=wait.until(ExpectedConditions.visibilityOfElementLocated(WaitforDropdownItemVisibility(str)));
       }
       catch (Exception e) {
           System.out.println(e.getMessage());
       }
        return  wb;
    }

    public WebElement VOPD_CauseOFDescription(String str)
    {
        WebElement wb=null;
        try {
            ClickElement(VOPDDeathValidations.CauseOfDescription);
            wb=wait.until(ExpectedConditions.visibilityOfElementLocated(WaitforDropdownItemVisibility(str)));
        }
        catch (Exception e) {
           e.printStackTrace();
        }
        return  wb;
    }

    public WebElement SelectDeathValidationRequirement(String Field, String Value)
    {
        WebElement wb=null;
        String ValidationRequirement=Field.toLowerCase();
        try {
            switch (ValidationRequirement) {
                case "validation source"  : ClickElement(driver.findElement(VOPDDeathValidations.ValidationResults));
                                            wb=wait.until(ExpectedConditions.visibilityOfElementLocated(DeathValidationRequirements(Value)));
                                            break;
                case "status"             : ClickElement(driver.findElement(VOPDDeathValidations.ValidationStatus));
                                            wb=wait.until(ExpectedConditions.visibilityOfElementLocated(DeathValidationRequirements(Value)));
                                            break;
                case "validation outcome" : ClickElement(driver.findElement(VOPDDeathValidations.ValidationOutcome));
                                            wb=wait.until(ExpectedConditions.visibilityOfElementLocated(DeathValidationRequirements(Value)));
                                            break;
            }
        }
        catch (Exception e) {
            System.out.println(e.getMessage());
        }
        return wb;
    }

    public WebElement VOPDFinalOutcome(String str)
    {
        WebElement wb=null;
        try {
            ClickElement(VOPDDeathValidations.DeathFinalOutcome);
            WebDriverWait wait=new WebDriverWait(driver,10);
            wb=wait.until(ExpectedConditions.visibilityOfElementLocated(WaitforDropdownItemVisibility(str)));
        }
        catch (Exception e) {
            e.printStackTrace();
        }
        return  wb;
    }

     public boolean SubmitVOPDValidationForm(String VOPD_Status, String VOPD_DOD, String CauseofDeath, String PlaceOfDeath, String DeathDescription,String ValidationSource, String Status, String ValidationOutcome,String DeathValidationComment, String finalOutcome, String AdditionalComments)
     {
         boolean flag=false;
         try{
             /*Optional<String> VOPDStatus=Optional.ofNullable(VOPD_Status);
             Optional<String> VOPDDOD=Optional.ofNullable(VOPD_DOD);
             Optional<String> VOPDPlaceOfDeath=Optional.ofNullable(PlaceOfDeath);
             Optional<String> VOPDCauseOfDeath=Optional.ofNullable(CauseofDeath);
             Optional<String> VOPDDeathDescription=Optional.ofNullable(DeathDescription);
             Optional<String> VOPDValidationSource=Optional.ofNullable(ValidationSource);
             Optional<String> VOPDstatus=Optional.ofNullable(Status);
             Optional<String> VOPDValidationOutcome=Optional.ofNullable(ValidationOutcome);
             Optional<String> VOPDDeathValidationComments=Optional.ofNullable(DeathValidationComment);
             Optional<String> VOPDFinalOutcome=Optional.ofNullable(finalOutcome);
             Optional<String> VOPD_AdditionalComments=Optional.ofNullable(AdditionalComments);
             */
             waitforPageload();
             ScollToElement(DeceasedStatus);
             ClickElement(VOPD_DeceasedStatus(VOPD_Status));
             EnterValue(VOPDDeathValidations.DeceasedDateOfDeath, VOPD_DOD);
             ClickElement(VOPD_CauseOFDeath(CauseofDeath));
             SendText(VOPDDeathValidations.PlaceOfDeath,PlaceOfDeath);
             ClickElement(VOPD_CauseOFDescription(DeathDescription));

             ClickElement(SelectDeathValidationRequirement("validation source",ValidationSource));
             ClickElement(SelectDeathValidationRequirement("status",Status));
             ClickElement(SelectDeathValidationRequirement("validation outcome",ValidationOutcome));

             SendText(VOPDDeathValidations.DeathValidationComments,DeathValidationComment);
             ClickElement(VOPDFinalOutcome(finalOutcome));
             SendText(VOPDDeathValidations.DeathAdditionalComments,AdditionalComments);
             flag=home.SubmitClaim(HomePage.SubmitFormNotification);
             if(flag)
                 return flag=true;
         }
         catch (Exception e) {
             System.out.println(e.getMessage());
         }
         return flag;
     }
}
