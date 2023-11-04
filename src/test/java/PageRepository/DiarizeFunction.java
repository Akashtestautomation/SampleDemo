package PageRepository;

import Methods.GenericMethod;
import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.remote.server.handler.ClickElement;
import org.openqa.selenium.support.ui.ExpectedConditions;

import javax.annotation.Nullable;
import java.time.DateTimeException;
import java.time.Duration;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeFormatterBuilder;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.TimeUnit;

public class DiarizeFunction extends GenericMethod {

    HomePage home=new HomePage();

    public static By DiarizationButton=By.xpath("//button//mat-label[contains(text(),'Diarize')]");
    public static By PreferredDiarizedDate=By.xpath("//input[@name='prefContactDate']");
    public static By PreferredDiarizedTime=By.xpath("//input[@name='prefContactTime']");
    public static By DiarizedReason=By.xpath("//mat-select[@name='diariseReason']");
    public static By DocumentRequired=By.xpath("//mat-select[@name='documents']");
    public static By DocumentRequiredPopUpWindow=By.xpath("//div[@id='mat-select-26-panel']");
    public static By SelectCurrentDate=By.xpath("@class='mat-calendar-body-cell-content mat-focus-indicator mat-calendar-body-today']");
    public static By DiarizationScreen=By.cssSelector("app-diarizetask");
    public static By DiarizationComments=By.xpath("//textarea[@name='comments']");
    public static By TimePickerDialogueBox=By.cssSelector("mat-timepicker-dialog");
    public static By SelectMinsFromlIst=By.xpath("//div[@class='circle']//button ");
    public static By SelectSecondsFromlIst=By.xpath("//div[@class='circle']//button[@class='number ng-star-inserted']");
    public static By FocusCLockSeconds=By.xpath("//span[@class='time fixed-font-size select']");
    public static By ClickOk=By.xpath("//button//span[text()='Ok']");
    public static By ClickCancel=By.xpath("//button//span[text()='Cancel']");
    public static By Scheduler=By.xpath("//div[text()='Scheduler']");
    public static By ContinueDiarization=By.xpath("//button[1]//mat-label[contains(text(),'Continue')]");
    public static By OnClick_Spinner=By.xpath("//mat-spinner");
    public static By ClientCommunicationScreen=By.xpath("//span[contains(text(),'Client Communication')]");
    public static By DiarizandSendButton=By.xpath("//mat-label[contains(text(),'Diarize and Send')]");
    public static By DiarizationIndicator=By.xpath("//span[contains(text(),'Task has been diarized for')]");
    public static By CloseOpenTask=By.xpath("//mat-label[text()=' My Task ']/../mat-icon[text()=' close ']");


    public WebElement SelectDiarizedReason(String str)
    {
        WebElement wb=null;
        try
        {
            ClickElement(DiarizedReason);
            wb=wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//mat-option//span[contains(text(),'" + str + "')]")));
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
        return  wb;
    }

    public WebElement SelectRequiredDocument(String str)
    {
        WebElement wb=null;
        try
        {
            ClickElement(DocumentRequired);
            wb=wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//mat-option//span[contains(text(),'" + str + "')]")));
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
        return  wb;
    }

    public  void Diarization(String DiarizationReason, String DocumentType)
    {
        try
        {
            ClickElement(DiarizationButton);
            WaitforVisibilityOFElement(DiarizationScreen,5);

            DateTimeFormatter datetime=DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
            LocalDateTime LocalTime=LocalDateTime.now();
            String s=datetime.format(LocalTime);
            System.out.println(s);
            String [] word=s.split(" ");
            String val=word[0]+"T"+word[1];
            System.out.println(val);
            String [] Date=word[0].split("-");
            String Month=RetrieveMonthNameByNumber(Integer.parseInt(Date[1]));
            String []Time=word[1].split(":");
            //int [] Return_Time=addTime(Integer.parseInt(Time[0]),Integer.parseInt(Time[1]));
            //System.out.println(Return_Time[0]+Return_Time[1]);
            //int [] AddedTime=IncreaseMins(Integer.parseInt(Time[0]),Integer.parseInt(Time[1]),Integer.parseInt(Time[2]));
            int [] AddedTime=IncreaseMins(2);
            String v1=word[0]+"T"+AddedTime[0]+":"+AddedTime[1]+":"+AddedTime[2];
            SelectCalenderDate(Integer.parseInt(Date[0]),Month,Integer.parseInt(Date[2]));
            //Two mins will be automatically added to the current system time for diarization purpose
            SelectTimeFromClock(AddedTime[0],AddedTime[1],AddedTime[2]);
            //SelectTimeFromClock(Return_Time[0],Return_Time[1]);

            String DiarizedDate=GetElement(PreferredDiarizedDate).getAttribute("ng-reflect-model").substring(0,10);
            String DiarizedTime=GetElement(PreferredDiarizedTime).getAttribute("ng-reflect-model");

            String DiarizedSetTime=DiarizedDate.substring(16,DiarizedTime.length()-6);
            String Diarization_DateTime=DiarizedDate+"T"+DiarizedSetTime;
            System.out.println(Diarization_DateTime);

            if(DiarizationReason.equals("Documents Required")) {
                ClickElement(SelectDiarizedReason(DiarizationReason));
                ClickElement(SelectRequiredDocument(DocumentType));
                MoveAndClick(GetElement(Scheduler));
                ClickElement(Scheduler);
            }
            else {
                ClickElement(SelectDiarizedReason(DiarizationReason));
            }

            ScollToElement(ContinueDiarization);
            ClickElement(ContinueDiarization);
            WaitforInvisibilityOfElement(OnClick_Spinner,60);
            WaitforVisibilityOFElement(ClientCommunicationScreen,30);
            ScollToElement(DiarizandSendButton);
            ClickElement(DiarizandSendButton);
            WaitforInvisibilityOfElement(OnClick_Spinner,60);
            WaitforVisibilityOFElement(DiarizationIndicator,20);
            String datetimes=GetElement(DiarizationIndicator).getText();
            System.out.println("After Diarization "+datetimes);
            if(datetimes.equals(Diarization_DateTime))
                System.out.println("SET");
                  else
                      System.out.println("Not Set");
            WaitforInvisibilityOfElement(DiarizationIndicator,20);

        }
        catch (Exception e)
        {
            System.out.println(e.getMessage());
        }

    }

    public  void Diarization(String DiarizationReason)
    {
        try
        {
            ClickElement(DiarizationButton);
            WaitforVisibilityOFElement(DiarizationScreen,5);

            DateTimeFormatter datetime=DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
            LocalDateTime LocalTime=LocalDateTime.now();
            String s=datetime.format(LocalTime);
            System.out.println(s);
            String [] word=s.split(" ");
            String val=word[0]+"T"+word[1];
            System.out.println("Local Time "+val);
            String [] Date=word[0].split("-");
            String Month=RetrieveMonthNameByNumber(Integer.parseInt(Date[1]));
            String []Time=word[1].split(":");
            //int [] Return_Time=addTime(Integer.parseInt(Time[0]),Integer.parseInt(Time[1]));
            //System.out.println(Return_Time[0]+Return_Time[1]);
            int [] AddedTime=IncreaseMins(2);
            String v1=word[0]+"T"+AddedTime[0]+":"+AddedTime[1]+":"+AddedTime[2];
            System.out.println("v1---------------"+v1);
            SelectCalenderDate(Integer.parseInt(Date[0]),Month,Integer.parseInt(Date[2]));
            //Two mins will be automatically added to the current system time for diarization purpose
            System.out.println("Added time inside Select time Method "+AddedTime[0]+AddedTime[1]+AddedTime[2]);
            SelectTimeFromClock(AddedTime[0],AddedTime[1],AddedTime[2]);
            //SelectTimeFromClock(Return_Time[0],Return_Time[1]);

            String DiarizedDate=GetElement(PreferredDiarizedDate).getAttribute("ng-reflect-model").substring(0,10);
            String DiarizedTime=GetElement(PreferredDiarizedTime).getAttribute("ng-reflect-model");

            String DiarizedSetTime=DiarizedTime.substring(16,DiarizedTime.length()-6);
            String Diarization_DateTime=DiarizedDate+"T"+DiarizedSetTime;
            System.out.println("Diarization_DateTime "+Diarization_DateTime);

            ClickElement(SelectDiarizedReason(DiarizationReason));

            ScollToElement(ContinueDiarization);
            ClickElement(ContinueDiarization);
            WaitforInvisibilityOfElement(OnClick_Spinner,60);
            WaitforVisibilityOFElement(ClientCommunicationScreen,30);
            ScollToElement(DiarizandSendButton);
            ClickElement(DiarizandSendButton);
            WaitforInvisibilityOfElement(OnClick_Spinner,60);
            WaitforVisibilityOFElement(DiarizationIndicator,20);
            String datetimes=GetElement(DiarizationIndicator).getText();
            String trimTime=datetimes.substring(27,46);
            if(trimTime.equals(Diarization_DateTime))
                System.out.println("SET");
                 else
                  System.out.println("Not Set");

            long minute[]=TimeSubstraction(DiarizedSetTime);
            int min= (int) minute[1];
            WaitforInvisibilityOfElement(DiarizationIndicator,20);
            TimeUnit.MINUTES.sleep(min+1);
            boolean flag=VerifyExpiredStatus(HomePage.MyTask,"FI1988816");
        }
        catch (Exception e)
        {
            System.out.println(e.getMessage());
        }
    }

    public boolean VerifyExpiredStatus(String FieldName, String EnterValue)
    {
        boolean value=false;
        try{
              WaitforVisibilityOFElement(CloseOpenTask,10);
              ClickElement(CloseOpenTask);
              WaitforInvisibilityOfElement(CloseOpenTask,10);
              home.FindPolicyUnderTask(FieldName,EnterValue,20);
              String s= driver.findElement(By.xpath("//app-task-management-in//mat-grid-tile[19]//mat-form-field//input")).getAttribute("ng-reflect-model");
              if(s.equals("Expired"))
                  value=true;
        }
        catch (Exception e) {
            System.out.println(e.getMessage());
        }
       return value;
    }


    public  void  SelectCalenderDate(int Year, String Month, int Date)
    {
        try{
            ClickElement(PreferredDiarizedDate);
            WaitforVisibilityOFElement(By.cssSelector("mat-datepicker-content"),5);
            ClickElement(By.xpath("//button[@aria-label='Choose month and year']"));
            WaitforVisibilityOFElement(By.xpath("//button[@aria-label='Choose date']"),5);
            List<WebElement>wb =driver.findElements(By.xpath("//div[contains(@class,'mat-calendar-body-cell-content mat-focus-indicator')]"));
            for(WebElement wb1 : wb)
            {
                String year=wb1.getText();
                if(Integer.parseInt(year)==Year)
                {
                    wb1.click();
                    break;
                }
            }

            WaitforVisibilityOFElement(By.cssSelector("thead[class='mat-calendar-table-header']"),5);
            List<WebElement>SelectMonth =driver.findElements(By.xpath("//div[contains(@class,'mat-calendar-body-cell-content mat-focus-indicator')]"));
            for(WebElement wb2 : SelectMonth)
            {
                String getMonth=wb2.getText();
                if(getMonth.equals(Month))
                {
                    wb2.click();
                    break;
                }
            }

            WaitforVisibilityOFElement(By.cssSelector("thead[class='mat-calendar-table-header']"),5);
            List<WebElement>SelectDate =driver.findElements(By.xpath("//div[contains(@class,'mat-calendar-body-cell-content mat-focus-indicator')]"));
            for(WebElement wb3 : SelectDate)
            {
                String getDate=wb3.getText();
                if(Integer.parseInt(getDate)==Date)
                {
                    wb3.click();
                    break;
                }
            }
        }
        catch (Exception e)
        {
            System.out.println(e.getMessage());
        }
    }


    public  void  SelectTimeFromClock(int hours,int Min, int sec) {
        try {
            System.out.println("System Local time - "+hours+" : "+Min);
            int [] Return_Time=IncreaseMins(2);
            System.out.println("Return_Time[0] "+Return_Time[0]);
            ClickElement(PreferredDiarizedTime);
            GetElement(TimePickerDialogueBox);
            List<WebElement> Mins=driver.findElements(SelectMinsFromlIst);

            for(WebElement getMin : Mins) {
              String Minute=getMin.getText();
                System.out.println("Minute Time "+Minute);
                  if(String.valueOf(Return_Time[0]).equals(Minute)) {
                      MoveAndClick(getMin);break;
                  }
               }
            Thread.sleep(2000);
            for(int i=0;i<6;i++) {
                /*if(sec==60) {
                    if(Min==12) {
                        Min=1;sec=0;
                    }else {
                        sec=0;
                    }
                    System.out.println("Minute increased after 60 second reached "+Return_Time[0]);
                    ClickElement(ClickCancel);
                    ClickElement(PreferredDiarizedTime);
                    GetElement(TimePickerDialogueBox);
                    Mins=null;
                    Mins=driver.findElements(SelectMinsFromlIst);

                    for(WebElement getMin : Mins) {
                        String Minute=getMin.getText();
                        if(String.valueOf(Return_Time[0]).equals(Minute)) {
                            MoveAndClick(getMin);break;
                        }
                    }

                    break;
                    *//* DateTimeFormatter datetime=DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");
                    LocalDateTime LocalTime=LocalDateTime.now();
                    String s=datetime.format(LocalTime);

                    String [] word=s.split(" ");
                    String [] Date=word[0].split("-");
                    String Month=RetrieveMonthNameByNumber(Integer.parseInt(Date[1]));
                    String []Time=word[1].split(":");

                    SelectCalenderDate(Integer.parseInt(Date[0]),Month,Integer.parseInt(Date[2]));
                    addTime(Min,sec+1);

                    Mins=null;  Mins=driver.findElements(SelectMinsFromlIst);
                    for(WebElement getMin : Mins) {
                        String Minute=getMin.getText();
                        if(String.valueOf(Return_Time[0]).equals(Minute)) {
                            MoveAndClick(getMin);break;
                        }
                    }*//*

                }*/
                if(Return_Time[1]%5==0) {
                    break;
                }
                else {
                    Return_Time[1]++;
                }
            }
            System.out.println("Time in Minute - "+Return_Time[1]);
            WaitforVisibilityOFElement(FocusCLockSeconds,5);
            List<WebElement> Seconds=driver.findElements(SelectSecondsFromlIst);
                for(WebElement Second : Seconds) {
                String Sec=Second.getText();
                if(Return_Time[1]==Integer.parseInt(Sec)) {
                    MoveAndClick(Second);break;
                }
            }
            ClickElement(ClickOk);

            /* List<WebElement> SecondsCount=driver.findElements(By.xpath("//button[@class='number minute-dot ng-star-inserted']"));
            int count=-8;
            for(WebElement Second : SecondsCount) {
                 count++;
                if(Return_Time[1]==count) {
                    MoveAndClick(Second);break;
                }
            } ClickElement(ClickOk);
            */

        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }
}
