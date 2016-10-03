import java.io.*;

/**
 * Created by Rayed Bin Wahed on 10/3/16.
 * Assignment# 01
 * Parsing Log File
 */

public class Main {
    public static void main(String [] args){
        BufferedReader reader = null;
        BufferedWriter writer = null;
        try {
            reader = new BufferedReader(new FileReader("log.txt"));
            writer = new BufferedWriter(new FileWriter("out.txt"));
            String line = null;
            while ((line = reader.readLine()) != null){
                writer.write(line);
                writer.newLine();
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
}
