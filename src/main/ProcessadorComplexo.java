package main;

import dominio.InformacoesArquivo;
import dominio.Ligacao;
import dominio.Particao;
import dominio.ParticaoCache;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

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

        ArrayList<Ligacao> melhorGeral = kruskal(new Particao(new ArrayList<>(), new ArrayList<>()), informacoesArquivo);
        if (melhorGeral == null) {
            return resultados;
        }

        if (obedeceMaximoLigacoes(melhorGeral)) {
            resultados.add(melhorGeral);
            return resultados;
        }

        ArrayList<Particao> particoesMelhor = particoes(melhorGeral);
        List<ParticaoCache> particoes = new ArrayList<>();
        for (Particao particao : particoesMelhor) {
            try {
                ParticaoCache particaoCache = new ParticaoCache(particao, informacoesArquivo);
                particoes.add(particaoCache);
            } catch (Exception e) {
                // Não precisa fazer nada
            }
        }

        while (true) {
            int melhorIndiceParticao = 0;
            Integer melhorCusto = null;

            for (int i = 0; i < particoes.size(); i++) {
                int custo = particoes.get(i).getCusto();
                if (melhorCusto == null || custo < melhorCusto) {
                    melhorIndiceParticao = i;
                    melhorCusto = custo;
                }
            }

            if (melhorCusto == null) {
                break;
            }

            ParticaoCache melhorParticaoCache = particoes.get(melhorIndiceParticao);
            Particao melhorParticao = melhorParticaoCache.getParticao();
            ArrayList<Ligacao> ligacoes = melhorParticaoCache.getLigacoes();
            particoes.remove(melhorIndiceParticao);

            if (obedeceMaximoLigacoes(ligacoes)) {
                resultados.add(ligacoes);
                return resultados;
            }

            ArrayList<Ligacao> diff = diferenca(ligacoes, melhorParticao);

            if (diff.isEmpty()) {
                continue;
            }

            ArrayList<Particao> particoesDiff = particoes(diff);

            for (Particao particao : particoesDiff) {
                particao.getLigacoesObrigatorias().addAll(melhorParticao.getLigacoesObrigatorias());
                particao.getLigacoesRestritas().addAll(melhorParticao.getLigacoesRestritas());

                particao.getLigacoesObrigatorias().sort(Comparator.comparing(Ligacao::getCusto));
                particao.getLigacoesRestritas().sort(Comparator.comparing(Ligacao::getCusto));

                try {
                    ParticaoCache particaoCache = new ParticaoCache(particao, informacoesArquivo);
                    particoes.add(particaoCache);
                } catch (Exception e) {
                    // Não precisa fazer nada
                }
            }
        }

        return resultados;
    }
}
