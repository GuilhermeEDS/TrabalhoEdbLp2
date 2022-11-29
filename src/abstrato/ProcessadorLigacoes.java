package abstrato;

import java.util.ArrayList;

import dominio.*;

public abstract class ProcessadorLigacoes {
    protected InformacoesArquivo informacoesArquivo;

    public ProcessadorLigacoes(InformacoesArquivo informacoesArquivo) {
        this.informacoesArquivo = informacoesArquivo;
    }

    public abstract ArrayList<ArrayList<Ligacao>> processar();

    public static int calcularCusto(ArrayList<Ligacao> ligacoes) {
        int custo = 0;
        for (Ligacao ligacao : ligacoes) {
            custo += ligacao.getCusto();
        }
        return custo;
    }

    public static ArrayList<Conjunto<Casa>> criarConjuntosUnitariosCasas(int numeroCasas) {
        ArrayList<Conjunto<Casa>> conjuntos = new ArrayList<>();
        for (int i = 0; i < numeroCasas; i++) {
            conjuntos.add(new Conjunto<>(new Casa(i)));
        }

        return conjuntos;
    }
}
