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

    private String uri;
    private Scanner sc;

    private static final String REGEX_HOUR = "\\d{2}:\\d{2}:\\d{2}";
    private int number_get;
    private int number_post;
    private int total_time;

    public static void main(String [] args){
        new Main().go();
    }

    public Main() {
        sc = new Scanner(System.in);
        number_get = 0;
        number_post = 0;
        total_time = 0;
    }

    private void go() {
        //System.out.println("Enter url");
        //String input = sc.nextLine().trim();

        uri = "/ma/entry";

        BufferedReader reader = null;
        BufferedWriter writer = null;

        try {
            reader = new BufferedReader(new FileReader("sample.log"));
            writer = new BufferedWriter(new FileWriter("out.log"));
            String line = null;
            while ((line = reader.readLine()) != null){
                if (containsUri(line)){
                    countGorP(line);

                    //System.out.println(1);
                    //Tester(line);
                }
//                String uri = getURI(line);
//                if (uri.contains(input)){
//                    countGorP(line);
//                    //break;
//                } else {
//                    continue;
//                }
                //uriTester(line);
            }
            System.out.println(number_get);
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

    private void Tester(String line) {
        String regex = "(\\s[GP])+";
        Pattern p = Pattern.compile(regex);
        Matcher m = p.matcher(line);
        if (m.find()){
            System.out.println(m.group(1));
        }
    }

    private boolean containsUri(String line) {
        String regex = "\\[" + uri + "\\]";
        Pattern pattern = Pattern.compile(regex);
        Matcher matcher = pattern.matcher(line);
        return matcher.find() ? true : false;
    }


    private void countGorP(String line) {
        String regex = "\\s[GP]";
        Pattern p = Pattern.compile(regex);
        Matcher m = p.matcher(line);
        if (m.find()){
            String s = m.group().trim();
            //System.out.println(s);
            if (s.equals("G")){
                number_get++;
            } else {
                number_post++;
            }
            //System.out.println(1);
        }
    }


//    private String getURI(String line) {
//        Pattern p = Pattern.compile(REGEX_URI);
//        Matcher m = p.matcher(line);
//        if (m.find()){
//            return m.group();
//        } else {
//            System.out.println("URI does not exist. Exiting!!!");
//            //System.exit(0);
//        }
//        return null;
//    }
}
