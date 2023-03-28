import java.io.File;
import java.io.FileNotFoundException;
import java.net.URI;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Dictionary;
import java.util.Hashtable;
import java.util.Objects;

public class Main {
    public static void main(String[] args) {
        System.out.println("Ran");
        // Parse Json, get list of items and voters with their orders set
        Item[] shows = new Item[]{};

        //Create Voters
        // Voter[] voters = new Voter(shows)
        ArrayList<Voter> voters = new ArrayList<Voter>();

        // Using path as makes it easier to support different OSes
        Path path = Paths.get("../scraper/data");
        File file = new File(path.toUri());
        for (File f : Objects.requireNonNull(file.listFiles())) {
             voters.add(new Voter(f.getName(), CSVParser.ParseCSV(f.getPath())));
        }

        for (Voter voter : voters) {
            System.out.println(voter.toString());
        }

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