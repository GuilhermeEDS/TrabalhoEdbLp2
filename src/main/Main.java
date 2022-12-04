package main;

import javax.swing.JFrame;

import dominio.GerenciadorProcessador;
import gui.Janela;

public class Main {
    public static void main(String[] args) throws Exception {

        boolean executarProcessadorComplexo = true;

        if ( args.length > 1) {
            if ( args[1] == "--simples" ) {
                executarProcessadorComplexo = false;
            } else if ( args[1] == "--complexo" ) {
                executarProcessadorComplexo = true;
            } else {
                System.out.println("Uso correto: java -jar arquivo <caminhoArquivo> <tipoSolucao>\n\t<caminhoArquivo>: O caminho para o arquivo contendo " + "o máximo de ligações e o custo de cada ligação possível\n\t<tipoSolucao>: O tipo de solução que deverá ser executado, sendo --simples para executar a primeira forma e --complexo para a segunda forma\n\t\tSe for passado sem este executará a segunda forma");
                System.exit(-1);
            }
        }

        GerenciadorProcessador gerenciadorProcessador = new GerenciadorProcessador(args, executarProcessadorComplexo);
        try {
            gerenciadorProcessador.executar();
        } catch (Exception e) {
            System.out.println(e.getMessage());
            System.exit(-1);
        }

        try {
            PrintFile.generateFile(gerenciadorProcessador);
        } catch (Exception e) {
            System.out.println("DEU ERRO NA HORA DE FAZER O ARQUIVO");
            System.out.println(e.getMessage());
        }

        JFrame frame = new JFrame();

        frame.getContentPane().add(new Janela(gerenciadorProcessador));
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(Janela.LARGURA, Janela.ALTURA);
        frame.setVisible(true);
    }
}
