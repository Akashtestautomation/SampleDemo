package PageRepository;
import java.util.concurrent.TimeUnit;

import org.junit.validator.PublicClassValidator;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedCondition;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import com.aventstack.extentreports.model.Log;
import Methods.GenericMethod;
import freemarker.core.ReturnInstruction.Return;

public class LoginPage extends GenericMethod  
{
	GenericMethod gm=new GenericMethod();
    WebElement wb=null;
    WebDriverWait wait=null;

    public static By PasswordTextBox=By.cssSelector("input[id='mat-input-1']");
	public static By UsernameTextBox=By.cssSelector("input[id='mat-input-0']");	
	public static By LoginBtn=By.id("registrationLogin");
	public static By HomePage=By.xpath("//mat-icon[contains(text(),' account_circle ')]");
	public static By LoginButton=By.xpath("//mat-label[contains(text(),'Log')]");
	public static By LogoutButton=By.xpath("//mat-icon[contains(text(),'logout')]");
	
	
	
	public boolean LoginToInsureworx(String username, String Password )
	{
		 try
		 {
		   WebDriverWait wait=new WebDriverWait(driver,5);	 
	       wait.until(ExpectedConditions.visibilityOfElementLocated(LoginPage.LoginButton));
	       gm.SendText(Locators.cssselector,LoginPage.UsernameTextBox,username);
	       gm.SendText(Locators.cssselector,LoginPage.PasswordTextBox,Password);	
           gm.ClickElement(Locators.xpath, LoginPage.LoginButton);
           wait.until(ExpectedConditions.visibilityOfElementLocated(LoginPage.HomePage));          
           boolean flag=gm.VerifyText(Locators.xpath , LogoutButton, "logout");
           if(flag) {
			   log.info("Insureworx login is successful");
			   return true;
		   } else
			   return false;
		 }
		 catch(Exception e) {
			 log.error("Login failed on insureworx due to "+e.getMessage());
			 return false;
		 }			
	}
}
