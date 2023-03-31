import java.io.File;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.*;

public class Main {
    public static void main(String[] args) {
        System.out.println("Ran");
        // Parse Json, get list of items and voters with their orders set
        HashSet<Item> showSet = new HashSet<>();


        //Create Voters
        // Voter[] voters = new Voter(shows)
        ArrayList<Voter> voters = new ArrayList<Voter>();

        // Using path as makes it easier to support different OSes
        Path path = Paths.get("../scraper/data");
        File file = new File(path.toUri());
        for (File f : Objects.requireNonNull(file.listFiles())) {
            ArrayList<Item> items = CSVParser.ParseCSV(f.getPath());
            for (Item item : items) {
                showSet.add(item);
            }
            voters.add(new Voter(f.getName(), items));
        }

        ArrayList<Item> shows = new ArrayList<>(showSet);

        for (Voter voter : voters) {
            System.out.println(voter.toString());
        }

        // forgot about arraylists, should change arrays into arraylists
        // since they need to be dynamic
        System.out.println("Bundling");

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