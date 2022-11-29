package main;

import java.util.*;

import abstrato.ProcessadorLigacoes;
import dominio.*;

public class ProcessadorSimples extends ProcessadorLigacoes {
    public ProcessadorSimples(InformacoesArquivo informacoesArquivo) {
        super(informacoesArquivo);
    }

    private static ArrayList<ArrayList<Integer>> copiar(
            ArrayList<ArrayList<Integer>> list) {
        ArrayList<ArrayList<Integer>> copia = new ArrayList<>();
        for (ArrayList<Integer> subLista : list) {
            ArrayList<Integer> copiaSubLista = new ArrayList<>(subLista);
            copia.add(copiaSubLista);
        }
        return copia;
    }

    private static ArrayList<ArrayList<Integer>> todosPossiveiscomTamanhoMaximo(int idFinal) {
        if (idFinal < 0) {
            ArrayList<ArrayList<Integer>> listaComListaVazia = new ArrayList<>();
            listaComListaVazia.add(new ArrayList<>());
            return listaComListaVazia;
        }

        ArrayList<ArrayList<Integer>> todosPossiveisMenosUm = todosPossiveiscomTamanhoMaximo(idFinal - 1);

        ArrayList<ArrayList<Integer>> copia = copiar(todosPossiveisMenosUm);
        for (ArrayList<Integer> possibilidade : copia) {
            possibilidade.add(idFinal);
        }
        todosPossiveisMenosUm.addAll(copia);

        return todosPossiveisMenosUm;
    }

    private static <T> ArrayList<ArrayList<T>> filtrarPorTamanho(
            ArrayList<ArrayList<T>> lista, int size) {
        ArrayList<ArrayList<T>> listaFiltrada = new ArrayList<>();
        for (ArrayList<T> elemento : lista) {
            if (elemento.size() == size) {
                listaFiltrada.add(elemento);
            }
        }

        return listaFiltrada;
    }

    private static ArrayList<ArrayList<Integer>> todosPossiveisComTamanho(
            int idFinal, int tamanho) {
        return filtrarPorTamanho(todosPossiveiscomTamanhoMaximo(idFinal), tamanho);
    }

    private Integer somaLigacoes(ArrayList<Ligacao> ligacoes) {
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

    public ArrayList<ArrayList<Ligacao>> processar() {
        ArrayList<ArrayList<Integer>> res = todosPossiveisComTamanho(
                informacoesArquivo.getLigacoes().size() - 1,
                informacoesArquivo.getNumeroCasas() - 1);

        Integer melhorIndiceLigacao = null;
        Integer melhorSoma = null;
        for (int indiceLigacao = 0; indiceLigacao < res.size(); indiceLigacao++) {
            ArrayList<Ligacao> ligacoes = new ArrayList<>();
            for (var indice : res.get(indiceLigacao)) {
                ligacoes.add(informacoesArquivo.getLigacoes().get(indice));
            }

            Integer soma = somaLigacoes(ligacoes);
            if (melhorSoma == null || (soma != null && soma < melhorSoma)) {
                melhorIndiceLigacao = indiceLigacao;
                melhorSoma = soma;
            }
        }

        var resultado = new ArrayList<ArrayList<Ligacao>>();
        if (melhorIndiceLigacao != null) {
            var melhor = new ArrayList<Ligacao>();
            for (var ligacao : res.get(melhorIndiceLigacao)) {
                melhor.add(informacoesArquivo.getLigacoes().get(ligacao));
            }

            resultado.add(melhor);
        }

        return resultado;
    }
}
