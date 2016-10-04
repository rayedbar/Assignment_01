/**
 * Created by Rayed Bin Wahed on 10/3/16.
 * Assignment# 01
 * Parsing Log File
 */

public class Main {
    public static void main(String[] args) {
        Control control = new Control();
        control.parseLog();
        //control.writeResults();
        control.sort();
        control.writeResults();
    }
}