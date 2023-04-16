import java.lang.reflect.Array;
import java.util.*;

public class GeneticAlgMain {
    // The bundles are the population that competes for voters utility
    private ArrayList<Bundle> Population;
    private int PopulationSize;
    // Voters determine bundles fitness score
    private ArrayList<Voter> Voters;
    private ResultAnalyzer.Result Result;
    private Random RMD = new Random();
    // TODO take in an initial result
    public GeneticAlgMain(ArrayList<Voter> voters, ArrayList<Bundle> bundles, ResultAnalyzer.Result initial) {
        this.Population = bundles;
        this.Voters = voters;
        this.PopulationSize = this.Population.size();
        this.Result = initial;

    }
    public void Run(int iterations) {
        for(int i = 0; i < iterations; i++){
            int totalUtility = 0;
            for (Bundle bundle : Result.getTotalUtilities().keySet()) {
                totalUtility += Result.getTotalUtilities().get(bundle);
            }
            System.out.println("Average Utility: " + (totalUtility/this.PopulationSize));
            System.out.println("Best Utility: " + Result.getBestBundle().getValue());
            System.out.println(Result.getBestBundle().getKey().toString());
            Reproduce();
            RunVoting();
        }
    }

    // This creates the new "population", the new bundles keeping to K, and making new ones
    // We will use the top 2/3's which pair up to create another 1/3 bundles
    private void Reproduce() {
        int k = (int) (PopulationSize * .666);
        List<Map.Entry<Bundle, Integer>> sorted = Result.getTotalUtilities().entrySet().stream().sorted((a,b) -> b.getValue().compareTo(a.getValue())).toList();
//        System.out.println(sorted);
        ArrayList<Bundle> newPopulation = new ArrayList<>();
        // Keep best 2/3rds
        for(int i = 0; i <= k; i++) {
            newPopulation.add(sorted.get(i).getKey());
        }
        // Cut out last one third
//        sorted = sorted.subList(0, k);
        // TODO this needs to not be hard coded, match line in run vote
        Random rnd = new Random();
        rnd.setSeed(111);
        ArrayList<Integer> options = new ArrayList<>();
        for(int i = 0; i <= k; i++) {
            options.add(i);
        }
        while (newPopulation.size() < PopulationSize) {
//        for(int i = 0; i < k/2; i++) {
            // get one parent, remove it
            int parentIndex = options.get(rnd.nextInt(options.size()));
            Bundle parent1 = sorted.get(parentIndex).getKey();
            options.remove((Integer) parentIndex);

            parentIndex = options.get(rnd.nextInt(options.size()));
            Bundle parent2 = sorted.get(parentIndex).getKey();
            options.remove((Integer) parentIndex);

            newPopulation.add(combineParents(parent1, parent2));
        }
        this.Population = newPopulation;

    }
    private Bundle combineParents(Bundle parent1, Bundle parent2) {
        // Get union list
        ArrayList<Item> unionedShows = new ArrayList<>(parent1.Bundle);
        for (Item show : parent2.Bundle) {
            if (!unionedShows.contains(show)) unionedShows.add(show);
        }
        ArrayList<Item> childBundle = new ArrayList<>();
        while (childBundle.size() < parent1.Bundle.size()) {
            // Get a random index and try to add it
            int indexToTry = this.RMD.nextInt(unionedShows.size());
            if (!childBundle.contains(unionedShows.get(indexToTry))) {
                childBundle.add(unionedShows.get(indexToTry));
            }
        }
        // TODO This needs to be a better name, but I can't remember how we decided to do that
        return new Bundle(childBundle, "0");
    }

    private void RunVoting() {
        for (Voter voter : Voters) {
            voter.clearBundleScores();
        }
        for (Bundle bundle : this.Population) {
            for (Voter voter : Voters) {
                voter.CalculatePreference(bundle);
            }
        }

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
