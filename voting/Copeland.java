import java.util.*;

public class Copeland extends VotingMethod {
    private final Map<String, Integer> Votes = new Hashtable<>();
    private final Map<String, Bundle> BundlesByName = new Hashtable<>();
    public Copeland(ArrayList<Voter> voters){
        super("Copeland");
        this.Voters = voters;
    }

    @Override
    public void CalculateVotes() {
        for (Voter voter : this.Voters) {
            for (Bundle currentBundle : voter.getBundleScore().keySet()){
//                if (!BundlesByName.containsKey(currentBundle)) BundlesByName.put(currentBundle.Name, currentBundle);
                for (Bundle comparisonBundle : voter.getBundleScore().keySet()){
                    // if the bundles are the same then skip i.e. comparing me to me
                    if (currentBundle == comparisonBundle) continue;
                    int isBetter = 0;
                    // if its a tie skip
                    if (voter.getBundleScore().get(comparisonBundle).equals(voter.getBundleScore().get(currentBundle))) {
                        continue;
                    }


                    String comparisonId;
                    boolean currentIsLower = false;
                    // Make sure lower key is first so comparisons always have same key
                    if (currentBundle.Name.compareTo(comparisonBundle.Name) < 0) {
                        comparisonId = currentBundle.Name + "." + comparisonBundle.Name;
                        currentIsLower = true;
                    } else {
                        continue;
//                        comparisonId = comparisonBundle.Name + "." + currentBundle.Name;
//                        currentIsLower = false;
                    }
                    boolean valueIsLower = voter.getBundleScore().get(currentBundle) < voter.getBundleScore().get(comparisonBundle);
                    if (currentIsLower == valueIsLower) {
                        isBetter = 1;
                    } else {
                        isBetter = -1;
                    }

                    int currCount = 0;
                    if (this.Votes.containsKey(comparisonId)) currCount = this.Votes.get(comparisonId);
                    this.Votes.put(comparisonId, currCount + isBetter);
                }
            }
        }

//        System.out.println("Comps made: " +this.Votes.size());
//        System.out.println("Voter Count: " + this.Voters.size());
//        System.out.println(this.BundleCount);
    }

    @Override
    public Bundle FindWinner() {
        Map<Bundle, Integer> copelandSums = new HashMap<>();
        for(Bundle bundle : this.Voters.get(0).getBundleScore().keySet()) {
            // Every comparison that I am in
            int winSum = 0;
            for (String comparison : this.Votes.keySet()) {
                String[] comparisonSplit = comparison.split("\\.");
                if (bundle.Name.equals(comparisonSplit[0])) {
                    // we are lower
                    if (this.Votes.get(comparison) < 0) {
                        // we are better
                        winSum += 1;
                    } else if (this.Votes.get(comparison) == 0) {
                        // we tie
                        winSum += .5;
                    }
                    // if we are worse add 0, so dont need to check
                }
                if (bundle.Name.equals(comparisonSplit[1])) {
                    // we are higher
                    if (this.Votes.get(comparison) > 0) {
                        // we are better
                        winSum += 1;
                    } else if (this.Votes.get(comparison) == 0) {
                        // we tie
                        winSum += .5;
                    }
                }
            }

            copelandSums.put(bundle, winSum);
        }
        int max = 0;
        Bundle maxBundle = null;
        for(Bundle bundle : copelandSums.keySet()) {
            if(Math.abs(copelandSums.get(bundle)) > max) {
                max = Math.abs(copelandSums.get(bundle));
                maxBundle = bundle;
            }
        }
        List<Map.Entry<Bundle, Integer>> sorted = copelandSums.entrySet().stream().sorted((a, b) -> b.getValue().compareTo(a.getValue())).toList();
        for (Map.Entry<Bundle, Integer> item : sorted.subList(0, (int) (sorted.size() * .666) + 1)) {
            this.BestSelectedBundles.add(item.getKey());
        }
        return maxBundle;
    }

    @Override
    public VotingMethod cloneAndReplaceVoters(ArrayList<Voter> newVoters) {
        return new Copeland(newVoters);
    }
}
