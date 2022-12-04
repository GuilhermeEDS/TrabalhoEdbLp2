package main;

import java.io.IOException;

import javax.swing.JFrame;

import dominio.GerenciadorProcessador;
import gui.Janela;

public class Main {
    public static void main(String[] args) throws Exception {
        GerenciadorProcessador gerenciadorProcessador = new GerenciadorProcessador(args, true);
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
