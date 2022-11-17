import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.*;

public class LeituraArquivos {
    public static InformacoesArquivo lerArquivo(String caminhoArquivo) throws Exception {
        String values = null;

        try {
            values = Files.readString(Paths.get(caminhoArquivo), StandardCharsets.UTF_8);
        } catch (IOException e) {
            throw new Exception("Erro ao ler arquivo de valores");
        }

        if (values == "")
            throw new Exception("Arquivo vazio");

        String[] parsedValues = values.split(" ", 0);

        return null;
    }
}
