import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Hashtable;
import java.util.Arrays;

public class Index {
    Hashtable<String, String> hash = new Hashtable<>();
    String[] array = new String[5000];

    public void createTables(){
        for(int i = 1; i <= 99; i ++){
            String fileName = "F" + i + ".txt";
            try (FileInputStream fileInputStream = new FileInputStream("Project2Dataset/" + fileName);
                InputStreamReader inputStreamReader = new InputStreamReader(fileInputStream);
                BufferedReader bufferedReader = new BufferedReader(inputStreamReader)) {
                    String text = bufferedReader.readLine();
                    for(int j = 0; j < 100; j++){
                        int index = j*40;
                        String randomV = text.substring(index+33, index+37);
                        String location = "F" + i + ":" + index;
                        if(hash.containsKey(randomV)){
                            hash.put(randomV, hash.get(randomV) + ", " + location);
                            array[Integer.parseInt(randomV)-1] = array[Integer.parseInt(randomV)-1] + ", " + location;
                            //System.out.println(array[Integer.parseInt(randomV)-1]);
                            //System.out.println(hash.get(Integer.parseInt(randomV)));
                        }
                        else{
                            array[Integer.parseInt(randomV)-1] = location;
                            hash.put(randomV, location);
                        }
                    }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public boolean indexCreated(){
        return !hash.isEmpty();
    }

    public void tableScanEquality(String v){
        System.out.println("No indexes - Full table scan in progress");
        String output = "";
        long startTime = System.nanoTime();
        for(int i = 1; i <= 99; i ++){
            String fileName = "F" + i + ".txt";
            try (FileInputStream fileInputStream = new FileInputStream("Project2Dataset/" + fileName);
                InputStreamReader inputStreamReader = new InputStreamReader(fileInputStream);
                BufferedReader bufferedReader = new BufferedReader(inputStreamReader)) {
                    String text = bufferedReader.readLine();
                    for(int j = 0; j < 100; j++){
                        int index = j*40;
                        String randomV = text.substring(index+33, index+37);
                        String location = "F" + i + ":" + index;
                        if(randomV.equals(v)){
                            output = output + location + ", ";
                        }
                    }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        long stopTime = System.nanoTime();
        System.out.println("Records: " + output.substring(0, output.length()));
        System.out.println("Data files read: 99");
        System.out.println("Time Taken: " + ((stopTime - startTime)/1000000) + " ms");
    }

    public void equalityScan(String v){
        System.out.println("Using hash-based index for scan");
        long startTime = System.nanoTime();
        this.getRecords(hash.get(v));
        long stopTime = System.nanoTime();
        System.out.println("Time Taken: " + ((stopTime - startTime)/1000000) + " ms");
    }

    public void getRecords(String records){
        String[] parts = records.split(", ");
        Arrays.sort(parts);
        String filename = null;
        FileInputStream fileInputStream = null;
        InputStreamReader inputStreamReader = null;
        BufferedReader bufferedReader = null;
        int numOfDataFiles = 0;
        System.out.println(parts.length);
        for(int i = 0; i < parts.length; i++){
            if(filename == null){
                filename = parts[i].substring(0,parts[i].indexOf(":")) + ".txt";
                try {
                    fileInputStream = new FileInputStream("Project2Dataset/" + filename);
                    inputStreamReader = new InputStreamReader(fileInputStream);
                    bufferedReader = new BufferedReader(inputStreamReader);
                    String text = bufferedReader.readLine();
                    int startIndex = Integer.parseInt(parts[i].substring(5));
                    System.out.println(text.substring(startIndex, startIndex+40));
                }
                catch (IOException e) {
                    e.printStackTrace();
                }
                numOfDataFiles++;
            }
            else if(filename.equals((parts[i].substring(0,parts[i].indexOf(":")) + ".txt"))){
                try {
                    String text = bufferedReader.readLine();
                    int startIndex = Integer.parseInt(parts[i].substring(5));
                    System.out.println(text.substring(startIndex, startIndex+40));
                }
                catch (IOException e) {
                    e.printStackTrace();
                }
            }
            else{
                filename = parts[i].substring(0,parts[i].indexOf(":")) + ".txt";
                try {
                    fileInputStream = new FileInputStream("Project2Dataset/" + filename);
                    inputStreamReader = new InputStreamReader(fileInputStream);
                    bufferedReader = new BufferedReader(inputStreamReader);
                    String text = bufferedReader.readLine();
                    int startIndex = Integer.parseInt(parts[i].substring(5));
                    System.out.println(text.substring(startIndex, startIndex+40));
                }
                catch (IOException e) {
                    e.printStackTrace();
                }
                numOfDataFiles++;
            }
        }
        System.out.println("Data files read: " + numOfDataFiles);
    }

    public void tableScanRange(String v1, String v2){
        System.out.println("No indexes - Full table scan in progress");
        String output = "";
        long startTime = System.nanoTime();
        for(int i = 1; i <= 99; i ++){
            String fileName = "F" + i + ".txt";
            try (FileInputStream fileInputStream = new FileInputStream("Project2Dataset/" + fileName);
                InputStreamReader inputStreamReader = new InputStreamReader(fileInputStream);
                BufferedReader bufferedReader = new BufferedReader(inputStreamReader)) {
                    String text = bufferedReader.readLine();
                    for(int j = 0; j < 100; j++){
                        int index = j*40;
                        String randomV = text.substring(index+33, index+37);
                        String location = "F" + i + ":" + index;
                        if(Integer.parseInt(randomV) > Integer.parseInt(v1) && Integer.parseInt(randomV) < Integer.parseInt(v2)){
                            output = output + location + ", ";
                        }
                    }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        long stopTime = System.nanoTime();
        System.out.println("Records: " + output.substring(0, output.length()));
        System.out.println("Data files read: 99");
        System.out.println("Time Taken: " + ((stopTime - startTime)/1000000) + " ms");
    }

    public void rangeScan(String v1, String v2){
        System.out.println("Using array-based index for scan");
        String output = "";
        long startTime = System.nanoTime();
        for(int i = Integer.parseInt(v1); i < Integer.parseInt(v2); i++){
            if(array[i] != null){
                output = output + array[i] + ", ";
            }
        }
        this.getRecords(output);
        long stopTime = System.nanoTime();
        System.out.println("Time Taken: " + ((stopTime - startTime)/1000000) + " ms");
    }

    public void tableScanInequality(String v){
        System.out.println("No indexes - Full table scan in progress");
        String output = "";
        long startTime = System.nanoTime();
        for(int i = 1; i <= 99; i ++){
            String fileName = "F" + i + ".txt";
            try (FileInputStream fileInputStream = new FileInputStream("Project2Dataset/" + fileName);
                InputStreamReader inputStreamReader = new InputStreamReader(fileInputStream);
                BufferedReader bufferedReader = new BufferedReader(inputStreamReader)) {
                    String text = bufferedReader.readLine();
                    for(int j = 0; j < 100; j++){
                        int index = j*40;
                        String randomV = text.substring(index+33, index+37);
                        String location = "F" + i + ":" + index;
                        if(!randomV.equals(v)){
                            output = output + location + ", ";
                        }
                    }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        long stopTime = System.nanoTime();
        System.out.println("Records: " + output.substring(0, output.length()));
        System.out.println("Data files read: 99");
        System.out.println("Time Taken: " + ((stopTime - startTime)/1000000) + " ms");
    }
}
