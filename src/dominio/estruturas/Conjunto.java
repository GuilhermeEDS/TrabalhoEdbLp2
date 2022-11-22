package dominio.estruturas;

import dominio.*;

public class Conjunto<T> {
    private Conjunto pai;
    private int rank;
    private T item;

    private Conjunto(T item) {
        pai = this;
        rank = 0;
        this.item = item;
    }

    int getRank() {
        return rank;
    }

    void setRank(int rank) {
        this.rank = rank;
    }

    public T getItem() {
        return item;
    }

    void union(Conjunto conjunto) {
        if (areMerged(conjunto)) {
            return;
        }

        this.find().link(conjunto.find());
    }

    void link(Conjunto conjunto) {
        if (rank > conjunto.rank) {
            conjunto.pai = this;
            return;
        }

        pai = conjunto;
        if (rank == conjunto.rank) {
            conjunto.setRank(conjunto.getRank() + 1);
        }
    }

    Conjunto find() {
        Conjunto conjunto = this;
        while (conjunto.pai != this) {
            conjunto = conjunto.pai;
        }
        return conjunto;
    }

    boolean areMerged(Conjunto conjunto) {
        return this.find() == conjunto.find();
    }
}
