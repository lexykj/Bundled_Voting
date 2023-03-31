public class Item {
    final int id;
    final int showId;
    final int rank;
    final String name;
    final String showName;
    final String seasonName;
    final int weeksInTopTen;
    public Item(int id, int showId, int rank, String name, String showName, String seasonName, int weeksInTopTen){
        this.id = id;
        this.showId = showId;
        this.rank = rank;
        this.name = name;
        this.showName = showName;
        this.seasonName = seasonName;
        this.weeksInTopTen = weeksInTopTen;
    }

    @Override
    public String toString() {
        return String.format("id: %d, showId: %d, rank: %d, name: %s, showName: %s, seasonName: %s, weeksInTopTen: %d", this.id, this.showId, this.rank, this.name, this.showName, this.seasonName, this.weeksInTopTen);
    }

    @Override
    public int hashCode() {
        return id;
    }

    @Override
    public boolean equals(Object o) {
        if (o instanceof Item) {
            Item item = (Item) o;
            return item.id == id;
        }
        return false;
    }
}
