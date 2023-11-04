package Methods;

import PageRepository.HomePage;
import org.openqa.selenium.By;
import org.openqa.selenium.remote.server.handler.ClickElement;

import java.awt.*;
import java.awt.datatransfer.StringSelection;
import java.awt.event.KeyEvent;

public class RobotClass extends GenericMethod {



    /*
    This method will set any parameter string to the system's clipboard.
     */
    public static void setClipboardData(String string) {
        //StringSelection is a class that can be used for copy and paste operations.
        StringSelection stringSelection = new StringSelection(string);
        Toolkit.getDefaultToolkit().getSystemClipboard().setContents(stringSelection, null);
    }

    public  void UploadFiles(By locator , String Path) throws AWTException, InterruptedException {

        //Setting clipboard with file location
        setClipboardData(Path);
        ClickElement(locator);
        Thread.sleep(3000);
        //native key strokes for CTRL, V and ENTER keys
        Robot r=new Robot();
        //pressing enter

        r.keyPress(KeyEvent.VK_ENTER);
        //releasing enter
        r.keyRelease(KeyEvent.VK_ENTER);
        //pressing ctrl+v
        r.keyPress(KeyEvent.VK_CONTROL);
        r.keyPress(KeyEvent.VK_V);
        //releasing ctrl+vC:\Users\F5401542\OneDrive - FRG\Downloads\Welcome Letter-FI.pdf

        r.keyRelease(KeyEvent.VK_CONTROL);
        r.keyRelease(KeyEvent.VK_V);
        //pressing enter
        r.keyPress(KeyEvent.VK_ENTER);
        //releasing enter
        r.keyRelease(KeyEvent.VK_ENTER);
    }
}
