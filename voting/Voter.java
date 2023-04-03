import java.util.ArrayList;
import java.util.Map;
import java.util.HashMap;
import java.util.Hashtable;

public class Voter {
    private ArrayList<Item> Order;
    private final Map<Bundle, Integer> Preferences = new HashMap<>();
    final String Name;
    public Voter(String name, ArrayList<Item> order) {
        this.Name = name;
        this.Order = order;
    }
    public void CalculatePreference(Bundle bundle) {
        this.Preferences.put(bundle, Order.size() - Order.indexOf(bundle));
    }

    public Map<Bundle, Integer> getPreferences() {
        return this.Preferences;
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