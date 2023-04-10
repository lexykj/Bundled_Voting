import java.util.ArrayList;
public class Bundler {
    private ArrayList<Item> Shows; // this could potentially be removed
    private final ArrayList<Bundle> Bundles;
    private final int MaxBundleCount = 10000;
    private final int BundleSize = 6;
    private final int ItemsToTryAfterMe = 10;
    private final BundlingStrategy bundlingStrategy;

    public Bundler(ArrayList<Item> shows, BundlingStrategy bundlingStrategy) {
        this.Shows = new ArrayList<>(shows);
        this.bundlingStrategy = bundlingStrategy;
        //Testing code with 3 items
//        this.Shows.clear();
//        this.Shows.add(shows.get(0));
//        this.Shows.add(shows.get(1));
//        this.Shows.add(shows.get(2));

        // CREATE bundles

        this.Bundles = bundlingStrategy.generateBundles(BundleSize, MaxBundleCount, shows);

        // Print Bundles
        for (Bundle bundle : this.Bundles) {
            System.out.println("Bundle");
            System.out.println(bundle.toString());
        }
        System.out.println("Shows: " + this.Shows.size());
        System.out.println("Bundled: "+this.Bundles.size());
    }


//    private void rcRandomWithMaxBundles(int depth, ArrayList<Item> curr, int size, int me, int MaxBundleCount) {
//        if (depth >= size) return; // Should be useless
//        if (this.Bundles.size() >= MaxBundleCount) return;
//        if (depth == size-1) { // If we are at the max bundle size add me
////            System.out.println(depth + " " + size);
//            if (!curr.contains(this.Shows.get(me))) {
//
//                curr.add(this.Shows.get(me));
//                Bundle tempBundle = new Bundle(curr, ""+getNextId());
//                if (!this.Bundles.contains(tempBundle)) {
//                    this.Bundles.add(tempBundle);
//                }
//            }
//            return;
//        }
//        if (!curr.contains(this.Shows.get(me))) { // mid way if not me add me
//            curr.add(this.Shows.get(me));
//            for(int i = 0; i < ItemsToTryAfterMe; i++) {
//                // Tossed in 10, higher should create less diversity not tested too low may not create enough bundles with me at this position.
//                // Does this matter?
//                // On if this should matter, this looks at how many items we try to bundle with after me, but since we just want x random bundles
//                // this may be able to be combined with the for loop at line 26 to just create x random bundles, we aren't worried about
//                // catching every bundle any more.
//                rcRandomWithMaxBundles(depth+1, new ArrayList<>(curr), size, rnd.nextInt(this.Shows.size()), MaxBundleCount);
//            }
//        }
//    }
//
//
//    private void rc(int depth, ArrayList<Item> curr, int size, int me) {
//        if (depth >= size) return; // Should be useless
//
//        if (depth == size-1) { // If we are at the max bundle size add me
////            System.out.println(depth + " " + size);
//            if (!curr.contains(this.Shows.get(me))) {
//
//                curr.add(this.Shows.get(me));
//                Bundle tempBundle = new Bundle(curr, ""+getNextId());
//                if (!this.Bundles.contains(tempBundle)) {
//                    this.Bundles.add(tempBundle);
//                }
//            }
//            return;
//        }
//        if (!curr.contains(this.Shows.get(me))) { // mid way if not me add me
//            curr.add(this.Shows.get(me));
//            for(int i = me; i < this.Shows.size(); i++) {
//                rc(depth+1, (ArrayList<Item>) curr.clone(), size, i);
//            }
//        }
//
//    }
//    private int CurrentId = -1;
//    private int getNextId() {
//        this.CurrentId += 1;
//        return this.CurrentId;
//    }
    public ArrayList<Bundle> getBundles(){
        return this.Bundles;
    }

}
