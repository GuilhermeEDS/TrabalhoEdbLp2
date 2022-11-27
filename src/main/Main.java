package main;

import dominio.*;
import excecao.ArquivoInvalido;

public class Main {
    public static String parseArgs(String[] args) throws Exception {
        if (args.length != 1) {
            throw new Exception();
        }

        return args[0];
    }

    public static void main(String[] args) {
        String caminhoArquivo = null;
        try {
            caminhoArquivo = parseArgs(args);
        } catch (Exception e) {
            System.out.println("Uso correto: java -jar arquivo <caminhoArquivo>\n"
                    + "\t<caminhoArquivo>: O caminho para o arquivo contendo "
                    + "o máximo de ligações e o custo de cada ligação possível");
            return;
        }

        InformacoesArquivo informacoesArquivo = null;
        try {
            informacoesArquivo = LeitorArquivos.lerArquivo(caminhoArquivo);
        } catch (ArquivoInvalido e) {
            System.out.println("Erro ao ler o arquivo: " + e.getMessage());
        }

        ProcessadorComplexo processadorComplexo = new ProcessadorComplexo(informacoesArquivo);
        var res = processadorComplexo.particoes(informacoesArquivo.getLigacoes());
        for (var particao : res) {
            for (var ligacao : particao.getLigacoesObrigatorias()) {
                System.out.println("S " + ligacao.getCasa1().getId() + " -> " + ligacao.getCasa2().getId() + " = "
                        + ligacao.getCusto());
            }
            for (var ligacao : particao.getLigacoesRestritas()) {
                System.out.println("N " + ligacao.getCasa1().getId() + " -> " + ligacao.getCasa2().getId() + " = "
                        + ligacao.getCusto());
            }
            System.out.println("=======");
        }
        // ProcessadorSimples processadorRede = new
        // ProcessadorSimples(informacoesArquivo);
        // processadorRede.processar();
    }
}
