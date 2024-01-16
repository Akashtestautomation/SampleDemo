package PageRepository;

import Methods.GenericMethod;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.util.Optional;

public class BeneficiaryAccountValidation_8B extends GenericMethod {

    VOPDDeathValidations VOPD=new VOPDDeathValidations();
    HomePage home=new HomePage();

    public static By BeneficiaryTypeDropdown= By.xpath("//mat-select[@name='beneficiaryType']");

    public WebElement BeneficiaryFormDropdown(By locator, String str)
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

    public boolean SubmitBeneficiaryForm_8B(String BeneficiaryType,String BeneficiaryNo,String BeneficiaryAccount,String BeneficiaryBranchCode,String RelationToDeceased,String RelationSource,String BeneficiaryOutcome,String BeneficiaryAdditionalComments) {
        boolean flag = false;
        try {
            Optional<String> Beneficiarytype = Optional.ofNullable(BeneficiaryType);
            Optional<String> Beneficiaryno = Optional.ofNullable(BeneficiaryNo);
            Optional<String> Beneficiaryaccount = Optional.ofNullable(BeneficiaryAccount);
            Optional<String> BeneficiarybranchCode = Optional.ofNullable(BeneficiaryBranchCode);
            Optional<String> RelationtoDeceased = Optional.ofNullable(RelationToDeceased);
            Optional<String> Relationsource = Optional.ofNullable(RelationSource);
            Optional<String> Beneficiaryoutcome = Optional.ofNullable(BeneficiaryOutcome);
            Optional<String> Beneficiaryadditionalcomments = Optional.ofNullable(BeneficiaryAdditionalComments);

            waitforPageload();
            if (!Beneficiarytype.get().isEmpty())
                ClickElement(BeneficiaryFormDropdown(BeneficiaryTracing.BeneficiaryTypeDropdown, Beneficiarytype.get()));
            if (!Beneficiaryno.get().isEmpty())
                SendText(BeneficiaryTracing.BeneficiaryNumber, Beneficiaryno.get());
            if (!Beneficiaryaccount.get().isEmpty())
                SendText(BeneficiaryTracing.BeneficiaryAccountNumber, Beneficiaryaccount.get());
            if (!BeneficiarybranchCode.get().isEmpty())
                SendText(BeneficiaryTracing.BeneficiaryAccBranchCode, BeneficiarybranchCode.get());
            if (!RelationtoDeceased.get().isEmpty())
                ClickElement(BeneficiaryFormDropdown(BeneficiaryTracing.RelationtoDeceasedDropdown, RelationtoDeceased.get()));
            if (!Relationsource.get().isEmpty())
                ClickElement(BeneficiaryFormDropdown(BeneficiaryTracing.RelationSourceDropdown, RelationtoDeceased.get()));

            ClickElement(VOPD.VOPDFinalOutcome(BeneficiaryOutcome));
            SendText(VOPDDeathValidations.DeathAdditionalComments, BeneficiaryAdditionalComments);
            flag = home.SubmitClaim(HomePage.SubmitFormNotification);
            if (flag)
                return flag = true;

        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        return flag;
    }

}
