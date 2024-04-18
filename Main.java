import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        System.out.println("Program is ready and waiting for user command."); 
        Index index = new Index();

        Scanner scanner = new Scanner(System.in);
        String input = scanner.nextLine();
            while(!input.equals("QUIT")){
                if (input.equals("CREATE INDEX ON Project2Dataset (RandomV)")) {
                    index.createTables();
                    System.out.println("The hash-based and array-based indexes are built successfully.");
                }
                else if(input.startsWith("SELECT * FROM Project2Dataset WHERE RandomV = ")) {
                    String[] parts = input.split("= ", 2);
                    if (parts.length == 2) {
                        if(index.indexCreated()){
                            index.equalityScan(parts[1]);
                        }
                        else{
                            index.tableScanEquality(parts[1]);
                        }
                    } else {
                        System.out.println("Invalid input format.");
                    }
                }
                else if(input.startsWith("SELECT * FROM Project2Dataset WHERE RandomV > ")) {
                    String[] parts = input.split(" ");
                    String v1 = parts[7];
                    String v2 = parts[11];
                    if (parts.length >= 11) {
                        if(index.indexCreated()){
                            index.rangeScan(v1, v2);
                        }
                        else{
                            index.tableScanRange(v1, v2);
                        }
                    } else {
                        System.out.println("Invalid input format.");
                    }
                }
                else if(input.startsWith("SELECT * FROM Project2Dataset WHERE RandomV != ")) {
                    String[] parts = input.split("!= ");
                    if (parts.length == 2) {
                        index.tableScanInequality(parts[1]);
                    } else {
                        System.out.println("Invalid input format.");
                    }
                }
                System.out.println("Program is ready and waiting for user command."); 
                input = scanner.nextLine();
            }
            scanner.close();
    }
}