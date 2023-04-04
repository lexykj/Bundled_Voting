import java.util.ArrayList;
import java.util.Random;

public class Bundler {
    private ArrayList<Item> Shows; // this could potentially be removed
    private final ArrayList<Bundle> Bundles = new ArrayList<>();
    private Random rnd = new Random();
    public Bundler(ArrayList<Item> shows) {
        this.Shows = (ArrayList<Item>) shows.clone();
        //Testing code with 3 items
//        this.Shows.clear();
//        this.Shows.add(shows.get(0));
//        this.Shows.add(shows.get(1));
//        this.Shows.add(shows.get(2));

        rnd.setSeed(11111111);

        // CREATE bundles
        for (int i = 0; i < this.Shows.size(); i++){
//            rc(0, new ArrayList<Item>(), 4,i);
            rcRandomWithMaxBundles(0, new ArrayList<Item>(), 4,i, 1000);
        }

        for (Bundle bundle : this.Bundles) {
            System.out.println("Bundle");
            System.out.println(bundle.toString());
        }
        System.out.println("Shows: " + this.Shows.size());
        System.out.println("Bundled: "+this.Bundles.size());
    }


    private void rcRandomWithMaxBundles(int depth, ArrayList<Item> curr, int size, int me, int MaxBundlesSize) {
        if (depth >= size) return; // Should be useless
        if (this.Bundles.size() >= MaxBundlesSize) return;
        if (depth == size-1) { // If we are at the max bundle size add me
//            System.out.println(depth + " " + size);
            if (!curr.contains(this.Shows.get(me))) {

                curr.add(this.Shows.get(me));
                Bundle tempBundle = new Bundle(curr, ""+getNextId());
                if (!this.Bundles.contains(tempBundle)) {
                    this.Bundles.add(tempBundle);
                }
            }
            return;
        }
        if (!curr.contains(this.Shows.get(me))) { // mid way if not me add me
            curr.add(this.Shows.get(me));
            for(int i = me; i < this.Shows.size(); i++) {
                rcRandomWithMaxBundles(depth+1, new ArrayList<>(curr), size, rnd.nextInt(this.Shows.size()), MaxBundlesSize);
            }
        }
    }


    private void rc(int depth, ArrayList<Item> curr, int size, int me) {
        if (depth >= size) return; // Should be useless

        if (depth == size-1) { // If we are at the max bundle size add me
//            System.out.println(depth + " " + size);
            if (!curr.contains(this.Shows.get(me))) {

                curr.add(this.Shows.get(me));
                Bundle tempBundle = new Bundle(curr, ""+getNextId());
                if (!this.Bundles.contains(tempBundle)) {
                    this.Bundles.add(tempBundle);
                }
            }
            return;
        }
        if (!curr.contains(this.Shows.get(me))) { // mid way if not me add me
            curr.add(this.Shows.get(me));
            for(int i = me; i < this.Shows.size(); i++) {
                rc(depth+1, (ArrayList<Item>) curr.clone(), size, i);
            }
        }

    }
    private int CurrentId = -1;
    private int getNextId() {
        this.CurrentId += 1;
        return this.CurrentId;
    }
    public ArrayList<Bundle> getBundles(){
        return this.Bundles;
    }

}
