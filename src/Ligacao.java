public class Ligacao {
    private Casa casa1;
    private Casa casa2;

    private Integer custo;

    Ligacao(Casa casa1, Casa casa2, Integer custo) {
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
}
