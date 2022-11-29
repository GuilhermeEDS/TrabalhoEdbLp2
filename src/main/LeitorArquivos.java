package main;

import java.io.IOException;
import java.nio.file.*;
import java.util.*;

import dominio.*;
import excecao.ArquivoInvalido;

public class LeitorArquivos {
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

        var quantidadeCasas = Integer.parseInt(informacoesGerais[0]);
        var maximoLigacoes = Integer.parseInt(informacoesGerais[1]);

        ArrayList<Ligacao> ligacoes = new ArrayList<>(quantidadeCasas * (quantidadeCasas - 1) / 2);
        for (int indiceCasaAtual = 0; indiceCasaAtual < quantidadeCasas - 1; indiceCasaAtual++) {
            String[] linhaAtual = linhasArquivo.get(indiceCasaAtual + 1).split(" ");
            if (linhaAtual.length != (quantidadeCasas - indiceCasaAtual - 1)) {
                throw new ArquivoInvalido("Número de conexões incorretas para a casa " + indiceCasaAtual);
            }

            for (int i = 0; i < linhaAtual.length; i++) {
                Casa casa1 = new Casa(indiceCasaAtual);
                Casa casa2 = new Casa(indiceCasaAtual + i + 1);
                Integer custo = Integer.parseInt(linhaAtual[i]);
                ligacoes.add(new Ligacao(casa1, casa2, custo));
            }
        }

        ligacoes.sort(Comparator.comparing(Ligacao::getCusto));

        return new InformacoesArquivo(quantidadeCasas, maximoLigacoes, ligacoes);
    }
}
