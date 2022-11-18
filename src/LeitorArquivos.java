import java.io.IOException;
import java.nio.file.*;
import java.util.*;

public class LeitorArquivos {
    public static InformacoesArquivo lerArquivo(String caminhoArquivo) throws Exception {
        List<String> linhasArquivo = null;

        try {
            linhasArquivo = Files.readAllLines(Paths.get(caminhoArquivo));
        } catch (IOException e) {
            throw new Exception("Erro ao ler arquivo de valores");
        }

        if (linhasArquivo.size() == 0)
            throw new Exception("Arquivo vazio");
        if (linhasArquivo.size() == 1)
            throw new Exception("Ligações não informadas");

        var informacoesGerais = linhasArquivo.get(0).split(" ");
        if (informacoesGerais.length != 2) {
            throw new Exception("Primeira linha inválida");
        }

        var quantidadeCasas = Integer.parseInt(informacoesGerais[0]);
        var maximoLigacoes = Integer.parseInt(informacoesGerais[1]);

        ArrayList<Ligacao> ligacoes = new ArrayList<Ligacao>(quantidadeCasas * (quantidadeCasas - 1) / 2);
        for (int indiceCasaAtual = 0; indiceCasaAtual < quantidadeCasas - 1; indiceCasaAtual++) {
            String[] linhaAtual = linhasArquivo.get(indiceCasaAtual + 1).split(" ");
            if (linhaAtual.length != (quantidadeCasas - indiceCasaAtual - 1)) {
                throw new Exception("Número de conexões incorretas para a casa " + indiceCasaAtual);
            }
            for (int i = 0; i < linhaAtual.length; i++) {
                ligacoes.add(new Ligacao(
                        new Casa(indiceCasaAtual),
                        new Casa(indiceCasaAtual + i + 1),
                        Integer.parseInt(linhaAtual[i])));
            }

        }

        return new InformacoesArquivo(ligacoes, maximoLigacoes);
    }
}
