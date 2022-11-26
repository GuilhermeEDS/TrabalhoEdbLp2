package abstrato;

import java.util.ArrayList;

import dominio.*;
import estruturas.*;

public abstract class ProcessadorLigacoes {
    protected InformacoesArquivo informacoesArquivo;

    public ProcessadorLigacoes(InformacoesArquivo informacoesArquivo) {
        this.informacoesArquivo = informacoesArquivo;
    }

    public abstract ArrayList<ArrayList<Ligacao>> processar();

    protected static ArrayList<Conjunto<Casa>> criarConjuntosUnitariosCasas(int numeroCasas) {
        ArrayList<Conjunto<Casa>> conjuntos = new ArrayList<Conjunto<Casa>>();
        for (int i = 0; i < numeroCasas; i++) {
            conjuntos.add(new Conjunto<Casa>(new Casa(i)));
        }

        return conjuntos;
    }

    protected Integer somaLigacoes(ArrayList<Ligacao> ligacoes) {
        Integer retorno = 0;

        ArrayList<Conjunto<Casa>> conjuntos = criarConjuntosUnitariosCasas(informacoesArquivo.getNumeroCasas());

        for (Ligacao ligacao : ligacoes) {
            Conjunto<Casa> c1 = conjuntos.get(ligacao.getCasa1().getId());
            Conjunto<Casa> c2 = conjuntos.get(ligacao.getCasa2().getId());

            if (c1.areMerged(c2)) {
                return null;
            }

            if (c1.getItem().getQuantidadeArestas() == informacoesArquivo
                    .getMaximoLigacoes()
                    || c2.getItem().getQuantidadeArestas() == informacoesArquivo
                            .getMaximoLigacoes()) {
                return null;
            }

            c1.union(c2);
            c1.getItem().setQuantidadeArestas(c1.getItem().getQuantidadeArestas() + 1);
            c2.getItem().setQuantidadeArestas(c2.getItem().getQuantidadeArestas() + 1);
            retorno += ligacao.getCusto();
        }

        Conjunto<Casa> primeiro = conjuntos.get(0).find();
        for (var conjunto : conjuntos) {
            if (!primeiro.areMerged(conjunto)) {
                return null;
            }
        }

        return retorno;
    }
}
