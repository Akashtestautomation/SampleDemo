package PageRepository;

import Methods.GenericMethod;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Wait;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.sql.Struct;
import java.util.List;
import java.util.Optional;

public class ComplexClaimAssessment extends GenericMethod {


    HomePage home=new HomePage();

    public  String [] FinalOutcomeDecision={"Approve Claim","Decline Claim","Refer to Fraud","Refer to Management","Close Claim (NTU)"};

    //----------------------Final outcome options----------------------------------------
    public final static String Approve_Claim="Approve Claim";
    public final static String Decline_Claim="Decline Claim";
    public final static String ReferToFraud="Refer to Fraud";
    public final static String ReferToManagement="Refer to Management";
    public final static String Close_Claim="Close Claim (NTU)";

    //----------------------Fraud Reason options----------------------------------------
    public final static String  Potential_Fraud_Suspected ="Potential Fraud Suspected";
    public final static String Fraud_Input_Required  ="Fraud Input Required";
    public final static  String Policy_Abuse ="Policy Abuse";
    public final static  String Early_Duration ="Early Duration";
    public final static  String Relationship_Definition_Not_Met ="Relationship Definition Not Met";
    public final static  String Sum_Assured_R100k ="Sum Assured  >= R100k";

    //----------------------Close Reason options----------------------------------------
    public final static String  Client_Requested_Cancellation="Client Requested Cancellation";
    public final static String  Insufficient_Information="Insufficient Information";

    //BI form
    //public static By BI1663Status=By.xpath("//app-documents-validations//mat-select");
    public static By BI1663Status=By.xpath("//mat-label[text()= ' BI1663 - Notice of Death ']//following::mat-label[contains(text(),'Status')]");
    public static By BI1663Reference=By.xpath("//mat-label[contains(text(),'BI1663 - Notice of Death')]//following::input[contains(@name,'refNumber1')]");

    //Medical form
    public static By MedicalPractitionerType=By.xpath("//app-medical-validations//mat-select[@name='medicalPractitionerType']");
    public static By Province=By.xpath("//app-medical-validations//mat-select[@name='province']");
    public static By RegistrationNumber=By.xpath("//app-medical-validations//input[@name='registrationNumber']");
    public static By PractitionerName=By.xpath("//app-medical-validations//input[@name='name']");
    public static By PractitionerSurname=By.xpath("//app-medical-validations//input[@name='surname']");
    public static By PracticeName=By.xpath("//app-medical-validations//input[@name='practiceName']");
    public static By PracticeNumber=By.xpath("//app-medical-validations//input[@name='practiceNumber']");
    public static By PractitionerContact=By.xpath("//app-medical-validations//input[@name='contactNumber']");
    public static By PractitionerAddress=By.xpath("//app-medical-validations//input[@name='address']");
    public static By PracticeCity=By.xpath("//app-medical-validations//input[@name='city']");
    public static By PracticePostalCode=By.xpath("//app-medical-validations//input[@name='postalCode']");
    public static By PractitionerProvince=By.xpath("//app-medical-validations//mat-select[@name='province']");

    //Undertaker
    public static By FuneralParlourName=By.xpath("//app-undertaker-validations//input[@name='funeralParlourName']");
    public static By DHADesignationNumber=By.xpath("//input[@name='dhaDesignationNumber']");
    public static By CompanyRegistrationNumber=By.xpath("//input[@name='companyRegistrationNumber']");
    public static By SARSRegistrationNumber=By.xpath("//input[@name='sarsRegistrationNumber']");

    //Informant
    public static By InformantIDNumber=By.xpath("//app-informant-validations//input[@name='idNumber']");
    public static By InformantDOB=By.xpath("//app-informant-validations//input[@name='dateofBirth']");
    public static By InformantName=By.xpath("//app-informant-validations//input[@name='name']");
    public static By InformantSurname=By.xpath("//app-informant-validations//input[@name='surname']");
    public static By InformantAddress=By.xpath("//app-informant-validations//input[@name='residentialAddress']");
    public static By InformantCity=By.xpath("//app-informant-validations//input[@name='city']");
    public static By InformantPostalCode=By.xpath("//app-informant-validations//input[@name='postalCode']");
    public static By InformantNameProvince=By.xpath("//app-informant-validations//mat-select[@name='province']");
    public static By InformantNameRelationship=By.xpath("//app-informant-validations//mat-select[@name='relationshipToDeceased']");
    public static By InformantContact=By.xpath("//app-informant-validations//input[@name='contactNumber']");

    //Induna

    public static By IndunaCauseOfDeath=By.xpath("//app-induna-validations//mat-label[text()=' Cause of Death ']");
    public static By IndunaPlaceOfDeath=By.xpath("//app-induna-validations//mat-label[contains(text(),' Place of Death Description ')]");
    public static By IndunaSourceOfDeath=By.xpath("//app-induna-validations//mat-label[contains(text(),' Cause of Death Source ')]");
    public static By IndunaName=By.xpath("//app-induna-validations//input[@name='name']");
    public static By IndunaSurname=By.xpath("//app-induna-validations//input[@name='surname']");
    public static By IndunaID=By.xpath("//app-induna-validations//input[@name='idNumber']");
    public static By IndunaContactNumber=By.xpath("//app-induna-validations//input[@name='contactNumber']");
    public static By IndunaAddress=By.xpath("//app-induna-validations//input[@name='address']");
    public static By IndunaCity=By.xpath("//app-induna-validations//input[@name='city']");
    public static By IndunaPostalCode=By.xpath("//app-induna-validations//input[@name='postalCode']");
    public static By IndunaProvince=By.xpath("//app-induna-validations//mat-label[text()=' Province ']");

    //Validation Source
    public static By ValidationOutcome=By.xpath("//app-undertaker-validations//mat-label[contains(text(),'Validation Outcome')]");
    public static By ValidationSourceReference=By.xpath("//app-undertaker-validations//mat-label[contains(text(),'Source Reference')]");
    public static By ValidationSource=By.xpath("//app-undertaker-validations//mat-label[contains(text(),'Validation Source')]");

    public static By RecalculateRiskScore=By.xpath("//mat-label[contains(text(),'Recalculate Risk Score')]");
    public static By FinalOutcome=By.xpath("//mat-select[@name='finalOutcomeDecision']");
    public static By FinalReason=By.xpath("//mat-select[@name='decisionReason']");
    public static By ConfirmationCheckbox=By.xpath("//span[@class='mat-checkbox-inner-container']");
    public static By ComplexAssessmentResults=By.xpath("//textarea[@name='systemComments']");
    public static By FraudAssessmentResults=By.xpath("//textarea[@name='fraudComments']");

    public WebElement ValidationOutcome(Enum formName)
    {return driver.findElement(By.xpath("//app-"+formName.toString()+"-validations//mat-label[contains(text(),'Validation Outcome')]"));}

    public WebElement ValidationSource(Enum formName)
    {return driver.findElement(By.xpath("//app-"+formName.toString()+"-validations//mat-label[contains(text(),'Validation Source')]"));}

    public WebElement ValidationSourceReference(Enum formName)
    {return driver.findElement(By.xpath("//app-"+formName.toString()+"-validations//input[contains(@name,'sourceReferenceNumber')]"));}

    public WebElement ViewFormDetails(Enum formName)
    {return driver.findElement(By.xpath("//app-"+formName.toString()+"-validations"));}

    public WebElement GetDropdownItem(String Value)
    {return wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//mat-label[text()=' BI1663 - Notice of Death ']//following::span[(text()='"+" "+Value+" "+"')]")));}

    public WebElement ExpandForm(String  FormName)
    {return wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//div[contains(text(),'"+FormName+"')]/../mat-icon")));}

    public WebElement ValidationsComments(Enum  FormName)
    {return wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//app-"+FormName.toString()+"-validations//textarea")));}

    public WebElement SelectDecision(String  outcome)
    {return wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//span[contains(text(),'"+outcome+"')]")));}



    public WebElement SubmitValidationResult(String str)
    {
        WebElement wb=null;
        try
        {
            ClickElement(VOPDDeathValidations.CauseOfDescription);
            wb=wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//mat-option//span[contains(text(),'" + str + "')]")));
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
        return  wb;
    }

    public WebElement DocumentRequireForm(String DocumentStatus, String ReferenceNo)
    {
        WebElement wb=null;
        try {
            ClickElement(ExpandForm(HomePage.DocumentRequiredForm));
            ScollToElement(ViewFormDetails(ValidationForm.documents));
            ClickElement(BI1663Status);
            wb=GetDropdownItem(DocumentStatus);
            ClickElement(wb);
            SendText(BI1663Reference,ReferenceNo);
        }
        catch (Exception e)
        {
            System.out.println(e.getMessage());
        }
        return wb;
    }


    public WebElement MedicalPractitionerForm(String PractitionerType, String HPCSARegNo, String Name, String SurName, String NameOfPractice, String PracNumber, String PracticeContact, String BusinessAddress, String PracCity, String PostalCode, String Provice)
    {
        WebElement wb=null;
        try {
            ClickElement(ExpandForm(HomePage.MedicalPractitionerForm));
            ScollToElement(ViewFormDetails(ValidationForm.medical));

            ClickElement(MedicalPractitionerType);
            ClickElement(GetDropdownItem(PractitionerType));

            SendText(RegistrationNumber,HPCSARegNo);
            SendText(PractitionerName,Name);
            SendText(PractitionerSurname,SurName);
            SendText(PracticeName,NameOfPractice);
            SendText(PracticeNumber,PracNumber);
            SendText(PractitionerContact,PracticeContact);
            SendText(PractitionerAddress,BusinessAddress);
            SendText(PracticeCity,PracCity);
            SendText(PracticePostalCode,PostalCode);
            ClickElement(PractitionerProvince);
            wb=GetDropdownItem(Provice);
            ClickElement(wb);

        }
        catch (Exception e)
        {
            System.out.println(e.getMessage());
        }
        return wb;
    }

    public WebElement FuneralUndertakerForm(String ParlourName, String DHADesignationNo, String CompanyRegistrationNo, String SARSRegistrationNo)
    {
        WebElement wb=null;
        try {
            ClickElement(ExpandForm(HomePage.FuneralUndertakerFrom));
            ScollToElement(ViewFormDetails(ValidationForm.undertaker));

            SendText(FuneralParlourName,ParlourName);
            SendText(DHADesignationNumber,DHADesignationNo);
            SendText(CompanyRegistrationNumber,CompanyRegistrationNo);
            SendText(SARSRegistrationNumber,SARSRegistrationNo);
        }
        catch (Exception e)
        {
            System.out.println(e.getMessage());
        }
        return wb;
    }

    public WebElement InformantForm(String InformantID,String Informant_DOB, String Informant_Name, String Informant_Surname, String ResidentAddress, String City, String Postalcode,String Province, String RelationToDecease, String Contact)
    {
        WebElement wb=null;
        try {
            ClickElement(ExpandForm(HomePage.InformantFrom));
            ScollToElement(ViewFormDetails(ValidationForm.informant));

            SendText(InformantIDNumber,InformantID);
            EnterValue(InformantDOB,Informant_DOB);
            SendText(InformantName,Informant_Name);
            SendText(InformantSurname,Informant_Surname);
            SendText(InformantAddress,ResidentAddress);
            SendText(InformantCity,City);
            SendText(InformantPostalCode,Postalcode);
            ClickElement(InformantNameProvince);
            wb=GetDropdownItem(Province);
            ClickElement(wb);
            ClickElement(InformantNameRelationship);
            wb=GetDropdownItem(RelationToDecease);
            ClickElement(wb);

            SendText(InformantContact,Contact);

        }
        catch (Exception e)
        {
            System.out.println(e.getMessage());
        }
        return wb;
    }

    public WebElement IndunaForm(String causeOfDeath, String placeOfDeath, String SourceOFDeath, String Name, String Surname, String IDNumber, String contactnumber, String businessAddress, String City, String postalCode, String Province)
    {
        WebElement wb=null;
        try {
            ClickElement(ExpandForm(HomePage.InndunaFrom));
            ScollToElement(ViewFormDetails(ValidationForm.induna));

            ClickElement(IndunaCauseOfDeath);
            ClickElement(GetDropdownItem(causeOfDeath));

            ClickElement(IndunaPlaceOfDeath);
            ClickElement(GetDropdownItem(placeOfDeath));

            ClickElement(IndunaSourceOfDeath);
            ClickElement(GetDropdownItem(SourceOFDeath));

            SendText(IndunaName,Name);
            SendText(IndunaSurname,Surname);
            SendText(IndunaID,IDNumber);
            SendText(IndunaContactNumber,contactnumber);
            SendText(IndunaAddress,businessAddress);
            SendText(IndunaCity,City);
            SendText(IndunaPostalCode,postalCode);
            ClickElement(IndunaProvince);
            wb=GetDropdownItem(Province);
            ClickElement(wb);

        }
        catch (Exception e)
        {
            System.out.println(e.getMessage());
        }
        return wb;
    }


    public void SubmitValidationsResult(Enum FormName, String Outcome, String Source, String ReferenceNo, String ValidationComments)
    {

        WebElement wb=null;
        try {
            ClickElement(ValidationOutcome(FormName));
            wb=home.ValidationResultOptions(FormName,Outcome);
            ClickElement(wb);

            ClickElement(ValidationSource(FormName));
            wb=home.ValidationResultOptions(FormName,Source);
            ClickElement(wb);

            SendText(ValidationSourceReference(FormName),ReferenceNo);
            SendText(ValidationsComments(FormName),ValidationComments);
        }
        catch (Exception e)
        {
            System.out.println(e.getMessage());
        }
    }

    public boolean RecalculateRisk(String SubmitNotification)
    {
        boolean flag=false;
        try{
            ClickElement(ComplexClaimAssessment.RecalculateRiskScore);
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

    public void SelectFinalDecisionAndReason(String FinalDecision, String Reason)
    {
        try {
            if(FinalDecision.equalsIgnoreCase("Approve Claim")) {
                waitforPageload();
                ClickElement(FinalOutcome);
                ClickElement(SelectDecision(FinalDecision));
            }
            else {
                waitforPageload();
                ClickElement(FinalOutcome);
                ClickElement(SelectDecision(FinalDecision));
                wait.until(ExpectedConditions.visibilityOfElementLocated(FinalReason));
                ClickElement(FinalReason);
                ClickElement(SelectDecision(Reason));
            }
        }
        catch (Exception e) {

            // TODO: handle exception
            System.out.println(e.getMessage());
        }
    }

    public void SelectFinalDecisionAndReason(String FinalDecision)
    {
        try {
            if(FinalDecision.equalsIgnoreCase("Approve Claim")) {
                waitforPageload();
                ClickElement(FinalOutcome);
                ClickElement(SelectDecision(FinalDecision));
            }
        }
        catch (Exception e) {
            // TODO: handle exception
            System.out.println(e.getMessage());
        }
    }


    public enum ValidationForm
    {
        documents,
        medical,
        undertaker,
        saps,
        relationship,
        informant,
        induna
    }


    public void SubmitMS5MandatoryValidationsForm(String MS5DocumentStatus, String MS5DocumentReference,String	MS5PractitionerType, String	MS5HPCSANumber, String	MS5PractitionerName, String	MS5PractitionerSurname, String	MS5FacilityName, String	MS5PractitionerNumber, String	MS5PracticeContact	, String MS5BusinessAddress, String	MS5PracticeCity, String	MS5PracticePostalcode, String MS5PracticeProvice, String MS5PractitionerValidationOutcome, String MS5PractitionerValidationSource, String MS5PractionerReferenceNo	, String MS5PractitionerComments, String MS5FuneralParlourName, String	MS5FuneralDHANo, String	MS5FuneralCompanyRegistrationNo, String	MS5FuneralSARSNo, String	MS5FuneralValidationOutcome, String	MS5FuneralValidationSource, String	MS5FuneralReferenceNo, String	MS5FuneralComments,String MS5InformantID,String	MS5InformantDOB,String	MS5InformantName,String	MS5InformantSurname,String	MS5InformantAddress,String	MS5InformantCity,String	MS5InformantPostalCode,String	MS5InformantProvince,String	MS5InformantRelationToDeceased,String	MS5InformantContact,String	MS5InformantValidationOutcome,String	MS5InformantValidationSource,String	MS5InformantReferenceNo,String	MS5InformantComments,String MS5IndunaCauseofDeath,String	MS5IndunaPlaceOfDeath,String	MS5IndunaDeceaseSource,String	MS5IndunaName,String	MS5IndunaSurname,String	MS5IndunaID,String	MS5IndunaContactNo,String	MS5IndunaAddress,String	MS5IndunaCity,String	MS5IndunaPostalCode,String	MS5IndunaProvince,String	MS5IndunaValidationOutcome,String	MS5IndunaValidationSource,String	MS5IndunaReferenceNo,String	MS5IndunaComments)
    {
        try{
            waitforPageload();
            //DocumentRequireForm("Received","R2653");
            DocumentRequireForm(MS5DocumentStatus,MS5DocumentReference);

            MedicalPractitionerForm(MS5PractitionerType,	MS5HPCSANumber,	MS5PractitionerName,	MS5PractitionerSurname,	MS5FacilityName,	MS5PractitionerNumber,	MS5PracticeContact,	MS5BusinessAddress,	MS5PracticeCity,	MS5PracticePostalcode,	MS5PracticeProvice);
            SubmitValidationsResult(ComplexClaimAssessment.ValidationForm.medical,MS5PractitionerValidationOutcome,  MS5PractitionerValidationSource,  MS5PractionerReferenceNo,  MS5PractitionerComments);
            //MedicalPractitionerForm("Pathologist","H76tw7","John","Smith","General","P12461","7124132345","Old Road","Joberg", "4010","Free State");
            //SubmitValidationsResult(ComplexClaimAssessment.ValidationForm.medical,"Pass","Leelo","Medical form updated","Medical Practitioner form submitted");
            FuneralUndertakerForm( MS5FuneralParlourName, 	MS5FuneralDHANo, 	MS5FuneralCompanyRegistrationNo, 	MS5FuneralSARSNo);
            SubmitValidationsResult(ComplexClaimAssessment.ValidationForm.undertaker,MS5FuneralValidationOutcome, 	MS5FuneralValidationSource, 	MS5FuneralReferenceNo, MS5FuneralComments);
            //FuneralUndertakerForm("General","DHA145","C1524","SARS4131");
            //SubmitValidationsResult(ComplexClaimAssessment.ValidationForm.undertaker,"Pass","HPCSA Portal","Undertaker form updated","Undertaker form submitted");
            InformantForm(MS5InformantID,	MS5InformantDOB,	MS5InformantName,	MS5InformantSurname,	MS5InformantAddress,	MS5InformantCity,	MS5InformantPostalCode,	MS5InformantProvince,	MS5InformantRelationToDeceased,	MS5InformantContact );
            SubmitValidationsResult(ComplexClaimAssessment.ValidationForm.informant,MS5InformantValidationOutcome,	MS5InformantValidationSource,	MS5InformantReferenceNo,	MS5InformantComments);

            //InformantForm("7401014800083","1996-10-10","Adam","joy","R1451","Kimberley","6001","Northern Cape","Cousin","7125142561");
            //SubmitValidationsResult(ComplexClaimAssessment.ValidationForm.informant,"Waive","XDS Portal","R15156","Informant form submitted");

            //IndunaForm("Natural","Hospital","Telephonic Call with Doctor/Nurse","Sam","Peter","7007036608089","7123435245","New Lake","Capetown","4001","Northern Cape");
            IndunaForm(MS5IndunaCauseofDeath,MS5IndunaPlaceOfDeath,MS5IndunaDeceaseSource,MS5IndunaName,MS5IndunaSurname,MS5IndunaID,MS5IndunaContactNo,MS5BusinessAddress,MS5IndunaCity,MS5IndunaPostalCode,MS5IndunaProvince);
            SubmitValidationsResult(ValidationForm.induna,MS5IndunaValidationOutcome,MS5IndunaValidationSource,MS5IndunaReferenceNo,MS5IndunaComments);

        }
        catch (Exception e) {
            System.out.println(e.getMessage());
        }

    }
}
