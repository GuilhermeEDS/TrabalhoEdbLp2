package estruturas;

public class ArvoreBuscaBinaria {
    public No raiz;

    public ArvoreBuscaBinaria(int raiz) {
        this.raiz = new No(raiz);
    }

    private No buscar(No no, int valor) {
        if (no == null)
            return null;

        if (valor < no.valor)
            return buscar(no.esquerda, valor);

        if (valor > no.valor)
            return buscar(no.direita, valor);

        return no;
    }

    public No buscar(int valor) {
        return buscar(raiz, valor);
    }

    private boolean inserirSemAtualizar(No no, int valor) {
        if (valor < no.valor) {
            if (no.esquerda == null) {
                no.esquerda = new No(valor);
                return true;
            }

            return inserirSemAtualizar(no.esquerda, valor);
        }

        if (valor > no.valor) {
            if (no.direita == null) {
                no.direita = new No(valor);
                return true;
            }

            return inserirSemAtualizar(no.direita, valor);
        }

        return false;
    }

    public boolean inserir(int valor) {
        boolean resultado = inserirSemAtualizar(raiz, valor);
        atualizarNumeroNos(raiz);
        return resultado;
    }

    private No buscarNoPai(No no, int valor) throws Exception {
        if (no == null)
            return null;

        if (valor < no.valor) {
            if (no.esquerda == null)
                return null;

            if (no.esquerda.valor == valor)
                return no;

            return buscarNoPai(no.esquerda, valor);
        }

        if (valor > no.valor) {
            if (no.direita == null)
                return null;

            if (no.direita.valor == valor)
                return no;

            return buscarNoPai(no.direita, valor);
        }

        throw new Exception("O nó passado já possui o valor");
    }

    private Integer removerSemAtualizar(int valor) {
        No pai = null;
        try {
            pai = buscarNoPai(raiz, valor);
        } catch (Exception e) {
            raiz.valor = remover(noMaisDireita(raiz.esquerda));
            return valor;
        }

        if (pai == null)
            return null;

        if (pai.esquerda != null && pai.esquerda.valor == valor) {
            if (pai.esquerda.esquerda == null)
                pai.esquerda = pai.esquerda.direita;
            else if (pai.esquerda.direita == null)
                pai.esquerda = pai.esquerda.esquerda;
            else
                pai.esquerda.valor = remover(noMaisDireita(pai.esquerda.esquerda));
        } else if (pai.direita.esquerda == null)
            pai.direita = pai.direita.direita;
        else if (pai.direita.direita == null)
            pai.direita = pai.direita.esquerda;
        else
            pai.direita.valor = remover(noMaisDireita(pai.direita.esquerda));

        return valor;
    }

    private int atualizarNumeroNos(No no) {
        if (no == null)
            return 0;

        no.tamanhoArvoreEsquerda = atualizarNumeroNos(no.esquerda);
        no.tamanhoArvoreDireita = atualizarNumeroNos(no.direita);
        return no.tamanhoArvoreEsquerda + no.tamanhoArvoreDireita + 1;
    }

    public Integer remover(int valor) {
        Integer resultado = removerSemAtualizar(valor);
        atualizarNumeroNos(raiz);
        return resultado;
    }

    private int noMaisDireita(No no) {
        No noAtual = no;
        while (noAtual.direita != null) {
            noAtual = noAtual.direita;
        }

        return noAtual.valor;
    }

    private Integer enesimoElemento(No no, int n) {
        if (no == null)
            return null;

        if (n == (no.tamanhoArvoreEsquerda + 1))
            return no.valor;

        if (n < (no.tamanhoArvoreEsquerda + 1))
            return enesimoElemento(no.esquerda, n);

        return enesimoElemento(no.direita, n - no.tamanhoArvoreEsquerda - 1);
    }

    public Integer enesimoElemento(int n) {
        return enesimoElemento(raiz, n);
    }

    private int posicao(No no, int x) throws Exception {
        if (no == null)
            throw new Exception("Valor não encontrado");

        if (x < no.valor)
            return posicao(no.esquerda, x);

        if (x > no.valor)
            return posicao(no.direita, x) + no.tamanhoArvoreEsquerda + 1;

        return no.tamanhoArvoreEsquerda + 1;
    }

    public int posicao(int x) throws Exception {
        return posicao(raiz, x);
    }

    public int mediana() {
        int tamanhoArvore = raiz.tamanhoArvoreEsquerda + raiz.tamanhoArvoreDireita + 1;

        if (tamanhoArvore % 2 == 1)
            return enesimoElemento((tamanhoArvore + 1) / 2);

        return enesimoElemento(tamanhoArvore / 2);
    }

    private double soma(No no) {
        if (no == null)
            return 0;
        return no.valor + soma(no.esquerda) + soma(no.direita);
    }

    private double media(No no, int x) {
        if (no == null)
            return 0;

        if (x < no.valor)
            return media(no.esquerda, x);

        if (x > no.valor)
            return media(no.direita, x);

        return soma(no) / (no.tamanhoArvoreEsquerda + no.tamanhoArvoreDireita + 1);
    }

    public double media(int x) {
        return media(raiz, x);
    }

    public boolean ehCheia(No no) {
        if ((no.esquerda == null && no.direita == null) && maxNivel(raiz) != getNivel(no, raiz))
            return false;

        if ((no.esquerda == null && no.direita != null) || (no.esquerda != null && no.direita == null))
            return false;

        if (no.esquerda != null && !ehCheia(no.esquerda))
            return false;

        if (no.direita != null && !ehCheia(no.direita))
            return false;

        return true;
    }

    public boolean ehCheia() {
        return ehCheia(raiz);
    }

    public boolean ehCompleta(No no) {
        if (no.esquerda == null || no.direita == null) {
            int max = maxNivel(raiz);
            int nivel = getNivel(no, raiz);
            if (!(max == nivel || max - 1 == nivel))
                return false;
        }

        if (no.esquerda != null)
            return ehCompleta(no.esquerda);

        if (no.direita != null)
            return ehCompleta(no.direita);

        return true;
    }

    public boolean ehCompleta() {
        return ehCompleta(raiz);
    }

    private int getNivel(No no, No raiz) {
        if (no.valor > raiz.valor)
            return 1 + getNivel(no, raiz.direita);

        if (no.valor < raiz.valor)
            return 1 + getNivel(no, raiz.esquerda);

        return 1;
    }

    public int maxNivel(No no) {
        int max = 1;
        if (no.esquerda != null) {
            int maxAux = maxNivel(no.esquerda);
            if (maxAux > max)
                max = maxAux;
        }

        if (no.direita != null) {
            int maxAux = maxNivel(no.direita);
            if (maxAux > max)
                max = maxAux;
        }

        if (no.esquerda == null && no.direita == null)
            max = getNivel(no, raiz);

        return max;
    }

    private String pre_ordem(No no) {
        String retorno = "" + no.valor;

        if (no.esquerda != null)
            retorno += " " + pre_ordem(no.esquerda);

        if (no.direita != null)
            retorno += " " + pre_ordem(no.direita);

        return retorno;
    }

    public String pre_ordem() {
        return pre_ordem(raiz);
    }

    public void imprimeArvore(int s) {
        if (s == 1 || s == 2) {
            if (s == 1)
                System.out.println(formato1(raiz));
            else if (s == 2)
                System.out.println(formato2(raiz));
        }
    }

    public String formato1(No no) {
        String retorno = "";
        int nivel = getNivel(no, raiz);
        for (int i = 0; i < nivel - 1; i++)
            retorno += "\t";

        retorno += no.valor;
        for (int i = 0; i < maxNivel(raiz) - nivel + 1; i++)
            retorno += "--------";

        retorno += "\n";
        if (no.esquerda != null)
            retorno += formato1(no.esquerda);

        if (no.direita != null)
            retorno += formato1(no.direita);

        return retorno;
    }

    public String formato2(No no) {
        String retorno = "(" + no.valor;
        if (no.esquerda != null)
            retorno += " " + formato2(no.esquerda);

        if (no.direita != null)
            retorno += " " + formato2(no.direita);

        return retorno + ")";
    }
}