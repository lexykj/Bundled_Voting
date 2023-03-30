import java.util.ArrayList;

public class Bundler {
    private ArrayList<Item> Shows; // this could potentially be removed
    private final ArrayList<Bundle> Bundles = new ArrayList<>();
    public Bundler(ArrayList<Item> shows) {
        this.Shows = shows;
        // CREATE bundles
    }
    public ArrayList<Bundle> getBundles(){
        return this.Bundles;
    }
}
