import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Hashtable;
import java.util.Arrays;

public class Index {
    //Hashtable for Hash-Based Index Structure
    Hashtable<String, String> hash = new Hashtable<>();
    //Array for Array-Based Index Structure
    String[] array = new String[5000];

    //Method to create the hash and array index structures
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

    //Boolean to check if the index structures have been made
    public boolean indexCreated(){
        return !hash.isEmpty();
    }

    //Equality-Based Query if there are no indexes built
    public void tableScanEquality(String v){
        System.out.println("No indexes - Full table scan in progress");
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
                        if(randomV.equals(v)){
                            System.out.println(text.substring(index,index+40));
                        }
                    }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        long stopTime = System.nanoTime();
        System.out.println("Data files read: 99");
        System.out.println("Time Taken: " + ((stopTime - startTime)/1000000) + " ms");
    }

    //Equality-Based Query if there is indexes built
    //Uses the hashtable
    public void equalityScan(String v){
        System.out.println("Using hash-based index for scan");
        long startTime = System.nanoTime();
        this.getRecords(hash.get(v));
        long stopTime = System.nanoTime();
        System.out.println("Time Taken: " + ((stopTime - startTime)/1000000) + " ms");
    }

    //Method to get the records given record location.
    //Method sorts the list of records so multiple I/O doesn't occur for a single text file.
    public void getRecords(String records){
        String[] parts = records.split(", ");
        Arrays.sort(parts);
        String filename = null;
        FileInputStream fileInputStream = null;
        InputStreamReader inputStreamReader = null;
        BufferedReader bufferedReader = null;
        int numOfDataFiles = 0;
        String text = "";
        System.out.println(parts.length);
        for(int i = 0; i < parts.length; i++){
            if(filename == null){
                filename = parts[i].substring(0,parts[i].indexOf(":")) + ".txt";
                try {
                    fileInputStream = new FileInputStream("Project2Dataset/" + filename);
                    inputStreamReader = new InputStreamReader(fileInputStream);
                    bufferedReader = new BufferedReader(inputStreamReader);
                    text = bufferedReader.readLine();
                    int startIndex = Integer.parseInt(parts[i].substring(parts[i].indexOf(":") + 1));
                    System.out.println(text.substring(startIndex, startIndex+40));
                }
                catch (IOException e) {
                    e.printStackTrace();
                }
                numOfDataFiles++;
            }
            else if(filename.equals((parts[i].substring(0,parts[i].indexOf(":")) + ".txt"))){
                    int startIndex = Integer.parseInt(parts[i].substring(parts[i].indexOf(":") + 1));
                    System.out.println(text.substring(startIndex, startIndex+40));
            }
            else{
                filename = parts[i].substring(0,parts[i].indexOf(":")) + ".txt";
                try {
                    fileInputStream = new FileInputStream("Project2Dataset/" + filename);
                    inputStreamReader = new InputStreamReader(fileInputStream);
                    bufferedReader = new BufferedReader(inputStreamReader);
                    text = bufferedReader.readLine();
                    int startIndex = Integer.parseInt(parts[i].substring(parts[i].indexOf(":") + 1));
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

    //Range-Based Query if there are no indexes built
    public void tableScanRange(String v1, String v2){
        System.out.println("No indexes - Full table scan in progress");
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
                        if(Integer.parseInt(randomV) > Integer.parseInt(v1) && Integer.parseInt(randomV) < Integer.parseInt(v2)){
                            System.out.println(text.substring(index,index+40));
                        }
                    }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        long stopTime = System.nanoTime();
        System.out.println("Data files read: 99");
        System.out.println("Time Taken: " + ((stopTime - startTime)/1000000) + " ms");
    }

    //Range-Based Query if there are no indexes built
    //Uses the array
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

    //Inequality-Based Query, does not use index structures
    public void tableScanInequality(String v){
        System.out.println("No indexes - Full table scan in progress");
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
                        if(!randomV.equals(v)){
                            System.out.println(text.substring(index,index+40));
                        }
                    }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        long stopTime = System.nanoTime();
        System.out.println("Data files read: 99");
        System.out.println("Time Taken: " + ((stopTime - startTime)/1000000) + " ms");
    }
}
