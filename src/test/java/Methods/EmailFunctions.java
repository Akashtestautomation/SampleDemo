package Methods;


import org.apache.commons.mail.DefaultAuthenticator;
import org.apache.commons.mail.Email;
import org.apache.commons.mail.EmailException;
import org.apache.commons.mail.SimpleEmail;
import org.openqa.selenium.remote.server.Session;

import javax.mail.*;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;
import javax.net.ssl.SSLServerSocket;
import javax.net.ssl.SSLServerSocketFactory;
import java.util.Properties;

import static org.openqa.selenium.remote.server.Session.*;

public class EmailFunctions {

    String to = "fnblifepre1@fnb.co.za";
    String from = "TestAutomation93@outlook.com";
    String host = "smtp.outlook.com";
    int port=587;

    public  void SendEmail(String Username, String Password, String Subject, String Body) throws EmailException {
        try{

            Properties prop = System.getProperties();
            prop.put("mail.smtp.auth", true);
            prop.put("mail.smtp.starttls.enable", "true");
            prop.put("mail.smtp.host", host);
            prop.put("mail.smtp.port", port);
            prop.put("mail.smtp.ssl.trust", host);

            javax.mail.Session session= javax.mail.Session.getInstance(prop, new Authenticator() {
                @Override
                protected PasswordAuthentication getPasswordAuthentication() {
                    return new PasswordAuthentication(Username,Password);
                }
            });


            /* javax.mail.Session session1= javax.mail.Session.getInstance(prop, new Authenticator() {
                @Override
                protected PasswordAuthentication getPasswordAuthentication() {
                    return new PasswordAuthentication(Username,Password);
                }
            });*/

            Message message = new MimeMessage(session);
            message.setFrom(new InternetAddress(from));
            message.setRecipients(Message.RecipientType.TO,InternetAddress.parse(to));
            message.setSubject(Subject);

            MimeBodyPart body=new MimeBodyPart();
            body.setContent(Body, "text/html; charset=utf-8");

            Multipart part=new MimeMultipart();
            part.addBodyPart(body);

            message.setContent(part);
            Transport.send(message);
        }
        catch(Exception e){
            System.out.println(e.getMessage());
        }
    }

}
