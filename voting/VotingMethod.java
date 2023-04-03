import java.util.ArrayList;

import java.util.ArrayList;
public abstract class VotingMethod {
    private String Name;
    public Bundle Winner;
    protected ArrayList<Voter> Voters = new ArrayList<Voter>();

    public VotingMethod(String name) {
        this.Name = name;
    }

    public Bundle RunVote() {
        CalculateVotes();
        this.Winner =  FindWinner();
        return this.Winner;
    }
    public abstract void CalculateVotes();
    public abstract Bundle FindWinner();

    @Override
    public String toString() {
        return Name;
    }
}
