package dominio;

public class Conjunto<T> {
    private Conjunto<T> pai;
    private int rank;
    private final T item;

    public Conjunto(T item) {
        pai = this;
        rank = 0;
        this.item = item;
    }

    private int getRank() {
        return rank;
    }

    private void setRank(int rank) {
        this.rank = rank;
    }

    public T getItem() {
        return item;
    }

    public void union(Conjunto<T> conjunto) {
        if (areMerged(conjunto)) {
            return;
        }

        this.find().link(conjunto.find());
    }

    private void link(Conjunto<T> conjunto) {
        if (rank > conjunto.rank) {
            conjunto.pai = this;
            return;
        }

        pai = conjunto;
        if (rank == conjunto.rank) {
            conjunto.setRank(conjunto.getRank() + 1);
        }
    }

    public Conjunto<T> find() {
        Conjunto<T> conjunto = this;
        while (conjunto.pai != conjunto) {
            conjunto = conjunto.pai;
        }
        return conjunto;
    }

    public boolean areMerged(Conjunto<T> conjunto) {
        return this.find() == conjunto.find();
    }
}
