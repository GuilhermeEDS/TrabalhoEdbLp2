package main;

import dominio.*;

import java.util.ArrayList;

public class ProcessadorLigacoes {
    protected final InformacoesArquivo informacoesArquivo;

    public ProcessadorLigacoes(InformacoesArquivo informacoesArquivo) {
        this.informacoesArquivo = informacoesArquivo;
    }

    public static ArrayList<Ligacao> kruskal(Particao particao, InformacoesArquivo informacoesArquivo) {
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

    public static int calcularCusto(ArrayList<Ligacao> ligacoes) throws Exception {
        if (ligacoes == null || ligacoes.size() == 0) {
            throw new Exception("Ligações inválidas");
        }
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
