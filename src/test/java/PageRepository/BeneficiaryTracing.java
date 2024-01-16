package PageRepository;

import Methods.GenericMethod;
import org.apache.xmlbeans.impl.xb.xsdschema.Public;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.remote.server.handler.ClickElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.util.Optional;

public  class BeneficiaryTracing extends GenericMethod {

    VOPDDeathValidations VOPD=new VOPDDeathValidations();

    public static By BeneficiaryValidationResult= By.xpath("//mat-label[contains(text(),'Beneficiary Validation Result')]//preceding::textarea");
    public static By ExpandClientInitiationform= By.xpath("//div[contains(text(),'Client Initiated Claim ')]/../mat-icon[contains(text(),'add_circle')]");
    public static By Systeminitiationform= By.xpath("//app-ms7-system-initiated-claim");
    public static By BeneficiaryTypeDropdown= By.xpath("//mat-select[@name='beneficiaryType']");
    public static By BeneficiaryNumber= By.xpath("//input[@name='beneficiaryIdNumber']");
    public static By BeneficiaryAccountNumber= By.xpath("//input[@name='beneficiaryAccountNumber']");
    public static By BeneficiaryAccBranchCode= By.xpath("//input[@name='beneficiaryBranchCode']");
    public static By BeneficiaryDOB= By.xpath("//input[contains(@mattooltip,'Date of birth calendar date')]");
    public static By BeneficiaryTraceFinalOutcome=By.xpath("//mat-select[@name='finalOutcome']");
    public static By BeneficiaryAdditionalComments=By.xpath("//textarea[@name='additionalComments']");
    public static By RelationtoDeceasedDropdown=By.xpath("//mat-select[@name='relationshipToDeceased']");
    public static By RelationSourceDropdown=By.xpath("//mat-select[@name='relationshipValidationSource']");

    public void SubmitBeneficiaryTracingForm(String BeneficiaryType,String BeneficiaryNo,String BeneficiaryAccount,String BeneficiaryBranchCode,String RelationToDeceased,String RelationSource,String BeneficiaryOutcome,String BeneficiaryAdditionalComments)
    {
        try
        {
            Optional<String> Beneficiarytype=Optional.ofNullable(BeneficiaryType);
            Optional<String> Beneficiaryno=Optional.ofNullable(BeneficiaryNo);
            Optional<String> Beneficiaryaccount=Optional.ofNullable(BeneficiaryAccount);
            Optional<String> BeneficiarybranchCode=Optional.ofNullable(BeneficiaryBranchCode);
            Optional<String> RelationtoDeceased=Optional.ofNullable(RelationToDeceased);
            Optional<String> Relationsource=Optional.ofNullable(RelationSource);
            Optional<String> Beneficiaryoutcome=Optional.ofNullable(BeneficiaryOutcome);
            Optional<String> BeneficiaryAdditionalcomments=Optional.ofNullable(BeneficiaryAdditionalComments);


            waitforPageload();
            ScollToElement(BeneficiaryTracing.ExpandClientInitiationform);
            ClickElement(BeneficiaryTracing.ExpandClientInitiationform);
            if(!Beneficiarytype.get().isEmpty())
                ClickElement(BeneficiaryFormDropdown(BeneficiaryTracing.BeneficiaryTypeDropdown,Beneficiarytype.get()));
            if(!Beneficiaryno.get().isEmpty())
                SendText(BeneficiaryTracing.BeneficiaryNumber,Beneficiaryno.get());
            if(!Beneficiaryaccount.get().isEmpty())
                SendText(BeneficiaryTracing.BeneficiaryAccountNumber,Beneficiaryaccount.get());
            if(!BeneficiarybranchCode.get().isEmpty())
                SendText(BeneficiaryTracing.BeneficiaryAccBranchCode,BeneficiarybranchCode.get());
            if(!RelationtoDeceased.get().isEmpty())
                ClickElement(BeneficiaryFormDropdown(BeneficiaryTracing.RelationtoDeceasedDropdown,RelationtoDeceased.get()));
            if(!Relationsource.get().isEmpty())
                ClickElement(BeneficiaryFormDropdown(BeneficiaryTracing.RelationSourceDropdown,Relationsource.get()));
            ClickElement(VOPD.VOPDFinalOutcome(Beneficiaryoutcome.get()));
            if(!BeneficiaryAdditionalcomments.get().isEmpty())
                SendText(ClaimDeclinePage.AdditionalComments, BeneficiaryAdditionalcomments.get());
        }
        catch (Exception e)
        {
          System.out.println(e.getMessage());
        }

    }

    public WebElement BeneficiaryFormDropdown(By locator,String str)
    {
        WebElement wb=null;
        try
        {
            ClickElement(locator);
            wait=new WebDriverWait(driver,2000);
            wb=wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//mat-option//span[contains(text(),'"+str+"')]")));
        }
        catch (Exception e)
        {
            System.out.println(e.getMessage());
        }
        return  wb;
    }


    public WebElement BeneficiaryFinalOutcome(String str)
    {
        WebElement wb=null;
        try
        {
            ClickElement(VOPDDeathValidations.DeathFinalOutcome);
            wb=wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//span[contains(text(),'"+str+"')]")));
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
        return  wb;
    }


}
