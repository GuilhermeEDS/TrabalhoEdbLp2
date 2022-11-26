import java.util.*;

import dominio.*;
import abstrato.ProcessadorLigacoes;

public class ProcessadorSimples extends ProcessadorLigacoes {
    public ProcessadorSimples(InformacoesArquivo informacoesArquivo) {
        super(informacoesArquivo);
    }

    private static ArrayList<ArrayList<Integer>> copiar(
            ArrayList<ArrayList<Integer>> list) {
        ArrayList<ArrayList<Integer>> copia = new ArrayList<ArrayList<Integer>>();
        for (ArrayList<Integer> subLista : list) {
            ArrayList<Integer> copiaSubLista = new ArrayList<Integer>();
            copiaSubLista.addAll(subLista);
            copia.add(copiaSubLista);
        }
        return copia;
    }

    private static ArrayList<ArrayList<Integer>> todosPossiveiscomTamanhoMaximo(
            int idFinal, int tamanho) {
        if (idFinal < 0) {
            ArrayList<ArrayList<Integer>> listaComListaVazia = new ArrayList<ArrayList<Integer>>();
            listaComListaVazia.add(new ArrayList<Integer>());
            return listaComListaVazia;
        }

        ArrayList<ArrayList<Integer>> todosPossiveisMenosUm = todosPossiveiscomTamanhoMaximo(idFinal - 1, tamanho);

        ArrayList<ArrayList<Integer>> copia = copiar(todosPossiveisMenosUm);
        for (ArrayList<Integer> possibilidade : copia) {
            if (possibilidade.size() < tamanho) {
                possibilidade.add(idFinal);
            }
        }
        todosPossiveisMenosUm.addAll(copia);

        return todosPossiveisMenosUm;
    }

    private static <T> ArrayList<ArrayList<T>> filtrarPorTamanho(
            ArrayList<ArrayList<T>> lista, int size) {
        ArrayList<ArrayList<T>> listaFiltrada = new ArrayList<ArrayList<T>>();
        for (ArrayList<T> elemento : lista) {
            if (elemento.size() == size) {
                listaFiltrada.add(elemento);
            }
        }

        return listaFiltrada;
    }

    private static ArrayList<ArrayList<Integer>> todosPossiveisComTamanho(
            int idFinal, int tamanho) {
        return filtrarPorTamanho(todosPossiveiscomTamanhoMaximo(idFinal, tamanho), tamanho);
    }

    public ArrayList<ArrayList<Ligacao>> processar() {
        ArrayList<ArrayList<Integer>> res = todosPossiveisComTamanho(
                informacoesArquivo.getLigacoes().size() - 1,
                informacoesArquivo.getNumeroCasas() - 1);

        Integer melhorIndiceLigacao = 0;
        Integer melhorSoma = null;
        for (int indiceLigacao = 0; indiceLigacao < res.size(); indiceLigacao++) {
            ArrayList<Ligacao> ligacoes = new ArrayList<Ligacao>();
            for (var indice : res.get(indiceLigacao)) {
                ligacoes.add(informacoesArquivo.getLigacoes().get(indice));
            }

            Integer soma = somaLigacoes(ligacoes);
            if (melhorSoma == null || (soma != null && soma < melhorSoma)) {
                melhorIndiceLigacao = indiceLigacao;
                melhorSoma = soma;
            }
        }

        res.get(melhorIndiceLigacao).forEach(System.out::println);

        return null;
    }
}
