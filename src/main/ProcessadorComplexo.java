package main;

import java.util.*;

import dominio.*;
import estrutura.*;
import abstrato.ProcessadorLigacoes;

public class ProcessadorComplexo extends ProcessadorLigacoes {
    public ProcessadorComplexo(InformacoesArquivo informacoesArquivo) {
        super(informacoesArquivo);
    }

    public ArrayList<Particao> particoes(ArrayList<Ligacao> ligacoes) {
        ArrayList<Particao> particoes = new ArrayList<Particao>();

        for (int index = 0; index < ligacoes.size(); index++) {
            ArrayList<Ligacao> ligacoesRestritas = new ArrayList<Ligacao>();
            ligacoesRestritas.add(ligacoes.get(index));

            ArrayList<Ligacao> ligacoesObrigatorias = new ArrayList<Ligacao>();
            for (int j = 0; j < index; j++) {
                ligacoesObrigatorias.add(ligacoes.get(j));
            }

            Particao particao = new Particao(ligacoesObrigatorias, ligacoesRestritas);
            if (particaoValida(particao)) {
                particoes.add(particao);
            }
        }

        return particoes;
    }

    private Boolean temCasaBloqueada(ArrayList<Ligacao> ligacoesRestritas) {
        ArrayList<Integer> numeroLigacoesBloqueadas = new ArrayList<Integer>(
                Collections.nCopies(informacoesArquivo.getNumeroCasas(), 0));
        for (Ligacao ligacao : ligacoesRestritas) {
            int idCasa1 = ligacao.getCasa1().getId();

            numeroLigacoesBloqueadas.set(idCasa1, numeroLigacoesBloqueadas.get(idCasa1) + 1);
            if (numeroLigacoesBloqueadas.get(idCasa1) >= informacoesArquivo.getNumeroCasas() - 1) {
                return true;
            }

            int idCasa2 = ligacao.getCasa2().getId();
            numeroLigacoesBloqueadas.set(idCasa2, numeroLigacoesBloqueadas.get(idCasa2) + 1);
            if (numeroLigacoesBloqueadas.get(idCasa2) >= informacoesArquivo.getNumeroCasas() - 1) {
                return true;
            }
        }

        return false;
    }

    private boolean particaoValida(Particao particao) {
        if (temCasaBloqueada(particao.getLigacoesRestritas())) {
            return false;
        }

        ArrayList<Conjunto<Casa>> conjuntos = criarConjuntosUnitariosCasas(informacoesArquivo.getNumeroCasas());

        for (Ligacao ligacao : particao.getLigacoesObrigatorias()) {
            Conjunto<Casa> c1 = conjuntos.get(ligacao.getCasa1().getId());
            Conjunto<Casa> c2 = conjuntos.get(ligacao.getCasa2().getId());

            if (c1.areMerged(c2)) {
                return false;
            }

            if (c1.getItem().getQuantidadeArestas() == informacoesArquivo
                    .getMaximoLigacoes()
                    || c2.getItem().getQuantidadeArestas() == informacoesArquivo
                            .getMaximoLigacoes()) {
                return false;
            }

            c1.union(c2);
            c1.getItem().setQuantidadeArestas(c1.getItem().getQuantidadeArestas() + 1);
            c2.getItem().setQuantidadeArestas(c2.getItem().getQuantidadeArestas() + 1);
        }

        return true;
    }

    /*
     * algorithm Kruskal(G) is
     * F:= ∅
     * for each v ∈ G.V do
     * MAKE-SET(v)
     * for each (u, v) in G.E ordered by weight(u, v), increasing do
     * if FIND-SET(u) ≠ FIND-SET(v) then
     * F:= F ∪ {(u, v)} ∪ {(v, u)}
     * UNION(FIND-SET(u), FIND-SET(v))
     * return F
     */

    private ArrayList<Ligacao> ligacoesOpcionais(Particao particao) {
        ArrayList<Ligacao> ligacoes = new ArrayList<Ligacao>();
        for (Ligacao ligacao : informacoesArquivo.getLigacoes()) {
            if (!particao.getLigacoesObrigatorias().contains(ligacao)
                    && !particao.getLigacoesRestritas().contains(ligacao)) {
                ligacoes.add(ligacao);
            }
        }

        return ligacoes;
    }

    private ArrayList<Ligacao> kruskal(Particao particao) {
        ArrayList<Conjunto<Casa>> conjuntos = ProcessadorLigacoes
                .criarConjuntosUnitariosCasas(
                        informacoesArquivo.getNumeroCasas());

        ArrayList<Ligacao> ligacoes = new ArrayList<Ligacao>();
        for (Ligacao ligacao : particao.getLigacoesObrigatorias()) {
            Conjunto<Casa> conjuntoCasa1 = conjuntos.get(ligacao.getCasa1().getId());
            Conjunto<Casa> conjuntoCasa2 = conjuntos.get(ligacao.getCasa2().getId());

            conjuntoCasa1.union(conjuntoCasa2);

            Casa casa1 = conjuntoCasa1.getItem();
            Casa casa2 = conjuntoCasa2.getItem();

            casa1.setQuantidadeArestas(casa1.getQuantidadeArestas() + 1);
            casa2.setQuantidadeArestas(casa2.getQuantidadeArestas() + 1);

            ligacoes.add(ligacao);
        }

        ArrayList<Ligacao> ligacoesOpcionais = ligacoesOpcionais(particao);
        for (Ligacao ligacao : ligacoesOpcionais) {
            Conjunto<Casa> conjuntoCasa1 = conjuntos.get(ligacao.getCasa1().getId());
            Conjunto<Casa> conjuntoCasa2 = conjuntos.get(ligacao.getCasa2().getId());

            Casa casa1 = conjuntoCasa1.getItem();
            Casa casa2 = conjuntoCasa2.getItem();

            if (casa1.getQuantidadeArestas() >= informacoesArquivo.getMaximoLigacoes()
                    || casa2.getQuantidadeArestas() >= informacoesArquivo.getMaximoLigacoes()) {
                continue;
            }

            if (conjuntoCasa1.areMerged(conjuntoCasa2)) {
                continue;
            }

            conjuntoCasa1.union(conjuntoCasa2);

            casa1.setQuantidadeArestas(casa1.getQuantidadeArestas() + 1);
            casa2.setQuantidadeArestas(casa2.getQuantidadeArestas() + 1);

            ligacoes.add(ligacao);
        }

        Conjunto<Casa> primeiro = conjuntos.get(0).find();
        for (Conjunto<Casa> conjunto : conjuntos) {
            if (!primeiro.areMerged(conjunto)) {
                return null;
            }
        }

        return ligacoes;
    }

    public ArrayList<ArrayList<Ligacao>> processar() {
        ArrayList<ArrayList<Ligacao>> resultados = new ArrayList<ArrayList<Ligacao>>();

        // Calcula MST (Kruskal) usando todas as ligações (1)
        ArrayList<Ligacao> melhor = kruskal(new Particao(new ArrayList<Ligacao>(), new ArrayList<Ligacao>()));
        resultados.add(melhor);

        // Particiona as ligações de (1)
        ArrayList<Particao> particoesMelhor = particoes(melhor);

        // Pega a melhor MST com as restrições (2)

        // (2) é a segunda melhor
        // Faz MST de 2
        // Particiona as ligações de (2) que não estão em (1)

        return resultados;
    }
}
