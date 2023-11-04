package PageRepository;

import Methods.GenericMethod;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;

import java.util.List;

public class FraudDesktopAssessment extends GenericMethod {

    public static By FraudOutcomeCategory = By.xpath("//mat-select[@name='fraudOutcome']");
    public static By FraudOutcomeSubCategory = By.xpath("//mat-select[@name='decisionReason']");
    public static By FraudRecommendedClaimOutcome = By.xpath("//mat-select[@name='claimOutcome']");
    public static By FraudAssessmentOutcomesList = By.xpath("//span[@class='mat-option-text']");
    public static By FraudComplexityType = By.xpath("//mat-select[@name='complexityType']");
    public static By FraudAdditionalText = By.xpath("//textarea[@name='additionalComments']");
    public static By FieldInvestigatorList = By.xpath("//table//th[text()=' Employee Number ']//following::tr/td[3]");

    //----------------------Fraud Outcome----------------------------------------
    public final static String Invalid_Referral = "Invalid Referral";
    public final static String NoFraudDetected = "No Fraud Detected/Confirmed";
    public final static String Fraud_Confirmed = "Fraud Confirmed";
    public final static String ReferToFieldInvestigator = "Refer to Field Investigator";
    public final static String ReferToManagement = "Refer to Management";
    public final static String Sum_Assured_R100k = "Sum Assured  >= R100k";

   //-----------------------Case Complexity Type-----------------------------------
   public final static String  Simple = "Simple";
   public final static String Complex = "Complex";

   //-----------------------Recommended claim outcome------------------------------
   public final static String Approved_Recommended = "Approve Recommended";
   public final static String Decline_Recommended = "Decline Recommended";

   //----------------------Confirm Fraud Categories--------------------------------
   public final static String Internal_Multiple_Beneficiary="Policy Abuse - Internal Multiple Beneficiary";
   public final static String External_Multiple_Beneficiary="Policy Abuse - External Multiple Beneficiary";
   public final static String MisrepresentationByPH="Misrepresentation/Material Non-Disclosure by Policy Holder";
   public final static String MisrepresentationByClaimant="Misrepresentation/Material Non-Disclosure by Claimant";
   public final static String Fraudulent_Documentation="Fraudulent Documentation";
   public final static String SyndicateInvolvement="Syndicate Involvement in Death";
   public final static String ClaimantInvolvement="Claimant Involvement in Death";
   public final static String Fraudulent_Reinstatement="Fraudulent Reinstatement";
   public final static String Application_Fraud="Application Fraud (by beneficiary at sales stage)";

    public static By MS6AManualAssessmentResult=By.xpath("//textarea[@name='manualComments']");

    public WebElement SelectDecision(String  Outcome)
    {return wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//span[@class='mat-option-text' and contains(text(),'"+Outcome+"')]")));}


    public void SelectFinalDecisionOutcome(String FruadOutcome, String ComplexityType)
    {
        try {
                if(FruadOutcome=="Refer to Management" || FruadOutcome=="Invalid Referral") {
                    ClickElement(FraudOutcomeCategory);
                    ClickElement(SelectDecision(FruadOutcome));
                    ClickElement(FraudComplexityType);
                    ClickElement(SelectDecision(ComplexityType));
                }
        }
        catch (Exception e) {
            // TODO: handle exception
            System.out.println(e.getMessage());
        }
    }
    public void SelectFinalDecisionOutcome(String FruadOutcome,String ClaimOutcome ,String ComplexityType)
    {
        try {
            if(FruadOutcome=="No Fraud Detected/Confirmed") {
                ClickElement(FraudOutcomeCategory);
                ClickElement(SelectDecision(FruadOutcome));
                ClickElement(FraudRecommendedClaimOutcome);
                ClickElement(SelectDecision(ClaimOutcome));
                ClickElement(FraudComplexityType);
                ClickElement(SelectDecision(ComplexityType));
            }
            else if(FruadOutcome=="Refer to Field Investigator")
            {
                ClickElement(FraudOutcomeCategory);
                ClickElement(SelectDecision(FruadOutcome));
                List <WebElement> EmployeeIDs =driver.findElements(FieldInvestigatorList);
                for(WebElement EmployeeID : EmployeeIDs) {
                    String GetEMP_ID = EmployeeID.getText();
                    if (ClaimOutcome.equals(GetEMP_ID)) {
                        ClickElement(EmployeeID);break;
                    }
                }
                ClickElement(FraudComplexityType);
                ClickElement(SelectDecision(ComplexityType));

            }
        }
        catch (Exception e) {
            // TODO: handle exception
            System.out.println(e.getMessage());
        }
    }

    public void SelectFinalDecisionOutcome(String FruadOutcome,String Subcategory,String ClaimOutcome ,String ComplexityType)
    {
        try {
            if(FruadOutcome=="Fraud Confirmed") {
                ClickElement(FraudOutcomeCategory);
                ClickElement(SelectDecision(FruadOutcome));
                ClickElement(FraudOutcomeSubCategory);
                ClickElement(SelectDecision(Subcategory));
                ClickElement(FraudRecommendedClaimOutcome);
                ClickElement(SelectDecision(ClaimOutcome));
                ClickElement(FraudComplexityType);
                ClickElement(SelectDecision(ComplexityType));
            }
        }
        catch (Exception e) {
            // TODO: handle exception
            System.out.println(e.getMessage());
        }
    }

}
