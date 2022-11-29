package main;

import java.util.*;

import dominio.*;
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

        ArrayList<Ligacao> ligacoesOpcionais = particao.ligacoesOpcionais(informacoesArquivo.getLigacoes());
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

    private ArrayList<Ligacao> diferenca(ArrayList<Ligacao> ligacoes, Particao particao) {
        ArrayList<Ligacao> diferenca = new ArrayList<Ligacao>();

        for (Ligacao ligacao : ligacoes) {
            if (!particao.getLigacoesObrigatorias().contains(ligacao)
                    && !particao.getLigacoesRestritas().contains(ligacao)) {
                diferenca.add(ligacao);
            }
        }

        return diferenca;
    }

    public ArrayList<ArrayList<Ligacao>> processar() {
        ArrayList<ArrayList<Ligacao>> resultados = new ArrayList<ArrayList<Ligacao>>();

        ArrayList<Ligacao> melhorGeral = kruskal(new Particao(new ArrayList<Ligacao>(), new ArrayList<Ligacao>()));
        resultados.add(melhorGeral);

        ArrayList<Particao> particoes = particoes(melhorGeral);

        while (true) {
            int melhorIndiceParticao = 0;
            Integer melhorCusto = null;

            for (int i = 0; i < particoes.size(); i++) {
                Particao particao = particoes.get(i);
                ArrayList<Ligacao> mst = kruskal(particao);

                if (mst == null) {
                    continue;
                }

                Integer custo = calcularCusto(mst);
                if (melhorCusto == null || custo < melhorCusto) {
                    melhorIndiceParticao = i;
                    melhorCusto = custo;
                }
            }

            if (melhorCusto == null) {
                break;
            }

            Particao melhorParticao = particoes.get(melhorIndiceParticao);
            ArrayList<Ligacao> mst = kruskal(melhorParticao);
            resultados.add(mst);
            particoes.remove(melhorIndiceParticao);

            ArrayList<Ligacao> diff = diferenca(mst, melhorParticao);
            for (Ligacao ligacao : diff) {
                System.out.println(ligacao);
            }
            return null;
            // if (diff.isEmpty()) {
            // continue;
            // }
            // ArrayList<Particao> particoesDiff = particoes(diff);
            // for (Particao particao : particoesDiff) {
            // particao.getLigacoesObrigatorias().addAll(melhorParticao.getLigacoesObrigatorias());
            // particao.getLigacoesObrigatorias().sort((a1, a2) ->
            // a1.getCusto().compareTo(a2.getCusto()));

            // particao.getLigacoesRestritas().addAll(melhorParticao.getLigacoesRestritas());
            // particao.getLigacoesRestritas().sort((a1, a2) ->
            // a1.getCusto().compareTo(a2.getCusto()));
            // }

            // particoes.addAll(particoesDiff);

        }

        return resultados;
    }
}
