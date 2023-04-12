import java.util.ArrayList;

public class Bucklin extends VotingMethod {
    public Bucklin(ArrayList<Voter> voters){
        super("Bucklin");
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
