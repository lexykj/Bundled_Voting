import java.util.*;

public class ResultAnalyzer {

    public static class Result {
        private final Map<Bundle, Integer> totalUtilities;
        private final Map.Entry<Bundle, Integer> bestBundle;
        private final Map<String, Bundle> winners;

        private final int seed;

        private Result(Map<Bundle, Integer> totalUtilities, Map.Entry<Bundle, Integer> bestBundle, Map<String, Bundle> winners, int seed) {
            this.totalUtilities = totalUtilities;
            this.bestBundle = bestBundle;
            this.winners = winners;
            this.seed = seed;
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

        static public String getCSVHeader() {
            return "seed, best bundle id, best bundle utility, borda winner, borda winner utility, copland winner, copland winner id, copland utility, pairwise winner, pairwise utility";
        }

        /**
         * Outputs CSV row with following format
         * `seed, best bundle id, best bundle total utility, borda winner id, borda winner total utility, copland winner id, copland winner total utility, pairwise winner id, pairwise winner total utility, `
         * @return
         */
        public String toCSVRow() {
            StringBuilder builder = new StringBuilder();
            builder.append(seed);
            builder.append(",");
            builder.append(bestBundle.getKey().Name);
            builder.append(",");
            builder.append(bestBundle.getValue());
            String[] votingMethodNames = {"Borda", "Copland", "Pairwise"};
            for (String votingMethodName : votingMethodNames) {
                if (winners.get(votingMethodName) != null) {
                    builder.append(",");
                    builder.append(winners.get(votingMethodName).Name);
                    builder.append(",");
                    builder.append(totalUtilities.get(winners.get(votingMethodName)));
                } else {
                    builder.append(",,");
                }
            }

            return builder.toString();
        }
    }

    public static Result analyze(Map<String, Bundle> winners, ArrayList<Bundle> bundles, ArrayList<Voter> voters, int seed) {
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

        return new Result(totalUtility, maxEntry, winners, seed);
    }
}
