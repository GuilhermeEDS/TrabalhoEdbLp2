package main;

import dominio.InformacoesArquivo;
import excecao.ArquivoInvalido;

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

        if (PROCESSADOR_COMPLEXO) {
            long inicio = System.nanoTime();
            ProcessadorComplexo processadorComplexo = new ProcessadorComplexo(informacoesArquivo);
            var res = processadorComplexo.processar();
            long fim = System.nanoTime();
            System.out.println("Demorou " + (fim - inicio) / 1000000 + " ms");

            System.out.println("Complexo: " + res.size());
        } else {
            long inicio = System.nanoTime();
            ProcessadorSimples processadorSimples = new ProcessadorSimples(informacoesArquivo);
            var res = processadorSimples.processar();
            long fim = System.nanoTime();

            System.out.println("Demorou " + (fim - inicio) / 1000000 + " ms");
            for (var ligacao : res.get(0)) {
                System.out.println(ligacao);
            }
            System.out.println("Custo: " + ProcessadorSimples.calcularCusto(res.get(0)));
        }
    }
}
