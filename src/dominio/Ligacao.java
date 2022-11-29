package dominio;

public class Ligacao {
    private final Casa casa1;
    private final Casa casa2;
    private final Integer custo;

    public Ligacao(Casa casa1, Casa casa2, Integer custo) {
        this.casa1 = casa1;
        this.casa2 = casa2;
        this.custo = custo;
    }

    public Casa getCasa1() {
        return casa1;
    }

    public Casa getCasa2() {
        return casa2;
    }

    public Integer getCusto() {
        return custo;
    }

    @Override
    public String toString() {
        return casa1.getId() + " -> " + casa2.getId() + " : " + custo;
    }
}
