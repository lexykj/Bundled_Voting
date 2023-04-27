import java.util.Random;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class BatchRunner {
    private static final int NUMBER_OF_CORES_ON_SYSTEM = 8;
    private static final int NUMBER_OF_TRIALS = 1000;

    public static void main(String[] args) {
        Random random = new Random();
        ExecutorService pool = Executors.newFixedThreadPool(NUMBER_OF_CORES_ON_SYSTEM);
        for (int i = 0; i < NUMBER_OF_TRIALS; i++) {
            pool.execute(new Main(new String[] {"borda", Integer.toString(random.nextInt(999999))}));
        }
        for (int i = 0; i < NUMBER_OF_TRIALS; i++) {
            pool.execute(new Main(new String[] {"copland", Integer.toString(random.nextInt(999999))}));
        }
        pool.close();
    }
}
