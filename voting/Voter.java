import java.util.ArrayList;
import java.util.Map;
import java.util.HashMap;

public class Voter {
    private ArrayList<Item> Order;
    private final Map<Bundle, Integer> BundleScore = new HashMap<>();
    final String Name;

    private final VotingStrategy votingStrategy;
    public Voter(String name, ArrayList<Item> order, VotingStrategy votingStrategy) {
        this.Name = name;
        this.Order = order;
        this.votingStrategy = votingStrategy;
    }
    public void CalculatePreference(Bundle bundle) {
        this.BundleScore.put(bundle, votingStrategy.computeUtility(bundle, Order));
    }

    public Map<Bundle, Integer> getBundleScore() {
        return this.BundleScore;
    }

    public int getBundleCount() {
        return BundleScore.size();
    }

    public ArrayList<Bundle> getBundles () { return new ArrayList<Bundle>(this.BundleScore.keySet()); }

    public void clearBundleScores() {
        this.BundleScore.clear();
    }

    @Override
    public String toString() {
        StringBuilder builder = new StringBuilder();
        builder.append(String.format("Voter: %s\nRanking:\n", Name));

        for (Item item : Order) {
            builder.append(String.format("\t%s\n", item.toString()));
        }

        return builder.toString();
    }
}