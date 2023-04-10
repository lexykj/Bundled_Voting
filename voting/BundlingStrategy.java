import java.util.ArrayList;
import java.util.HashSet;

public abstract class BundlingStrategy {

    protected final HashSet<Bundle> bundles = new HashSet<>();

    abstract ArrayList<Bundle> generateBundles(int bundleSize, int numberOfBundles, ArrayList<Item> items);
}
