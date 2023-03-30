import java.util.ArrayList;

public class Voter {
    private ArrayList<Item> Order;
    private ArrayList<Bundle> Prefrences;
    final String Name;
    public Voter(String name, ArrayList<Item> order) {
        this.Name = name;
        this.Order = order;
    }
    public void CalculatePrefrence(Bundle bundle) {
    }

    @Override
    public String toString() {
        StringBuilder builder = new StringBuilder();
        builder.append(String.format("Voter: %s\nRanking:", Name));

        for (Item item : Order) {
            builder.append(String.format("\t%s\n", item.toString()));
        }

        return builder.toString();
    }
}