import java.util.ArrayList;

import java.util.ArrayList;
public abstract class VotingMethod {
    public Bundle Winner;
    protected ArrayList<Voter> Voters = new ArrayList<Voter>();
    public Bundle RunVote() {
        CalculateVotes();
        return FindWinner();
    }
    public abstract void CalculateVotes();
    public abstract Bundle FindWinner();
}
