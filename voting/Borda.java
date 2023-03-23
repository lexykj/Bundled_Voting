import java.util.ArrayList;

public class Borda extends VotingMethod {

    public Borda(ArrayList<Voter> voters){
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
