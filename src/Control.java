import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.*;

/**
 * Created by rayed on 10/4/16.
 */
public class Control {

    public String uri;
    public Parser parser;

    public Control(){
        parser = new Parser();
    }

    public void parseLog() {
        getUserInput();
        parser.parse(uri);
    }

    public void getUserInput() {
        Scanner sc = new Scanner(System.in);
        System.out.println("Enter uri:");
        uri = sc.nextLine().trim();
    }

    public void writeResults() {
        System.out.println("See out.log");
        BufferedWriter writer;
        try {
            writer = new BufferedWriter(new FileWriter("out.log"));
            writer.write("Showing results for: " + uri);
            writer.newLine();
            writer.newLine();
            writer.write("     TIME          GET            POST             SERVER TIME");
            writer.newLine();
            String ampm = "am";
            for (Map.Entry<Integer, ArrayList<Integer>> entry : parser.map.entrySet()){
                int key = entry.getKey();
                ArrayList<Integer> values = entry.getValue();
                if (key > 11){
                    ampm = "pm";
                }
                int length = String.valueOf(key).length();
                String zero = "";
                if (length == 1){
                    zero = "0";
                }
                writer.write(zero + key + ":00 " + ampm + " -> " + values.get(0) + " GET Requests, " +
                        values.get(1) + " POST Requests, " + "Total Server Time = " +
                        values.get(2) + "ms");
                writer.newLine();
            }
            writer.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void sort(){
        List<Map.Entry<Integer,ArrayList<Integer>>> entries = new ArrayList<>(parser.map.entrySet());
        Collections.sort(entries, new Comparator<Map.Entry<Integer, ArrayList<Integer>>>() {
            @Override
            public int compare(Map.Entry<Integer, ArrayList<Integer>> integerArrayListEntry, Map.Entry<Integer, ArrayList<Integer>> integerArrayListEntry2) {
                int serverTime = integerArrayListEntry2.getValue().get(2).compareTo(integerArrayListEntry.getValue().get(2));
                if (serverTime == 0){
                    int postTime = integerArrayListEntry2.getValue().get(1).compareTo(integerArrayListEntry.getValue().get(1));
                    if (postTime == 0){
                        int getTime = integerArrayListEntry2.getValue().get(0).compareTo(integerArrayListEntry.getValue().get(0));
                        return getTime;
                    }
                }
                return serverTime;
            }

        });
        Map<Integer, ArrayList<Integer>> sortedMap = new LinkedHashMap<>();
        for (Map.Entry<Integer,ArrayList<Integer>> e : entries) {
            sortedMap.put(e.getKey(), e.getValue());
        }
        parser.map = sortedMap;
    }
}
