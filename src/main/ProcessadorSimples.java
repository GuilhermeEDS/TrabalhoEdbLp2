package main;

import abstrato.ProcessadorLigacoes;
import dominio.Casa;
import dominio.Conjunto;
import dominio.InformacoesArquivo;
import dominio.Ligacao;

import java.math.BigInteger;
import java.util.ArrayList;

public class ProcessadorSimples extends ProcessadorLigacoes {
    public ProcessadorSimples(InformacoesArquivo informacoesArquivo) {
        super(informacoesArquivo);
    }

    private int somaLigacoes(ArrayList<Ligacao> ligacoes, Integer melhorSomaAtual) throws Exception {
        int retorno = 0;

        ArrayList<Conjunto<Casa>> conjuntos = criarConjuntosUnitariosCasas(informacoesArquivo.getNumeroCasas());

        for (Ligacao ligacao : ligacoes) {
            Conjunto<Casa> c1 = conjuntos.get(ligacao.getCasa1().getId());
            Conjunto<Casa> c2 = conjuntos.get(ligacao.getCasa2().getId());

            if (c1.areMerged(c2)) {
                throw new Exception();
            }

            if (c1.getItem().getQuantidadeArestas() == informacoesArquivo.getMaximoLigacoes() || c2.getItem().getQuantidadeArestas() == informacoesArquivo.getMaximoLigacoes()) {
                throw new Exception();
            }

            c1.union(c2);
            c1.getItem().setQuantidadeArestas(c1.getItem().getQuantidadeArestas() + 1);
            if (c1.getItem().getQuantidadeArestas() > informacoesArquivo.getMaximoLigacoes()) {
                throw new Exception();
            }

            c2.getItem().setQuantidadeArestas(c2.getItem().getQuantidadeArestas() + 1);
            if (c2.getItem().getQuantidadeArestas() > informacoesArquivo.getMaximoLigacoes()) {
                throw new Exception();
            }

            retorno += ligacao.getCusto();
            if (melhorSomaAtual != null && retorno > melhorSomaAtual) {
                throw new Exception();
            }
        }

        Conjunto<Casa> primeiro = conjuntos.get(0).find();
        for (var conjunto : conjuntos) {
            if (!primeiro.areMerged(conjunto)) {
                throw new Exception();
            }
        }

        return retorno;
    }

    public int quantidadeBitsUm(int valor, int quantidadeBits) {
        int total = 0;
        for (int i = 0; i < quantidadeBits; i++) {
            total += (valor >> i) & 1;
        }
        return total;
    }

    public ArrayList<ArrayList<Ligacao>> processar() {
        final var quantidadeLigacoes = informacoesArquivo.getLigacoes().size();
        final BigInteger quantidadeCombinacoesPossiveis = new BigInteger("2").pow(quantidadeLigacoes);

        ArrayList<Ligacao> melhor = null;
        Integer melhorSoma = null;
        for (int possibilidadeBits = 0; BigInteger.valueOf(possibilidadeBits).compareTo(quantidadeCombinacoesPossiveis) < 0; possibilidadeBits++) {
            int quantidadeBitsUm = quantidadeBitsUm(possibilidadeBits, quantidadeLigacoes);
            if (quantidadeBitsUm != informacoesArquivo.getNumeroCasas() - 1) {
                continue;
            }

            ArrayList<Ligacao> ligacoes = new ArrayList<>();
            for (int i = 0; i < quantidadeLigacoes; i++) {
                if (((possibilidadeBits >> i) & 1) == 1) {
                    ligacoes.add(informacoesArquivo.getLigacoes().get(i));
                }
            }

            try {
                int soma = somaLigacoes(ligacoes, melhorSoma);
                if (melhorSoma == null || (soma < melhorSoma)) {
                    melhorSoma = soma;
                    melhor = ligacoes;
                }
            } catch (Exception e) {
                // Não é necessário fazer nada
            }
        }

        var resultado = new ArrayList<ArrayList<Ligacao>>();
        if (melhor != null) {
            resultado.add(melhor);
        }

        return resultado;
    }
}
