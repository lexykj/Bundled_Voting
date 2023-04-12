import java.util.ArrayList;

public class Bundle {
    public final ArrayList<Item> Bundle;
    public final String Name;
    public Bundle(ArrayList<Item> bundle, String name) {
        this.Bundle = bundle;
        Name = name;
    }

    @Override
    public String toString() {
        StringBuilder builder = new StringBuilder();
        builder.append(String.format("Bundle: %s\nShows:\n", Name));

        for (Item item : Bundle) {
            builder.append(String.format("\t%s\n", item.toString()));
        }

        return builder.toString();
    }

    @Override
    public int hashCode() {
        // This is not functioning currently
//        int hash = 0;
//        for(Item show : this.Bundle) {
//            hash += show.hashCode();
//        }
        return Name.hashCode();
    }

    @Override
    public boolean equals(Object o) {
        if (o instanceof Bundle) {
            Bundle item = (Bundle) o;
            if (item.Bundle.size() != this.Bundle.size()) return false;
            for(Item show : item.Bundle) {
                if (!this.Bundle.contains(show)) {
                    return false;
                }
            }

            return true; // All items are shared
        }
        return false;
    }
}
