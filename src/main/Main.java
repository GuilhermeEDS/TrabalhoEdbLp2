package main;

import dominio.GerenciadorProcessador;

public class Main {
    public static void main(String[] args) {
        GerenciadorProcessador gerenciadorProcessador = new GerenciadorProcessador(args, true);
        try {
            gerenciadorProcessador.executar();
        } catch (Exception e) {
            System.out.println("Erro ao obter árvore");
            System.exit(-1);
        }

        try {
            PrintFile.generateFile(gerenciadorProcessador);
        } catch (Exception e) {
            System.out.println("DEU ERRO NA HORA DE FAZER O ARQUIVO");
            System.out.println(e.getMessage());
        }
    }
}
