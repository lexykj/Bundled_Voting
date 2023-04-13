import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
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
        Reproduce();
    }
    // This will return a result class
    private ResultAnalyzer.Result CalculateFitness() {
        return null;
    }
    // This creates the new "population", the new bundles keeping to K, and making new ones
    // We will use the top 2/3's which pair up to create another 1/3 bundles
    private void Reproduce() {
        int k = (int) (PopulationSize * .666);
        List<Map.Entry<Bundle, Integer>> sorted = Result.getTotalUtilities().entrySet().stream().sorted((a,b) -> b.getValue().compareTo(a.getValue())).toList();
//        System.out.println(sorted);
        ArrayList<Bundle> newPopulation = new ArrayList<>();
        // Keep best 2/3rds
        for(int i = 0; i < k; i++) {
            newPopulation.add(sorted.get(i).getKey());
        }
        ArrayList<Integer> parents = new ArrayList<>(k);
        System.out.println(parents);
        for(int i = 0; i < PopulationSize-k; i++) {

        }
    }
    private void RunVoting() {

    }
}
