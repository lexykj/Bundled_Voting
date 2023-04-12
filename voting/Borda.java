import java.util.ArrayList;
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
        Bundle winner = null;
        int winnerVotes = 0;
        for(Bundle key : this.Votes.keySet()) {
            if (this.Votes.get(key) > winnerVotes) {
                winner = key;
                winnerVotes = this.Votes.get(key);
            }
        }
//        System.out.println(winner.toString());
//        System.out.println(winnerVotes);
        return winner;
    }
    
}
