package main;

import javax.swing.JFrame;

import dominio.GerenciadorProcessador;
import dominio.Ligacao;
import gui.Janela;

public class Main {
    public static void main(String[] args) throws Exception {

        GerenciadorProcessador gerenciadorProcessador = new GerenciadorProcessador(args);

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

        int size = gerenciadorProcessador.getSolucoes().get(0).size();
        System.out.print("[ ");
        for (int i = 0; i < size; i++ ) {
            var ligacao = gerenciadorProcessador.getSolucoes().get(0).get(i);
            System.out.print( ligacao + ( i == size - 1 ? " " : ", ") );
        }
        System.out.println("]");

        JFrame frame = new JFrame();

        frame.getContentPane().add(new Janela(gerenciadorProcessador));
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(Janela.LARGURA, Janela.ALTURA);
        frame.setVisible(true);
    }
}
