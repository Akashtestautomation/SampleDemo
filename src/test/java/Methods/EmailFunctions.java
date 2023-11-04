package Methods;


import org.apache.commons.mail.DefaultAuthenticator;
import org.apache.commons.mail.Email;
import org.apache.commons.mail.EmailException;
import org.apache.commons.mail.SimpleEmail;

import javax.mail.*;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import javax.net.ssl.SSLServerSocket;
import javax.net.ssl.SSLServerSocketFactory;
import java.util.Properties;

import static org.openqa.selenium.remote.server.Session.*;

public class EmailFunctions {

    String to = "fnblifepre1@fnb.co.za";
    String from = "akash.shinde@fnb.co.za";
    String host = "outlook.office365.com";
    final String user = "akash.shinde@fnb.co.za";
    final String password = "Pa$$word@777";

    public  void SendEmail() throws EmailException {
        try{
        /*
        Properties prop = System.getProperties();
        prop.put("mail.smtp.auth", true);
        prop.put("mail.smtp.starttls.enable", "true");
        prop.put("mail.smtp.host", host);
        prop.put("mail.smtp.port", 587);
        prop.put("mail.smtp.ssl.trust", host);
        prop.put("mail.smtp.ssl.protocols", "TLSv1.2");
        prop.put("mail.smtp.socketFactory.class", "javax.net.ssl.SSLSocketFactory");
        prop.put("mail.smtp.auth", "true");

        Session s= Session.getDefaultInstance(prop,
                new Authenticator() {
                    @Override
                    protected PasswordAuthentication getPasswordAuthentication() {
                        return new PasswordAuthentication(user,password);
                    }
                });
        try{
            MimeMessage msg=new MimeMessage(s);
            msg.setFrom(new InternetAddress(from));
            msg.addRecipient(Message.RecipientType.TO,new InternetAddress(to));
            msg.setSubject("FI3362051");
            msg.setText("Hello, This is funeral Death claim");
            Transport.send(msg);
            System.out.println("Policy sent successfully via email");*/
        Email email = new SimpleEmail();
        email.setHostName("smtp.office365.com");
        email.setSmtpPort(587);
        email.setAuthenticator(new DefaultAuthenticator("akash.shinde@fnb.co.za", "Pa$$word@777"));
        email.setSSLOnConnect(true);
        email.setFrom("akash.shinde@fnb.co.za");
        email.setSubject("FI3362051");
        email.setMsg("Hello, This is funeral Death claim");
        email.addTo("fnblifepre1@fnb.co.za");
        email.send();
    }
    catch(Exception e){
        System.out.println(e.getMessage());
    }
 }
}
