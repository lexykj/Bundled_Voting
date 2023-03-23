public class Bundler {
    private Item[] Shows; // this could potentially be removed
    private Bundle[] Bundles;
    public Bundler(Item[] shows) {
        this.Shows = shows;
        // CREATE bundles
    }
    public Bundle[] getBundles(){
        return this.Bundles;
    }
}
