package MainScript;
import java.lang.reflect.Method;
import java.lang.reflect.Parameter;
import java.util.*;
import java.util.List;
import Methods.EmailFunctions;
import Methods.PDFReader;
import Methods.RobotClass;
import PageRepository.*;
import org.apache.commons.mail.EmailException;
import org.testng.ITestContext;
import org.testng.annotations.*;
import Drivers.EnvironmentVariables;
import Methods.GenericMethod;

public class TestRunner extends GenericMethod {

        GenericMethod gm = new GenericMethod();
        HomePage home = new HomePage();
        FlowPage flow = new FlowPage();
        LoginPage login = new LoginPage();
        ClaimDeclinePage Decline = new ClaimDeclinePage();
        VOPDDeathValidations VOPD = new VOPDDeathValidations();
        ReviewClaimInfo MS2B=new ReviewClaimInfo();
        ReviewClaimInfo_OCR MS2A=new ReviewClaimInfo_OCR();
        GenericLocators genericLocator = new GenericLocators();
        BeneficiaryTracing BeneficiaryTrace = new BeneficiaryTracing();
        BeneficiaryAccountValidation Hyphen = new BeneficiaryAccountValidation();
        BeneficiaryAccountValidation_8B MS_8B=new BeneficiaryAccountValidation_8B();
        FraudDesktopAssessment MS6 = new FraudDesktopAssessment();
        RealTimeScreening RTS = new RealTimeScreening();
        ComplexClaimAssessment MS5 = new ComplexClaimAssessment();
        DiarizeFunction Diarise = new DiarizeFunction();
        RobotClass robot = new RobotClass();
        EmailFunctions email = new EmailFunctions();
        PDFReader pdf = new PDFReader();



    @BeforeTest
    public void InitandLaunch() throws Exception {
        gm.DeleteExistingScreenshots();
        gm.DeleteExistingLogContent();
    }

    @Test(dataProvider = "ClaimSubmission")
    public void TC_1(Map<String,String> Getvalue, Method method) throws InterruptedException, EmailException {

        try {
            test = report.createTest("Insurewrox Duplicate Test");
            gm.UpdatePolicyStatus("com.ibm.as400.access.AS400JDBCDriver", "jdbc:as400:fnbpre.fnb.co.za", Getvalue.get("Policy Number"), "FNBTTEST5", "FNBTTEST5", 6);
            gm.ClearWorkItemFromDB("com.ibm.as400.access.AS400JDBCDriver", "jdbc:as400://fnbpre.fnb.co.za;libraries=WORKFLOW;", Getvalue.get("Policy Number"), "FNBFLOW", "FNBFLOW");
            gm.DeleteClaimsFromDB("com.ibm.as400.access.AS400JDBCDriver", "jdbc:as400:fnbpre.fnb.co.za", Getvalue.get("Policy Number"), "FNBTTEST5", "FNBTTEST5");
            //email.SendEmail();

            gm.launchBrowser("chrome");
            gm.GoToURL(EnvironmentVariables.InsureworxPre);
            Boolean LoginStatus = login.LoginToInsureworx(Getvalue.get("Username"), Getvalue.get("Password"));
            if (LoginStatus)
                gm.LogPass("Insureworx Login Page", "Insureworx Login successful");
            else
                gm.LogFail("Insureworx Login Page", "Insureworx Login Unsuccessful");

            //-------------------------Upload Functionality----------------------------
            /* home.FindPolicyUnderTask(HomePage.MyTask,Policy,300);
            MS5.ClickElement(MS5.ExpandForm(HomePage.MedicalPractitionerForm));
            String path=System.getProperty("user.dir")+"\\src\\test\\java\\InputData\\Welcome Letter-FI.pdf";
            //By locator=By.xpath("//app-medical-validations/div/app-validation-result/div/div/div/app-uploadfile//span[1]/mat-icon");
            robot.UploadFiles(HomePage.MediacalFileUpload,path);
            WaitforVisibilityOFElement(HomePage.UploadedFiles);
            boolean b1=home.VerifyCRMImage(path);
            boolean b2=pdf.DownloadandVerifyCRMFile(EnvironmentVariables.DownloadFilePath+"Welcome Letter-FI.pdf");
            home.ClosePDFDoc();
            pdf.DeleteFile(EnvironmentVariables.DownloadFilePath+"Welcome Letter-FI.pdf");
            */

            //--------------------------------------------------------------------------------------

            //Boolean IsPolicySubmitted = home.SubmitClaimForm(OperationDesk, RequestChannel, ProcessCategory, SubProcessCategory, Policy, deceased, Ph, Beneficiary, Fname, Lname, "7124634535", Email, Account, Branch);
            Boolean IsPolicySubmitted=home.SubmitClaimForm(Getvalue.get("OperationDesk"), Getvalue.get("RequestChannel"), Getvalue.get("ProcessCategory"), Getvalue.get("SubProcessCategory"), Getvalue.get("Policy Number"), Getvalue.get("DeceasedID"), Getvalue.get("PolicyHolderID"), Getvalue.get("BeneficiaryID"), Getvalue.get("FirstName"), Getvalue.get("LastName"), Getvalue.get("7124634535"), Getvalue.get("EmailID"), Getvalue.get("AccountNumber"), Getvalue.get("BranchCode"));
            if (IsPolicySubmitted)
                gm.LogPass("Policy submitted successfully", "Policy submitted successfully");
            else
                gm.LogFail("Policy Not submitted successfully", "Policy Not submitted successfully");


            home.FindPolicyUnderTask(Getvalue.get("OperationDesk"),HomePage.TeamTask, Getvalue.get("Policy Number"), 800);
            home.FindPolicyUnderTask(Getvalue.get("OperationDesk"),HomePage.MyTask, Getvalue.get("Policy Number"), 800);
            String DeathValidationResult = getTextUsingJs(VOPDDeathValidations.DeathValidationResults);
            if (DeathValidationResult.equals("Auto Death Validation failed and waiver rules have NOT been applied - Please complete full Manual Validation for Deceased"))
                gm.LogPass("VOPD Outcome Validated", "VOPD Outcome Validated Successfully");
            else
                gm.LogFail("VOPD Outcome not Validation failed", "VOPD Outcome Validation Failed");
            //VOPD.SubmitVOPDValidationForm(VOPDStatus, VOPD_DOD, causeOfDeath, placeOfDeath, DeathDescription, ValidationSource, Status, ValidationOutcome, DeathValidationsComments, FinalOutcome, AdditionalComments);
            VOPD.SubmitVOPDValidationForm(Getvalue.get("VOPD_Status"), Getvalue.get("VOPD_DOD"), Getvalue.get("CauseOfDeath"),Getvalue.get("PlaceOfDeath"), Getvalue.get("DeathDescription"), Getvalue.get("ValidationSource"), Getvalue.get("Status"), Getvalue.get("ValidationOutcome"), Getvalue.get("DeathValidationComments"), Getvalue.get("VOPD_FinalOutcome"), Getvalue.get("VOPDAdditionalComments"));

            //-----------------Beneficiary Tracing--------------------
            System.out.println("Beneficiary Tracing - MS-7 Started");
            //home.FindPolicyUnderTask(HomePage.TeamTask, Policy, 300);
            //home.FindPolicyUnderTask(HomePage.MyTask, Policy, 300);
            home.FindPolicyUnderTask(Getvalue.get("OperationDesk"),HomePage.TeamTask, Getvalue.get("Policy Number"), 300);
            home.FindPolicyUnderTask(Getvalue.get("OperationDesk"),HomePage.MyTask, Getvalue.get("Policy Number"), 300);

            String BeneficiaryResult = home.getTextUsingJs(BeneficiaryTrace.BeneficiaryValidationResult);
            if (BeneficiaryResult.equals("Client Initiated Claim Request Received - The Claimant details provided does not match the Beneficiary details for the Policy. Please attempt to contact the correct Beneficiary and inform of the potential claim. Beneficiary details were retrieved from MINT."))
                gm.LogPass("Beneficiary Outcome Validated", "Beneficiary Outcome Validated Successfully");
            else
                gm.LogFail("Beneficiary Outcome not Validation failed", "Beneficiary Outcome Validation Failed");
            BeneficiaryTrace.SubmitBeneficiaryTracingForm(Getvalue.get("BeneficiaryType"), Getvalue.get("BeneficiaryNo"), Getvalue.get("BeneficiaryAccount"), Getvalue.get("BeneficiaryBranchCode"), Getvalue.get("RelationToDeceased"), Getvalue.get("RelationSource"), Getvalue.get("BeneficiaryOutcome"), Getvalue.get("BeneficiaryAdditionalComments"));

            //home.FindPolicyUnderTask(HomePage.TeamTask,Policy,800);
            //home.FindPolicyUnderTask(HomePage.MyTask,Policy,800);
            Diarise.Diarization("Documents Required"," Beneficiary form and related IDs ");
            //home.FindPolicyUnderTask(HomePage.MyTask,Policy,800);

            String ProductName = home.GetOverviewData(HomePage.OverviewTabName.Policy, HomePage.PolicyOverview.productName);
            String PolicyStatus = home.GetOverviewData(HomePage.OverviewTabName.Policy, HomePage.PolicyOverview.policyStatus);
            String PolicyNumber = home.GetOverviewData(HomePage.OverviewTabName.Policy, HomePage.PolicyOverview.policyNumber);
            String PolicyHolderID = home.GetOverviewData(HomePage.OverviewTabName.Policy, HomePage.PolicyOverview.policyHolderId);
            String DeceasedID = home.GetOverviewData(HomePage.OverviewTabName.Deceased, HomePage.DeceasedOverview.idNumber);
            String DeceasedDOB = home.GetOverviewData(HomePage.OverviewTabName.Deceased, HomePage.DeceasedOverview.dateOfBirth);
            String DeceasedStatus = home.GetOverviewData(HomePage.OverviewTabName.Deceased, HomePage.DeceasedOverview.deceasedStatus);
            String DateOfDeath = home.GetOverviewData(HomePage.OverviewTabName.Deceased, HomePage.DeceasedOverview.dateOfDeath);
            String CauseOfDeath = home.GetOverviewData(HomePage.OverviewTabName.Deceased, HomePage.DeceasedOverview.causeOfDeath);
            String PlaceOfDeath = home.GetOverviewData(HomePage.OverviewTabName.Deceased, HomePage.DeceasedOverview.placeOfDeath);

            ArrayList<String> list = new ArrayList<String>();
            list.add(ProductName);
            list.add(PolicyStatus);
            list.add(PolicyNumber);
            list.add(PolicyHolderID);
            list.add(DeceasedStatus);
            list.add(DateOfDeath);
            list.add(CauseOfDeath);
            list.add(PlaceOfDeath);

            Iterator iltr = list.iterator();
            while (iltr.hasNext()) {
                System.out.println(iltr.next());
            }
            home.SubmitClaim(HomePage.SubmitFormNotification);
            System.out.println("Beneficiary Tracing - MS-7 Ended");

            //-------------------------Beneficiary Account Validations-----------------------------------

            System.out.println("Beneficiary Account Validations - MS-8C Started");
            home.FindPolicyUnderTask(Getvalue.get("OperationDesk"),HomePage.TeamTask, Getvalue.get("Policy Number"), 300);
            home.FindPolicyUnderTask(Getvalue.get("OperationDesk"),HomePage.MyTask, Getvalue.get("Policy Number"), 300);
            String HyphenResult = home.getTextUsingJs(BeneficiaryAccountValidation.HyphenAccountResult);
            System.out.println("Client Account Validation Failed - Hyphen Unable to Complete Validation Request - Please Confirm and Validate Account Details Manually");
            if (HyphenResult.equals("Client Account Validation Failed - Hyphen Unable to Complete Validation Request - Please Confirm and Validate Account Details Manually"))
                gm.LogPass("Beneficiary Account Validations", "Beneficiary Account Validations Form Submitted Successfully");
            else
                gm.LogFail("Beneficiary Account Validations", "Beneficiary Account Validations Form Submitted Failed");
            System.out.println("Beneficiary Account Validations - MS-8C Ended");
            Hyphen.SubmitAccountValidationForm(Getvalue.get("HyphenBeneficiaryType"), Getvalue.get("HyphenBeneficiaryBankName"), Getvalue.get("HyphenBeneficiaryNumber"), Getvalue.get("HyphenBeneficiaryAccountType"), Getvalue.get("HyphenBeneficiaryAccountNumber"),Getvalue.get("HyphenBeneficiaryAccBranchCode"), Getvalue.get("HyphenRelationtoDeceasedDropdown"), Getvalue.get("HyphenRelationSource"), Getvalue.get("HyphenRelationshipSource"), Getvalue.get("HyphenValidationSource"), Getvalue.get("HyphenStatus"), Getvalue.get("HyphenValidationOutcome"), Getvalue.get("BeneficiaryAccountExist"), Getvalue.get("BeneficiaryAccountAcceptPayment"), Getvalue.get("IsBeneficiaryAccountHolder"), Getvalue.get("IsBenficiaryAccountTypeValid"), Getvalue.get("IsBeneficiaryAccountOpen"), Getvalue.get("IsBenficiaryAccOpen3Months"), Getvalue.get("HyphenFinalOutcome"), Getvalue.get("HyphenAdditionalComments"));


            //--------------------------Real Time screening------------------------------------------
            home.FindPolicyUnderTask(Getvalue.get("OperationDesk"),HomePage.TeamTask, Getvalue.get("Policy Number"), 200);
            home.FindPolicyUnderTask(Getvalue.get("OperationDesk"),HomePage.MyTask, Getvalue.get("Policy Number"), 200);

            String RTS_Result = home.getTextUsingJs(RealTimeScreening.RTS_Result);
            System.out.println("Unable to Screen Beneficiary via CIS/Hogan – Please Screen Beneficiary via RTS Tool");
            if (RTS_Result.equals("Unable to Screen Beneficiary via CIS/Hogan – Please Screen Beneficiary via RTS Tool"))
                gm.LogPass("RTS Validations", "RTS Validations Form Submitted Successfully");
            else
                gm.LogFail("RTS Validations", "RTS Validations Form Submitted Failed");

            RTS.SubmitRTSForm(Getvalue.get("RTSReferenceNumber"), Getvalue.get("RTSResult"));
            //-------------------------ComplexClaimAssessment(MS-5)-----------------------------------

            home.FindPolicyUnderTask(Getvalue.get("OperationDesk"),HomePage.TeamTask, Getvalue.get("Policy Number"), 300);
            home.FindPolicyUnderTask(Getvalue.get("OperationDesk"),HomePage.MyTask, Getvalue.get("Policy Number"), 300);
            String ComplexAssessment = "Business Filter\n" + "Manual Assessment - Business Rules Not Passed - Review System Assessment Results and complete Manual Claims Assessment\n" + "\n" + "Risk Filter\n" + "Fraud Assessment - Fraud Assessment Required - Risk Score >= 150 - Review Risk Model Outcomes and Refer the Claim to Forensics";
            //MS5.SubmitMS5MandatoryValidationsForm(MS5DocumentStatus, MS5DocumentReference, MS5PractitionerType, MS5HPCSANumber, MS5PractitionerName, MS5PractitionerSurname, MS5FacilityName, MS5PractitionerNumber, MS5PracticeContact, MS5BusinessAddress, MS5PracticeCity, MS5PracticePostalcode, MS5PracticeProvice, MS5PractitionerValidationOutcome, MS5PractitionerValidationSource, MS5PractionerReferenceNo, MS5PractitionerComments, MS5FuneralParlourName, MS5FuneralDHANo, MS5FuneralCompanyRegistrationNo, MS5FuneralSARSNo, MS5FuneralValidationOutcome, MS5FuneralValidationSource, MS5FuneralReferenceNo, MS5FuneralComments, MS5InformantID, MS5InformantDOB, MS5InformantName, MS5InformantSurname, MS5InformantAddress, MS5InformantCity, MS5InformantPostalCode, MS5InformantProvince, MS5InformantRelationToDeceased, MS5InformantContact, MS5InformantValidationOutcome, MS5InformantValidationSource, MS5InformantReferenceNo, MS5InformantComments, MS5IndunaCauseofDeath, MS5IndunaPlaceOfDeath, MS5IndunaDeceaseSource, MS5IndunaName, MS5IndunaSurname, MS5IndunaID, MS5IndunaContactNo, MS5IndunaAddress, MS5IndunaCity, MS5IndunaPostalCode, MS5IndunaProvince, MS5IndunaValidationOutcome, MS5IndunaValidationSource, MS5IndunaReferenceNo, MS5IndunaComments);
            MS5.SubmitMS5MandatoryValidationsForm(Getvalue.get("MS5DocumentStatus"),Getvalue.get("MS5DocumentReference"),Getvalue.get("MS5PractitionerType"),Getvalue.get("MS5HPCSANumber"),Getvalue.get("MS5PractitionerName"),Getvalue.get("MS5PractitionerSurname"),Getvalue.get("MS5FacilityName"),Getvalue.get("MS5PractitionerNumber"),Getvalue.get("MS5PracticeContact"),Getvalue.get("MS5BusinessAddress"),Getvalue.get("MS5PracticeCity"),Getvalue.get("MS5PracticePostalcode"),Getvalue.get("MS5PracticeProvice"),Getvalue.get("MS5PractitionerValidationOutcome"),Getvalue.get("MS5PractitionerValidationSource"),Getvalue.get("MS5PractionerReferenceNo"),Getvalue.get("MS5PractitionerComments"),Getvalue.get("MS5FuneralParlourName"),Getvalue.get("MS5FuneralDHANo"),Getvalue.get("MS5FuneralCompanyRegistrationNo"),Getvalue.get("MS5FuneralSARSNo"),Getvalue.get("MS5FuneralValidationOutcome"),Getvalue.get("MS5FuneralValidationSource"),Getvalue.get("MS5FuneralReferenceNo"),Getvalue.get("MS5FuneralComments"),Getvalue.get("MS5InformantID"),Getvalue.get("MS5InformantDOB"),Getvalue.get("MS5InformantName"),Getvalue.get("MS5InformantSurname"),Getvalue.get("MS5InformantAddress"),Getvalue.get("MS5InformantCity"),Getvalue.get("MS5InformantPostalCode"),Getvalue.get("MS5InformantProvince"),Getvalue.get("MS5InformantRelationToDeceased"),Getvalue.get("MS5InformantContact"),Getvalue.get("MS5InformantValidationOutcome"),Getvalue.get("MS5InformantValidationSource"),Getvalue.get("MS5InformantReferenceNo"),Getvalue.get("MS5InformantComments"),Getvalue.get("MS5IndunaCauseofDeath"),Getvalue.get("MS5IndunaPlaceOfDeath"),Getvalue.get("MS5IndunaDeceaseSource"),Getvalue.get("MS5IndunaName"),Getvalue.get("MS5IndunaSurname"),Getvalue.get("MS5IndunaID"),Getvalue.get("MS5IndunaContactNo"),Getvalue.get("MS5IndunaAddress"),Getvalue.get("MS5IndunaCity"),Getvalue.get("MS5IndunaPostalCode"),Getvalue.get("MS5IndunaProvince"),Getvalue.get("MS5IndunaValidationOutcome"),Getvalue.get("MS5IndunaValidationSource"),Getvalue.get("MS5IndunaReferenceNo"),Getvalue.get("MS5IndunaComments"));
            String MS5AdditionalComments = "Submitted to Recalculate Risk Score";
            SendText(HomePage.AdditionalComments, MS5AdditionalComments);
            String MS5_Results = home.getTextUsingJs(ComplexClaimAssessment.ComplexAssessmentResults);
            if (MS5_Results.equals(ComplexAssessment))
                gm.LogPass("MS-5 Recalculation", "MS-5 Submitted for Recalculation Successfully");
            else
                gm.LogFail("MS-5 Recalculation", "MS-5 Forms Submitted for Recalculation Failed");
            MS5.RecalculateRisk(HomePage.SubmitFormNotification);

            //--------------------------------------Back to MS-5--------------------------
            home.FindPolicyUnderTask(Getvalue.get("OperationDesk"),HomePage.MyTask, Getvalue.get("Policy Number"), 300);

            String[] MedicalPractitionerFields = new String[]{Getvalue.get("MS5PractitionerType"), Getvalue.get("MS5HPCSANumber"), Getvalue.get("MS5PractitionerName"), Getvalue.get("MS5PractitionerSurname"), Getvalue.get("MS5FacilityName"), Getvalue.get("MS5PractitionerNumber"), Getvalue.get("MS5PracticeContact"), Getvalue.get("MS5BusinessAddress"),Getvalue.get("MS5PracticeCity"), Getvalue.get("MS5PracticePostalcode.toString()"), Getvalue.get("MS5PracticeProvice"), Getvalue.get("MS5PractitionerValidationOutcome"), Getvalue.get("MS5PractitionerValidationSource"), Getvalue.get("MS5PractionerReferenceNo"), Getvalue.get("MS5PractitionerComments")};
            String[] FuneralFields = new String[]{Getvalue.get("MS5FuneralValidationOutcome"), Getvalue.get("MS5FuneralValidationSource"), Getvalue.get("MS5FuneralReferenceNo"), Getvalue.get("MS5FuneralComments"), Getvalue.get("MS5FuneralParlourName"), Getvalue.get("MS5FuneralDHANo"), Getvalue.get("MS5FuneralCompanyRegistrationNo"), Getvalue.get("MS5FuneralSARSNo")};
            String[] Informant = new String[]{Getvalue.get("MS5InformantValidationOutcome"), Getvalue.get("MS5InformantValidationSource"), Getvalue.get("MS5InformantReferenceNo"), Getvalue.get("MS5InformantComments"), Getvalue.get("MS5InformantID"), Getvalue.get("MS5InformantDOB"), Getvalue.get("MS5InformantName"), Getvalue.get("MS5InformantSurname"), Getvalue.get("MS5InformantAddress"), Getvalue.get("MS5InformantCity"), Getvalue.get("MS5InformantPostalCode"), Getvalue.get("MS5InformantProvince"),Getvalue.get("MS5InformantRelationToDeceased"), Getvalue.get("MS5InformantContact")};
            String[] Induna = new String[]{Getvalue.get("MS5IndunaValidationOutcome"), Getvalue.get("MS5IndunaValidationSource"), Getvalue.get("MS5IndunaReferenceNo"), Getvalue.get("MS5IndunaComments"), Getvalue.get("MS5IndunaCauseofDeath"), Getvalue.get("MS5IndunaPlaceOfDeath"),Getvalue.get("MS5IndunaDeceaseSource"), Getvalue.get("MS5IndunaName"), Getvalue.get("MS5IndunaSurname"), Getvalue.get("MS5IndunaID"), Getvalue.get("MS5IndunaContactNo"), Getvalue.get("MS5IndunaAddress"), Getvalue.get("MS5IndunaCity"), Getvalue.get("MS5IndunaPostalCode"), Getvalue.get("MS5IndunaProvince")};

            boolean MedicalPracOverviewData = home.CompareValidationsForms("Medical Practitioner Validation", MedicalPractitionerFields, HomePage.OverviewTabName.Manual);
            boolean FuneralOverviewData = home.CompareValidationsForms("Funeral Undertaker Validation", FuneralFields, HomePage.OverviewTabName.Manual);
            boolean InformantPracOverviewData = home.CompareValidationsForms("Informant Validation", Informant, HomePage.OverviewTabName.Manual);
            boolean IndunaOverviewData = home.CompareValidationsForms("Induna/Traditional Leader Validation", Induna, HomePage.OverviewTabName.Manual);

            String finalOutcome = home.GetOverviewData(HomePage.OverviewTabName.Manual, HomePage.ManualAssessmentOverview.finalOutcome);
            String DecisionReason = home.GetOverviewData(HomePage.OverviewTabName.Manual, HomePage.ManualAssessmentOverview.finalOutcomeReason);
            String additionalComments = home.GetOverviewData(HomePage.OverviewTabName.Manual, HomePage.ManualAssessmentOverview.additionalComments);

            if (!MedicalPracOverviewData && !FuneralOverviewData && !InformantPracOverviewData && !IndunaOverviewData && additionalComments.equals(MS5AdditionalComments) && finalOutcome.equals("") && DecisionReason.equals(""))
                gm.LogPass("MS-5 after Recalculation", "Back to MS-5 after recalculation Successfully");
            else
                gm.LogFail("MS-5 after Recalculation", "Back to MS-5 after recalculation Failed");
            /*
            String medicalPractitionerType =home.GetOverviewData(HomePage.OverviewTabName.Manual,"Medical Practitioner Validation", HomePage.MedicalPractitionerFormFields.medicalPractitionerType);
            String registrationNumber=home.GetOverviewData(HomePage.OverviewTabName.Manual,"Medical Practitioner Validation", HomePage.MedicalPractitionerFormFields.registrationNumber);
            String practiceName=home.GetOverviewData(HomePage.OverviewTabName.Manual,"Medical Practitioner Validation", HomePage.MedicalPractitionerFormFields.practiceName);
            String practiceNumber=home.GetOverviewData(HomePage.OverviewTabName.Manual,"Medical Practitioner Validation", HomePage.MedicalPractitionerFormFields.practiceNumber);
            String contactNumber=home.GetOverviewData(HomePage.OverviewTabName.Manual,"Medical Practitioner Validation", HomePage.MedicalPractitionerFormFields.contactNumber);
            String name=home.GetOverviewData(HomePage.OverviewTabName.Manual,"Medical Practitioner Validation", HomePage.MedicalPractitionerFormFields.name);
            String surname=home.GetOverviewData(HomePage.OverviewTabName.Manual,"Medical Practitioner Validation", HomePage.MedicalPractitionerFormFields.surname);
            String address=home.GetOverviewData(HomePage.OverviewTabName.Manual,"Medical Practitioner Validation", HomePage.MedicalPractitionerFormFields.address);
            String city=home.GetOverviewData(HomePage.OverviewTabName.Manual,"Medical Practitioner Validation", HomePage.MedicalPractitionerFormFields.city);
            String postalCode=home.GetOverviewData(HomePage.OverviewTabName.Manual,"Medical Practitioner Validation", HomePage.MedicalPractitionerFormFields.postalCode);
            String province=home.GetOverviewData(HomePage.OverviewTabName.Manual,"Medical Practitioner Validation", HomePage.MedicalPractitionerFormFields.province);
            String postalcode=home.GetOverviewData(HomePage.OverviewTabName.Manual,"Medical Practitioner Validation", HomePage.MedicalPractitionerFormFields.postalCode);

            String validationOutcome=home.GetOverviewData(HomePage.OverviewTabName.Manual,"Medical Practitioner Validation", HomePage.MedicalPractitionerFormFields.validationOutcome);
            String validationSource=home.GetOverviewData(HomePage.OverviewTabName.Manual,"Medical Practitioner Validation", HomePage.MedicalPractitionerFormFields.validationSource);
            String sourceReferenceNumber=home.GetOverviewData(HomePage.OverviewTabName.Manual,"Medical Practitioner Validation", HomePage.MedicalPractitionerFormFields.sourceReferenceNumber);
            String comments=home.GetOverviewData(HomePage.OverviewTabName.Manual,"Medical Practitioner Validation", HomePage.MedicalPractitionerFormFields.comments);
            System.out.println(registrationNumber+"\t"+practiceName+"\t"+practiceNumber+"\t"+contactNumber+"\t"+name+"\t"+surname+"\t"+address+"\t"+city+"\t"+postalCode+"\t"+province+"\t"+postalCode+"\t"+validationOutcome+"\t"+validationSource+"\t"+sourceReferenceNumber+"\t"+comments);
            //String [] str=home.GetOverviewDataList(HomePage.OverviewTabName.Manual);
            String [] str=home.GetOverviewDataList(HomePage.OverviewTabName.Manual);
            */

            MS5.SelectFinalDecisionAndReason(ComplexClaimAssessment.ReferToFraud, ComplexClaimAssessment.Policy_Abuse);
            String MS5_AdditionalComment = "Submitted to Fraud Investigator";
            gm.SendText(HomePage.AdditionalComments, MS5_AdditionalComment);
            ClickElement(ComplexClaimAssessment.ConfirmationCheckbox);
            home.SubmitClaim();

            //---------------------------------MS-6------------------------------------
            waitforPageload();
            home.FindPolicyUnderTask("Fraud",HomePage.TeamTask, Getvalue.get("Policy Number"), 300);
            home.FindPolicyUnderTask("Fraud",HomePage.MyTask, Getvalue.get("Policy Number"), 300);
            waitforPageload();
            ClickElement(MS5.ExpandForm(HomePage.MedicalPractitionerForm));
            MS5.SubmitValidationsResult(ComplexClaimAssessment.ValidationForm.medical, Getvalue.get("MS6APractitionerValidationOutcome"), Getvalue.get("MS6APractitionerValidationSource"), Getvalue.get("MS6APractionerReferenceNo"), Getvalue.get("MS6APractitionerComments"));

            ClickElement(MS5.ExpandForm(HomePage.FuneralUndertakerFrom));
            MS5.SubmitValidationsResult(ComplexClaimAssessment.ValidationForm.undertaker, Getvalue.get("MS6AFuneralValidationOutcome"), Getvalue.get("MS6AFuneralValidationSource"), Getvalue.get("MS6AFuneralReferenceNo"), Getvalue.get("MS6AFuneralComments"));

            ClickElement(MS5.ExpandForm(HomePage.InformantFrom));
            MS5.SubmitValidationsResult(ComplexClaimAssessment.ValidationForm.informant, Getvalue.get("MS6AInformantValidationOutcome"), Getvalue.get("MS6AInformantValidationSource"), Getvalue.get("MS6AInformantReferenceNo"), Getvalue.get("MS6AInformantComments"));

            ClickElement(MS5.ExpandForm(HomePage.InndunaFrom));
            MS5.SubmitValidationsResult(ComplexClaimAssessment.ValidationForm.induna, Getvalue.get("MS6AIndunaValidationOutcome"), Getvalue.get("MS6AIndunaValidationSource"), Getvalue.get("MS6AIndunaReferenceNo"), Getvalue.get("MS6AIndunaComments"));

            MS6.SelectFinalDecisionOutcome(FraudDesktopAssessment.Invalid_Referral, FraudDesktopAssessment.Simple);
            //MS6.SelectFinalDecisionOutcome("Refer to Field Investigator","F5401542","Simple");
            //MS6.SelectFinalDecisionOutcome("Fraud Confirmed","Fraudulent Documentation","Decline Recommended","Simple");
            String MS6A_AdditionalComments = "Submitted back to Claim Assessor";
            gm.SendText(HomePage.AdditionalComments, MS6A_AdditionalComments);
            String MS6AResult = home.getTextUsingJs(FraudDesktopAssessment.MS6AManualAssessmentResult);
            System.out.println(MS6AResult);
            System.out.println("Potential Fraud Suspected by Assessor - Claim Referred to Fraud Desktop Assessment\n" +
                    "\n" +
                    "Fraud Referral Reason: Fraud Input Required\n" +
                    "\n" +
                    "Additional Comments from Manual Assessor: Sending to MS-6A");

            finalOutcome = home.GetOverviewData(HomePage.OverviewTabName.Manual, HomePage.ManualAssessmentOverview.finalOutcome);
            DecisionReason = home.GetOverviewData(HomePage.OverviewTabName.Manual, HomePage.ManualAssessmentOverview.finalOutcomeReason);
            additionalComments = home.GetOverviewData(HomePage.OverviewTabName.Manual, HomePage.ManualAssessmentOverview.additionalComments);

            if (finalOutcome.equals(ComplexClaimAssessment.ReferToFraud) && DecisionReason.equals(ComplexClaimAssessment.Policy_Abuse) && additionalComments.equals(MS5_AdditionalComment))
                gm.LogPass("Final Outcome for Manual Assessment", "Final Outcome for Manual Assessment validated Successfully on MS-6A");
            else
                gm.LogFail("Final Outcome for Manual Assessment", "Final Outcome for Manual Assessment on MS-6A failed");

            home.SubmitClaim();
            //-----------------------------Back to MS-5--------------------------------------
            home.FindPolicyUnderTask(Getvalue.get("OperationDesk"),HomePage.MyTask, Getvalue.get("Policy Number"), 300);
            //ClickElement(MS5.ExpandForm(HomePage.MedicalPractitionerForm));
            String[] MS6A_MedicalPractitionerFields = new String[]{Getvalue.get("MS5PractitionerType"), Getvalue.get("MS5HPCSANumber"), Getvalue.get("MS5PractitionerName"), Getvalue.get("MS5PractitionerSurname"), Getvalue.get("MS5FacilityName"), Getvalue.get("MS5PractitionerNumber"), Getvalue.get("MS5PracticeContact"), Getvalue.get("MS5BusinessAddress"), Getvalue.get("MS5PracticeCity"), Getvalue.get("MS5PracticePostalcode.toString()"), Getvalue.get("MS5PracticeProvice"),Getvalue.get("MS6APractitionerValidationOutcome"), Getvalue.get("MS6APractitionerValidationSource"), Getvalue.get("MS6APractionerReferenceNo"), Getvalue.get("MS6APractitionerComments")};
            String[] MS6A_FuneralFields = new String[]{Getvalue.get("MS5FuneralParlourName"), Getvalue.get("MS5FuneralDHANo"), Getvalue.get("MS5FuneralCompanyRegistrationNo"), Getvalue.get("MS5FuneralSARSNo"), Getvalue.get("MS6AFuneralValidationOutcome"), Getvalue.get("MS6AFuneralValidationSource"), Getvalue.get("MS6AFuneralReferenceNo"), Getvalue.get("MS6AFuneralComments")};
            String[] MS6A_Informant = new String[]{Getvalue.get("MS5InformantID"), Getvalue.get("MS5InformantDOB"), Getvalue.get("MS5InformantName"), Getvalue.get("MS5InformantSurname"), Getvalue.get("MS5InformantAddress"), Getvalue.get("MS5InformantCity"), Getvalue.get("MS5InformantPostalCode"), Getvalue.get("MS5InformantProvince"), Getvalue.get("MS5InformantRelationToDeceased"), Getvalue.get("MS5InformantContact"), Getvalue.get("MS6AInformantValidationOutcome"), Getvalue.get("MS6AInformantValidationSource"), Getvalue.get("MS6AInformantReferenceNo"), Getvalue.get("MS6AInformantComments")};
            String[] MS6A_Induna = new String[]{Getvalue.get("MS5IndunaCauseofDeath"), Getvalue.get("MS5IndunaPlaceOfDeath"), Getvalue.get("MS5IndunaDeceaseSource"), Getvalue.get("MS5IndunaName"), Getvalue.get("MS5IndunaSurname"), Getvalue.get("MS5IndunaID"), Getvalue.get("MS5IndunaContactNo"), Getvalue.get("MS5IndunaAddress"), Getvalue.get("MS5IndunaCity"), Getvalue.get("MS5IndunaPostalCode"), Getvalue.get("MS5IndunaProvince"), Getvalue.get("MS6AIndunaValidationOutcome"), Getvalue.get("MS6AIndunaValidationSource"), Getvalue.get("MS6AIndunaReferenceNo"), Getvalue.get("MS6AIndunaComments")};

            boolean MS6A_MedicalOverviewData = home.CompareValidationsForms("Medical Practitioner Validation", MS6A_MedicalPractitionerFields, HomePage.OverviewTabName.Fraud);
            boolean MS6A_FuneralOverviewData = home.CompareValidationsForms("Funeral Undertaker Validation", MS6A_FuneralFields, HomePage.OverviewTabName.Fraud);
            boolean MS6A_InformantOverviewData = home.CompareValidationsForms("Informant Validation", MS6A_Informant, HomePage.OverviewTabName.Fraud);
            boolean MS6A_IndunaOverviewData = home.CompareValidationsForms("Induna/Traditional Leader Validation", MS6A_Induna, HomePage.OverviewTabName.Fraud);

            boolean FraudOverviewData = false;
            boolean FraudOutcomeData = false;
            if (!MS6A_MedicalOverviewData && !MS6A_FuneralOverviewData && !MS6A_InformantOverviewData && !MS6A_IndunaOverviewData)
                FraudOverviewData = true;
            else
                FraudOverviewData = false;

            finalOutcome = home.GetOverviewData(HomePage.OverviewTabName.Fraud, HomePage.FraudAssessmentOverview.fraudOutcome);
            String Complexity = home.GetOverviewData(HomePage.OverviewTabName.Fraud, HomePage.FraudAssessmentOverview.complexityType);
            additionalComments = home.GetOverviewData(HomePage.OverviewTabName.Fraud, HomePage.FraudAssessmentOverview.fraudAssessmentComments);

            String MS6A_FraudAdditionalComments = home.getTextUsingJs(ComplexClaimAssessment.FraudAssessmentResults);

            if (finalOutcome.equals(FraudDesktopAssessment.Invalid_Referral) && Complexity.equals(FraudDesktopAssessment.Simple) && additionalComments.equals(MS6A_AdditionalComments))
                FraudOutcomeData = true;
            else
                FraudOutcomeData = false;

            if (FraudOverviewData && FraudOutcomeData && MS6A_FraudAdditionalComments.equals(MS6A_AdditionalComments))
                gm.LogPass("Final Outcome for Manual Assessment", "Final Outcome for Fraud Assessment validated Successfully on MS-5");
            else
                gm.LogFail("Final Outcome for Manual Assessment", "Final Outcome for Fraud Assessment Not validated on MS-5");

            MS5.SelectFinalDecisionAndReason(ComplexClaimAssessment.Approve_Claim);
            String ApproveComment = "Submitted as approved from MS-5";
            gm.SendText(HomePage.AdditionalComments, ApproveComment);
            ClickElement(ComplexClaimAssessment.ConfirmationCheckbox);
            home.SubmitClaim();


        /* String str=gm.GetText(ClaimDeclinePage.SystemRecommendedAction);
        System.out.println("----------------"+str+"----------------");
        gm.SelectDeclineDecision("Decision","Valid Decline");
        gm.SelectDeclineDecision("Reason","The terms and conditions of this Plan are not met");
        gm.SendText(ClaimDeclinePage.AdditionalComments, "adsvh nds h vcdfh vndf ");*/

        /*String v1=home.GetOverviewDataList(HomePage.OverviewTabName.Claim, "Claim Channel");
        String v2=home.GetOverviewDataList(HomePage.OverviewTabName.Policy, "Policy Number");
        String v3=home.GetOverviewDataList(HomePage.OverviewTabName.Deceased, "Deceased ID");
        */

            System.out.println("=====================Flow Search===================");
            gm.GoToURL(EnvironmentVariables.FlowURLForPre);
            flow.LoginToFlow("F7890124", "password");
            flow.SearchPolicyNumber(Getvalue.get("Policy Number"));
            flow.OpenFlowComments("APPROVED");
            flow.GetCommentDescriptions("New Documents Appended - Greenline Claim in Progress on InsureWorX");
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }

    }

    @Test(dataProvider = "ClaimSubmission")
    public void TC_2(Map<String,String> Getvalue, Method method)  {
        try {


            log.info("================================"+method.getName()+"==================================");
            test = report.createTest(method.getName());
            //email.SendEmail();

            gm.UpdatePolicyStatus("com.ibm.as400.access.AS400JDBCDriver", "jdbc:as400:fnbpre.fnb.co.za", Getvalue.get("Policy Number"), "FNBTTEST5", "FNBTTEST5", 6);
            gm.ClearWorkItemFromDB("com.ibm.as400.access.AS400JDBCDriver", "jdbc:as400://fnbpre.fnb.co.za;libraries=WORKFLOW;", Getvalue.get("Policy Number"), "FNBFLOW", "FNBFLOW");
            gm.DeleteClaimsFromDB("com.ibm.as400.access.AS400JDBCDriver", "jdbc:as400:fnbpre.fnb.co.za", Getvalue.get("Policy Number"), "FNBTTEST5", "FNBTTEST5");

            //gm.launchBrowser("chrome");
            //gm.GoToURL(EnvironmentVariables.InsureworxPre);
            Boolean LoginStatus = login.LoginToInsureworx(Getvalue.get("Username"), Getvalue.get("Password"));
            if (LoginStatus)
                gm.LogPass("Insureworx Login Page", "Insureworx Login successful");
                 else
                   gm.LogFail("Insureworx Login Page", "Insureworx Login Unsuccessful");

            home.FindPolicyUnderTask(Getvalue.get("OperationDesk"),HomePage.MyTask, Getvalue.get("Policy Number"), 800);
            Diarise.Diarization("Documents Required"," Beneficiary form and related IDs ");


            Boolean IsPolicySubmitted = home.SubmitClaimForm(Getvalue.get("OperationDesk"), Getvalue.get("RequestChannel"), Getvalue.get("ProcessCategory"), Getvalue.get("SubProcessCategory"), Getvalue.get("Policy Number"), Getvalue.get("DeceasedID"), Getvalue.get("PolicyHolderID"), Getvalue.get("BeneficiaryID"), Getvalue.get("FirstName"), Getvalue.get("LastName"), Getvalue.get("7124634535"), Getvalue.get("EmailID"), Getvalue.get("AccountNumber"), Getvalue.get("BranchCode"));
            if (IsPolicySubmitted)
                gm.LogPass("Policy submitted successfully", "Policy submitted successfully");
                  else
                   gm.LogFail("Policy Not submitted successfully", "Policy Not submitted successfully");


            //home.FindPolicyUnderTask(Getvalue.get("OperationDesk"),HomePage.TeamTask, Getvalue.get("Policy Number"), 800);
            home.FindPolicyUnderTask(Getvalue.get("OperationDesk"),HomePage.MyTask, Getvalue.get("Policy Number"), 800);
            Diarise.Diarization("Documents Required"," Beneficiary form and related IDs ");
            String DeathValidationResult = getTextUsingJs(VOPDDeathValidations.DeathValidationResults);
            if (DeathValidationResult.equals("Auto Death Validation failed and waiver rules have NOT been applied - Please complete full Manual Validation for Deceased"))
                gm.LogPass("VOPD Outcome Validated", "VOPD Outcome Validated Successfully");
            else
                gm.LogFail("VOPD Outcome not Validation failed", "VOPD Outcome Validation Failed");
            VOPD.SubmitVOPDValidationForm(Getvalue.get("VOPD_Status"), Getvalue.get("VOPD_DOD"), Getvalue.get("CauseOfDeath"),Getvalue.get("PlaceOfDeath"), Getvalue.get("DeathDescription"), Getvalue.get("ValidationSource"), Getvalue.get("Status"), Getvalue.get("ValidationOutcome"), Getvalue.get("DeathValidationComments"), Getvalue.get("VOPD_FinalOutcome"), Getvalue.get("VOPDAdditionalComments"));


            log.info("=====Flow Search=====");
            home.SubmitClaimForm(Getvalue.get("OperationDesk"), Getvalue.get("RequestChannel"), Getvalue.get("ProcessCategory"), Getvalue.get("SubProcessCategory"), Getvalue.get("Policy Number"), Getvalue.get("DeceasedID"), Getvalue.get("PolicyHolderID"), Getvalue.get("BeneficiaryID"), Getvalue.get("FirstName"), Getvalue.get("LastName"), Getvalue.get("7124634535"), Getvalue.get("EmailID"), Getvalue.get("AccountNumber"), Getvalue.get("BranchCode"));
            gm.GoToURL(EnvironmentVariables.FlowURLForPre);
            flow.LoginToFlow("F7890124", "password");
            flow.SearchPolicyNumber(Getvalue.get("Policy Number"));
            flow.OpenFlowComments("DUPLICATE");
            boolean VerifyDuplicateComments = flow.GetCommentDescriptions("New Documents Appended - Greenline Claim in Progress on InsureWorX");
            if(VerifyDuplicateComments)
                    gm.LogPass("Duplicate Claim-appended to insureworx", "Duplicate Claim-appended to insureworx successfully");
                else
                    gm.LogFail("Duplicate Claim NOT appended to insureworx", "Duplicate Claim NOT appended to insureworx");

        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

    @Test(dataProvider = "ClaimSubmission")
    public void TC_3(Map<String,String> Getvalue, Method method)  {
        try {
            log.info("================================"+method.getName()+"==================================");
            test = report.createTest(method.getName());
            //email.SendEmail();

            gm.UpdatePolicyStatus("com.ibm.as400.access.AS400JDBCDriver", "jdbc:as400:fnbpre.fnb.co.za", Getvalue.get("Policy Number"), "FNBTTEST5", "FNBTTEST5", 6);
            gm.ClearWorkItemFromDB("com.ibm.as400.access.AS400JDBCDriver", "jdbc:as400://fnbpre.fnb.co.za;libraries=WORKFLOW;", Getvalue.get("Policy Number"), "FNBFLOW", "FNBFLOW");
            gm.DeleteClaimsFromDB("com.ibm.as400.access.AS400JDBCDriver", "jdbc:as400:fnbpre.fnb.co.za", Getvalue.get("Policy Number"), "FNBTTEST5", "FNBTTEST5");

            //gm.launchBrowser("chrome");
            //gm.GoToURL(EnvironmentVariables.InsureworxPre);
            Boolean LoginStatus = login.LoginToInsureworx(Getvalue.get("Username"), Getvalue.get("Password"));
            if (LoginStatus)
                gm.LogPass("Insureworx Login Page", "Insureworx Login successful");
                 else
                   gm.LogFail("Insureworx Login Page", "Insureworx Login Unsuccessful");


            Boolean IsPolicySubmitted = home.SubmitClaimForm(Getvalue.get("OperationDesk"), Getvalue.get("RequestChannel"), Getvalue.get("ProcessCategory"), Getvalue.get("SubProcessCategory"), Getvalue.get("Policy Number"), Getvalue.get("DeceasedID"), Getvalue.get("PolicyHolderID"), Getvalue.get("BeneficiaryID"), Getvalue.get("FirstName"), Getvalue.get("LastName"), Getvalue.get("7124634535"), Getvalue.get("EmailID"), Getvalue.get("AccountNumber"), Getvalue.get("BranchCode"));
            if (IsPolicySubmitted)
                gm.LogPass("Policy submitted successfully", "Policy submitted successfully");
                 else
                   gm.LogFail("Policy Not submitted successfully", "Policy Not submitted successfully");


            home.FindPolicyUnderTask(Getvalue.get("OperationDesk"),HomePage.TeamTask, Getvalue.get("Policy Number"), 800);
            home.FindPolicyUnderTask(Getvalue.get("OperationDesk"),HomePage.MyTask, Getvalue.get("Policy Number"), 800);
            String ReviewPASValidationResult = getTextUsingJs(ReviewClaimInfo.ReviewValidationResults);
            System.out.println(ReviewPASValidationResult);
            if (ReviewPASValidationResult.equals("No members found for Deceased ID on the policy - Complete Deceased section below"))
                gm.LogPass("MS2B Outcome Validated", "MS-2B Validated Successfully");
                 else
                  gm.LogFail("MS2B Outcome not Validation failed", "MS-2B Outcome Validation Failed");
            //String Member=Getvalue.get("FirstName").concat(" ").concat(Getvalue.get("LastName"));
            String AdditionalComments="Non funeral request identified";
            MS2B.SubmitPASReviewFormAsInvalid(ReviewClaimInfo.InvalidClaimRequest,ReviewClaimInfo.UnableToConfirmCover,AdditionalComments);

            home.FindPolicyUnderTask(Getvalue.get("OperationDesk"),HomePage.TeamTask, Getvalue.get("Policy Number"), 800);
            home.FindPolicyUnderTask(Getvalue.get("OperationDesk"),HomePage.MyTask, Getvalue.get("Policy Number"), 800);
            String DeclinedValidationResult = getTextUsingJs(ClaimDeclinePage.SystemRecommendedAction);
            if (DeclinedValidationResult.equals("Claim Decline Suggested by Demo Demo - Unable to Confirm Deceased Cover - Review details and determine if suggested decline outcome is valid"))
                gm.LogPass("MS4 Outcome Validated", "Claim Declined Successfully on MS-4");
                  else
                   gm.LogFail("MS4 Outcome not Validation failed", "Failed to declined Successfully on MS-4");

            Decline.SubmitPASReviewValidationForm("Valid Decline","Death occurred due to the participation in a criminal act","Declined claim on MS-4");

            gm.GoToURL(EnvironmentVariables.FlowURLForPre);
            flow.LoginToFlow("F7890124", "password");
            flow.SearchPolicyNumber(Getvalue.get("Policy Number"));
            flow.OpenFlowComments("DECLINED");
            boolean VerifyDuplicateComments = flow.GetCommentDescriptions("Unable to Initiate Claim - Death occurred due to the participation in a criminal act");
            if(VerifyDuplicateComments)
                gm.LogPass("Insureworx Declined", "Claim Declined on Flow Successfully");
                 else
                  gm.LogFail("Insureworx not Declined", "Failed to Declined claim on Flow");
        } catch (Exception e) {
            log.error(e.getMessage());
        }
    }

    @Test(dataProvider = "ClaimSubmission")
    public void TC_4(Map<String,String> Getvalue, Method method)  {
        try {

            test = report.createTest(method.getName());
            gm.UpdatePolicyStatus("com.ibm.as400.access.AS400JDBCDriver", "jdbc:as400:fnbpre.fnb.co.za", Getvalue.get("Policy Number"), "FNBTTEST5", "FNBTTEST5", 6);
            gm.ClearWorkItemFromDB("com.ibm.as400.access.AS400JDBCDriver", "jdbc:as400://fnbpre.fnb.co.za;libraries=WORKFLOW;", Getvalue.get("Policy Number"), "FNBFLOW", "FNBFLOW");
            gm.DeleteClaimsFromDB("com.ibm.as400.access.AS400JDBCDriver", "jdbc:as400:fnbpre.fnb.co.za", Getvalue.get("Policy Number"), "FNBTTEST5", "FNBTTEST5");

            //gm.launchBrowser("chrome");
            //gm.GoToURL(EnvironmentVariables.InsureworxPre);
            Boolean LoginStatus = login.LoginToInsureworx(Getvalue.get("Username"), Getvalue.get("Password"));
            if (LoginStatus)
                gm.LogPass("Insureworx Login Page", "Insureworx Login successful");
            else
                gm.LogFail("Insureworx Login Page", "Insureworx Login Unsuccessful");

            Boolean IsPolicySubmitted = home.SubmitClaimForm(Getvalue.get("OperationDesk"), Getvalue.get("RequestChannel"), Getvalue.get("ProcessCategory"), Getvalue.get("SubProcessCategory"), Getvalue.get("Policy Number"), Getvalue.get("DeceasedID"), Getvalue.get("PolicyHolderID"), Getvalue.get("BeneficiaryID"), Getvalue.get("FirstName"), Getvalue.get("LastName"), Getvalue.get("7124634535"), Getvalue.get("EmailID"), Getvalue.get("AccountNumber"), Getvalue.get("BranchCode"));
            if (IsPolicySubmitted)
                gm.LogPass("Policy submitted successfully", "Policy submitted successfully");
            else
                gm.LogFail("Policy Not submitted successfully", "Policy Not submitted successfully");

            home.FindPolicyUnderTask(Getvalue.get("OperationDesk"),HomePage.TeamTask, Getvalue.get("Policy Number"), 800);
            home.FindPolicyUnderTask(Getvalue.get("OperationDesk"),HomePage.MyTask, Getvalue.get("Policy Number"), 800);
            String ReviewPASValidationResult = getTextUsingJs(ReviewClaimInfo.ReviewValidationResults);
            if (ReviewPASValidationResult.equals("No members found for Deceased ID on the policy - Complete Deceased section below"))
                gm.LogPass("MS2B Outcome Validated", "MS-2B Validated Successfully");
            else
                gm.LogFail("MS2B Outcome not Validation failed", "MS-2B Outcome Validation Failed");

            String AdditionalComments="Invalid claim identified";
            MS2B.SubmitPASReviewFormAsInvalid(ReviewClaimInfo.CloseClaim,ReviewClaimInfo.InsufficientClaimDetails,AdditionalComments);

            gm.GoToURL(EnvironmentVariables.FlowURLForPre);
            flow.LoginToFlow("F7890124", "password");
            flow.SearchPolicyNumber(Getvalue.get("Policy Number"));
            boolean VerifyWorkItem=flow.VerifyFlowWorkItem("FUNERLCLM","NTU","FNRQLTY");
            flow.OpenFlowComments("NTU");
            boolean VerifyDuplicateComments = flow.GetCommentDescriptions("Unable to Initiate Claim - Insufficient Details Provided");
            if(VerifyDuplicateComments && VerifyWorkItem)
                gm.LogPass("Insureworx Refer to flow", "Claim sent to on Flow Successfully");
                 else
                   gm.LogFail("Insureworx Refer to flow", "Failed to send claim on Flow");
            driver.close();

        } catch (Exception e) {
            log.error(e.getMessage());
        }
    }

    @Test(dataProvider = "ClaimSubmission")
    public void TC_5(Map<String,String> Getvalue, Method method)  {
        try {
            test = report.createTest(method.getName());
            //email.SendEmail();
            gm.UpdatePolicyStatus("com.ibm.as400.access.AS400JDBCDriver", "jdbc:as400:fnbpre.fnb.co.za", Getvalue.get("Policy Number"), "FNBTTEST5", "FNBTTEST5", 6);
            gm.ClearWorkItemFromDB("com.ibm.as400.access.AS400JDBCDriver", "jdbc:as400://fnbpre.fnb.co.za;libraries=WORKFLOW;", Getvalue.get("Policy Number"), "FNBFLOW", "FNBFLOW");
            gm.DeleteClaimsFromDB("com.ibm.as400.access.AS400JDBCDriver", "jdbc:as400:fnbpre.fnb.co.za", Getvalue.get("Policy Number"), "FNBTTEST5", "FNBTTEST5");

            gm.launchBrowser("chrome");

            gm.GoToURL(EnvironmentVariables.InsureworxPre);
            Boolean LoginStatus = login.LoginToInsureworx(Getvalue.get("Username"), Getvalue.get("Password"));
            if (LoginStatus)
                gm.LogPass("Insureworx Login Page", "Insureworx Login successful");
                 else
                  gm.LogFail("Insureworx Login Page", "Insureworx Login Unsuccessful");

            Boolean IsPolicySubmitted = home.SubmitClaimForm(Getvalue.get("OperationDesk"), Getvalue.get("RequestChannel"), Getvalue.get("ProcessCategory"), Getvalue.get("SubProcessCategory"), Getvalue.get("Policy Number"), Getvalue.get("DeceasedID"), Getvalue.get("PolicyHolderID"), Getvalue.get("BeneficiaryID"), Getvalue.get("FirstName"), Getvalue.get("LastName"), Getvalue.get("7124634535"), Getvalue.get("EmailID"), Getvalue.get("AccountNumber"), Getvalue.get("BranchCode"));
            if (IsPolicySubmitted)
                gm.LogPass("Policy submitted successfully", "Policy submitted successfully");
                 else
                  gm.LogFail("Policy Not submitted successfully", "Policy Not submitted successfully");

            home.FindPolicyUnderTask(Getvalue.get("OperationDesk"),HomePage.TeamTask, Getvalue.get("Policy Number"), 800);
            home.FindPolicyUnderTask(Getvalue.get("OperationDesk"),HomePage.MyTask, Getvalue.get("Policy Number"), 800);
            String ReviewPASValidationResult = getTextUsingJs(ReviewClaimInfo.ReviewValidationResults);
            if (ReviewPASValidationResult.equals("No members found for Deceased ID on the policy - Complete Deceased section below"))
                gm.LogPass("MS2B Outcome Validated", "MS-2B Validated Successfully");
                 else
                 gm.LogFail("MS2B Outcome not Validation failed", "MS-2B Outcome Validation Failed");
            //String Member=Getvalue.get("FirstName").concat(" ").concat(Getvalue.get("LastName"));
            String AdditionalComments="Non funeral request identified";
            MS2B.SubmitPASReviewFormAsInvalid(ReviewClaimInfo.NonFuneralClaimRequest,ReviewClaimInfo.EmbeddedChequeFuneralClaimRequest,AdditionalComments);

            gm.GoToURL(EnvironmentVariables.FlowURLForPre);
            flow.LoginToFlow("F7890124", "password");
            flow.SearchPolicyNumber(Getvalue.get("Policy Number"));
            boolean VerifyWorkItem=flow.VerifyFlowWorkItem("OTHER","New","FNBCLMINDX");
            flow.OpenFlowComments("NEW");
            boolean VerifyDuplicateComments = flow.GetCommentDescriptions("Claim Request Referred to Flow For Manual Indexing - Non-Funeral (FI) Claim Request Identified");
            if(VerifyDuplicateComments && VerifyWorkItem)
                gm.LogPass("Insureworx Refer to flow", "Claim sent to on Flow Successfully");
                 else
                  gm.LogFail("Insureworx Refer to flow", "Failed to send claim on Flow");

        } catch (Exception e) {
            log.error(e.getMessage());
        }
    }

    @Test(dataProvider = "ClaimSubmission")
    public void TC_6(Map<String,String> Getvalue, Method method)  {
        try {
            test = report.createTest(method.getName());
            //email.SendEmail();
            gm.UpdatePolicyStatus("com.ibm.as400.access.AS400JDBCDriver", "jdbc:as400:fnbpre.fnb.co.za", Getvalue.get("Policy Number"), "FNBTTEST5", "FNBTTEST5", 6);
            gm.ClearWorkItemFromDB("com.ibm.as400.access.AS400JDBCDriver", "jdbc:as400://fnbpre.fnb.co.za;libraries=WORKFLOW;", Getvalue.get("Policy Number"), "FNBFLOW", "FNBFLOW");
            gm.DeleteClaimsFromDB("com.ibm.as400.access.AS400JDBCDriver", "jdbc:as400:fnbpre.fnb.co.za", Getvalue.get("Policy Number"), "FNBTTEST5", "FNBTTEST5");

            gm.launchBrowser("chrome");
            gm.GoToURL(EnvironmentVariables.InsureworxPre);
            Boolean LoginStatus = login.LoginToInsureworx(Getvalue.get("Username"), Getvalue.get("Password"));
            if (LoginStatus)
                gm.LogPass("Insureworx Login Page", "Insureworx Login successful");
            else
                gm.LogFail("Insureworx Login Page", "Insureworx Login Unsuccessful");

            //--------------------------------------------------------------------------------------

            Boolean IsPolicySubmitted = home.SubmitClaimForm(Getvalue.get("OperationDesk"), Getvalue.get("RequestChannel"), Getvalue.get("ProcessCategory"), Getvalue.get("SubProcessCategory"), Getvalue.get("Policy Number"), Getvalue.get("DeceasedID"), Getvalue.get("PolicyHolderID"), Getvalue.get("BeneficiaryID"), Getvalue.get("FirstName"), Getvalue.get("LastName"), Getvalue.get("7124634535"), Getvalue.get("EmailID"), Getvalue.get("AccountNumber"), Getvalue.get("BranchCode"));
            if (IsPolicySubmitted)
                gm.LogPass("Policy submitted successfully", "Policy submitted successfully");
            else
                gm.LogFail("Policy Not submitted successfully", "Policy Not submitted successfully");


            home.FindPolicyUnderTask(Getvalue.get("OperationDesk"),HomePage.TeamTask, Getvalue.get("Policy Number"), 800);
            home.FindPolicyUnderTask(Getvalue.get("OperationDesk"),HomePage.MyTask, Getvalue.get("Policy Number"), 800);
            String DeathValidationResult = getTextUsingJs(VOPDDeathValidations.DeathValidationResults);
            if (DeathValidationResult.equals("Auto Death Validation failed and waiver rules have NOT been applied - Please complete full Manual Validation for Deceased"))
                gm.LogPass("VOPD Outcome Validated", "VOPD Outcome Validated Successfully");
            else
                gm.LogFail("VOPD Outcome not Validation failed", "VOPD Outcome Validation Failed");
            VOPD.SubmitVOPDValidationForm(Getvalue.get("VOPD_Status"), Getvalue.get("VOPD_DOD"), Getvalue.get("CauseOfDeath"),Getvalue.get("PlaceOfDeath"), Getvalue.get("DeathDescription"), Getvalue.get("ValidationSource"), Getvalue.get("Status"), Getvalue.get("ValidationOutcome"), Getvalue.get("DeathValidationComments"), Getvalue.get("VOPD_FinalOutcome"), Getvalue.get("VOPDAdditionalComments"));


            //-----------------Beneficiary Tracing--------------------
            System.out.println("Beneficiary Tracing - MS-7 Started");
            home.FindPolicyUnderTask(Getvalue.get("OperationDesk"),HomePage.TeamTask, Getvalue.get("Policy Number"), 300);
            home.FindPolicyUnderTask(Getvalue.get("OperationDesk"),HomePage.MyTask, Getvalue.get("Policy Number"), 300);
            String BeneficiaryResult = home.getTextUsingJs(BeneficiaryTrace.BeneficiaryValidationResult);
            if (BeneficiaryResult.equals("Client Initiated Claim Request Received - The Claimant details provided does not match the Beneficiary details for the Policy. Please attempt to contact the correct Beneficiary and inform of the potential claim. Beneficiary details were retrieved from MINT."))
                gm.LogPass("Beneficiary Outcome Validated", "Beneficiary Outcome Validated Successfully");
            else
                gm.LogFail("Beneficiary Outcome not Validation failed", "Beneficiary Outcome Validation Failed");
            BeneficiaryTrace.SubmitBeneficiaryTracingForm(Getvalue.get("BeneficiaryType"), Getvalue.get("BeneficiaryNo"), Getvalue.get("BeneficiaryAccount"), Getvalue.get("BeneficiaryBranchCode"), Getvalue.get("RelationToDeceased"), Getvalue.get("RelationSource"), Getvalue.get("BeneficiaryOutcome"), Getvalue.get("BeneficiaryAdditionalComments"));
            home.SubmitClaim(HomePage.SubmitFormNotification);

            gm.GoToURL(EnvironmentVariables.FlowURLForPre);
            flow.LoginToFlow("F7890124", "password");
            flow.SearchPolicyNumber(Getvalue.get("Policy Number"));
            boolean VerifyWorkItem=flow.VerifyFlowWorkItem("FUNERLCLM","INDEXED","FNRWRK");
            flow.OpenFlowComments("INDEXED");
            boolean VerifyDuplicateComments = flow.GetCommentDescriptions("Claim Referred to Flow for Long-Term Beneficiary Tracing");
            if(VerifyDuplicateComments && VerifyWorkItem)
                gm.LogPass("Insureworx Refer to flow", "Claim sent to on Flow Successfully");
            else
                gm.LogFail("Insureworx Refer to flow", "Failed to send claim on Flow");


        } catch (Exception e) {
            log.error(e.getMessage());
        }
    }

    @Test(dataProvider = "ClaimSubmission")
    public void TC_7(Map<String,String> Getvalue, Method method) throws InterruptedException, EmailException {
        try {
            test = report.createTest("Insurewrox Duplicate Test");
            gm.UpdatePolicyStatus("com.ibm.as400.access.AS400JDBCDriver", "jdbc:as400:fnbpre.fnb.co.za", Getvalue.get("Policy Number"), "FNBTTEST5", "FNBTTEST5", 6);
            gm.ClearWorkItemFromDB("com.ibm.as400.access.AS400JDBCDriver", "jdbc:as400://fnbpre.fnb.co.za;libraries=WORKFLOW;", Getvalue.get("Policy"), "FNBFLOW", "FNBFLOW");
            gm.DeleteClaimsFromDB("com.ibm.as400.access.AS400JDBCDriver", "jdbc:as400:fnbpre.fnb.co.za", Getvalue.get("Policy Number"), "FNBTTEST5", "FNBTTEST5");
            //email.SendEmail();

            gm.launchBrowser("chrome");
            gm.GoToURL(EnvironmentVariables.InsureworxPre);
            Boolean LoginStatus = login.LoginToInsureworx(Getvalue.get("Username"), Getvalue.get("Password"));
            if (LoginStatus)
                gm.LogPass("Insureworx Login Page", "Insureworx Login successful");
            else
                gm.LogFail("Insureworx Login Page", "Insureworx Login Unsuccessful");


            //-------------------------Upload Functionality----------------------------
            /* home.FindPolicyUnderTask(HomePage.MyTask,Policy,300);
            MS5.ClickElement(MS5.ExpandForm(HomePage.MedicalPractitionerForm));
            String path=System.getProperty("user.dir")+"\\src\\test\\java\\InputData\\Welcome Letter-FI.pdf";
            //By locator=By.xpath("//app-medical-validations/div/app-validation-result/div/div/div/app-uploadfile//span[1]/mat-icon");
            robot.UploadFiles(HomePage.MediacalFileUpload,path);
            WaitforVisibilityOFElement(HomePage.UploadedFiles);
            boolean b1=home.VerifyCRMImage(path);
            boolean b2=pdf.DownloadandVerifyCRMFile(EnvironmentVariables.DownloadFilePath+"Welcome Letter-FI.pdf");
            home.ClosePDFDoc();
            pdf.DeleteFile(EnvironmentVariables.DownloadFilePath+"Welcome Letter-FI.pdf");
            */

            //--------------------------------------------------------------------------------------

            Boolean IsPolicySubmitted = home.SubmitClaimForm(Getvalue.get("OperationDesk"), Getvalue.get("RequestChannel"), Getvalue.get("ProcessCategory"), Getvalue.get("SubProcessCategory"), Getvalue.get("Policy Number"), Getvalue.get("DeceasedID"), Getvalue.get("PolicyHolderID"), Getvalue.get("BeneficiaryID"), Getvalue.get("FirstName"), Getvalue.get("LastName"), Getvalue.get("7124634535"), Getvalue.get("EmailID"), Getvalue.get("AccountNumber"), Getvalue.get("BranchCode"));
            if (IsPolicySubmitted)
                gm.LogPass("Policy submitted successfully", "Policy submitted successfully");
            else
                gm.LogFail("Policy Not submitted successfully", "Policy Not submitted successfully");


            home.FindPolicyUnderTask(Getvalue.get("OperationDesk"),HomePage.TeamTask, Getvalue.get("Policy Number"), 300);
            home.FindPolicyUnderTask(Getvalue.get("OperationDesk"),HomePage.MyTask, Getvalue.get("Policy Number"), 300);
            String DeathValidationResult = getTextUsingJs(VOPDDeathValidations.DeathValidationResults);
            if (DeathValidationResult.equals("Auto Death Validation failed and waiver rules have NOT been applied - Please complete full Manual Validation for Deceased"))
                gm.LogPass("VOPD Outcome Validated", "VOPD Outcome Validated Successfully");
            else
                gm.LogFail("VOPD Outcome not Validation failed", "VOPD Outcome Validation Failed");
            VOPD.SubmitVOPDValidationForm(Getvalue.get("VOPD_Status"), Getvalue.get("VOPD_DOD"), Getvalue.get("CauseOfDeath"),Getvalue.get("PlaceOfDeath"), Getvalue.get("DeathDescription"), Getvalue.get("ValidationSource"), Getvalue.get("Status"), Getvalue.get("ValidationOutcome"), Getvalue.get("DeathValidationComments"), Getvalue.get("VOPD_FinalOutcome"), Getvalue.get("VOPDAdditionalComments"));


            //-----------------Beneficiary Account Validation MS-8B--------------------
            System.out.println("Beneficiary Tracing - MS-7 Started");
            home.FindPolicyUnderTask(Getvalue.get("OperationDesk"),HomePage.TeamTask, Getvalue.get("Policy Number"), 300);
            home.FindPolicyUnderTask(Getvalue.get("OperationDesk"),HomePage.MyTask, Getvalue.get("Policy Number"), 300);

            String BeneficiaryResult = home.getTextUsingJs(BeneficiaryTrace.BeneficiaryValidationResult);
            if (BeneficiaryResult.equals("Client Account Validation Failed - Account Does Not Exist - Please Review and Confirm Account Details"))
                gm.LogPass("Beneficiary Outcome Validated", "Beneficiary Outcome Validated Successfully");
            else
                gm.LogFail("Beneficiary Outcome not Validation failed", "Beneficiary Outcome Validation Failed");
            MS_8B.SubmitBeneficiaryForm_8B(Getvalue.get("BeneficiaryType"), Getvalue.get("BeneficiaryNo"), Getvalue.get("BeneficiaryAccount"), Getvalue.get("BeneficiaryBranchCode"), Getvalue.get("RelationToDeceased"), Getvalue.get("RelationSource"), Getvalue.get("BeneficiaryOutcome"), Getvalue.get("BeneficiaryAdditionalComments"));

            //-------------------------Beneficiary Account Validations-MS8C-----------------------------------

            System.out.println("Beneficiary Account Validations - MS-8C Started");
            home.FindPolicyUnderTask(Getvalue.get("OperationDesk"),HomePage.TeamTask, Getvalue.get("Policy Number"), 300);
            home.FindPolicyUnderTask(Getvalue.get("OperationDesk"),HomePage.MyTask, Getvalue.get("Policy Number"), 300);

            String HyphenResult = home.getTextUsingJs(BeneficiaryAccountValidation.HyphenAccountResult);
            if (HyphenResult.equals("Client Account Validation Failed - Account Does Not Exist - Please Review and Confirm Account Details"))
                gm.LogPass("Beneficiary Account Validations", "Beneficiary Account Validations Form Submitted Successfully");
            else
                gm.LogFail("Beneficiary Account Validations", "Beneficiary Account Validations Form Submitted Failed");
            System.out.println("Beneficiary Account Validations - MS-8C Ended");
            Hyphen.SubmitAccountValidationForm(Getvalue.get("HyphenBeneficiaryType"), Getvalue.get("HyphenBeneficiaryBankName"), Getvalue.get("HyphenBeneficiaryNumber"), Getvalue.get("HyphenBeneficiaryAccountType"), Getvalue.get("HyphenBeneficiaryAccountNumber"), Getvalue.get("HyphenBeneficiaryAccBranchCode"), Getvalue.get("HyphenRelationtoDeceasedDropdown"), Getvalue.get("HyphenRelationSource"), Getvalue.get("HyphenRelationshipSource"), Getvalue.get("HyphenValidationSource"), Getvalue.get("HyphenStatus"), Getvalue.get("HyphenValidationOutcome"), Getvalue.get("BeneficiaryAccountExist"), Getvalue.get("BeneficiaryAccountAcceptPayment"), Getvalue.get("IsBeneficiaryAccountHolder"), Getvalue.get("IsBenficiaryAccountTypeValid"), Getvalue.get("IsBeneficiaryAccountOpen"), Getvalue.get("IsBenficiaryAccOpen3Months"), Getvalue.get("HyphenFinalOutcome"), Getvalue.get("HyphenAdditionalComments"));


            //--------------------------Real Time screening------------------------------------------
            home.FindPolicyUnderTask(Getvalue.get("OperationDesk"),HomePage.TeamTask, Getvalue.get("Policy Number"), 300);
            home.FindPolicyUnderTask(Getvalue.get("OperationDesk"),HomePage.MyTask, Getvalue.get("Policy Number"), 300);
            String RTS_Result = home.getTextUsingJs(RealTimeScreening.RTS_Result);
            System.out.println("Unable to Screen Beneficiary via CIS/Hogan – Please Screen Beneficiary via RTS Tool");
            if (RTS_Result.equals("Unable to Screen Beneficiary via CIS/Hogan – Please Screen Beneficiary via RTS Tool"))
                gm.LogPass("RTS Validations", "RTS Validations Form Submitted Successfully");
                 else
                  gm.LogFail("RTS Validations", "RTS Validations Form Submitted Failed");

            RTS.SubmitRTSForm(Getvalue.get("RTSReferenceNumber"), Getvalue.get("RTSResult"));
            //-------------------------ComplexClaimAssessment(MS-5)-----------------------------------

            home.FindPolicyUnderTask(Getvalue.get("OperationDesk"),HomePage.TeamTask, Getvalue.get("Policy Number"), 300);
            home.FindPolicyUnderTask(Getvalue.get("OperationDesk"),HomePage.MyTask, Getvalue.get("Policy Number"), 300);
            String ComplexAssessment = "Business Filter\n" +
                    "Manual Assessment - Business Rules Not Passed - Deceased Cover Changed in the Last 12 Months - Unnatural Death - Review System Assessment Results and complete Manual Claims Assessment\n" +
                    "\n" +
                    "Risk Filter\n" +
                    "Manual Assessment - Manual Assessment Required - Account Validated Manually - Review Results, Request Documents if Required and Complete Manual Claims Assessment";
            String MS5_Results = home.getTextUsingJs(ComplexClaimAssessment.ComplexAssessmentResults);
            if (MS5_Results.equals(ComplexAssessment))
                gm.LogPass("MS-5 Recalculation", "MS-5 Submitted for Recalculation Successfully");
                 else
                   gm.LogFail("MS-5 Recalculation", "MS-5 Forms Submitted for Recalculation Failed");

            MS5.SelectFinalDecisionAndReason(ComplexClaimAssessment.Approve_Claim);
            String ApproveComment = "Submitted as approved from MS-5";
            gm.SendText(HomePage.AdditionalComments, ApproveComment);
            ClickElement(ComplexClaimAssessment.ConfirmationCheckbox);
            home.SubmitClaim();


            System.out.println("=====================Flow Search===================");
            gm.GoToURL(EnvironmentVariables.FlowURLForPre);
            flow.LoginToFlow("F7890124", "password");
            flow.SearchPolicyNumber(Getvalue.get("Policy Number"));
            flow.OpenFlowComments("APPROVED");
            Boolean flag=flow.GetCommentDescriptions("Claim Approved on InsureWorX");
            if(flag)
              gm.LogPass("Approved on Flow", "Approved on Flow Successfully");
                 else
                  gm.LogFail("Not Approved", "Not Approved on Flow");

        } catch (Exception e) {
            System.out.println(e.getMessage());
        }

    }


   /* @DataProvider(name="ClaimSubmission")
    public Object[][] value(ITestContext context) throws Exception
   {
	   String sheetname=context.getCurrentXmlTest().getParameter("sheetName");
	   System.out.println(sheetname);
	   Object[][] c1=gm.ReadExcel(EnvironmentVariables.ExcelsheetPath,sheetname);
	   return c1;	   
   }*/


    /*
    @DataProvider(name = "ClaimSubmission")
    public static Object[][] value(Method m) throws Exception {
        String TestCase = m.getName();
        List<Map<String, String>> list = GenericMethod.ReadExcelSheet(EnvironmentVariables.ExcelsheetPath, "TestData");
        List<Map<String, String>> ExecutionList = new ArrayList<>();
        for (int i = 0; i < list.size(); i++) {
            if (list.get(i).get("Test_Case").equalsIgnoreCase(TestCase)) {
                ExecutionList.add(list.get(i));
            }
        }
        return (Object[][]) ExecutionList.toArray();
    }
    */

    @Test(dataProvider = "ClaimSubmission")
    public void TC_8(Map<String,String> Getvalue, Method method)  {
        try {
            test = report.createTest(method.getName());

            gm.UpdatePolicyStatus("com.ibm.as400.access.AS400JDBCDriver", "jdbc:as400:fnbpre.fnb.co.za", Getvalue.get("Policy Number"), "FNBTTEST5", "FNBTTEST5", 6);
            gm.ClearWorkItemFromDB("com.ibm.as400.access.AS400JDBCDriver", "jdbc:as400://fnbpre.fnb.co.za;libraries=WORKFLOW;", Getvalue.get("Policy Number"), "FNBFLOW", "FNBFLOW");
            gm.DeleteClaimsFromDB("com.ibm.as400.access.AS400JDBCDriver", "jdbc:as400:fnbpre.fnb.co.za", Getvalue.get("Policy Number"), "FNBTTEST5", "FNBTTEST5");
            //String url=gm.getURL();

            Boolean LoginStatus = login.LoginToInsureworx(Getvalue.get("Username"), Getvalue.get("Password"));
            if (LoginStatus)
                gm.LogPass("Insureworx Login Page", "Insureworx Login successful");
            else
                gm.LogFail("Insureworx Login Page", "Insureworx Login Unsuccessful");

            //--------------------------------------------------------------------------------------

            home.FindPolicyUnderTask(Getvalue.get("OperationDesk"),HomePage.TeamTask, Getvalue.get("Policy Number"), 800);
            home.FindPolicyUnderTask(Getvalue.get("OperationDesk"),HomePage.MyTask, Getvalue.get("Policy Number"), 800);
            String OCRValidationResult = getTextUsingJs(ReviewClaimInfo_OCR.ReviewValidationResults_OCR);
            if (OCRValidationResult.equals("No Deceased ID Number captured/extracted"))
                gm.LogPass("OCR Outcome Validated", "OCR Outcome Validated Successfully");
            else
                gm.LogFail("OCR Outcome not Validation failed", "OCR Outcome Validation Failed");

            String AdditionalComments="Non funeral request identified";
            MS2B.SubmitPASReviewFormAsInvalid(ReviewClaimInfo.NonFuneralClaimRequest,ReviewClaimInfo.FuneralClaimRequest,AdditionalComments);

            gm.GoToURL(EnvironmentVariables.FlowURLForInt);
            flow.LoginToFlow("F7890124", "password");
            flow.SearchPolicyNumber(Getvalue.get("Policy Number"));
            flow.OpenFlowComments("NEW");
            boolean NonFuneralRequest = flow.GetCommentDescriptions("Claim Request Referred to Flow For Manual Indexing - Non-Funeral (FI) Claim Request Identified");
            if(NonFuneralRequest)
                gm.LogPass("Non funeral indexing", "Claim offboarded on Flow Successfully");
                 else
                   gm.LogFail("Non funeral indexing", "Failed to offboard claim on Flow");


        } catch (Exception e) {
            log.error(e.getMessage());
        }
    }

    @Test(dataProvider = "ClaimSubmission")
    public void TC_9(Map<String,String> Getvalue, Method method)  {
        try {
            test = report.createTest(method.getName());

            gm.UpdatePolicyStatus("com.ibm.as400.access.AS400JDBCDriver", "jdbc:as400:fnbpre.fnb.co.za", Getvalue.get("Policy Number"), "FNBTTEST5", "FNBTTEST5", 6);
            gm.ClearWorkItemFromDB("com.ibm.as400.access.AS400JDBCDriver", "jdbc:as400://fnbpre.fnb.co.za;libraries=WORKFLOW;", Getvalue.get("Policy Number"), "FNBFLOW", "FNBFLOW");
            gm.DeleteClaimsFromDB("com.ibm.as400.access.AS400JDBCDriver", "jdbc:as400:fnbpre.fnb.co.za", Getvalue.get("Policy Number"), "FNBTTEST5", "FNBTTEST5");
            email.SendEmail(Getvalue.get("EmailUsername"),Getvalue.get("EmailPassword"),Getvalue.get("Email Subject"),Getvalue.get("Email Body"));

            String url=gm.getURL();
            Boolean LoginStatus = login.LoginToInsureworx(Getvalue.get("Username"), Getvalue.get("Password"));
            if (LoginStatus)
                gm.LogPass("Insureworx Login Page", "Insureworx Login successful");
            else
                gm.LogFail("Insureworx Login Page", "Insureworx Login Unsuccessful");

            //--------------------------------------------------------------------------------------

            home.FindPolicyUnderTask(Getvalue.get("OperationDesk"),HomePage.TeamTask, Getvalue.get("Policy Number"), 800);
            home.FindPolicyUnderTask(Getvalue.get("OperationDesk"),HomePage.MyTask, Getvalue.get("Policy Number"), 800);
            String OCRValidationResult = getTextUsingJs(ReviewClaimInfo_OCR.ReviewValidationResults_OCR);
            if (OCRValidationResult.equals("No Deceased ID Number captured/extracted"))
                gm.LogPass("OCR Outcome Validated", "OCR Outcome Validated Successfully");
            else
                gm.LogFail("OCR Outcome not Validation failed", "OCR Outcome Validation Failed");

            String AdditionalComments="Closing funeral request";
            MS2B.SubmitPASReviewFormAsInvalid(ReviewClaimInfo.CloseClaim,ReviewClaimInfo.InsufficientClaimDetails,AdditionalComments);

            gm.GoToURL(EnvironmentVariables.FlowURLForInt);
            flow.LoginToFlow("F7890124", "password");
            flow.SearchPolicyNumber(Getvalue.get("Policy Number"));
            flow.OpenFlowComments("NTU");
            boolean NonFuneralRequest = flow.GetCommentDescriptions("Unable to Initiate Claim - Insufficient Details Provided");
            if(NonFuneralRequest)
                gm.LogPass("Closing claim", "Claim offboarded on Flow Successfully");
            else
                gm.LogFail("Closing claim", "Failed to offboard claim on Flow");


        } catch (Exception e) {
            log.error(e.getMessage());
        }
    }

    @AfterMethod
    public void teardown() {
        report.flush();
        driver.quit();
    }



    @DataProvider(name = "ClaimSubmission")
    public Object[][] values(Method m, ITestContext context) throws Exception {

        String TestCase = m.getName();
        List<Map<String, String>> ReadEnvironmentType=GenericMethod.ReadEnvironmentTypeFromExcel(EnvironmentVariables.ExcelsheetPath,"TestCase");
        String EnvType=ReadEnvironmentType.get(0).get("EnvironmentType");
        gm.launchBrowser("chrome");
        List<Map<String, String>> list=null;
        if(EnvType.equals("INT")) {
            gm.GoToURL(EnvironmentVariables.InsureworxInt);
               list= GenericMethod.ReadExcelSheet(EnvironmentVariables.ExcelsheetPath, "INT_TestData");
        }
        else {
            gm.GoToURL(EnvironmentVariables.InsureworxPre);
                 list = GenericMethod.ReadExcelSheet(EnvironmentVariables.ExcelsheetPath, "QA_TestData");
        }
        List<Map<String, String>> ExecutionList = new ArrayList<>();
        for (int i = 0; i < list.size(); i++) {
            if (list.get(i).get("Test_Case").equalsIgnoreCase(TestCase)) {
                ExecutionList.add(list.get(i));
            }
        }
        return new Object[][]{
                ExecutionList.toArray()
        };
    }
}