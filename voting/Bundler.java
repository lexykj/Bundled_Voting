import java.util.ArrayList;

public class Bundler {
    private ArrayList<Item> Shows; // this could potentially be removed
    private final ArrayList<Bundle> Bundles = new ArrayList<>();
    public Bundler(ArrayList<Item> shows) {
        this.Shows = (ArrayList<Item>) shows.clone();
        //Testing code with 3 items
//        this.Shows.clear();
//        this.Shows.add(shows.get(0));
//        this.Shows.add(shows.get(1));
//        this.Shows.add(shows.get(2));

        // CREATE bundles
        for (int i = 0; i < this.Shows.size(); i++){
            rc(0, new ArrayList<Item>(), 6,i);
        }

        for (Bundle bundle : this.Bundles) {
            System.out.println("Bundle");
            System.out.println(bundle.toString());
        }
        System.out.println("Shows: " + this.Shows.size());
        System.out.println("Bundled: "+this.Bundles.size());
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
