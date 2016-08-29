import javax.mail.*;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import java.util.Properties;

/**
 * Created by Oscar on 2016-07-20.
 */

public class Email {

    public Email(String subject, String body, String toEmail){
        final String fromEmail = "robot@andell.eu";
        final String password = "engelbrekt";

        Properties props = new Properties();
        props.put("mail.smtp.host", "send.one.com"); //SMTP Host
        props.put("mail.smtp.socketFactory.port", "465"); //SSL Port
        props.put("mail.smtp.socketFactory.class",
                "javax.net.ssl.SSLSocketFactory"); //SSL Factory Class
        props.put("mail.smtp.auth", "true"); //Enabling SMTP Authentication
        props.put("mail.smtp.port", "465"); //SMTP Port

        Authenticator auth = new Authenticator() {
            //override the getPasswordAuthentication method
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication(fromEmail, password);
            }
        };
        Session session = Session.getDefaultInstance(props, auth);
        sendEmail(session, toEmail, subject, body);
    }

    public static void sendEmail(Session session, String toEmail, String subject, String body){
        try
        {
            MimeMessage msg = new MimeMessage(session);
            //set message headers
            msg.addHeader("Content-type", "text/HTML; charset=UTF-8");
            msg.addHeader("format", "flowed");
            msg.addHeader("Content-Transfer-Encoding", "8bit");
            msg.setFrom(new InternetAddress("robot@andell.eu", "robot"));
            msg.setReplyTo(InternetAddress.parse("robot@andell.eu", false));
            msg.setSubject(subject);
            msg.setText(body);
            msg.setRecipients(Message.RecipientType.TO, InternetAddress.parse(toEmail, false));
            Transport.send(msg);
            System.out.println("EMail Sent Successfully!!");
    }
        catch (Exception e) {
            e.printStackTrace();
        }
    }
}
