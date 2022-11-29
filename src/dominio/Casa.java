package dominio;

public class Casa {
    private final int id;
    private int quantidadeArestas;

    public Casa(int id) {
        this.id = id;
        this.quantidadeArestas = 0;
    }

    public int getId() {
        return id;
    }

    public int getQuantidadeArestas() {
        return quantidadeArestas;
    }

    public void setQuantidadeArestas(int quantidadeArestas) {
        this.quantidadeArestas = quantidadeArestas;
    }
}
