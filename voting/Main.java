import java.util.ArrayList;
import java.util.Dictionary;
import java.util.Hashtable;

public class Main {
    public static void main(String[] args) {
        System.out.println("Ran");
        // Parse Json, get list of items and voters with their orders set
        Item[] shows = JsonParser.ParseJson("..\\scraper\\data.json");

        //Create Voters
        // Voter[] voters = new Voter(shows)
        ArrayList<Voter> voters = new ArrayList<Voter>();
        // forgot about arraylists, should change arrays into arraylists
        // since they need to be dynamic

        // Bundler creates the bundles
        Bundler bundler = new Bundler(shows);

        // For each bundle, each voter rates each bundle
        for (Bundle bundle : bundler.getBundles()) {
            for (Voter voter : voters) {
                voter.CalculatePrefrence(bundle);
            }
        }

        // Call a voting method passing it the list of voters
        VotingMethod borda = new Borda(voters);
        VotingMethod chaplain = new Chaplin(voters);
        VotingMethod pairwise = new Pairwise(voters);
        VotingMethod bucklin = new Bucklin(voters);

        VotingMethod[] votingMethods = {borda, chaplain, pairwise, bucklin};
        Dictionary<String,Bundle> winners = new Hashtable<>();
        for (VotingMethod votingMethod : votingMethods) {
            votingMethod.RunVote();
            winners.put(votingMethod.toString(), votingMethod.Winner);
        }


        System.out.println("Completed Simulation");
    }
}