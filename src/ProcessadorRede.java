import java.util.ArrayList;

import dominio.Casa;
import dominio.InformacoesArquivo;
import dominio.Ligacao;
import dominio.estruturas.Conjunto;

public class ProcessadorRede {
    private InformacoesArquivo informacoesArquivo;

    public ProcessadorRede(InformacoesArquivo informacoesArquivo) {
        this.informacoesArquivo = informacoesArquivo;
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

    private Integer soma_rede(ArrayList<Integer> rede) {
        Integer retorno = 0;

        ArrayList<Conjunto<Casa>> ligacoes = new ArrayList<>();
        for (int i = 0; i < informacoesArquivo.getNumeroCasas(); i++) {
            ligacoes.add(new Conjunto<Casa>(new Casa(i)));
        }

        for (var a : rede) {
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

    public ArrayList<Conjunto<Casa>> processar() {
        var res = ProcessadorRede.todosPossiveis(informacoesArquivo.getLigacoes().size() - 1);
        var filteredRes = ProcessadorRede.filtrarPorTamanho(res, informacoesArquivo.getNumeroCasas() - 1);

        int min = -1;
        Integer soma = null;
        for (int i = 0; i < filteredRes.size(); i++) {
            if(min == -1){
                    var ligacoes = filteredRes.get(i);
                    soma = soma_rede(ligacoes);

                    if(soma != null)
                        min = i; 
            }else{
                Integer somaAux = soma_rede(filteredRes.get(i)); 
                    if (somaAux!= null && somaAux < soma) {
                        min = i;
                    }
                }
            }  

        filteredRes.get(min).forEach(System.out::println);

        return null;
    }
}
