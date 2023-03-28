import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Scanner;

public class CSVParser {
    public static Item[] ParseCSV(String fileName) {
        File file = new File(fileName);
        Scanner scanner = null;
        try {
            scanner = new Scanner(file);
        } catch (FileNotFoundException exception) {
            System.out.println(exception.toString());
            return new Item[]{};
        }
        String firstLine = scanner.nextLine();
        String[] columns = firstLine.split(", ");
        ArrayList<Item> items = new ArrayList<>();
        while (scanner.hasNextLine()) {
            String[] values = scanner.nextLine().split(", ");
            items.add(new Item(Integer.parseInt(values[0]), Integer.parseInt(values[1]), Integer.parseInt(values[2]), values[3], values[4], values[5], Integer.parseInt(values[6])));
        }
        return items.toArray(new Item[0]);
    }
}
