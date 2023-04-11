import java.util.ArrayList;
import java.util.Map;
import java.util.HashMap;

public class Voter {
    private ArrayList<Item> Order;
    private final Map<Bundle, Integer> BundleScore = new HashMap<>();
    final String Name;
    public Voter(String name, ArrayList<Item> order) {
        this.Name = name;
        this.Order = order;
    }
    public void CalculatePreference(Bundle bundle) {
        int totalScore = 0;
        for (Item item : bundle.Bundle) {
            totalScore += Order.size() - Order.indexOf(item);
        }
        this.BundleScore.put(bundle, totalScore);
    }

    public Map<Bundle, Integer> getBundleScore() {
        return this.BundleScore;
    }

    public int getBundleCount() {
        return BundleScore.size();
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