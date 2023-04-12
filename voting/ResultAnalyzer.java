import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class ResultAnalyzer {

    public static void analyze(Map<String, Bundle> winners, ArrayList<Bundle> bundles, ArrayList<Voter> voters, VotingStrategy votingStrategy) {
        HashMap<Bundle, Integer> totalUtility = new HashMap<>();
        for (Voter voter : voters) {
            for (Bundle bundle : bundles) {
                if (!totalUtility.containsKey(bundle)) {
                    totalUtility.put(bundle, 0);
                }
                totalUtility.put(bundle, totalUtility.get(bundle) + voter.getBundleScore().get(bundle));
            }
        }
        Map.Entry<Bundle, Integer> maxEntry = null;
        for (Map.Entry<Bundle, Integer> entry: totalUtility.entrySet()) {
            if (maxEntry == null) {
                maxEntry = entry;
            }
            if (maxEntry.getValue() < entry.getValue()) {
                maxEntry = entry;
            }
        }
        System.out.printf("The bundle with the highest total utility is:\n%s\nIt's total utility is %d\n\n", maxEntry.getKey(), maxEntry.getValue());
        System.out.printf("The utility of the winning bundles is as follows:\n");
        for (Map.Entry<String, Bundle> entry : winners.entrySet()) {
            System.out.printf("\t%s: %d\n", entry.getKey(), totalUtility.get(entry.getValue()));
        }
    }
}
