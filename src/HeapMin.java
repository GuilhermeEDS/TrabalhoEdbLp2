import java.util.ArrayList;

public class HeapMin<T> {
    private ArrayList<T> valores;
    int capacidadeUsada = 0;

    public HeapMin(int capacidade) {
        valores = new ArrayList<T>(capacidade);
    }

    public T peek() {
        return valores.get(0);
    }

    public T pop() {
        T raiz = valores.get(0);

        // Coisar árvore

        return raiz;
    }

    public void inserir() throws Exception {
        if (capacidadeUsada == valores.size()) {
            throw new Exception("Capacidade cheia");
        }
    }
}
