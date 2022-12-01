package main;

import abstrato.ProcessadorLigacoes;
import dominio.*;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

public class ProcessadorComplexo extends ProcessadorLigacoes {
    public ProcessadorComplexo(InformacoesArquivo informacoesArquivo) {
        super(informacoesArquivo);
    }

    public ArrayList<Particao> particoes(ArrayList<Ligacao> ligacoes) {
        ArrayList<Particao> particoes = new ArrayList<>();

        for (int index = 0; index < ligacoes.size(); index++) {
            ArrayList<Ligacao> ligacoesRestritas = new ArrayList<>();
            ligacoesRestritas.add(ligacoes.get(index));

            ArrayList<Ligacao> ligacoesObrigatorias = new ArrayList<>();
            for (int j = 0; j < index; j++) {
                ligacoesObrigatorias.add(ligacoes.get(j));
            }

            Particao particao = new Particao(ligacoesObrigatorias, ligacoesRestritas);
            particoes.add(particao);
        }

        return particoes;
    }

    private ArrayList<Ligacao> kruskal(Particao particao) {
        ArrayList<Conjunto<Casa>> conjuntos = ProcessadorLigacoes.criarConjuntosUnitariosCasas(informacoesArquivo.getNumeroCasas());

        ArrayList<Ligacao> ligacoes = new ArrayList<>();
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
        ArrayList<Ligacao> diferenca = new ArrayList<>();

        for (Ligacao ligacao : ligacoes) {
            if (!particao.getLigacoesObrigatorias().contains(ligacao) && !particao.getLigacoesRestritas().contains(ligacao)) {
                diferenca.add(ligacao);
            }
        }

        return diferenca;
    }

    private boolean obedeceMaximoLigacoes(ArrayList<Ligacao> ligacoes) {
        ArrayList<Integer> quantidadeLigacoesCasa = new ArrayList<>(Collections.nCopies(informacoesArquivo.getNumeroCasas(), 0));
        for (Ligacao ligacao : ligacoes) {
            int idCasa1 = ligacao.getCasa1().getId();


            quantidadeLigacoesCasa.set(idCasa1, quantidadeLigacoesCasa.get(idCasa1) + 1);
            if (quantidadeLigacoesCasa.get(idCasa1) > informacoesArquivo.getMaximoLigacoes()) {
                return false;
            }

            int idCasa2 = ligacao.getCasa2().getId();
            quantidadeLigacoesCasa.set(idCasa2, quantidadeLigacoesCasa.get(idCasa2) + 1);
            if (quantidadeLigacoesCasa.get(idCasa2) > informacoesArquivo.getMaximoLigacoes()) {
                return false;
            }
        }

        return true;
    }

    public ArrayList<ArrayList<Ligacao>> processar() {
        ArrayList<ArrayList<Ligacao>> resultados = new ArrayList<>();

        ArrayList<Ligacao> melhorGeral = kruskal(new Particao(new ArrayList<>(), new ArrayList<>()));
        if (melhorGeral == null) {
            return resultados;
        }

        if (obedeceMaximoLigacoes(melhorGeral)) {
            resultados.add(melhorGeral);
        }

        ArrayList<Particao> particoes = particoes(melhorGeral);

        while (true) {
            int melhorIndiceParticao = 0;
            Integer melhorCusto = null;

            for (int i = 0; i < particoes.size(); i++) {
                Particao particao = particoes.get(i);
                ArrayList<Ligacao> mst = kruskal(particao);

                if (mst == null) {
                    particoes.remove(particao);
                    continue;
                }

                int custo = calcularCusto(mst);
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
            if (obedeceMaximoLigacoes(mst)) {
                resultados.add(mst);
            }

            particoes.remove(melhorIndiceParticao);

            ArrayList<Ligacao> diff = diferenca(mst, melhorParticao);

            if (diff.isEmpty()) {
                continue;
            }

            ArrayList<Particao> particoesDiff = particoes(diff);

            for (Particao particao : particoesDiff) {
                particao.getLigacoesObrigatorias().addAll(melhorParticao.getLigacoesObrigatorias());
                particao.getLigacoesRestritas().addAll(melhorParticao.getLigacoesRestritas());

                particao.getLigacoesObrigatorias().sort(Comparator.comparing(Ligacao::getCusto));
                particao.getLigacoesRestritas().sort(Comparator.comparing(Ligacao::getCusto));
            }

            particoes.addAll(particoesDiff);
        }

        return resultados;
    }
}
