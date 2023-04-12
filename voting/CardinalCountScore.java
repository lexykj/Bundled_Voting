import java.util.ArrayList;

public class CardinalCountScore implements VotingStrategy {
    @Override
    public int computeUtility(Bundle bundle, ArrayList<Item> ordering) {
        int totalScore = 0;
        for (Item item : bundle.Bundle) {
            // indexOf returns -1, if the item is not in the array
            // Need to not run code to avoid rewarding items not in their order
            if (ordering.contains(item)) {
                totalScore += ordering.size() - ordering.indexOf(item);
            }
        }
        return totalScore;
    }
}
