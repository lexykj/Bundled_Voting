import java.util.ArrayList;
import java.util.Hashtable;
import java.util.Map;

public class MultiThreadedVoting {
    public static Map<String,Bundle> Run(ArrayList<Voter> Voters) {
        VotingMethod borda = new Borda(new ArrayList<>(Voters));
        VotingMethod copeland = new Copeland(new ArrayList<>(Voters));
        VotingMethod pairwise = new Pairwise(new ArrayList<>(Voters));

        VotingMethod[] votingMethods = {borda, pairwise, copeland};
        Thread[] votingThreads = new Thread[3];
        for(int i = 0; i < votingMethods.length; i++) {
            System.out.println(votingMethods[i]);
            votingThreads[i] = new Thread(votingMethods[i]);
            votingThreads[i].start();
//            votingThreads[i].run();
        }

        Map<String,Bundle> winners = new Hashtable<>();
        for(int i = 0; i < votingMethods.length; i++) {
            try {
                votingThreads[i].join();
//                System.out.println(votingMethods[i].toString());
//                System.out.println(votingMethods[i].Winner);
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
