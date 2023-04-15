import java.io.File;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.*;
import java.util.regex.*;

public class Main {
    public static void main(String[] args) {
        System.out.println("Ran");
        // Parse Json, get list of items and voters with their orders set
        HashSet<Item> showSet = new HashSet<>();


        //Create Voters
        // Voter[] voters = new Voter(shows)
        ArrayList<Voter> voters = new ArrayList<>();

        System.out.println("\nCreating Voters\n----------");


        // Using path as makes it easier to support different OSes
        Path path = Paths.get("../scraper/data");
        File file = new File(path.toUri());
        Pattern pattern = Pattern.compile("(-?)([a-zA-z])([a-zA-z]*)(\\.csv|)");

        for (File f : Objects.requireNonNull(file.listFiles())) {
            ArrayList<Item> items = CSVParser.ParseCSV(f.getPath());
//            // TODO: REMOVE THIS LINE IT IS TEST CODE
//            items = (ArrayList<Item>) items.subList(0,5);

            for (Item item : items) {
                showSet.add(item);
            }
            String titleCaseName = pattern.matcher(f.getName()).replaceAll(matchResult -> {
                    if (matchResult.group(1).equals("-")) {
                        return " " + matchResult.group(2).toUpperCase() + matchResult.group(3);
                    }
                    return matchResult.group(2).toUpperCase() + matchResult.group(3);
            });
            voters.add(new Voter(titleCaseName, items, new CardinalCountScore()));
        }

        ArrayList<Item> shows = new ArrayList<>(showSet);
        for (Voter voter : voters) {
            System.out.println(voter.toString());
        }

        // forgot about arraylists, should change arrays into arraylists
        // since they need to be dynamic
        System.out.println("\nCreating Bundles\n----------");

        // Bundler creates the bundles
        int seed = 111111;
        Bundler bundler = new Bundler(shows, new RandomBundler(seed));
        // For each bundle, each voter rates each bundle
        for (Bundle bundle : bundler.getBundles()) {
            for (Voter voter : voters) {
                voter.CalculatePreference(bundle);
            }
        }

        System.out.println("\nVoting\n----------");
        // Call a voting method passing it the list of voters
        VotingMethod borda = new Borda(voters);
        VotingMethod copland = new Copland(voters);
        VotingMethod pairwise = new Pairwise(voters);
        VotingMethod bucklin = new Bucklin(voters);
//, copland, bucklin
        VotingMethod[] votingMethods = {borda, pairwise, copland};
        Map<String,Bundle> winners = new Hashtable<>();
        for (VotingMethod votingMethod : votingMethods) {
            System.out.println("Running: " + votingMethod.Name);
            votingMethod.RunVote();
            if (votingMethod.Winner != null) {
                winners.put(votingMethod.toString(), votingMethod.Winner);
            } else {
                System.out.println("No winner found for: " + votingMethod.Name);
            }
        }

        System.out.println("\nWinners\n----------");
        for (String key : winners.keySet()) {
            System.out.println("Winner for: " + key);
            System.out.println(winners.get(key));
        }

        System.out.println(ResultAnalyzer.Result.getCSVHeader());
        System.out.println(ResultAnalyzer.analyze(winners, bundler.getBundles(), voters, seed).toCSVRow());
        System.out.println("Completed Simulation");
// Had to remove the new Cardinal count, using 111 as hard coded seed
        GeneticAlgMain gaMain = new GeneticAlgMain(voters, bundler.getBundles(), ResultAnalyzer.analyze(winners, bundler.getBundles(), voters, 111));

        GeneticAlgMain gaMain = new GeneticAlgMain(voters, bundler.getBundles(), ResultAnalyzer.analyze(winners, bundler.getBundles(), voters, seed));
        gaMain.Run();
    }
}