import java.util.*;

public class GeneticAlgMain implements Runnable{
    // The bundles are the population that competes for voters utility
    private ArrayList<Bundle> Population;
    public int PopulationSize;
    // Voters determine bundles fitness score
    private ArrayList<Voter> Voters;
    public ResultAnalyzer.Result Result;
    public List<ResultAnalyzer.Result> results = new ArrayList<>();
    private ArrayList<Bundle> ParentList;
    private int Seed;
    private Random RMD = new Random();
    private VotingMethod VotingClass;
    // TODO take in an initial result
    public GeneticAlgMain(ArrayList<Voter> voters, ArrayList<Bundle> bundles, VotingMethod method, ResultAnalyzer.Result initial, int seed) {
        this.Population = bundles;
        this.Voters = voters;
        this.PopulationSize = this.Population.size();
        this.Result = initial;
        this.Seed = seed;
        this.RMD.setSeed(this.Seed);
        this.ParentList = method.BestSelectedBundles;
        this.VotingClass = method;

    }
    public void Run(int iterations) {
//        for(int i = 0; i < iterations; i++){
        while(true) {
            int totalUtility = 0;
            for (Bundle bundle : Result.getTotalUtilities().keySet()) {
                totalUtility += Result.getTotalUtilities().get(bundle);
            }
//            System.out.println("Generation Utility Mean: " + totalUtility/PopulationSize);
//            System.out.println("Generation Winner Mean:  " + Result.getBestBundle().getValue());
//            System.out.println("Winning Bundle: \n" + Result.getBestBundle().getKey());
//            System.out.println();
            if (totalUtility/PopulationSize == Result.getBestBundle().getValue()) break;

            Reproduce();
            RunVoting();
        }
    }

    // This creates the new "population", the new bundles keeping to K, and making new ones
    // We will use the top 2/3's which pair up to create another 1/3 bundles
    private void Reproduce() {
        int k = (int) (PopulationSize * .666);
        ArrayList<Bundle> newPopulation = new ArrayList<>(this.ParentList);
        ArrayList<Integer> options = new ArrayList<>();
        for(int i = 0; i <= k; i++) {
            options.add(i);
        }
        // if we get the biggest name then anything after is unique
        int biggestName = 0;
        for (Bundle bundle : this.Population) {
            if(Integer.parseInt( bundle.Name) > biggestName) biggestName = Integer.parseInt( bundle.Name);
        }
        while (newPopulation.size() < PopulationSize) {
            if (this.ParentList.size() < 2) break;
//        for(int i = 0; i < k/2; i++) {
            // get one parent, remove it

            Bundle parent1 = this.ParentList.get(RMD.nextInt(this.ParentList.size()));
            this.ParentList.remove(parent1);

            Bundle parent2 = this.ParentList.get(RMD.nextInt(this.ParentList.size()));
            this.ParentList.remove(parent2);

            int nextName = biggestName +1;
            newPopulation.add(combineParents(parent1, parent2, "" + nextName));
            biggestName = nextName;
        }
        this.Population = newPopulation;
    }
    private Bundle combineParents(Bundle parent1, Bundle parent2, String newName) {
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
        return new Bundle(childBundle, newName);
    }

    private void RunVoting() {
        // This is the issue
        for (Voter voter : Voters) {
            voter.clearBundleScores();
        }
        for (Bundle bundle : this.Population) {
            for (Voter voter : Voters) {
                voter.CalculatePreference(bundle);
            }
        }
//        Map<String,Bundle> winners = MultiThreadedVoting.Run(Voters);
        // create new vote of type Method pass new voters, get back list.
        this.VotingClass = this.VotingClass.cloneAndReplaceVoters(Voters);
        this.VotingClass.RunVote();
        this.ParentList = this.VotingClass.BestSelectedBundles;

        // Need to replace with non-hard coded
        this.Result = ResultAnalyzer.analyze(new HashMap<>(), Population, Voters,this.Seed);
        results.add(Result);
    }
    public void run() {
        this.Run(20);
    }
}
