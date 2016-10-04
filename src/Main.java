import java.io.*;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by Rayed Bin Wahed on 10/3/16.
 * Assignment# 01
 * Parsing Log File
 */

public class Main {

    //private static final String REGEX_HOUR = ;
    private Map<Integer, ArrayList<Integer>> map;
    private String uri;

    public static void main(String [] args){
        new Main().go();
    }

    public Main() {
        map = new HashMap<>(24);
        initializeMap();
    }

    private void initializeMap() {
        for (int i = 0;  i < 25; ++i){
            map.put(i, new ArrayList<Integer>(Collections.nCopies(3,0)));
        }
    }

    private void go() {
        Scanner sc = new Scanner(System.in);
        System.out.println("Enter uri");
        uri = sc.nextLine().trim();

        //ListTester();

        //uri = "/ma/client/clientList.do";

        BufferedReader reader = null;


        try {
            reader = new BufferedReader(new FileReader("sample.log"));

            String line = null;
            while ((line = reader.readLine()) != null){
                if (containsUri(line)){
                    calculate(line);

                    //System.out.println(1);
                    //Tester(line);
                }
//                String uri = getURI(line);
//                if (uri.contains(input)){
//                    calculate(line);
//                    //break;
//                } else {
//                    continue;
//                }
                //uriTester(line);
            }
            //System.out.println(number_get);
            printResults();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                reader.close();
                //writer.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

    }

    private void printResults() {
        BufferedWriter writer = null;
        try {
            writer = new BufferedWriter(new FileWriter("out.log"));
            String s = "am";
            for (Map.Entry<Integer, ArrayList<Integer>> entry : map.entrySet()){
                int key = entry.getKey();
                ArrayList<Integer> values = entry.getValue();
                if (key > 11){
                    s = "pm";
                }
                writer.write(key + ":00 " + s + " -> " + values.get(0) + " GET Requests, " +
                    values.get(1) + " POST Requests, " + "Total Server Time = " + values.get(2) + "ms");
                writer.newLine();
            }
            writer.close();
        } catch (IOException e) {
            e.printStackTrace();
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


    private void calculate(String line) {
        int hour = getHour(line);
        //System.out.println("Hour: " + hour);
        String regex = "\\s[GP]";
        Pattern p = Pattern.compile(regex);
        Matcher m = p.matcher(line);
        if (m.find()){
            String s = m.group().trim();
            //System.out.println(s);
            if (s.equals("G")){
                map.get(hour).set(0, map.get(hour).get(0) + 1);
            } else {
                map.get(hour).set(1, map.get(hour).get(1) + 1);
            }
            //System.out.println(1);
        }
        serverTime(hour, line);
    }

    private void serverTime(int hour, String line) {
        String regex = "\\d+ms";
        Pattern pattern = Pattern.compile(regex);
        Matcher matcher = pattern.matcher(line);
        if (matcher.find()){
            String s = matcher.group();
            System.out.println(s);
            String si = s.replace("ms", "");
            int time = Integer.parseInt(si.trim());
            map.get(hour).set(2, map.get(hour).get(2) + time);
        }
    }

    private int getHour(String line){
        String regex = "\\d{2}:\\d{2}:\\d{2}";
        Pattern pattern = Pattern.compile(regex);
        Matcher matcher = pattern.matcher(line);
        int hour = -1;
        if (matcher.find()){
            String [] time = matcher.group().trim().split(":");
            hour = Integer.parseInt(time[0]);
        }
        return hour;
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
