import java.util.ArrayList;

import dominio.InformacoesArquivo;
import dominio.Ligacao;
import dominio.estruturas.Conjunto;

public class ProcessadorRede {
    private InformacoesArquivo informacoesArquivo;

    public ProcessadorRede(InformacoesArquivo informacoesArquivo) {
        this.informacoesArquivo = informacoesArquivo;
    }

    private ArrayList<Conjunto> novoConjuntoDisjunto() {
        var conjuntos = new ArrayList<Conjunto>();
        for (Ligacao ligacao : informacoesArquivo.getLigacoes()) {
            conjuntos.add(Conjunto.makeSet(ligacao));
        }

        return conjuntos;
    }

    private static ArrayList<ArrayList<Integer>> copy(ArrayList<ArrayList<Integer>> list) {
        ArrayList<ArrayList<Integer>> res = new ArrayList<ArrayList<Integer>>();
        for (ArrayList<Integer> subLista : list) {
            ArrayList<Integer> copiaSubLista = new ArrayList<Integer>();
            copiaSubLista.addAll(subLista);
            res.add(copiaSubLista);
        }
        return res;
    }

    private static ArrayList<ArrayList<Integer>> todosPossiveis(Integer idFinal) {
        if (idFinal < 0) {
            ArrayList<ArrayList<Integer>> res = new ArrayList<ArrayList<Integer>>();
            res.add(new ArrayList<Integer>());
            return res;
        }

        ArrayList<ArrayList<Integer>> todosPossiveisMenosUm = todosPossiveis(idFinal - 1);
        ArrayList<ArrayList<Integer>> copia = copy(todosPossiveisMenosUm);
        for (ArrayList<Integer> possibilidade : copia) {
            possibilidade.add(idFinal);
        }
        todosPossiveisMenosUm.addAll(copia);

        return todosPossiveisMenosUm;
    }

    private static <T> ArrayList<ArrayList<T>> filtrarPorTamanho(ArrayList<ArrayList<T>> arrayList, int size) {
        ArrayList<ArrayList<T>> result = new ArrayList<ArrayList<T>>();
        for (ArrayList<T> element : arrayList) {
            if (element.size() == size) {
                result.add(element);
            }
        }
        return result;
    }

    public ArrayList<Conjunto> processar() {
        var res = ProcessadorRede.todosPossiveis(informacoesArquivo.getLigacoes().size());
        var filteredRes = ProcessadorRede.filtrarPorTamanho(res, informacoesArquivo.getNumeroCasas() - 1);

        for (var lt : filteredRes) {
            for (Integer i : lt) {
                System.out.print(i + " ");
            }
            System.out.println();
        }

        ArrayList<Conjunto> conjuntos = novoConjuntoDisjunto();

        return null;
    }
}
