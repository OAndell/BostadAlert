import com.gargoylesoftware.htmlunit.NicelyResynchronizingAjaxController;
import com.gargoylesoftware.htmlunit.WebClient;
import com.gargoylesoftware.htmlunit.html.HtmlPage;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;

import javax.swing.*;
import java.awt.*;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;

/**
 * Created by Oscar on 2016-07-19.
 */
public class BostadAlert {
    public static ArrayList<String> emailList;
    public static void main(String[] args){
        emailList = Functions.getEmailList();
        JOptionPane.showMessageDialog(null, "Running Program");
        boolean running = true;
        for(int i = 0; i < emailList.size(); i++) {
            //new Email("BostadAlert har startats", "BostadAlert kommer att informera via mail om det släpps nya bostäder via BostadDirekt på studentbostader.se\n \n" +
            //        "BostadAlert är en Java-applikation skapad av Oscar Andell", emailList.get(i));
        }
        while (running){
            try{
                startProgram();
                running = false;
            }catch (Exception e){
                System.out.println("Restarting");
            }
        }
    }

    private static void startProgram(){
        java.util.logging.Logger.getLogger("com.gargoylesoftware.htmlunit").setLevel(java.util.logging.Level.OFF);
        java.util.logging.Logger.getLogger("org.apache.http").setLevel(java.util.logging.Level.OFF);
        String compareString = "Område Adress Boyta Storlek Hyra Poäng Ledig";
        String strURL = "https://www.studentbostader.se/sv/sok-bostad/lediga-bostader?actionId=&omraden=&egenskaper=SNABB&objektTyper=";
        int nmbrOfChecks = 0;
        boolean running = true;
        while (running){
            String listedApartments = getRelevantContent(getPageContent(strURL));
            nmbrOfChecks++;
            if(!listedApartments.equals(compareString)){ //Change detected
                try {
                    System.out.println(listedApartments);
                    Desktop.getDesktop().browse(new URL(strURL).toURI());
                    for(int i = 0; i < emailList.size(); i++){
                        new Email("BostadAlert", Functions.getEmailText("/Documents/mailText.txt") + "\n" + listedApartments, emailList.get(i));  //Sends Message to all email addresses
                    }
                    JOptionPane.showMessageDialog(null, "Alert!", "Change detected", JOptionPane.INFORMATION_MESSAGE );
                    running = false; //closes application
                } catch (Exception e) {
                    System.out.println("error");
                }
            }
            Functions.sleep(60000);
            System.out.print(nmbrOfChecks + " ");
            if(nmbrOfChecks == 1000){
                new Email("BostadAlert is running", Functions.getEmailText("/Documents/updateEmail.txt"), "oscar@andell.eu"); //Sends email every 10h.
                nmbrOfChecks = 0;
            }
        }
    }

    /**
     * gets the html and saves it as a String.
     */
    private static String getPageContent(String strURL){
        try {
        WebClient webClient = new WebClient();
        webClient.setAjaxController(new NicelyResynchronizingAjaxController());
        HtmlPage myPage = webClient.getPage(strURL);
        webClient.waitForBackgroundJavaScript(10 * 1000);
        String theContent = myPage.asXml();
            webClient.close();
        return theContent;
        } catch (IOException e) {
            System.out.println("error");
            Functions.sleep(10000);
            return getPageContent(strURL);
        }
    }

    /**
     * Cuts out the relevant data from the html string
     */
    private static String getRelevantContent(String html){
        Document doc = Jsoup.parse(html);
        return doc.getElementsByClass("objektListaMarknad").text();
    }

}
