public abstract class VotingMethod {
    public Bundle Winner;
    private Voter[] Voters;
    public Bundle RunVote(Voter[] voters) {
        this.Voters = voters;
        CalculateVotes();
        return FindWinner();
    }
    public abstract void CalculateVotes();
    public abstract Bundle FindWinner();
}
