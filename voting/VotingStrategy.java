import java.util.ArrayList;

public interface VotingStrategy {
     int computeUtility(Bundle bundle, ArrayList<Item> ordering);
}
