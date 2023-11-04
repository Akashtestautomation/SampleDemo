package PageRepository;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;

import java.sql.Driver;

public class GenericLocators {

    public static By  SubmitButton= By.xpath("//mat-label[contains(text(),'Submit')]");
    public static By  CRMButton= By.xpath("//mat-label[contains(text(),'CRM')]");
    public static By  NewClaimButton= By.xpath("//mat-label[contains(text(),'New Claim')]");
    public static By  SearchPolicy= By.xpath("//mat-label[contains(text(),'Search')]");
    public static By  DiarizeButton= By.xpath("//mat-label[contains(text(),'Diarize')]");
    public static By  TransferButton= By.xpath("//mat-label[contains(text(),'Transfer')]");
}
