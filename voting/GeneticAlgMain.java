import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Hashtable;
import java.util.Map;

public class GeneticAlgMain {
    // The bundles are the population that competes for voters utility
    private ArrayList<Bundle> Population;
    private int PopulationSize;
    // Voters determine bundles fitness score
    private ArrayList<Voter> Voters;
    private ResultAnalyzer.Result Result;
    // TODO take in an initial result
    public GeneticAlgMain(ArrayList<Voter> voters, ArrayList<Bundle> bundles, ResultAnalyzer.Result initial) {
        this.Population = bundles;
        this.Voters = voters;
        this.PopulationSize = this.Population.size();
        this.Result = initial;
    }

    // This creates the new "population", the new bundles keeping to K, and making new ones
    // We will use the top 2/3's which pair up to create another 1/3 bundles
    private void Reproduce() {

    }
    private void RunVoting() {
        VotingMethod borda = new Borda(Voters);
        VotingMethod copland = new Copland(Voters);
        VotingMethod pairwise = new Pairwise(Voters);

        VotingMethod[] votingMethods = {borda, pairwise, copland};
        Map<String,Bundle> winners = new Hashtable<>();
        for (VotingMethod votingMethod : votingMethods) {
            System.out.println("Running: " + votingMethod.Name);
            votingMethod.RunVote();
            if (votingMethod.Winner != null) {
                winners.put(votingMethod.toString(), votingMethod.Winner);
            }
        }
        // Need to replace with non-hard coded
        this.Result = ResultAnalyzer.analyze(winners, Population, Voters,111);
    }
}
