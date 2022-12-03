package main;

import dominio.InformacoesArquivo;
import dominio.Ligacao;
import excecao.ArquivoInvalido;

import java.util.ArrayList;

public class Main {
    private static final boolean PROCESSADOR_COMPLEXO = true;

    public static String parseArgs(String[] args) throws Exception {
        if (args.length != 1) {
            throw new Exception();
        }

        return args[0];
    }

    public static void main(String[] args) {
        String caminhoArquivo;
        try {
            caminhoArquivo = parseArgs(args);
        } catch (Exception e) {
            System.out.println("Uso correto: java -jar arquivo <caminhoArquivo>\n\t<caminhoArquivo>: O caminho para o arquivo contendo " + "o máximo de ligações e o custo de cada ligação possível");
            return;
        }

        InformacoesArquivo informacoesArquivo = null;
        try {
            informacoesArquivo = LeitorArquivos.lerArquivo(caminhoArquivo);
        } catch (ArquivoInvalido e) {
            System.out.println("Erro ao ler o arquivo: " + e.getMessage());
        }

        long inicio = System.nanoTime();
        ArrayList<ArrayList<Ligacao>> res;
        if (PROCESSADOR_COMPLEXO) {
            ProcessadorComplexo processadorComplexo = new ProcessadorComplexo(informacoesArquivo);
            res = processadorComplexo.processar();

        } else {
            ProcessadorSimples processadorSimples = new ProcessadorSimples(informacoesArquivo);
            res = processadorSimples.processar();
        }
        long fim = System.nanoTime();

        System.out.println("Demorou " + (fim - inicio) / 1000000 + " ms");

        try {
            PrintFile.generateFile(res);
        } catch (Exception e) {
            System.out.println("DEU ERRO NA HORA DE FAZER O ARQUIVO");
            System.out.println(e.getMessage());
        }
    }
}
