import java.util.ArrayList;
import java.util.Hashtable;
import java.util.Map;

public class MultiThreadedVoting {
    public static Map<String,Bundle> Run(ArrayList<Voter> Voters) {
        VotingMethod borda = new Borda(Voters);
        VotingMethod copland = new Copland(Voters);
        VotingMethod pairwise = new Pairwise(Voters);

        VotingMethod[] votingMethods = {borda, pairwise, copland};
        Thread[] votingThreads = new Thread[3];
        for(int i = 0; i < votingMethods.length; i++) {
            votingThreads[i] = new Thread(votingMethods[i]);
            votingThreads[i].start();
        }

        Map<String,Bundle> winners = new Hashtable<>();
        for(int i = 0; i < votingMethods.length; i++) {
            try {
                votingThreads[i].join();
                if (votingMethods[i].Winner != null) {
                    winners.put(votingMethods[i].toString(), votingMethods[i].Winner);
                }
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        return winners;
    }
}
