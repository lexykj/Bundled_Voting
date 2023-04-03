import java.util.ArrayList;

public class Chaplin extends VotingMethod {
    public Chaplin(ArrayList<Voter> voters){
        super("chaplin");
        this.Voters = voters;
    }

    @Override
    public void CalculateVotes() {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'CalculateVotes'");
    }

    @Override
    public Bundle FindWinner() {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'FindWinner'");
    }
}
