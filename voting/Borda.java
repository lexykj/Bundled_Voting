import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Hashtable;

public class Borda extends VotingMethod {

    private final Map<Bundle, Integer> Votes = new Hashtable<>();
    public Borda(ArrayList<Voter> voters){
        super("Borda");
        this.Voters = voters;
    }

    @Override
    public void CalculateVotes() {
        for (Voter voter : this.Voters) {
            for (Bundle key : voter.getBundleScore().keySet()){
                int currCount = 0;
                if (this.Votes.containsKey(key)) currCount = this.Votes.get(key);
                this.Votes.put(key, voter.getBundleScore().get(key) + currCount);
//                System.out.println(this.Votes.get(key));
            }
        }
    }

    @Override
    public Bundle FindWinner() {
        this.BestSelectedBundles.clear();
        Bundle winner = null;
        int winnerVotes = 0;
        for(Bundle key : this.Votes.keySet()) {
            if (this.Votes.get(key) > winnerVotes) {
                winner = key;
                winnerVotes = this.Votes.get(key);
            }
        }
        List<Map.Entry<Bundle, Integer>> sorted = Votes.entrySet().stream().sorted((a, b) -> b.getValue().compareTo(a.getValue())).toList();
        for (Map.Entry<Bundle, Integer> item : sorted.subList(0, (int) (sorted.size() * .666) + 1)) {
            this.BestSelectedBundles.add(item.getKey());
        }
//        System.out.println(winner.toString());
//        System.out.println(winnerVotes);
        return winner;
    }

    @Override
    public VotingMethod cloneAndReplaceVoters(ArrayList<Voter> newVoters) {
        return new Borda(newVoters);
    }

}
