public class Main {
    public static String parseArgs(String[] args) throws Exception {
        if (args.length != 1) {
            throw new Exception("Argumentos incorretos");
        }

        return args[0];
    }

    public static void main(String[] args) {
        try {
            String caminhoArquivo = parseArgs(args);
            InformacoesArquivo informacoesArquivo = LeitorArquivos.lerArquivo(caminhoArquivo);
            System.out.print(informacoesArquivo);
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }
}
