import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by rayed on 10/4/16.
 */
public class Parser {

    public Map<Integer, ArrayList<Integer>> map;

    public Parser(){
        map = new HashMap<>(24);
        initializeMap();
    }

    private void initializeMap() {
        for (int i = 0;  i < 24; ++i){
            map.put(i, new ArrayList<>(Collections.nCopies(3, 0)));
        }
    }

    public void parse(String uri) {
        BufferedReader reader;
        boolean flag = false;
        try {
            reader = new BufferedReader(new FileReader("sample.log"));
            String line;
            while ((line = reader.readLine()) != null){
                if (containsUri(uri, line)){
                    parseLine(line);
                    flag = true;
                }
            }
            if (!flag){
                System.out.println("URI DOESN'T MATCH. PLEASE TRY AGAIN. Exiting!!!");
                System.exit(0);
            }
            reader.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private boolean containsUri(String uri, String line) {
        String regex = ".*\\[" + uri + "\\].*";
        return Pattern.matches(regex, line);
    }

    private void parseLine(String line) {
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
        getServerTime(hour, line);
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

    private void getServerTime(int hour, String line) {
        String regex = "\\d+ms";
        Pattern pattern = Pattern.compile(regex);
        Matcher matcher = pattern.matcher(line);
        if (matcher.find()){
            String s = matcher.group();
            String si = s.replace("ms", "");
            int time = Integer.parseInt(si.trim());
            map.get(hour).set(2, map.get(hour).get(2) + time);
        }
    }
}

