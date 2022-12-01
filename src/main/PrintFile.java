package main;
import java.io.*;
import java.util.ArrayList;
import java.util.List;

import dominio.Ligacao;

public class PrintFile {
    public static void generateFile( ArrayList<ArrayList<Ligacao>> data ) throws IOException{
        final String fileName = "resultado.txt";

        OutputStream os = new FileOutputStream("../data/"+fileName);
        Writer wr = new OutputStreamWriter(os);
        BufferedWriter br = new BufferedWriter(wr);

        int [] tamanhos = getBiggerSize(data);
        
        br.write("#" + getCaracter( 26 + 3 + 9 + tamanhos[3], '=' ) + "#");
        br.newLine();

        int somaTamanhoEspacos = (3 + 9 + tamanhos[3]);
        br.write("|" + 
                 getCaracter( (int) Math.floor( somaTamanhoEspacos / 2 ), ' ') + 
                 "COMBINAÇÃO COM MENOR CUSTO" +
                 getCaracter( (int) Math.ceil( somaTamanhoEspacos / 2 ), ' ') + 
                 "|");
        br.newLine();

        br.write("#" + getCaracter( 26 + 3 + 9 + tamanhos[3], '=' ) + "#");
        br.newLine();
        
        for (var ListaLigacoes : data) {
            for ( var ligacao : ListaLigacoes) {
                int casaA = ligacao.getCasa1().getId();
                int sizeCasaA = String.valueOf( casaA ).length();

                int casaB = ligacao.getCasa2().getId();
                int sizeCasaB = String.valueOf( casaB ).length();

                int custo = ligacao.getCusto();
                int sizeCusto = String.valueOf(custo).length();

                br.write("| Ligação entre as casas: "+ 
                         ( 
                            sizeCasaA >= tamanhos[0] ? 
                            casaA :
                            casaA + getCaracter( (tamanhos[0] - sizeCasaA) , ' ')
                         ) + " e " + 
                         (
                            sizeCasaB >= tamanhos[1] ?
                            casaB :
                            casaB + getCaracter( (tamanhos[1] - sizeCasaB), ' ')
                         ) + "; Custo: " + 
                         (
                            sizeCusto >= tamanhos[2] ?
                            custo :
                            custo + getCaracter( (tamanhos[2] - sizeCusto), ' ')
                         ) +" |");
                br.newLine();
            
            }

            br.write("#" + getCaracter( 26 + 3 + 9 + tamanhos[3], '=' ) + "#");
            br.newLine();
        }

        br.close();

    }

    private static int[] getBiggerSize( ArrayList<ArrayList<Ligacao>> data ) {
        int [] response = {0,0,0,0};

        for (ArrayList<Ligacao> ListaLigacoes : data) {
            for ( Ligacao ligacao : ListaLigacoes) {
                int casaA = ligacao.getCasa1().getId();
                int sizeCasaA = String.valueOf( casaA ).length();
                response[0] = sizeCasaA > response[0] ? sizeCasaA : response[0];

                int casaB = ligacao.getCasa2().getId();
                int sizeCasaB = String.valueOf( casaB ).length();
                response[1] = sizeCasaB > response[1] ? sizeCasaB : response[1];

                int custo = ligacao.getCusto();
                int sizeCusto = String.valueOf(custo).length();
                response[2] = sizeCusto > response[2] ? sizeCusto : response[2];
            }
        }       
        response[3] = response[0] + response[1] + response[2];

        return response;
    }

    private static String getCaracter(int quantidade, char caracter) {
        String response = "";
        for (int i = 0; i < quantidade; i++)
            response += caracter;
        return response;
    } 

}
