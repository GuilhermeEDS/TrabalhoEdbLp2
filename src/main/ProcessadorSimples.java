package main;

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
        for (Conjunto<Casa> conjunto : conjuntos) {
            if (!primeiro.areMerged(conjunto)) {
                throw new Exception();
            }
        }

        return retorno;
    }

    public int quantidadeBitsUm(BigInteger valor, int quantidadeBits) {
        int total = 0;
        for (int i = 0; i < quantidadeBits; i++) {
            if (valor.testBit(i)) {
                total++;
            }
        }
        return total;
    }

    private ArrayList<Ligacao> bitsParaLigacoes(BigInteger possibilidadeBits) {
        ArrayList<Ligacao> ligacoes = new ArrayList<>();
        for (int i = 0; i < informacoesArquivo.getLigacoes().size(); i++) {
            if (possibilidadeBits.testBit(i)) {
                ligacoes.add(informacoesArquivo.getLigacoes().get(i));
            }
        }
        return ligacoes;
    }

    public ArrayList<ArrayList<Ligacao>> processar() {
        final int quantidadeLigacoes = informacoesArquivo.getLigacoes().size();
        final BigInteger quantidadeCombinacoesPossiveis = new BigInteger("2").pow(quantidadeLigacoes);

        ArrayList<Ligacao> melhor = null;
        Integer melhorSoma = null;
        for (BigInteger possibilidadeBits = BigInteger.valueOf(0); possibilidadeBits.compareTo(quantidadeCombinacoesPossiveis) < 0; possibilidadeBits = possibilidadeBits.add(BigInteger.valueOf(1))) {
            int quantidadeBitsUm = quantidadeBitsUm(possibilidadeBits, quantidadeLigacoes);
            if (quantidadeBitsUm != informacoesArquivo.getNumeroCasas() - 1) {
                continue;
            }

            ArrayList<Ligacao> ligacoes = bitsParaLigacoes(possibilidadeBits);

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

        ArrayList<ArrayList<Ligacao>> resultado = new ArrayList<>();
        if (melhor != null) {
            resultado.add(melhor);
        }

        return resultado;
    }
}
