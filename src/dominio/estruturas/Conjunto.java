package dominio.estruturas;

import dominio.Ligacao;

public class Conjunto {
    private Conjunto pai;
    private int rank;
    private Ligacao ligacao;

    private Conjunto(Ligacao ligacao) {
        pai = this;
        rank = 0;
        this.ligacao = ligacao;
    }

    int getRank() {
        return rank;
    }

    void setRank(int rank) {
        this.rank = rank;
    }

    public Ligacao getLigacao() {
        return ligacao;
    }

    public static Conjunto makeSet(Ligacao ligacao) {
        return new Conjunto(ligacao);
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
