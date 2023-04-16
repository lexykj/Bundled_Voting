import java.util.ArrayList;
import java.util.Arrays;
import java.util.Hashtable;
import java.util.Map;

public class Pairwise extends VotingMethod {
    private final Map<String, Integer> Votes = new Hashtable<>();
//    private final Map<String, Bundle> BundlesByName = new Hashtable<>();
    private final int BundleCount;
    public Pairwise(ArrayList<Voter> voters){
        super("Pairwise");
        this.Voters = voters;
        this.BundleCount = this.Voters.get(0).getBundleCount();
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
                    if (Integer.parseInt(currentBundle.Name) < Integer.parseInt( comparisonBundle.Name)) {
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
        for(Bundle bundle : this.Voters.get(0).getBundleScore().keySet()) {
            // Every comparison that I am in
            boolean isCondorcet = true;
            for (String comparison : this.Votes.keySet()) {
                String[] comparisonSplit = comparison.split("\\.");
                if (bundle.Name.equals(comparisonSplit[0])) {
                    isCondorcet= this.Votes.get(comparison) < 0;
                }
                if (bundle.Name.equals(comparisonSplit[1])) {
                    isCondorcet= this.Votes.get(comparison) > 0;
                }
                if(!isCondorcet) break;
            }

            if(isCondorcet) return bundle;
        }

        return null;
    }
}
