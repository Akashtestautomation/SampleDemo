package PageRepository;

import Methods.GenericMethod;
import org.apache.xmlbeans.impl.xb.xsdschema.Public;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.sql.Driver;
import java.util.List;

public class BeneficiaryAccountValidation extends GenericMethod {

    VOPDDeathValidations VOPD=new VOPDDeathValidations();
    HomePage home=new HomePage();
    //public static By BeneficiaryTypeDropdown= By.xpath("//mat-select[@name='beneficiaryType']");
    public static By BeneficiaryTypeDropdown= By.xpath("//mat-label[contains(text(),'Beneficiary Type')]");
    public static By BeneficiaryNumber= By.xpath("//input[@name='beneficiaryIdNumber']");
    //public static By BeneficiaryAccountType= By.xpath("//mat-select[@name='accountType']");
    public static By BeneficiaryAccountType= By.xpath("//mat-label[(text()=' Account Type ')]");
    //public static By BeneficiaryBankName= By.xpath("//input[@name='bankName']");
    public static By BeneficiaryBankName= By.xpath("//mat-select[@name='bankName']");
    public static By BeneficiaryAccountNumber= By.xpath("//input[@name='beneficiaryAccountNumber']");
    public static By BeneficiaryAccBranchCode= By.xpath("//input[@name='beneficiaryBranchCode']");
    public static By BeneficiaryDOB= By.xpath("//input[contains(@mattooltip,'Date of birth calendar date')]");
    public static By RelationtoDeceasedDropdown=By.xpath("//mat-select[@name='relationshipToDeceased']");
    public static By RelationSourceDropdown=By.xpath("//mat-select[@name='relationshipValidationSource']");
    public static By BeneficiaryTraceFinalOutcome=By.xpath("//mat-select[@name='finalOutcome']");
    public static By BeneficiaryAdditionalComments=By.xpath("//textarea[@name='additionalComments']");
    public static By BeneficiaryAccountExist=By.xpath("//mat-label[contains(text(),'Account Exists')]");
    public static By BeneficiaryAccountAcceptPayment=By.xpath("//mat-label[contains(text(),'Account Accepts Payments')]");
    public static By IsBeneficiaryAccountHolder=By.xpath("//mat-label[contains(text(),'Beneficiary is the Account Holder')]");
    public static By ISBenficiaryAccountTypeValid=By.xpath("//mat-label[contains(text(),'Account Type is Valid')]");
    public static By IsBeneficiaryAccountOpen=By.xpath("//mat-label[contains(text(),'Account is Open')]");
    public static By ISBenficiaryAccOpen3Months=By.xpath("//mat-label[contains(text(),'Account Open > 3 Months')]");
    public static By BeneficiaryDetailSection=By.xpath("//span[contains(text(),'Beneficiary Details')]");
    public static By HyphenAccountResult=By.xpath("//textarea[@name='beneficiaryValidation']");

    public WebElement BeneficiaryAccountFormDropdown(By locator,String str)
    {
        WebElement wb=null;
        try
        {
            ClickElement(locator);
            wait=new WebDriverWait(driver,1000);
            wb=wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//mat-option//span[contains(text(),'"+str+"')]")));
        }
        catch (Exception e)
        {
            System.out.println(e.getMessage());
        }
        return  wb;
    }

    public WebElement SelectAccountValidationFields(By Field, String Value)
    {
        WebElement wb=null;
        wait=new WebDriverWait(driver,3000);

        try {
            String AccountField=driver.findElement(Field).getText();
            switch (AccountField)
            {
                case "Account Exists" :  ClickElement(BeneficiaryAccountExist);
                    wb=wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//span[contains(text(),'"+ Value +"')]")));
                    break;
                case "Account Accepts Payments"  :  ClickElement(driver.findElement(BeneficiaryAccountAcceptPayment));
                    wb=wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//span[contains(text(),'"+ Value +"')]")));
                    break;
                case "Beneficiary is the Account Holder" : ClickElement(driver.findElement(IsBeneficiaryAccountHolder));
                    wb=wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//span[contains(text(),'"+ Value +"')]")));
                    break;
                case "Account Type is Valid" : ClickElement(driver.findElement(ISBenficiaryAccountTypeValid));
                    wb=wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//span[contains(text(),'"+ Value +"')]")));
                    break;
                case "Account is Open" : ClickElement(driver.findElement(IsBeneficiaryAccountOpen));
                    wb=wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//span[contains(text(),'"+ Value +"')]")));
                    break;
                case " Account Open > 3 Months" : ClickElement(driver.findElement(ISBenficiaryAccOpen3Months));
                    wb=wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//span[contains(text(),'"+ Value +"')]")));
                    break;
            }
        }
        catch (Exception e)
        {
            System.out.println(e.getMessage());
        }
        return wb;
    }


    public boolean SubmitAccountValidationForm(String HyphenBeneficiaryType,String	HyphenBeneficiaryBankName,String HyphenBeneficiaryNumber,String	HyphenBeneficiaryAccountType,String	HyphenBeneficiaryAccountNumber,String HyphenBeneficiaryAccBranchCode,String	HyphenRelationtoDeceasedDropdown,String	HyphenRelationSource,String	HyphenRelationshipSource,String	HyphenValidationSource,String HyphenStatus,String	HyphenValidationOutcome,String	BeneficiaryAccountexist,String	BeneficiaryAccountAcceptpayment,String	IsBeneficiaryAccountholder,String	IsBenficiaryAccountTypevalid,String	IsBeneficiaryAccountopen,String	IsBenficiaryAccOpen3months,String HyphenFinalOutcome,String HyphenAdditionalComments)
    {
        boolean flag=false;
        try {
            waitforPageload();
            Thread.sleep(3000);
            ScollToElement(BeneficiaryAccountValidation.BeneficiaryDetailSection);
            List<WebElement> wb = driver.findElements(By.xpath("//mat-error"));
            WebElement FieldName = null;
            for (WebElement elements : wb) {
                String MandatoryField = elements.getText();
                System.out.println(MandatoryField);
                if (MandatoryField.contains("Beneficiary type"))
                    ClickElement(BeneficiaryAccountFormDropdown(BeneficiaryAccountValidation.BeneficiaryTypeDropdown, HyphenBeneficiaryAccountType));
                else if (MandatoryField.contains("Bank name"))
                    ClickElement(BeneficiaryAccountFormDropdown(BeneficiaryAccountValidation.BeneficiaryBankName, HyphenBeneficiaryBankName));
                else if (MandatoryField.contains("Id number"))
                    ClickElement(BeneficiaryAccountFormDropdown(BeneficiaryAccountValidation.BeneficiaryNumber, HyphenBeneficiaryNumber));
                else if (MandatoryField.contains("Account type"))
                    ClickElement(BeneficiaryAccountFormDropdown(BeneficiaryAccountValidation.BeneficiaryAccountType, HyphenBeneficiaryAccountType));
                else if (MandatoryField.contains("Account number"))
                    SendText(BeneficiaryAccountValidation.BeneficiaryAccountNumber, HyphenBeneficiaryAccountNumber);
                else if (MandatoryField.contains("Branch Code"))
                    SendText(BeneficiaryAccountValidation.BeneficiaryAccBranchCode, HyphenBeneficiaryAccBranchCode);
                else if (MandatoryField.contains("Relationship to deceased"))
                    ClickElement(BeneficiaryAccountFormDropdown(BeneficiaryAccountValidation.RelationtoDeceasedDropdown, HyphenRelationtoDeceasedDropdown));
                else if (MandatoryField.contains("Relationship validation"))
                    ClickElement(BeneficiaryAccountFormDropdown(BeneficiaryAccountValidation.RelationSourceDropdown, HyphenRelationSource));
                else if (MandatoryField.contains("Validation Source")) {
                    ClickElement(VOPD.SelectDeathValidationRequirement("validation source", HyphenValidationSource));
                    ClickElement(VOPD.SelectDeathValidationRequirement("status", HyphenStatus));
                    ClickElement(VOPD.SelectDeathValidationRequirement("validation outcome", HyphenValidationOutcome));
                    break;
                }
            }
        }
        catch (Exception e) {
            System.out.println(e.getMessage());
        }

        ClickElement(BeneficiaryAccountFormDropdown(BeneficiaryAccountExist,BeneficiaryAccountexist));
        ClickElement(BeneficiaryAccountFormDropdown(BeneficiaryAccountAcceptPayment,BeneficiaryAccountAcceptpayment));
        ClickElement(BeneficiaryAccountFormDropdown(IsBeneficiaryAccountHolder,IsBeneficiaryAccountholder));
        ClickElement(BeneficiaryAccountFormDropdown(ISBenficiaryAccountTypeValid,IsBenficiaryAccountTypevalid));
        ClickElement(BeneficiaryAccountFormDropdown(IsBeneficiaryAccountOpen,IsBeneficiaryAccountopen));
        ClickElement(BeneficiaryAccountFormDropdown(ISBenficiaryAccOpen3Months,IsBenficiaryAccOpen3months));

        ClickElement(VOPD.VOPDFinalOutcome(HyphenFinalOutcome));
        SendText(VOPDDeathValidations.DeathAdditionalComments, HyphenAdditionalComments);
        flag=home.SubmitClaim(HomePage.SubmitFormNotification);
        if(flag)
            return flag=true;
        return  flag;
    }

}
