package estruturas;

public class No {
    int valor;

    int altura;

    int tamanhoArvoreEsquerda;
    int tamanhoArvoreDireita;

    No esquerda;
    No direita;

    public No(int valor) {
        this.valor = valor;

        altura = 1;
        tamanhoArvoreEsquerda = 0;
        tamanhoArvoreDireita = 0;
    }
}