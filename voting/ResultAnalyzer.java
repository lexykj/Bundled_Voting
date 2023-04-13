import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class ResultAnalyzer {

    public static class Result {
        private final Map<Bundle, Integer> totalUtilities;
        private final Map.Entry<Bundle, Integer> bestBundle;
        private final Map<String, Bundle> winners;

        private Result(Map<Bundle, Integer> totalUtilities, Map.Entry<Bundle, Integer> bestBundle, Map<String, Bundle> winners) {
            this.totalUtilities = totalUtilities;
            this.bestBundle = bestBundle;
            this.winners = winners;
        }

        public Map<Bundle, Integer> getTotalUtilities() {
            return totalUtilities;
        }

        public Map.Entry<Bundle, Integer> getBestBundle() {
            return bestBundle;
        }

        public Map<String, Bundle> getWinners() {
            return winners;
        }

        @Override
        public String toString() {
            StringBuilder builder = new StringBuilder();
            builder.append(String.format("The bundle with the highest total utility is:\n%s\nIts total utility is %d\n\n", bestBundle.getKey(), bestBundle.getValue()));
            builder.append("The utility of the winning bundles are as follows:\n");
            for (Map.Entry<String, Bundle> entry : winners.entrySet()) {
                if (entry.getValue() != null) {
                    builder.append(String.format("\t%s: %d\n", entry.getKey(), totalUtilities.get(entry.getValue())));
                }
            }
            return builder.toString();
        }
    }

    public static Result analyze(Map<String, Bundle> winners, ArrayList<Bundle> bundles, ArrayList<Voter> voters, VotingStrategy votingStrategy) {
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

        return new Result(totalUtility, maxEntry, winners);
    }
}
