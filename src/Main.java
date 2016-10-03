import java.io.*;
import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by Rayed Bin Wahed on 10/3/16.
 * Assignment# 01
 * Parsing Log File
 */

public class Main {

    private String url;
    private Scanner sc;

    private static final String REGEX_HOUR = "\\d{2}:\\d{2}:\\d{2}";
    private static final String REGEX_URI = "URI\\=\\[.*\\]";
    //private static final String REGEX_

    public static void main(String [] args){
        new Main().go();
    }

    public Main() {
        sc = new Scanner(System.in);
    }

    private void go() {
        System.out.println("Enter url");
        //input = sc.nextLine().trim();

        BufferedReader reader = null;
        BufferedWriter writer = null;

        try {
            reader = new BufferedReader(new FileReader("sample.log"));
            writer = new BufferedWriter(new FileWriter("out.log"));
            String line = null;
            while ((line = reader.readLine()) != null){
                String uri = getURI(line);
                if (uri == null){
                    continue;
                }
                System.out.println(uri);
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                reader.close();
                writer.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

    }


    private String getURI(String line) {
        Pattern p = Pattern.compile(REGEX_URI);
        Matcher m = p.matcher(line);
        String s = "";
        if (m.find()){
            return m.group();
        }
        return null;
    }
}
