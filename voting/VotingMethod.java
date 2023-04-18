import java.util.ArrayList;

import java.util.ArrayList;
public abstract class VotingMethod implements Runnable {
    public String Name;
    public Bundle Winner;
    public ArrayList<Bundle> BestSelectedBundles = new ArrayList<>();
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
    public abstract VotingMethod cloneAndReplaceVoters(ArrayList<Voter> newVoters);

    @Override
    public String toString() {
        return Name;
    }
    public void run() {
        RunVote();
    }
}
