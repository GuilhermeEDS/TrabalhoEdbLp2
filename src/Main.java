import DataStructure.BinarySearchTree.ArvoreBuscaBinaria;

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

            ArvoreBuscaBinaria bst = new ArvoreBuscaBinaria(0);

            System.out.println(
                bst.buscar(0)
            );

        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }
}
