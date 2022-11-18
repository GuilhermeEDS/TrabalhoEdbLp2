public class Casa {
    int id;
    int nArestas;

    Casa(int id) {
        this.id = id;
        this.nArestas = 0;
    }

    public int getId() {
        return id;
    }

    public void setnArestas(int nArestas) {
        this.nArestas = nArestas;
    }

    public int getnArestas() {
        return nArestas;
    }
}