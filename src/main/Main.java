package main;

import abstrato.ProcessadorLigacoes;
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

        ProcessadorSimples processadorSimples = new ProcessadorSimples(informacoesArquivo);
        processadorSimples.processar();

        ProcessadorComplexo processadorComplexo = new ProcessadorComplexo(informacoesArquivo);
        var res = processadorComplexo.processar();
        System.out.println("Complexo: " + res.size());

    }
}
