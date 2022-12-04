package main;

import dominio.Casa;
import dominio.InformacoesArquivo;
import dominio.Ligacao;
import excecao.ArquivoInvalido;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

public class LeitorArquivos {
    public static String parseArgs(String[] args) throws Exception {
        if (args.length <= 1 || args.length > 2) {
            throw new Exception();
        }
        return args[0];
    }

    public static InformacoesArquivo lerArquivo(String caminhoArquivo) throws ArquivoInvalido {
        List<String> linhasArquivo;
        try {
            linhasArquivo = Files.readAllLines(Paths.get(caminhoArquivo));
        } catch (IOException e) {
            throw new ArquivoInvalido("Não foi possível ler o arquivo");
        }

        if (linhasArquivo.size() == 0) {
            throw new ArquivoInvalido("O arquivo está vazio");
        }

        if (linhasArquivo.size() == 1) {
            throw new ArquivoInvalido("As ligações entre as casas não foram informadas");
        }

        String[] informacoesGerais = linhasArquivo.get(0).split(" ");
        if (informacoesGerais.length != 2) {
            throw new ArquivoInvalido("A primeira linha está inválida");
        }

        int quantidadeCasas = Integer.parseInt(informacoesGerais[0]);
        int maximoLigacoes = Integer.parseInt(informacoesGerais[1]);

        ArrayList<Ligacao> ligacoes = new ArrayList<>(quantidadeCasas * (quantidadeCasas - 1) / 2);
        for (int indiceCasaAtual = 0; indiceCasaAtual < quantidadeCasas - 1; indiceCasaAtual++) {
            String[] linhaAtual = linhasArquivo.get(indiceCasaAtual + 1).split(" ");
            if (linhaAtual.length != (quantidadeCasas - indiceCasaAtual - 1)) {
                throw new ArquivoInvalido("Número de conexões incorretas para a casa " + indiceCasaAtual);
            }

            for (int i = 0; i < linhaAtual.length; i++) {
                Casa casa1 = new Casa(indiceCasaAtual);
                Casa casa2 = new Casa(indiceCasaAtual + i + 1);
                int custo = Integer.parseInt(linhaAtual[i]);
                ligacoes.add(new Ligacao(casa1, casa2, custo));
            }
        }

        ligacoes.sort(Comparator.comparing(Ligacao::getCusto));

        return new InformacoesArquivo(quantidadeCasas, maximoLigacoes, ligacoes);
    }
}
