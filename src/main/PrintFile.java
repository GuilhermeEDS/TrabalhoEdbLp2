package main;

import dominio.GerenciadorProcessador;
import dominio.Ligacao;

import java.io.*;
import java.util.ArrayList;

public class PrintFile {
    public static void generateFile(GerenciadorProcessador gerenciadorProcessador) throws IOException {
        ArrayList<ArrayList<Ligacao>> solucoes = new ArrayList<>();
        ArrayList<Integer> custos = new ArrayList<>();

        try {
            solucoes = gerenciadorProcessador.getSolucoes();
            custos = gerenciadorProcessador.getCustos();
        } catch (Exception e) {
            System.out.println(e.getMessage());
            System.exit(-1);
        }
        final String fileName = "resultado.txt";

        OutputStream os = new FileOutputStream("../data/" + fileName);
        Writer wr = new OutputStreamWriter(os);
        BufferedWriter br = new BufferedWriter(wr);

        int[] tamanhos = getBiggerSize(solucoes);
        int maiorCusto = String.valueOf(getBiggerCost(custos)).length();

        br.write("#" + getCaracter(26 + 3 + 9 + tamanhos[3], '=') + "#");
        br.newLine();

        int somaTamanhoEspacos = (3 + 9 + tamanhos[3]);
        br.write("|" + getCaracter(somaTamanhoEspacos / 2, ' ') + "COMBINAÇÃO COM MENOR CUSTO" + getCaracter((int) Math.ceil((double) somaTamanhoEspacos / 2), ' ') + "|");
        br.newLine();

        br.write("#" + getCaracter(26 + 3 + 9 + tamanhos[3], '=') + "#");
        br.newLine();

        for (int i = 0; i < solucoes.size(); i++) {
            ArrayList<Ligacao> listaLigacoes = solucoes.get(i);

            for (Ligacao ligacao : listaLigacoes) {
                int casaA = ligacao.getCasa1().getId();
                int sizeCasaA = String.valueOf(casaA).length();

                int casaB = ligacao.getCasa2().getId();
                int sizeCasaB = String.valueOf(casaB).length();

                int custo = ligacao.getCusto();
                int sizeCusto = String.valueOf(custo).length();

                br.write("| Ligação entre as casas: " + (sizeCasaA >= tamanhos[0] ? casaA : casaA + getCaracter((tamanhos[0] - sizeCasaA), ' ')) + " e " + (sizeCasaB >= tamanhos[1] ? casaB : casaB + getCaracter((tamanhos[1] - sizeCasaB), ' ')) + "; Custo: " + (sizeCusto >= tamanhos[2] ? custo : custo + getCaracter((tamanhos[2] - sizeCusto), ' ')) + " |");
                br.newLine();

            }

            int custoTotal = custos.get(i);

            br.write("| O custo total é: " + (String.valueOf(custoTotal).length() >= maiorCusto ? custoTotal : custoTotal + getCaracter((maiorCusto - custoTotal), ' ')) + getCaracter(((38 + tamanhos[3]) - (19 + (maiorCusto - String.valueOf(custoTotal).length()))) - 1, ' ') + "|");
            br.newLine();
            br.write("#" + getCaracter(26 + 3 + 9 + tamanhos[3], '=') + "#");
            br.newLine();

        }

        br.close();

    }

    private static int[] getBiggerSize(ArrayList<ArrayList<Ligacao>> data) {
        int[] response = {0, 0, 0, 0};

        for (ArrayList<Ligacao> ListaLigacoes : data) {
            for (Ligacao ligacao : ListaLigacoes) {
                int casaA = ligacao.getCasa1().getId();
                int sizeCasaA = String.valueOf(casaA).length();
                response[0] = Math.max(sizeCasaA, response[0]);

                int casaB = ligacao.getCasa2().getId();
                int sizeCasaB = String.valueOf(casaB).length();
                response[1] = Math.max(sizeCasaB, response[1]);

                int custo = ligacao.getCusto();
                int sizeCusto = String.valueOf(custo).length();
                response[2] = Math.max(sizeCusto, response[2]);
            }
        }
        response[3] = response[0] + response[1] + response[2];

        return response;
    }

    private static String getCaracter(int quantidade, char caracter) {
        return String.valueOf(caracter).repeat(Math.max(0, quantidade));
    }

    private static int getBiggerCost(ArrayList<Integer> custos) {
        int response = 0;
        for (var custo : custos)
            response = custo > response ? custo : response;
        return response;
    }
}
