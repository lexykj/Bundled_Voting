import java.util.ArrayList;
public class Bundler {
    private ArrayList<Item> Shows; // this could potentially be removed
    private final ArrayList<Bundle> Bundles;
    private final int MaxBundleCount = 100;
    private final int BundleSize = 6;
    private final int ItemsToTryAfterMe = 10;
    private final BundlingStrategy bundlingStrategy;

    public Bundler(ArrayList<Item> shows, BundlingStrategy bundlingStrategy) {
        this.Shows = new ArrayList<>(shows);
        this.bundlingStrategy = bundlingStrategy;

        // CREATE bundles
        this.Bundles = bundlingStrategy.generateBundles(BundleSize, MaxBundleCount, Shows);

        // Print Bundles
        for (Bundle bundle : this.Bundles) {
            System.out.println("Bundle Number: " + bundle.Name);
//            System.out.println("Bundle");
//            System.out.println(bundle.toString());
        }
        System.out.println("Shows: " + this.Shows.size());
        System.out.println("Bundled: "+this.Bundles.size());
    }

    public ArrayList<Bundle> getBundles(){
        return this.Bundles;
    }

}
