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

        BufferedReader reader = null;

        try {
            reader = new BufferedReader(new FileReader("sample.log"));
            String line = null;
            while ((line = reader.readLine()) != null){
                if (containsUri(line)){
                    calculate(line);
                }
            }
            printResults();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                reader.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
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
        String regex = "\\s[GP]";
        Pattern p = Pattern.compile(regex);
        Matcher m = p.matcher(line);
        if (m.find()){
            String s = m.group().trim();
            if (s.equals("G")){
                map.get(hour).set(0, map.get(hour).get(0) + 1);
            } else {
                map.get(hour).set(1, map.get(hour).get(1) + 1);
            }
        }
        serverTime(hour, line);
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

    private void printResults() {
        BufferedWriter writer = null;
        try {
            writer = new BufferedWriter(new FileWriter("out.log"));
            writer.write("Showing results for: " + uri);
            writer.newLine();
            writer.newLine();
            String s = "am";
            for (Map.Entry<Integer, ArrayList<Integer>> entry : map.entrySet()){
                int key = entry.getKey();
                ArrayList<Integer> values = entry.getValue();
                if (key > 11){
                    s = "pm";
                }
                int length = String.valueOf(key).length();
                String time = "";
                if (length == 1){
                    time = "0";
                }
                writer.write(time + key + ":00 " + s + " -> " + values.get(0) + " GET Requests, " +
                                        values.get(1) + " POST Requests, " + "Total Server Time = " +
                                                values.get(2) + "ms");
                writer.newLine();
            }
            writer.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
