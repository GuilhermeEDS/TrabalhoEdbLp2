import java.util.*;

import dominio.*;
import estruturas.Conjunto;
import interfaces.ProcessadorLigacoes;

public class ProcessadorSimples extends ProcessadorLigacoes {
    public ProcessadorSimples(InformacoesArquivo informacoesArquivo) {
        super(informacoesArquivo);
    }

    private static ArrayList<ArrayList<Integer>> copiar(ArrayList<ArrayList<Integer>> list) {
        ArrayList<ArrayList<Integer>> copia = new ArrayList<ArrayList<Integer>>();
        for (ArrayList<Integer> subLista : list) {
            ArrayList<Integer> copiaSubLista = new ArrayList<Integer>();
            copiaSubLista.addAll(subLista);
            copia.add(copiaSubLista);
        }
        return copia;
    }

    private static ArrayList<ArrayList<Integer>> todosPossiveiscomTamanhoMaximo(int idFinal, int tamanho) {
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

    private static <T> List<ArrayList<T>> filtrarPorTamanho(ArrayList<ArrayList<T>> lista, int size) {
        return lista.stream().filter(item -> item.size() == size).toList();
    }

    private static List<ArrayList<Integer>> todosPossiveisComTamanho(int idFinal, int tamanho) {
        return filtrarPorTamanho(todosPossiveiscomTamanhoMaximo(idFinal, tamanho), tamanho);
    }

    private static ArrayList<Conjunto<Casa>> criarConjuntosUnitariosCasas(int numeroCasas) {
        ArrayList<Conjunto<Casa>> conjuntos = new ArrayList<>();
        for (int i = 0; i < numeroCasas; i++) {
            conjuntos.add(new Conjunto<Casa>(new Casa(i)));
        }

        return conjuntos;
    }

    private Integer somaRede(ArrayList<Integer> rede) {
        Integer retorno = 0;

        ArrayList<Conjunto<Casa>> ligacoes = criarConjuntosUnitariosCasas(informacoesArquivo.getNumeroCasas());

        for (Integer a : rede) {
            Ligacao aux = informacoesArquivo.getLigacoes().get(a);
            Conjunto<Casa> c1 = ligacoes.get(aux.getCasa1().getId());
            Conjunto<Casa> c2 = ligacoes.get(aux.getCasa2().getId());

            if (c1.areMerged(c2)) {
                return null;
            }

            if (c1.getItem().getQuantidadeArestas() == informacoesArquivo
                    .getMaximoLigacoes()
                    || (ligacoes.get(aux.getCasa2().getId())).getItem().getQuantidadeArestas() == informacoesArquivo
                            .getMaximoLigacoes()) {
                return null;
            }

            c1.union(c2);
            c1.getItem().setQuantidadeArestas(c1.getItem().getQuantidadeArestas() + 1);
            c2.getItem().setQuantidadeArestas(c2.getItem().getQuantidadeArestas() + 1);
            retorno += aux.getCusto();
        }
        return retorno;
    }

    public ArrayList<ArrayList<Ligacao>> processar() {
        List<ArrayList<Integer>> res = todosPossiveisComTamanho(
                informacoesArquivo.getLigacoes().size() - 1,
                informacoesArquivo.getNumeroCasas() - 1);

        Integer melhorIndiceLigacao = 0;
        Integer melhorSoma = null;
        for (int indiceLigacao = 0; indiceLigacao < res.size(); indiceLigacao++) {
            Integer soma = somaRede(res.get(indiceLigacao));
            if (melhorSoma == null || (soma != null && soma < melhorSoma)) {
                melhorIndiceLigacao = indiceLigacao;
                melhorSoma = soma;
            }
        }

        res.get(melhorIndiceLigacao).forEach(System.out::println);

        return null;
    }
}
