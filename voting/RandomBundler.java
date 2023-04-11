import java.util.ArrayList;
import java.util.Random;

public class RandomBundler extends BundlingStrategy{
    @Override
    ArrayList<Bundle> generateBundles(int bundleSize, int numberOfBundles, ArrayList<Item> items) {
        Random rnd = new Random();
        rnd.setSeed(11111111);
        bundles.clear();
        for (int bundleNumber = 0; bundleNumber < numberOfBundles; bundleNumber = bundles.size()) {
            ArrayList<Item> bundle = new ArrayList<>();
            while (bundle.size() < bundleSize) {
                int index = rnd.nextInt(items.size());
                if (!bundle.contains(items.get(index))) {
                    bundle.add(items.get(index));
                }
            }
            bundles.add(new Bundle(bundle, Integer.toString(bundleNumber)));
        }
        return new ArrayList<>(bundles);
    }
}
