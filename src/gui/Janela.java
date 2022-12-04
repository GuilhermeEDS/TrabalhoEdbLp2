package gui;

import javax.imageio.ImageIO;
import javax.swing.*;

import dominio.GerenciadorProcessador;
import dominio.Ligacao;

import java.awt.*;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Random;

public class Janela extends JPanel {
    public static final int LARGURA = 800;
    public static final int ALTURA = 800;

    private final Image imagemCasa = ImageIO.read(new File("../img/casa.png"));

    int numeroCasas;
    ArrayList<Ligacao> ligacoes;
    int custo;

    public Janela(GerenciadorProcessador gerenciadorProcessador) throws Exception {
        numeroCasas = gerenciadorProcessador.getInformacoesArquivo().getNumeroCasas();
        ligacoes = gerenciadorProcessador.getSolucoes().get(0);
        custo = gerenciadorProcessador.getCustos().get(0);
    }

    public void paint(Graphics graphics) {
        ((Graphics2D) graphics).setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

        ArrayList<Posicao> posicoesCasas = getPosicoesCasas(numeroCasas);

        desenharLigacoes(graphics, posicoesCasas);

        graphics.setColor(Color.BLACK);
        desenharCasas(graphics, posicoesCasas);

        graphics.setFont(new Font("Arial", Font.PLAIN, 18));
        desenharCustoTotal(graphics);
    }

    private ArrayList<Posicao> getPosicoesCasas(int numeroCasas) {
        final int DISTANCIA_CENTRO = 250;
        ArrayList<Posicao> posicoes = new ArrayList<>(numeroCasas);
        for (int indiceCasa = 0; indiceCasa < numeroCasas; indiceCasa++) {
            double valor = 2 * Math.PI * indiceCasa / numeroCasas;

            int x = (int) (DISTANCIA_CENTRO * Math.cos(valor));
            int y = (int) (DISTANCIA_CENTRO * Math.sin(valor));

            posicoes.add(new Posicao((LARGURA - DISTANCIA_CENTRO / 2) / 2 + x, (ALTURA - DISTANCIA_CENTRO / 2) / 2 + y));
        }

        return posicoes;
    }

    private void desenharCasas(Graphics graphics, ArrayList<Posicao> posicoes) {
        final int DISTANCIA_EXTRA = 20;

        for (int i = 0; i < posicoes.size(); i++) {
            Posicao posicao = posicoes.get(i);
            graphics.drawImage(imagemCasa,
                    posicao.getX() + imagemCasa.getWidth(null) / 2,
                    posicao.getY() + imagemCasa.getHeight(null) / 2,
                    null);
            graphics.drawString(Integer.toString(i),
                    posicao.getX() + imagemCasa.getWidth(null) / 2 + DISTANCIA_EXTRA,
                    posicao.getY() + imagemCasa.getHeight(null) / 2);
        }
    }

    private void desenharLigacoes(Graphics graphics, ArrayList<Posicao> posicoes) {
        Random rand = new Random();
        for (Ligacao ligacao : ligacoes) {
            // Cor aleatória para distinguir as ligações
            graphics.setColor(Color.getHSBColor(rand.nextFloat(), rand.nextFloat(), 0.6f));

            Posicao casa1 = posicoes.get(ligacao.getCasa1().getId());
            Posicao casa2 = posicoes.get(ligacao.getCasa2().getId());

            graphics.drawLine(
                    casa1.getX() + imagemCasa.getWidth(null),
                    casa1.getY() + imagemCasa.getHeight(null),
                    casa2.getX() + imagemCasa.getWidth(null),
                    casa2.getY() + imagemCasa.getWidth(null));

            graphics.drawString(Integer.toString(ligacao.getCusto()),
                    Math.abs(casa1.getX() + casa2.getX())/2 + imagemCasa.getWidth(null),
                    Math.abs(casa1.getY() + casa2.getY())/2 + imagemCasa.getHeight(null) - 20);
        }
    }

    private void desenharCustoTotal(Graphics graphics) {
        graphics.drawString("Custo total: " + custo, 50, 50);
    }

}
