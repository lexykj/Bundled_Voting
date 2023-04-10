import java.util.ArrayList;
import java.util.Hashtable;
import java.util.Map;

public class Pairwise extends VotingMethod {
    private final Map<String, Integer> Votes = new Hashtable<>();
    public Pairwise(ArrayList<Voter> voters){
        super("pairwise");
        this.Voters = voters;
    }

    @Override
    public void CalculateVotes() {
        for (Voter voter : this.Voters) {
            for (Bundle mainKey : voter.getBundleScore().keySet()){
                for (Bundle key : voter.getBundleScore().keySet()){
                    if (mainKey == key) continue;
                    int isBetter = 0;
                    if (voter.getBundleScore().get(key) == voter.getBundleScore().get(mainKey)) {
                        continue;
                    } else if (voter.getBundleScore().get(key) < voter.getBundleScore().get(mainKey)) {
                        isBetter = 1;
                    } else {
                        isBetter = 0;
                    }
                    String comparisonId;
                    // Make sure lower key is first so comparisons always have same key
                    if (Integer.parseInt(key.Name) < Integer.parseInt( mainKey.Name)) {
                        comparisonId = key.Name + "." + mainKey.Name;
                    } else {
                        comparisonId = mainKey.Name + "." + key.Name;
                    }
                    int currCount = 0;
                    if (this.Votes.containsKey(comparisonId)) currCount = this.Votes.get(comparisonId);
                    this.Votes.put(comparisonId, currCount + isBetter);
                }
            }
        }
    }

    @Override
    public Bundle FindWinner() {
        for (String key : this.Votes.keySet()) {
            key.split(".");
        }
        //        if(condorcet) return //best/condorcet
        return null;
    }
}
