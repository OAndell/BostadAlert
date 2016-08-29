import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;

/**
 * Created by Oscar on 2016-07-21.
 */
public class Functions {

    public static void sleep(long time){
        try {
            Thread.sleep(time);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public static ArrayList<String> getEmailList(){
        try {
            InputStream filepath = Functions.class.getResourceAsStream("/Documents/EmailList.txt");
            BufferedReader reader = new BufferedReader(new InputStreamReader(filepath));
            String line;
            ArrayList<String> emailList = new ArrayList<String>();
            for (int i = 0;(line = reader.readLine()) != null; i++) {
                emailList.add(line);
            }
            reader.close();
            return emailList;
        } catch (IOException e) {
            System.out.println("No file found");
        }
        return null;
    }

    public static String getEmailText(String path){
        try {
            InputStream filepath = Functions.class.getResourceAsStream(path);
            BufferedReader reader = new BufferedReader(new InputStreamReader(filepath));
            String line;
            String content = "";
            for (int i = 0;(line = reader.readLine()) != null; i++) {
                content = content + line + "\n";
            }
            reader.close();
            return content;
        } catch (IOException e) {
            System.out.println("No file found");
        }
        return null;
    }

}
