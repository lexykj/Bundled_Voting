import java.io.*;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Objects;
import java.util.Scanner;

public class CombineResults {
    public static void main(String[] args) throws IOException {
        File output = new File("./combined.csv");
        if (output.createNewFile()) {
            System.out.println("File created: " + output.getName());
        } else {
            System.out.println("File already exists.");
            return;
        }
        FileWriter myWriter = new FileWriter(output.getPath());
        File borda = new File("./borda");
        myWriter.write("vote method, generation,seed, winning bundle id, winning bundle utility\n");
        for (File f : Objects.requireNonNull(borda.listFiles())) {
            Scanner scanner = new Scanner(f);
            scanner.nextLine();
            int generation = 0;
            while (scanner.hasNext()) {
                myWriter.write(String.format("borda, %d, %s\n", ++generation, scanner.nextLine()));
            }
            scanner.close();
        }
        File copeland = new File("./copeland");
        for (File f : Objects.requireNonNull(copeland.listFiles())) {
            Scanner scanner = new Scanner(f);
            scanner.nextLine();
            int generation = 0;
            while (scanner.hasNext()) {
                myWriter.write(String.format("copeland, %d, %s\n", ++generation, scanner.nextLine()));
            }
            scanner.close();
        }
    }
}
